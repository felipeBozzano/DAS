package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadHomeBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPublicidadesRepository {
    Map<String, Map<Integer, List<PublicidadHomeBean>>> obtenerPublicidadesAgrupadas();
    List<PublicidadHomeBean> getPublicidadesActivas();
    Map<Integer, List<PublicidadHomeBean>> agruparPublicidad(List<PublicidadHomeBean> listaPublicidad);
}
