package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Cliente;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.Validator;

/**
 * Esta clase tiene la responsabilidad de representar el administrador
 * de accesos a datos persistentes de clientes.
 * 
 * @author Lucas Garc�a
 *
 */
@Repository("clienteDao")
public class ClienteDao extends GenericDaoHibernate<Cliente, Long> implements IClienteDao {

	public ClienteDao() {
		super(Cliente.class);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findByDocumento(String documento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);
		
		if(!Validator.isBlankOrNull(documento)){
			criteria.add(Restrictions.ilike("documento", documento));
		}
		criteria.addOrder(Order.desc("apellido"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findByFiltro(ConsultaClienteDto filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);
		
		if(!Validator.isBlankOrNull(filtro.getDocumento())){
			criteria.add(Restrictions.ilike("documento", filtro.getDocumento()));
		}
		
		if(!Validator.isBlankOrNull(filtro.getNombre())){
			criteria.add(Restrictions.ilike("nombre", filtro.getNombre()));
		}
		
		if(Validator.isNotNull(filtro.getApellido())){
			criteria.add(Restrictions.ilike("apellido", filtro.getApellido()));
		}

		criteria.addOrder(Order.desc("apellido"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Cliente saveOrUpdate(Cliente cliente){
		getHibernateTemplate().saveOrUpdate(cliente);
		return cliente;
	}

}
