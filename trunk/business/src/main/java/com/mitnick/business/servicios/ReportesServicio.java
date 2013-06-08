package com.mitnick.business.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.mitnick.persistence.daos.ICierreZDao;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.daos.ICuotaDao;
import com.mitnick.persistence.daos.IMovimientoDao;
import com.mitnick.persistence.daos.IReporteDao;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.persistence.entities.CierreZ;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Comprobante;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Pago;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.IReportesServicio;
import com.mitnick.servicio.servicios.dtos.ComprobanteDto;
import com.mitnick.servicio.servicios.dtos.FacturaDto;
import com.mitnick.servicio.servicios.dtos.ReporteCompraSugeridaDTO;
import com.mitnick.servicio.servicios.dtos.ReporteDetalleMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteFacturasDto;
import com.mitnick.servicio.servicios.dtos.ReporteMovimientosDto;
import com.mitnick.servicio.servicios.dtos.ReporteVentaArticuloDTO;
import com.mitnick.servicio.servicios.dtos.ReporteVentasResultadoDTO;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.DateHelper;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.MovimientoProductoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.VentaDto;

@SuppressWarnings("rawtypes")
@Service("reportesServicio")
public class ReportesServicio extends ServicioBase implements IReportesServicio {

	@Autowired
	protected IMovimientoDao movimientoDao;

	@Autowired
	protected IVentaDAO ventaDao;

	@Autowired
	protected IReporteDao reporteDao;

	@Autowired
	protected ICuotaDao cuotaDao;
	
	@Autowired
	protected IClienteDao clienteDao;
	
