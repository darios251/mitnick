package com.mitnick.persistence.daos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Credito;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.VentaDto;

@Repository("ventaDao")
public class VentaDAO extends GenericDaoHibernate<Venta, Long>  implements IVentaDAO {

	public VentaDAO() {
		super(Venta.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Venta> findByFiltro(ReportesDto filtro, int numeroCaja) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);

		if(Validator.isNotNull(Validator.isNotNull(filtro.getFechaInicio()))){
			criteria.add(Restrictions.ge("fecha", filtro.getFechaInicio()));
		}
		if(Validator.isNotNull(filtro.getFechaFin())){
			criteria.add(Restrictions.le("fecha", filtro.getFechaFin()));
		}

		criteria.add(Restrictions.eq("numeroCaja", numeroCaja));
		criteria.add(Restrictions.eq("canceled", false));
		
		criteria.addOrder(Order.desc("fecha"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public Venta saveOrUpdate(Venta venta){
		
		if (!venta.isCanceled() && venta.isDevolucion()){
			//SE CREA LA NOTA DE CREDITO
			Credito credito = new Credito();
			credito.setFecha(new Date());			
			credito.setMonto(venta.getPagoContado());
			credito.setMontoUsado(new BigDecimal(0));
			credito.setNumeroTicket(venta.getNumeroTicketOriginal());
			credito.setNumeroNC(venta.getNumeroTicket());
			getHibernateTemplate().saveOrUpdate(credito);
		}
		
		getHibernateTemplate().saveOrUpdate(venta);
		return venta;
	}
	
	public void actualizarCreditos(VentaDto venta) {
		if (!venta.getPagoNotaCredito().isEmpty()){
			Iterator<PagoDto> creditosUsados = venta.getPagoNotaCredito().iterator();
			while (creditosUsados.hasNext()){
				PagoDto pago = creditosUsados.next();
				usarCredito(pago.getNroNC(), pago.getMonto());
			}
		}
	}
	
	public void actualizarCreditos(List<CuotaDto> cuotas) {
		for (CuotaDto cuota : cuotas){
			for (PagoDto pago : cuota.getPagos()){
					if (!pago.isComprobante() && MitnickConstants.Medio_Pago.NOTA_CREDITO.equals(pago.getMedioPago().getCodigo())) 
						usarCredito(pago.getNroNC(), pago.getMonto());					
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void usarCredito(String nroCredito, BigDecimal montoUsado) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Credito.class);
		
		criteria.add(Restrictions.ilike("numeroNC", nroCredito));	
		List<Credito> creditos = getHibernateTemplate().findByCriteria(criteria);
		if (creditos!=null && !creditos.isEmpty()) {
			Credito credito = creditos.get(0);
			BigDecimal montoUsadoAntes=credito.getMontoUsado();			
			credito.setMontoUsado(montoUsado.add(montoUsadoAntes));
			getHibernateTemplate().saveOrUpdate(credito);	
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public Credito getCredito(String nroCredito) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Credito.class);
		
		criteria.add(Restrictions.ilike("numeroNC", nroCredito));	
		List<Credito> creditos = getHibernateTemplate().findByCriteria(criteria);
		if (creditos!=null && !creditos.isEmpty())
			return creditos.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Venta> findByClient(Long cliente){
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);
		
		criteria.createAlias("cliente", "c");
		if(Validator.isNotNull(cliente)){
			criteria.add(Restrictions.eq("c.id", cliente));
		}		
		criteria.add(Restrictions.eq("canceled", false));

		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public Venta findLastByClient(Long cliente) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);
		
		criteria.createAlias("cliente", "c");
		if(Validator.isNotNull(cliente)){
			criteria.add(Restrictions.eq("c.id", cliente));
		}		
		criteria.add(Restrictions.eq("canceled", false));
		criteria.addOrder(Order.desc("fecha"));
		List<Venta> ventas = getHibernateTemplate().findByCriteria(criteria);
		if (Validator.isNotEmptyOrNull(ventas))
			return ventas.get(0);
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public Venta findByNumeroFactura(String numeroTicket, int numeroCaja) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);
		
		criteria.add(Restrictions.ilike("numeroTicket", numeroTicket));	
		criteria.add(Restrictions.eq("canceled", false));
		criteria.add(Restrictions.eq("tipo", MitnickConstants.VENTA));
		criteria.add(Restrictions.eq("numeroCaja", numeroCaja));
		
		List<Venta> ventas = getHibernateTemplate().findByCriteria(criteria);
		if (ventas==null || ventas.isEmpty())
				return null;
		return ventas.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Venta findTransactionByNumeroFactura(String numeroTicket, int numeroCaja) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);
		
		criteria.add(Restrictions.ilike("numeroTicket", numeroTicket));	
		criteria.add(Restrictions.eq("numeroCaja", numeroCaja));
		criteria.add(Restrictions.eq("canceled", false));

		List<Venta> ventas = getHibernateTemplate().findByCriteria(criteria);
		if (ventas==null || ventas.isEmpty())
				return null;
		return ventas.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Venta findTransactionByNumeroTipoFactura(String numeroTicket, String tipo, String factura, int numeroCaja) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);
		
		criteria.add(Restrictions.ilike("numeroTicket", numeroTicket));	
		criteria.add(Restrictions.ilike("tipoTicket", factura));
		int tipoTrx = MitnickConstants.VENTA;
		if (PropertiesManager.getProperty("dialog.consultarTransacciones.filter.devolucion").equals(tipo))
			tipoTrx = MitnickConstants.DEVOLUCION;
		criteria.add(Restrictions.eq("tipo", tipoTrx));	
		criteria.add(Restrictions.eq("numeroCaja", numeroCaja));
		criteria.add(Restrictions.eq("canceled", false));

		List<Venta> ventas = getHibernateTemplate().findByCriteria(criteria);
		if (ventas==null || ventas.isEmpty())
				return null;
		return ventas.get(0);
	}
		
}
