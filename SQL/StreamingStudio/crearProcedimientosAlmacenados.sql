USE StreamingStudio;

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

/* Plataforma_de_Streaming */

CREATE OR ALTER PROCEDURE Añadir_Plataforma_de_Streaming @nombre_de_fantasia VARCHAR(255),
                                                         @razón_social VARCHAR(255),
                                                         @url_imagen VARCHAR(255),
                                                         @token_de_servicio VARCHAR(255),
                                                         @url_api VARCHAR(255),
                                                         @valido BIT
AS
BEGIN
    INSERT INTO dbo.Plataforma_de_Streaming(nombre_de_fantasia, razón_social, url_imagen, token_de_servicio,
                                            url_api, valido)
    VALUES (@nombre_de_fantasia, @razón_social, @url_imagen, @token_de_servicio, @url_api, @valido)
END
go

CREATE OR ALTER PROCEDURE Editar_Plataforma_de_Streaming @id_plataforma INT,
                                                         @nombre_de_fantasia VARCHAR(255),
                                                         @razón_social VARCHAR(255),
                                                         @url_imagen VARCHAR(255),
                                                         @token_de_servicio VARCHAR(255),
                                                         @url_api VARCHAR(255),
                                                         @valido BIT
AS
BEGIN
    UPDATE Plataforma_de_Streaming
    SET nombre_de_fantasia = @nombre_de_fantasia,
        razón_social       = @razón_social,
        url_imagen         = @url_imagen,
        token_de_servicio  = @token_de_servicio,
        url_api            = @url_api,
        valido             = @valido
    WHERE id_plataforma = @id_plataforma
END
go

CREATE OR ALTER PROCEDURE Eliminar_Plataforma_de_Streaming @id_plataforma INT
AS
BEGIN
    DELETE
    FROM Plataforma_de_Streaming
    WHERE id_plataforma = @id_plataforma
END
go

CREATE OR ALTER PROCEDURE Obtener_Datos_de_Sesion @id_plataforma INT
AS
BEGIN
    SELECT url_api, token_de_servicio
    FROM dbo.Plataforma_de_Streaming
    WHERE id_plataforma = @id_plataforma
END
go

CREATE OR ALTER PROCEDURE Dar_de_Baja_Plataforma_de_Streaming @id_plataforma INT
AS
BEGIN
    UPDATE Plataforma_de_Streaming
    SET valido = 0
    WHERE id_plataforma = @id_plataforma
END
go

CREATE OR ALTER PROCEDURE Reactivar_Plataforma_de_Streaming @id_plataforma INT
AS
BEGIN
    UPDATE dbo.Plataforma_de_Streaming
    SET valido = 1
    WHERE id_plataforma = @id_plataforma
END
go

/* Transaccion */

CREATE OR ALTER PROCEDURE Comenzar_Federacion @id_plataforma INT,
                                              @id_cliente INT,
                                              @codigo_de_transaccion VARCHAR(255),
                                              @url_login_registro_plataforma VARCHAR(255),
                                              @url_redireccion_propia VARCHAR(255),
                                              @tipo_usuario SMALLINT
AS
BEGIN
    DECLARE @facturada BIT;
    SET @facturada = (SELECT IIF(EXISTS (SELECT 1
                                         FROM dbo.Transaccion
                                         WHERE id_plataforma = @id_plataforma
                                           and id_cliente = @id_cliente
                                           and facturada = 1), 1, 0))
    INSERT INTO dbo.Transaccion(id_plataforma, id_cliente, fecha_alta, codigo_de_transaccion,
                                url_login_registro_plataforma, url_redireccion_propia, tipo_usuario, token, fecha_baja,
                                facturada)
    VALUES (@id_plataforma, @id_cliente, GETDATE(), @codigo_de_transaccion, @url_login_registro_plataforma,
            @url_redireccion_propia, @tipo_usuario, NULL, NULL, @facturada)
END
go

CREATE OR ALTER PROCEDURE Interrumpir_Federacion @id_plataforma INT,
                                                 @id_cliente INT
AS
BEGIN
    DECLARE @max_fecha_alta DATETIME;
    SET @max_fecha_alta = (SELECT MAX(fecha_alta)
                           FROM dbo.Transaccion
                           WHERE id_plataforma = @id_plataforma
                             AND id_cliente = @id_cliente)
    UPDATE Transaccion
    SET fecha_baja = CURRENT_TIMESTAMP
    WHERE id_plataforma = @id_plataforma
      and id_cliente = @id_cliente
      and fecha_alta = @max_fecha_alta
END
go

CREATE OR ALTER PROCEDURE Finalizar_Federacion @id_plataforma INT,
                                               @id_cliente INT,
                                               @token VARCHAR(255)
AS
BEGIN
    DECLARE @max_fecha_alta DATETIME;
    SET @max_fecha_alta = (SELECT MAX(fecha_alta)
                           FROM dbo.Transaccion
                           WHERE id_plataforma = @id_plataforma
                             AND id_cliente = @id_cliente)
    UPDATE Transaccion
    SET token = @token
    WHERE id_plataforma = @id_plataforma
      and id_cliente = @id_cliente
      and fecha_alta = @max_fecha_alta
END
go

/* Federacion */

CREATE OR ALTER PROCEDURE Desvincular_Federacion @id_plataforma INT,
                                                 @id_cliente INT
AS
BEGIN
    DELETE
    FROM Federacion
    WHERE id_plataforma = @id_plataforma
      and id_cliente = @id_cliente
END
go

/* Banner */

CREATE OR ALTER PROCEDURE Crear_Banner @tamaño_de_banner VARCHAR(255),
                                       @exclusividad BIT,
                                       @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Banner(tamaño_de_banner, exclusividad, descripcion)
    VALUES (@tamaño_de_banner, @exclusividad, @descripcion)
END
go

CREATE OR ALTER PROCEDURE Editar_Banner @id_banner INT,
                                        @tamaño_de_banner VARCHAR(255),
                                        @exclusividad BIT,
                                        @descripcion VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Banner
    SET tamaño_de_banner = @tamaño_de_banner,
        exclusividad     = @exclusividad,
        descripcion      = @descripcion
    WHERE id_banner = @id_banner
END
go

CREATE OR ALTER PROCEDURE Eliminar_Banner @id_banner INT
AS
BEGIN
    DELETE
    FROM dbo.Banner
    WHERE id_banner = @id_banner
END
go

/* Tipo_Banner */

CREATE OR ALTER PROCEDURE Crear_Tipo_Banner @costo FLOAT
AS
BEGIN
    INSERT INTO Tipo_Banner (costo, fecha_alta, fecha_baja)
    VALUES (@costo, GETDATE(), NULL);
END
go

