package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.EstadisticaPlataformaBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PlataformaDeStreamingBean;

import java.util.List;
import java.util.Map;

public interface IEstadisticasPlataformaRepository {
    public String reportesPlataformas();
//    public Map<Integer, List<EstadisticaPlataformaBean>> reportesPlataformas();
    public Map<Integer, List<EstadisticaPlataformaBean>> obtenerEstadisticasPlataformas();
    public int crearReportePlataforma(int id_plataforma);
    public void crearDetalleReporte(int id_reporte, EstadisticaPlataformaBean detalle);
    public void finalizarReporte(int id_reporte, int clics_totales);
    public PlataformaDeStreamingBean obtenerDatosDePlataforma(int id_plataforma);
    public void enviarReporte(int id_reporte, PlataformaDeStreamingBean plataforma);
}
