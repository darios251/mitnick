package com.mitnick.presentacion.controladores;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.utils.VentaManager;
import com.mitnick.presentacion.vistas.PrincipalView;
import com.mitnick.presentacion.vistas.VentaView;
import com.mitnick.presentacion.vistas.controles.JTabbedPaneConBoton;
import com.mitnick.presentacion.vistas.paneles.BuscarProductoPanel;
import com.mitnick.presentacion.vistas.paneles.ClienteNuevoPanel;
import com.mitnick.presentacion.vistas.paneles.DetalleProductoPanel;
import com.mitnick.presentacion.vistas.paneles.PagoPanel;
import com.mitnick.presentacion.vistas.paneles.VendedorDialog;
import com.mitnick.presentacion.vistas.paneles.VentaClientePanel;
import com.mitnick.presentacion.vistas.paneles.VentaPanel;
import com.mitnick.servicio.servicios.IClienteServicio;
import com.mitnick.servicio.servicios.IMedioPagoServicio;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.IVentaServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.VentaHelper;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CreditoDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.MedioPagoDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.TipoCompradorDto;
import com.mitnick.utils.dtos.VentaDto;

@Controller("ventaController")
public class VentaController extends BaseController {

	@Autowired
	private PrincipalView principalView;

	@Autowired
	private VentaView ventaView;
	@Autowired
	private VentaPanel ventaPanel;
	@Autowired
	private PagoPanel pagoPanel;
	@Autowired
	private BuscarProductoPanel buscarProductoPanel;
	@Autowired
	private DetalleProductoPanel detalleProductoPanel;
	@Autowired
	private VentaClientePanel ventaClientePanel;
	@Autowired
	private ClienteNuevoPanel clienteNuevoPanel;

	@Autowired
	private IVentaServicio ventaServicio;

	@Autowired
	private IProductoServicio productoServicio;

	@Autowired
	private IMedioPagoServicio medioPagoServicio;

	@Autowired
	private IClienteServicio clienteServicio;
	
	public VentaController() {

	}

	/**
	 * Este metodo es invocado cuando el cliente cancela la venta actual antes
	 * de finalizarla. Se limpian todas las pantallas de la venta, y se anula la
	 * venta actual.
	 * 
	 */
	public void limpiarVentanasVenta() {
		ventaPanel.limpiarVenta();
		pagoPanel.limpiarCamposPantalla();
		ventaClientePanel.limpiarClientes();
		ventaClientePanel.limpiarComboPantalla();
		buscarProductoPanel.limpiarCamposPantalla();
	}

	public List<CuotaDto> getCuotas(String cuotas, String total) {
		if (Validator.isInt(cuotas))
			return ventaServicio.generarCuotas(Integer.parseInt(cuotas), new BigDecimal(total), VentaManager.getVentaActual()
					.getCliente());
		else
			throw new PresentationException("error.venta.cantidad.cuotas");
	}

	public void guardarCuotas(List<CuotaDto> cuotas) {
		ventaServicio.guardarCuotas(VentaManager.getVentaActual(), cuotas);
	}

	public void mostrarBuscarArticuloPanel() {
		ultimoPanelMostrado = buscarProductoPanel;
		ventaPanel.setVisible(false);
		pagoPanel.setVisible(false);
		detalleProductoPanel.setVisible(false);
		ventaClientePanel.setVisible(false);
		clienteNuevoPanel.setVisible(false);
		buscarProductoPanel.setVisible(true);
		buscarProductoPanel.limpiarCamposPantalla();
		buscarProductoPanel.actualizarPantalla();
	}

	public void mostrarVentasPanel() {

		ultimoPanelMostrado = ventaPanel;
		buscarProductoPanel.setVisible(false);
		pagoPanel.setVisible(false);
		detalleProductoPanel.setVisible(false);
		ventaClientePanel.setVisible(false);
		clienteNuevoPanel.setVisible(false);
		ventaPanel.setVisible(true);
		ventaPanel.actualizarPantalla();
	}

