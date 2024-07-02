package projetos.contas_pagar_api.service;

import java.util.List;

public interface ICrudService<ID, T> {
    List<T> findAll();
    T findById(ID id);
    T create(T entity);
    T update(ID id, T entity);
    void delete(ID id);
}
