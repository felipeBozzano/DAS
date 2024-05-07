USE StreamingStudio;

-- Crear 10 usuarios
INSERT INTO Cliente_Usuario (usuario, contrasena, email, nombre, apellido, valido)
VALUES ('usuario1', N'contrasena1', 'usuario1@example.com', 'Juan', N'Pérez', 1),
       ('usuario2', N'contrasena2', 'usuario2@example.com', N'María', N'Gómez', 1),
       ('usuario3', N'contrasena3', 'usuario3@example.com', 'Carlos', N'López', 1),
       ('usuario4', N'contrasena4', 'usuario4@example.com', 'Laura', N'Martínez', 1),
       ('usuario5', N'contrasena5', 'usuario5@example.com', 'Pedro', N'Hernández', 1),
       ('usuario6', N'contrasena6', 'usuario6@example.com', 'Ana', N'Sánchez', 1),
       ('usuario7', N'contrasena7', 'usuario7@example.com', 'Roberto', N'García', 1),
       ('usuario8', N'contrasena8', 'usuario8@example.com', 'Elena', N'Fernández', 1),
       ('usuario9', N'contrasena9', 'usuario9@example.com', 'Miguel', N'Díaz', 1),
       ('usuario10', N'contrasena10', 'usuario10@example.com', 'Carmen', N'Rodríguez', 1);
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
INSERT INTO Contenido (id_contenido, titulo, descripcion, url_imagen, clasificacion, mas_visto)
VALUES ('P-1','Pelicula1', N'Descripción de Pelicula1', 'url_imagen1.jpg', 2, 0),
       ('S-1','Serie1', N'Descripción de Serie1', 'url_imagen2.jpg', 1, 1),
       ('P-2','Pelicula2', N'Descripción de Pelicula2', 'url_imagen3.jpg', 2, 0),
       ('S-2','Serie2', N'Descripción de Serie2', 'url_imagen4.jpg', 1, 1),
       ('P-3','Pelicula3', N'Descripción de Pelicula3', 'url_imagen5.jpg', 2, 0),
       ('S-3','Serie3', N'Descripción de Serie3', 'url_imagen6.jpg', 1, 0),
       ('P-4','Pelicula4', N'Descripción de Pelicula4', 'url_imagen7.jpg', 2, 1),
       ('S-4','Serie4', N'Descripción de Serie4', 'url_imagen8.jpg', 1, 0);
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
INSERT INTO Plataforma_de_Streaming (nombre_de_fantasia, razón_social, url_imagen, token_de_servicio, url_api, valido)
VALUES ('Plataforma1', N'Razón Social 1', 'https://www.urlimagen1.com/imagenes/imagen.jpg', 'token1',
        'https://www.urlapi1.com', 1),
       ('Plataforma2', N'Razón Social 2', 'https://www.urlimagen2.com/imagenes/imagen.jpg', 'token2',
        'https://www.urlapi2.com', 1),
       ('Plataforma3', N'Razón Social 3', 'https://www.urlimagen3.com/imagenes/imagen.jpg', 'token3',
        'https://www.urlapi3.com', 1);
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

-- Crear 6 Banners

-- Crear 6 Tipo_Banner
INSERT INTO Tipo_Banner (fecha_alta, tamano, exclusividad, fecha_baja)
VALUES (GETDATE(), '10*20', 'E', '2024-06-10 00:14:47.770'),
       (GETDATE(), '30*40', 'NE', '2024-06-10 00:14:47.770'),
       (GETDATE(), '50*50', 'E', '2024-06-10 00:14:47.770'),
       (GETDATE(), '20*30', 'NE', '2024-06-10 00:14:47.770'),
       (GETDATE(), '10*20', 'E','2024-06-10 00:14:47.770'),
       (GETDATE(), '5*15', 'NE', '2024-06-10 00:14:47.770');
go

-- Crear 6 Costo_Banner
INSERT INTO Costo_Banner (id_tipo_banner, fecha_alta, costo, fecha_baja)
VALUES (1, GETDATE(), 10.5, '2024-06-10 00:14:47.770'),
       (2,GETDATE(),2, '2024-07-11 00:14:47.770'),
       (3,GETDATE(),3, '2024-08-10 00:14:47.770'),
       (4,GETDATE(),4, '2024-05-05 00:14:47.770'),
       (5,GETDATE(),5, '2024-05-20 00:14:47.770'),
       (6,GETDATE(),6, '2024-05-19 00:14:47.770');
go

