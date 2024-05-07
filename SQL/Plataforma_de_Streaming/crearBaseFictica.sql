USE Plataforma_de_Streaming;

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

-- Crear partner
INSERT INTO Partner (nombre, token_de_servicio)
VALUES ('StreamingStudio', '123456789');
go
