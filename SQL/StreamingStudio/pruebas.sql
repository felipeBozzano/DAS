USE StreamingStudio;

/*
    PROBAMOS LOS FLUJOS DE FEDERACION Y DESVINCULACION
    -) FEDERACION Y DESVINCULACION
    -) FEDERACION Y DAR DE BAJA USUARIO
    -) FEDERACION Y DAR DE BAJA PLATAFORMA DE STREAMING
 */

/* FEDERAR CLIENTE 1 CON PLATAFORMA 1 */
EXEC Buscar_Federacion @id_plataforma = 1, @id_cliente = 1;
EXEC Verificar_Federacion_en_Curso @id_plataforma = 1, @id_cliente = 1;
EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 1;
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo1_Cliente1_Plataforma1",
     @tipo_transaccion = 'L', @url_login_registro_plataforma = 'https://www.plataforma1.com/registro',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @token = "Token1_Cliente1_Plataforma1";

/* DESVINCULAR FEDERACION DE CLIENTE 1 CON PLATAFORMA 1 */
EXEC Desvincular_Federacion @id_plataforma = 1, @id_cliente = 1;

/* ------------------------------------------------------------------------------------------------------------------ */

/* FEDERAR NUEVAMENTE CLIENTE 1 CON PLATAFORMA 1 */
EXEC Buscar_Federacion @id_plataforma = 1, @id_cliente = 1;
EXEC Verificar_Federacion_en_Curso @id_plataforma = 1, @id_cliente = 1;
EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 1;
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo2_Cliente1_Plataforma1",
     @tipo_transaccion = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @token = "Token2_Cliente1_Plataforma1";

/* FEDERAR CLIENTE 1 CON PLATAFORMA 2 */
EXEC Buscar_Federacion @id_plataforma = 2, @id_cliente = 1;
EXEC Verificar_Federacion_en_Curso @id_plataforma = 2, @id_cliente = 1;
EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 2;
EXEC Comenzar_Federacion @id_plataforma = 2, @id_cliente = 1, @codigo_de_transaccion = "Codigo1_Cliente1_Plataforma2",
     @tipo_transaccion = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 2, @id_cliente = 1, @token = "Token1_Cliente1_Plataforma2";

/* DAR DE BAJA CLIENTE 1 */
EXEC Eliminar_Usuario @id_cliente = 1;

/* REACTIVAR CLIENTE 1 */
EXEC Validar_Usuario @id_cliente = 1;

/* ------------------------------------------------------------------------------------------------------------------ */

/* FEDERAR NUEVAMENTE CLIENTE 1 CON PLATAFORMA 1 */
EXEC Buscar_Federacion @id_plataforma = 1, @id_cliente = 1;
EXEC Verificar_Federacion_en_Curso @id_plataforma = 1, @id_cliente = 1;
EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 1;
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo3_Cliente1_Plataforma1",
     @tipo_transaccion = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @token = "Token3_Cliente1_Plataforma1";

/* FEDERAR CLIENTE 2 CON PLATAFORMA 1 */
EXEC Buscar_Federacion @id_plataforma = 1, @id_cliente = 2;
EXEC Verificar_Federacion_en_Curso @id_plataforma = 1, @id_cliente = 2;
EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 1;
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 2, @codigo_de_transaccion = "Codigo1_Cliente2_Plataforma1",
     @tipo_transaccion = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 2, @token = "Token1_Cliente2_Plataforma1";

/* DAR DE BAJA PLATAFORMA 1 */
EXEC Dar_de_Baja_Plataforma_de_Streaming @id_plataforma = 1;

/* REACTIVAR PLATAFORMA 1 */
EXEC Reactivar_Plataforma_de_Streaming @id_plataforma = 1;

/* LIMPIAR TABLA TRANSACCION */
DELETE
FROM Transaccion
WHERE id_plataforma IS NOT NULL;

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

/*
    PROBAMOS EL FLUJO DE FACTURACIÓN DE FEDERACIONES
    -) FEDERACION, FACTURACION Y NUEVA FEDERACION
    -) FACTURACIÓN DE PUBLICIDADES
 */