CREATE OR ALTER PROCEDURE Editar_Tipo_Banner @id_tipo_banner SMALLINT,
                                             @costo FLOAT
AS
BEGIN
    UPDATE Tipo_Banner
    SET costo = @costo
    WHERE id_tipo_banner = @id_tipo_banner;
END
go

CREATE OR ALTER PROCEDURE Eliminar_Tipo_Banner @id_tipo_banner SMALLINT
AS
BEGIN
    DELETE
    FROM Tipo_Banner
    WHERE id_tipo_banner = @id_tipo_banner;
END
go

CREATE OR ALTER PROCEDURE Dar_de_Baja_Tipo_Banner @id_tipo_banner SMALLINT
AS
BEGIN
    UPDATE Tipo_Banner
    SET fecha_baja = GETDATE()
    WHERE id_tipo_banner = @id_tipo_banner;
END
go

/* Costo_Banner */

CREATE OR ALTER PROCEDURE Crear_Costo_Banner @id_tipo_banner SMALLINT,
                                             @id_banner SMALLINT
AS
BEGIN
    INSERT INTO Costo_Banner (id_tipo_banner, id_banner)
    VALUES (@id_tipo_banner, @id_banner);
END
go

CREATE OR ALTER PROCEDURE Eliminar_Costo_Banner @id_tipo_banner SMALLINT,
                                                @id_banner SMALLINT
AS
BEGIN
    DELETE
    FROM Costo_Banner
    WHERE id_tipo_banner = @id_tipo_banner
      AND id_banner = @id_banner;
END
go

/* Publicista */

CREATE OR ALTER PROCEDURE Registrar_Publicista @nombre_de_fantasia VARCHAR(255),
                                               @razón_social VARCHAR(255),
                                               @email VARCHAR(255),
                                               @contraseña VARCHAR(255),
                                               @token_de_servicio VARCHAR(255),
                                               @url_api VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Publicista(nombre_de_fantasia, razón_social, email, contrasena, token_de_servicio, url_api)
    VALUES (@nombre_de_fantasia, @razón_social, @email, @contraseña, @token_de_servicio, @url_api)
END
go

CREATE OR ALTER PROCEDURE Editar_Publicista @id_publicista INT,
                                            @nombre_de_fantasia VARCHAR(255),
                                            @razón_social VARCHAR(255),
                                            @email VARCHAR(255),
                                            @contraseña VARCHAR(255),
                                            @token_de_servicio VARCHAR(255),
                                            @url_api VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Publicista
    SET nombre_de_fantasia = @nombre_de_fantasia,
        razón_social       = @razón_social,
        email              = @email,
        contrasena         = @contraseña,
        token_de_servicio  = @token_de_servicio,
        url_api            = @url_api
    WHERE id_publicista = @id_publicista
END
go

CREATE OR ALTER PROCEDURE Eliminar_Publicista @id_publicista INT
AS
BEGIN
    DELETE
    FROM dbo.Publicista
    WHERE id_publicista = @id_publicista
END
go

/* Publicidad */

CREATE OR ALTER PROCEDURE Registrar_Publicidad @id_publicista VARCHAR(255),
                                               @id_banner VARCHAR(255),
                                               @codigo_publicidad VARCHAR(255),
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

/* Estado_Factura */

CREATE OR ALTER PROCEDURE Crear_Estado_Factura @id_estado SMALLINT,
                                               @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Estado_Factura(id_estado, descripcion)
    VALUES (@id_estado, @descripcion)
END
go

CREATE OR ALTER PROCEDURE Modificar_Estado_Factura @id_estado SMALLINT,
                                                   @descripcion VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Estado_Factura
    SET descripcion = @descripcion
    WHERE id_estado = @id_estado
END
go

CREATE OR ALTER PROCEDURE Eliminar_Estado_Factura @id_estado INT
AS
BEGIN
    DELETE
    FROM dbo.Estado_Factura
    WHERE id_estado = @id_estado
END
go

/* Factura */

CREATE OR ALTER PROCEDURE Crear_Factura_Plataforma @id_plataforma INT,
                                                   @id_factura INT OUTPUT
AS
BEGIN
    INSERT INTO dbo.Factura(total, fecha, estado, id_publicista, id_plataforma)
    VALUES (0, (SELECT CONVERT(date, CURRENT_TIMESTAMP)), 0, NULL, @id_plataforma)

    SET @id_factura = SCOPE_IDENTITY();

    SELECT @id_factura AS id_factura;
END
go

CREATE OR ALTER PROCEDURE Crear_Factura_Publicista @id_publicista INT,
                                                   @id_factura INT OUTPUT
AS
BEGIN
    INSERT INTO dbo.Factura(total, fecha, estado, id_publicista, id_plataforma)
    VALUES (0, (SELECT CONVERT(date, CURRENT_TIMESTAMP)), 0, @id_publicista, NULL)

    SET @id_factura = SCOPE_IDENTITY();

    SELECT @id_factura AS id_factura;
END
go

CREATE OR ALTER PROCEDURE Finalizar_Factura @id_factura INT,
                                            @total VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Factura
    SET total  = @total,
        estado = 1
    WHERE id_factura = @id_factura
END
go

CREATE OR ALTER PROCEDURE Enviar_Factura @id_factura INT
AS
BEGIN
    UPDATE dbo.Factura
    SET estado = 2
    WHERE id_factura = @id_factura
END
go

CREATE OR ALTER PROCEDURE Cancelar_Factura @id_factura INT
AS
BEGIN
    UPDATE dbo.Factura
    SET estado = -1
    WHERE id_factura = @id_factura
END
go

/* Detalle_Factura */

CREATE OR ALTER PROCEDURE Crear_Detalle_Factura @id_factura INT,
                                                @precio_unitario FLOAT,
                                                @cantidad INT,
                                                @subtotal FLOAT,
                                                @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Detalle_Factura(id_factura, precio_unitario, cantidad, subtotal, descripcion)
    VALUES (@id_factura, @precio_unitario, @cantidad, @subtotal, @descripcion)
END
go

CREATE OR ALTER PROCEDURE Modificar_Detalle_Factura @id_factura INT,
                                                    @id_detalle INT,
                                                    @precio_unitario FLOAT,
                                                    @cantidad INT,
                                                    @subtotal FLOAT,
                                                    @descripcion VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Detalle_Factura
    SET precio_unitario = @precio_unitario,
        cantidad        = @cantidad,
        subtotal        = @subtotal,
        descripcion     = @descripcion
    WHERE id_factura = @id_factura
      AND id_detalle = @id_detalle
END
go

CREATE OR ALTER PROCEDURE Eliminar_Detalle_Factura @id_factura INT,
                                                   @id_detalle INT
