USE Publicista_Bicicletas;

/* Publicidad */

CREATE OR ALTER PROCEDURE Registrar_Publicidad @id_publicista VARCHAR(255),
                                               @url_de_imagen VARCHAR(255),
                                               @url_de_publicidad VARCHAR(255),
                                               @fecha_de_alta DATE,
                                               @fecha_de_baja DATE,
                                               @tipo_banner VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Publicidad(codigo_publicidad, url_de_imagen, url_de_publicidad, fecha_de_alta, fecha_de_baja,
                               tipo_banner)
    VALUES (@id_publicista, @url_de_imagen, @url_de_publicidad, @fecha_de_alta, @fecha_de_baja, @tipo_banner)
END
go

CREATE OR ALTER PROCEDURE Obtener_Datos_de_Publicidades
AS
BEGIN
    SELECT codigo_publicidad, url_de_publicidad, url_de_imagen
    FROM dbo.Publicidad
    WHERE fecha_de_baja >= GETDATE()
      AND fecha_de_alta <= GETDATE()
END
go

/* Partner */

CREATE OR ALTER PROCEDURE Crear_Partner @nombre VARCHAR(255),
                                        @token_de_servicio VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Partner (nombre, token_de_servicio)
    VALUES (@nombre, @token_de_servicio);
END
go

CREATE OR ALTER PROCEDURE Editar_Partner @id_partner INT,
                                         @nombre VARCHAR(255),
                                         @token_de_servicio VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Partner
    SET nombre            = @nombre,
        token_de_servicio = @token_de_servicio
    WHERE id_partner = @id_partner;
END
go

CREATE OR ALTER PROCEDURE Eliminar_Partner @id_partner INT
AS
BEGIN
    DELETE
    FROM dbo.Partner
    WHERE id_partner = @id_partner;
END
go

CREATE OR ALTER PROCEDURE Verificar_Token_de_Partner @token VARCHAR(255)
AS
BEGIN
    SELECT CASE
               WHEN EXISTS (SELECT 1
                            FROM dbo.Partner
                            WHERE token_de_servicio = @token) THEN 'true'
               ELSE 'false'
               END AS ExistePartner;
END
go

/* Factura */

CREATE OR ALTER PROCEDURE Registrar_Factura @totaL FLOAT,
                                            @fecha DATE,
                                            @descripcion VARCHAR
AS
BEGIN
    INSERT INTO dbo.Factura(total, fecha, descripcion)
    VALUES (@total, @fecha, @descripcion)
END
go

/* Reporte */

CREATE OR ALTER PROCEDURE Registrar_Reporte @total FLOAT,
                                            @fecha DATE,
                                            @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Reporte(total, fecha, descripcion)
    VALUES (@total, @fecha, @descripcion)
END
go
