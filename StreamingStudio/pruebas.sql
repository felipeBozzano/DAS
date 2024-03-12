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
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo1_Cliente1_Plataforma1", @tipo_usuario = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro', @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @token = "Token1_Cliente1_Plataforma1";

/* DESVINCULAR FEDERACION DE CLIENTE 1 CON PLATAFORMA 1 */
EXEC Desvincular_Federacion @id_plataforma = 1, @id_cliente = 1;

/* ------------------------------------------------------------------------------------------------------------------ */

/* FEDERAR NUEVAMENTE CLIENTE 1 CON PLATAFORMA 1 */
EXEC Buscar_Federacion @id_plataforma = 1, @id_cliente = 1;
EXEC Verificar_Federacion_en_Curso @id_plataforma = 1, @id_cliente = 1;
EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 1;
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo2_Cliente1_Plataforma1", @tipo_usuario = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro', @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @token = "Token2_Cliente1_Plataforma1";

/* FEDERAR CLIENTE 1 CON PLATAFORMA 2 */
EXEC Buscar_Federacion @id_plataforma = 2, @id_cliente = 1;
EXEC Verificar_Federacion_en_Curso @id_plataforma = 2, @id_cliente = 1;
EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 2;
EXEC Comenzar_Federacion @id_plataforma = 2, @id_cliente = 1, @codigo_de_transaccion = "Codigo1_Cliente1_Plataforma2", @tipo_usuario = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro', @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
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
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo3_Cliente1_Plataforma1", @tipo_usuario = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro', @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @token = "Token3_Cliente1_Plataforma1";

/* FEDERAR CLIENTE 2 CON PLATAFORMA 1 */
EXEC Buscar_Federacion @id_plataforma = 1, @id_cliente = 2;
EXEC Verificar_Federacion_en_Curso @id_plataforma = 1, @id_cliente = 2;
EXEC Obtener_Token_de_Servicio_de_Plataforma @id_plataforma = 1;
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 2, @codigo_de_transaccion = "Codigo1_Cliente2_Plataforma1", @tipo_usuario = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro', @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 2, @token = "Token1_Cliente2_Plataforma1";

/* DAR DE BAJA PLATAFORMA 1 */
EXEC Dar_de_Baja_Plataforma_de_Streaming @id_plataforma = 1;

/* REACTIVAR PLATAFORMA 1 */
EXEC Reactivar_Plataforma_de_Streaming @id_plataforma = 1;

/* LIMPIAR TABLA TRANSACCION */
DELETE FROM Transaccion
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
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo1_Cliente1_Plataforma1", @tipo_usuario = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro', @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @token = "Token1_Cliente1_Plataforma1";

/* FEDERAR CLIENTE 1 CON PLATAFORMA 2 */
EXEC Comenzar_Federacion @id_plataforma = 2, @id_cliente = 1, @codigo_de_transaccion = "Codigo1_Cliente1_Plataforma2", @tipo_usuario = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro', @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 2, @id_cliente = 1, @token = "Token1_Cliente1_Plataforma2";

/* FEDERAR CLIENTE 2 CON PLATAFORMA 1 */
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 2, @codigo_de_transaccion = "Codigo1_Cliente2_Plataforma1", @tipo_usuario = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro', @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 2, @token = "Token1_Cliente2_Plataforma1";

/* FLUJO DE FACTURACION */
EXEC Obtener_Datos_de_Federaciones;

EXEC Crear_Factura_Plataforma @id_plataforma = 1;
EXEC Obtener_Fees_de_Plataforma @id_plataforma = 1;
EXEC Crear_Detalle_Factura @id_factura = 3, @precio_unitario = 2.5, @cantidad = 2, @subtotal = 5.0, @descripcion = 'CLIENTES NUEVOS';
EXEC Crear_Detalle_Factura @id_factura = 3, @precio_unitario = 1.0, @cantidad = 1, @subtotal = -1.0, @descripcion = 'CLIENTES REGISTRADOS';
EXEC Finalizar_Factura @id_factura = 3, @total = 4.0;
EXEC Obtener_Datos_de_Plataforma @id_plataforma = 1;
EXEC Enviar_Factura @id_factura = 3;
EXEC Facturar_Federacion @id_plataforma = 1;