AS
BEGIN
    DELETE
    FROM dbo.Detalle_Factura
    WHERE id_factura = @id_factura
      AND id_detalle = @id_detalle
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

/* Preferencia */

CREATE OR ALTER PROCEDURE Crear_Preferencia @id_genero INT,
                                            @id_cliente INT
AS
BEGIN
    INSERT INTO dbo.Preferencia(id_genero, id_cliente)
    VALUES (@id_genero, @id_cliente)
END
go

CREATE OR ALTER PROCEDURE Eliminar_Preferencia @id_genero INT,
                                               @id_cliente INT
AS
BEGIN
    DELETE
    FROM dbo.Preferencia
    WHERE id_genero = @id_genero
      AND id_cliente = @id_cliente
END
go

/* Contenido */

CREATE OR ALTER PROCEDURE Crear_Contenido @id_contenido INT,
                                          @titulo VARCHAR(255),
                                          @descripcion VARCHAR(255),
                                          @url_imagen VARCHAR(255),
                                          @clasificacion INT
AS
BEGIN
    INSERT INTO dbo.Contenido(id_contenido, titulo, descripcion, url_imagen, clasificacion, mas_visto)
    VALUES (@id_contenido, @titulo, @descripcion, @url_imagen, @clasificacion, 0)
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

/* Catalogo */

CREATE OR ALTER PROCEDURE Agregar_Item_al_Catalogo @id_contenido INT,
                                                   @id_plataforma INT,
                                                   @reciente BIT,
                                                   @destacado BIT,
                                                   @id_en_plataforma VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Catalogo(id_contenido, id_plataforma, reciente, destacado, id_en_plataforma, fecha_de_alta,
                             fecha_de_baja)
    VALUES (@id_contenido, @id_plataforma, @reciente, @destacado, @id_en_plataforma, GETDATE(), NULL)
END
go

/* Clic */

CREATE OR ALTER PROCEDURE Registrar_Clic @id_cliente INT,
                                         @id_publicidad INT,
                                         @id_plataforma INT,
                                         @id_contenido INT
AS
BEGIN
    INSERT INTO dbo.Clic(id_cliente, id_publicidad, id_plataforma, id_contenido, fecha)
    VALUES (@id_cliente, @id_publicidad, @id_plataforma, @id_contenido, CURRENT_TIMESTAMP)
END
go

CREATE OR ALTER PROCEDURE Obtener_Contenido_mas_Visto
AS
BEGIN
    SELECT TOP 100 id_contenido
    FROM dbo.Clic
    WHERE id_plataforma IS NOT NULL
      AND id_contenido IS NOT NULL
      AND fecha BETWEEN DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) - 1, 0)
        AND DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0)
    GROUP BY id_contenido
    ORDER BY count(id_clic)
END
go

/* Administrador */

CREATE OR ALTER PROCEDURE Crear_Administrador @usuario VARCHAR(255),
                                              @contraseña VARCHAR(255),
                                              @email VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Administrador(usuario, contraseña, email)
    VALUES (@usuario, @contraseña, @email)
END
go

CREATE OR ALTER PROCEDURE Modificar_Administrador @id_administrador INT,
                                                  @usuario VARCHAR(255),
                                                  @contraseña VARCHAR(255),
                                                  @email VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Administrador
    SET usuario    = @usuario,
        contraseña = @contraseña,
        email      = @email
    WHERE id_administrador = @id_administrador
END
go

CREATE OR ALTER PROCEDURE Eliminar_Administrador @id_administrador INT
AS
BEGIN
    DELETE
    FROM dbo.Administrador
    WHERE id_administrador = @id_administrador
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

/* Estado_Reporte */

CREATE OR ALTER PROCEDURE Crear_Estado_Reporte @id_estado SMALLINT,
                                               @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Estado_Reporte(id_estado, descripcion)
    VALUES (@id_estado, @descripcion)
END
go

CREATE OR ALTER PROCEDURE Modificar_Estado_Reporte @id_estado SMALLINT,
                                                   @descripcion VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Estado_Reporte
    SET descripcion = @descripcion
    WHERE id_estado = @id_estado
END
go

CREATE OR ALTER PROCEDURE Eliminar_Estado_Reporte @id_estado INT
AS
BEGIN
    DELETE
    FROM dbo.Estado_Reporte
    WHERE id_estado = @id_estado
END
go

/* Reporte */

CREATE OR ALTER PROCEDURE Crear_Reporte_Publicista @id_publicista INT,
                                                   @id_reporte INT OUTPUT
AS
BEGIN
    INSERT INTO dbo.Reporte(total, fecha, estado, id_publicista, id_plataforma)
    VALUES (0, (SELECT CONVERT(date, CURRENT_TIMESTAMP)), 0, @id_publicista, NULL);

    SET @id_reporte = SCOPE_IDENTITY();

    SELECT @id_reporte AS id_reporte;
END
go

CREATE OR ALTER PROCEDURE Crear_Reporte_Plataforma @id_plataforma SMALLINT,
                                                   @id_reporte INT OUTPUT
AS
BEGIN
    INSERT INTO dbo.Reporte(total, fecha, estado, id_publicista, id_plataforma)
    VALUES (0, (SELECT CONVERT(date, CURRENT_TIMESTAMP)), 0, NULL, @id_plataforma);

    SET @id_reporte = SCOPE_IDENTITY();

    SELECT @id_reporte AS id_reporte;
END
go

CREATE OR ALTER PROCEDURE Finalizar_Reporte @id_reporte INT,
                                            @total INT
AS
BEGIN
    UPDATE dbo.Reporte
    SET total  = @total,
        estado = 1
    WHERE id_reporte = @id_reporte
END
go

CREATE OR ALTER PROCEDURE Enviar_Reporte @id_reporte INT
AS
BEGIN
    UPDATE dbo.Reporte
    SET estado = 2
    WHERE id_reporte = @id_reporte
END
go

CREATE OR ALTER PROCEDURE Cancelar_Reporte @id_reporte INT
AS
BEGIN
    UPDATE dbo.Reporte
    SET estado = -1
    WHERE id_reporte = @id_reporte
END
go

/* Detalle_Reporte */

CREATE OR ALTER PROCEDURE Crear_Detalle_Reporte @id_reporte INT,
                                                @descripcion VARCHAR(255),
                                                @cantidad_de_clics INT
AS
BEGIN
    INSERT INTO dbo.Detalle_Reporte(id_reporte, descripcion, cantidad_de_clics)
    VALUES (@id_reporte, @descripcion, @cantidad_de_clics)
END
go

CREATE OR ALTER PROCEDURE Modificar_Detalle_Reporte @id_reporte INT,
                                                    @id_detalle INT,
                                                    @descripcion VARCHAR(255),
                                                    @cantidad_de_clics INT
