USE StreamingStudio;

DROP PROCEDURE Añadir_Plataforma_de_Streaming
DROP PROCEDURE Comenzar_Federacion
DROP PROCEDURE Crear_Usuario
DROP PROCEDURE Desvincular_Federacion
DROP PROCEDURE Editar_Plataforma_de_Streaming
DROP PROCEDURE Editar_Usuario
DROP PROCEDURE Eliminar_Plataforma_de_Streaming
DROP PROCEDURE Eliminar_Usuario
DROP PROCEDURE Finalizar_Federacion
DROP PROCEDURE Interrumpir_Federacion
DROP PROCEDURE Validar_Usuario
go

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

CREATE OR ALTER PROCEDURE Obtener_Url_de_Sesion @id_plataforma INT
AS
BEGIN
    SELECT url_api
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

/* Transaccion */

CREATE OR ALTER PROCEDURE Comenzar_Federacion @id_plataforma INT,
                                              @id_cliente INT,
                                              @codigo_de_transaccion VARCHAR(255),
                                              @tipo_usuario SMALLINT
AS
BEGIN
    INSERT INTO dbo.Transaccion(id_plataforma, id_cliente, fecha_alta, codigo_de_transaccion,
                                url_login_registro_plataforma, url_redireccion_propia, tipo_usuario, token, fecha_baja,
                                facturada)
    VALUES (@id_plataforma, @id_cliente, getdate(), @codigo_de_transaccion)
END
go

CREATE OR ALTER PROCEDURE Interrumpir_Federacion @id_plataforma INT,
                                                 @id_cliente INT,
                                                 @codigo_de_transaccion VARCHAR(255)
AS
BEGIN
    UPDATE Transaccion
    SET fecha_baja = CURRENT_TIMESTAMP
    WHERE id_plataforma = @id_plataforma
      and id_cliente = @id_cliente
      and codigo_de_transaccion = @codigo_de_transaccion
END
go

CREATE OR ALTER PROCEDURE Finalizar_Federacion @id_plataforma INT,
                                               @id_cliente INT,
                                               @codigo_de_transaccion VARCHAR(255),
                                               @token VARCHAR(255),
                                               @email_externo VARCHAR(255)
AS
BEGIN
    UPDATE Transaccion
    SET token         = @token,
        email_externo = @email_externo
    WHERE id_plataforma = @id_plataforma
      and id_cliente = @id_cliente
      and codigo_de_transaccion = @codigo_de_transaccion
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

/* Exclusividad */

CREATE OR ALTER PROCEDURE Crear_Exclusividad @grado_de_exclusividad VARCHAR(255),
                                             @costo FLOAT,
                                             @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Exclusividad(grado_de_exclusividad, costo, descripcion)
    VALUES (@grado_de_exclusividad, @costo, @descripcion)
END
go

CREATE OR ALTER PROCEDURE Editar_Exclusividad @id_exclusividad INT,
                                              @grado_de_exclusividad VARCHAR(255),
                                              @costo FLOAT,
                                              @descripcion VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Exclusividad
    SET grado_de_exclusividad = @grado_de_exclusividad,
        costo                 = @costo,
        descripcion           = @descripcion
    WHERE id_exclusividad = @id_exclusividad
END
go

CREATE OR ALTER PROCEDURE Eliminar_Exclusividado @id_exclusividad INT
AS
BEGIN
    DELETE
    FROM dbo.Exclusividad
    WHERE id_exclusividad = @id_exclusividad
END
go

/* Banner */

CREATE OR ALTER PROCEDURE Crear_Banner @tamaño_de_banner VARCHAR(255),
                                       @costo FLOAT,
                                       @descripcion VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Banner(tamaño_de_banner, costo, descripcion)
    VALUES (@tamaño_de_banner, @costo, @descripcion)
END
go

CREATE OR ALTER PROCEDURE Editar_Banner @id_banner INT,
                                        @tamaño_de_banner VARCHAR(255),
                                        @costo FLOAT,
                                        @descripcion VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Banner
    SET tamaño_de_banner = @tamaño_de_banner,
        costo            = @costo,
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

