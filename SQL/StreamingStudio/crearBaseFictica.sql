USE StreamingStudio;

-- Crear 10 usuarios
INSERT INTO Cliente_Usuario (contrasena, email, nombre, apellido, valido)
VALUES (N'contrasena1', 'usuario1@example.com', 'Juan', N'Pérez', 1),
       (N'contrasena2', 'usuario2@example.com', N'María', N'Gómez', 1),
       (N'contrasena3', 'usuario3@example.com', 'Carlos', N'López', 1),
       (N'contrasena4', 'usuario4@example.com', 'Laura', N'Martínez', 1),
       (N'contrasena5', 'usuario5@example.com', 'Pedro', N'Hernández', 1),
       (N'contrasena6', 'usuario6@example.com', 'Ana', N'Sánchez', 1),
       (N'contrasena7', 'usuario7@example.com', 'Roberto', N'García', 1),
       (N'contrasena8', 'usuario8@example.com', 'Elena', N'Fernández', 1),
       (N'contrasena9', 'usuario9@example.com', 'Miguel', N'Díaz', 1),
       (N'contrasena10', 'usuario10@example.com', 'Carmen', N'Rodríguez', 1),
       (N'Contrasena1@', 'francisco1@gmail.com', 'Carmen', N'Rodríguez', 1);
go

-- Crear 3 géneros
INSERT INTO Genero (descripcion)
VALUES ('Drama'),
       ('Comedia'),
       (N'Acción');
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

-- Crear 8 actores
INSERT INTO Actor (nombre, apellido)
VALUES ('Actor1', 'Apellido1'),
       ('Actor2', 'Apellido2'),
       ('Actor3', 'Apellido3'),
       ('Actor4', 'Apellido4'),
       ('Actor5', 'Apellido5'),
       ('Actor6', 'Apellido6'),
       ('Actor7', 'Apellido7'),
       ('Actor8', 'Apellido8')
go

-- Crear 5 directores
INSERT INTO Director (nombre, apellido)
VALUES ('Director1', 'Apellido1'),
       ('Director2', 'Apellido2'),
       ('Director3', 'Apellido3'),
       ('Director4', 'Apellido4'),
       ('Director5', 'Apellido5');
go