AS
BEGIN
    UPDATE dbo.Detalle_Reporte
    SET cantidad_de_clics = @cantidad_de_clics,
        descripcion       = @descripcion
    WHERE id_reporte = @id_reporte
      AND id_detalle = @id_detalle
END
go

CREATE OR ALTER PROCEDURE Eliminar_Detalle_Reporte @id_reporte INT,
                                                   @id_detalle INT
AS
BEGIN
    DELETE
    FROM dbo.Detalle_Reporte
    WHERE id_reporte = @id_reporte
      AND id_detalle = @id_detalle
END
go

/* Tipo_de_Fee */

CREATE OR ALTER PROCEDURE Crear_Tipo_de_Fee @tipo_de_fee VARCHAR(1),
                                            @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO Tipo_Fee (tipo_de_fee, descripcion)
    VALUES (@tipo_de_fee, @descripcion);
END
go

CREATE OR ALTER PROCEDURE Editar_Tipo_de_Fee @id_tipo_de_fee SMALLINT,
                                             @tipo_de_fee VARCHAR(1),
                                             @descripcion VARCHAR(255)
AS
BEGIN
    UPDATE Tipo_Fee
    SET tipo_de_fee = @tipo_de_fee,
        descripcion = @descripcion
    WHERE id_tipo_de_fee = @id_tipo_de_fee;
END
go

CREATE OR ALTER PROCEDURE Eliminar_Tipo_de_Fee @id_tipo_de_fee SMALLINT
AS
BEGIN
    DELETE
    FROM Tipo_Fee
    WHERE id_tipo_de_fee = @id_tipo_de_fee;
END
go

/* Fee */

CREATE OR ALTER PROCEDURE Crear_Fee @monto FLOAT,
                                    @tipo_de_fee SMALLINT
AS
BEGIN
    INSERT INTO Fee (monto, fecha_alta, fecha_baja, tipo_de_fee)
    VALUES (@monto, GETDATE(), NULL, @tipo_de_fee);
END
go

CREATE OR ALTER PROCEDURE Editar_Fee @id_fee SMALLINT,
                                     @monto FLOAT,
                                     @tipo_de_fee SMALLINT
AS
BEGIN
    UPDATE Fee
    SET monto       = @monto,
        tipo_de_fee = @tipo_de_fee
    WHERE id_fee = @id_fee;
END
go

CREATE OR ALTER PROCEDURE Eliminar_Fee @id_fee SMALLINT
AS
BEGIN
    DELETE
    FROM Fee
    WHERE id_fee = @id_fee;
END
go

CREATE OR ALTER PROCEDURE Dar_de_Baja_Fee @id_fee SMALLINT
AS
BEGIN
    UPDATE Fee
    SET fecha_baja = GETDATE()
    WHERE id_fee = @id_fee;
END
go

/* Fee_Plataforma */

CREATE OR ALTER PROCEDURE Crear_Fee_Plataforma @id_plataforma SMALLINT,
                                               @id_fee SMALLINT
AS
BEGIN
    INSERT INTO Fee_Plataforma (id_plataforma, id_fee)
    VALUES (@id_plataforma, @id_fee);
END
go

CREATE OR ALTER PROCEDURE Eliminar_Fee_Plataforma @id_plataforma SMALLINT,
                                                  @id_fee SMALLINT
AS
BEGIN
    DELETE
    FROM Fee_Plataforma
    WHERE id_plataforma = @id_plataforma
      AND id_fee = @id_fee;
END
go


/* ------------------------------------------------------------------------------------------------------------------ */
/* ----------------------------------- OBTENER Y ENVIAR REPORTES DE ESTADISTICAS ------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Obtener_Estadisticas_para_Publicistas
AS
BEGIN
    SELECT P.id_publicista, P.id_publicidad, P.codigo_publicidad, count(c.id_clic) AS cantidad_de_clics
    FROM dbo.Publicidad P
             JOIN dbo.Clic C ON P.id_publicidad = C.id_publicidad
    WHERE C.id_publicidad IS NOT NULL
      AND fecha BETWEEN DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) - 1, 0)
        AND DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0)
    GROUP BY P.id_publicista, P.id_publicidad, P.codigo_publicidad
END
go

/* POR CADA PUBLICISTA REPETIR EL SIGUIENTE CICLO*/

/* Crear_Reporte_Publicista */
/* Crear_Detalle_Reporte */
/* Finalizar_Reporte */

CREATE OR ALTER PROCEDURE Obtener_Datos_de_Publicista @id_publicista INT
AS
BEGIN
    SELECT nombre_de_fantasia, razón_social, token_de_servicio, url_api
    FROM dbo.Publicista
    WHERE id_publicista = @id_publicista
END
go

/* PEGARLE A LA API DEL PUBLICISTA PARA CARGAR EL REPORTE */
/* Enviar_Reporte */

/* FIN DE CICLO DE PUBLICISTAS */

CREATE OR ALTER PROCEDURE Obtener_Estadisticas_para_Plataformas
AS
BEGIN
    SELECT Cc.id_plataforma, Cc.id_contenido, Co.id_en_plataforma, COUNT(id_clic) AS cantidad_de_clics
    FROM dbo.Clic Cc
             JOIN dbo.Catalogo Co ON Cc.id_contenido = Co.id_contenido AND Cc.id_plataforma = Co.id_plataforma
    WHERE Cc.id_contenido IS NOT NULL
      AND Cc.id_plataforma IS NOT NULL
      AND Co.fecha_de_baja IS NULL
      AND Cc.fecha BETWEEN DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) - 1, 0)
        AND DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0)
    GROUP BY Cc.id_plataforma, Cc.id_contenido, Co.id_en_plataforma
END
go

/* POR CADA PLATAFORMA DE STREAMING REPETIR EL SIGUIENTE CICLO*/

/* Crear_Reporte_Plataforma */
/* Crear_Detalle_Reporte */
/* Finalizar_Reporte */

CREATE OR ALTER PROCEDURE Obtener_Datos_de_Plataforma @id_plataforma INT
AS
BEGIN
    SELECT nombre_de_fantasia, razón_social, token_de_servicio, url_api
    FROM dbo.Plataforma_de_Streaming
    WHERE id_plataforma = @id_plataforma
END
go

/* PEGARLE A LA API DE PLATAFORMA PARA CARGAR EL REPORTE */
/* Enviar_Reporte */

