USE StreamingStudio;

-- Crear 10 usuarios
INSERT INTO Cliente_Usuario (usuario, contraseña, email, nombre, apellido, valido)
VALUES ('usuario1', N'contraseña1', 'usuario1@example.com', 'Juan', N'Pérez', 1),
       ('usuario2', N'contraseña2', 'usuario2@example.com', N'María', N'Gómez', 1),
       ('usuario3', N'contraseña3', 'usuario3@example.com', 'Carlos', N'López', 1),
       ('usuario4', N'contraseña4', 'usuario4@example.com', 'Laura', N'Martínez', 1),
       ('usuario5', N'contraseña5', 'usuario5@example.com', 'Pedro', N'Hernández', 1),
       ('usuario6', N'contraseña6', 'usuario6@example.com', 'Ana', N'Sánchez', 1),
       ('usuario7', N'contraseña7', 'usuario7@example.com', 'Roberto', N'García', 1),
       ('usuario8', N'contraseña8', 'usuario8@example.com', 'Elena', N'Fernández', 1),
       ('usuario9', N'contraseña9', 'usuario9@example.com', 'Miguel', N'Díaz', 1),
       ('usuario10', N'contraseña10', 'usuario10@example.com', 'Carmen', N'Rodríguez', 1);
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
INSERT INTO Clasificacion (descripcion)
VALUES ('Serie'),
       (N'Película');
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
INSERT INTO Contenido (titulo, descripcion, url_imagen, clasificacion, mas_visto)
VALUES ('Pelicula1', N'Descripción de Pelicula1', 'url_imagen1.jpg', 2, 0),
       ('Serie1', N'Descripción de Serie1', 'url_imagen2.jpg', 1, 1),
       ('Pelicula2', N'Descripción de Pelicula2', 'url_imagen3.jpg', 2, 0),
       ('Serie2', N'Descripción de Serie2', 'url_imagen4.jpg', 1, 1),
       ('Pelicula3', N'Descripción de Pelicula3', 'url_imagen5.jpg', 2, 0),
       ('Serie3', N'Descripción de Serie3', 'url_imagen6.jpg', 1, 0),
       ('Pelicula4', N'Descripción de Pelicula4', 'url_imagen7.jpg', 2, 1),
       ('Serie4', N'Descripción de Serie4', 'url_imagen8.jpg', 1, 0);
go

-- Asignar directores a contenidos en Director_Contenido
INSERT INTO Director_Contenido (id_contenido, id_director)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (4, 1),
       (5, 2),
       (6, 3),
       (7, 4),
       (8, 5);
go

-- Asignar actores a contenidos en Actor_Contenido
INSERT INTO Actor_Contenido (id_contenido, id_actor)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (4, 6),
       (5, 7),
       (6, 8),
       (7, 1),
       (8, 2)
go

-- Asignar generos a contenidos en Genero_Contenido
INSERT INTO Genero_Contenido (id_contenido, id_genero)
VALUES (1, 1),
       (1, 3),
       (2, 2),
       (3, 3),
       (3, 2),
       (4, 1),
       (5, 2),
       (6, 3),
       (6, 1),
       (7, 1),
       (8, 2),
       (8, 3);
go

-- Crear 3 Plataformas de Streaming
INSERT INTO Plataforma_de_Streaming (nombre_de_fantasia, razón_social, url_imagen, token_de_servicio, url_api, valido)
VALUES ('Plataforma1', N'Razón Social 1', 'https://www.urlimagen1.com/imagenes/imagen.jpg', 'token1', 'https://www.urlapi1.com', 1),
       ('Plataforma2', N'Razón Social 2', 'https://www.urlimagen2.com/imagenes/imagen.jpg', 'token2', 'https://www.urlapi2.com', 1),
       ('Plataforma3', N'Razón Social 3', 'https://www.urlimagen3.com/imagenes/imagen.jpg', 'token3', 'https://www.urlapi3.com', 1);