-- Crear 3 Publicista
INSERT INTO Publicista (nombre_de_fantasia, razón_social, email, contrasena, token_de_servicio, url_api)
VALUES ('Publicista1', N'Razón Social 1', 'publicista1@email.com', N'contrasena1', 'token1', 'https://www.urlapi1.com'),
       ('Publicista2', N'Razón Social 2', 'publicista2@email.com', N'contrasena2', 'token2', 'https://www.urlapi2.com'),
       ('Publicista3', N'Razón Social 3', 'publicista3@email.com', N'contrasena3', 'token3', 'https://www.urlapi3.com');
go

-- Crear 9 Publicidades asignadas a Banners y Exclusividades
INSERT INTO Publicidad (id_publicista, codigo_publicidad, url_de_imagen, url_de_publicidad, fecha_de_alta,
                        fecha_de_baja)
VALUES (1, 'CP1', 'https://www.urlimagen1.com/imagenes/imagen.jpg', 'https://www.urlpublicidad1.com/publicidad.jpg',
        '2024-02-28', '2024-03-10'),
       (2, 'CP2', 'https://www.urlimagen2.com/imagenes/imagen.jpg', 'https://www.urlpublicidad2.com/publicidad.jpg',
        '2024-04-30', '2024-05-01'),
       (3, 'CP3', 'https://www.urlimagen3.com/imagenes/imagen.jpg', 'https://www.urlpublicidad3.com/publicidad.jpg',
        '2024-04-01', '2024-04-02'),
       (1, 'CP4', 'https://www.urlimagen4.com/imagenes/imagen.jpg', 'https://www.urlpublicidad4.com/publicidad.jpg',
        '2024-03-02', '2024-03-03'),
       (2, 'CP5', 'https://www.urlimagen5.com/imagenes/imagen.jpg', 'https://www.urlpublicidad5.com/publicidad.jpg',
        '2024-03-03', '2024-05-04'),
       (3, 'CP6', 'https://www.urlimagen6.com/imagenes/imagen.jpg', 'https://www.urlpublicidad6.com/publicidad.jpg',
        '2024-04-04', '2024-04-25'),
       (1,'CP7', 'https://www.urlimagen7.com/imagenes/imagen.jpg', 'https://www.urlpublicidad7.com/publicidad.jpg',
        '2024-03-05', '2024-04-01'),
       (2, 'CP8', 'https://www.urlimagen8.com/imagenes/imagen.jpg', 'https://www.urlpublicidad8.com/publicidad.jpg',
        '2024-03-06', '2024-03-27'),
       (3, 'CP9', 'https://www.urlimagen9.com/imagenes/imagen.jpg', 'https://www.urlpublicidad9.com/publicidad.jpg',
        '2024-04-07', '2024-05-18');
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
VALUES (1, 1, NULL, NULL, '2024-03-20 00:14:47.940'),
       (1, 2, NULL, NULL, '2024-03-03 00:14:47.940'),
       (2, 1, NULL, NULL, '2024-03-15 00:14:47.940'),
       (2, 1, NULL, NULL, '2024-02-01 00:14:47.940'),
       (2, 3, NULL, NULL, '2024-03-23 00:14:47.940'),
       (3, 4, NULL, NULL, '2024-03-10 00:14:47.940'),
       (4, 5, NULL, NULL, '2024-03-10 00:14:47.940'),
       (5, 6, NULL, NULL, '2024-03-10 00:14:47.940'),
       (5, 6, NULL, NULL, '2024-03-10 00:14:47.940'),
       (5, 7, NULL, NULL, '2024-03-10 00:14:47.940'),
       (6, NULL, 1, 'P-1', '2024-03-20 00:14:47.940'),
       (7, NULL, 1, 'P-2', '2024-03-03 00:14:47.940'),
       (7, NULL, 2, 'P-3', '2024-03-15 00:14:47.940'),
       (8, NULL, 2, 'P-4', '2024-02-01 00:14:47.940'),
       (8, NULL, 1, 'P-2', '2024-03-23 00:14:47.940'),
       (8, NULL, 3, 'S-1', '2024-03-10 00:14:47.940'),
       (9, NULL, 3, 'P-3', '2024-03-10 00:14:47.940'),
       (10, NULL, 3, 'S-3', '2024-03-10 00:14:47.940'),
       (10, NULL, 2, 'S-3', '2024-04-10 00:14:47.940'),
       (10, NULL, 2, 'S-3', '2024-03-10 00:14:47.940');
go