/* FIN DE CICLO DE PLATAFORMAS DE STREAMING */

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------ OBTENER Y ENVIAR FACTURAS --------------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Obtener_Datos_de_Publicidades
AS
BEGIN
    DECLARE @PrimerDiaMesAnterior AS DATE = DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) - 1, 0)
    DECLARE @UltimoDiaMesAnterior AS DATE = EOMONTH(DATEADD(MONTH, -1, GETDATE()))
    SELECT id_publicidad,
           id_publicista,
           id_banner,
           codigo_publicidad,
           CASE
               WHEN fecha_de_alta < @PrimerDiaMesAnterior
                   AND fecha_de_baja < @UltimoDiaMesAnterior
                   THEN DATEDIFF(DAY, @PrimerDiaMesAnterior, fecha_de_baja)
               WHEN fecha_de_alta < @PrimerDiaMesAnterior
                   AND fecha_de_baja > @UltimoDiaMesAnterior
                   THEN DATEDIFF(DAY, @PrimerDiaMesAnterior, @UltimoDiaMesAnterior)
               WHEN fecha_de_alta > @PrimerDiaMesAnterior
                   AND fecha_de_baja < @UltimoDiaMesAnterior
                   THEN DATEDIFF(DAY, fecha_de_alta, fecha_de_baja)
               WHEN fecha_de_alta > @PrimerDiaMesAnterior
                   AND fecha_de_baja > @UltimoDiaMesAnterior
                   THEN DATEDIFF(DAY, fecha_de_alta, @UltimoDiaMesAnterior)
               END                                                                                     AS cantidad_de_dias,
           IIF(fecha_de_alta < @PrimerDiaMesAnterior, @PrimerDiaMesAnterior, fecha_de_alta)            AS fecha_inicio,
           IIF(Publicidad.fecha_de_baja > @UltimoDiaMesAnterior, @UltimoDiaMesAnterior, fecha_de_baja) AS fecha_final
    FROM dbo.Publicidad
    WHERE fecha_de_alta <= @UltimoDiaMesAnterior
      AND fecha_de_baja >= @PrimerDiaMesAnterior
END
go

/* Crear_Factura_Publicista  agrupar por id_publicista y crear una factura */

CREATE OR ALTER PROCEDURE Obtener_Costo_de_Banner @id_banner INT
AS
BEGIN
    SELECT tp.costo
    FROM dbo.Costo_Banner cb
             JOIN dbo.Tipo_Banner tp ON tp.id_tipo_banner = cb.id_tipo_banner
    WHERE id_banner = @id_banner
END
go

/* Crear_Detalle_Factura */
/* Finalizar_Factura */
/* Obtener_Datos_de_Publicista */
/* PEGARLE A LA API DEL PUBLICISTA PARA CARGAR LA FACTURA */
/* Enviar_Factura */



CREATE OR ALTER PROCEDURE Obtener_Datos_de_Federaciones
AS
BEGIN
    SELECT id_plataforma, tipo_usuario, count(id_cliente) AS cantidad_de_federaciones
    FROM dbo.Federacion
    WHERE facturada = 0
    GROUP BY id_plataforma, tipo_usuario
END
go

/* Crear_Factura_Plataforma registro =  cobro  |  login = pago */

CREATE OR ALTER PROCEDURE Obtener_Fees_de_Plataforma @id_plataforma INT
AS
BEGIN
    SELECT f.monto, tp.tipo_de_fee
    FROM dbo.Fee_Plataforma fp
             JOIN dbo.Fee f ON fp.id_fee = f.id_fee
             JOIN dbo.Tipo_Fee tp ON f.tipo_de_fee = tp.id_tipo_de_fee
    WHERE fp.id_plataforma = @id_plataforma
      AND f.fecha_baja IS NULL
END
go

/* Crear_Detalle_Factura */
/* Finalizar_Factura */
/* Obtener_Datos_de_Plataforma */
/* PEGARLE A LA API DE LA PLATAFORMA PARA CARGAR LA FACTURA */
/* Enviar_Factura */

CREATE OR ALTER PROCEDURE Facturar_Federacion @id_plataforma INT
AS
BEGIN
    UPDATE Federacion
    SET facturada = 1
    WHERE facturada = 0
      AND id_plataforma = @id_plataforma
END
go

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------ FEDERAR CLIENTE A PLATAFORMA ------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Buscar_Federacion @id_plataforma INT,
                                            @id_cliente INT
AS
BEGIN
    SELECT IIF(COUNT(*) > 0, 1, 0) AS federacion
    FROM dbo.Federacion
    WHERE id_plataforma = @id_plataforma
      AND id_cliente = @id_cliente
END
go


-- DECLARE @id_plataforma INT = 1; -- Replace with the desired id_plataforma value
-- DECLARE @id_cliente INT = 1; -- Replace with the desired id_cliente value

-- Execute the stored procedure
-- EXEC Buscar_Federacion @id_plataforma, @id_cliente;

/* SI EXISTE LA FEDERACION, TERMINAR EL FLUJO */

CREATE OR ALTER PROCEDURE Verificar_Federacion_en_Curso @id_plataforma INT,
                                                        @id_cliente INT
AS
BEGIN
    DECLARE @max_fecha_alta DATETIME;
    SET @max_fecha_alta = (SELECT MAX(fecha_alta)
                           FROM dbo.Transaccion
                           WHERE id_plataforma = @id_plataforma
                             AND id_cliente = @id_cliente)
    SELECT IIF(COUNT(*) > 0, 1, 0) AS existe_federacion
    FROM dbo.Transaccion
    WHERE id_plataforma = @id_plataforma
      AND id_cliente = @id_cliente
      AND fecha_baja IS NULL
      AND fecha_alta = @max_fecha_alta
END
go

/* SI HAY UNA FEDERACION EN CURSO, IR DIRECTAMENTE AL PUNTO 2 */

CREATE OR ALTER PROCEDURE Obtener_Token_de_Servicio_de_Plataforma @id_plataforma INT
AS
BEGIN
    SELECT token_de_servicio
    FROM dbo.Plataforma_de_Streaming
    WHERE id_plataforma = @id_plataforma
END
go

/* PEGARLE A LA API DE LA PLATAFORMA PARA OBTENER LA URL DE LOGIN Y EL CÓDIGO DE TRANSACCIÓN. */
/* Comenzar_Federacion */
/* PUNTO 2 -- PEGARLE A LA API PARA CONSULTAR EL TOKEN DEL USUARIO */
/* Finalizar_Federacion */

/* ------------------------------------------------------------------------------------------------------------------ */
/* ---------------------------------------- TERMINAR FEDERACIONES PENDIENTES ---------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Consultar_Federaciones_Pendientes
AS
BEGIN
    SELECT id_plataforma, id_cliente, codigo_de_transaccion, tipo_usuario
    FROM dbo.Transaccion
    WHERE token IS NULL
      AND fecha_baja IS NULL
END
go

/* POR CADA FEDERACION PENDIENTE */

