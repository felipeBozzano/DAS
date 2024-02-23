USE StreamingStudio;

/* FEDERAR CLIENTE 1 CON PLATAFORMA 1 */
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo1", @tipo_usuario = 1;
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo1", @token = "Token1", @email_externo = "usuarioExterno1@example.com";

/* DESVINCULAR FEDERACION DE CLIENTE 1 CON PLATAFORMA 1 */
EXEC Desvincular_Federacion @id_plataforma = 1, @id_cliente = 1;

/* FEDERAR NUEVAMENTE CLIENTE 1 CON PLATAFORMA 1 */
EXEC Comenzar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo1.5", @tipo_usuario = 1;
EXEC Finalizar_Federacion @id_plataforma = 1, @id_cliente = 1, @codigo_de_transaccion = "Codigo1.5", @token = "Token1", @email_externo = "usuarioExterno1@example.com";

/* DAR DE BAJA CLIENTE 1 */
EXEC Eliminar_Usuario @id_cliente = 1;

/* REACTIVAR CLIENTE 1 */
EXEC Validar_Usuario @id_cliente = 1;
