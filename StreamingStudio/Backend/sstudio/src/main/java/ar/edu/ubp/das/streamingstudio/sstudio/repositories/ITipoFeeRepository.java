package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.Tipo_de_Fee;

import java.util.List;

public interface ITipoFeeRepository {
        public List<Tipo_de_Fee> getTipoFee(int id_tipo_fee);
}