/* Obtener_Token_de_Servicio_de_Plataforma */
/* PEGARLE A LA API DE LA PLATAFORMA PARA OBTENER LA URL DE LOGIN Y EL CÓDIGO DE TRANSACCIÓN. */
/* Finalizar_Federacion */

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------ OBTENER Y ACTUALIZAR CATALOGO ----------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Obtener_Catalogo_Actual
AS
BEGIN
    SELECT id_plataforma, id_contenido, reciente, destacado, id_en_plataforma, fecha_de_alta, fecha_de_baja
    FROM dbo.Catalogo
END
go

CREATE OR ALTER PROCEDURE Obtener_Plataformas_de_Streaming_Activas
AS
BEGIN
    SELECT id_plataforma, valido
    FROM dbo.Plataforma_de_Streaming
    WHERE valido = 1
END
go

/* POR CADA PLATAFORMA DE STREAMING VALIDA */

/* Obtener_Token_de_Servicio_de_Plataforma */
/* PEGARLE A LA API PARA CONSEGUIR EL CATÁLOGO DE LAS PLATAFORMAS */
/* POR PLATAFORMA, CONVERTIR AMBOS CONTENIDOS EN HASHSET Y VER CUALES SON LAS PELÍCULAS QUE ESTAN EN STREAMING STUDIO
   PERO NO EN LA PLATAFORMA */

CREATE OR ALTER PROCEDURE Dar_de_Baja_Item_en_Catalogo @id_contenido INT,
                                                       @id_plataforma INT
AS
BEGIN
    UPDATE dbo.Catalogo
    SET fecha_de_baja = CURRENT_TIMESTAMP
    WHERE id_contenido = @id_contenido
      AND id_plataforma = @id_plataforma
END
go

/* VER CUALES SON LAS PELÍCULAS QUE ESTAN EN LA PLATAFORMA DE STREAMING PERO NO EN STREAMING STUDIO*/
/* VERIFICAR QUE CONTENIDO YA ESTA CARGADO EN EL CATALOGO */

CREATE OR ALTER PROCEDURE Activar_Item_en_Catalogo @id_contenido INT,
                                                   @id_plataforma INT
AS
BEGIN
    UPDATE dbo.Catalogo
    SET fecha_de_alta = CURRENT_TIMESTAMP
    WHERE id_contenido = @id_contenido
      AND id_plataforma = @id_plataforma
END
go

/* CON EL CONTENIDO RESTANTE */

CREATE OR ALTER PROCEDURE Buscar_Contenido @id_contenido INT
AS
BEGIN
    SELECT IIF(COUNT(*) > 0, 1, 0) as contenido
    FROM dbo.Contenido Co
    WHERE Co.id_contenido = @id_contenido
END
go

/* Crear_Contenido */
/* Agregar_Item_al_Catalogo */

/* VER CUALES SON LAS PELÍCULAS QUE ESTAN EN LA PLATAFORMA DE STREAMING Y EN STREAMING STUDIO*/

CREATE OR ALTER PROCEDURE Actualizar_Catalogo @id_contenido INT,
                                              @id_plataforma INT,
                                              @reciente BIT,
                                              @destacado BIT
AS
BEGIN
    UPDATE dbo.Catalogo
    SET reciente  = @reciente,
        destacado = @destacado
    WHERE id_contenido = @id_contenido
      AND id_plataforma = @id_plataforma
END
go

/* ------------------------------------------------------------------------------------------------------------------ */
/* -------------------------------------- OBTENER DATOS DE PUBLICIDADES --------------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

/* POR CADA PUBLICISTA */
/* Obtener_Datos_de_Publicista */
/* PEGARLE A LA API PARA CONSEGUIR LAS PUBLICIDADES DE LOS PUBLICISTAS */

CREATE OR ALTER PROCEDURE Obtener_Datos_de_Publicidades_Activas
AS
BEGIN
    SELECT id_publicista,
           id_publicidad,
           codigo_publicidad,
           url_de_imagen,
           url_de_publicidad
    FROM dbo.Publicidad
    WHERE fecha_de_baja >= CONVERT(DATE, GETDATE())
END
go

/* CONVERTIR AMBOS RESULTADOS EN HASHSET Y VER CUALES SON LAS PUBLICIDADES QUE ESTAN EN STREAMING STUDIO Y EN EL
   PUBLICISTA */
/* VERIFICAR CUALES SON LAS PUBLICIDADES QUE NECESITAN SER ACTUALIZADAS */

CREATE OR ALTER PROCEDURE Actualizar_Publicidades @id_publicidad INT,
                                                  @url_de_imagen VARCHAR(255),
                                                  @url_de_publicidad VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Publicidad
    SET url_de_imagen     = @url_de_imagen,
        url_de_publicidad = @url_de_publicidad
    WHERE id_publicidad = @id_publicidad
END
go

/* ------------------------------------------------------------------------------------------------------------------ */
/* -------------------------------------------- ACTUALIZAR MAS VISTOS ----------------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Obtener_Contenido_mas_Visto_Actual
AS
BEGIN
    SELECT id_contenido
    FROM dbo.Contenido
    WHERE mas_visto = 1
END
go

CREATE OR ALTER PROCEDURE Obtener_Contenido_mas_Visto_del_Mes_Anterior
AS
BEGIN
    DECLARE @PrimerDiaMesAnterior AS DATE = DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) - 1, 0)
    DECLARE @UltimoDiaMesAnterior AS DATE = EOMONTH(DATEADD(MONTH, -1, GETDATE()))
    SELECT TOP 10 id_contenido
    FROM dbo.Clic
    WHERE fecha BETWEEN @PrimerDiaMesAnterior AND @UltimoDiaMesAnterior
      AND id_publicidad IS NULL
    GROUP BY id_contenido
    ORDER BY COUNT(id_clic) DESC
END
go

/* CONVERTIR AMBOS RESULTADOS EN HASHSET Y VER CUALES SON LOS CONTENIDOS MAS VISTOS DEL MES ANTERIOR QUE NO ESTÁN
   DENTRO DE LOS CONTENIDOS MAS VISTOS ACTUALES */

CREATE OR ALTER PROCEDURE Actualizar_a_Contenido_mas_Visto @id_contenido INT
AS
BEGIN
    UPDATE dbo.Contenido
    SET mas_visto = 1
    WHERE id_contenido = @id_contenido
END
go

/* VER CUALES SON LOS CONTENIDOS MAS VISTOS ACTUALES QUE NO ESTÁN DENTRO DE LOS CONTENIDOS MAS VISTOS ACTUALES */

