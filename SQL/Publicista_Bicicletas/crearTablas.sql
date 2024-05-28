USE Publicista_Bicicletas;

DROP TABLE IF EXISTS dbo.Factura
DROP TABLE IF EXISTS dbo.Reporte
DROP TABLE IF EXISTS dbo.Partner
DROP TABLE IF EXISTS dbo.Publicidad

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

CREATE TABLE [Publicidad]
(
    [id_publicidad]     VARCHAR(255) PRIMARY KEY,
    [url_de_imagen]     VARCHAR(255) NOT NULL,
    [url_de_publicidad] VARCHAR(255) NOT NULL,
    [fecha_de_alta]     DATE         NOT NULL,
    [fecha_de_baja]     DATE         NOT NULL,
    [tipo_banner]       INT          NOT NULL,
);
