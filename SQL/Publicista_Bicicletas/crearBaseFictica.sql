USE Publicista_Bicicletas;

INSERT INTO Publicidad (codigo_publicidad, url_de_imagen, url_de_publicidad, fecha_de_alta, fecha_de_baja, tipo_banner)
VALUES ('PB1', 'https://www.urlimagen1.com/imagenes/imagen.jpg', 'https://www.urlpublicidad1.com/publicidad.jpg', '2024-05-30', '2024-06-10', 5),
       ('PB2', 'https://www.urlimagen2.com/imagenes/imagen.jpg', 'https://www.urlpublicidad2.com/publicidad.jpg', '2024-05-31', '2024-06-01', 6),
       ('PB3', 'https://www.urlimagen3.com/imagenes/imagen.jpg', 'https://www.urlpublicidad3.com/publicidad.jpg', '2024-05-01', '2024-06-02', 1),
       ('PB4', 'https://www.urlimagen4.com/imagenes/imagen.jpg', 'https://www.urlpublicidad4.com/publicidad.jpg', '2024-05-02', '2024-06-03', 2),
       ('PB5', 'https://www.urlimagen5.com/imagenes/imagen.jpg', 'https://www.urlpublicidad5.com/publicidad.jpg', '2024-05-03', '2024-06-04', 3);
go

INSERT INTO Reporte (total, fecha, descripcion)
VALUES (50, '2024-03-03', '15 clics en CP1, 35 en CP2');
go

INSERT INTO Factura (total, fecha, descripcion)
VALUES (155.45, '2024-03-10', 'PB1 BANNER 20X20 CON PRESENCIA EN LA PAGINA PRINCIPAL POR X CANTIDAD DE DIAS $50, CP2 BANNER 25X25 CON PRESENCIA EN TODA LA PLATAFORMA POR X CANTIDAD DE DIAS $105.45');
go

INSERT INTO Partner (nombre, token_de_servicio)
VALUES ('Streaming Studio', 'Bicicletas123');
go