CREATE OR ALTER PROCEDURE Quitar_de_Contenido_mas_Visto @id_contenido INT
AS
BEGIN
    UPDATE dbo.Contenido
    SET mas_visto = 0
    WHERE id_contenido = @id_contenido
END
go


/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------ MOSTRAR HOME ---------------------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Obtener_Publicidades_Activas
AS
BEGIN
    SELECT p.id_publicidad, p.id_banner, tb.id_tipo_banner, p.url_de_imagen, p.url_de_publicidad
    FROM dbo.Publicidad p
             JOIN dbo.Costo_Banner cb ON p.id_banner = cb.id_banner
             JOIN dbo.Tipo_Banner tb ON cb.id_tipo_banner = tb.id_tipo_banner
    WHERE CONVERT(DATE, fecha_de_baja, 23) > CONVERT(DATE, CURRENT_TIMESTAMP, 23)
      AND CONVERT(DATE, fecha_de_alta, 23) <= CONVERT(DATE, CURRENT_TIMESTAMP, 23)
      AND tb.fecha_baja IS NULL
    ORDER BY tb.id_tipo_banner DESC
END
go

CREATE OR ALTER PROCEDURE Obtener_Contenido_Destacado @id_cliente INT = NULL
AS
BEGIN
    IF @id_cliente IS NOT NULL AND EXISTS (SELECT 1 FROM dbo.Federacion WHERE id_cliente = @id_cliente)
        BEGIN
            WITH Plataformas_Disponibles AS (SELECT DISTINCT(P.id_plataforma)
                                             FROM dbo.Plataforma_de_Streaming P
                                                      LEFT JOIN dbo.Federacion F ON P.id_plataforma = F.id_plataforma
                                             WHERE F.id_cliente = @id_cliente
                                               AND p.valido = 1)
            SELECT Ca.id_contenido, Ca.id_plataforma, Co.url_imagen
            FROM dbo.Catalogo Ca
                     JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
            WHERE Ca.destacado = 1
              AND Ca.id_plataforma IN (SELECT id_plataforma FROM Plataformas_Disponibles)
              AND (Ca.fecha_de_baja IS NULL OR Ca.fecha_de_alta > Ca.fecha_de_baja)
        END
    ELSE
        BEGIN
            SELECT Ca.id_contenido, Ca.id_plataforma, Co.url_imagen
            FROM dbo.Catalogo Ca
                     JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
            WHERE Ca.destacado = 1
              AND Ca.id_plataforma IN (SELECT id_plataforma FROM dbo.Plataforma_de_Streaming WHERE valido = 1)
              AND (Ca.fecha_de_baja IS NULL OR Ca.fecha_de_alta > Ca.fecha_de_baja)
        END
END
go

CREATE OR ALTER PROCEDURE Obtener_Contenido_Reciente @id_cliente INT = NULL
AS
BEGIN
    IF @id_cliente IS NOT NULL AND EXISTS (SELECT 1 FROM dbo.Federacion WHERE id_cliente = @id_cliente)
        BEGIN
            WITH Plataformas_Disponibles AS (SELECT DISTINCT(P.id_plataforma)
                                             FROM dbo.Plataforma_de_Streaming P
                                                      LEFT JOIN dbo.Federacion F ON P.id_plataforma = F.id_plataforma
                                             WHERE F.id_cliente = @id_cliente
                                               AND p.valido = 1)
            SELECT Ca.id_contenido, Ca.id_plataforma, Co.url_imagen
            FROM dbo.Catalogo Ca
                     JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
            WHERE reciente = 1
              AND Ca.id_plataforma IN (SELECT id_plataforma FROM Plataformas_Disponibles)
              AND (Ca.fecha_de_baja IS NULL OR Ca.fecha_de_alta > Ca.fecha_de_baja)
        END
    ELSE
        BEGIN
            SELECT Ca.id_contenido, Ca.id_plataforma, Co.url_imagen
            FROM dbo.Catalogo Ca
                     JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
            WHERE reciente = 1
              AND Ca.id_plataforma IN (SELECT id_plataforma FROM dbo.Plataforma_de_Streaming WHERE valido = 1)
              AND (Ca.fecha_de_baja IS NULL OR Ca.fecha_de_alta > Ca.fecha_de_baja)
        END
END
go

CREATE OR ALTER PROCEDURE Obtener_Contenido_mas_Visto @id_cliente INT = NULL
AS
BEGIN
    IF @id_cliente IS NOT NULL AND EXISTS (SELECT 1 FROM dbo.Federacion WHERE id_cliente = @id_cliente)
        BEGIN
            WITH Plataformas_Disponibles AS (SELECT DISTINCT(P.id_plataforma)
                                             FROM dbo.Plataforma_de_Streaming P
                                                      LEFT JOIN dbo.Federacion F ON P.id_plataforma = F.id_plataforma
                                             WHERE F.id_cliente = @id_cliente
                                               AND p.valido = 1)
            SELECT Ca.id_contenido, Ca.id_plataforma, Co.url_imagen
            FROM dbo.Catalogo Ca
                     JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
            WHERE mas_visto = 1
              AND Ca.id_plataforma IN (SELECT id_plataforma FROM Plataformas_Disponibles)
              AND (Ca.fecha_de_baja IS NULL OR Ca.fecha_de_alta > Ca.fecha_de_baja)
        END
    ELSE
        BEGIN
            SELECT Ca.id_contenido, Ca.id_plataforma, Co.url_imagen
            FROM dbo.Catalogo Ca
                     JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
            WHERE mas_visto = 1
              AND Ca.id_plataforma IN (SELECT id_plataforma FROM dbo.Plataforma_de_Streaming WHERE valido = 1)
              AND (Ca.fecha_de_baja IS NULL OR Ca.fecha_de_alta > Ca.fecha_de_baja)
        END
END
go

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------ BUSCAR CONTENIDO POR FILTROS ------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Buscar_Contenido_por_Filtros @id_cliente INT = NULL,
                                                       @titulo VARCHAR(255) = NULL,
                                                       @reciente BIT = NULL,
                                                       @destacado BIT = NULL,
                                                       @clasificacion VARCHAR(255) = NULL,
                                                       @mas_visto BIT = NULL,
                                                       @genero VARCHAR(255) = NULL