	public void mostrarPagosPanel() {
		// if(Validator.isNull(VentaManager.getVentaActual().getCliente()))
		// throw new PresentationException("error.venta.cliente.null");
		ultimoPanelMostrado = pagoPanel;
		buscarProductoPanel.setVisible(false);
		ventaPanel.setVisible(false);
		detalleProductoPanel.setVisible(false);
		ventaClientePanel.setVisible(false);
		clienteNuevoPanel.setVisible(false);
		pagoPanel.setVisible(true);
		pagoPanel.actualizarPantalla();
	}

	public void mostrarClienteVenta() {
		// valido si puede ir o no a la pantalla de pagos
		if (Validator.isEmptyOrNull(VentaManager.getVentaActual().getProductos()))
			throw new PresentationException("error.venta.pagos.productos.vacios");
		else if (!Validator.isMoreThanZero(VentaManager.getVentaActual().getTotal()))
			throw new PresentationException("error.venta.pagos.total.cero");
		ultimoPanelMostrado = ventaClientePanel;
		buscarProductoPanel.setVisible(false);
		ventaPanel.setVisible(false);
		detalleProductoPanel.setVisible(false);
		pagoPanel.setVisible(false);
		clienteNuevoPanel.setVisible(false);
		ventaClientePanel.setVisible(true);
		if (VentaManager.getVentaActual().isDevolucion() && Validator.isNotNull(VentaManager.getVentaActual().getCliente()))
			ventaClientePanel.actualizarPantallaDevolucion();
		else
			ventaClientePanel.actualizarPantalla();
	}

	public void mostrarClienteNuevoPanel() {
		logger.debug("Entrando a la funcionalidad de agregar cliente");
		ultimoPanelMostrado = clienteNuevoPanel;
		buscarProductoPanel.setVisible(false);
		ventaPanel.setVisible(false);
		detalleProductoPanel.setVisible(false);
		pagoPanel.setVisible(false);
		ventaClientePanel.setVisible(false);
		clienteNuevoPanel.setPanelRetorno(ventaClientePanel);
		clienteNuevoPanel.setVisible(true);
		clienteNuevoPanel.limpiarCamposPantalla();
		clienteNuevoPanel.actualizarPantalla();
	}

	public void mostrarModificarClientePanel() {
		logger.debug("Entrando a la modificacion de clientes");
		ClienteDto cliente = null;
		try {
			int index = getVentaClientePanel().getTable().getSelectedRow();
			if (index > -1)
				index = getVentaClientePanel().getTable().convertRowIndexToModel(index);
			cliente = getVentaClientePanel().getModel().getCliente(index);
		} catch (IndexOutOfBoundsException exception) {
			if (getVentaClientePanel().getModel().getRowCount() == 0) {
				throw new PresentationException("error.ventaClientePanel.clientes.vacio");
			} else {
				throw new PresentationException("error.ventaClientePanel.cliente.noSeleccionado");
			}
		}

		logger.debug("Cliente: " + cliente);
		ultimoPanelMostrado = clienteNuevoPanel;
		buscarProductoPanel.setVisible(false);
		ventaPanel.setVisible(false);
		detalleProductoPanel.setVisible(false);
		pagoPanel.setVisible(false);
		ventaClientePanel.setVisible(false);
		clienteNuevoPanel.setPanelRetorno(ventaClientePanel);
		clienteNuevoPanel.setCliente(cliente);
		clienteNuevoPanel.setVisible(true);
		clienteNuevoPanel.actualizarPantalla();
	}

	public void mostrarDetalleProductoPanel() {
		if (ultimoPanelMostrado == null || !ventaPanel.equals(ultimoPanelMostrado))
			throw new PresentationException("error.venta.detalleProducto.mostrar");

		try {
			int index = ventaPanel.getTable().getSelectedRow();
			if (index > -1)
				index = ventaPanel.getTable().convertRowIndexToModel(index);
			ProductoVentaDto productoVentaDto = ventaPanel.getModel().getProductosVenta(index);

			detalleProductoPanel.setProducto(productoVentaDto);
			ultimoPanelMostrado = detalleProductoPanel;
			buscarProductoPanel.setVisible(false);
			ventaPanel.setVisible(false);
			pagoPanel.setVisible(false);
			detalleProductoPanel.setVisible(true);

			detalleProductoPanel.limpiarCamposPantalla();
			detalleProductoPanel.actualizarPantalla();
		} catch (IndexOutOfBoundsException exception) {
			if (ventaPanel.getModel().getRowCount() == 0) {
				throw new PresentationException("ventaPanel.dialog.warning.emptyModel");
			} else {
				throw new PresentationException("error.venta.producto.cantidad.seleccionarFila");
			}
		}
	}

