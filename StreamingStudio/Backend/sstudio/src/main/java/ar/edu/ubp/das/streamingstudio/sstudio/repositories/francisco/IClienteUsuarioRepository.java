package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.Tipo_de_Fee;

import java.util.List;

public interface IClienteUsuarioRepository {
        public List<Tipo_de_Fee> getTipoFee(int id_tipo_fee);
}





