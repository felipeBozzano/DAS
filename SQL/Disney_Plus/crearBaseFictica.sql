USE Disney_Plus;

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
       (N'Contrasena1@', 'usuario10@example.com', 'Carmen', N'Rodríguez', 1);
go

-- Crear partner
INSERT INTO Partner (nombre, token_de_servicio)
VALUES ('StreamingStudio', 'DisneyPlus123');
go

INSERT INTO Clasificacion(id_clasificacion, descripcion)
VALUES ('P', 'Pelicula'),
       ('S', 'Serie');
go

INSERT INTO Contenido (id_contenido, titulo, descripcion, url_imagen, url_reproduccion, clasificacion, reciente,
                       destacado, valido)
VALUES ('CP1', 'Toy Story', 'Descripcion de Toy Story',
        'https://es.web.img3.acsta.net/pictures/14/03/17/10/20/509771.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'P', 0, 1, 1),
       ('CS1', 'Spiderman', 'Descripcion de Spiderman',
        'https://image.api.playstation.com/vulcan/ap/rnd/202009/3021/B2aUYFC0qUAkNnjbTHRyhrg3.png',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'S', 0, 0, 1),
       ('CP3', 'Thor', 'Descripcion de Thor', 'https://es.web.img3.acsta.net/medias/nmedia/18/79/89/52/19711203.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'P', 1, 0, 1),
       ('CS3', 'Kung fu Panda', 'Descripcion de Kung fu Panda',
        'https://m.media-amazon.com/images/S/pv-target-images/d775fb8599018935d1e28ed9cbc2fb67870d726d850f0eee13dc6f50f37b76d3.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'S', 1, 1, 1),
       ('CP5', 'The Avengers', 'Descripcion de The Avengers',
        'https://cdn.marvel.com/content/1x/avengersendgame_lob_crd_05.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'P', 1, 0, 1),
       ('CS7', 'Better Call Saul', 'Descripcion de Better Call Saul',
        'https://m.media-amazon.com/images/S/pv-target-images/fe1c0138b7b0e05ea22a711f44e57cd80cfbaea30745c425b7043d786ba66cd1.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'S', 0, 1, 1);
go

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

INSERT INTO Director_Contenido (id_contenido, id_director)
VALUES ('CP1', 1),
       ('CP1', 2),
       ('CS1', 3),
       ('CS1', 4),
       ('CP3', 2),
       ('CS3', 3),
       ('CP5', 6),
       ('CS7', 1),
       ('CS7', 8);
go

INSERT INTO Actor_Contenido (id_contenido, id_actor)
VALUES ('CP1', 1),
       ('CP1', 2),
       ('CS1', 3),
       ('CS1', 4),
       ('CP3', 7),
       ('CS3', 8),
       ('CP5', 6),
       ('CS7', 7),
       ('CS7', 9);
go

INSERT INTO Genero (descripcion)
VALUES ('Drama'),
       ('Comedia'),
       ('Accion');
go

INSERT INTO Genero_Contenido (id_contenido, id_genero)
VALUES ('CP1', 1),
       ('CP1', 3),
       ('CS1', 2),
       ('CP3', 2),
       ('CS3', 3),
       ('CS3', 1),
       ('CP5', 1),
       ('CP5', 2),
       ('CS7', 3);
go