/* FEDERAR CLIENTE 1 CON PLATAFORMA 1 */
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo1_Cliente1_Plataforma1",
     @tipo_transaccion = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @token = "Token1_Cliente1_Plataforma1";

/* FEDERAR CLIENTE 1 CON PLATAFORMA 2 */
EXEC Comenzar_Federacion @id_plataforma = 2, @id_cliente = 1, @codigo_de_transaccion = "Codigo1_Cliente1_Plataforma2",
     @tipo_transaccion = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 2, @id_cliente = 1, @token = "Token1_Cliente1_Plataforma2";

/* FEDERAR CLIENTE 2 CON PLATAFORMA 1 */
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 2, @codigo_de_transaccion = "Codigo1_Cliente2_Plataforma1",
     @tipo_transaccion = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 2, @token = "Token1_Cliente2_Plataforma1";

/* FLUJO DE FACTURACION */
EXEC Obtener_Datos_de_Federaciones;

EXEC Crear_Factura_Plataforma @id_plataforma = 1;
EXEC Obtener_Fees_de_Plataforma @id_plataforma = 1;
EXEC Crear_Detalle_Factura @id_factura = 3, @precio_unitario = 2.5, @cantidad = 2, @subtotal = 5.0,
     @descripcion = 'CLIENTES NUEVOS';
EXEC Crear_Detalle_Factura @id_factura = 3, @precio_unitario = 1.0, @cantidad = 1, @subtotal = -1.0,
     @descripcion = 'CLIENTES REGISTRADOS';
EXEC Finalizar_Factura @id_factura = 3, @total = 4.0;
EXEC Obtener_Datos_de_Plataforma @id_plataforma = 1;
EXEC Enviar_Factura @id_factura = 3;
EXEC Facturar_Federacion @id_plataforma = 1;

EXEC Crear_Factura_Plataforma @id_plataforma = 2;
EXEC Obtener_Fees_de_Plataforma @id_plataforma = 2;
EXEC Crear_Detalle_Factura @id_factura = 4, @precio_unitario = 1.5, @cantidad = 1, @subtotal = 1.5,
     @descripcion = 'CLIENTES NUEVOS';
EXEC Crear_Detalle_Factura @id_factura = 4, @precio_unitario = 2.0, @cantidad = 2, @subtotal = -4.0,
     @descripcion = 'CLIENTES REGISTRADOS';
EXEC Finalizar_Factura @id_factura = 4, @total = -2.5;
EXEC Obtener_Datos_de_Plataforma @id_plataforma = 2;
EXEC Enviar_Factura @id_factura = 4;
EXEC Facturar_Federacion @id_plataforma = 2;

/* DESVINCULAR FEDERACION DE CLIENTE 1 CON PLATAFORMA 1 */
EXEC Desvincular_Federacion @id_plataforma = 1, @id_cliente = 1;

/* DESVINCULAR FEDERACION DE CLIENTE 2 CON PLATAFORMA 1 */
EXEC Desvincular_Federacion @id_plataforma = 1, @id_cliente = 2;

/* FEDERAR NUEVAMENTE CLIENTE 1 CON PLATAFORMA 1 */
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo2_Cliente1_Plataforma1",
     @tipo_transaccion = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @token = "Token2_Cliente1_Plataforma1";

/* FEDERAR NUEVAMENTE CLIENTE 2 CON PLATAFORMA 1 */
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 2, @codigo_de_transaccion = "Codigo2_Cliente2_Plataforma1",
     @tipo_transaccion = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 2, @token = "Token2_Cliente2_Plataforma1";

/* ------------------------------------------------------------------------------------------------------------------ */

EXEC Obtener_Datos_de_Publicidades;

