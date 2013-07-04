package com.mitnick.business.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.ICiudadDao;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.daos.ICuotaDao;
import com.mitnick.persistence.daos.IDireccionDao;
import com.mitnick.persistence.daos.IEmpresaDao;
import com.mitnick.persistence.daos.IMedioPagoDAO;
import com.mitnick.persistence.daos.IProvinciaDao;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Comprobante;
import com.mitnick.persistence.entities.Cuota;
import com.mitnick.persistence.entities.Empresa;
import com.mitnick.persistence.entities.Pago;
import com.mitnick.persistence.entities.Provincia;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientoClienteDto;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.VentaHelper;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.MedioPagoDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProvinciaDto;
import com.mitnick.utils.dtos.VentaDto;

@SuppressWarnings("rawtypes")
@Service("clienteServicio")
public class ClienteServicio extends ServicioBase implements IClienteServicio {

	@Autowired
	protected IClienteDao clienteDao;

	@Autowired
	protected IProvinciaDao provinciaDao;

	@Autowired
	protected ICiudadDao ciudadDao;
	
	@Autowired
	protected IEmpresaDao empresaDao;

	@Autowired
	protected IDireccionDao direccionDao;

	@Autowired
	protected ICuotaDao cuotaDao;

	@Autowired
	protected IMedioPagoDAO medioPagoDao;

	@Autowired
	protected IVentaDAO ventaDao;

	@Transactional
	@Override
	public ClienteDto guardarCliente(ClienteDto clienteDto) {
		try {
			@SuppressWarnings("unchecked")
			Cliente cliente = (Cliente) entityDTOParser
					.getEntityFromDto(clienteDto);

			validateEntity(cliente);

			cliente = clienteDao.saveOrUpdate(cliente);
			clienteDto.setId(cliente.getId());
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar guardar el cliente");
		}
		return clienteDto;
	}

