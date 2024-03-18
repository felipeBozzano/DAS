USE Publicista;

/* Factura */

CREATE OR ALTER PROCEDURE Registrar_Factura
AS
BEGIN
    INSERT INTO dbo.Factura(total, fecha, estado, id_publicista, id_plataforma)
    VALUES (0, (SELECT CONVERT(date, CURRENT_TIMESTAMP)), 0, NULL, @id_plataforma)
END
go

/* Reporte */

CREATE OR ALTER PROCEDURE Registrar_Reporte @id_publicista INT
AS
BEGIN
    INSERT INTO dbo.Reporte(total, fecha, estado, id_publicista, id_plataforma)
    VALUES (0, (SELECT CONVERT(date, CURRENT_TIMESTAMP)), 0, @id_publicista, NULL)
END
go

/* Partner */

CREATE OR ALTER PROCEDURE Crear_Partner
CREATE OR ALTER PROCEDURE Editar_Partner
CREATE OR ALTER PROCEDURE Eliminar_Partner

/* Publicidad */

CREATE OR ALTER PROCEDURE Registrar_Publicidad @id_publicista VARCHAR(255),
                                               @url_de_imagen VARCHAR(255),
                                               @url_de_publicidad VARCHAR(255),
                                               @fecha_de_alta DATE,
                                               @fecha_de_baja DATE
AS
BEGIN
    INSERT INTO dbo.Publicidad(id_publicista, id_banner, codigo_publicidad, url_de_imagen,
                               url_de_publicidad, fecha_de_alta, fecha_de_baja)
    VALUES (@id_publicista, @id_banner, @codigo_publicidad, @url_de_imagen, @url_de_publicidad, @fecha_de_alta,
            @fecha_de_baja)
END
go

CREATE OR ALTER PROCEDURE Editar_Publicidad @id_publicidad INT,
                                            @id_publicista VARCHAR(255),
                                            @id_banner VARCHAR(255),
                                            @codigo_publicidad VARCHAR(255),
                                            @url_de_imagen VARCHAR(255),
                                            @url_de_publicidad VARCHAR(255),
                                            @fecha_de_baja DATE
AS
BEGIN
    UPDATE dbo.Publicidad
    SET id_publicista     = @id_publicista,
        id_banner         = @id_banner,
        codigo_publicidad = @codigo_publicidad,
        url_de_imagen     = @url_de_imagen,
        url_de_publicidad = @url_de_publicidad,
        fecha_de_baja     = @fecha_de_baja
    WHERE id_publicidad = @id_publicidad
END
go

CREATE OR ALTER PROCEDURE Eliminar_Publicidad @id_publicidad INT
AS
BEGIN
    DELETE
    FROM dbo.Publicidad
    WHERE id_publicidad = @id_publicidad
END
go