/* Publicista */

CREATE OR ALTER PROCEDURE Registrar_Publicista @nombre_de_fantasia VARCHAR(255),
                                               @razón_social VARCHAR(255),
                                               @email VARCHAR(255),
                                               @contraseña VARCHAR(255),
                                               @token_de_servicio VARCHAR(255),
                                               @url_de_reportes VARCHAR(255)
AS
BEGIN
    INSERT INTO dbo.Publicista(nombre_de_fantasia, razón_social, email, contraseña, token_de_servicio, url_de_reportes)
    VALUES (@nombre_de_fantasia, @razón_social, @email, @contraseña, @token_de_servicio, @url_de_reportes)
END
go

CREATE OR ALTER PROCEDURE Editar_Publicista @id_publicista INT,
                                            @nombre_de_fantasia VARCHAR(255),
                                            @razón_social VARCHAR(255),
                                            @email VARCHAR(255),
                                            @contraseña VARCHAR(255),
                                            @token_de_servicio VARCHAR(255),
                                            @url_de_reportes VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Publicista
    SET nombre_de_fantasia = @nombre_de_fantasia,
        razón_social       = @razón_social,
        email              = @email,
        contraseña         = @contraseña,
        token_de_servicio  = @token_de_servicio,
        url_de_reportes    = @url_de_reportes
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
                                               @id_exclusividad VARCHAR(255),
                                               @id_banner VARCHAR(255),
                                               @codigo_publicidad VARCHAR(255),
                                               @url_de_imagen VARCHAR(255),
                                               @url_de_publicidad VARCHAR(255),
                                               @fecha_de_alta DATE,
                                               @fecha_de_baja DATE
AS
BEGIN
    INSERT INTO dbo.Publicidad(id_publicista, id_exclusividad, id_banner, codigo_publicidad, url_de_imagen,
                               url_de_publicidad, fecha_de_alta, fecha_de_baja)
    VALUES (@id_publicista, @id_exclusividad, @id_banner, @codigo_publicidad, @url_de_imagen, @url_de_publicidad,
            @fecha_de_alta, @fecha_de_baja)
END
go

CREATE OR ALTER PROCEDURE Editar_Publicidad @id_publicidad INT,
                                            @id_publicista VARCHAR(255),
                                            @id_exclusividad VARCHAR(255),
                                            @id_banner VARCHAR(255),
                                            @codigo_publicidad VARCHAR(255),
                                            @url_de_imagen VARCHAR(255),
                                            @url_de_publicidad VARCHAR(255),
                                            @fecha_de_alta DATE,
                                            @fecha_de_baja DATE
AS
BEGIN
    UPDATE dbo.Publicidad
    SET id_publicista     = @id_publicista,
        id_exclusividad   = @id_exclusividad,
        id_banner         = @id_banner,
        codigo_publicidad = @codigo_publicidad,
        url_de_imagen     = @url_de_imagen,
        url_de_publicidad = @url_de_publicidad,
        fecha_de_alta     = @fecha_de_alta,
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

CREATE OR ALTER PROCEDURE Crear_Factura
AS
BEGIN
    INSERT INTO dbo.Factura(total, fecha, estado)
    VALUES (0, (SELECT CONVERT(date, CURRENT_TIMESTAMP)), 0)
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

/* Factura_Publicista */

CREATE OR ALTER PROCEDURE Crear_Factura_Publicista @id_publicista INT,
                                                   @id_factura INT
AS
BEGIN
    INSERT INTO dbo.Factura_Publicista(id_publicista, id_factura)
    VALUES (@id_publicista, @id_factura)
END
go

CREATE OR ALTER PROCEDURE Eliminar_Factura_Publicista @id_publicista INT,
                                                      @id_factura INT
AS
BEGIN
    DELETE
    FROM dbo.Factura_Publicista
    WHERE id_publicista = @id_publicista
      AND id_factura = @id_factura
END
go

/* Factura_Plataforma */

