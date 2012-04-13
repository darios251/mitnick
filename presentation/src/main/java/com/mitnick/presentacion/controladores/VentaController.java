package com.mitnick.presentacion.controladores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.utils.VentaManager;
import com.mitnick.presentacion.vistas.BaseView;
import com.mitnick.presentacion.vistas.VentaView;
import com.mitnick.presentacion.vistas.paneles.BuscarProductoPanel;
import com.mitnick.presentacion.vistas.paneles.DetalleProductoPanel;
import com.mitnick.presentacion.vistas.paneles.PagoPanel;
import com.mitnick.presentacion.vistas.paneles.VentaPanel;
import com.mitnick.servicio.servicios.IMedioPagoServicio;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.IVentaServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.MedioPagoDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;

@Controller("ventaController")
public class VentaController extends BaseController {

	private static Logger logger = Logger.getLogger(VentaController.class);
	
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
	private	IVentaServicio ventaServicio;
	
	@Autowired
	private IProductoServicio productoServicio;
	
	@Autowired
	private IMedioPagoServicio medioPagoServicio;
	
	private BaseView ultimoPanelMostrado = null;
	
	public VentaController() {
		
	}

	public void mostrarBuscarArticuloPanel() {
		ultimoPanelMostrado = buscarProductoPanel;
		ventaPanel.setVisible(false);
		pagoPanel.setVisible(false);
		detalleProductoPanel.setVisible(false);
		buscarProductoPanel.setVisible(true);
		buscarProductoPanel.limpiarCamposPantalla();
		buscarProductoPanel.actualizarPantalla();
	}
	
	public void mostrarVentasPanel() {
		// TODO: preguntar si es necesaria esta validación
		// valido si puede mostrar el panel de ventas
		if(pagoPanel != null && pagoPanel.equals(ultimoPanelMostrado) && VentaManager.getVentaActual()!= null && !VentaManager.getVentaActual().getPagos().isEmpty())
			throw new PresentationException("error.venta.pagos.noVacio");
		
		ultimoPanelMostrado = ventaPanel;
		buscarProductoPanel.setVisible(false);
		pagoPanel.setVisible(false);
		detalleProductoPanel.setVisible(false);
		ventaPanel.setVisible(true);
		ventaPanel.actualizarPantalla();
	}
	
	public void mostrarPagosPanel() {
		// valido si puede ir o no a la pantalla de pagos
		if(VentaManager.getVentaActual().getProductos().isEmpty())
			throw new PresentationException("error.venta.pagos.productos.vacios");
		else if(BigDecimal.ZERO.equals(VentaManager.getVentaActual().getTotal()))
			throw new PresentationException("error.venta.pagos.total.cero");
		
		ultimoPanelMostrado = pagoPanel;
		buscarProductoPanel.setVisible(false);
		ventaPanel.setVisible(false);
		detalleProductoPanel.setVisible(false);
		pagoPanel.setVisible(true);
		pagoPanel.actualizarPantalla();
	}
	
	public void mostrarDetalleProductoPanel() {
		if(ultimoPanelMostrado == null || !ventaPanel.equals(ultimoPanelMostrado))
			throw new PresentationException("error.venta.detalleProducto.mostrar");
		
		try {
			int index = ventaPanel.getTable().getSelectedRow();
			ProductoVentaDto productoVentaDto = ventaPanel.getModel().getProductosVenta(index);
			
			ultimoPanelMostrado = detalleProductoPanel;
			buscarProductoPanel.setVisible(false);
			ventaPanel.setVisible(false);
			pagoPanel.setVisible(false);
			detalleProductoPanel.setVisible(true);
			detalleProductoPanel.setProducto(productoVentaDto);
			detalleProductoPanel.limpiarCamposPantalla();
			detalleProductoPanel.actualizarPantalla();
		}
		catch (IndexOutOfBoundsException exception) {
			if(ventaPanel.getModel().getRowCount() == 0) {
				throw new PresentationException("ventaPanel.dialog.warning.emptyModel");
			}
			else {
				throw new PresentationException("ventaPanel.dialog.warning.noRowSelected");
			}
		}
	}
	