	public void mostrarUltimoPanelMostrado() {
		if (buscarProductoPanel.equals(ultimoPanelMostrado))
			mostrarBuscarArticuloPanel();
		if (getDetalleProductoPanel().equals(ultimoPanelMostrado))
			mostrarBuscarArticuloPanel();
		else if (pagoPanel.equals(ultimoPanelMostrado)
				&& Validator.isNotEmptyOrNull(VentaManager.getVentaActual().getProductos()))
			mostrarPagosPanel();
		else
			mostrarVentasPanel();
	}

	public void actualizarDevolucion() {
		ventaPanel.actualizarPantalla();
	}

	public void agregarProducto(String codigo) {
		if (Validator.isBlankOrNull(codigo))
			throw new PresentationException("error.venta.agregarProducto.codigo.null");

		if (Validator.isNotBlankOrNull(codigo)) {
			try {
				VentaManager.setVentaActual(ventaServicio.agregarProducto(codigo, VentaManager.getVentaActual()));
			} catch (BusinessException be) {
				throw new PresentationException("error.venta.agregarProducto.productoNoEncontrado");
			}

			ventaPanel.actualizarPantalla();
		} else {
			throw new PresentationException("error.venta.agregarProducto.productoNoEncontrado");
		}
	}

	public void agregarDescuento(DescuentoDto descuento, VentaDto venta, ProductoVentaDto producto) {
		if (Validator.isNull(descuento) || Validator.isNull(venta))
			throw new PresentationException("error.venta.agregarDescuento");

		try {
			if (Validator.isNotNull(producto))
				VentaManager.setVentaActual(ventaServicio.agregarDescuento(descuento, venta, producto));
			else
				VentaManager.setVentaActual(ventaServicio.agregarDescuento(descuento, venta));
					
		} catch (BusinessException be) {
			throw new PresentationException("error.venta.agregarDescuento");
		}

		ventaPanel.actualizarPantalla();
	
	}
	
	public void quitarDescuento(VentaDto venta, ProductoVentaDto producto) {
		if (Validator.isNull(venta))
			throw new PresentationException("error.venta.quitarDescuento");

		try {
			if (Validator.isNotNull(producto))
				VentaManager.setVentaActual(ventaServicio.quitarDescuento(venta, producto));
			else
				VentaManager.setVentaActual(ventaServicio.quitarDescuento(venta));
					
		} catch (BusinessException be) {
			throw new PresentationException("error.venta.quitarDescuento");
		}

		ventaPanel.actualizarPantalla();
	
	}
	
	public void quitarProductoVentaDto() {
		try {
			int index = ventaPanel.getTable().getSelectedRow();
			if (index > -1)
				index = ventaPanel.getTable().convertRowIndexToModel(index);
			ProductoVentaDto productoVentaDto = ventaPanel.getModel().getProductosVenta(index);

			logger.info("Quitando producto: " + productoVentaDto);

			VentaManager.setVentaActual(ventaServicio.quitarProducto(productoVentaDto, VentaManager.getVentaActual()));
			ventaPanel.actualizarPantalla();
		} catch (IndexOutOfBoundsException exception) {
			if (ventaPanel.getModel().getRowCount() == 0) {
				throw new PresentationException("ventaPanel.dialog.warning.emptyModel");
			} else {
				throw new PresentationException("ventaPanel.dialog.warning.noRowSelected");
			}
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

		logger.info("Recalculando totales");
	}

	public void crearNuevaVenta(int tipo) {
		VentaManager.eliminarVenta();
		VentaManager.crearNuevaVenta(tipo);
		limpiarVentanasVenta();
	}

	public boolean checkFinalizarVenta() {
		if (Validator.isNull(VentaManager.getVentaActual()))
			return false;
		return VentaManager.getVentaActual().isPagado() || VentaManager.getVentaActual().isDevolucion();
	}

	public List<MedioPagoDto> getAllMedioPago() {
		return getMedioPagoServicio().obtenerMediosPagos();
	}

	public void agregarPago(MedioPagoDto medioPago, String monto, String numeroNC) {
		logger.debug("Entrado al metodo agregarPago, con medioPago: " + medioPago + " y monto: " + monto);
		if (Validator.isNull(medioPago))
			throw new PresentationException("error.venta.medioPago.null");
		if (Validator.isBlankOrNull(monto))
			throw new PresentationException("error.venta.monto.null");
		if (!Validator.isDouble(monto))
			throw new PresentationException("error.venta.monto.formato");
		PagoDto pago = new PagoDto();
		pago.setNroNC(numeroNC);
		pago.setMedioPago(medioPago);
		pago.setMonto(new BigDecimal(monto));

		try {
			VentaManager.setVentaActual(getVentaServicio().agregarPago(pago, VentaManager.getVentaActual()));
			getPagoPanel().actualizarPantalla();
		} catch (BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar agregar el pago con medio de pago: "
					+ medioPago + " y monto: " + monto);
		}

		finalizarVenta();

		logger.debug("Saliendo del método agregarPago");
	}