AS
BEGIN
    IF @id_cliente IS NOT NULL AND EXISTS (SELECT 1 FROM dbo.Federacion WHERE id_cliente = @id_cliente)
        BEGIN
            WITH Plataformas_Disponibles AS (SELECT DISTINCT(P.id_plataforma)
                                             FROM dbo.Plataforma_de_Streaming P
                                                      LEFT JOIN dbo.Federacion F ON P.id_plataforma = F.id_plataforma
                                             WHERE F.id_cliente = @id_cliente
                                               AND p.valido = 1)
            SELECT Ca.id_contenido, Co.url_imagen, Co.titulo
            FROM dbo.Catalogo Ca
                     JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
            WHERE (fecha_de_baja IS NULL OR fecha_de_alta > fecha_de_baja)
              AND Ca.id_plataforma IN (SELECT id_plataforma FROM Plataformas_Disponibles)
              AND (@titulo IS NULL
                OR Co.titulo LIKE '%' + @titulo + '%')
              AND (@reciente IS NULL
                OR Ca.reciente = @reciente)
              AND (@destacado IS NULL
                OR Ca.destacado = @destacado)
              AND (@clasificacion IS NULL
                OR Co.clasificacion = @clasificacion)
              AND (@mas_visto IS NULL
                OR Co.mas_visto = @mas_visto)
              AND (@genero IS NULL OR
                   EXISTS (SELECT 1
                           FROM dbo.Genero_Contenido Gc
                                    JOIN Genero G ON Gc.id_genero = G.id_genero
                           WHERE Gc.id_contenido = Ca.id_contenido
                             AND G.descripcion IN (SELECT value FROM STRING_SPLIT(@genero, ','))))
        END
    ELSE
        BEGIN
            SELECT Ca.id_contenido, Co.url_imagen, Co.titulo
            FROM dbo.Catalogo Ca
                     JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
            WHERE (fecha_de_baja IS NULL OR fecha_de_alta > fecha_de_baja)
              AND Ca.id_plataforma IN (SELECT id_plataforma FROM dbo.Plataforma_de_Streaming WHERE valido = 1)
              AND (@titulo IS NULL
                OR Co.titulo LIKE '%' + @titulo + '%')
              AND (@reciente IS NULL
                OR Ca.reciente = @reciente)
              AND (@destacado IS NULL
                OR Ca.destacado = @destacado)
              AND (@clasificacion IS NULL
                OR Co.clasificacion = @clasificacion)
              AND (@mas_visto IS NULL
                OR Co.mas_visto = @mas_visto)
              AND (@genero IS NULL OR
                   EXISTS (SELECT 1
                           FROM dbo.Genero_Contenido Gc
                                    JOIN Genero G ON Gc.id_genero = G.id_genero
                           WHERE Gc.id_contenido = Ca.id_contenido
                             AND G.descripcion IN (SELECT value FROM STRING_SPLIT(@genero, ','))))
        END
END
go

CREATE OR ALTER PROCEDURE Obtener_Informacion_de_Contenido @id_contenido INT
AS
BEGIN
    SELECT Co.url_imagen, Co.titulo, Co.descripcion
    FROM dbo.Contenido Co
    WHERE Co.id_contenido = @id_contenido
END
go

CREATE OR ALTER PROCEDURE Obtener_Generos @id_contenido INT
AS
BEGIN
    SELECT G.descripcion
    FROM dbo.Contenido Co
             JOIN dbo.Genero_Contenido Gc ON Co.id_contenido = Gc.id_contenido
             JOIN dbo.Genero G ON Gc.id_genero = G.id_genero
    WHERE Co.id_contenido = @id_contenido
END
go

CREATE OR ALTER PROCEDURE Obtener_Directores @id_contenido INT
AS
BEGIN
    SELECT D.nombre, D.apellido
    FROM dbo.Contenido Co
             JOIN dbo.Director_Contenido Dc ON Co.id_contenido = Dc.id_contenido
             JOIN dbo.Director D ON Dc.id_director = D.id_director
    WHERE Co.id_contenido = @id_contenido
END
go

CREATE OR ALTER PROCEDURE Obtener_Actores @id_contenido INT
AS
BEGIN
    SELECT A.nombre, A.apellido
    FROM dbo.Contenido Co
             JOIN dbo.Actor_Contenido Ac ON Co.id_contenido = Ac.id_contenido
             JOIN dbo.Actor A ON Ac.id_actor = A.id_actor
    WHERE Co.id_contenido = @id_contenido
END
go

CREATE OR ALTER PROCEDURE Obtener_Informacion_de_Plataforma @id_cliente INT = NULL,
                                                            @id_contenido INT
AS
BEGIN
    IF @id_cliente IS NOT NULL AND EXISTS (SELECT 1 FROM dbo.Federacion WHERE id_cliente = @id_cliente)
        BEGIN
            WITH Plataformas_Disponibles AS (SELECT DISTINCT(P.id_plataforma)
                                             FROM dbo.Plataforma_de_Streaming P
                                                      LEFT JOIN dbo.Federacion F ON P.id_plataforma = F.id_plataforma
                                             WHERE F.id_cliente = @id_cliente
                                               AND p.valido = 1)
            SELECT Ps.id_plataforma, Ps.url_imagen, Ca.id_en_plataforma
            FROM dbo.Plataforma_de_Streaming Ps
                     JOIN dbo.Catalogo Ca ON Ps.id_plataforma = Ca.id_plataforma
            WHERE id_contenido = @id_contenido
              AND Ps.id_plataforma IN (SELECT id_plataforma FROM Plataformas_Disponibles)
              AND (Ca.fecha_de_baja IS NOT NULL OR Ca.fecha_de_alta > Ca.fecha_de_baja)
        END
    ELSE
        BEGIN
            SELECT Ps.id_plataforma, Ps.url_imagen, Ca.id_en_plataforma
            FROM dbo.Plataforma_de_Streaming Ps
                     JOIN dbo.Catalogo Ca ON Ps.id_plataforma = Ca.id_plataforma
            WHERE id_contenido = @id_contenido
              AND Ps.id_plataforma IN (SELECT id_plataforma FROM dbo.Plataforma_de_Streaming WHERE valido = 1)
              AND (Ca.fecha_de_baja IS NOT NULL OR Ca.fecha_de_alta > Ca.fecha_de_baja)
        END
END
go

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------ CONSULTAR URL DE CONTENIDO A REPRODUCIR ------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

-- buscar federaion

/* Obtener_Datos_de_Sesion */ --> ESTO ESTÁ CREADO, ES PARA OBTENER EL TOKEN_DE_SERVICIO Y LA URL_API DE LA PLATAFORMA

/* PEGARLE A LA API DE LA PLATAFORMA DE STREAMING PARA OBTENER LA SESION DEL USUARIO */

-- PEGARLE A LA API DE LA PLATAFORMA DE STREAMING PARA OBTENER EL CONTENIDO


-- prueba para controller

CREATE OR ALTER PROCEDURE Obtener_Tipo_Fee @id_tipo_de_fee SMALLINT
AS
BEGIN
    SELECT *
    FROM dbo.Tipo_Fee
    WHERE id_tipo_de_fee = @id_tipo_de_fee
END
go

