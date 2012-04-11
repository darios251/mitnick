package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Producto;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.servicio.servicios.dtos.ConsultaStockDto;
import com.mitnick.utils.Validator;

@Repository("productoDao")
public class ProductoDAO extends GenericDaoHibernate<Producto, Long>  implements IProductoDAO {

	public ProductoDAO() {
		super(Producto.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> findStockByFiltro(ConsultaStockDto filtro){
		DetachedCriteria criteria = DetachedCriteria.forClass(Producto.class);

		if(!Validator.isBlankOrNull(filtro.getCodigo())){
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo()));
		}
		
		if(!Validator.isBlankOrNull(filtro.getDescripcion())){
			criteria.add(Restrictions.ilike("descripcion", filtro.getDescripcion()));
		}
		criteria.addOrder(Order.desc("codigo"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public Producto saveOrUpdate(Producto producto){
		getHibernateTemplate().saveOrUpdate(producto);
		return producto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> findByFiltro(ConsultaProductoDto filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Producto.class);
		
		if(!Validator.isBlankOrNull(filtro.getCodigo())){
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo()));
		}
		
		if(!Validator.isBlankOrNull(filtro.getDescripcion())){
			criteria.add(Restrictions.ilike("descripcion", filtro.getDescripcion()));
		}
		
		if(Validator.isNotNull(filtro.getMarca())){
			criteria.add(Restrictions.eq("marca.id", filtro.getMarca().getId()));
		}
		
		if(Validator.isNotNull(filtro.getTipo())){
			criteria.add(Restrictions.eq("tipo.id", filtro.getTipo().getId()));
		}
		
		criteria.addOrder(Order.desc("codigo"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
}
