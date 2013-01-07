package com.mitnick.presentacion.controladores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.ClienteView;
import com.mitnick.presentacion.vistas.paneles.ClienteNuevoPanel;
import com.mitnick.presentacion.vistas.paneles.ClientePanel;
import com.mitnick.presentacion.vistas.paneles.CuentaCorrientePagoPanel;
import com.mitnick.presentacion.vistas.paneles.CuentaCorrientePanel;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.IMedioPagoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.AuthorizationRequired;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.DireccionDto;
import com.mitnick.utils.dtos.MedioPagoDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProvinciaDto;

@Controller("clienteController")
public class ClienteController extends BaseController {
	
	@Autowired protected ClienteView clienteView;
	
	@Autowired protected ClientePanel clientePanel;
	
	@Autowired private ClienteNuevoPanel clienteNuevoPanel;
	
	@Autowired private CuentaCorrientePanel cuentaCorrientePanel;
	
	@Autowired private IClienteServicio clienteServicio;
	
	@Autowired private CuentaCorrientePagoPanel cuentaCorrientePagoPanel;
	
	@Autowired private IMedioPagoServicio medioPagoServicio;
	
	public ClienteController() {
		
	}
	
	public void mostrarCuentaCorrientePagoPanel() {
		ultimoPanelMostrado = cuentaCorrientePagoPanel;
		int[] indexs = getCuentaCorrientePanel().getTable().getSelectedRows();
		List<CuotaDto> cuotas = new ArrayList<CuotaDto>();
		for (int i = 0; i < indexs.length; i++) {
			cuotas.add(getCuentaCorrientePanel().getModel().getCuota(indexs[i]));
		}
		cuentaCorrientePagoPanel.setCuotas(cuotas);
		cuentaCorrientePagoPanel.setCliente(cuentaCorrientePanel.getCliente());
		cuentaCorrientePanel.setVisible(false);
		cuentaCorrientePagoPanel.setVisible(true);
		cuentaCorrientePagoPanel.actualizarPantalla();
	}
	
