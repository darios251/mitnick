package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;

import com.mitnick.persistence.entities.Cliente;

/**
 * Esta clase tiene la responsabilidad de representar el administrador
 * de accesos a datos persistentes de clientes.
 * 
 * @author Lucas Garc�a
 *
 */
public class ClienteDao extends GenericDaoHibernate<Cliente, Long> implements IClienteDao {

	public ClienteDao(Class<Cliente> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findByDocumento(Long documento) {
		return getHibernateTemplate().find("from Cliente c where c.documento=?", documento);
	}

}
