package com.mitnick.persistence.daos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Comprobante;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.ConstraintValidationHelper;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ClienteDto;

/**
 * Esta clase tiene la responsabilidad de representar el administrador de
 * accesos a datos persistentes de clientes.
 * 
 * @author Lucas García
 * 
 */
@Repository("clienteDao")
public class ClienteDao extends GenericDaoHibernate<Cliente, Long> implements
		IClienteDao {

	protected javax.validation.Validator entityValidator = Validation
			.buildDefaultValidatorFactory().getValidator();

	@Autowired
	protected ICuotaDao cuotaDao;

	@Autowired
	protected IVentaDAO ventaDao;

	public ClienteDao() {
		super(Cliente.class);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findByDocumento(String documento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);

		if (Validator.isNotBlankOrNull(documento)) {
			criteria.add(Restrictions.ilike("documento", documento));
		}
		criteria.add(Restrictions.eq("eliminado", false));
		criteria.addOrder(Order.desc("nombre"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public Cliente findByDocumentoEq(String documento) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);

		if (Validator.isNotBlankOrNull(documento)) {
			criteria.add(Restrictions.eq("documento", documento));
		}
		criteria.add(Restrictions.eq("eliminado", false));
		List<Cliente> clientes = getHibernateTemplate()
				.findByCriteria(criteria);
		if (clientes != null && !clientes.isEmpty())
			return clientes.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public Cliente findByCuitEq(String cuit) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);

		if (Validator.isNotBlankOrNull(cuit)) {
			criteria.add(Restrictions.eq("cuit", cuit));
		}
		criteria.add(Restrictions.eq("eliminado", false));
		List<Cliente> clientes = getHibernateTemplate()
				.findByCriteria(criteria);
		if (clientes != null && !clientes.isEmpty())
			return clientes.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public Cliente findById(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);

		if (Validator.isNotNull(id)) {
			criteria.add(Restrictions.eq("id", id));
		}
		criteria.add(Restrictions.eq("eliminado", false));
		List<Cliente> clientes = getHibernateTemplate()
				.findByCriteria(criteria);
		if (clientes != null && !clientes.isEmpty())
			return clientes.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Comprobante> findComprobantesByClienteId(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);

		if (Validator.isNotNull(id)) {
			criteria.add(Restrictions.eq("id", id));
		}
		criteria.add(Restrictions.eq("eliminado", false));
		List<Cliente> clientes = getHibernateTemplate()
				.findByCriteria(criteria);
		if (clientes != null && !clientes.isEmpty())
			return clientes.get(0).getComprobantes();
		return null;
	}

	@SuppressWarnings("unchecked")
	public Comprobante findComprobanteByNumero(String nroComprobante) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(Comprobante.class);

		if (Validator.isNotBlankOrNull(nroComprobante)) {
			criteria.add(Restrictions.eq("id", nroComprobante));
		}
		List<Comprobante> comprobantes = getHibernateTemplate().findByCriteria(
				criteria);
		if (comprobantes != null && !comprobantes.isEmpty())
			return comprobantes.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findByFiltro(ConsultaClienteDto filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);

		if (Validator.isNotBlankOrNull(filtro.getDocumento())) {
			criteria.add(Restrictions.ilike("documento", filtro.getDocumento()
					.trim()));
		}

		if (Validator.isNotBlankOrNull(filtro.getNombre())) {
			criteria.add(Restrictions.ilike("nombre", "%"
					+ filtro.getNombre().trim() + "%"));
		}

		criteria.add(Restrictions.eq("eliminado", false));
		criteria.addOrder(Order.desc("nombre"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public Cliente saveOrUpdate(Cliente cliente) {
		Set<ConstraintViolation<Cliente>> constraintViolations = entityValidator
				.validate(cliente);
		if (Validator.isNotEmptyOrNull(constraintViolations))
			throw new PersistenceException(
					new ConstraintValidationHelper<Cliente>()
							.getMessage(constraintViolations));

		getHibernateTemplate().saveOrUpdate(cliente);
		return cliente;
	}

	public void eliminarComprobante(Comprobante comprobante) {
		try {
			getHibernateTemplate().delete(comprobante);
		} catch (Exception e1) {
			throw new PersistenceException(
					"error.cancelar.comprobante.Cliente",
					"Error al eliminar el comprobante de pago del cliente.", e1);
		}
	}

	public Comprobante saveOrUpdate(Comprobante comprobante) {
		getHibernateTemplate().saveOrUpdate(comprobante);
		return comprobante;
	}

	public BigDecimal getSaldoDeudor(ClienteDto cliente) {

		return cuotaDao.getSaldoPendiente(cliente.getId());
	}

	/**
	 * Este método se creo para mantener compatibilidad hacia atras con version anterior del proyecto.
	 * Migracion de datos.
	 */
	@SuppressWarnings("unchecked")
	public List<Cliente> findAllWithComprobantes() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Cliente.class);
		criteria.setFetchMode("comprobantes", FetchMode.JOIN);
		return getHibernateTemplate().findByCriteria(criteria);
	}
}
