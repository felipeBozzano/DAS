package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ClienteUsuarioBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PlataformaDeStreamingBean;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IUsuarioClienteRepository {
    public List<ClienteUsuarioBean> createUser(ClienteUsuarioBean cliente);
    public int verificarUsuario(String usuario, String contrasena);
    public ClienteUsuarioBean obtenerInformacionUsuario(int id_cliente);
    public Map<String, List<PlataformaDeStreamingBean>> obtenerFederaciones(int id_cliente);
    public Set<PlataformaDeStreamingBean> obtenerPlataformasActivas();
    public Set<PlataformaDeStreamingBean> obtenerPlataformasFederadas(int id_cliente);
}
