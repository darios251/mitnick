package com.mitnick.business.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.ICiudadDao;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.daos.ICuotaDao;
import com.mitnick.persistence.daos.IDireccionDao;
import com.mitnick.persistence.daos.IMedioPagoDAO;
import com.mitnick.persistence.daos.IProvinciaDao;
import com.mitnick.persistence.daos.IVentaDAO;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Comprobante;
import com.mitnick.persistence.entities.Cuota;
import com.mitnick.persistence.entities.Pago;
import com.mitnick.persistence.entities.Provincia;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.MitnickConstants;
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
		@SuppressWarnings("unchecked")
		Cliente cliente = (Cliente) entityDTOParser
				.getEntityFromDto(clienteDto);
		validateEntity(cliente);

		try {
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

	@Override
	public void cargarReporte() {
		clienteDao.cargarReporte();
	}

	public List<CuotaDto> obtenerCuotasPendientes(ClienteDto cliente) {
		@SuppressWarnings("unchecked")
		List<CuotaDto> cuotas = entityDTOParser.getDtosFromEntities(cuotaDao.getCuotaByClienteId(cliente.getId()));
		return cuotas;
	}

	@Transactional
	@Override
	public void eliminarCuota(CuotaDto cuotaDto) {
		if (cuotaDto.getId() == null) {
			throw new BusinessException("error.clienteServicio.id.nulo", "Se invoca la eliminación de una cuota que no existe en la base de datos ya que no se brinda el ID");
		}
		try {
			cuotaDao.eliminarCuota(cuotaDto);
		} catch (PersistenceException e) {
			throw new BusinessException(e, "Error al intentar eliminar el cliente");
		}
	}

	@Transactional
	@Override
	public void guardarCuotas(List<CuotaDto> cuotasDtos) {

		try {
			for (int i = 0; i < cuotasDtos.size(); i++) {
				CuotaDto cuotaDto = cuotasDtos.get(i);
				guardarCuota(cuotaDto);
			}
		} catch (PersistenceException e) {
			throw new BusinessException(e, "Error al intentar guardar las cuotas");
		}
	}

	@Transactional
	@Override
	public void guardarCuota(CuotaDto cuotaDto) {

		try {
			@SuppressWarnings("unchecked")
			Cuota cuota = (Cuota) entityDTOParser.getEntityFromDto(cuotaDto);
			cuota.setFechaPago(new Date());
			cuota = cuotaDao.saveOrUpdate(cuota);
			cuotaDto.setId(cuota.getId());
		} catch (PersistenceException e) {
			throw new BusinessException(e, "Error al intentar guardar las cuotas");
		}
	}

	@Override
	public List<CuotaDto> quitarPago(PagoDto pago, List<CuotaDto> cuotas) {
		if (pago.isComprobante())
			throw new BusinessException("El pago ya gener� comprobante de pago");
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
		while (continuar && cuotasIt.hasNext()){
			BigDecimal total = pago.getMonto();
			CuotaDto cuota = cuotasIt.next();
			if (!cuota.isPagado()) {
				total = agregarPago(pago, cuota);
			}
			if (total.compareTo(new BigDecimal(0))>0){
				MedioPagoDto mp = pago.getMedioPago();
				pago = new PagoDto();
				pago.setMonto(total);
				pago.setComprobante(false);
				pago.setMedioPago(mp);				
			} else
				continuar = false;
		}
		return cuotas;
	}
	
	/**
	 * Agrega el pago ala cuota y retorna el monto sobrante para generar un nuevo pago si corresponde.
	 * @param pago
	 * @param cuota
	 * @return
	 */
	private BigDecimal agregarPago(PagoDto pago, CuotaDto cuota) {
		BigDecimal resto = new BigDecimal(0);
		if (cuota.getFaltaPagar().compareTo(pago.getMonto())<0) {
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
			if (!pDto.isComprobante() && pDto.getMedioPago().getId().equals(pago.getMedioPago().getId()))
				pagoDto = pDto;
		}
		if (pagoDto==null) {
			pagoDto = new PagoDto();
			pagoDto.setComprobante(false);
			pagoDto.setMedioPago(pago.getMedioPago());
			pagoDto.setMonto(pago.getMonto());
			cuota.getPagos().add(pago);
		}
		return pagoDto;
	}

	@Transactional
	@Override
	public void comprobantePago(List<CuotaDto> cuotas) {
		
		for (int i = 0; i < cuotas.size(); i++) {
			VentaHelper.calcularTotales(cuotas.get(i));
		}

		Comprobante comprobante = clienteDao.generarComprobante(cuotas);

		for (int i = 0; i < cuotas.size(); i++) {
			if (cuotas.get(i).getPagos()!=null) {
				Iterator<PagoDto> pagosIt = cuotas.get(i).getPagos().iterator();
				while (pagosIt.hasNext()){
					PagoDto pago = pagosIt.next();
					if (!pago.isComprobante())
						comprobante.addPago((Pago)entityDTOParser.getEntityFromDto(pago));
					pago.setComprobante(true);
				}
			}
			clienteDao.saveOrUpdate(comprobante);
			guardarCuota(cuotas.get(i));
		}
		
	}
	
	public void reporteMovimientosCliente(ClienteDto cliente){
		clienteDao.reporteMovimientosCliente(cliente);
	}
	
	/**
	 * Este metodo se invoca cuando el cliente que realiza la devolucion tiene cuenta corriente con cuotas pendientes de pago.
	 * Este metodo utiliza el credito otorgado por la devolucion para cancelar las cuotas correspondientes.
	 */
	public void pagarCuotasNC(VentaDto venta) {
		List<Cuota> cuotas = cuotaDao.getCuotaByClienteId(venta.getCliente().getId());
		BigDecimal pendiente = new BigDecimal(0);
		if (cuotas!=null){
			Iterator<Cuota> cuotasIt = cuotas.iterator();
			boolean seguir = true;
			List<CuotaDto> cuotasAPagar = new ArrayList<CuotaDto>();
			while (cuotasIt.hasNext() && seguir){
				Cuota cuota = cuotasIt.next();
				pendiente = pendiente.add(cuota.getFaltaPagar());
				cuotasAPagar.add((CuotaDto)entityDTOParser.getDtoFromEntity(cuota));
				if (pendiente.compareTo(venta.getTotal())>=0)
					seguir = false;
			}			
			MedioPagoDto medioPagoDto = (MedioPagoDto)entityDTOParser.getDtoFromEntity(medioPagoDao.getByCode(MitnickConstants.Medio_Pago.NOTA_CREDITO));
			PagoDto pago = new PagoDto();
			pago.setMedioPago(medioPagoDto);
			pago.setMonto(venta.getTotal());
			agregarPago(pago, cuotasAPagar);
			BigDecimal creditoUsado = new BigDecimal(0);
			if (pendiente.compareTo(venta.getTotal())>=0)
				creditoUsado = venta.getTotal();
			else
				creditoUsado = pendiente;
				
			ventaDao.usarCredito(venta.getNumeroTicket(), creditoUsado);
			comprobantePago(cuotasAPagar);
			
		}
	}
		
}