	public void mostrarUltimoPanelMostrado () {
		if(buscarProductoPanel.equals(ultimoPanelMostrado))
			mostrarBuscarArticuloPanel();
		if(getDetalleProductoPanel().equals(ultimoPanelMostrado))
			mostrarBuscarArticuloPanel();
		else if(pagoPanel.equals(ultimoPanelMostrado) && !VentaManager.getVentaActual().getProductos().isEmpty())
			mostrarPagosPanel();
		else
			mostrarVentasPanel();
	}
	
	public void limpiarVenta() {
		ventaPanel.limpiarCamposPantalla();
		buscarProductoPanel.limpiarCamposPantalla();
		pagoPanel.limpiarCamposPantalla();
	}

	public void agregarProducto(String codigo) {
		if(Validator.isBlankOrNull(codigo))
			throw new PresentationException("error.venta.agregarProducto.codigo.null");
		
		ConsultaProductoDto filtro = new ConsultaProductoDto();
		filtro.setCodigo(codigo);
		
		List<ProductoDto> productos = null;
		
		try {
			productos = new ArrayList<ProductoDto>(getProductoServicio().consultaProducto(filtro));
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		
		if(Validator.isNotEmptyOrNull(productos)) {
			VentaManager.setVentaActual(ventaServicio.agregarProducto(productos.get(0), VentaManager.getVentaActual()));
			ventaPanel.actualizarPantalla();
		}
		else {
			throw new PresentationException("error.venta.agregarProducto.productoNoEncontrado");
		}
	}

	public void quitarProductoVentaDto() {
		logger.info("Quitando producto ... ");

		try {
			int index = ventaPanel.getTable().getSelectedRow();
			ProductoVentaDto productoVentaDto = ventaPanel.getModel().getProductosVenta(index);
			
			VentaManager.setVentaActual(ventaServicio.quitarProducto(productoVentaDto, VentaManager.getVentaActual()));
			ventaPanel.actualizarPantalla();
		}
		catch (IndexOutOfBoundsException exception) {
			if(ventaPanel.getModel().getRowCount() == 0) {
				throw new PresentationException("ventaPanel.dialog.warning.emptyModel");
			}
			else {
				throw new PresentationException("ventaPanel.dialog.warning.noRowSelected");
			}
		}
		catch (BusinessException e) {
			throw new PresentationException(e);
		}
		
		logger.info("Recalculando totales");
	}
	
	public void crearNuevaVenta() {
		VentaManager.crearNuevaVenta();
		limpiarVenta();
	}

	public boolean checkFinalizarVenta() {
		if(VentaManager.getVentaActual() != null && (VentaManager.getVentaActual().getPagos() == null || VentaManager.getVentaActual().getPagos().isEmpty()))
			return false;
		if(!VentaManager.getVentaActual().isPagado())
			return false;
		
		return true;
	}

	public List<MedioPagoDto> getAllMedioPago() {
		return getMedioPagoServicio().obtenerMediosPagos();
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
			VentaManager.setVentaActual(getVentaServicio().agregarPago(pago, VentaManager.getVentaActual()));
			getPagoPanel().actualizarPantalla();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(),"Hubo un error al intentar agregar el pago con medio de pago: " + medioPago + " y monto: " + monto);
		}
		
		finalizarVenta();
		
