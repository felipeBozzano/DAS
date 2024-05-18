package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.TransaccionBean;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface IFederarClienteRepository {
    Map<String, String> federarClientePlataforma(int id_plataforma, int id_cliente, String tipo_transaccion) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    boolean buscarFederacion(int id_plataforma, int id_cliente);
    Map<String, String> verificarFederacionCurso(int id_plataforma, int id_cliente);
    Map<String, String> obtenerInformacionDeConexionAPlataforma(int id_plataforma);
    Map<String, String> comenzarFederacion(int id_plataforma, int id_cliente, String tipo_transaccion) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    Map<String, String> finalizarFederacion(int id_plataforma, int id_cliente, String codigo_de_transaccion,
                                            String id_cliente_plataforma, boolean actualizar_info) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    void actualizarUrlToken(int id_plataforma, int id_cliente, String codigo_de_transaccion, String id_cliente_plataforma);
}
