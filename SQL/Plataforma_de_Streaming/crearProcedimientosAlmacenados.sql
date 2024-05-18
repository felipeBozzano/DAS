

/* Cliente_Usuario */

CREATE OR ALTER PROCEDURE Crear_Usuario @usuario VARCHAR(255),
                                        @contrasena VARCHAR(255),
                                        @email VARCHAR(255),
                                        @nombre VARCHAR(255),
                                        @apellido VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Cliente_Usuario(usuario, contrasena, email, nombre, apellido, valido)
    VALUES (@usuario, @contrasena, @email, @nombre, @apellido, 0)
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
    SET usuario    = @usuario,
        contrasena = @contrasena,
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

CREATE OR ALTER PROCEDURE Login_Usuario @usuario VARCHAR(255),
                                        @contrasena VARCHAR(255)
AS
BEGIN
    SELECT id_cliente
    FROM dbo.Cliente_Usuario CU
    WHERE CU.usuario = @usuario
      AND CU.contrasena = @contrasena
      AND CU.valido = 1
END
go

/* Transaccion */

CREATE OR ALTER PROCEDURE Crear_Transaccion @codigo_de_transaccion VARCHAR(255),
                                            @url_de_redireccion VARCHAR(255),
                                            @tipo_de_transaccion VARCHAR(1)
AS
BEGIN
    INSERT INTO dbo.Transaccion(codigo_de_transaccion, fecha_de_alta, url_de_redireccion, tipo_de_transaccion)
    VALUES (@codigo_de_transaccion, GETDATE(), @url_de_redireccion, @tipo_de_transaccion)
END
go

CREATE OR ALTER PROCEDURE Verificar_Codigo @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    SELECT CASE
               WHEN EXISTS (SELECT 1
                            FROM dbo.Transaccion
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
    INSERT INTO dbo.Autorizacion(codigo_de_transaccion, id_cliente, token, fecha_de_alta, fecha_de_baja)
    VALUES (@codigo_de_transaccion, @id_cliente, @token, GETDATE(), NULL)
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

CREATE OR ALTER PROCEDURE Obtener_Token @id_cliente INT,
                                        @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    SELECT token
    FROM dbo.Autorizacion
    WHERE id_cliente = @id_cliente
      AND codigo_de_transaccion = @codigo_de_transaccion
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
                                       @id_partner INT
AS
BEGIN
    INSERT INTO dbo.Sesion (id_cliente, sesion, fecha_de_creacion, fecha_de_uso, id_partner)
    VALUES (@id_cliente, (SELECT LEFT(CONVERT(VARCHAR(36), NEWID()), 6)), GETDATE(), null,
            @id_partner)
END
go

CREATE OR ALTER PROCEDURE Usar_Sesion @id_cliente INT,
                                      @sesion VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Sesion
    SET fecha_de_uso = GETDATE()
    WHERE id_cliente = @id_cliente
      AND sesion = @sesion
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
                                          @url_imagen VARCHAR(255),
                                          @clasificacion SMALLINT,
                                          @reciente BIT,
                                          @destacado BIT
AS
BEGIN
    INSERT INTO dbo.Contenido(id_contenido, titulo, descripcion, url_imagen, clasificacion, reciente, destacado,
                              fecha_alta, fecha_baja)
    VALUES (@id_contenido, @titulo, @descripcion, @url_imagen, @clasificacion, @reciente, @destacado, GETDATE(),
            NULL)
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
    WHERE fecha_baja IS NULL
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

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------ FEDERAR USUARIO ------------------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

/* Verificar_Token_de_Partner */

/* Crear_Transaccion */

CREATE OR ALTER PROCEDURE Obtener_codigo_de_redireccion @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    SELECT url_de_redireccion
    FROM dbo.Transaccion
    WHERE codigo_de_transaccion = @codigo_de_transaccion
END
go

/* Obtener_Token */

CREATE OR ALTER PROCEDURE Verificar_Autorizacion @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    SELECT A.id_cliente, T.url_de_redireccion
    FROM dbo.Autorizacion A
        JOIN dbo.Transaccion T ON A.codigo_de_transaccion = T.codigo_de_transaccion
    WHERE A.codigo_de_transaccion = @codigo_de_transaccion
END
go