CREATE OR ALTER PROCEDURE Crear_Factura_Plataforma @id_factura INT,
                                                   @id_plataforma INT
AS
BEGIN
    INSERT INTO dbo.Factura_Plataforma(id_factura, id_plataforma)
    VALUES (@id_factura, @id_plataforma)
END
go

CREATE OR ALTER PROCEDURE Eliminar_Factura_Plataforma @id_factura INT,
                                                      @id_plataforma INT
AS
BEGIN
    DELETE
    FROM dbo.Factura_Plataforma
    WHERE id_factura = @id_factura
      AND id_plataforma = @id_plataforma
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

CREATE OR ALTER PROCEDURE Crear_Contenido @titulo VARCHAR(255),
                                          @descripcion VARCHAR(255),
                                          @url_imagen VARCHAR(255),
                                          @clasificacion INT,
                                          @mas_visto BIT
AS
BEGIN
    INSERT INTO dbo.Contenido(titulo, descripcion, url_imagen, clasificacion, mas_visto)
    VALUES (@titulo, @descripcion, @url_imagen, @clasificacion, @mas_visto)
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
                                                   @id_en_plataforma VARCHAR(255),
                                                   @fecha_de_alta DATETIME
AS
BEGIN
    INSERT INTO dbo.Catalogo(id_contenido, id_plataforma, reciente, destacado, id_en_plataforma, fecha_de_alta,
                             fecha_de_baja, valido)
    VALUES (@id_contenido, @id_plataforma, @reciente, @destacado, @id_en_plataforma, @fecha_de_alta, NULL, 1)
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

CREATE OR ALTER PROCEDURE Crear_Reporte
AS
BEGIN
    INSERT INTO dbo.Reporte(total, fecha, estado)
    VALUES (0, (SELECT CONVERT(date, CURRENT_TIMESTAMP)), 0)
END
go

CREATE OR ALTER PROCEDURE Finalizar_Reporte @id_reporte INT,
                                            @total VARCHAR(255)
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

/* Reporte_Publicista */

CREATE OR ALTER PROCEDURE Crear_Reporte_Publicista @id_publicista INT,
                                                   @id_reporte INT
AS
BEGIN
    INSERT INTO dbo.Reporte_Publicista(id_publicista, id_reporte)
    VALUES (@id_publicista, @id_reporte)
END
go

CREATE OR ALTER PROCEDURE Eliminar_Reporte_Publicista @id_publicista INT,
                                                      @id_reporte INT
AS
BEGIN
    DELETE
    FROM dbo.Reporte_Publicista
    WHERE id_publicista = @id_publicista
      AND id_reporte = @id_reporte
END
go

/* Reporte_Plataforma */

CREATE OR ALTER PROCEDURE Crear_Reporte_Plataforma @id_reporte INT,
                                                   @id_plataforma INT
AS
BEGIN
    INSERT INTO dbo.Reporte_Plataforma(id_reporte, id_plataforma)
    VALUES (@id_reporte, @id_plataforma)
END
go

CREATE OR ALTER PROCEDURE Eliminar_Reporte_Plataforma @id_reporte INT,
                                                      @id_plataforma INT
AS
BEGIN
    DELETE
    FROM dbo.Reporte_Plataforma
    WHERE id_reporte = @id_reporte
      AND id_plataforma = @id_plataforma
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

/* Crear_Reporte */
/* Crear_Reporte_Publicista */
/* Crear_Detalle_Reporte */
/* Finalizar_Reporte */

CREATE OR ALTER PROCEDURE Obtener_Datos_de_Publicista @id_publicista INT
AS
BEGIN
    SELECT nombre_de_fantasia, razón_social, token_de_servicio, url_de_reportes
    FROM dbo.Publicista
    WHERE id_publicista = @id_publicista
END
go

/* PEGARLE A LA API DEL PUBLICISTA PARA CARGAR EL REPORTE */
/* Enviar_Reporte */