EXEC Crear_Factura_Plataforma @id_plataforma = 2;
EXEC Obtener_Fees_de_Plataforma @id_plataforma = 2;
EXEC Crear_Detalle_Factura @id_factura = 4, @precio_unitario = 1.5, @cantidad = 1, @subtotal = 1.5, @descripcion = 'CLIENTES NUEVOS';
EXEC Crear_Detalle_Factura @id_factura = 4, @precio_unitario = 2.0, @cantidad = 2, @subtotal = -4.0, @descripcion = 'CLIENTES REGISTRADOS';
EXEC Finalizar_Factura @id_factura = 4, @total = -2.5;
EXEC Obtener_Datos_de_Plataforma @id_plataforma = 2;
EXEC Enviar_Factura @id_factura = 4;
EXEC Facturar_Federacion @id_plataforma = 2;

/* DESVINCULAR FEDERACION DE CLIENTE 1 CON PLATAFORMA 1 */
EXEC Desvincular_Federacion @id_plataforma = 1, @id_cliente = 1;

/* DESVINCULAR FEDERACION DE CLIENTE 2 CON PLATAFORMA 1 */
EXEC Desvincular_Federacion @id_plataforma = 1, @id_cliente = 2;

/* FEDERAR NUEVAMENTE CLIENTE 1 CON PLATAFORMA 1 */
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo2_Cliente1_Plataforma1", @tipo_usuario = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro', @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @token = "Token2_Cliente1_Plataforma1";

/* FEDERAR NUEVAMENTE CLIENTE 2 CON PLATAFORMA 1 */
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 2, @codigo_de_transaccion = "Codigo2_Cliente2_Plataforma1", @tipo_usuario = 1, @url_login_registro_plataforma = 'https://www.plataforma1.com/registro', @url_redireccion_propia = 'https://www.streamingstudio.com/terminarfederacion';
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 2, @token = "Token2_Cliente2_Plataforma1";

/* ------------------------------------------------------------------------------------------------------------------ */

EXEC Obtener_Datos_de_Publicidades;

EXEC Crear_Factura_Publicista @id_publicista = 1;
EXEC Obtener_Costo_de_Banner @id_banner = 1;
EXEC Obtener_Costo_de_Banner @id_banner = 4;
EXEC Obtener_Costo_de_Banner @id_banner = 3;
EXEC Crear_Detalle_Factura @id_factura = 1002, @precio_unitario = 23.5, @cantidad = 9, @subtotal = 211.5, @descripcion = 'ALGO';
EXEC Crear_Detalle_Factura @id_factura = 1002, @precio_unitario = 15.0, @cantidad = 27, @subtotal = 405.0, @descripcion = 'ALGO';
EXEC Crear_Detalle_Factura @id_factura = 1002, @precio_unitario = 10.5, @cantidad = 24, @subtotal = 252.0, @descripcion = 'ALGO';
EXEC Finalizar_Factura @id_factura = 1002, @total = 868.5;
EXEC Obtener_Datos_de_Publicista @id_publicista = 1;
EXEC Enviar_Factura @id_factura = 1002;

EXEC Crear_Factura_Publicista @id_publicista = 2;
EXEC Obtener_Costo_de_Banner @id_banner = 1;
EXEC Obtener_Costo_de_Banner @id_banner = 4;
EXEC Crear_Detalle_Factura @id_factura = 1003, @precio_unitario = 23.5, @cantidad = 26, @subtotal = 611.0, @descripcion = 'ALGO';
EXEC Crear_Detalle_Factura @id_factura = 1003, @precio_unitario = 15.0, @cantidad = 21, @subtotal = 315.0, @descripcion = 'ALGO';
EXEC Finalizar_Factura @id_factura = 1003, @total = 926;
EXEC Obtener_Datos_de_Publicista @id_publicista = 2;
EXEC Cancelar_Factura @id_factura = 1003;

EXEC Crear_Factura_Publicista @id_publicista = 3;
EXEC Obtener_Costo_de_Banner @id_banner = 1;
EXEC Crear_Detalle_Factura @id_factura = 1004, @precio_unitario = 23.5, @cantidad = 11, @subtotal = 258.5, @descripcion = 'ALGO';
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
