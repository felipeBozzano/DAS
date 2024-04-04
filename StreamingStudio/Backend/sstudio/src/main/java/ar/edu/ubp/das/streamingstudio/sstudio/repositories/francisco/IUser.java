package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ClienteUsuarioBean;

import java.util.List;

public interface IUser {
    public List<ClienteUsuarioBean> createUser(ClienteUsuarioBean cliente);
}