CREATE OR ALTER PROCEDURE Obtener_Estadisticas_para_Plataformas
AS
BEGIN
    SELECT Cc.id_plataforma, Cc.id_contenido, Co.id_en_plataforma, COUNT(id_clic) AS cantidad_de_clics
    FROM dbo.Clic Cc
             JOIN dbo.Catalogo Co ON Cc.id_contenido = Co.id_contenido AND Cc.id_plataforma = Co.id_plataforma
    WHERE Cc.id_contenido IS NOT NULL
      AND Cc.id_plataforma IS NOT NULL
      AND Co.valido = 1
      AND Cc.fecha BETWEEN DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) - 1, 0)
        AND DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0)
    GROUP BY Cc.id_plataforma, Cc.id_contenido, Co.id_en_plataforma
END
go

/* Crear_Reporte */
/* Crear_Reporte_Plataforma */
/* Crear_Detalle_Reporte */
/* Finalizar_Reporte */

CREATE OR ALTER PROCEDURE Obtener_Datos_de_Plataforma @id_plataforma INT
AS
BEGIN
    SELECT nombre_de_fantasia, razón_social, token_de_servicio, url_de_reportes, fee_de_federacion, fee_de_registro
    FROM dbo.Plataforma_de_Streaming
    WHERE id_plataforma = @id_plataforma
END
go

/* PEGARLE A LA API DE PLATAFORMA PARA CARGAR EL REPORTE */
/* Enviar_Reporte */

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------ OBTENER Y ENVIAR FACTURAS --------------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Obtener_Datos_de_Publicidades
AS
BEGIN
    DECLARE @PrimerDiaMesAnterior AS DATE = DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) - 1, 0)
    DECLARE @UltimoDiaMesAnterior AS DATE = EOMONTH(DATEADD(MONTH, -1, GETDATE()))
    SELECT id_publicista,
           id_publicidad,
           id_exclusividad,
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
               END AS cantidad_de_dias
    FROM dbo.Publicidad
    WHERE fecha_de_alta <= @UltimoDiaMesAnterior
      AND fecha_de_baja >= @PrimerDiaMesAnterior
END
go

/* Crear_Factura */
/* Crear_Factura_Plataforma */

CREATE OR ALTER PROCEDURE Obtener_Costo_de_Banner @id_banner INT
AS
BEGIN
    SELECT tamaño_de_banner, costo
    FROM dbo.Banner
    WHERE id_banner = @id_banner
END
go

CREATE OR ALTER PROCEDURE Obtener_Costo_de_Exclusividad @id_exclusividad INT
AS
BEGIN
    SELECT descripcion, costo
    FROM dbo.Exclusividad
    WHERE id_exclusividad = @id_exclusividad
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
    WHERE fecha_alta BETWEEN DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) - 1, 0)
        AND DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0)
      AND facturada = 0
    GROUP BY id_plataforma, tipo_usuario
END
go

/* Crear_Factura */
/* Crear_Factura_Plataforma */

CREATE OR ALTER PROCEDURE Obtener_Fees_de_Plataforma @id_plataforma INT
AS
BEGIN
    SELECT fee_de_federacion, fee_de_registro
    FROM dbo.Plataforma_de_Streaming
    WHERE id_plataforma = @id_plataforma
END
go

/* Crear_Detalle_Factura */
/* Finalizar_Factura */
/* Obtener_Datos_de_Plataforma */
/* PEGARLE A LA API DE LA PLATAFORMA PARA CARGAR LA FACTURA */
/* Enviar_Factura */

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------ FEDERAR CLIENTE A PLATAFORMA ------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Obtener_Token_de_Servicio_de_Plataforma @id_plataforma INT
AS
BEGIN
    SELECT token_de_servicio
    FROM dbo.Plataforma_de_Streaming
    WHERE id_plataforma = @id_plataforma
END
go

CREATE OR ALTER PROCEDURE Obtener_Tipo_de_Usuario @id_tipo_usuario SMALLINT
AS
BEGIN
    SELECT descripcion
    FROM dbo.Tipo_Usuario
    WHERE id_tipo_usuario = @id_tipo_usuario
