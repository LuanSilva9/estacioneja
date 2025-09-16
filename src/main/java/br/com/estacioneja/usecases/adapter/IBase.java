package br.com.estacioneja.usecases.adapter;

public interface IBase<T, ID, IDTO, ODTO> {
    /* Métodos de transações */
    ODTO create(IDTO dto);
    ODTO update(ID id, IDTO dto);
    void delete(ID id);

    /* Consultas */
    T findEntityById(ID id);
    ODTO findById(ID id);

}