EXEC Crear_Factura_Publicista @id_publicista = 1;
EXEC Obtener_Costo_de_Banner @id_banner = 1;
EXEC Obtener_Costo_de_Banner @id_banner = 4;
EXEC Obtener_Costo_de_Banner @id_banner = 3;
EXEC Crear_Detalle_Factura @id_factura = 1002, @precio_unitario = 23.5, @cantidad = 9, @subtotal = 211.5,
     @descripcion = 'PUBLICIDAD CP1 - DESDE 01/02/2024 HASTA 10/02/2024';
EXEC Crear_Detalle_Factura @id_factura = 1002, @precio_unitario = 15.0, @cantidad = 27, @subtotal = 405.0,
     @descripcion = 'PUBLICIDAD CP4 - DESDE 02/02/2024 HASTA 29/02/2024';
EXEC Crear_Detalle_Factura @id_factura = 1002, @precio_unitario = 10.5, @cantidad = 24, @subtotal = 252.0,
     @descripcion = 'PUBLICIDAD CP7 - DESDE 05/02/2024 HASTA 29/02/2024';
EXEC Finalizar_Factura @id_factura = 1002, @total = 868.5;
EXEC Obtener_Datos_de_Publicista @id_publicista = 1;
EXEC Enviar_Factura @id_factura = 1002;

EXEC Crear_Factura_Publicista @id_publicista = 2;
EXEC Obtener_Costo_de_Banner @id_banner = 1;
EXEC Obtener_Costo_de_Banner @id_banner = 4;
EXEC Crear_Detalle_Factura @id_factura = 1003, @precio_unitario = 23.5, @cantidad = 26, @subtotal = 611.0,
     @descripcion = 'PUBLICIDAD CP5 - DESDE 03/02/2024 HASTA 29/02/2024';
EXEC Crear_Detalle_Factura @id_factura = 1003, @precio_unitario = 15.0, @cantidad = 21, @subtotal = 315.0,
     @descripcion = 'PUBLICIDAD CP8 - DESDE 06/02/2024 HASTA 27/02/2024';
EXEC Finalizar_Factura @id_factura = 1003, @total = 926;
EXEC Obtener_Datos_de_Publicista @id_publicista = 2;
EXEC Cancelar_Factura @id_factura = 1003;

EXEC Crear_Factura_Publicista @id_publicista = 3;
EXEC Obtener_Costo_de_Banner @id_banner = 1;
EXEC Crear_Detalle_Factura @id_factura = 1004, @precio_unitario = 23.5, @cantidad = 11, @subtotal = 258.5,
     @descripcion = 'PUBLICIDAD CP9 - DESDE 07/02/2024 HASTA 18/02/2024';
EXEC Finalizar_Factura @id_factura = 1002, @total = 868.5;
EXEC Obtener_Datos_de_Publicista @id_publicista = 1;
EXEC Enviar_Factura @id_factura = 1002;
EXEC Finalizar_Factura @id_factura = 1004, @total = 258.5;
EXEC Obtener_Datos_de_Publicista @id_publicista = 3;
EXEC Enviar_Factura @id_factura = 1004;

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

/*
    PROBAMOS EL FLUJO DE REPORTES
    -) PLATAFORMAS DE STREAMING
    -) PUBLICISTAS
 */

EXEC Obtener_Estadisticas_para_Plataformas;

EXEC Crear_Reporte_Plataforma @id_plataforma = 1;
EXEC Crear_Detalle_Reporte @id_reporte = 1003, @descripcion = 'CONTENIDO P1_C1', @cantidad_de_clics = 1;
EXEC Crear_Detalle_Reporte @id_reporte = 1003, @descripcion = 'CONTENIDO P2_C1', @cantidad_de_clics = 2;
EXEC Finalizar_Reporte @id_reporte = 1003, @total = 3;
EXEC Obtener_Datos_de_Plataforma @id_plataforma = 1;
EXEC Enviar_Reporte @id_reporte = 1003;

EXEC Crear_Reporte_Plataforma @id_plataforma = 2;
EXEC Crear_Detalle_Reporte @id_reporte = 1004, @descripcion = 'CONTENIDO P3_C1', @cantidad_de_clics = 1;
EXEC Crear_Detalle_Reporte @id_reporte = 1004, @descripcion = 'CONTENIDO S3_C2', @cantidad_de_clics = 1;
EXEC Finalizar_Reporte @id_reporte = 1004, @total = 2;
EXEC Obtener_Datos_de_Plataforma @id_plataforma = 2;
EXEC Cancelar_Reporte @id_reporte = 1004;