END
go

/* PEGARLE A LA API DE LA PLATAFORMA PARA OBTENER LA URL DE LOGIN Y EL CÓDIGO DE TRANSACCIÓN. */
/* Comenzar_Federacion */
/* PEGARLE A LA API PARA CONSULTAR EL TOKEN DEL USUARIO */
/* Finalizar_Federacion */

/* ------------------------------------------------------------------------------------------------------------------ */
/* ---------------------------------------- TERMINAR FEDERACIONES PENDIENTES ---------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Consultar_Federaciones_Pendientes
AS
BEGIN
    SELECT id_plataforma, id_cliente, codigo_de_transaccion, tipo_usuario, email_interno
    FROM dbo.Transaccion
    WHERE token IS NULL
      AND fecha_baja IS NOT NULL
END
go

/* POR CADA FEDERACION PENDIENTE */
/* PEGARLE A LA API DE LA PLATAFORMA PARA OBTENER LA URL DE LOGIN Y EL CÓDIGO DE TRANSACCIÓN. */
/* Finalizar_Federacion */

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------ OBTENER Y ACTUALIZAR CATALOGO ----------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Obtener_Catalogo_Actual
AS
BEGIN
    SELECT id_plataforma, id_contenido, reciente, destacado, id_en_plataforma
    FROM dbo.Catalogo
    WHERE fecha_de_baja  IS NULL
END
go

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
/* Crear_Contenido */
/* Agregar_Item_al_Catalogo */

CREATE OR ALTER PROCEDURE Actualizar_Catalogo @id_contenido INT,
                                              @id_plataforma INT,
                                              @reciente BIT,
                                              @destacado BIT,
                                              @id_en_plataforma VARCHAR(255)
AS
BEGIN
    UPDATE dbo.Catalogo
    SET reciente         = @reciente,
        destacado        = @destacado,
        id_en_plataforma = @id_en_plataforma
    WHERE id_contenido = @id_contenido
      AND id_plataforma = @id_plataforma
      AND fecha_de_baja IS NULL
END
go

/* ------------------------------------------------------------------------------------------------------------------ */
/* -------------------------------------- OBTENER DATOS DE PUBLICIDADES --------------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

/* POR CADA PUBLICISTA */
/* Obtener_Datos_de_Publicista */
/* PEGARLE A LA API PARA CONSEGUIR LAS PUBLICIDADES DE LOS PUBLICISTAS */

CREATE OR ALTER PROCEDURE Obtener_Datos_de_Publicidades
AS
BEGIN
    SELECT id_publicista,
           id_publicidad,
           codigo_publicidad,
           url_de_imagen,
           url_de_publicidad
    FROM dbo.Publicidad
    WHERE DAY(fecha_de_baja) >= DAY(GETDATE())
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
    SELECT TOP 100 id_contenido, COUNT(id_clic)
    FROM dbo.Clic
    WHERE fecha BETWEEN @PrimerDiaMesAnterior AND @UltimoDiaMesAnterior
    GROUP BY id_contenido
    ORDER BY COUNT(id_clic) DESC
END
go

/* CONVERTIR AMBOS RESULTADOS EN HASHSET Y VER CUALES SON LOS CONTENIDOS MAS VISTOS DEL MES ANTERIOR QUE NO ESTÁN
   DENTRO DE LOS CONTENIDOS MAS VISTOS ACTUALES */

CREATE OR ALTER PROCEDURE Actualizar_Contenido_mas_Visto @id_contenido INT,
                                                         @mas_visto BIT
AS
BEGIN
    UPDATE dbo.Contenido
    SET mas_visto = @mas_visto
    WHERE id_contenido = @id_contenido
END
go

/* VER CUALES SON LOS CONTENIDOS MAS VISTOS ACTUALES QUE NO ESTÁN DENTRO DE LOS CONTENIDOS MAS VISTOS ACTUALES */
/* Actualizar_Contenido_mas_Visto */


