USE Prime_Video;

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
VALUES ('StreamingStudio', 'PrimeVideo123');
go

INSERT INTO Clasificacion(id_clasificacion, descripcion)
VALUES ('P', 'Pelicula'),
       ('S', 'Serie');
go

INSERT INTO Contenido (id_contenido, titulo, descripcion, url_imagen, url_reproduccion, clasificacion, reciente,
                       destacado, valido)
VALUES ('CP3', 'Thor', N'Descripción de Thor', 'https://es.web.img3.acsta.net/medias/nmedia/18/79/89/52/19711203.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'P', 0, 0, 1),
       ('CS3', 'Kung fu Panda', N'Descripción de Kung fu Panda',
        'https://m.media-amazon.com/images/S/pv-target-images/d775fb8599018935d1e28ed9cbc2fb67870d726d850f0eee13dc6f50f37b76d3.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'S', 0, 1, 1),
       ('CP4', 'Drive', N'Descripción de Drive', 'https://i.blogs.es/d365ef/drive-2011/650_1200.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'P', 1, 0, 1),
       ('CS4', 'El padrino', N'Descripción de el padrino',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNvJucE9mgq6GvXtcJAPzaJ9huMajoCDRSHWgVfmp9Tg&s',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'S', 0, 1, 1),
       ('CP5', 'The Avengers', 'descripcion de The Avengers',
        'https://cdn.marvel.com/content/1x/avengersendgame_lob_crd_05.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'P', 1, 1, 1),
       ('CS5', 'One Piece', 'El mejor anime',
        'https://m.media-amazon.com/images/S/pv-target-images/a0cb3885c44b8305ac89ba7ce98e8cd978bf3ebba6a151a00dbf2d528e98bf3b.jpg',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'S', 0, 0, 1),
       ('CS6', 'Hunter x Hunter', 'Buen anime',
        'https://imgsrv.crunchyroll.com/cdn-cgi/image/fit=contain,format=auto,quality=85,width=480,height=720/catalog/crunchyroll/cbb55a6382682bf71e91f685c6473c5a.jpe',
        'https://www.youtube.com/watch?v=sfXPGuZ68HM', 'S', 1, 1, 1);;
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
VALUES ('CP3', 2),
       ('CS3', 3),
       ('CP4', 4),
       ('CS4', 5),
       ('CP5', 6),
       ('CS5', 7),
       ('CS5', 8),
       ('CS6', 9);
go

INSERT INTO Actor_Contenido (id_contenido, id_actor)
VALUES ('CP3', 7),
       ('CS3', 8),
       ('CP4', 1),
       ('CS4', 2),
       ('CP5', 6),
       ('CS5', 7),
       ('CS5', 8),
       ('CS6', 9);
go

INSERT INTO Genero (descripcion)
VALUES ('Drama'),
       ('Comedia'),
       ('Accion');
go

INSERT INTO Genero_Contenido (id_contenido, id_genero)
VALUES ('CP3', 2),
       ('CS3', 3),
       ('CS3', 1),
       ('CP4', 1),
       ('CS4', 2),
       ('CS4', 3),
       ('CP5', 1),
       ('CP5', 2),
       ('CS5', 2),
       ('CS5', 3),
       ('CS6', 1);
go
