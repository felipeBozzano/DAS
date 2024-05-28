USE Star_Plus;

DROP TABLE IF EXISTS dbo.Autorizacion
DROP TABLE IF EXISTS dbo.Transaccion
DROP TABLE IF EXISTS dbo.Sesion
DROP TABLE IF EXISTS dbo.Partner
DROP TABLE IF EXISTS dbo.Cliente_Usuario
DROP TABLE IF EXISTS dbo.Actor_Contenido
DROP TABLE IF EXISTS dbo.Actor
DROP TABLE IF EXISTS dbo.Director_Contenido
DROP TABLE IF EXISTS dbo.Director
DROP TABLE IF EXISTS dbo.Genero_Contenido
DROP TABLE IF EXISTS dbo.Genero
DROP TABLE IF EXISTS dbo.Contenido
DROP TABLE IF EXISTS dbo.Clasificacion
DROP TABLE IF EXISTS dbo.Factura
DROP TABLE IF EXISTS dbo.Reporte

CREATE TABLE [Director]
(
    [id_director] INT IDENTITY (1,1),
    [nombre]      VARCHAR(255) NOT NULL,
    [apellido]    VARCHAR(255) NOT NULL,
    PRIMARY KEY ([id_director])
);

CREATE TABLE [Clasificacion]
(
    [id_clasificacion] INT IDENTITY (1,1),
    [descripcion]      VARCHAR(255) NOT NULL,
    PRIMARY KEY ([id_clasificacion])
);

CREATE TABLE [Contenido]
(
    [id_contenido]  VARCHAR(255),
    [titulo]        VARCHAR(255) NOT NULL,
    [descripcion]   VARCHAR(255) NOT NULL,
    [url_imagen]    VARCHAR(255) NOT NULL,
    [clasificacion] INT          NOT NULL,
    [reciente]      BIT          NOT NULL,
    [destacado]     BIT          NOT NULL,
    [fecha_alta]    DATETIME     NOT NULL,
    [fecha_baja]    DATETIME,
    PRIMARY KEY ([id_contenido]),
    CONSTRAINT [FK_Contenido.clasificacion]
        FOREIGN KEY ([clasificacion])
            REFERENCES [Clasificacion] ([id_clasificacion])
);

CREATE TABLE [Director_Contenido]
(
    [id_contenido] VARCHAR(255) NOT NULL,
    [id_director]  INT          NOT NULL,
    PRIMARY KEY ([id_contenido], [id_director]),
    CONSTRAINT [FK_Director_Contenido.id_contenido]
        FOREIGN KEY ([id_contenido])
            REFERENCES [Contenido] ([id_contenido]),
    CONSTRAINT [FK_Director_Contenido.id_director]
        FOREIGN KEY ([id_director])
            REFERENCES [Director] ([id_director])
);

CREATE TABLE [Partner]
(
    [id_partner]        INT IDENTITY (1,1) NOT NULL,
    [nombre]            VARCHAR(255)       NOT NULL,
    [token_de_servicio] VARCHAR(255)       NOT NULL,
    PRIMARY KEY ([id_partner])
);

CREATE TABLE [Cliente_Usuario]
(
    [id_cliente] INT IDENTITY (1,1) NOT NULL,
    [contrasena] VARCHAR(255)       NOT NULL,
    [email]      VARCHAR(255)  UNIQUE     NOT NULL,
    [nombre]     VARCHAR(255)       NOT NULL,
    [apellido]   VARCHAR(255)       NOT NULL,
    [valido]     BIT                NOT NULL,
    PRIMARY KEY ([id_cliente])
);

CREATE INDEX [UK] ON [Cliente_Usuario] ([email]);

