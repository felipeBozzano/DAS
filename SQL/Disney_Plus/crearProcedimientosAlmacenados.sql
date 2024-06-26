/* Cliente_Usuario */

CREATE OR ALTER PROCEDURE Crear_Usuario @contrasena VARCHAR(255),
                                        @email VARCHAR(255),
                                        @nombre VARCHAR(255),
                                        @apellido VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Cliente_Usuario(contrasena, email, nombre, apellido, valido)
    VALUES (@contrasena, @email, @nombre, @apellido, 0)
END
go

CREATE OR ALTER PROCEDURE Editar_Usuario @id_cliente INT,
                                         @usuario VARCHAR(255),
                                         @contrasena VARCHAR(255),
                                         @email VARCHAR(255),
                                         @nombre VARCHAR(255),
                                         @apellido VARCHAR(255)
AS
BEGIN
    UPDATE Cliente_Usuario
    SET contrasena = @contrasena,
        email      = @email,
        nombre     = @nombre,
        apellido   = @apellido
    WHERE id_cliente = @id_cliente
END
go

CREATE OR ALTER PROCEDURE Eliminar_Usuario @id_cliente INT
AS
BEGIN
    UPDATE Cliente_Usuario
    SET valido = 0
    WHERE id_cliente = @id_cliente
END
go

CREATE OR ALTER PROCEDURE Validar_Usuario @id_cliente INT
AS
BEGIN
    UPDATE Cliente_Usuario
    SET valido = 1
    WHERE id_cliente = @id_cliente
END
go

CREATE OR ALTER PROCEDURE Informacion_Usuario @email VARCHAR(255),
                                              @contrasena VARCHAR(255)
AS
BEGIN
    SELECT *
    FROM Cliente_Usuario
    WHERE email = @email
      AND contrasena = @contrasena
END;
go

CREATE OR ALTER PROCEDURE Login_Usuario @email VARCHAR(255),
                                        @contrasena VARCHAR(255)
AS
BEGIN
    DECLARE @resultado INT;

    -- Verificar si existe el usuario y contrasena
    IF EXISTS (SELECT 1
               FROM Cliente_Usuario
               WHERE email = @email
                 AND contrasena = @contrasena)
        BEGIN
            SET @resultado = 1; -- Usuario y contrasena coinciden
        END
    ELSE
        BEGIN
            SET @resultado = 0; -- Usuario y/o contrasena no coinciden
        END

    -- Devolver el resultado
    SELECT @resultado AS 'ExisteUsuario';
END;
go

/* Transaccion */

CREATE OR ALTER PROCEDURE Crear_Transaccion @codigo_de_transaccion VARCHAR(255),
                                            @url_de_redireccion VARCHAR(255),
                                            @tipo_de_transaccion VARCHAR(1)
AS
BEGIN
    INSERT INTO dbo.Autorizacion(codigo_de_transaccion, id_cliente, token, fecha_de_alta, url_de_redireccion,
                                 tipo_de_transaccion, fecha_de_baja)
    VALUES (@codigo_de_transaccion, NULL, NULL, GETDATE(), @url_de_redireccion, @tipo_de_transaccion, NULL)
END
go

CREATE OR ALTER PROCEDURE Verificar_Codigo @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    SELECT CASE
               WHEN EXISTS (SELECT 1
                            FROM dbo.Autorizacion
                            WHERE codigo_de_transaccion = @codigo_de_transaccion) THEN 'true'
               ELSE 'false'
               END AS ExisteValor;
END
go


/* Autorizacion */

CREATE OR ALTER PROCEDURE Crear_Autorizacion @id_cliente INT,
                                             @codigo_de_transaccion VARCHAR(255),
                                             @token VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Autorizacion
    SET id_cliente = @id_cliente,
        token      = @token
    WHERE codigo_de_transaccion = @codigo_de_transaccion
END
go

CREATE OR ALTER PROCEDURE Desautorizar @id_cliente INT,
                                       @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    UPDATE Autorizacion
    set fecha_de_baja = GETDATE()
    WHERE id_cliente = @id_cliente
      AND codigo_de_transaccion = @codigo_de_transaccion
END
go

CREATE OR ALTER PROCEDURE Obtener_Token @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    SELECT token
    FROM dbo.Autorizacion
    WHERE codigo_de_transaccion = @codigo_de_transaccion
      AND fecha_de_baja IS NULL
END
go

CREATE OR ALTER PROCEDURE Obtener_Cliente @token VARCHAR(255)
AS
BEGIN
    SELECT id_cliente
    FROM dbo.Autorizacion
    WHERE token = @token
      AND fecha_de_baja IS NULL
END
go

/* Sesion */

