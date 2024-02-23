USE StreamingStudio;

-- Crear 10 usuarios
INSERT INTO dbo.Cliente_Usuario (usuario, contraseña, email, nombre, apellido, valido)
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
INSERT INTO Plataforma_de_Streaming (nombre_de_fantasia, razón_social, url_imagen, token_de_servicio, url_de_reportes,
                                     url_de_sesion, fee_de_federacion, fee_de_registro)
VALUES ('Plataforma1', N'Razón Social 1', 'url_imagen1.jpg', 'token1', 'url_reportes1', 'url_sesion1', 0.1, 0.05),
       ('Plataforma2', N'Razón Social 2', 'url_imagen2.jpg', 'token2', 'url_reportes2', 'url_sesion2', 0.12, 0.06),
       ('Plataforma3', N'Razón Social 3', 'url_imagen3.jpg', 'token3', 'url_reportes3', 'url_sesion3', 0.15, 0.07);
go

INSERT INTO Catalogo (id_contenido, id_plataforma, reciente, destacado, id_en_plataforma, fecha_de_alta, fecha_de_baja,
                      valido)
VALUES
    -- Plataforma 1
    (1, 1, 1, 1, 'P1_C1', GETDATE(), NULL, 1),
    (2, 1, 1, 0, 'S1_C2', GETDATE(), NULL, 1),
    (3, 1, 0, 1, 'P2_C1', GETDATE(), NULL, 1),
    (4, 1, 0, 0, 'S2_C2', GETDATE(), NULL, 1),
    -- Plataforma 2
    (5, 2, 1, 1, 'P3_C1', GETDATE(), NULL, 1),
    (6, 2, 1, 0, 'S3_C2', GETDATE(), NULL, 1),
    (7, 2, 0, 1, 'P4_C1', GETDATE(), NULL, 1),
    (8, 2, 0, 0, 'S4_C2', GETDATE(), NULL, 1),
    -- Plataforma 3
    (1, 3, 1, 1, 'P1_C3', GETDATE(), NULL, 1),
    (2, 3, 1, 0, 'S1_C4', GETDATE(), NULL, 1),
    (5, 3, 0, 1, 'P3_C1', GETDATE(), NULL, 1),
    (6, 3, 0, 0, 'S3_C2', GETDATE(), NULL, 1);
go

-- Insertar los tipos de usuario "Registrado" y "Nuevo"
INSERT INTO Tipo_Usuario (id_tipo_usuario, descripcion)
VALUES (1, N'Nuevo'),
       (2, N'Registrado');
go

-- Crear 6 Banners
INSERT INTO Banner (tamaño_de_banner, costo, descripcion)
VALUES ('20x20', 50.0, N'Descripción de Banner1'),
       ('15x30', 45.0, N'Descripción de Banner2'),
       ('20x20', 40.0, N'Descripción de Banner3'),
       ('15x30', 35.0, N'Descripción de Banner4'),
       ('20x20', 30.0, N'Descripción de Banner5'),
       ('15x30', 25.0, N'Descripción de Banner6');
go

INSERT INTO Exclusividad (grado_de_exclusividad, costo, descripcion)
VALUES (1, 25.0, 'Pagina_Principal'),
       (2, 30.0, 'En_Todas_Las_Paginas');
go

INSERT INTO Publicista (nombre_de_fantasia, razón_social, email, contraseña, token_de_servicio, url_de_reportes)
VALUES ('Publicista1', N'Razón Social 1', 'publicista1@email.com', N'contraseña1', 'token1', 'url_reportes1'),
       ('Publicista2', N'Razón Social 2', 'publicista2@email.com', N'contraseña2', 'token2', 'url_reportes2'),
       ('Publicista3', N'Razón Social 3', 'publicista3@email.com', N'contraseña3', 'token3', 'url_reportes3');
go

-- Crear 9 Publicidades asignadas a Banners y Exclusividades
INSERT INTO Publicidad (id_publicista, id_exclusividad, id_banner, codigo_publicidad, url_de_imagen, url_de_publicidad,
                        fecha_de_alta, fecha_de_baja)
VALUES (1, 1, 1, 'CP1', 'url_imagen_publicidad1.jpg', 'url_publicidad1', '2024-01-30', '2024-02-29'),
       (2, 2, 2, 'CP2', 'url_imagen_publicidad2.jpg', 'url_publicidad2', '2024-01-31', '2024-03-01'),
       (3, 1, 3, 'CP3', 'url_imagen_publicidad3.jpg', 'url_publicidad3', '2024-02-01', '2024-03-02'),
       (1, 2, 4, 'CP4', 'url_imagen_publicidad4.jpg', 'url_publicidad4', '2024-02-02', '2024-03-03'),
       (2, 1, 5, 'CP5', 'url_imagen_publicidad5.jpg', 'url_publicidad5', '2024-02-03', '2024-03-04'),
       (3, 2, 6, 'CP6', 'url_imagen_publicidad6.jpg', 'url_publicidad6', '2024-02-04', '2024-03-05'),
       (1, 1, 1, 'CP7', 'url_imagen_publicidad7.jpg', 'url_publicidad7', '2024-02-05', '2024-03-06'),
       (2, 2, 2, 'CP8', 'url_imagen_publicidad8.jpg', 'url_publicidad8', '2024-02-06', '2024-03-07'),
       (3, 1, 3, 'CP9', 'url_imagen_publicidad9.jpg', 'url_publicidad9', '2024-02-07', '2024-03-08');
go