	@Autowired
	protected ICierreZDao cierreDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<MovimientoProductoDto> reporteMovimientosAgrupadosPorProducto(
			ReporteMovimientosDto filtro) {
		try {
			return agruparPorProducto(movimientoDao.findByFiltro(filtro));
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el reporte de movimientos agrupados por producto");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public void exportarMovimientosAgrupadosPorProducto(
			List<MovimientoProductoDto> movimientos) {
		try {
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/movimientoProductos.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			JRDataSource dr = new JRBeanCollectionDataSource(movimientos);

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);
			JasperViewer.viewReport(jasperPrint, false);

		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el reporte de movimientos agrupados por producto");
		} catch (JRException e) {
			throw new BusinessException(
					"Error al intentar obtener el reporte de ventas", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<MovimientoDto> reporteMovimientosDeProducto(
			ReporteDetalleMovimientosDto filtro) {
		try {
			return entityDTOParser.getDtosFromEntities(movimientoDao
					.findByFiltro(filtro));
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el reporte de movimientos de producto");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public void exportarMovimientosDeProducto(List<MovimientoDto> movimientos,
			ProductoDto producto, String stockOriginal, String stockFinal) {
		try {
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/detalleMovimientoProducto.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("codigo", producto.getCodigo());
			parameters.put("descripcion", producto.getDescripcion());
			if (Validator.isNotNull(producto.getMarca()))
				parameters.put("marca", producto.getMarca().getDescripcion());
			else
				parameters.put("marca", "");
			parameters.put("stockOriginal", stockOriginal);
			parameters.put("stockFinal", stockFinal);

			JRDataSource dr = new JRBeanCollectionDataSource(movimientos);

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);

			JasperViewer.viewReport(jasperPrint, false);

		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el reporte de movimientos agrupados por producto");
		} catch (JRException e) {
			throw new BusinessException(
					"Error al intentar obtener el reporte de ventas", e);
		}
	}

	/**
	 * Obtiene la lista de productos con sus movimientos sumarizados.
	 * 
	 * @param movimientos
	 * @return
	 */
	private List<MovimientoProductoDto> agruparPorProducto(
			List<Movimiento> movimientos) {
		List<MovimientoProductoDto> productos = new ArrayList<MovimientoProductoDto>();
		for (Movimiento movimiento : movimientos) {
			MovimientoProductoDto movimientoDto = getDetallePorProducto(
					movimiento, productos);
			if (movimiento.getTipo() == Movimiento.CREACION) {
				movimientoDto.setStockOriginal(movimiento.getCantidad());
			}else if (movimiento.getTipo() == Movimiento.AJUSTE) {
				movimientoDto.setAjustes(movimientoDto.getAjustes()
						+ movimiento.getCantidad());
			} else if (movimiento.getTipo() == Movimiento.VENTA) {
				movimientoDto.setVentas(movimientoDto.getVentas()
						- movimiento.getCantidad());
			} else if (movimiento.getTipo() == Movimiento.DEVOLUCION) {
				movimientoDto.setVentas(movimientoDto.getVentas()
						+ movimiento.getCantidad());
			}
		}
		return productos;
	}

	/**
	 * Obtiene el detalle de movimientos del producto para el movimiento pasado
	 * por parametro.
	 * 
	 * @param movimiento
	 * @param productos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MovimientoProductoDto getDetallePorProducto(Movimiento movimiento,
			List<MovimientoProductoDto> productos) {
		Iterator<MovimientoProductoDto> movProductos = productos.iterator();
		MovimientoProductoDto movimientoDto = null;
		while (movimientoDto == null && movProductos.hasNext()) {
			MovimientoProductoDto movimientoProducto = movProductos.next();
			if (movimientoProducto.getProducto().getCodigo()
					.equals(movimiento.getProducto().getCodigo()))
				movimientoDto = movimientoProducto;
		}
		if (movimientoDto == null) {
			movimientoDto = new MovimientoProductoDto();
			movimientoDto.setAjustes(0);
			movimientoDto.setVentas(0);
			movimientoDto.setProducto((ProductoDto) entityDTOParser
					.getDtoFromEntity(movimiento.getProducto()));
			productos.add(movimientoDto);
		}

		return movimientoDto;
	}

	@Transactional(readOnly = true)
	@Override
	public void reporteFacturas(ReportesDto filtro) {
		try {
			List<Venta> ventas = ventaDao.findByFiltro(filtro);
			List<ReporteFacturasDto> reportes = new ArrayList<ReporteFacturasDto>();

			for (Venta venta : ventas) {
				BigDecimal totalVenta = venta.getTotal();
				BigDecimal impuestoVenta = venta.getImpuesto();
				BigDecimal netoVenta = venta.getNeto();
				
				ReporteFacturasDto diarioDto = getCorte(venta.getFecha(), reportes);				
				if ("A".equals(venta.getTipoTicket())){
					String nroFactura = venta.getNumeroTicket();
					FacturaDto factura = new FacturaDto();
					factura.setCliente(venta.getCliente().getNombre());
					factura.setCondicion(venta.getDiscriminacionIVA().getDescripcionCorta());
					factura.setCuit(venta.getCliente().getCuit());
					factura.setNeto(venta.getNeto());
					factura.setIva(venta.getImpuesto());
					
					if (venta.isDevolucion()){
						nroFactura = "NC-".concat(nroFactura);
						diarioDto.setIvaNA(diarioDto.getIvaNA().add(impuestoVenta));
						diarioDto.setNetoNA(diarioDto.getNetoNA().add(netoVenta));
						diarioDto.setTotalNA(diarioDto.getTotalNA().add(totalVenta));
					} else {
						nroFactura = "F-".concat(nroFactura);
						diarioDto.setIvaA(diarioDto.getIvaA().add(impuestoVenta));
						diarioDto.setNetoA(diarioDto.getNetoA().add(netoVenta));
						diarioDto.setTotalA(diarioDto.getTotalA().add(totalVenta));
					}
						
					
					factura.setNroFactura(nroFactura);
					factura.setTotal(totalVenta);

					diarioDto.addfactura(factura);
					
				} else {
					if (venta.isDevolucion()){
						diarioDto.setTotalNB(diarioDto.getTotalNB().add(totalVenta));
					} else {
						diarioDto.setTotalB(diarioDto.getTotalB().add(totalVenta));	
					}
											
				}
			}
			for (ReporteFacturasDto diario: reportes){
				BigDecimal total = diario.getTotalA();
				total = total.add(diario.getTotalB());
				total = total.subtract(diario.getTotalNA());
				total = total.subtract(diario.getTotalNB());
				diario.setTotal(total);
			}
			JasperReport reporteJR = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream("/reports/reportefacturas.jasper"));

			JRDataSource dr = new JRBeanCollectionDataSource(reportes);
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporteJR,
					parameters, dr);
			JasperViewer.viewReport(jasperPrint, false);

		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException(
					"Error al intentar obtener el reporte de ventas", e);
		}

	}

	private ReporteFacturasDto getCorte(Date fecha, List<ReporteFacturasDto> reportes){
		CierreZ cierre = cierreDao.findByFecha(fecha);
		int corte = -1;
		if (Validator.isNotNull(cierre))
			corte = cierre.getNumero().intValue();
			
		ReporteFacturasDto dto = null;
		for (ReporteFacturasDto reporte : reportes) {
			if (reporte.getCorteZ()==corte)
				return reporte;
		}
		dto = new ReporteFacturasDto();
		dto.setFecha(fecha);
		dto.setCorteZ(corte);
		dto.setIvaA(new BigDecimal(0));
		dto.setNetoA(new BigDecimal(0));
		dto.setTotalA(new BigDecimal(0));
		dto.setIvaNA(new BigDecimal(0));
		dto.setNetoNA(new BigDecimal(0));
		dto.setTotalNA(new BigDecimal(0));
		dto.setTotalB(new BigDecimal(0));
		dto.setTotalNB(new BigDecimal(0));
		dto.setTotal(new BigDecimal(0));
		reportes.add(dto);
		return dto;
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public void reporteIngresos(ReportesDto filtro, int tipo) {
		// reporte de transacciones realizadas sin agrupar
		List<ReporteVentasResultadoDTO> ingresos = new ArrayList<ReporteVentasResultadoDTO>();
		BigDecimal totalEfectivo = new BigDecimal(0);
		BigDecimal totaltarjeta = new BigDecimal(0);
		BigDecimal totalNC = new BigDecimal(0);
		BigDecimal totalCC = new BigDecimal(0);
		BigDecimal totalDev = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		try {
			List<Venta> ventas = ventaDao.findByFiltro(filtro);
			for (Venta venta : ventas) {
				ReporteVentasResultadoDTO dto = null;
				if (TRANSACCIONAL == tipo) {
					dto = new ReporteVentasResultadoDTO();
					dto.setFecha(DateHelper.getFecha(venta.getFecha()));
					String nroTRX = "";
					if (venta.isDevolucion())
						nroTRX = "N";
					if ("A".equals(venta.getTipoTicket()))
						nroTRX = nroTRX.concat("A-");
					else
						nroTRX = nroTRX.concat("B-");
					nroTRX = nroTRX.concat(venta.getNumeroTicket());
					dto.setNroTrx(nroTRX);
					ingresos.add(dto);
				} else if (DIARIO == tipo)
					dto = getDTOFecha(ingresos, venta.getFecha());
				else if (MENSUAL == tipo)
					dto = getDTOMes(ingresos, venta.getFecha());
				else if (ANUAL == tipo)
					dto = getDTOAnio(ingresos, venta.getFecha());

				
				if (venta.isVenta()) {
					dto.setTotal(dto.getTotal().add(venta.getTotal()));
					total = total.add(venta.getTotal());
					for (Pago pago : venta.getPagos()) {
						BigDecimal parcialTotal = pago.getPago();

						if (MitnickConstants.Medio_Pago.EFECTIVO.equals(pago
								.getMedioPago().getCodigo())) {
							parcialTotal = parcialTotal.add(dto
									.getTotalEfectivo());
							dto.setTotalEfectivo(parcialTotal);
							totalEfectivo = totalEfectivo.add(pago.getPago());
						}

						if (MitnickConstants.Medio_Pago.DEBITO.equals(pago
								.getMedioPago().getCodigo())) {
							parcialTotal = parcialTotal.add(dto
									.getTotalTarjeta());
							dto.setTotalTarjeta(parcialTotal);
							totaltarjeta = totaltarjeta.add(pago.getPago());
						}

						if (MitnickConstants.Medio_Pago.CREDITO.equals(pago
								.getMedioPago().getCodigo())) {
							parcialTotal = parcialTotal.add(dto
									.getTotalTarjeta());
							dto.setTotalTarjeta(parcialTotal);
							totaltarjeta = totaltarjeta.add(pago.getPago());
						}

						if (MitnickConstants.Medio_Pago.CUENTA_CORRIENTE
								.equals(pago.getMedioPago().getCodigo())) {
							parcialTotal = parcialTotal.add(dto.getTotalCC());
							dto.setTotalCC(parcialTotal);
							totalCC = totalCC.add(pago.getPago());
						}

						if (MitnickConstants.Medio_Pago.NOTA_CREDITO
								.equals(pago.getMedioPago().getCodigo())) {
							parcialTotal = parcialTotal.add(dto.getTotalNC());
							dto.setTotalNC(parcialTotal);
							totalNC = totalNC.add(pago.getPago());
						}
					}
				} else {
					// si es una devolucion no se usan medios de pago - no afecta los totales ya que no se devuelve dinero efectivo
					totalDev = totalDev.add(venta.getTotal());
					dto.setTotalDev(dto.getTotalDev().add(venta.getTotal()));
					dto.setTotal(dto.getTotal().subtract(venta.getTotal()));
				}

			}

			total = total.subtract(totalDev);
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream("/reports/ventas.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("totalEfectivo", totalEfectivo.toString());
			parameters.put("totalTarjeta", totaltarjeta.toString());
			parameters.put("totalNC", totalNC.toString());
			parameters.put("totalCC", totalCC.toString());
			parameters.put("totalDev", totalDev.toString());
			parameters.put("total", total.toString());

			JRDataSource dr = new JRBeanCollectionDataSource(ingresos);

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);
			JasperViewer.viewReport(jasperPrint, false);

		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException(
					"Error al intentar obtener el reporte de ventas", e);
		}

	}
	
	@Transactional(readOnly = true)
	@Override
	public void reporteCaja(ReportesDto filtro) {
		if (filtro.getFechaInicio() == null)
			filtro.setFechaInicio(new Date());
		if (filtro.getFechaFin() == null)
			filtro.setFechaFin(new Date());
		
		// reporte de transacciones realizadas sin agrupar
		List<ComprobanteDto> comprobantes = new ArrayList<ComprobanteDto>();
		BigDecimal totalContado = new BigDecimal(0);		
		BigDecimal totalCredito = new BigDecimal(0);
		BigDecimal totalDebito = new BigDecimal(0);
		BigDecimal totalEfectivo = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		BigDecimal totalDevolucion = new BigDecimal(0);
		
		try {
			List<Venta> ventas = ventaDao.findByFiltro(filtro);
			for (Venta venta : ventas) {
				if (venta.isVenta()) {
					for (Pago pago : venta.getPagos()) {
						if (MitnickConstants.Medio_Pago.EFECTIVO.equals(pago.getMedioPago().getCodigo()))
							totalEfectivo = totalEfectivo.add(pago.getPago());
						if (MitnickConstants.Medio_Pago.DEBITO.equals(pago.getMedioPago().getCodigo()))
							totalDebito = totalDebito.add(pago.getPago());
						if (MitnickConstants.Medio_Pago.CREDITO.equals(pago.getMedioPago().getCodigo()))
							totalCredito = totalCredito.add(pago.getPago());	
						if (MitnickConstants.Medio_Pago.EFECTIVO.equals(pago.getMedioPago().getCodigo())
								|| MitnickConstants.Medio_Pago.DEBITO.equals(pago.getMedioPago().getCodigo())
								||MitnickConstants.Medio_Pago.CREDITO.equals(pago.getMedioPago().getCodigo()))
							totalContado = totalContado.add(pago.getPago());
					}
				} else
					totalDevolucion = totalDevolucion.add(venta.getTotal());			
			}
			
			//nueva funcionalidad (04062013)
			List<Comprobante> recibosNew = cuotaDao.getComprobantes(filtro);
			for (Comprobante recibo: recibosNew) {
				if (Validator.isNotNull(recibo.getCliente()) ){
					ComprobanteDto comprobante = getComprobanteCliente(comprobantes, recibo.getCliente());
					BigDecimal monto = comprobante.getMonto();
					monto = monto.add(recibo.getTotal());
					comprobante.setMonto(monto);
					
				}				
			}

			
			BigDecimal totalRecibo = new BigDecimal(0);
			for (ComprobanteDto c: comprobantes){
				totalRecibo = totalRecibo.add(c.getMonto());
			}
			total = totalContado.add(totalRecibo);
			
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream("/reports/caja.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("totalContado", totalContado.toString());
			parameters.put("totalCredito", totalCredito.toString());
			parameters.put("totalDebito", totalDebito.toString());
			parameters.put("totalEfectivo", totalEfectivo.toString());
			parameters.put("totalDevolucion", totalDevolucion.toString());		
			parameters.put("total", total.toString());
			parameters.put("desde", DateHelper.getFecha(filtro.getFechaInicio()));
			parameters.put("hasta", DateHelper.getFecha(filtro.getFechaFin()));
			parameters.put("totalRecibos", totalRecibo.toString());
			
			JasperPrint jasperPrint = null;
			if (Validator.isEmptyOrNull(comprobantes)){
				ComprobanteDto compTemp = new ComprobanteDto();
				compTemp.setCliente("No hay recibos");
				compTemp.setMonto(new BigDecimal(0));
				comprobantes.add(compTemp);
			}
				
			JRDataSource dr = new JRBeanCollectionDataSource(comprobantes);
			jasperPrint = JasperFillManager.fillReport(reporte,parameters, dr);			
			JasperViewer.viewReport(jasperPrint, false);

		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el reporte de caja");
		} catch (JRException e) {
			throw new BusinessException(
					"Error al intentar obtener el reporte de caja", e);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public void reporteCajero(ReportesDto filtro) {
		this.reporteCaja(filtro);
	}
	
	private ComprobanteDto getComprobanteCliente(List<ComprobanteDto> comprobantes, Cliente cliente){
		for (ComprobanteDto comprobante : comprobantes) {
			if (comprobante.getIdCliente().equals(cliente.getId()))
				return comprobante;
		}
		ComprobanteDto comprobante = new ComprobanteDto();
		comprobante.setIdCliente(cliente.getId());
		comprobante.setCliente(cliente.getNombre());
		comprobantes.add(comprobante);
		return comprobante;
	}
	
	private ReporteVentasResultadoDTO getDTOFecha(
			List<ReporteVentasResultadoDTO> ingresos, Date fecha) {
		for (ReporteVentasResultadoDTO dto : ingresos) {
			String fechaA = DateHelper.getFecha(fecha);
			String fechaB =dto.getFecha();
			if (fechaA.equals(fechaB))
				return dto;
		}
		ReporteVentasResultadoDTO dto = new ReporteVentasResultadoDTO();
		dto.setFecha(DateHelper.getFecha(fecha));
		dto.setTotal(new BigDecimal(0));
		ingresos.add(dto);
		return dto;
	}

	/**
	 * dto.getFecha format: mes - a�o (Enero-2012)
	 * @param ingresos
	 * @param fecha
	 * @return
	 */
	private ReporteVentasResultadoDTO getDTOMes(
			List<ReporteVentasResultadoDTO> ingresos, Date fecha) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha);
		int month = calendario.get(Calendar.MONTH);
		int year = calendario.get(Calendar.YEAR);
		String mes = DateHelper.getMes(month);
		String ano = String.valueOf(year);
		for (ReporteVentasResultadoDTO dto : ingresos) {
			String[] fechaDto = dto.getFecha().split("-");
			if (fechaDto[0].equals(mes) && fechaDto[1].equals(ano))
				return dto;
		}
		ReporteVentasResultadoDTO dto = new ReporteVentasResultadoDTO();
		dto.setFecha(mes.concat("-").concat(ano));
		dto.setTotal(new BigDecimal(0));
		ingresos.add(dto);
		return dto;
	}

	private ReporteVentasResultadoDTO getDTOAnio(
			List<ReporteVentasResultadoDTO> ingresos, Date fecha) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha);
		int year = calendario.get(Calendar.YEAR);
		String ano = String.valueOf(year);
		for (ReporteVentasResultadoDTO dto : ingresos) {
			String fechaDto = dto.getFecha();
			if (fechaDto.equals(ano))
				return dto;
		}
		ReporteVentasResultadoDTO dto = new ReporteVentasResultadoDTO();
		dto.setFecha(ano);
		dto.setTotal(new BigDecimal(0));
		ingresos.add(dto);
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public void consultarEstadoCuentas(ReportesDto filtro) {
		List<CuotaDto> cuotas = new ArrayList<CuotaDto>();
		try {
			cuotas.addAll(entityDTOParser.getDtosFromEntities(cuotaDao
					.findByFiltro(filtro)));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			BigDecimal total = new BigDecimal(0);
			
			for (CuotaDto cuota: cuotas){
				total = total.add(cuota.getFaltaPagar());
				setUltimoMovimiento(cuota.getClienteDto());
			}
			parameters.put("tipoReporte", "Cuotas Detalladas");
			parameters.put("total", total.toString());
			
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/estadoCuenta.jasper"));

			JRDataSource dr = new JRBeanCollectionDataSource(cuotas);

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);

			JasperViewer.viewReport(jasperPrint, false);

		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException(
					"Error al intentar obtener el reporte de ventas", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public void consultarEstadoCuentasPorCliente(ReportesDto filtro) {
		List<CuotaDto> cuotas = new ArrayList<CuotaDto>();
		try {
			cuotas.addAll(entityDTOParser.getDtosFromEntities(cuotaDao
					.findByFiltro(filtro)));
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			BigDecimal total = new BigDecimal(0);
			
			for (CuotaDto cuota: cuotas){
				total = total.add(cuota.getFaltaPagar());
				setUltimoMovimiento(cuota.getClienteDto());
			}
			
			parameters.put("total", total.toString());
			parameters.put("tipoReporte", "Cuentas");
			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/estadoCuenta.jasper"));

			JRDataSource dr = new JRBeanCollectionDataSource(agruparPorCliente(cuotas));

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);

			JasperViewer.viewReport(jasperPrint, false);

		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException(
					"Error al intentar obtener el reporte de ventas", e);
		}
	}
	
	
	private List<CuotaDto> agruparPorCliente(List<CuotaDto> cuotas){
		List<CuotaDto> cuotasAgrupadas = new ArrayList<CuotaDto>();
		for (CuotaDto cuota: cuotas){
			CuotaDto cuotaCliente = getCuotaCliente(cuota, cuotasAgrupadas);
			if (cuotaCliente!=null) {
				BigDecimal apagar = cuotaCliente.getFaltaPagar();
				apagar = apagar.add(cuota.getFaltaPagar());
				cuotaCliente.setFaltaPagar(apagar);
			}
		}
		return cuotasAgrupadas;
	}
	
	private CuotaDto getCuotaCliente(CuotaDto cuota, List<CuotaDto> cuotas){
		CuotaDto retorno = null;
		for (CuotaDto cuotaIn: cuotas){
			if (cuotaIn.getClienteDto().getId().equals(cuota.getClienteDto().getId())){
				retorno = cuotaIn;
			} 
		}
		if (retorno == null){
			cuotas.add(cuota);						
		}			
		return retorno;
	}
	
	@Transactional(readOnly = true)
	@Override
	public void consultarVentaPorArticulo(ReporteMovimientosDto dto) {

		try {
			List<ReporteVentaArticuloDTO> articulos = reporteDao
					.consultarVentaPorArticulo(dto);

			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/VentasProducto.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			JRDataSource dr = new JRBeanCollectionDataSource(articulos);

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);

			JasperViewer.viewReport(jasperPrint, false);

		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException(
					"Error al intentar obtener el reporte de ventas", e);
		}
	}

