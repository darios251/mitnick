package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Vendedor;
import com.mitnick.servicio.servicios.dtos.ConsultaVendedorDto;
import com.mitnick.utils.Validator;

@Repository("vendedorDAO")
public class VendedorDao extends GenericDaoHibernate<Vendedor, Long>  implements IVendedorDao {

	public VendedorDao() {
		super(Vendedor.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Vendedor findById(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Vendedor.class);

		if (Validator.isNotNull(id)) {
			criteria.add(Restrictions.eq("id", id));
		}		
		List<Vendedor> vendedores = getHibernateTemplate().findByCriteria(criteria);
		if (vendedores != null && !vendedores.isEmpty())
			return vendedores.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vendedor> findByFiltro(ConsultaVendedorDto filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Vendedor.class);
		
		if(Validator.isNotBlankOrNull(filtro.getCodigo())){
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo()));
		}
		
		if(Validator.isNotBlankOrNull(filtro.getNombre())){
			criteria.add(Restrictions.ilike("nombre", filtro.getNombre()));
		}
		
		criteria.add(Restrictions.eq("eliminado", false));
		criteria.addOrder(Order.asc("nombre"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public Vendedor saveOrUpdate(Vendedor vendedor) {
		getHibernateTemplate().saveOrUpdate(vendedor);
		return vendedor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vendedor> getAllNoEliminado() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Vendedor.class);
		criteria.add(Restrictions.eq("eliminado", false));
		criteria.addOrder(Order.asc("nombre"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
