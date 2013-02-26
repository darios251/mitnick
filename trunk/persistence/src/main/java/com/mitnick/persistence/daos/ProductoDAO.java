package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Producto;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.utils.Validator;

@Repository("productoDao")
public class ProductoDAO extends GenericDaoHibernate<Producto, Long>  implements IProductoDAO {

	public ProductoDAO() {
		super(Producto.class);
	}

	@SuppressWarnings("unchecked")
	public Producto findByCode(String codigo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Producto.class);

		if (Validator.isNotNull(codigo)) {
			criteria.add(Restrictions.eq("codigo", codigo));
		}
		criteria.add(Restrictions.eq("eliminado", false));
		List<Producto> productos = getHibernateTemplate()
				.findByCriteria(criteria);
		if (productos != null && !productos.isEmpty())
			return productos.get(0);
		return null;
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
			criteria.add(Restrictions.ilike("codigo", filtro.getCodigo(), MatchMode.START));
		}
		
		if(!Validator.isBlankOrNull(filtro.getDescripcion())){
			criteria.add(Restrictions.ilike("descripcion", filtro.getDescripcion(), MatchMode.ANYWHERE));
		}
		
		if(Validator.isNotNull(filtro.getMarca()) && filtro.getMarca().getId() > 0){
			criteria.add(Restrictions.eq("marca.id", filtro.getMarca().getId()));
		}
		
		if(Validator.isNotNull(filtro.getTipo()) && filtro.getTipo().getId() > 0){
			criteria.add(Restrictions.eq("tipo.id", filtro.getTipo().getId()));
		}
		
		if(Validator.isNotNull(filtro.getProveedor()) && filtro.getProveedor().getId() > 0) {
			criteria.add(Restrictions.eq("proveedor.id", filtro.getProveedor().getId()));
		}
		
		criteria.add(Restrictions.eq("eliminado", false));
		criteria.addOrder(Order.desc("codigo"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
}
