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
    AFTER UPDATE
    AS
BEGIN
    DELETE
    FROM dbo.Federacion
    WHERE Federacion.id_cliente = deleted.id_cliente
      AND deleted.valido = 0
END
go

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