EXEC Crear_Reporte_Plataforma @id_plataforma = 3;
EXEC Crear_Detalle_Reporte @id_reporte = 1007, @descripcion = 'CONTENIDO S1_C4', @cantidad_de_clics = 1;
EXEC Crear_Detalle_Reporte @id_reporte = 1007, @descripcion = 'CONTENIDO P3_C1', @cantidad_de_clics = 1;
EXEC Crear_Detalle_Reporte @id_reporte = 1007, @descripcion = 'CONTENIDO S3_C2', @cantidad_de_clics = 1;
EXEC Finalizar_Reporte @id_reporte = 1007, @total = 3;
EXEC Obtener_Datos_de_Plataforma @id_plataforma = 3;
EXEC Enviar_Reporte @id_reporte = 1007;

/* ------------------------------------------------------------------------------------------------------------------ */

EXEC Obtener_Estadisticas_para_Publicistas;

EXEC Crear_Reporte_Publicista @id_publicista = 1;
EXEC Crear_Detalle_Reporte @id_reporte = 1008, @descripcion = 'CODIGO DE PUBLICIDAD CP1', @cantidad_de_clics = 2;
EXEC Crear_Detalle_Reporte @id_reporte = 1008, @descripcion = 'CODIGO DE PUBLICIDAD CP4', @cantidad_de_clics = 1;
EXEC Crear_Detalle_Reporte @id_reporte = 1008, @descripcion = 'CODIGO DE PUBLICIDAD CP7', @cantidad_de_clics = 1;
EXEC Finalizar_Reporte @id_reporte = 1008, @total = 4;
EXEC Obtener_Datos_de_Publicista @id_publicista = 1;
EXEC Enviar_Reporte @id_reporte = 1008;

EXEC Crear_Reporte_Publicista @id_publicista = 2;
EXEC Crear_Detalle_Reporte @id_reporte = 1009, @descripcion = 'CODIGO DE PUBLICIDAD CP2', @cantidad_de_clics = 1;
EXEC Crear_Detalle_Reporte @id_reporte = 1009, @descripcion = 'CODIGO DE PUBLICIDAD CP5', @cantidad_de_clics = 1;
EXEC Finalizar_Reporte @id_reporte = 1009, @total = 2;
EXEC Obtener_Datos_de_Publicista @id_publicista = 2;
EXEC Cancelar_Reporte @id_reporte = 1009;

EXEC Crear_Reporte_Publicista @id_publicista = 3;
EXEC Crear_Detalle_Reporte @id_reporte = 1010, @descripcion = 'CODIGO DE PUBLICIDAD CP3', @cantidad_de_clics = 1;
EXEC Crear_Detalle_Reporte @id_reporte = 1010, @descripcion = 'CODIGO DE PUBLICIDAD CP6', @cantidad_de_clics = 1;
EXEC Finalizar_Reporte @id_reporte = 1010, @total = 2;
EXEC Obtener_Datos_de_Publicista @id_publicista = 3;
EXEC Enviar_Reporte @id_reporte = 1010;

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

/*
    PROBAMOS EL FLUJO DE FEDERACIONES PENDIENTES
 */

/* INICIAR FEDERACION DE CLIENTE 3 CON PLATAFORMA 3 */
EXEC Comenzar_Federacion @id_plataforma = 3, @id_cliente = 3, @codigo_de_transaccion = "Codigo1_Cliente3_Plataforma3",
     @tipo_transaccion = 2, @url_login_registro_plataforma = 'https://www.plataforma3.com/login',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';

/* FEDERAR CLIENTE 4 CON PLATAFORMA 1 */
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 4, @codigo_de_transaccion = "Codigo1_Cliente4_Plataforma1",
     @tipo_transaccion = 2, @url_login_registro_plataforma = 'https://www.plataforma3.com/login',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';

