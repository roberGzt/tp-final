package com.TpFinal.data.dao.interfaces;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.TpFinal.dto.BorradoLogico;
import com.TpFinal.dto.Identificable;


public interface DAO<T extends Identificable & BorradoLogico> {
	public boolean create(T entidad);
	public List<T> readAll();
	public List<T> readAllActives();
	public boolean update(T entidad);
	public boolean delete(T entidad);
	public boolean logicalDelete(T entidad);
	public boolean save(T entidad);
	public T findById(Long id);
	default public List<T> findByCriteria(DetachedCriteria criteria) {
        throw new RuntimeException("Metodo FindByCriteria no Implementado!");
    }
	public boolean saveOrUpdate(T entidad);
	boolean merge(T entidad); 
}
