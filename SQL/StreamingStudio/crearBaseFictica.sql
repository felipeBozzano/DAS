USE StreamingStudio;

-- Crear 10 usuarios
INSERT INTO Cliente_Usuario (contrasena, email, nombre, apellido, valido)
VALUES (N'Contrasena1@', 'usuario1@example.com', 'Juan', N'Pérez', 1),
       (N'Contrasena1@', 'usuario2@example.com', N'María', N'Gómez', 1),
       (N'Contrasena1@', 'usuario3@example.com', 'Carlos', N'López', 1),
       (N'Contrasena1@', 'usuario4@example.com', 'Laura', N'Martínez', 1),
       (N'Contrasena1@', 'usuario5@example.com', 'Pedro', N'Hernández', 1),
       (N'Contrasena1@', 'usuario6@example.com', 'Ana', N'Sánchez', 1),
       (N'Contrasena1@', 'usuario7@example.com', 'Roberto', N'García', 1),
       (N'Contrasena1@', 'usuario8@example.com', 'Elena', N'Fernández', 1),
       (N'Contrasena1@', 'usuario9@example.com', 'Miguel', N'Díaz', 1),
       (N'Contrasena1@', 'usuario10@example.com', 'Carmen', N'Rodríguez', 1),
       (N'Contrasena1@', 'felipe@gmail.com', 'Felipe', 'Bozzano', 1),
       (N'Contrasena1@', 'francisco@gmail.com', 'Francisco', 'Olmos', 1);
go

-- Crear 3 géneros
INSERT INTO Genero (descripcion)
VALUES ('Drama'),
       ('Comedia'),
       ('Accion');
go

-- Crear Preferencias
INSERT INTO Preferencia (id_genero, id_cliente)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (1, 3),
       (3, 3),
       (2, 4),
       (3, 4),
       (1, 5),
       (2, 5),
       (1, 6),
       (3, 6),
       (1, 7),
       (2, 7),
       (3, 8),
       (2, 9),
       (3, 9),
       (1, 10),
       (2, 10)
go

-- Crear clasificaciones "Serie" y "Película"
INSERT INTO Clasificacion (id_clasificacion, descripcion)
VALUES ('S', 'Serie'),
       ('P', 'Pelicula');
go

-- Crear 10 actores
INSERT INTO Actor (nombre, apellido)
VALUES ('Actor1', 'Apellido1'),
       ('Actor2', 'Apellido2'),
       ('Actor3', 'Apellido3'),
       ('Actor4', 'Apellido4'),
       ('Actor5', 'Apellido5'),
       ('Actor6', 'Apellido6'),
       ('Actor7', 'Apellido7'),
       ('Actor8', 'Apellido8'),
       ('Actor9', 'Apellido9'),
       ('Actor10', 'Apellido10');
go

-- Crear 10 directores
INSERT INTO Director (nombre, apellido)
VALUES ('Director1', 'Apellido1'),
       ('Director2', 'Apellido2'),
       ('Director3', 'Apellido3'),
       ('Director4', 'Apellido4'),
       ('Director5', 'Apellido5'),
       ('Director6', 'Apellido6'),
       ('Director7', 'Apellido7'),
       ('Director8', 'Apellido8'),
       ('Director9', 'Apellido9'),
       ('Director10', 'Apellido10');
go

-- Crear 8 contenidos
INSERT INTO Contenido (id_contenido, titulo, descripcion, url_imagen, clasificacion, mas_visto)
VALUES ('CP1', 'Toy Story', N'Descripcion de Toy Story',
        'https://es.web.img3.acsta.net/pictures/14/03/17/10/20/509771.jpg',
        'P', 1),
       ('CS3', 'Kung fu Panda', N'Descripción de Kung fu Panda',
        'https://m.media-amazon.com/images/S/pv-target-images/d775fb8599018935d1e28ed9cbc2fb67870d726d850f0eee13dc6f50f37b76d3.jpg',
        'S', 0),
       ('CS1', 'Spiderman', N'Descripción de Spiderman',
        'https://image.api.playstation.com/vulcan/ap/rnd/202009/3021/B2aUYFC0qUAkNnjbTHRyhrg3.png',
        'S', 1),
       ('CP4', 'Drive', N'Descripción de Drive',
        'https://i.blogs.es/d365ef/drive-2011/650_1200.jpg',
        'P', 0);