go

-- Llenar Catálogo
INSERT INTO Catalogo (id_contenido, id_plataforma, reciente, destacado, id_en_plataforma, fecha_de_alta, fecha_de_baja)
VALUES
    -- Plataforma 1
    (1, 1, 1, 1, 'P1_C1', GETDATE(), NULL),
    (2, 1, 1, 0, 'S1_C2', GETDATE(), NULL),
    (3, 1, 0, 1, 'P2_C1', GETDATE(), NULL),
    (4, 1, 0, 0, 'S2_C2', GETDATE(), NULL),
    -- Plataforma 2
    (5, 2, 1, 1, 'P2_C1', GETDATE(), NULL),
    (6, 2, 1, 0, 'S3_C2', GETDATE(), NULL),
    (7, 2, 0, 1, 'P4_C1', GETDATE(), NULL),
    (8, 2, 0, 0, 'S4_C2', GETDATE(), NULL),
    -- Plataforma 3
    (1, 3, 1, 1, 'P1_C3', GETDATE(), NULL),
    (2, 3, 1, 0, 'S1_C4', GETDATE(), NULL),
    (5, 3, 0, 1, 'P3_C1', GETDATE(), NULL),
    (6, 3, 0, 0, 'S3_C2', GETDATE(), NULL);
go

-- Insertar los tipos de usuario "Registrado" y "Nuevo"
INSERT INTO Tipo_Usuario (id_tipo_usuario, descripcion)
VALUES (1, N'Nuevo'),
       (2, N'Registrado');
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

-- Crear 6 Banners
INSERT INTO Banner (tamaño_de_banner, descripcion)
VALUES ('20x20', N'Banner Arriba Izquierda 20x20 Exclusivo'),
       ('15x30', N'Banner Arriba Derecha 15x30 Exclusivo'),
       ('10x20', N'Banner Arriba Derecha 15x30 NO Exclusivo'),
       ('20x30', N'Banner Abajo Derecha 20x30 NO Exclusivo'),
       ('20x20', N'Banner Arriba Izquierda 20x20 NO Exclusivo'),
       ('15x30', N'Banner Arriba Derecha 15x30 NO Exclusivo')
go

-- Crear 6 Tipo_Banner
INSERT INTO Tipo_Banner (costo, exclusividad, fecha_alta, fecha_baja, descripcion)
VALUES (23.5, 1, GETDATE(), NULL, 'Banner Arriba Izquierda Exclusivo'),
       (30.0, 1, GETDATE(), NULL, 'Banner Arriba Derecha Exclusivo'),
       (15.5, 0, GETDATE(), NULL, 'Banner Arriba Izquierda NO Exclusivo'),
       (20.0, 0, GETDATE(), NULL, 'Banner Arriba Derecha NO Exclusivo'),
       (10.5, 0, GETDATE(), NULL, 'Banner Abajo Izquierda NO Exclusivo'),
       (15.0, 0, GETDATE(), NULL, 'Banner Abajo Derecha NO Exclusivo');
go

-- Crear 6 Costo_Banner
INSERT INTO Costo_Banner (id_tipo_banner, id_banner)
VALUES (1, 1),
       (3, 1),
       (2, 2),
       (4, 2),
       (5, 3),
       (6, 4);
go

-- Crear 3 Publicista
INSERT INTO Publicista (nombre_de_fantasia, razón_social, email, contraseña, token_de_servicio, url_api)
VALUES ('Publicista1', N'Razón Social 1', 'publicista1@email.com', N'contraseña1', 'token1', 'https://www.urlapi1.com'),
       ('Publicista2', N'Razón Social 2', 'publicista2@email.com', N'contraseña2', 'token2', 'https://www.urlapi2.com'),
       ('Publicista3', N'Razón Social 3', 'publicista3@email.com', N'contraseña3', 'token3', 'https://www.urlapi3.com');
