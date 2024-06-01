USE Prime_Video;

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
       (N'contrasena10', 'usuario10@example.com', 'Carmen', N'Rodríguez', 1);
go

-- Crear partner
INSERT INTO Partner (nombre, token_de_servicio)
VALUES ('StreamingStudio', 'PrimeVideo123');
go

INSERT INTO Clasificacion(id_clasificacion, descripcion)
VALUES ('P', 'Pelicula'), ('S', 'Serie');
go

INSERT INTO Contenido (id_contenido, titulo, descripcion, url_imagen, clasificacion, reciente, destacado, valido)
VALUES ('CP12','The Avengers', 'descripcion de The Avengers',
        'https://cdn.marvel.com/content/1x/avengersendgame_lob_crd_05.jpg', 'P', 1, 1, 1),
       ('CP13','One Piece', 'El mejor anime',
        'https://m.media-amazon.com/images/S/pv-target-images/a0cb3885c44b8305ac89ba7ce98e8cd978bf3ebba6a151a00dbf2d528e98bf3b.jpg', 'S', 1, 1,1),
       ('CP14','Hunter x Hunter', 'Buen anime',
        'https://imgsrv.crunchyroll.com/cdn-cgi/image/fit=contain,format=auto,quality=85,width=480,height=720/catalog/crunchyroll/cbb55a6382682bf71e91f685c6473c5a.jpe', 'S', 1, 1, 1);;
go

INSERT INTO Director(nombre, apellido)
VALUES ('Nombre director 1', 'Apellido director 1');
go

INSERT INTO Actor(nombre, apellido)
VALUES ('Nombre Actor 1', 'Apellido Actor 1');
go

INSERT INTO Director_Contenido(id_contenido, id_director)
VALUES ('CP13',1),
       ('CP12',1),
       ('CP14',1);
go

INSERT INTO Actor_Contenido(id_contenido, id_actor)
VALUES ('CP13',1),
       ('CP12',1),
       ('CP14',1);
go