	public void exportarCompraSugerida(ReporteMovimientosDto dto) {
		try {
			List<ReporteCompraSugeridaDTO> articulos = reporteDao
					.consultarCompraSugerida(dto);

			JasperReport reporte = (JasperReport) JRLoader.loadObject(this
					.getClass().getResourceAsStream(
							"/reports/compraSugerida.jasper"));
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			JRDataSource dr = new JRBeanCollectionDataSource(articulos);

			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, dr);

			JasperViewer.viewReport(jasperPrint, false);

		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el reporte de ventas");
		} catch (JRException e) {
			throw new BusinessException(
					"Error al intentar obtener el reporte de ventas", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void consultarTransaccion(String nroTrx){
		Venta venta = ventaDao.findTransactionByNumeroFactura(nroTrx);
		if (Validator.isNull(venta))
			throw new BusinessException("error.consultarTransaccion.noExiste","No se encuentra una transacci�n con el n�mero ingresado");
		try {
			VentaDto ventaDto = (VentaDto) entityDTOParser.getDtoFromEntity(venta);
			ventaDao.generarFactura(ventaDto, true);
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar obtener el comprobante de la transacci�n");
		}
	}
	
	public void setUltimoMovimiento(ClienteDto cliente) {
		try {
			Date ultimo = null;
			Venta venta = ventaDao.findLastByClient(cliente.getId());
			Comprobante comprobante = null;
			List<Comprobante> comprobantes = clienteDao.findComprobantesByClienteId(cliente.getId());
			if (Validator.isNotEmptyOrNull(comprobantes))
				comprobante = getlast(comprobantes);
			if (Validator.isNotNull(venta))
				ultimo = venta.getFecha();
			if (Validator.isNotNull(comprobante) && Validator.isNotNull(ultimo))
				if (comprobante.getFecha().compareTo(ultimo)>0)
					ultimo = comprobante.getFecha();
			if (Validator.isNotNull(comprobante) && Validator.isNull(ultimo))
				ultimo = comprobante.getFecha();
			if (Validator.isNotNull(ultimo))
				cliente.setUltimoMovimiento(DateHelper.getFecha(ultimo));
					
		} catch (Exception e1) {
			throw new PersistenceException("error.reporte.movimientos.Cliente","Error al generar el reporte de movimientos del cliente.",e1);
		}	
	}
	
	@SuppressWarnings("unchecked")
	private Comprobante getlast(List<Comprobante> comprobantes) {
	       Collections.sort(comprobantes, new Comparator() {  
	 
	           public int compare(Object o1, Object o2) {  
	           	Comprobante e1 = (Comprobante) o1;  
	           	Comprobante e2 = (Comprobante) o2;  
	               return e2.getFecha().compareTo(e1.getFecha());  
	           }  
	       }); 
			return comprobantes.get(0);
		}
}