/* FEDERAR CLIENTE 4 CON PLATAFORMA 2 */
EXEC Comenzar_Federacion @id_plataforma = 2, @id_cliente = 4, @codigo_de_transaccion = "Codigo1_Cliente4_Plataforma2",
     @tipo_transaccion = 2, @url_login_registro_plataforma = 'https://www.plataforma3.com/login',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 2, @id_cliente = 4, @token = "Token1_Cliente4_Plataforma2";

/* INICIAR FEDERACION DE CLIENTE 5 CON PLATAFORMA 2 */
EXEC Comenzar_Federacion @id_plataforma = 2, @id_cliente = 5, @codigo_de_transaccion = "Codigo1_Cliente5_Plataforma2",
     @tipo_transaccion = 1, @url_login_registro_plataforma = 'https://www.plataforma3.com/registro',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';

/* INICIAR FEDERACION DE  CLIENTE 5 CON PLATAFORMA 1 */
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 5, @codigo_de_transaccion = "Codigo1_Cliente5_Plataforma1",
     @tipo_transaccion = 2, @url_login_registro_plataforma = 'https://www.plataforma3.com/login',
     @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';

EXEC Consultar_Federaciones_Pendientes;

EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 1;
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 4, @token = 'Token1_Cliente4_Plataforma1';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 5, @token = 'Token1_Cliente5_Plataforma1';

EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 2;
EXEC Finalizar_Federacion @id_plataforma = 2, @id_cliente = 5, @token = 'Token1_Cliente5_Plataforma2';

EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 3;
EXEC Finalizar_Federacion @id_plataforma = 3, @id_cliente = 3, @token = 'Token1_Cliente3_Plataforma3';

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

/*
    PROBAMOS EL FLUJO DE ACTUALIZACIÓN DE CATÁLOGO
 */

EXEC Obtener_Catalogo_Actual;
EXEC Obtener_Plataformas_de_Streaming_Activas;

EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 1;
EXEC Dar_de_Baja_Item_en_Catalogo @id_contenido = 1, @id_plataforma = 1;
EXEC Activar_Item_en_Catalogo @id_contenido = 1, @id_plataforma = 3;

EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 2;
EXEC Dar_de_Baja_Item_en_Catalogo @id_contenido = 7, @id_plataforma = 2;

EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 3;

EXEC Crear_Contenido @titulo = 'Pelicula5', @descripcion = 'Descripcion de Pelicula5', @url_imagen = 'url_imagen9.jpg',
     @clasificacion = 2;
EXEC Crear_Contenido @titulo = 'Serie5', @descripcion = 'Descripcion de Serie5', @url_imagen = 'url_imagen10.jpg',
     @clasificacion = 1;

EXEC Agregar_Item_al_Catalogo @id_contenido = 1002, @id_plataforma = 1, @reciente = 1, @destacado = 0,
     @id_en_plataforma = 'P3_C1', @fecha_de_alta = '2024-03-12 21:14:47.770';
EXEC Agregar_Item_al_Catalogo @id_contenido = 1003, @id_plataforma = 2, @reciente = 1, @destacado = 1,
     @id_en_plataforma = 'P3_C2', @fecha_de_alta = '2024-03-12 21:14:47.770';
EXEC Agregar_Item_al_Catalogo @id_contenido = 1003, @id_plataforma = 1, @reciente = 0, @destacado = 0,
     @id_en_plataforma = 'S3_C1', @fecha_de_alta = '2024-03-12 21:14:47.770';

EXEC Actualizar_Catalogo @id_contenido = 4, @id_plataforma = 1, @reciente = 0, @destacado = 1;

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

/*
    PROBAMOS EL FLUJO DE OBTENCION DE DATOS DE PUBLICIDADES
 */

EXEC Obtener_Datos_de_Publicista @id_publicista = 1;
EXEC Obtener_Datos_de_Publicista @id_publicista = 2;
EXEC Obtener_Datos_de_Publicista @id_publicista = 3;

