package ar.edu.ubp.das.streamingstudio.sstudio.models;

public enum Codigo{
    OK(200),
    ERROR(500),
    NO_AUTORIZADO(400),
    NO_ENCONTRADO(404),
    CREADO(201);

    private int codigo;

    Codigo(int codigo){
        this.codigo = codigo;
    }

    public int getCodigo(){
        return codigo;
    }
}
