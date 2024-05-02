package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.TransaccionBean;

import java.util.List;

public interface IFederacionesPendientesRepository {
    public String terminarFederacionesPendientes();
    public List<TransaccionBean> consultarFederacionesPendientes();
    public String obtenerTokenDeServicioDePlataforma(int id_plataforma);
    public void finalizarFederacion(int id_plataforma, int id_cliente, String token);
}
