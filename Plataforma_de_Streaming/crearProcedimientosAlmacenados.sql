USE Plataforma_de_Streaming;

/* Cliente_Usuario */

CREATE OR ALTER PROCEDURE Crear_Usuario @usuario VARCHAR(255),
                                        @contraseña VARCHAR(255),
                                        @email VARCHAR(255),
                                        @nombre VARCHAR(255),
                                        @apellido VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Cliente_Usuario(usuario, contraseña, email, nombre, apellido, valido)
    VALUES (@usuario, @contraseña, @email, @nombre, @apellido, 0)
END
go

CREATE OR ALTER PROCEDURE Editar_Usuario @id_cliente INT,
                                         @usuario VARCHAR(255),
                                         @contraseña VARCHAR(255),
                                         @email VARCHAR(255),
                                         @nombre VARCHAR(255),
                                         @apellido VARCHAR(255)
AS
BEGIN
    UPDATE Cliente_Usuario
    SET usuario    = @usuario,
        contraseña = @contraseña,
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

/* Transaccion */

CREATE OR ALTER PROCEDURE Crear_Transaccion @url_de_redireccion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Transaccion(codigo_de_transaccion, fecha_de_alta, url_de_redireccion)
    VALUES((SELECT LEFT(CONVERT(VARCHAR(36), NEWID()), 8)), GETDATE(), @url_de_redireccion)
END
go

/* Autorizacion */

CREATE OR ALTER PROCEDURE Crear_Autorizacion @id_cliente INT,
                                             @url_de_redireccion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Autorizacion(id_cliente, codigo_de_transaccion, estado, fecha_de_alta, token, url_de_redireccion)
    VALUES (@id_cliente, (SELECT LEFT(CONVERT(VARCHAR(36), NEWID()), 8)), 0, GETDATE(), NULL, @url_de_redireccion)
END
go

CREATE OR ALTER PROCEDURE Desactualizar @id_cliente INT,
                                                @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    DELETE
    FROM dbo.Autorizacion
    WHERE id_cliente = @id_cliente
      AND codigo_de_transaccion = @codigo_de_transaccion
END
go

CREATE OR ALTER PROCEDURE Obtener_Codigo @id_cliente INT
AS
BEGIN
    SELECT codigo_de_transaccion
    FROM dbo.Autorizacion
    WHERE id_cliente = @id_cliente
END
go

CREATE OR ALTER PROCEDURE Obtener_Token @id_cliente INT,
                                        @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    SELECT token
    FROM dbo.Autorizacion
    WHERE id_cliente = @id_cliente
      AND codigo_de_transaccion = @codigo_de_transaccion
END
go

/* Sesion */

CREATE OR ALTER PROCEDURE Crear_Sesion @id_cliente INT
AS
BEGIN
    INSERT INTO dbo.Sesion (id_cliente, sesion, fecha_de_creacion, fecha_de_expiracion, fecha_de_uso)
    VALUES (@id_cliente, (SELECT LEFT(CONVERT(VARCHAR(36), NEWID()), 6)), GETDATE(),
            (SELECT DATEADD(HOUR, 1, GETDATE())), null)
END
go

/* poner como pk, id_cliente y sesion. Buscar por esa PK cuando usamos la sesion */

CREATE OR ALTER PROCEDURE Usar_Sesion @id_cliente INT,
                                      @sesion VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Sesion
    SET fecha_de_uso = GETDATE()
    WHERE id_cliente = @id_cliente
      AND fecha_de_creacion = (SELECT MAX(fecha_de_creacion)
                               FROM dbo.Sesion
                               WHERE fecha_de_uso IS NULL
                                 AND id_cliente = @id_cliente)
      AND fecha_de_uso IS NULL
      AND fecha_de_expiracion > GETDATE()
END
go

CREATE OR ALTER PROCEDURE Eliminar_Sesion

/* Partner */

    CREATE OR ALTER PROCEDURE Crear_Partner
        CREATE OR ALTER PROCEDURE Editar_Partner
            CREATE OR ALTER PROCEDURE Eliminar_Partner

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

CREATE OR ALTER PROCEDURE Crear_Contenido @titulo VARCHAR(255),
                                          @descripcion VARCHAR(255),
                                          @url_imagen VARCHAR(255),
                                          @clasificacion INT
AS
BEGIN
    INSERT INTO dbo.Contenido(titulo, descripcion, url_imagen, clasificacion, mas_visto)
    VALUES (@titulo, @descripcion, @url_imagen, @clasificacion, 0)
END
go

CREATE OR ALTER PROCEDURE Modificar_Contenido @id_contenido INT,
                                              @titulo VARCHAR(255),
                                              @descripcion VARCHAR(255),
                                              @url_imagen VARCHAR(255),
                                              @clasificacion INT,
                                              @mas_visto BIT
AS
BEGIN
    UPDATE dbo.Contenido
    SET titulo        = @titulo,
        descripcion   = @descripcion,
        url_imagen    = @url_imagen,
        clasificacion = @clasificacion,
        mas_visto     = @mas_visto
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

/* Clasificacion */

CREATE OR ALTER PROCEDURE Crear_Clasificacion @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Clasificacion(descripcion)
    VALUES (@descripcion)
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