	private void getVendedor(){
		
		if (Validator.isNotNull(PropertiesManager.getPropertyAsBoolean("application.venta.vendedor")) && PropertiesManager.getPropertyAsBoolean("application.venta.vendedor").booleanValue()) {
			VendedorDialog vendedorDialog = new VendedorDialog(this.getPrincipalView(), this.getPrincipalView().vendedorController);
			if (Validator.isNotNull(vendedorDialog.getSelected()))
					VentaManager.getVentaActual().setVendedor(vendedorDialog.getSelected());
			else
				getVendedor();
		}
	}
	
	private void getImprimir(){
		int option = JOptionPane.showConfirmDialog((java.awt.Component) null, "Desea imprimir la factura?", "Información", JOptionPane.YES_NO_OPTION);	
		if (option == JOptionPane.NO_OPTION){
			VentaManager.getVentaActual().setPrinted(true);
			VentaManager.getVentaActual().setTipoTicket(MitnickConstants.MODO_ENTRENAMIENTO_TIPO);
		}
	}
	
	public void finalizarVenta() {
		if (checkFinalizarVenta()) {
			try {
				getVendedor();
				getImprimir();
				getVentaServicio().facturar(VentaManager.getVentaActual());
			} catch (BusinessException e) {
				int opcion = getPagoPanel().mostrarMensajeReintentar(e.getMessage());

				if (opcion == 0) {
					finalizarVenta();
					return;
				} else {
					getVentaServicio().cancelar(VentaManager.getVentaActual());
					if (VentaManager.getVentaActual().isVenta()) {
						VentaManager.crearNuevaVenta(VentaManager.getVentaActual().getTipo());
						limpiarVentanasVenta();
						mostrarVentasPanel();
					} else {
						JTabbedPaneConBoton jTabbedPaneConBoton = this.getPrincipalView().jTabbedPaneConBoton;
						jTabbedPaneConBoton.remove(jTabbedPaneConBoton.getSelectedIndex());
					}

					return;
				}
			}
			getPagoPanel().finalizarVenta();
		}
	}

	public BigDecimal obtenerSaldoDeudorCliente() {
		if (Validator.isNull(VentaManager.getVentaActual().getCliente()))
			return new BigDecimal(0);
		return getVentaServicio().getSaldoDeudorCliente(VentaManager.getVentaActual());
	}

	public void quitarPago(PagoDto pagoDto) {
		logger.debug("Entrado al método quitarPago, con pago: " + pagoDto);

		try {
			VentaManager.setVentaActual(getVentaServicio().quitarPago(pagoDto, VentaManager.getVentaActual()));
			pagoPanel.actualizarPantalla();
		} catch (BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar el pago: " + pagoDto);
		}

		logger.debug("Saliendo del método quitaPago");
	}