-- Crear 8 contenidos
INSERT INTO Contenido (id_contenido, titulo, descripcion, url_imagen, clasificacion, mas_visto)
VALUES ('P-1', 'Toy Story', N'Descripcion de Toy Story', 'https://es.web.img3.acsta.net/pictures/14/03/17/10/20/509771.jpg', 'P', 0),
       ('S-1', 'Spiderman', N'Descripción de Spiderman', 'https://image.api.playstation.com/vulcan/ap/rnd/202009/3021/B2aUYFC0qUAkNnjbTHRyhrg3.png', 'S', 1),
       ('P-2', 'Batman', N'Descripción de Batman', 'https://es.web.img2.acsta.net/medias/nmedia/18/66/74/01/20350623.jpg', 'P', 0),
       ('S-2', 'Superman', N'Descripción de Superman', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQtkFIzNYyYVW1MQg2PBUJun45u95dCWpWTaj-baRad6w&s', 'S', 1),
       ('P-3', 'Thor', N'Descripción de Thor', 'https://es.web.img3.acsta.net/medias/nmedia/18/79/89/52/19711203.jpg', 'P', 0),
       ('S-3', 'Kung fu Panda', N'Descripción de Kung fu Panda', 'https://m.media-amazon.com/images/S/pv-target-images/d775fb8599018935d1e28ed9cbc2fb67870d726d850f0eee13dc6f50f37b76d3.jpg', 'S', 0),
       ('P-4', 'Drive', N'Descripción de Drive', 'https://i.blogs.es/d365ef/drive-2011/650_1200.jpg', 'P', 1),
       ('S-4', 'El padrino', N'Descripción de el padrino', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNvJucE9mgq6GvXtcJAPzaJ9huMajoCDRSHWgVfmp9Tg&s', 'S', 0);
go

-- Asignar directores a contenidos en Director_Contenido
INSERT INTO Director_Contenido (id_contenido, id_director)
VALUES ('P-1', 1),
       ('P-1', 2),
       ('S-1', 3),
       ('S-1', 4),
       ('P-2', 5),
       ('S-2', 1),
       ('P-3', 2),
       ('S-3', 3),
       ('P-4', 4),
       ('S-4', 5);
go

-- Asignar actores a contenidos en Actor_Contenido
INSERT INTO Actor_Contenido (id_contenido, id_actor)
VALUES ('P-1', 1),
       ('P-1', 2),
       ('S-1', 3),
       ('S-1', 4),
       ('P-2', 5),
       ('S-2', 6),
       ('P-3', 7),
       ('S-3', 8),
       ('P-4', 1),
       ('S-4', 2)
go

-- Asignar generos a contenidos en Genero_Contenido
INSERT INTO Genero_Contenido (id_contenido, id_genero)
VALUES ('P-1', 1),
       ('P-1', 3),
       ('S-1', 2),
       ('P-2', 3),
       ('P-2', 2),
       ('S-2', 1),
       ('P-3', 2),
       ('S-3', 3),
       ('S-3', 1),
       ('P-4', 1),
       ('S-4', 2),
       ('S-4', 3);
go

-- Crear 3 Plataformas de Streaming
INSERT INTO Plataforma_de_Streaming (nombre_de_fantasia, razon_social, url_imagen, token_de_servicio, url_api,
                                     protocolo_api, valido)
VALUES ('Netflix', N'Netflix Argentina S.R.L.',
        'https://images.ctfassets.net/4cd45et68cgf/4nBnsuPq03diC5eHXnQYx/d48a4664cdc48b6065b0be2d0c7bc388/Netflix-Logo.jpg', 'Netflix123', 'http://localhost:8081/netflix', 'REST', 1),
       ('Prime Video', N'Prime Video Argentina S.R.L.',
        'https://yt3.googleusercontent.com/pn_3JEt2nFaRA6dY08NzFM2w8A7NtUbaniamEnObxtLX3ZhT9w41KW0W0pjl-RiczGS0rgzZ=s900-c-k-c0x00ffffff-no-rj', 'PrimeVideo123', 'http://localhost:8082/prime_video', 'REST', 1),
       ('Star Plus', N'Star Plus S.R.L.',
        'https://media.ambito.com/p/050f05a32403fba3d95c8b783ccccee6/adjuntos/239/imagenes/039/297/0039297233/1200x675/smart/star-plusjpg.jpg', 'StarPlus123', 'http://localhost:8083/star_plus', 'SOAP', 1);
go

-- Llenar Catálogo
INSERT INTO Catalogo (id_contenido, id_plataforma, reciente, destacado, fecha_de_alta, fecha_de_baja)
VALUES
    -- Plataforma 1
    ('P-1', 1, 1, 1, '2024-03-10 00:14:47.770', NULL),
    ('S-1', 1, 1, 0, '2024-03-10 00:14:47.770', NULL),
    ('P-2', 1, 0, 1, '2024-03-10 00:14:47.770', NULL),
    ('S-2', 1, 0, 0, '2024-03-10 00:14:47.770', '2024-03-11 20:34:12'),
    -- Plataforma 2
    ('P-3', 2, 1, 1, '2024-03-10 00:14:47.770', NULL),
    ('S-3', 2, 1, 0, '2024-03-10 00:14:47.770', NULL),
    ('P-4', 2, 0, 1, '2024-03-10 00:14:47.770', NULL),
    ('S-4', 2, 0, 0, '2024-03-10 00:14:47.770', NULL),
    -- Plataforma 3
    ('P-1', 3, 1, 1, '2024-03-10 00:14:47.770', '2024-03-11 20:34:38'),
    ('S-1', 3, 1, 0, '2024-03-10 00:14:47.770', NULL),
    ('P-3', 3, 0, 1, '2024-03-10 00:14:47.770', NULL),
    ('S-3', 3, 0, 0, '2024-03-10 00:14:47.770', NULL);
go

-- Insertar los tipos de usuario "Registrado" y "Nuevo"

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
VALUES ('Publicista1', N'Razón Social 1', 'publicista1@email.com', N'contrasena1', '123456789',
        'https://www.urlapi1.com', 'REST'),
       ('Publicista2', N'Razón Social 2', 'publicista2@email.com', N'contrasena2', '123456789',
        'https://www.urlapi2.com', 'REST'),
       ('Publicista3', N'Razón Social 3', 'publicista3@email.com', N'contrasena3', '123456789',
        'https://www.urlapi3.com', 'REST');
go

-- Crear 9 Publicidades
INSERT INTO Publicidad (id_publicista, codigo_publicidad, url_de_imagen, url_de_publicidad, fecha_de_alta,
                        fecha_de_baja)
VALUES (1, 'CP1', 'https://play-lh.googleusercontent.com/a6rUmDehKQQmNxss8tRWIQQsymc6M6K0Dbyj-QidfcOEX7sXiiIyRM1gXMj8gcbrUA',
        'https://www.urlpublicidad1.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (2, 'CP2', 'https://inforges.es/wp-content/uploads/2022/07/migracion-aws.png',
        'https://www.urlpublicidad2.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (3, 'CP3', 'https://pbs.twimg.com/profile_images/1599827064282140672/MSxVR5u6_400x400.jpg',
        'https://www.urlpublicidad3.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (1, 'CP4', 'https://images.ctfassets.net/4cd45et68cgf/4nBnsuPq03diC5eHXnQYx/d48a4664cdc48b6065b0be2d0c7bc388/Netflix-Logo.jpg',
        'https://www.urlpublicidad4.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (2, 'CP5', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_qjqmDxaGwHDTmC0E_d4kDenBxzZ0ZwVEooOLafWgwA&s',
        'https://www.urlpublicidad5.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (3, 'CP6', 'https://media.urgente24.com/p/8b8c7e91210db75522b55a2e09b67ac4/adjuntos/319/imagenes/002/664/0002664389/star-plus.png',
        'https://www.urlpublicidad6.com/publicidad.jpg', '2024-05-15', '2024-06-15'),
       (1, 'CP7', 'https://www.globamaticmedia.com/wp-content/uploads/2023/02/logo-sony.jpg',
        'https://media.elpatagonico.com/p/2f9bc7b90864bb9a69ec3d41a3ac6509/adjuntos/193/imagenes/041/169/0041169897/770x0/smart/imagenpng.png', '2024-05-15', '2024-06-15'),
       (2, 'CP8', 'https://media.elpatagonico.com/p/2f9bc7b90864bb9a69ec3d41a3ac6509/adjuntos/193/imagenes/041/169/0041169897/770x0/smart/imagenpng.png',
        'https://www.clubelterritorio.com.ar/img/comercios/hipermercado_libertad_sa/logo.jpg', '2024-05-15', '2024-06-15'),
       (3, 'CP9', 'https://media.elpatagonico.com/p/2f9bc7b90864bb9a69ec3d41a3ac6509/adjuntos/193/imagenes/041/169/0041169897/770x0/smart/imagenpng.png',
        'https://sm.ign.com/ign_latam/tech/default/0a8gvr_37mb.jpg', '2024-05-15', '2024-06-15'),
       (1, 'CP10', 'https://cdn.pixabay.com/photo/2017/09/01/00/15/png-2702691_640.png',
        'https://sm.ign.com/ign_latam/tech/default/0a8gvr_37mb.jpg', '2024-05-15', '2024-06-15'),
       (2, 'CP11', 'https://cdn.urbantecno.com/urbantecno/2024/05/sin-tixxxxtulo.png',
        'https://sm.ign.com/ign_latam/tech/default/0a8gvr_37mb.jpg', '2024-05-15', '2024-06-15'),
       (3, 'CP12', 'https://cdn.pixabay.com/photo/2017/09/17/02/02/png-2757379_640.png',
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
VALUES (1, 1, NULL, NULL, '2024-04-20 00:14:47.940'),
       (1, 2, NULL, NULL, '2024-04-03 00:14:47.940'),
       (2, 1, NULL, NULL, '2024-04-15 00:14:47.940'),
       (2, 1, NULL, NULL, '2024-03-01 00:14:47.940'),
       (2, 3, NULL, NULL, '2024-04-23 00:14:47.940'),
       (3, 4, NULL, NULL, '2024-04-10 00:14:47.940'),
       (4, 5, NULL, NULL, '2024-04-10 00:14:47.940'),
       (5, 6, NULL, NULL, '2024-04-10 00:14:47.940'),
       (5, 6, NULL, NULL, '2024-04-10 00:14:47.940'),
       (5, 7, NULL, NULL, '2024-04-10 00:14:47.940'),
       (6, NULL, 1, 'P-1', '2024-04-20 00:14:47.940'),
       (7, NULL, 1, 'P-2', '2024-04-03 00:14:47.940'),
       (7, NULL, 2, 'P-3', '2024-04-15 00:14:47.940'),
       (8, NULL, 2, 'P-4', '2024-03-01 00:14:47.940'),
       (8, NULL, 1, 'P-2', '2024-04-23 00:14:47.940'),
       (8, NULL, 3, 'S-1', '2024-04-10 00:14:47.940'),
       (9, NULL, 3, 'P-3', '2024-04-10 00:14:47.940'),
       (10, NULL, 3, 'S-3', '2024-04-10 00:14:47.940'),
       (10, NULL, 2, 'S-3', '2024-05-10 00:14:47.940'),
       (10, NULL, 2, 'S-3', '2024-04-10 00:14:47.940');
go
