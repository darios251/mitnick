package com.mitnick.presentacion.controladores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.presentacion.excepciones.PresentationException;
import com.mitnick.presentacion.utils.VentaManager;
import com.mitnick.presentacion.vistas.BaseView;
import com.mitnick.presentacion.vistas.VentaView;
import com.mitnick.presentacion.vistas.paneles.BuscarProductoPanel;
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
	private	IVentaServicio ventaServicio;
	
	@Autowired
	private IProductoServicio productoServicio;
	
	@Autowired
	private IMedioPagoServicio medioPagoServicio;
	
	private BaseView ultimoPanelMostrado = null;
	
	public VentaController() {
		
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

	public void mostrarBuscarArticuloPanel() {
		ultimoPanelMostrado = buscarProductoPanel;
		ventaPanel.setVisible(false);
		pagoPanel.setVisible(false);
		buscarProductoPanel.setVisible(true);
		buscarProductoPanel.limpiarCamposPantalla();
	}
	
	public void mostrarVentasPanel() {
		// TODO: preguntar si es necesaria esta validación
		// valido si puede mostrar el panel de ventas
		if(pagoPanel != null && pagoPanel.equals(ultimoPanelMostrado) && !VentaManager.getVentaActual().getPagos().isEmpty())
			throw new PresentationException("error.venta.pagos.noVacio");
		
		ultimoPanelMostrado = ventaPanel;
		buscarProductoPanel.setVisible(false);
		pagoPanel.setVisible(false);
		ventaPanel.actualizarPantalla();
		ventaPanel.setVisible(true);
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
		pagoPanel.actualizarPantalla();
		pagoPanel.setVisible(true);
	}
	
	public void mostrarUltimoPanelMostrado () {
		if(ultimoPanelMostrado == null)
			ultimoPanelMostrado = ventaPanel;
		ultimoPanelMostrado.setVisible(true);
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


	public void quitarProductoVentaDto(ProductoVentaDto producto) {
		logger.info("Quitando elemento de la tabla");
		
		try {
			VentaManager.setVentaActual(ventaServicio.quitarProducto(producto, VentaManager.getVentaActual()));
			ventaPanel.actualizarPantalla();
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

	public void checkFinalizarVenta() {
		if(VentaManager.getVentaActual() != null && (VentaManager.getVentaActual().getPagos() == null || VentaManager.getVentaActual().getPagos().isEmpty()))
			throw new PresentationException("error.venta.finalizarVenta.sinPagos");
		if(!VentaManager.getVentaActual().isPagado())
			throw new PresentationException("error.venta.finalizarVenta.faltanPagos");
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
		logger.debug("Saliendo del método agregarPago");
	}
	
	public void quitarPago(PagoDto pagoDto) {
		logger.debug("Entrado al metodo quitarPago, con pago: " + pagoDto);
		
		try {
			VentaManager.setVentaActual(getVentaServicio().quitarPago(pagoDto, VentaManager.getVentaActual()));
			pagoPanel.actualizarPantalla();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar el pago: " + pagoDto);
		}
		
		logger.debug("Saliendo del método quitaPago");
	}
	
	private IMedioPagoServicio getMedioPagoServicio() {
		if(Validator.isNull(medioPagoServicio))
			throw new PresentationException("error.unknown", "El servicio: " + medioPagoServicio.getClass().getSimpleName() + " no ha sido inyectado.");
		return medioPagoServicio;
	}
	
	public void setMedioPagoServicio(IMedioPagoServicio medioPagoServicio) {
		this.medioPagoServicio = medioPagoServicio;
	}
	
	private IVentaServicio getVentaServicio() {
		if(Validator.isNull(ventaServicio))
			throw new PresentationException("error.unknown", "El servicio: " + ventaServicio.getClass().getSimpleName() + " no ha sido inyectado.");
		return ventaServicio;
	}
	
	public void setVentaServicio(IVentaServicio ventaServicio) {
		this.ventaServicio = ventaServicio;
	}
	
	private IProductoServicio getProductoServicio() {
		if(Validator.isNull(productoServicio))
			throw new PresentationException("error.unknown", "El servicio: " + productoServicio.getClass().getSimpleName() + " no ha sido inyectado.");
		return productoServicio;
	}

	public void setProductoServicio(IProductoServicio productoServicio) {
		this.productoServicio = productoServicio;
	}
	
}