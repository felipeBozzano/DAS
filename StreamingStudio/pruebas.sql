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

/*
    PROBAMOS EL FLUJO DE FACTURACIÓN DE FEDERACIONES
    -) FEDERACION, FACTURACION Y NUEVA FEDERACION
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
