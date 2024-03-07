USE StreamingStudio;

DROP TABLE IF EXISTS dbo.Administrador
DROP TABLE IF EXISTS dbo.Detalle_Factura
DROP TABLE IF EXISTS dbo.Detalle_Reporte
DROP TABLE IF EXISTS dbo.Federacion
DROP TABLE IF EXISTS dbo.Transaccion
DROP TABLE IF EXISTS dbo.Tipo_Usuario
DROP TABLE IF EXISTS dbo.Preferencia
DROP TABLE IF EXISTS dbo.Clic
DROP TABLE IF EXISTS dbo.Catalogo
DROP TABLE IF EXISTS dbo.Actor_Contenido
DROP TABLE IF EXISTS dbo.Actor
DROP TABLE IF EXISTS dbo.Director_Contenido
DROP TABLE IF EXISTS dbo.Director
DROP TABLE IF EXISTS dbo.Genero_Contenido
DROP TABLE IF EXISTS dbo.Genero
DROP TABLE IF EXISTS dbo.Contenido
DROP TABLE IF EXISTS dbo.Clasificacion
DROP TABLE IF EXISTS dbo.Fee_Plataforma
DROP TABLE IF EXISTS dbo.Fee
DROP TABLE IF EXISTS dbo.Plataforma_de_Streaming
DROP TABLE IF EXISTS dbo.Cliente_Usuario
DROP TABLE IF EXISTS dbo.Publicidad
DROP TABLE IF EXISTS dbo.Publicista
DROP TABLE IF EXISTS dbo.Factura
DROP TABLE IF EXISTS dbo.Estado_Factura
DROP TABLE IF EXISTS dbo.Reporte
DROP TABLE IF EXISTS dbo.Estado_Reporte
DROP TABLE IF EXISTS dbo.Costo_Banner
DROP TABLE IF EXISTS dbo.Tipo_Banner
DROP TABLE IF EXISTS dbo.Banner

CREATE TABLE [Administrador]
(
    [id_administrador] INT IDENTITY (1,1) PRIMARY KEY,
    [usuario]          VARCHAR(255) NOT NULL,
    [contraseña]       VARCHAR(255) NOT NULL,
    [email]            VARCHAR(255) NOT NULL
);

CREATE TABLE [Estado_Factura]
(
    [id_estado]   SMALLINT PRIMARY KEY,
    [descripcion] VARCHAR(255) NOT NULL
);

CREATE TABLE [Estado_Reporte]
(
    [id_estado]   SMALLINT PRIMARY KEY,
    [descripcion] VARCHAR(255) NOT NULL,
);

CREATE TABLE [Plataforma_de_Streaming]
(
    [id_plataforma]         SMALLINT IDENTITY (1,1) PRIMARY KEY,
    [nombre_de_fantasia]    VARCHAR(255) NOT NULL,
    [razón_social]          VARCHAR(255) NOT NULL,
    [url_imagen]            VARCHAR(255) NOT NULL,
    [token_de_servicio]     VARCHAR(255) NOT NULL,
    [url_api]               VARCHAR(255) NOT NULL,
    [valido]                BIT          NOT NULL
);

CREATE TABLE [Publicista]
(
    [id_publicista]         INT IDENTITY (1,1) PRIMARY KEY,
    [nombre_de_fantasia]    VARCHAR(255) NOT NULL,
    [razón_social]          VARCHAR(255) NOT NULL,
    [email]                 VARCHAR(255) NOT NULL,
    [contraseña]            VARCHAR(255) NOT NULL,
    [token_de_servicio]     VARCHAR(255) NOT NULL,
    [url_api]               VARCHAR(255) NOT NULL,
);

CREATE TABLE [Reporte]
(
    [id_reporte]    INT IDENTITY (1,1) PRIMARY KEY,
    [total]         FLOAT,
    [fecha]         DATE     NOT NULL,
    [estado]        SMALLINT NOT NULL,
    [id_publicista] SMALLINT,
    [id_plataforma] SMALLINT,
    CONSTRAINT [FK_Reporte.estado]
        FOREIGN KEY ([estado])
            REFERENCES [Estado_Reporte] ([id_estado]),
    CONSTRAINT [CHK_Reporte_Nullability]
        CHECK (
            ([id_publicista] IS NULL AND [id_plataforma] IS NOT NULL) OR
            ([id_publicista] IS NOT NULL AND [id_plataforma] IS NULL)
        )
);