/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------ MOSTRAR HOME ---------------------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Obtener_Publicidades_Activas
AS
BEGIN
    SELECT id_banner, url_de_imagen, url_de_publicidad, grado_de_exclusividad
    FROM dbo.Publicidad P
             JOIN dbo.Exclusividad E ON P.id_exclusividad = E.id_exclusividad
    WHERE CONVERT(DATE, fecha_de_baja, 23) > CONVERT(DATE, CURRENT_TIMESTAMP, 23)
      AND CONVERT(DATE, fecha_de_alta, 23) <= CONVERT(DATE, CURRENT_TIMESTAMP, 23)
    ORDER BY id_banner DESC
END
go

CREATE OR ALTER PROCEDURE Obtener_Contenido_Destacado @id_cliente INT = NULL
AS
BEGIN
    SELECT Ca.id_contenido, url_imagen
    FROM dbo.Catalogo Ca
             JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
    WHERE destacado = 1
      AND id_plataforma IN (IIF(@id_cliente IS NOT NULL,
                                (SELECT id_plataforma FROM dbo.Federacion WHERE id_cliente = @id_cliente),
                                (SELECT id_plataforma from dbo.Catalogo)))
      AND fecha_de_baja IS NULL
END
go

CREATE OR ALTER PROCEDURE Obtener_Contenido_Reciente @id_cliente INT = NULL
AS
BEGIN
    SELECT Ca.id_contenido, url_imagen
    FROM dbo.Catalogo Ca
             JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
    WHERE reciente = 1
      AND id_plataforma IN (IIF(@id_cliente IS NOT NULL,
                                (SELECT id_plataforma FROM dbo.Federacion WHERE id_cliente = @id_cliente),
                                (SELECT id_plataforma from dbo.Catalogo)))
      AND fecha_de_baja IS NULL
END
go

CREATE OR ALTER PROCEDURE Obtener_Contenido_mas_Visto @id_cliente INT = NULL
AS
BEGIN
    SELECT Ca.id_contenido, url_imagen
    FROM dbo.Catalogo Ca
             JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
    WHERE mas_visto = 1
      AND id_plataforma IN (IIF(@id_cliente IS NOT NULL,
                                (SELECT id_plataforma FROM dbo.Federacion WHERE id_cliente = @id_cliente),
                                (SELECT id_plataforma from dbo.Catalogo)))
      AND fecha_de_baja IS NULL
END
go

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------ BUSCAR CONTENIDO POR FILTROS ------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Buscar_Contenido_por_Filtros @id_cliente INT,
                                                       @titulo VARCHAR(255) = NULL,
                                                       @reciente BIT = NULL,
                                                       @destacado BIT = NULL,
                                                       @clasificacion VARCHAR(255) = NULL,
                                                       @mas_visto BIT = NULL,
                                                       @genero VARCHAR(255) = NULL
AS
BEGIN
    SELECT Ca.id_contenido, url_imagen
    FROM dbo.Catalogo Ca
             JOIN dbo.Contenido Co ON Ca.id_contenido = Co.id_contenido
        WHERE fecha_de_baja IS NULL
      AND id_plataforma IN (SELECT id_plataforma FROM dbo.Federacion WHERE id_cliente = @id_cliente)
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
go

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------ CONSULTAR URL DE CONTENIDO A REPRODUCIR ------------------------------------- */
/* ------------------------------------------------------------------------------------------------------------------ */

CREATE OR ALTER PROCEDURE Obtener_Datos_del_Contenido @id_contenido INT,
                                                      @id_plataforma INT
AS
BEGIN
    SELECT token_de_servicio, id_en_plataforma
    FROM dbo.Catalogo C
             JOIN dbo.Plataforma_de_Streaming P ON C.id_plataforma = P.id_plataforma
    WHERE C.id_contenido = @id_contenido
      AND C.id_plataforma = @id_plataforma
      AND valido = 1
END
go

/* PEGARLE A LA API DE LA PLATAFORMA DE STREAMING PARA OBTENER LA URL DE REPRODUCCION DE CONTENIDO */
