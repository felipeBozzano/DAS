package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class PublicidadesRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Autowired
    HomeRepository homeRepository;

    public Map<String, Map<?, List<?>>> obtenerPublicidadesAgrupadas( ) {
        List<PublicidadHomeBean> publicidades = this.homeRepository.getPublicidadesActivas();
        Map<Integer, List<?>> publicidades_agrupadas = this.homeRepository.agruparPublicidad(publicidades);
        Map<String, Map<?, List<?>>> publicidades_contenido = new HashMap<>();
        publicidades_contenido.put("Publicidades", publicidades_agrupadas);
        return publicidades_contenido;
    }
}