CREATE TABLE [Detalle_Reporte]
(
    [id_reporte]        INT                NOT NULL,
    [id_detalle]        INT IDENTITY (1,1) NOT NULL,
    [descripcion]       VARCHAR(255)       NOT NULL,
    [cantidad_de_clics] INT                NOT NULL,
    PRIMARY KEY ([id_reporte], [id_detalle]),
    CONSTRAINT [FK_Detalle_Reporte.Reporte]
        FOREIGN KEY ([id_reporte])
            REFERENCES [Reporte] ([id_reporte])
            ON UPDATE CASCADE
            ON DELETE CASCADE,

);

CREATE TABLE [Factura]
(
    [id_factura]    INT IDENTITY (1,1) PRIMARY KEY,
    [total]         FLOAT,
    [fecha]         DATE     NOT NULL,
    [estado]        SMALLINT NOT NULL,
    [id_publicista] SMALLINT,
    [id_plataforma] SMALLINT,
    CONSTRAINT [FK_Factura.Estado_Factura]
        FOREIGN KEY ([estado])
            REFERENCES [Estado_Factura] ([id_estado])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [CHK_Factura_Nullability]
        CHECK (
            ([id_publicista] IS NULL AND [id_plataforma] IS NOT NULL) OR
            ([id_publicista] IS NOT NULL AND [id_plataforma] IS NULL)
        )
);

CREATE TABLE [Detalle_Factura]
(
    [id_factura]      INT                NOT NULL,
    [id_detalle]      INT IDENTITY (1,1) NOT NULL,
    [precio_unitario] FLOAT              NOT NULL,
    [cantidad]        INT                NOT NULL,
    [subtotal]        FLOAT              NOT NULL,
    [descripcion]     VARCHAR(255)       NOT NULL,
    PRIMARY KEY ([id_factura], [id_detalle]),
    CONSTRAINT [FK_Detalle_Factura.Factura]
        FOREIGN KEY ([id_factura])
            REFERENCES [Factura] ([id_factura])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Banner]
(
    [id_banner]        SMALLINT IDENTITY (1,1) PRIMARY KEY,
    [tamaño_de_banner] VARCHAR(255) NOT NULL,
    [descripcion]      VARCHAR(255) NOT NULL
);

CREATE TABLE [Tipo_Banner]
(
    [id_tipo_banner]    SMALLINT IDENTITY (1,1) PRIMARY KEY,
    [costo]             FLOAT NOT NULL,
    [exclusividad]      BIT NOT NULL,
    [fecha_alta]        DATETIME NOT NULL,
    [fecha_baja]        DATETIME,
    [descripcion]       VARCHAR(255) NOT NULL,
);

CREATE TABLE [Costo_Banner]
(
    [id_tipo_banner]    SMALLINT NOT NULL,
    [id_banner]         SMALLINT NOT NULL,
    PRIMARY KEY ([id_tipo_banner], [id_banner]),
    CONSTRAINT [FK_Costo_Banner.Tipo_Banner]
        FOREIGN KEY ([id_tipo_banner])
            REFERENCES [Tipo_Banner] ([id_tipo_banner])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Costo_Banner.Banner]
        FOREIGN KEY ([id_banner])
            REFERENCES [Banner] ([id_banner])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Publicidad]
(
    [id_publicidad]     INT IDENTITY (1,1) PRIMARY KEY,
    [id_publicista]     INT          NOT NULL,
    [id_banner]         SMALLINT     NOT NULL,
    [codigo_publicidad] VARCHAR(255) NOT NULL,
    [url_de_imagen]     VARCHAR(255) NOT NULL,
    [url_de_publicidad] VARCHAR(255) NOT NULL,
    [fecha_de_alta]     DATE         NOT NULL,
    [fecha_de_baja]     DATE         NOT NULL,
    CONSTRAINT [FK_Publicidad.Banner]
        FOREIGN KEY ([id_banner])
            REFERENCES [Banner] ([id_banner])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Publicidad.Publicista]
        FOREIGN KEY ([id_publicista])
            REFERENCES [Publicista] ([id_publicista])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Fee]
