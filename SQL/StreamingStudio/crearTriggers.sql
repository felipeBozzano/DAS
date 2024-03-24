USE StreamingStudio
go

/*
    FINALIZAR UNA FEDERACIÓN IMPLICA OBTENER EL TOKEN ÚNICO DE USUARIO DE LA PLATAFORMA DE STREAMING.
    UNA VEZ QUE SE OBTIENE EL TOKEN, EN LA TABLA TRANSACCIÓN SE ACTUALIZA EL REGISTRO VINCULADO A ESA FEDERACIÓN
    DONDE ACTUALIZAMOS EL CAMPO TOKEN.
    SI EL CAMPO TOKEN ES ACTUALIZADO, ENTONCES SE DISPARA EL TRIGGER QUE CREA UN REGISTRO EN LA TABLA FEDERACIÓN.
*/

CREATE OR ALTER TRIGGER Registrar_Federacion
    ON dbo.Transaccion
    AFTER UPDATE
    AS
BEGIN
    WITH Federacion_Terminada AS (SELECT i.id_plataforma, i.id_cliente, i.token, i.tipo_usuario, i.facturada
                                  FROM inserted i
                                           JOIN deleted d ON i.id_cliente = d.id_cliente
                                  WHERE i.token IS NOT NULL
                                    and d.token IS NULL
                                    and i.fecha_baja IS NULL
                                    and d.fecha_baja IS NULL)
    INSERT
    INTO dbo.Federacion
    SELECT id_plataforma,
           id_cliente,
           token,
           tipo_usuario,
           facturada
    FROM Federacion_Terminada
END
go

/*
    DESVINCULAR UNA FEDERACIÓN, IMPLICA ELIMINAR EL REGISTRO QUE UNE AL USUARIO CON LA PLATAFORMA DE STREAMING A LA
    CUAL ESTABA FEDERADO.
    AL ELIMINAR DICHA FEDERACIÓN, SE DISPARA ESTE TRIGGER EN LA TABLA TRANSACCIÓN QUE BUSCA EL REGISTRO ASOCIADO A LA
    FEDERACIÓN, Y ACTUALIZA SU FECHA DE BAJA A LA HORA ACTUAL.
 */

CREATE OR ALTER TRIGGER Dar_de_Baja_Transaccion
    ON dbo.Federacion
    AFTER DELETE
    AS
BEGIN
    UPDATE Transaccion
    SET fecha_baja = GETDATE()
    FROM dbo.Transaccion AS t
             JOIN deleted AS d ON t.id_plataforma = d.id_plataforma AND t.id_cliente = d.id_cliente
    WHERE fecha_alta = (SELECT MAX(fecha_alta)
                        FROM dbo.Transaccion AS tt
                        WHERE tt.id_plataforma = d.id_plataforma
                          AND tt.id_cliente = d.id_cliente);
END
go

/*
    CUANDO UN USUARIO DECIDE DAR DE BAJA SU CUENTA, EL SISTEMA ACTUALIZA EL CAMPO VALIDO = 0.
    ESA SITUACION DISPARA ESTE TRIGGER QUE ELIMINA TODAS LAS FEDERACIONES ASOCIADAS A DICHO USUARIO.
 */

CREATE OR ALTER TRIGGER Desvincular_Federaciones
    ON dbo.Cliente_Usuario
    FOR UPDATE
    AS
BEGIN
    WITH Usuario_Dado_de_Baja AS (SELECT i.id_cliente
                                  FROM inserted i
                                           JOIN deleted d ON i.id_cliente = d.id_cliente
                                  WHERE i.valido = 0
                                    and d.valido = 1)
    DELETE f
    FROM dbo.Federacion f
             JOIN Usuario_Dado_de_Baja u ON f.id_cliente = u.id_cliente
END
go

/*
    SI UNA PLATAFORMA DE STREAMING SE ELIMINA, ELIMINE TODAS LAS FEDERACIONES DE ESA PLATAFORMA.
 */

CREATE OR ALTER TRIGGER Eliminar_Federaciones
    ON dbo.Plataforma_de_Streaming
    AFTER UPDATE
    AS
BEGIN
    WITH Plataforma_Dada_de_Baja AS (SELECT i.id_plataforma
                                     FROM inserted i
                                              JOIN deleted d ON i.id_plataforma = d.id_plataforma
                                     WHERE i.valido = 0
                                       and d.valido = 1)
    DELETE f
    FROM dbo.Federacion f
             JOIN Plataforma_Dada_de_Baja p ON p.id_plataforma = f.id_plataforma
END
go

/*SI UNA FEDERACION SE FACTURA (CAMBIA FACTURADA A 1) ENTONCES LA TRANSACCION ASOCIADA A ESA
  FEDERACION (EN LA TABLA TRANSACCIONES) TAMBIEN CAMBIE SU ESTADO DE FACTURADA A 1.*/

CREATE OR ALTER TRIGGER Facturar_Transacciones
    ON dbo.Federacion
    AFTER UPDATE
    AS
BEGIN
    WITH Federacion_Facturada AS (SELECT i.id_plataforma, i.id_cliente
                                  FROM inserted i
                                           JOIN deleted d
                                                ON i.id_cliente = d.id_cliente and i.id_plataforma = d.id_plataforma
                                  WHERE i.facturada = 1
                                    and d.facturada = 0)
    UPDATE dbo.Transaccion
    SET facturada = 1
    FROM dbo.Transaccion t
    WHERE EXISTS(SELECT 1
                 FROM Federacion_Facturada f
                 WHERE t.id_plataforma = f.id_plataforma
                   AND t.id_cliente = f.id_cliente)
      AND t.fecha_alta = (SELECT MAX(fecha_alta)
                          FROM dbo.Transaccion
                          WHERE id_plataforma = t.id_plataforma
                            AND id_cliente = t.id_cliente)
END
go


-- CREATE TRIGGER actualizar_facturada_transaccion AFTER UPDATE ON Plataforma_de_Streaming
-- FOR EACH ROW
-- BEGIN
--     IF NEW.facturada = 1 THEN
--         UPDATE Transaccion SET facturada = 1 WHERE id_plataforma = NEW.id_plataforma;
--     END IF;
-- END;

/*CREATE OR ALTER TRIGGER Verificar_Federacion
    ON dbo.Transaccion
    FOR UPDATE
    AS
    BEGIN
        IF EXISTS (SELECT 1 FROM deleted WHERE token IS NOT NULL)
        AND NOT EXISTS (SELECT 1 FROM dbo.Federacion WHERE id_cliente = (SELECT id_cliente FROM deleted)
                                                       AND id_plataforma = (SELECT id_plataforma FROM deleted)
                                                       AND codigo_de_transaccion = (SELECT codigo_de_transaccion FROM deleted))
            BEGIN
                RAISERROR ('No se permite actualizar si el campo token no es nulo.', 16, 1)
            END
    END
go*/