CREATE OR ALTER PROCEDURE Crear_Sesion @id_cliente INT,
                                       @id_partner INT,
                                       @sesion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Sesion (id_cliente, sesion, fecha_de_creacion, fecha_de_uso, id_partner)
    VALUES (@id_cliente, @sesion, GETDATE(), null, @id_partner)
END
go

CREATE OR ALTER PROCEDURE Usar_Sesion @sesion VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Sesion
    SET fecha_de_uso = GETDATE()
    WHERE sesion = @sesion
      AND fecha_de_uso IS NULL
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
                            WHERE @token = @token) THEN 'true'
               ELSE 'false'
               END AS ExistePartner;
END
go

CREATE OR ALTER PROCEDURE Obtener_ID_de_Partner @token VARCHAR(255)
AS
BEGIN
    SELECT id_partner
    FROM dbo.Partner
    WHERE token_de_servicio = @token;
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

CREATE OR ALTER PROCEDURE Registrar_Reporte @totaL FLOAT,
                                            @fecha DATE,
                                            @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Reporte(total, fecha, descripcion)
    VALUES (@total, @fecha, @descripcion)
END
go

/* Director */

CREATE OR ALTER PROCEDURE Registrar_Director @nombre VARCHAR(255),
                                             @apellido VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Director(nombre, apellido)
    VALUES (@nombre, @apellido)
END
go

CREATE OR ALTER PROCEDURE Modificar_Director @id_director INT,
                                             @nombre VARCHAR(255),
                                             @apellido VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Director
    SET nombre   = @nombre,
        apellido = @apellido
    WHERE id_director = @id_director
END
go

CREATE OR ALTER PROCEDURE Eliminar_Director @id_director INT
AS
BEGIN
    DELETE
    FROM dbo.Director
    WHERE id_director = @id_director
END
go

/* Actor */

CREATE OR ALTER PROCEDURE Registrar_Actor @nombre VARCHAR(255),
                                          @apellido VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Actor(nombre, apellido)
    VALUES (@nombre, @apellido)
END
go

CREATE OR ALTER PROCEDURE Modificar_Actor @id_actor INT,
                                          @nombre VARCHAR(255),
                                          @apellido VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Actor
    SET nombre   = @nombre,
        apellido = @apellido
    WHERE id_actor = @id_actor
END
go

CREATE OR ALTER PROCEDURE Eliminar_Actor @id_actor INT
AS
BEGIN
    DELETE
    FROM dbo.Actor
    WHERE id_actor = @id_actor
END
go

/* Director_Contenido */

CREATE OR ALTER PROCEDURE Crear_Director_Contenido @id_contenido INT,
                                                   @id_director INT
AS
BEGIN
    INSERT INTO dbo.Director_Contenido(id_contenido, id_director)
    VALUES (@id_contenido, @id_director)
END
go

CREATE OR ALTER PROCEDURE Eliminar_Director_Contenido @id_contenido INT,
                                                      @id_director INT
AS
BEGIN
    DELETE
    FROM dbo.Director_Contenido
    WHERE id_contenido = @id_contenido
      AND id_director = @id_director
END
go

/* Actor_Contenido */

CREATE OR ALTER PROCEDURE Crear_Actor_Contenido @id_contenido INT,
                                                @id_actor INT
AS
BEGIN
    INSERT INTO dbo.Actor_Contenido(id_contenido, id_actor)
    VALUES (@id_contenido, @id_actor)
END
go

CREATE OR ALTER PROCEDURE Eliminar_Actor_Contenido @id_contenido INT,
                                                   @id_actor INT
AS
BEGIN
    DELETE
    FROM dbo.Actor_Contenido
    WHERE id_contenido = @id_contenido
      AND id_actor = @id_actor
END
go

/* Genero */

CREATE OR ALTER PROCEDURE Crear_Genero @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Genero(descripcion)
    VALUES (@descripcion)
END
go

CREATE OR ALTER PROCEDURE Modificar_Genero @id_genero INT,
                                           @descripcion VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Genero
    SET descripcion = @descripcion
    WHERE id_genero = @id_genero
END
go

CREATE OR ALTER PROCEDURE Eliminar_Genero @id_genero INT
AS
BEGIN
    DELETE
    FROM dbo.Genero
    WHERE id_genero = @id_genero
END
go

/* Contenido */

CREATE OR ALTER PROCEDURE Crear_Contenido @id_contenido VARCHAR(255),
                                          @titulo VARCHAR(255),
                                          @descripcion VARCHAR(255),
                                          @url_reproduccion VARCHAR(255),
                                          @url_imagen VARCHAR(255),
                                          @clasificacion SMALLINT,
                                          @reciente BIT,
                                          @destacado BIT,
                                          @valido BIT
