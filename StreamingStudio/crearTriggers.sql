USE StreamingStudio
go

CREATE OR ALTER TRIGGER Registrar_Federacion
    ON dbo.Transaccion
    AFTER UPDATE
    AS
BEGIN
    INSERT INTO dbo.Federacion
    SELECT id_plataforma,
           id_cliente,
           codigo_de_transaccion,
           token,
           tipo_usuario,
           fecha_alta,
           IIF(EXISTS(SELECT 1
                      FROM dbo.Transaccion
                      WHERE Transaccion.id_plataforma = inserted.id_plataforma
                        AND Transaccion.id_cliente = inserted.id_cliente
                        AND Transaccion.facturada = 1), 1, 0)
    FROM inserted
    WHERE inserted.fecha_baja IS NULL
      AND inserted.token IS NOT NULL
END
go

CREATE OR ALTER TRIGGER Dar_de_Baja_Transaccion
    ON dbo.Federacion
    AFTER DELETE
    AS
BEGIN
    UPDATE dbo.Transaccion
    SET fecha_baja = CURRENT_TIMESTAMP
    WHERE id_cliente = (SELECT id_cliente FROM deleted)
      AND id_plataforma = (SELECT id_plataforma FROM deleted)
      AND codigo_de_transaccion = (SELECT codigo_de_transaccion FROM deleted)
END
go

CREATE OR ALTER TRIGGER Desvincular_Federaciones
    ON dbo.Cliente_Usuario
    FOR UPDATE
    AS
BEGIN
    DELETE f
    FROM dbo.Federacion f
             JOIN inserted i ON f.id_cliente = i.id_cliente
    WHERE i.valido = 0;
END
go


/*AGREGAR TRIGGER QUE SI UNA PLATAFORMA DE STREAMING SE ELIMINA, ELIMINE TODAS LAS FEDERACIONES DE ESA PLATAFORMA.*/

CREATE OR ALTER TRIGGER Eliminar_Federaciones
    ON dbo.Plataforma_de_Streaming
    AFTER UPDATE
BEGIN
    DELETE 
    FROM Federacion f
        JOIN inserted i ON i.id_plataforma = f.id_plataforma
    WHERE i.valido = 0;
END

/*AGREGAR TRIGGER PARA QUE SI UNA FEDERACION SE FACTURA (CAMBIA FACTURADA A 1) ENTONCES LA TRANSACCION ASOCIADA A ESA
   FEDERACION (EN LA TABLA TRANSACCIONES) TAMBIEN CAMBIE SU ESTADO DE FACTURADA A 1.*/

CREATE OR ALTER TRIGGER Facturar_Transacciones
    ON dbo.Federacion
    AFTER UPDATE
BEGIN
    UPDATE Transacion
    SET facturada = 1
        JOIN inserted i ON i.id_plataforma = t.id_plataforma AND i.id_cliente = t.id_cliente
    WHERE t.fecha_alta = (
                            SELECT MAX(fecha_alta)
                            FROM Transacciones
                            WHERE id_plataforma = t.id_plataforma
                                AND id_cliente = t.id_cliente
                        )

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