		logger.debug("Saliendo del método agregarPago");
	}
	
	protected void finalizarVenta() {
		if(checkFinalizarVenta()) {
			try {
				getVentaServicio().facturar(VentaManager.getVentaActual());
			}
			catch(BusinessException e) {
				int opcion = getPagoPanel().mostrarMensajeReintentar();
				
				if(opcion == 0) {
					finalizarVenta();
					return;
				}
				else {
					VentaManager.crearNuevaVenta();
					mostrarVentasPanel();
					return;
				}
			}
			getPagoPanel().finalizarVenta();
		}
	}
	
	public void quitarPago(PagoDto pagoDto) {
		logger.debug("Entrado al método quitarPago, con pago: " + pagoDto);
		
		try {
			VentaManager.setVentaActual(getVentaServicio().quitarPago(pagoDto, VentaManager.getVentaActual()));
			pagoPanel.actualizarPantalla();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar el pago: " + pagoDto);
		}
		
		logger.debug("Saliendo del método quitaPago");
	}
	
	public void modificarCantidad(ProductoVentaDto producto, String cantidad) {
		logger.debug("Entrado al método modificarCantidad, con producto: " + producto + " y cantidad: " + cantidad);
		
		if(Validator.isNull(producto))
			throw new PresentationException("error.venta.modificarCantidad.producto.null", "El producto al que se quiere modificar la cantidad es nulo");
		if(Validator.isBlankOrNull(cantidad))
			throw new PresentationException("error.venta.modificarCantidad.cantidad.null");
		if(!Validator.isInt(cantidad))
			throw new PresentationException("error.venta.modificarCantidad.cantidad.int");
		if(!Validator.isInRange(Integer.parseInt(cantidad), 0, 1000))
			throw new PresentationException("error.venta.modificarCantidad.cantidad.rango", new Object[] {"0", "1000"});
		
		try {
			getVentaServicio().modificarCantidad(producto, Integer.parseInt(cantidad), VentaManager.getVentaActual());
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		
		logger.debug("Saliendo del método modificarCantidad");
	}
	
	public VentaView getVentaView() {
		return ventaView;
	}

	public void setVentaView(VentaView ventaView) {
		this.ventaView = ventaView;
	}

	public VentaPanel getVentaPanel() {
		return ventaPanel;
	}

	public void setVentaPanel(VentaPanel ventaPanel) {
		this.ventaPanel = ventaPanel;
	}

	public PagoPanel getPagoPanel() {
		return pagoPanel;
	}

	public void setPagoPanel(PagoPanel pagoPanel) {
		this.pagoPanel = pagoPanel;
	}

	public BuscarProductoPanel getBuscarProductoPanel() {
		return buscarProductoPanel;
	}

	public void setBuscarProductoPanel(BuscarProductoPanel buscarProductoPanel) {
		this.buscarProductoPanel = buscarProductoPanel;
	}
	
	protected IMedioPagoServicio getMedioPagoServicio() {
		if(Validator.isNull(medioPagoServicio))
			throw new PresentationException("error.unknown", "El servicio: " + medioPagoServicio.getClass().getSimpleName() + " no ha sido inyectado.");
		return medioPagoServicio;
	}
	
	public void setMedioPagoServicio(IMedioPagoServicio medioPagoServicio) {
		this.medioPagoServicio = medioPagoServicio;
	}
	
	protected IVentaServicio getVentaServicio() {
		if(Validator.isNull(ventaServicio))
			throw new PresentationException("error.unknown", "El servicio: " + ventaServicio.getClass().getSimpleName() + " no ha sido inyectado.");
		return ventaServicio;
	}
	
	public void setVentaServicio(IVentaServicio ventaServicio) {
		this.ventaServicio = ventaServicio;
	}
	
	protected IProductoServicio getProductoServicio() {
		if(Validator.isNull(productoServicio))
			throw new PresentationException("error.unknown", "El servicio: " + productoServicio.getClass().getSimpleName() + " no ha sido inyectado.");
		return productoServicio;
	}

	public void setProductoServicio(IProductoServicio productoServicio) {
		this.productoServicio = productoServicio;
	}

	public DetalleProductoPanel getDetalleProductoPanel() {
		if(Validator.isNull(detalleProductoPanel))
			throw new PresentationException("error.unknown", "El panel: " + detalleProductoPanel.getClass().getSimpleName() + " no ha sido inyectado.");
		return detalleProductoPanel;
	}

	public void setDetalleProductoPanel(DetalleProductoPanel detalleProductoPanel) {
		this.detalleProductoPanel = detalleProductoPanel;
	}

}