go

-- Asignar directores a contenidos en Director_Contenido
INSERT INTO Director_Contenido (id_contenido, id_director)
VALUES ('CP1', 1),
       ('CP1', 2),
       ('CS3', 3),
       ('CS1', 3),
       ('CS1', 4),
       ('CP4', 4);
go

-- Asignar actores a contenidos en Actor_Contenido
INSERT INTO Actor_Contenido (id_contenido, id_actor)
VALUES ('CP1', 1),
       ('CP1', 2),
       ('CS3', 8),
       ('CS1', 3),
       ('CS1', 4),
       ('CP4', 1);
go

-- Asignar generos a contenidos en Genero_Contenido
INSERT INTO Genero_Contenido (id_contenido, id_genero)
VALUES ('CP1', 1),
       ('CP1', 3),
       ('CS3', 3),
       ('CS3', 1),
       ('CS1', 2),
       ('CP4', 1);
go

-- Crear 3 Plataformas de Streaming
INSERT INTO Plataforma_de_Streaming (nombre_de_fantasia, razon_social, url_imagen, token_de_servicio, url_api,
                                     protocolo_api, valido)
VALUES ('Netflix', N'Netflix Argentina S.R.L.',
        'https://images.ctfassets.net/4cd45et68cgf/4nBnsuPq03diC5eHXnQYx/d48a4664cdc48b6065b0be2d0c7bc388/Netflix-Logo.jpg',
        'Netflix123', 'http://localhost:8081/netflix', 'REST', 1),
       ('Prime Video', N'Prime Video Argentina S.R.L.',
        'https://yt3.googleusercontent.com/pn_3JEt2nFaRA6dY08NzFM2w8A7NtUbaniamEnObxtLX3ZhT9w41KW0W0pjl-RiczGS0rgzZ=s900-c-k-c0x00ffffff-no-rj',
        'PrimeVideo123', 'http://localhost:8082/prime_video', 'REST', 1),
       ('Disney Plus', N'Disney Plus S.R.L.',
        'https://yt3.googleusercontent.com/j2CPWGfJvBR7k_hNcV6Dx7oOqHR2nJu16ZaD8r8o9jjS9G-Spn8B_5vqJ8CqvBScLJ3rKLrk=s900-c-k-c0x00ffffff-no-rj',
        'DisneyPlus123', 'http://localhost:8083/disney_plus', 'REST', 1),
       ('Star Plus', N'Star Plus S.R.L.',
        'https://media.ambito.com/p/050f05a32403fba3d95c8b783ccccee6/adjuntos/239/imagenes/039/297/0039297233/1200x675/smart/star-plusjpg.jpg',
        'StarPlus123', 'http://localhost:8084/star_plus', 'SOAP', 1);
go

-- Llenar Catálogo
INSERT INTO Catalogo (id_contenido, id_plataforma, reciente, destacado, fecha_de_alta, fecha_de_baja)
VALUES ('CP1', 1, 1, 0, '2024-03-10 00:14:47.770', NULL),
       ('CS3', 2, 0, 1, '2024-03-10 00:14:47.770', NULL),
       ('CS1', 3, 0, 0, '2024-03-10 00:14:47.770', NULL),
       ('CP4', 4, 1, 0, '2024-03-10 00:14:47.770', NULL);
go

-- Crear 2 Tipo_de_Fee
INSERT INTO Tipo_Fee (tipo_de_fee, descripcion)
VALUES (1, N'Fee de federación para usuario Nuevo'),
       (2, N'Fee de federación para usuario Registrado');
go