(
    [id_fee]        SMALLINT PRIMARY KEY,
    [monto]         FLOAT NOT NULL,
    [fecha_baja]    DATETIME
);

CREATE TABLE [Fee_Plataforma]
(
    [id_plataforma] SMALLINT NOT NULL,
    [id_fee]        SMALLINT NOT NULL,
    PRIMARY KEY ([id_plataforma], [id_fee]),
    CONSTRAINT [FK_Fee_Plataforma.Fee]
        FOREIGN KEY ([id_fee])
            REFERENCES [Fee] ([id_fee])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Fee_Plataforma.Plataforma_de_Streaming]
        FOREIGN KEY ([id_plataforma])
            REFERENCES [Plataforma_de_Streaming] ([id_plataforma])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Cliente_Usuario]
(
    [id_cliente] INT IDENTITY (1,1) PRIMARY KEY,
    [usuario]    VARCHAR(255) NOT NULL,
    [contraseña] VARCHAR(255) NOT NULL,
    [email]      VARCHAR(255) UNIQUE NOT NULL,
    [nombre]     VARCHAR(255) NOT NULL,
    [apellido]   VARCHAR(255) NOT NULL,
    [valido]     BIT          NOT NULL
);

CREATE TABLE [Tipo_Usuario]
(
    [id_tipo_usuario] SMALLINT PRIMARY KEY,
    [descripcion]     VARCHAR(255) NOT NULL
);

CREATE TABLE [Transaccion]
(
    [id_plataforma]                 SMALLINT     NOT NULL,
    [id_cliente]                    INT          NOT NULL,
    [fecha_alta]                    DATETIME     NOT NULL,
    [codigo_de_transaccion]         VARCHAR(255) NOT NULL,
    [url_login_registro_plataforma] VARCHAR(255) NOT NULL,
    [url_redireccion_propia]        VARCHAR(255) NOT NULL,
    [tipo_usuario]                  SMALLINT     NOT NULL,
    [token]                         VARCHAR(255),
    [fecha_baja]                    DATETIME,
    [facturada]                     BIT          NOT NULL,
    PRIMARY KEY ([id_plataforma], [id_cliente], [fecha_alta]),
    CONSTRAINT [FK_Transaccion.Tipo_Usuario]
        FOREIGN KEY ([tipo_usuario])
            REFERENCES [Tipo_Usuario] ([id_tipo_usuario])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Transaccion.Plataforma_de_Streaming]
        FOREIGN KEY ([id_plataforma])
            REFERENCES [Plataforma_de_Streaming] ([id_plataforma])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Transaccion.Cliente_Usuario]
        FOREIGN KEY ([id_cliente])
            REFERENCES [Cliente_Usuario] ([id_cliente])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Federacion]
(
    [id_plataforma]         INT          NOT NULL,
    [id_cliente]            INT          NOT NULL,
    [token]                 VARCHAR(255) NOT NULL,
    [tipo_usuario]          VARCHAR(255) NOT NULL,
    [facturada]             BIT          NOT NULL,
    PRIMARY KEY ([id_plataforma], [id_cliente]),
    CONSTRAINT [FK_Federacion.Cliente_Usuario]
        FOREIGN KEY ([id_cliente])
            REFERENCES [Cliente_Usuario] ([id_cliente])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Clasificacion]
(
    [id_clasificacion] SMALLINT IDENTITY (1,1) PRIMARY KEY,
    [descripcion]      VARCHAR(255) NOT NULL
);

CREATE TABLE [Contenido]
(
    [id_contenido]  INT IDENTITY (1,1) PRIMARY KEY,
    [titulo]        VARCHAR(255) NOT NULL,
    [descripcion]   VARCHAR(255) NOT NULL,
    [url_imagen]    VARCHAR(255) NOT NULL,
    [clasificacion] SMALLINT     NOT NULL,
    [mas_visto]     BIT          NOT NULL,
    CONSTRAINT [FK_Contenido.Clasificacion]
        FOREIGN KEY ([clasificacion])
            REFERENCES [Clasificacion] ([id_clasificacion])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Catalogo]
