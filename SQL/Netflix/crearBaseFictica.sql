USE Netflix;

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
VALUES ('StreamingStudio', 'Netflix123');
go

INSERT INTO Clasificacion(id_clasificacion, descripcion)
VALUES ('P', 'Pelicula'),
       ('S', 'Serie');
go

INSERT INTO Contenido (id_contenido, titulo, descripcion, url_imagen, url_reproduccion, clasificacion, reciente,
                       destacado, valido)
VALUES ('CP1', 'Toy Story', N'Descripcion de Toy Story',
        'https://es.web.img3.acsta.net/pictures/14/03/17/10/20/509771.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'P', 1, 0, 1),
       ('CS1', 'Spiderman', N'Descripción de Spiderman',
        'https://image.api.playstation.com/vulcan/ap/rnd/202009/3021/B2aUYFC0qUAkNnjbTHRyhrg3.png',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'S', 1, 0, 1),
       ('CP2', 'Batman', N'Descripción de Batman',
        'https://es.web.img2.acsta.net/medias/nmedia/18/66/74/01/20350623.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'P', 0, 1, 1),
       ('CS2', 'Superman', N'Descripción de Superman',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQtkFIzNYyYVW1MQg2PBUJun45u95dCWpWTaj-baRad6w&s',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'S', 1, 1, 1),
       ('CP5', 'The Avengers', 'descripcion de The Avengers',
        'https://cdn.marvel.com/content/1x/avengersendgame_lob_crd_05.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'P', 1, 1, 1);
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
       ('CP2', 5),
       ('CS2', 1),
       ('CP5', 6);
go

INSERT INTO Actor_Contenido (id_contenido, id_actor)
VALUES ('CP1', 1),
       ('CP1', 2),
       ('CS1', 3),
       ('CS1', 4),
       ('CP2', 5),
       ('CS2', 6),
       ('CP5', 6);
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
       ('CP2', 3),
       ('CP2', 2),
       ('CS2', 1),
       ('CP5', 1),
       ('CP5', 2);
go
