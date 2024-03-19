USE Plataforma_de_Streaming;

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
DROP TABLE IF EXISTS dbo.Partner
DROP TABLE IF EXISTS dbo.Sesion
DROP TABLE IF EXISTS dbo.Autorizacion
DROP TABLE IF EXISTS dbo.Transaccion
DROP TABLE IF EXISTS dbo.Cliente_Usuario

CREATE TABLE [Reporte]
(
    [id_reporte]  INT IDENTITY (1,1) PRIMARY KEY,
    [total]       INT,
    [fecha]       DATE         NOT NULL,
    [descripcion] VARCHAR(255) NOT NULL
);

CREATE TABLE [Factura]
(
    [id_factura]  INT IDENTITY (1,1) PRIMARY KEY,
    [total]       FLOAT,
    [fecha]       DATE         NOT NULL,
    [descripcion] VARCHAR(255) NOT NULL
);

CREATE TABLE [Partner]
(
    [id_partner]        INT IDENTITY (1,1) PRIMARY KEY,
    [nombre]            VARCHAR(255) NOT NULL,
    [token_de_servicio] VARCHAR(255) NOT NULL
);

CREATE TABLE [Cliente_Usuario]
(
    [id_cliente] INT          NOT NULL,
    [usuario]    VARCHAR(255) NOT NULL,
    [contraseña] VARCHAR(255) NOT NULL,
    [email]      VARCHAR(255) NOT NULL,
    [nombre]     VARCHAR(255) NOT NULL,
    [apellido]   VARCHAR(255) NOT NULL,
    [valido]     BIT          NOT NULL,
    PRIMARY KEY ([id_cliente])
);

CREATE TABLE [Transaccion]
(
    [codigo_de_transaccion] VARCHAR(255),
    [fecha_de_alta]         DATETIME NOT NULL,
    [url_de_redireccion]    VARCHAR(255),
    PRIMARY KEY ([codigo_de_transaccion])
);

CREATE TABLE [Autorizacion]
(
    [codigo_de_transaccion] VARCHAR(255),
    [id_cliente]            INT,
    [token]                 VARCHAR(255) NOT NULL,
    [fecha_de_alta]         DATETIME     NOT NULL,
    [fecha_de_baja]         DATETIME,
    PRIMARY KEY ([codigo_de_transaccion], [id_cliente]),
    CONSTRAINT [FK_Autorizacion.codigo_de_transaccion]
        FOREIGN KEY ([codigo_de_transaccion])
            REFERENCES [Transaccion] ([codigo_de_transaccion]),
    CONSTRAINT [FK_Autorizacion.id_cliente]
        FOREIGN KEY ([id_cliente])
            REFERENCES [Cliente_Usuario] ([id_cliente])
);


CREATE TABLE [Sesion]
(
    [id_cliente]          INT          NOT NULL,
    [sesion]              VARCHAR(255) NOT NULL,
    [fecha_de_creacion]   DATETIME     NOT NULL,
    [fecha_de_expiracion] DATETIME     NOT NULL,
    [fecha_de_uso]        DATETIME,
    PRIMARY KEY ([id_cliente], [sesion]),
    CONSTRAINT [FK_Sesion.id_cliente]
        FOREIGN KEY ([id_cliente])
            REFERENCES [Cliente_Usuario] ([id_cliente])
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
    [reciente]      BIT          NOT NULL,
    [destacado]     BIT          NOT NULL,
    [fecha_alta]    DATETIME     NOT NULL,
    [fecha_baja]    DATETIME,
    CONSTRAINT [FK_Contenido.Clasificacion]
        FOREIGN KEY ([clasificacion])
            REFERENCES [Clasificacion] ([id_clasificacion])
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE [Genero]
(
    [id_genero]   INT IDENTITY (1,1) PRIMARY KEY,
    [descripcion] VARCHAR(255) NOT NULL,
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