(
    [id_contenido]     INT          NOT NULL,
    [id_plataforma]    SMALLINT     NOT NULL,
    [reciente]         BIT          NOT NULL,
    [destacado]        BIT          NOT NULL,
    [id_en_plataforma] VARCHAR(255) NOT NULL,
    [fecha_de_alta]    DATETIME     NOT NULL,
    [fecha_de_baja]    DATETIME
    PRIMARY KEY ([id_contenido], [id_plataforma]),
    CONSTRAINT [FK_Catalogo.Contenido]
        FOREIGN KEY ([id_contenido])
            REFERENCES [Contenido] ([id_contenido])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Catalogo.Plataforma_de_Streaming]
        FOREIGN KEY ([id_plataforma])
            REFERENCES [Plataforma_de_Streaming] ([id_plataforma])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Clic]
(
    [id_clic]       INT IDENTITY (1,1) PRIMARY KEY,
    [id_cliente]    INT      NOT NULL,
    [id_publicidad] INT,
    [id_plataforma] SMALLINT,
    [id_contenido]  INT,
    [fecha]         DATETIME NOT NULL,
    CONSTRAINT [FK_Clic.Cliente_Usuario]
        FOREIGN KEY ([id_cliente])
            REFERENCES [Cliente_Usuario] ([id_cliente])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Clic.Catalogo]
        FOREIGN KEY ([id_contenido], [id_plataforma])
            REFERENCES [Catalogo] ([id_contenido], [id_plataforma])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Clic.Publicidad]
        FOREIGN KEY ([id_publicidad])
            REFERENCES [Publicidad] ([id_publicidad])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [CHK_Clic_Nullability]
        CHECK (
            ([id_publicidad] IS NULL AND [id_plataforma] IS NOT NULL AND [id_contenido] IS NOT NULL) OR
            ([id_publicidad] IS NOT NULL AND [id_plataforma] IS NULL AND [id_contenido] IS NULL)
        )
);

CREATE TABLE [Genero]
(
    [id_genero]   INT IDENTITY (1,1) PRIMARY KEY,
    [descripcion] VARCHAR(255) NOT NULL,
);

CREATE TABLE [Preferencia]
(
    [id_genero]  INT NOT NULL,
    [id_cliente] INT NOT NULL,
    PRIMARY KEY ([id_genero], [id_cliente]),
    CONSTRAINT [FK_Preferencia.Cliente_Usuario]
        FOREIGN KEY ([id_cliente])
            REFERENCES [Cliente_Usuario] ([id_cliente])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Preferencia.Genero]
        FOREIGN KEY ([id_genero])
            REFERENCES [Genero] ([id_genero])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Genero_Contenido]
(
    [id_contenido] INT NOT NULL,
    [id_genero]    INT NOT NULL,
    PRIMARY KEY ([id_contenido], [id_genero]),
    CONSTRAINT [FK_Genero_Contenido.Genero]
        FOREIGN KEY ([id_genero])
            REFERENCES [Genero] ([id_genero])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Genero_Contenido.Contenido]
        FOREIGN KEY ([id_contenido])
            REFERENCES [Contenido] ([id_contenido])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Actor]
(
    [id_actor] INT IDENTITY (1,1) PRIMARY KEY,
    [nombre]   VARCHAR(255) NOT NULL,
    [apellido] VARCHAR(255) NOT NULL
);

CREATE TABLE [Actor_Contenido]
(
    [id_contenido] INT NOT NULL,
    [id_actor]     INT NOT NULL,
    PRIMARY KEY ([id_contenido], [id_actor]),
    CONSTRAINT [FK_Actor_Contenido.Contenido]
        FOREIGN KEY ([id_contenido])
            REFERENCES [Contenido] ([id_contenido])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Actor_Contenido.Actor]
        FOREIGN KEY ([id_actor])
            REFERENCES [Actor] ([id_actor])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Director]
(
    [id_director] INT IDENTITY (1,1) PRIMARY KEY,
    [nombre]      VARCHAR(255) NOT NULL,
    [apellido]    VARCHAR(255) NOT NULL
);

CREATE TABLE [Director_Contenido]
(
    [id_contenido] INT NOT NULL,
    [id_director]  INT NOT NULL,
    PRIMARY KEY ([id_contenido], [id_director]),
    CONSTRAINT [FK_Director_Contenido.Contenido]
        FOREIGN KEY ([id_contenido])
            REFERENCES [Contenido] ([id_contenido])
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT [FK_Director_Contenido.Director]
        FOREIGN KEY ([id_director])
            REFERENCES [Director] ([id_director])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);