-- Crear 4 Fee
INSERT INTO Fee (monto, fecha_alta, fecha_baja, tipo_de_fee)
VALUES (2.0, GETDATE(), NULL, 1),
       (3.0, GETDATE(), NULL, 1),
       (2.5, GETDATE(), NULL, 2),
       (1.5, GETDATE(), NULL, 2);
go

-- Crear 6 Fee_Plataforma
INSERT INTO Fee_Plataforma (id_plataforma, id_fee)
VALUES (1, 1),
       (1, 3),
       (2, 2),
       (2, 4),
       (3, 1),
       (3, 4);
go

-- Crear 6 Tipo_Banner
INSERT INTO Tipo_Banner (fecha_alta, tamano, exclusividad, fecha_baja)
VALUES (GETDATE(), '10*20', 'E', null),
       (GETDATE(), '30*40', 'E', null),
       (GETDATE(), '50*50', 'N', null),
       (GETDATE(), '20*30', 'N', null),
       (GETDATE(), '10*20', 'N', null),
       (GETDATE(), '15*15', 'N', null);
go

-- Crear 6 Costo_Banner
INSERT INTO Costo_Banner (id_tipo_banner, fecha_alta, costo, fecha_baja)
VALUES (1, GETDATE(), 9.5, null),
       (2, GETDATE(), 2.0, null),
       (3, GETDATE(), 3.0, null),
       (4, GETDATE(), 4.0, null),
       (5, GETDATE(), 5.5, null),
       (6, GETDATE(), 6.5, null);
go

-- Crear 3 Publicista
INSERT INTO Publicista (nombre_de_fantasia, razon_social, email, contrasena, token_de_servicio, url_api, protocolo_api)
VALUES ('Publicista_Musimundo', N'Musimundo', 'musimundo@example.com', N'contrasena1', 'Musimundo123',
        'http://localhost:8086/publicistaMusimundo', 'SOAP'),
       ('Publicista_Bicicletas', N'Bicicletas', 'bicicletas@example.com', N'contrasena2', 'Bicicletas123',
        'http://localhost:8087/publicistaBicicletas', 'SOAP'),
       ('Publicista_UBP', N'UBP', 'ubp@example.com', N'contrasena3', 'UBP123',
        'http://localhost:8088/publicistaUBP', 'REST');
go

-- Crear 9 Publicidades
INSERT INTO Publicidad (id_publicista, codigo_publicidad, url_de_imagen, url_de_publicidad, fecha_de_alta,
                        fecha_de_baja)