EXEC Obtener_Datos_de_Publicidades_Activas;

EXEC Actualizar_Publicidades @id_publicidad = 5, @url_de_imagen = 'https://www.urlimagen5.com/imagenes/nuevaImagen.jpg',
     @url_de_publicidad = 'https://www.urlpublicidad5.com/nuevaPublicidad.jpg';

/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

/*
    PROBAMOS EL FLUJO DE ACTUALIZACION DE CONTENIDO MAS VISTO EN STREAMING STUDIO
 */

EXEC Obtener_Contenido_mas_Visto_Actual;

EXEC Obtener_Contenido_mas_Visto_del_Mes_Anterior;

EXEC Actualizar_a_Contenido_mas_Visto @id_contenido = 6;
EXEC Actualizar_a_Contenido_mas_Visto @id_contenido = 5;
EXEC Actualizar_a_Contenido_mas_Visto @id_contenido = 3;
EXEC Actualizar_a_Contenido_mas_Visto @id_contenido = 1;

EXEC Quitar_de_Contenido_mas_Visto @id_contenido = 4;
EXEC Quitar_de_Contenido_mas_Visto @id_contenido = 7;


/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

/*
    PROBAMOS EL FLUJO PARA MOSTRAR HOME
    -) PARA UN CLIENTE REGISTRADO
    -) PARA UN CLIENTE NO REGISTRADO
 */

EXEC Obtener_Publicidades_Activas;

EXEC Obtener_Contenido_Destacado @id_cliente = 1;
EXEC Obtener_Contenido_Destacado @id_cliente = 6;
EXEC Obtener_Contenido_Destacado;

EXEC Obtener_Contenido_Reciente @id_cliente = 1;
EXEC Obtener_Contenido_Reciente @id_cliente = 6;
EXEC Obtener_Contenido_Reciente;

EXEC Obtener_Contenido_mas_Visto @id_cliente = 1;
EXEC Obtener_Contenido_mas_Visto @id_cliente = 6;
EXEC Obtener_Contenido_mas_Visto;


/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

/*
    PROBAMOS EL FLUJO PARA BUSCAR CONTENIDO POR FILTROS
 */

-- SERIES
EXEC Buscar_Contenido_por_Filtros @id_cliente = 1, @clasificacion = 1;

-- SERIES
EXEC Buscar_Contenido_por_Filtros @id_cliente = 6, @clasificacion = 1;

-- PELIS
EXEC Buscar_Contenido_por_Filtros @id_cliente = 6, @clasificacion = 2;

-- RECIENTE
EXEC Buscar_Contenido_por_Filtros @id_cliente = 6, @reciente = 1;

-- DESTACADO
EXEC Buscar_Contenido_por_Filtros @destacado = 1;

-- MAS VISTAS
EXEC Buscar_Contenido_por_Filtros @id_cliente = 6, @mas_visto = 1;

-- DRAMA
EXEC Buscar_Contenido_por_Filtros @id_cliente = 6, @genero = "Acción";

-- COMEDIA
EXEC Buscar_Contenido_por_Filtros @id_cliente = 6, @genero = "Drama";

-- ACCION
EXEC Buscar_Contenido_por_Filtros @genero = "Drama,Comedia";

EXEC Obtener_Informacion_de_Contenido @id_contenido = 1;
EXEC Obtener_Generos @id_contenido = 1;
EXEC Obtener_Directores @id_contenido = 1;
EXEC Obtener_Actores @id_contenido = 1;
EXEC Obtener_Informacion_de_Plataforma @id_cliente = 1, @id_contenido = 1;

EXEC Obtener_Informacion_de_Plataforma @id_contenido = 4;


/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------------------------ */

/*
    PROBAMOS EL FLUJO PARA CONSULTAR URL DE CONTENIDO A REPRODUCIR
 */

-- inserto datos en tipo_fee

INSERT INTO Tipo_Fee (tipo_de_fee, descripcion)
VALUES
    ('A', 'Description for A'),
    ('B', 'Description for B');