go

-- Crear 9 Publicidades asignadas a Banners y Exclusividades
INSERT INTO Publicidad (id_publicista, id_banner, codigo_publicidad, url_de_imagen, url_de_publicidad, fecha_de_alta,
                        fecha_de_baja)
VALUES (1, 1, 'CP1', 'https://www.urlimagen1.com/imagenes/imagen.jpg', 'https://www.urlpublicidad1.com/publicidad.jpg', '2024-01-30', '2024-02-10'),
       (2, 2, 'CP2', 'https://www.urlimagen2.com/imagenes/imagen.jpg', 'https://www.urlpublicidad2.com/publicidad.jpg', '2024-03-31', '2024-04-01'),
       (3, 3, 'CP3', 'https://www.urlimagen3.com/imagenes/imagen.jpg', 'https://www.urlpublicidad3.com/publicidad.jpg', '2024-03-01', '2024-03-02'),
       (1, 4, 'CP4', 'https://www.urlimagen4.com/imagenes/imagen.jpg', 'https://www.urlpublicidad4.com/publicidad.jpg', '2024-02-02', '2024-03-03'),
       (2, 1, 'CP5', 'https://www.urlimagen5.com/imagenes/imagen.jpg', 'https://www.urlpublicidad5.com/publicidad.jpg', '2024-02-03', '2024-04-04'),
       (3, 2, 'CP6', 'https://www.urlimagen6.com/imagenes/imagen.jpg', 'https://www.urlpublicidad6.com/publicidad.jpg', '2024-03-04', '2024-03-25'),
       (1, 3, 'CP7', 'https://www.urlimagen7.com/imagenes/imagen.jpg', 'https://www.urlpublicidad7.com/publicidad.jpg', '2024-02-05', '2024-03-01'),
       (2, 4, 'CP8', 'https://www.urlimagen8.com/imagenes/imagen.jpg', 'https://www.urlpublicidad8.com/publicidad.jpg', '2024-02-06', '2024-02-27'),
       (3, 1, 'CP9', 'https://www.urlimagen9.com/imagenes/imagen.jpg', 'https://www.urlpublicidad9.com/publicidad.jpg', '2024-02-07', '2024-02-18');
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
VALUES (1, 1, NULL, NULL, '2024-02-20 00:14:47.940'),
       (1, 2, NULL, NULL, '2024-02-03 00:14:47.940'),
       (2, 1, NULL, NULL, '2024-02-15 00:14:47.940'),
       (2, 1, NULL, NULL, '2024-01-01 00:14:47.940'),
       (2, 3, NULL, NULL, '2024-02-23 00:14:47.940'),
       (3, 4, NULL, NULL, '2024-02-10 00:14:47.940'),
       (4, 5, NULL, NULL, '2024-02-10 00:14:47.940'),
       (5, 6, NULL, NULL, '2024-02-10 00:14:47.940'),
       (5, 6, NULL, NULL, '2024-03-10 00:14:47.940'),
       (5, 7, NULL, NULL, '2024-02-10 00:14:47.940'),
       (6, NULL, 1, 1, '2024-02-20 00:14:47.940'),
       (7, NULL, 1, 3, '2024-02-03 00:14:47.940'),
       (7, NULL, 2, 5, '2024-02-15 00:14:47.940'),
       (8, NULL, 2, 7, '2024-01-01 00:14:47.940'),
       (8, NULL, 1, 3, '2024-02-23 00:14:47.940'),
       (8, NULL, 3, 2, '2024-02-10 00:14:47.940'),
       (9, NULL, 3, 5, '2024-02-10 00:14:47.940'),
       (10, NULL, 3, 6, '2024-02-10 00:14:47.940'),
       (10, NULL, 2, 6, '2024-03-10 00:14:47.940'),
       (10, NULL, 2, 6, '2024-02-10 00:14:47.940');
go