	@Transactional
	@Override
	public void eliminarCliente(ClienteDto clienteDto) {
		if (clienteDto.getId() == null) {
			throw new BusinessException(
					"error.clienteServicio.id.nulo",
					"Se invoca la eliminación de un cliente que no existe en la base de datos ya que no se brinda el ID");
		}
		try {
			@SuppressWarnings("unchecked")
			Cliente cliente = (Cliente) entityDTOParser
					.getEntityFromDto(clienteDto);
			cliente.setEliminado(true);
			clienteDao.saveOrUpdate(cliente);
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar eliminar el cliente");
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<ClienteDto> consultarCliente(ConsultaClienteDto filtro) {
		List<ClienteDto> resultado = new ArrayList<ClienteDto>();
		try {
			resultado = entityDTOParser.getDtosFromEntities(clienteDao
					.findByFiltro(filtro));
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar consultar clientes");
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProvinciaDto> obtenerProvincias() {
		return entityDTOParser.getDtosFromEntities(provinciaDao.getAll());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CiudadDto> obtenerCiudades(ProvinciaDto provincia) {
		return entityDTOParser.getDtosFromEntities(ciudadDao
				.getByProvincia((Provincia) entityDTOParser
						.getEntityFromDto(provincia)));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void cargarReporte(ConsultaClienteDto filtro) {
		try {
			List<ClienteDto> clientes = entityDTOParser
					.getDtosFromEntities(clienteDao.findByFiltro(filtro));
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/reporteClientes.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			JRDataSource dr = new JRBeanCollectionDataSource(clientes);

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);
			JasperViewer.viewReport(jasperPrint, false);

		} catch (PersistenceException e) {
			throw new BusinessException(
					"error.reporte.listadoClientes",
					"Error al intentar obtener el reporte de movimientos agrupados por producto",
					e);
		} catch (JRException e) {
			throw new BusinessException("error.reporte.listadoClientes",
					"Error al intentar obtener el reporte de ventas", e);
		}
	}

	public List<CuotaDto> obtenerCuotasPendientes(ClienteDto cliente) {
		@SuppressWarnings("unchecked")
		List<CuotaDto> cuotas = entityDTOParser.getDtosFromEntities(cuotaDao
				.getCuotaByClienteId(cliente.getId()));
		return cuotas;
	}

	@Transactional
	@Override
	public void eliminarCuota(CuotaDto cuotaDto) {
		if (cuotaDto.getId() == null) {
			throw new BusinessException(
					"error.clienteServicio.id.nulo",
					"Se invoca la eliminación de una cuota que no existe en la base de datos ya que no se brinda el ID");
		}
		try {
			cuotaDao.eliminarCuota(cuotaDto);
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar eliminar el cliente");
		}
	}

	@Transactional
	@Override
	public void guardarCuota(CuotaDto cuotaDto) {

		try {
			saveCuota(cuotaDto, null);
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar guardar las cuotas");
		}
	}

	@Transactional
	private Cuota saveCuota(CuotaDto cuotaDto, Date fechaPago) {

		try {
			VentaHelper.calcularTotales(cuotaDto);
			if (!Validator.isMoreThanZero(cuotaDto.getFaltaPagar()))
				cuotaDto.setPagado(true);
			@SuppressWarnings("unchecked")
			Cuota cuota = (Cuota) entityDTOParser.getEntityFromDto(cuotaDto);
			cuota.setFechaPago(fechaPago);
			cuota = cuotaDao.saveOrUpdate(cuota);
			cuotaDto.setId(cuota.getId());
			return cuota;
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar guardar las cuotas");
		}
	}

	@Override
	public List<CuotaDto> quitarPago(PagoDto pago, List<CuotaDto> cuotas) {
		if (pago.isComprobante())
			throw new BusinessException(
					"El pago ya generó comprobante de pago");
		for (int i = 0; i < cuotas.size(); i++) {
			CuotaDto cuota = cuotas.get(i);
			cuota.getPagos().remove(pago);
			VentaHelper.calcularTotales(cuota);
		}
		return cuotas;
	}

	@Override
	public List<CuotaDto> agregarPago(PagoDto pago, List<CuotaDto> cuotas) {
		boolean continuar = true;
		Iterator<CuotaDto> cuotasIt = cuotas.iterator();
		String nc = pago.getNroNC();
		while (continuar && cuotasIt.hasNext()) {
			BigDecimal total = pago.getMonto();
			CuotaDto cuota = cuotasIt.next();
			if (!cuota.isPagado()) {
				total = agregarPago(pago, cuota);
			}
			if (total.compareTo(new BigDecimal(0)) > 0) {
				MedioPagoDto mp = pago.getMedioPago();
				pago = new PagoDto();
				pago.setNroNC(nc);
				pago.setMonto(total);
				pago.setComprobante(false);
				pago.setMedioPago(mp);
			} else
				continuar = false;
		}
		return cuotas;
	}

	/**
	 * Agrega el pago ala cuota y retorna el monto sobrante para generar un
	 * nuevo pago si corresponde.
	 * 
	 * @param pago
	 * @param cuota
	 * @return
	 */
	private BigDecimal agregarPago(PagoDto pago, CuotaDto cuota) {
		BigDecimal resto = new BigDecimal(0);
		if (cuota.getFaltaPagar().compareTo(pago.getMonto()) < 0) {
			resto = pago.getMonto().subtract(cuota.getFaltaPagar());
			pago.setMonto(cuota.getFaltaPagar());
		}

		PagoDto pagoDto = getPagoDto(pago, cuota);
		validarPago(pago, cuota);
		if (pagoDto == null)
			// agrego el nuevo pago
			cuota.getPagos().add(pago);
		else {
			// actualizo el pago existente en la venta
			BigDecimal pagado = pagoDto.getMonto();
			BigDecimal pagando = pago.getMonto();
			pagoDto.setMonto(pagado.add(pagando));
		}
		VentaHelper.calcularTotales(cuota);
		return resto;
	}

	private void validarPago(PagoDto pago, CuotaDto cuotaDto) {
		// si es cuenta se debe tener un cliente asociado
		if (MitnickConstants.Medio_Pago.CUENTA_CORRIENTE.equals(pago
				.getMedioPago().getCodigo()))
			throw new BusinessException(
					"error.clienteServicio.PagoCuentaCorriente.medioPago",
					"La cuenta corriente no se puede pagar con este medio de pago");
	}

	public PagoDto getPagoDto(PagoDto pago, CuotaDto cuota) {
		Iterator<PagoDto> pagos = cuota.getPagos().iterator();
		PagoDto pagoDto = null;
		while (pagos.hasNext()) {
			PagoDto pDto = pagos.next();
			if (!pDto.isComprobante()
					&& pDto.getMedioPago().getId()
							.equals(pago.getMedioPago().getId()))
				pagoDto = pDto;
		}
		if (pagoDto == null) {
			pagoDto = new PagoDto();
			pagoDto.setComprobante(false);
			pagoDto.setMedioPago(pago.getMedioPago());
			pagoDto.setMonto(new BigDecimal(0));
			pagoDto.setNroNC(pago.getNroNC());
			cuota.getPagos().add(pago);
		}
		return pagoDto;
	}

	@Transactional
	@Override
	public void comprobantePago(List<CuotaDto> cuotas) {
		try {
			for (int i = 0; i < cuotas.size(); i++) {
				VentaHelper.calcularTotales(cuotas.get(i));
			}

			ventaDao.actualizarCreditos(cuotas);

			Comprobante comprobante = generarComprobante(cuotas);

			for (int i = 0; i < cuotas.size(); i++) {
				Cuota cuotaEnt = saveCuota(cuotas.get(i), new Date());
				if (cuotaEnt.getPagos() != null) {
					Iterator<Pago> pagosIt = cuotaEnt.getPagos().iterator();
					while (pagosIt.hasNext()) {
						Pago pago = pagosIt.next();
						if (!pago.isComprobante()) {
							comprobante.addPago(pago);
							pago.setFecha(new Date());
						}

						pago.setComprobante(true);
					}
				}
				clienteDao.saveOrUpdate(comprobante);
			}
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar generar el comprobante de pago");
		}

	}

	@SuppressWarnings("unchecked")
	/**
	 * Este metodo se invoca cuando el cliente que realiza la devolucion tiene cuenta corriente con cuotas pendientes de pago.
	 * Este metodo utiliza el credito otorgado por la devolucion para cancelar las cuotas correspondientes.
	 */
	public void pagarCuotasNC(VentaDto venta) {
		List<Cuota> cuotas = cuotaDao.getCuotaByClienteId(venta.getCliente()
				.getId());
		BigDecimal pendiente = new BigDecimal(0);
		if (cuotas != null) {
			Iterator<Cuota> cuotasIt = cuotas.iterator();
			boolean seguir = true;
			List<CuotaDto> cuotasAPagar = new ArrayList<CuotaDto>();
			while (cuotasIt.hasNext() && seguir) {
				Cuota cuota = cuotasIt.next();
				pendiente = pendiente.add(cuota.getFaltaPagar());
				cuotasAPagar.add((CuotaDto) entityDTOParser.getDtoFromEntity(cuota));
				if (pendiente.compareTo(venta.getTotal()) >= 0)
					seguir = false;
			}
			MedioPagoDto medioPagoDto = (MedioPagoDto) entityDTOParser
					.getDtoFromEntity(medioPagoDao
							.getByCode(MitnickConstants.Medio_Pago.NOTA_CREDITO));
			PagoDto pago = new PagoDto();
			pago.setMedioPago(medioPagoDto);
			pago.setMonto(venta.getTotal());
			pago.setNroNC(venta.getNumeroTicket());
			agregarPago(pago, cuotasAPagar);
			
			comprobantePago(cuotasAPagar);

		}
	}

	@Transactional
	public void cancelarComprobante(String nroComprobante) {

		Comprobante comprobante = clienteDao
				.findComprobanteByNumero(nroComprobante);
		if (Validator.isNull(comprobante))
			throw new BusinessException(
					"error.clienteServicio.comprobante.cancelar.noExiste",
					"No se encuentra el comprobante que desea eliminar");
		List<Pago> pagos = comprobante.getPagos();
		for (Pago pago : pagos) {
			// se actualiza la cuota
			Cuota cuota = pago.getCuota();
			BigDecimal cuotafaltaPagar = cuota.getFaltaPagar();
			cuotafaltaPagar = cuotafaltaPagar.add(pago.getPago());
			cuota.setFaltaPagar(cuotafaltaPagar);
			cuota.setPagado(false);
			cuota.getPagos().remove(pago);

		}
		// se elimina el comprobante
		try {
			clienteDao.eliminarComprobante(comprobante);
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar eliminar el comprobante");
		}

	}

	public BigDecimal getSaldoDeudor(ClienteDto cliente) {
		return clienteDao.getSaldoDeudor(cliente);
	}
	
	private Comprobante generarComprobante(List<CuotaDto> cuotas) {
		ClienteDto cliente = cuotas.get(0).getClienteDto();
		List<PagoDto> pagosComprobante = new ArrayList<PagoDto>();
		BigDecimal pagoComprobante = new BigDecimal(0);

		for (int i = 0; i < cuotas.size(); i++) {
			pagosComprobante.addAll(cuotas.get(i).getPagosComprobante());
			pagoComprobante = pagoComprobante.add(cuotas.get(i)
					.getPagoComprobante());
		}

		if (Validator.isEmptyOrNull(pagosComprobante))
			throw new PersistenceException("error.cuota.comprobante.sinPagos");
		try {
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/comprobante.jasper"));
			JRDataSource dr = new JRBeanCollectionDataSource(
					clean(pagosComprobante));

			Empresa empresa = empresaDao.getEmpresa();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nombreEmpresa", empresa.getNombre());
			parameters.put("empresaDireccion", empresa.getDireccion()
					.getDomicilio()
					+ "("
					+ empresa.getDireccion().getCodigoPostal()
					+ ")"
					+ empresa.getDireccion().getCiudad().getDescripcion()
					+ "\n Tel" + empresa.getTelefono());
			parameters.put("tipoResponsable", empresa.getTipoResponsable());
			parameters.put("cuitEmpresa", empresa.getCuit());
			parameters.put("ingBrutos", empresa.getIngBrutos());
			parameters.put("fechaInicioActividadEmpresa",
					empresa.getFechaInicioActividad());
			parameters.put("tipoIva", "Consumidor Final");
			parameters.put("nombreCliente", cliente.getNombre());
			parameters.put("codigo", cliente.getId().toString());
			String direccion = "";

			if (Validator.isNotNull(cliente.getDireccion())) {
				direccion = direccion.concat(cliente.getDireccion()
						.getDomicilio());
				if (Validator.isNotNull(cliente.getDireccion().getCiudad()))
					direccion = direccion.concat(" ")
							.concat(cliente.getDireccion().getCiudad()
									.getDescripcion());
			}

			parameters.put("direccionCliente", direccion);
			BigDecimal saldoTotal = getSaldoDeudor(cliente);

			BigDecimal saldoPendiente = saldoTotal.subtract(pagoComprobante);

			parameters.put("saldoTotal", saldoTotal.toString());
			parameters.put("total", pagoComprobante.toString());
			parameters.put("saldoPendiente", saldoPendiente.toString());

			Comprobante comprobante = new Comprobante();
			comprobante.setNumeroCaja(PropertiesManager.getPropertyAsInteger("application.caja.numero"));
			String id = String.valueOf(cliente.getId()).concat(String.valueOf(cliente.getCantidadComprobantes()));
			comprobante.setId(id);
			comprobante.setFecha(new Date());
			comprobante.setTotal(pagoComprobante);
			Cliente clienteObject = clienteDao.get(cliente.getId());
			comprobante.setCliente(clienteObject);
			clienteObject.addComprobante(comprobante);
			clienteObject = clienteDao.saveOrUpdate(clienteObject);
			
			parameters.put("nroComprobante", id);

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);

			JasperViewer.viewReport(jasperPrint, false);

			return comprobante;

		} catch (Exception e1) {
			throw new PersistenceException("error.comprobante.Cliente",
					"Error al generar el comprobante de pago del cliente.", e1);
		}
	}
	
	private List<PagoDto> clean(List<PagoDto> pagos) {
		List<PagoDto> pagosLimpios = new ArrayList<PagoDto>();
		Iterator<PagoDto> pagosIt = pagos.iterator();
		while (pagosIt.hasNext()) {
			PagoDto pago = pagosIt.next();
			PagoDto auxiliar = new PagoDto();
			auxiliar.setMedioPago(pago.getMedioPago());
			auxiliar.setMonto(pago.getMonto());
			PagoDto pagoDto = getPagoMedioPago(pagosLimpios, auxiliar);
			if (pagoDto == null)
				pagosLimpios.add(auxiliar);
			else
				pagoDto.setMonto(pagoDto.getMonto().add(pago.getMonto()));
		}
		return pagosLimpios;
	}
	
	private PagoDto getPagoMedioPago(List<PagoDto> pagos, PagoDto pago) {
		for (int i = 0; i < pagos.size(); i++) {
			if (pagos.get(i).getMedioPago().getCodigo()
					.equals(pago.getMedioPago().getCodigo()))
				return pagos.get(i);
		}
		return null;
	}
	
	public void reporteMovimientosCliente(ClienteDto cliente) {
		try {
			List<Venta> ventas = ventaDao.findByClient(cliente.getId());
			List<ReporteMovimientoClienteDto> movimientos = new ArrayList<ReporteMovimientoClienteDto>();
			for (int i = 0; i < ventas.size(); i++) {
				Venta venta = ventas.get(i);
				ReporteMovimientoClienteDto movimiento = new ReporteMovimientoClienteDto();
				movimiento.setMonto(venta.getTotal());
				String nro = venta.getNumeroTicket();
				movimiento.setFecha(venta.getFecha());
				if (venta.isVenta()) {
					movimiento.setNroComprobante("F" + venta.getTipoTicket()
							+ "-" + nro);
					movimiento.setDebe(venta.getPagoCuenta());
					BigDecimal pago = venta.getPagoContado();
					pago = pago.add(venta.getPagoNC());
					movimiento.setHaber(pago);
				} else {
					movimiento.setNroComprobante("NC" + venta.getTipoTicket()
							+ "-" + nro);
					movimiento.setDebe(new BigDecimal(0));
					movimiento.setHaber(venta.getTotal());
				}
				movimientos.add(movimiento);
			}
			List<Comprobante> comprobantes = clienteDao.findComprobantesByClienteId(cliente
					.getId());
			for (int j = 0; j < comprobantes.size(); j++) {
				Comprobante comprobante = comprobantes.get(j);
				ReporteMovimientoClienteDto movimiento = new ReporteMovimientoClienteDto();
				movimiento.setMonto(comprobante.getTotal());
				String nro = comprobante.getId().toString();
				movimiento.setNroComprobante("Comp-" + nro);
				movimiento.setFecha(comprobante.getFecha());
				movimiento.setDebe(new BigDecimal(0));
				movimiento.setHaber(comprobante.getTotal());
				movimientos.add(movimiento);
			}

			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/movimientosCliente.jasper"));

			JRDataSource dr = new JRBeanCollectionDataSource(
					orderByDate(movimientos));

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("saldoDeudor", getSaldoDeudor(cliente).toString());			
			parameters.put("nombreCliente", cliente.getNombre());
			String direccion = "";
			if (Validator.isNotNull(cliente.getDireccion())) {
				direccion = direccion.concat(cliente.getDireccion()
						.getDomicilio());
				if (Validator.isNotNull(cliente.getDireccion().getCiudad()))
					direccion = direccion.concat(" ")
							.concat(cliente.getDireccion().getCiudad()
									.getDescripcion());
			}

			parameters.put("direccionCliente", direccion);
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);

			JasperViewer.viewReport(jasperPrint, false);

		} catch (Exception e1) {
			throw new PersistenceException("error.reporte.movimientos.Cliente",
					"Error al generar el reporte de movimientos del cliente.",
					e1);
		}
	}

	@SuppressWarnings("unchecked")
	private List<ReporteMovimientoClienteDto> orderByDate(
			List<ReporteMovimientoClienteDto> movimientos) {
		// ordenamos la lista por fecha
		Collections.sort(movimientos, new Comparator() {

			public int compare(Object o1, Object o2) {
				ReporteMovimientoClienteDto e1 = (ReporteMovimientoClienteDto) o1;
				ReporteMovimientoClienteDto e2 = (ReporteMovimientoClienteDto) o2;
				return e1.getFecha().compareTo(e2.getFecha());
			}
		});
		return movimientos;
	}

}
