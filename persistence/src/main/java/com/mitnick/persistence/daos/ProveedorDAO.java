package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Proveedor;
import com.mitnick.servicio.servicios.dtos.ConsultaProveedorDto;
import com.mitnick.utils.Validator;

@Repository("proveedorDAO")
public class ProveedorDAO extends GenericDaoHibernate<Proveedor, Long>  implements IProveedorDAO {

	public ProveedorDAO() {
		super(Proveedor.class);
	}

	public Proveedor saveOrUpdate(Proveedor proveedor){
		getHibernateTemplate().saveOrUpdate(proveedor);
		return proveedor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Proveedor> findByFiltro(ConsultaProveedorDto filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Proveedor.class);
		
		if(!Validator.isBlankOrNull(filtro.getCodigo())){
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo()));
		}
		
		if(!Validator.isBlankOrNull(filtro.getNombre())){
			criteria.add(Restrictions.ilike("nombre", filtro.getNombre()));
		}
		
		criteria.add(Restrictions.eq("eliminado", false));
		criteria.addOrder(Order.asc("nombre"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Proveedor> getAllNoEliminado() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Proveedor.class);
		criteria.add(Restrictions.eq("eliminado", false));
		criteria.addOrder(Order.asc("nombre"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
