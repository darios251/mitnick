package com.mitnick.persistence.daos;

import java.util.ArrayList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Cliente;

/**
 * Esta clase tiene la responsabilidad de representar el administrador
 * de accesos a datos persistentes de clientes.
 * 
 * @author Lucas Garc�a
 *
 */
@Repository("clienteDao")
public class ClienteDao extends GenericDaoHibernate<Cliente, Long> implements IClienteDao {

	public ClienteDao(Class<Cliente> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findByDocumento(String documento) {
		return getHibernateTemplate().find("from Cliente c where c.documento=?", documento);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Cliente> findByDocumentoNombreApellido(String documento, String nombre, String apellido) {
		String query = "from Cliente c";
		List<Object> filtros = new ArrayList<Object>();
		if ((documento!=null && !documento.trim().equals("")) || (nombre!=null && !nombre.trim().equals("")) || (apellido!=null && !apellido.trim().equals(""))) {
			query = query.concat(" where ");
			
			boolean and = false;
			if (documento!=null && !documento.trim().equals("")) {
				query = query.concat(" c.documento like ?");
				filtros.add("%" + documento+ "%");
				and = true;
			}
			
			if (nombre!=null && !nombre.trim().equals("")) {
				if (and)
					query = query.concat(" and ");
				and = true;
				query = query.concat(" c.nombre like ?");
				filtros.add("%" + nombre+ "%");
			}

			if (apellido!=null && !apellido.trim().equals("")) {
				if (and)
					query = query.concat(" and ");
				query = query.concat(" c.apellido like ?");
				filtros.add("%" + apellido + "%");
			}						
		}
		
		return getHibernateTemplate().find(query, filtros.toArray());
	}
	
	public Cliente saveOrUpdate(Cliente cliente){
		getHibernateTemplate().saveOrUpdate(cliente);
		return cliente;
	}

}
