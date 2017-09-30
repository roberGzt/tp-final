package com.TpFinal.data.dao;

import java.util.Collection;
import java.util.List;

import com.TpFinal.data.dao.interfaces.DAO;
import com.TpFinal.data.dao.interfaces.DAOInquilino;
import com.TpFinal.data.dto.DashboardNotification;
import com.TpFinal.data.dto.persona.Inquilino;
import com.TpFinal.data.dto.dummy.User;


public class DAOInquilinoImpl implements DAOInquilino  {
	
	private DAO<Inquilino>dao;
	
	public DAOInquilinoImpl() {
		dao = new DAOImpl<>(Inquilino.class);
	}

	@Override
	public boolean create(Inquilino entidad) {
		return dao.create(entidad);
	}

	@Override
	public List<Inquilino> readAll() {
		return dao.readAll();
	}

	@Override
	public boolean update(Inquilino entidad) {
		return dao.update(entidad);
	}

	@Override
	public boolean delete(Inquilino entidad) {
		return dao.delete(entidad);
	}

	@Override
	public boolean save(Inquilino entidad) {
		return dao.save(entidad);
	}

	@Override
	public Inquilino findById(Long id) {
		return dao.findById(id);
	}


	

}