	public void modificarCantidad(ProductoVentaDto producto, String cantidad) {
		logger.debug("Entrado al método modificarCantidad, con producto: " + producto + " y cantidad: " + cantidad);

		if (Validator.isNull(producto))
			throw new PresentationException("error.venta.modificarCantidad.producto.null",
					"El producto al que se quiere modificar la cantidad es nulo");
		if (Validator.isBlankOrNull(cantidad))
			throw new PresentationException("error.venta.modificarCantidad.cantidad.null");
		if (!Validator.isInt(cantidad))
			throw new PresentationException("error.venta.modificarCantidad.cantidad.int");
		if (!Validator.isInRange(Integer.parseInt(cantidad), 0, 1000))
			throw new PresentationException("error.venta.modificarCantidad.cantidad.rango", new Object[] { "0", "1000" });

		try {
			getVentaServicio().modificarCantidad(producto, Integer.parseInt(cantidad), VentaManager.getVentaActual());
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

		logger.debug("Saliendo del método modificarCantidad");
	}

	public void modificarPrecioUnitario(ProductoVentaDto producto, String precioUnitario) {
		logger.debug("Entrado al método modificarPrecioUnitario, con producto: " + producto + " y precioUnitario: "
				+ precioUnitario);

		if (Validator.isNull(producto))
			throw new PresentationException("error.venta.modificarUnitario.producto.null",
					"El producto al que se quiere modificar el precio unitario es nulo");
		if (Validator.isBlankOrNull(precioUnitario))
			throw new PresentationException("error.venta.modificarPrecioUnitario.precioUnitario.null");
		if (!Validator.isBigDecimal(precioUnitario))
			throw new PresentationException("error.venta.modificarPrecioUnitario.precioUnitario.int");

		try {
			getVentaServicio().modificarPrecioUnitario(producto, new BigDecimal(precioUnitario), VentaManager.getVentaActual());
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

		logger.debug("Saliendo del método modificarPrecioUnitario");
	}

	public List<ClienteDto> obtenerClientesByFilter(ConsultaClienteDto filtroDto) {
		logger.debug("Entrando al método consultarClienteByFilter con :" + filtroDto);

		if (Validator.isNull(filtroDto))
			throw new PresentationException("error.unknown", "El filtro para la consulta de clientes no puede ser nulo");
		if (Validator.isNotBlankOrNull(filtroDto.getDocumento()) && !Validator.isDocumentNumber(filtroDto.getDocumento()))
			throw new PresentationException("error.cliente.consulta.documento.format");

		List<ClienteDto> clientes = null;
		try {
			clientes = clienteServicio.consultarCliente(filtroDto);
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

		if (Validator.isEmptyOrNull(clientes))
			throw new PresentationException("error.cliente.consulta.clientes.null");

		logger.debug("Saliendo del método consultarClienteByFilter");

		return clientes;
	}

	public void quitarCliente() {
		logger.debug("Entrando al método quitarCliente");

		try {
			ventaServicio.quitarCliente(VentaManager.getVentaActual());
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

		logger.info("El cliente se quitó correctamente de la venta.");
		logger.debug("Saliendo del método quitarCliente");
	}

	public BigDecimal agregarCliente() {
		logger.debug("Entrando al método agregarCliente");

		ClienteDto cliente = null;
		try {
			int index = getVentaClientePanel().getTable().getSelectedRow();
			if (index > -1) {
				index = getVentaClientePanel().getTable().convertRowIndexToModel(index);
				cliente = getVentaClientePanel().getModel().getCliente(index);	
				logger.info("El cliente : " + cliente + " se agregó correctamente a la venta.");
				ventaServicio.agregarCliente(cliente, VentaManager.getVentaActual());
			} else
				cliente = VentaManager.getVentaActual().getCliente();
			
		} catch (IndexOutOfBoundsException exception) {
			if (getVentaClientePanel().getModel().getRowCount() == 0) {
				throw new PresentationException("error.ventaClientePanel.clientes.vacio");
			} else {
				throw new PresentationException("error.ventaClientePanel.cliente.noSeleccionado");
			}
		}

		try {
			
			logger.debug("Saliendo del método agregarCliente");
			if (Validator.isNotNull(cliente))
				return clienteServicio.getSaldoDeudor(cliente);
			else
				return new BigDecimal(0);
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

	}

	public BigDecimal getSaldoDeudor() {
		logger.debug("Entrando al método getSaldoDeudor");
		ClienteDto cliente = VentaManager.getVentaActual().getCliente();
		try {
			if (Validator.isNotNull(cliente))
				return clienteServicio.getSaldoDeudor(cliente);
			else
				return new BigDecimal(0);
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

	}

	public void validarCliente() {
		logger.debug("Entrando al método validarCliente");

		ClienteDto cliente = null;
		try {
			int index = getVentaClientePanel().getTable().getSelectedRow();
			if (index > -1)
				index = getVentaClientePanel().getTable().convertRowIndexToModel(index);
			cliente = getVentaClientePanel().getModel().getCliente(index);
		} catch (IndexOutOfBoundsException exception) {
			if (getVentaClientePanel().getModel().getRowCount() == 0) {
				throw new PresentationException("error.ventaClientePanel.clientes.vacio");
			} else {
				throw new PresentationException("error.ventaClientePanel.cliente.noSeleccionado");
			}
		}

		try {
			ventaServicio.validarCliente(cliente);
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

		logger.debug("Saliendo del método validarCliente");
	}

	public void validarTotalVenta() {
		if (!Validator.isMoreThanZero(VentaManager.getVentaActual().getTotal()))
			throw new PresentationException("error.venta.pagos.total.cero");
	}

	public void desagregarCliente() {
		logger.debug("Entrando al método desagregarCliente");

		try {
			ventaServicio.desagregarCliente(VentaManager.getVentaActual());
		} catch (BusinessException e) {
			throw new PresentationException(e);
		}

		logger.debug("Saliendo del método desagregarCliente");
	}

	public void setTipoResponsable(TipoCompradorDto tipoComprador) {
		if (Validator.isNotNull(VentaManager.getVentaActual())){
			VentaManager.getVentaActual().setTipoResponsabilidad(tipoComprador);
			//los totales varian segun el tipo de facturacion ya que el impuesto se calcula diferente
			VentaHelper.calcularTotales(VentaManager.getVentaActual());
		}
			
	}

	public VentaView getVentaView() {
		return ventaView;
	}

	public PrincipalView getPrincipalView() {
		return principalView;
	}

	public VentaPanel getVentaPanel() {
		return ventaPanel;
	}

	public PagoPanel getPagoPanel() {
		return pagoPanel;
	}

	public BuscarProductoPanel getBuscarProductoPanel() {
		return buscarProductoPanel;
	}

	public VentaClientePanel getVentaClientePanel() {
		return ventaClientePanel;
	}

	protected IMedioPagoServicio getMedioPagoServicio() {
		if (Validator.isNull(medioPagoServicio))
			throw new PresentationException("error.unknown", "El servicio: " + medioPagoServicio.getClass().getSimpleName()
					+ " no ha sido inyectado.");
		return medioPagoServicio;
	}

	protected IVentaServicio getVentaServicio() {
		if (Validator.isNull(ventaServicio))
			throw new PresentationException("error.unknown", "El servicio: " + ventaServicio.getClass().getSimpleName()
					+ " no ha sido inyectado.");
		return ventaServicio;
	}

	protected IProductoServicio getProductoServicio() {
		if (Validator.isNull(productoServicio))
			throw new PresentationException("error.unknown", "El servicio: " + productoServicio.getClass().getSimpleName()
					+ " no ha sido inyectado.");
		return productoServicio;
	}

	public DetalleProductoPanel getDetalleProductoPanel() {
		if (Validator.isNull(detalleProductoPanel))
			throw new PresentationException("error.unknown", "El panel: " + detalleProductoPanel.getClass().getSimpleName()
					+ " no ha sido inyectado.");
		return detalleProductoPanel;
	}

	public ClienteNuevoPanel getClienteNuevoPanel() {
		return clienteNuevoPanel;
	}

	public VentaDto getVentaByNroFacturaTipo(String nroTicket, String tipo, int numeroCaja) {
		return ventaServicio.getVentaByNroFacturaTipo(nroTicket, tipo, numeroCaja);
	}

	public void pagarCuotasNC() {
		clienteServicio.pagarCuotasNC(VentaManager.getVentaActual());
	}

	public CreditoDto obtenerCredito(String nroNC) {
		return ventaServicio.obtenerCredito(nroNC);
	}
	
	public void getDevolucionFromVenta(VentaDto venta, VentaDto devolucion) {
		ventaServicio.getDevolucionFromVenta(venta, devolucion);
	}
}