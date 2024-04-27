package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.EstadisticaPublicistaBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.Publicista;

import java.util.List;
import java.util.Map;

public interface IEstadisticasRepository {

    public String reportesPublicistas();
    public Map<Integer, List<EstadisticaPublicistaBean>> obtenerEstadisticasPublicistas();
    public int crearReportePublicista(int id_publicista);
    public void crearDetalleReporte(int id_reporte, EstadisticaPublicistaBean detalle);
    public void finalizarReporte(int id_reporte);
    public Publicista obtenerDatosDePublicista(int id_publicista);
    public void enviarReporte(int id_reporte);
}