	public List<ClienteDto> obtenerClientesByFilter(ConsultaClienteDto filtroDto) {
		logger.debug("Entrando al método obtenerClientesByFilter con :" + filtroDto);
		
		if(Validator.isNull(filtroDto))
			throw new PresentationException("error.unknown", "El filtro para la consulta de clientes no puede ser nulo");
		if(!Validator.isBlankOrNull(filtroDto.getDocumento()) && !Validator.isDocumentNumber(filtroDto.getDocumento()))
			throw new PresentationException("error.cliente.consulta.documento.format");
		
		List<ClienteDto> clientes = null;
		try {
			clientes = clienteServicio.consultarCliente(filtroDto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
		}
		
		if(Validator.isEmptyOrNull(clientes))
			throw new PresentationException("error.cliente.consulta.clientes.null");
		
		logger.debug("Saliendo del método consultarClienteByFilter");
		
		return clientes;
	}
	
	public List<ProvinciaDto> obtenerProvincias() {
		List<ProvinciaDto> provincias = null;
		
		try {
			provincias = clienteServicio.obtenerProvincias();
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
		}
		
		if(Validator.isEmptyOrNull(provincias))
			throw new PresentationException("error.cliente.nuevo.provincias.null");
		
		return provincias;
	}
	
	public List<CiudadDto> obtenerCiudadesPorProvincia(ProvinciaDto provincia) {
		List<CiudadDto> ciudades = null;
		
		try {
			ciudades = clienteServicio.obtenerCiudades(provincia);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		catch(Exception e) {
			throw new PresentationException("error.unkwon", e.getMessage());
		}
		
		if(Validator.isNull(ciudades))
			throw new PresentationException("error.cliente.nuevo.ciudades.null");
		
		return ciudades;
	}

	public void mostrarClientePanel() {
		ultimoPanelMostrado = clientePanel;
		cuentaCorrientePagoPanel.setVisible(false);
		cuentaCorrientePanel.setVisible(false);
		clienteNuevoPanel.setVisible(false);
		clientePanel.setVisible(true);
	}
	
	public void mostrarClienteNuevoPanel() {
		ultimoPanelMostrado = clienteNuevoPanel;
		clientePanel.setVisible(false);
		clienteNuevoPanel.setVisible(true);
		clienteNuevoPanel.actualizarPantalla();
		cleanFields();
	}
	
	public void mostrarCuentaCorrientePanel() {
		ultimoPanelMostrado = cuentaCorrientePanel;
		clientePanel.setVisible(false);		
		cuentaCorrientePagoPanel.setVisible(false);
		cuentaCorrientePanel.setVisible(true);
		cuentaCorrientePanel.actualizarPantalla();
	}
	
	private void guardarClienteAut(ClienteDto cliente) {
		try {
			clienteServicio.guardarCliente(cliente);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
	}

	@AuthorizationRequired
	public void guardarCliente(ClienteDto cliente, String actividad, String nombre, String documento,
			String cuit, String telefono, String email, String fechaNacimiento, String domicilio, String codigoPostal, CiudadDto ciudad) {
		
		if(Validator.isNull(cliente))
			cliente = new ClienteDto();
		
		cliente.setActividad(actividad);
		cliente.setNombre(nombre);
		cliente.setDocumento(documento);
		cliente.setCuit(cuit);
		cliente.setEmail(email);
		cliente.setFechaNacimiento(fechaNacimiento);
		cliente.setTelefono(telefono);
		if(cliente.getDireccion() == null)
			cliente.setDireccion(new DireccionDto());
		cliente.getDireccion().setDomicilio(domicilio);
		cliente.getDireccion().setCodigoPostal(codigoPostal);
		cliente.getDireccion().setCiudad(ciudad);
		
		//valido el dto
		validateDto(cliente);
		
		guardarClienteAut(cliente);
		
	}
	
	public void nuevoCliente() {
		clienteNuevoPanel.setCliente(null);
		mostrarClienteNuevoPanel();
	}

	@AuthorizationRequired
	public void editarCliente() {
		ClienteDto clienteDto = null;
		try {
			int index = getClientePanel().getTable().getSelectedRow();
			clienteDto = getClientePanel().getModel().getCliente(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getClientePanel().getModel().getRowCount() == 0) {
				throw new PresentationException("error.clientePanel.clientes.editar.vacio");
			}
			else {
				throw new PresentationException("error.clientePanel.cliente.editar.noSeleccionado");
			}
		}
		
		try {
			clienteNuevoPanel.setCliente(clienteDto);
			mostrarClienteNuevoPanel();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar editar el cliente");
		}
	}

	@AuthorizationRequired
	public void eliminarCliente() {
		ClienteDto clienteDto = null;
		try {
			int index = getClientePanel().getTable().getSelectedRow();
			clienteDto = getClientePanel().getModel().getCliente(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getClientePanel().getModel().getRowCount() == 0) {
				throw new PresentationException("error.clientePanel.clientes.vacio");
			}
			else {
				throw new PresentationException("error.clientePanel.cliente.noSeleccionado");
			}
		}
		
		try {
			getClienteServicio().eliminarCliente(clienteDto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar el cliente");
		}
	}
	
	public void eliminarCuota() {
		CuotaDto cuotaDto = null;
		int index = -1;
		try {
			index = getCuentaCorrientePanel().getTable().getSelectedRow();
			cuotaDto = getCuentaCorrientePanel().getModel().getCuota(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getCuentaCorrientePanel().getModel().getRowCount() == 0) {
				throw new PresentationException("error.cuentaPanel.cuentas.vacio");
			}
			else {
				throw new PresentationException("error.cuentaPanel.cuota.noSeleccionado");
			}
		}
		
		try {
			getClienteServicio().eliminarCuota(cuotaDto);
			getCuentaCorrientePanel().getModel().remove(index);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar la cuota");
		}
	}


	public void cuentaCorriente() {
		ClienteDto clienteDto = null;
		try {
			int index = getClientePanel().getTable().getSelectedRow();
			clienteDto = getClientePanel().getModel().getCliente(index);
			actualizarCuotas(clienteDto);
			cuentaCorrientePanel.setCliente(clienteDto);
			mostrarCuentaCorrientePanel();
		}
		catch (IndexOutOfBoundsException exception) {
			if(getClientePanel().getModel().getRowCount() == 0) {
				throw new PresentationException("error.clientePanel.clientes.editar.vacio");
			}
			else {
				throw new PresentationException("error.clientePanel.cliente.editar.noSeleccionado");
			}
		} catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar editar el cliente");
		}
	}
	
	public void actualizarCuotas(ClienteDto clienteDto){
		List<CuotaDto> cuotas = clienteServicio.obtenerCuotasPendientes(clienteDto);
		cuentaCorrientePanel.setCuotas(cuotas);
	}
	
	public void guardarCuota(CuotaDto cuotaDto){		
		validateDto(cuotaDto);
		clienteServicio.guardarCuota(cuotaDto);
	}
		
	
	public void quitarPago(PagoDto pagoDto) {
		logger.debug("Entrado al método quitarPago, con pago: " + pagoDto);
		
		try {
			List<CuotaDto> cuotas = getClienteServicio().quitarPago(pagoDto, cuentaCorrientePagoPanel.getCuotas());
			cuentaCorrientePagoPanel.setCuotas(cuotas);
			cuentaCorrientePagoPanel.actualizarPantalla();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar el pago: " + pagoDto);
		}
		
		logger.debug("Saliendo del método quitaPago");
	}
	
	public void agregarPago(MedioPagoDto medioPago, String monto) {
		logger.debug("Entrado al metodo agregarPago, con medioPago: " + medioPago + " y monto: " + monto);
		if(Validator.isNull(medioPago))
			throw new PresentationException("error.venta.medioPago.null");
		if(Validator.isBlankOrNull(monto))
			throw new PresentationException("error.venta.monto.null");
		if(!Validator.isDouble(monto))
			throw new PresentationException("error.venta.monto.formato");
		PagoDto pago = new PagoDto();
		pago.setMedioPago(medioPago);
		pago.setMonto(new BigDecimal(monto));
		
		try {
			List<CuotaDto> cuotas = getClienteServicio().agregarPago(pago, cuentaCorrientePagoPanel.getCuotas());
			cuentaCorrientePagoPanel.setCuotas(cuotas);
			cuentaCorrientePagoPanel.actualizarPantalla();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(),"Hubo un error al intentar agregar el pago con medio de pago: " + medioPago + " y monto: " + monto);
		}
		
		finalizarPagoCuota();
		
		logger.debug("Saliendo del método agregarPago");
	}
	
	public void finalizarPagoCuotaParcial() {
		try {
			getClienteServicio().comprobantePago(getCuentaCorrientePagoPanel().getCuotas());
		}
		catch(BusinessException e) {
			int opcion = getCuentaCorrientePagoPanel().mostrarMensajeReintentar();
			
			if(opcion == 0) {
				finalizarPagoCuota();
				return;
			}
			else {
				mostrarCuentaCorrientePanel();
				return;
			}
		}
		actualizarCuotas(getCuentaCorrientePagoPanel().getCliente());
		mostrarCuentaCorrientePanel();
	}
	
	private void finalizarPagoCuota() {
		if(checkFinalizarPagoCuota()) {
			try {
				getClienteServicio().comprobantePago(getCuentaCorrientePagoPanel().getCuotas());
			}
			catch(BusinessException e) {
				int opcion = getCuentaCorrientePagoPanel().mostrarMensajeReintentar();
				
				if(opcion == 0) {
					finalizarPagoCuota();
					return;
				}
				else {
					mostrarCuentaCorrientePanel();
					return;
				}
			}
			getCuentaCorrientePagoPanel().finalizarPagos();
		}
	}	
	
	private boolean checkFinalizarPagoCuota() {
		List<CuotaDto> cuotas = getCuentaCorrientePagoPanel().getCuotas(); 
		for (int i = 0; i < cuotas.size(); i++) {
			CuotaDto cuota = cuotas.get(i);
			if(!cuota.isPagado())
				return false;
		}		
		return true;
	}
	
	public List<MedioPagoDto> getMediosPago() {
		List<MedioPagoDto> pagos = getMedioPagoServicio().obtenerMediosPagos();		
		return pagos;
	}
	
	public List<MedioPagoDto> getMediosPagoCuentaCorriente() {
		List<MedioPagoDto> pagos = getMedioPagoServicio().obtenerMediosPagosCuentaCorriente();		
		return pagos;
	}
	
	public void cargarReporte() {
		clienteServicio.cargarReporte();		
	}
	
	protected IClienteServicio getClienteServicio() {
		return clienteServicio;
	}
	
	public ClienteView getClienteView() {
		return clienteView;
	}

	public ClientePanel getClientePanel() {
		return clientePanel;
	}

	public ClienteNuevoPanel getClienteNuevoPanel() {
		return clienteNuevoPanel;
	}

	public CuentaCorrientePanel getCuentaCorrientePanel() {
		return cuentaCorrientePanel;
	}
	
	public void setCuentaCorrientePanel(CuentaCorrientePanel cuentaCorrientePanel) {
		this.cuentaCorrientePanel = cuentaCorrientePanel;
	}
	
	public CuentaCorrientePagoPanel getCuentaCorrientePagoPanel() {
		return cuentaCorrientePagoPanel;
	}
	
	public void setCuentaCorrientePagoPanel(
			CuentaCorrientePagoPanel cuentaCorrientePagoPanel) {
		this.cuentaCorrientePagoPanel = cuentaCorrientePagoPanel;
	}
	
	protected IMedioPagoServicio getMedioPagoServicio() {
		if(Validator.isNull(medioPagoServicio))
			throw new PresentationException("error.unknown", "El servicio: " + medioPagoServicio.getClass().getSimpleName() + " no ha sido inyectado.");
		return medioPagoServicio;
	}


	@Override
	public void mostrarUltimoPanelMostrado() {
		
	}
	
}