VALUES (1, 'PM1',
        'https://play-lh.googleusercontent.com/a6rUmDehKQQmNxss8tRWIQQsymc6M6K0Dbyj-QidfcOEX7sXiiIyRM1gXMj8gcbrUA',
        'https://www.urlpublicidad1.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (2, 'PB1', 'https://inforges.es/wp-content/uploads/2022/07/migracion-aws.png',
        'https://www.urlpublicidad2.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (3, 'PU1', 'https://pbs.twimg.com/profile_images/1599827064282140672/MSxVR5u6_400x400.jpg',
        'https://www.urlpublicidad3.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (1, 'PM2',
        'https://images.ctfassets.net/4cd45et68cgf/4nBnsuPq03diC5eHXnQYx/d48a4664cdc48b6065b0be2d0c7bc388/Netflix-Logo.jpg',
        'https://www.urlpublicidad4.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (2, 'PB2',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_qjqmDxaGwHDTmC0E_d4kDenBxzZ0ZwVEooOLafWgwA&s',
        'https://www.urlpublicidad5.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (3, 'PU2',
        'https://media.urgente24.com/p/8b8c7e91210db75522b55a2e09b67ac4/adjuntos/319/imagenes/002/664/0002664389/star-plus.png',
        'https://www.urlpublicidad6.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (1, 'PM3', 'https://www.globamaticmedia.com/wp-content/uploads/2023/02/logo-sony.jpg',
        'https://media.elpatagonico.com/p/2f9bc7b90864bb9a69ec3d41a3ac6509/adjuntos/193/imagenes/041/169/0041169897/770x0/smart/imagenpng.png',
        '2024-05-15', '2024-06-15'),
       (2, 'PB3',
        'https://media.elpatagonico.com/p/2f9bc7b90864bb9a69ec3d41a3ac6509/adjuntos/193/imagenes/041/169/0041169897/770x0/smart/imagenpng.png',
        'https://www.clubelterritorio.com.ar/img/comercios/hipermercado_libertad_sa/logo.jpg', '2024-05-15',
        '2024-06-15'),
       (3, 'PU3',
        'https://media.elpatagonico.com/p/2f9bc7b90864bb9a69ec3d41a3ac6509/adjuntos/193/imagenes/041/169/0041169897/770x0/smart/imagenpng.png',
        'https://sm.ign.com/ign_latam/tech/default/0a8gvr_37mb.jpg', '2024-05-15', '2024-06-15'),
       (1, 'PM4', 'https://cdn.pixabay.com/photo/2017/09/01/00/15/png-2702691_640.png',
        'https://sm.ign.com/ign_latam/tech/default/0a8gvr_37mb.jpg', '2024-05-15', '2024-06-15'),
       (2, 'PB4', 'https://cdn.urbantecno.com/urbantecno/2024/05/sin-tixxxxtulo.png',
        'https://sm.ign.com/ign_latam/tech/default/0a8gvr_37mb.jpg', '2024-05-15', '2024-06-15'),
       (3, 'PU4', 'https://cdn.pixabay.com/photo/2017/09/17/02/02/png-2757379_640.png',
        'https://sm.ign.com/ign_latam/tech/default/0a8gvr_37mb.jpg', '2024-05-15', '2024-06-15');
go

-- Crear 18 Publicidad_Tipo_Banner
INSERT INTO Publicidad_Tipo_Banner (id_tipo_banner, id_publicidad)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (1, 7),
       (2, 8),
       (3, 9),
       (4, 10),
       (5, 11),
       (6, 12);
go

-- Crear 4 Estado_Factura
INSERT INTO Estado_Factura (id_estado, descripcion)
VALUES (0, 'Creada'),
       (1, 'Finalizada'),
       (2, 'Enviada'),
       (-1, 'Cancelada');
go

-- Crear 4 Estado_Reporte
INSERT INTO Estado_Reporte (id_estado, descripcion)
VALUES (0, 'Creado'),
       (1, 'Finalizado'),
       (2, 'Enviado'),
       (-1, 'Cancelado');
go

-- Crear Clics para publicidades y catalogo
INSERT INTO Clic (id_cliente, id_publicidad, id_plataforma, id_contenido, fecha)
VALUES (1, 1, NULL, NULL, '2024-05-20 00:14:47.940'),
       (1, 2, NULL, NULL, '2024-05-03 00:14:47.940'),
       (2, 1, NULL, NULL, '2024-05-15 00:14:47.940'),
       (2, 1, NULL, NULL, '2024-04-01 00:14:47.940'),
       (2, 3, NULL, NULL, '2024-05-23 00:14:47.940'),
       (3, 4, NULL, NULL, '2024-05-10 00:14:47.940'),
       (4, 5, NULL, NULL, '2024-05-10 00:14:47.940'),
       (5, 6, NULL, NULL, '2024-05-10 00:14:47.940'),
       (5, 6, NULL, NULL, '2024-05-10 00:14:47.940'),
       (5, 7, NULL, NULL, '2024-05-10 00:14:47.940'),
       (6, NULL, 1, 'CP1', '2024-05-20 00:14:47.940'),
       (8, NULL, 4, 'CP4', '2024-05-01 00:14:47.940'),
       (8, NULL, 3, 'CS1', '2024-05-10 00:14:47.940'),
       (10, NULL, 2, 'CS3', '2024-05-10 00:14:47.940'),
       (10, NULL, 2, 'CS3', '2024-06-10 00:14:47.940'),
       (10, NULL, 2, 'CS3', '2024-05-10 00:14:50.940');
go
