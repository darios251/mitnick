package com.mitnick.persistence.daos;

import java.util.ArrayList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Producto;
import com.mitnick.utils.Validator;

@Repository("productoDao")
public class ProductoDAO extends GenericDaoHibernate<Producto, Long>  implements IProductoDAO {

	public ProductoDAO(Class<Producto> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> findByCodigoDescripcion(String codigo,
			String descripcion) {
		String query = "from Producto p ";
		List<Object> filtros = new ArrayList<Object>();

		if (!Validator.isBlankOrNull(codigo) || !Validator.isBlankOrNull(descripcion)) {
			query = query.concat(" where ");
			
			boolean and = false;
			if (!Validator.isBlankOrNull(codigo)) {
				query = query.concat(" p.codigo like ?");
				filtros.add("%" + codigo + "%");
				and = true;
			}
			
			if (!Validator.isBlankOrNull(descripcion)) {
				if (and)
					query = query.concat(" and ");
				query = query.concat(" p.descripcion like ?");
				filtros.add("%" + descripcion + "%");
			}

		}
		
		return getHibernateTemplate().find(query, filtros.toArray());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> findByCodigoDescripcionTipoMarca(String codigo,String descripcion, Long tipo, Long marca) {
		String query = "from Producto p ";
		List<Object> filtros = new ArrayList<Object>();

		if (!Validator.isBlankOrNull(codigo) || !Validator.isBlankOrNull(descripcion)) {
			query = query.concat(" where ");
			
			boolean and = false;
			if (!Validator.isBlankOrNull(codigo)) {
				query = query.concat(" p.codigo like ?");
				filtros.add("%" + codigo + "%");
				and = true;
			}
			
			if (!Validator.isBlankOrNull(descripcion)) {
				if (and)
					query = query.concat(" and ");
				query = query.concat(" p.descripcion like ?");
				filtros.add("%" + descripcion + "%");
			}
			
			if (Validator.isNotNull(tipo)) {
				if (and)
					query = query.concat(" and ");
				query = query.concat(" p.tipo = ?");
				filtros.add(tipo);
			}
			
			if (Validator.isNotNull(marca)) {
				if (and)
					query = query.concat(" and ");
				query = query.concat(" p.marca = ?");
				filtros.add(marca);
			}

		}
		
		return getHibernateTemplate().find(query, filtros.toArray());
	}
	
	public Producto saveOrUpdate(Producto producto){
		getHibernateTemplate().saveOrUpdate(producto);
		return producto;
	}
	
}