AS
BEGIN
    INSERT INTO dbo.Contenido(id_contenido, titulo, descripcion, url_imagen, url_reproduccion, clasificacion, reciente,
                              destacado, valido)
    VALUES (@id_contenido, @titulo, @descripcion, @url_imagen, @url_reproduccion, @clasificacion, @reciente, @destacado,
            @valido)
END
go

CREATE OR ALTER PROCEDURE Modificar_Contenido @id_contenido VARCHAR(255),
                                              @titulo VARCHAR(255),
                                              @descripcion VARCHAR(255),
                                              @url_imagen VARCHAR(255),
                                              @clasificacion SMALLINT,
                                              @reciente BIT,
                                              @destacado BIT
AS
BEGIN
    UPDATE dbo.Contenido
    SET titulo        = @titulo,
        descripcion   = @descripcion,
        url_imagen    = @url_imagen,
        clasificacion = @clasificacion,
        reciente      = @reciente,
        destacado     = @destacado
    WHERE id_contenido = @id_contenido
END
go

CREATE OR ALTER PROCEDURE Eliminar_Contenido @id_contenido INT
AS
BEGIN
    DELETE
    FROM dbo.Contenido
    WHERE id_contenido = @id_contenido
END
go

CREATE OR ALTER PROCEDURE Obtener_Contenido
AS
BEGIN
    SELECT *
    FROM dbo.Contenido
    WHERE valido = 1
END
go

CREATE OR ALTER PROCEDURE Obtener_Url_de_Contenido @id_contenido VARCHAR(255)
AS
BEGIN
    SELECT url_reproduccion
    FROM dbo.Contenido
    WHERE id_contenido = @id_contenido
END
GO

/* Clasificacion */

CREATE OR ALTER PROCEDURE Crear_Clasificacion @id_clasificacion VARCHAR(255),
                                              @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Clasificacion(id_clasificacion, descripcion)
    VALUES (@id_clasificacion, @descripcion)
END
go

CREATE OR ALTER PROCEDURE Modificar_Clasificacion @id_clasificacion INT,
                                                  @descripcion VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Clasificacion
    SET descripcion = @descripcion
    WHERE id_clasificacion = @id_clasificacion
END
go

CREATE OR ALTER PROCEDURE Eliminar_Clasificacion @id_clasificacion INT
AS
BEGIN
    DELETE
    FROM dbo.Clasificacion
    WHERE id_clasificacion = @id_clasificacion
END
go

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------ FEDERAR USUARIO ------------------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

/* Verificar_Token_de_Partner */

/* Crear_Transaccion */

CREATE OR ALTER PROCEDURE Obtener_codigo_de_redireccion @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    SELECT url_de_redireccion
    FROM dbo.Autorizacion
    WHERE codigo_de_transaccion = @codigo_de_transaccion
END
go

/* Obtener_Token */

CREATE OR ALTER PROCEDURE Verificar_Autorizacion @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    SELECT A.id_cliente, T.url_de_redireccion
    FROM dbo.Autorizacion A
             JOIN dbo.Autorizacion T ON A.codigo_de_transaccion = T.codigo_de_transaccion
    WHERE A.codigo_de_transaccion = @codigo_de_transaccion
END
go

CREATE OR ALTER PROCEDURE Obtener_Contenido_Actual
AS
BEGIN
    SELECT *
    FROM dbo.Contenido
    WHERE valido = 1
END;
GO

CREATE OR ALTER PROCEDURE Obtener_Directores @id_contenido VARCHAR(255)
AS
BEGIN
    SELECT d.id_director, d.apellido, d.nombre
    FROM dbo.Director_Contenido as dc
             join Director as d on dc.id_director = d.id_director
    WHERE dc.id_contenido = @id_contenido
END;
GO

CREATE OR ALTER PROCEDURE Obtener_Actores @id_contenido VARCHAR(255)
AS
BEGIN
    SELECT ac.id_actor, a.apellido, a.nombre
    FROM dbo.Actor_Contenido as ac
             join Actor as a on ac.id_actor = a.id_actor
    WHERE ac.id_contenido = @id_contenido
END;
GO

CREATE OR ALTER PROCEDURE Obtener_Generos @id_contenido VARCHAR(255)
AS
BEGIN
    SELECT gc.id_genero, g.descripcion
    FROM dbo.Genero_Contenido as gc
             join Genero as g on gc.id_genero = g.id_genero
    WHERE gc.id_contenido = @id_contenido
END;
GO

CREATE OR ALTER PROCEDURE desvincular @token VARCHAR(255)
AS
BEGIN
    UPDATE Autorizacion
    set fecha_de_baja = GETDATE()
    WHERE Autorizacion.token = @token
END;
go