CREATE TABLE [Sesion]
(
    [id_cliente]        INT          NOT NULL,
    [sesion]            VARCHAR(255) NOT NULL,
    [fecha_de_creacion] DATETIME     NOT NULL,
    [fecha_de_uso]      DATETIME,
    [id_partner]        INT          NOT NULL,
    PRIMARY KEY ([id_cliente], [sesion]),
    CONSTRAINT [FK_Sesion.id_partner]
        FOREIGN KEY ([id_partner])
            REFERENCES [Partner] ([id_partner]),
    CONSTRAINT [FK_Sesion.id_cliente]
        FOREIGN KEY ([id_cliente])
            REFERENCES [Cliente_Usuario] ([id_cliente])
);

CREATE TABLE [Reporte]
(
    [id_reporte]  INT IDENTITY (1,1) NOT NULL,
    [total]       INT                NOT NULL,
    [fecha]       DATE               NOT NULL,
    [descripcion] VARCHAR(MAX)       NOT NULL,
    PRIMARY KEY ([id_reporte])
);

CREATE TABLE [Genero]
(
    [id_genero]   INT IDENTITY (1,1) NOT NULL,
    [descripcion] VARCHAR(255)       NOT NULL,
    PRIMARY KEY ([id_genero])
);

CREATE TABLE [Transaccion]
(
    [codigo_de_transaccion] VARCHAR(255),
    [fecha_de_alta]         DATETIME     NOT NULL,
    [url_de_redireccion]    VARCHAR(255) NOT NULL,
    [tipo_de_transaccion]   VARCHAR(1)   NOT NULL,
    PRIMARY KEY ([codigo_de_transaccion])
);

CREATE TABLE [Factura]
(
    [id_factura]  INT IDENTITY (1,1) NOT NULL,
    [total]       FLOAT              NOT NULL,
    [fecha]       DATE               NOT NULL,
    [descripcion] VARCHAR(MAX)       NOT NULL,
    PRIMARY KEY ([id_factura])
);

CREATE TABLE [Actor]
(
    [id_actor] INT IDENTITY (1,1) NOT NULL,
    [nombre]   VARCHAR(255)       NOT NULL,
    [apellido] VARCHAR(255)       NOT NULL,
    PRIMARY KEY ([id_actor])
);

CREATE TABLE [Actor_Contenido]
(
    [id_contenido] VARCHAR(255) NOT NULL,
    [id_actor]     INT          NOT NULL,
    PRIMARY KEY ([id_contenido], [id_actor]),
    CONSTRAINT [FK_Actor_Contenido.id_actor]
        FOREIGN KEY ([id_actor])
            REFERENCES [Actor] ([id_actor]),
    CONSTRAINT [FK_Actor_Contenido.id_contenido]
        FOREIGN KEY ([id_contenido])
            REFERENCES [Contenido] ([id_contenido])
);

CREATE TABLE [Genero_Contenido]
(
    [id_contenido] VARCHAR(255) NOT NULL,
    [id_genero]    INT          NOT NULL,
    PRIMARY KEY ([id_contenido], [id_genero]),
    CONSTRAINT [FK_Genero_Contenido.id_contenido]
        FOREIGN KEY ([id_contenido])
            REFERENCES [Contenido] ([id_contenido]),
    CONSTRAINT [FK_Genero_Contenido.id_genero]
        FOREIGN KEY ([id_genero])
            REFERENCES [Genero] ([id_genero])
);

CREATE TABLE [Autorizacion]
(
    [codigo_de_transaccion] VARCHAR(255),
    [id_cliente]            INT,
    [token]                 VARCHAR(255) ,
    [fecha_de_alta]         DATETIME     NOT NULL,
    [url_de_redireccion]    VARCHAR(255) NOT NULL,
    [tipo_de_transaccion]   VARCHAR(1)   NOT NULL,
    [fecha_de_baja]         DATETIME,
    PRIMARY KEY ([codigo_de_transaccion]),
    CONSTRAINT [FK_Autorizacion.id_cliente]
        FOREIGN KEY ([id_cliente])
            REFERENCES [Cliente_Usuario] ([id_cliente]),
    CONSTRAINT [UK_codigo_de_transaccion] UNIQUE (codigo_de_transaccion),
    CONSTRAINT [UK_token] UNIQUE (token),
);

