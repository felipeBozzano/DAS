package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.TransaccionBean;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IFederacionesPendientesRepository {
    public String terminarFederacionesPendientes() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    public List<TransaccionBean> consultarFederacionesPendientes();
}
