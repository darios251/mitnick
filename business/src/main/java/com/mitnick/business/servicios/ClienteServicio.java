package com.mitnick.business.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.mitnick.persistence.daos.IProvinciaDao;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Cuota;
import com.mitnick.persistence.entities.Provincia;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.VentaHelper;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;
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
	public void guardarCuotas(List<CuotaDto> cuotasDtos) {

		try {
			for (int i = 0; i < cuotasDtos.size(); i++) {
				CuotaDto cuotaDto = cuotasDtos.get(i);
				guardarCuota(cuotaDto);
			}
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar guardar las cuotas");
		}
	}

	@Transactional
	@Override
	public void guardarCuota(CuotaDto cuotaDto) {

		try {
			Cuota cuota = (Cuota) entityDTOParser.getEntityFromDto(cuotaDto);
			cuota = cuotaDao.saveOrUpdate(cuota);
			cuotaDto.setId(cuota.getId());
		} catch (PersistenceException e) {
			throw new BusinessException(e,
					"Error al intentar guardar las cuotas");
		}
	}

	@Override
	public CuotaDto quitarPago(PagoDto pago, CuotaDto cuota) {
		cuota.getPagos().remove(pago);
		VentaHelper.calcularTotales(cuota);
		return cuota;
	}

	@Override
	public CuotaDto agregarPago(PagoDto pago, CuotaDto cuota) {
		validarPago(pago, cuota);

		PagoDto pagoDto = getPagoDto(pago, cuota);

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
		return cuota;
	}

	private void validarPago(PagoDto pago, CuotaDto cuota) {
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
			if (pDto.getMedioPago().getId().equals(pago.getMedioPago().getId()))
				pagoDto = pDto;
		}
		return pagoDto;
	}

	@Transactional
	@Override
	public void comprobantePago(CuotaDto cuotaDto) {
		VentaHelper.calcularTotales(cuotaDto);

		if (!cuotaDto.isPagado()) {
			throw new BusinessException("error.ventaServicio.facturar",
					"No se puede impriir comprobante de pago ya que no se pago el total");
		}
		guardarCuota(cuotaDto);

		// ventaDao.generarFactura(venta);

	}
}
