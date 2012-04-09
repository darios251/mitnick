package com.mitnick.presentacion.controladores;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.presentacion.excepciones.PresentationException;
import com.mitnick.presentacion.vistas.ProductoView;
import com.mitnick.presentacion.vistas.paneles.ProductoNuevoPanel;
import com.mitnick.presentacion.vistas.paneles.ProductoPanel;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Controller("productoController")
public class ProductoController extends BaseController {
	
	private static Logger logger = Logger.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoView productoView;
	@Autowired
	private ProductoPanel productoPanel;
	@Autowired
	private ProductoNuevoPanel productoNuevoPanel;
	@Autowired
	private IProductoServicio productoServicio;
	
	public ProductoController() {
		
	}
	
	public ProductoView getProductoView() {
		return productoView;
	}

	public void setProductoView(ProductoView productoView) {
		this.productoView = productoView;
	}

	public ProductoPanel getProductoPanel() {
		return productoPanel;
	}

	public void setProductoPanel(ProductoPanel productoPanel) {
		this.productoPanel = productoPanel;
	}

	public ProductoNuevoPanel getProductoNuevoPanel() {
		return productoNuevoPanel;
	}

	public void setProductoNuevoPanel(ProductoNuevoPanel productoNuevoPanel) {
		this.productoNuevoPanel = productoNuevoPanel;
	}

	public void mostrarProductoNuevoPanel() {
		logger.info("Mostrando el panel de producto nuevo");
		productoPanel.setVisible(false);
		productoNuevoPanel.setVisible(true);
	}

	public void mostrarProductosPanel() {
		logger.info("Mostrando el panel de productos");
		productoNuevoPanel.setVisible(false);
		productoPanel.setVisible(true);
		
	}
	
	public List<ProductoDto> getProductosByFilter(ConsultaProductoDto dto) {
		logger.info("Consultando productos por filtro");
		
		
		List<ProductoDto> resultado = null;
		try {
			resultado = getProductoServicio().consultaProducto(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		
		// chequeo si se encontró o no algo en la búsqueda
		if(resultado == null || resultado.isEmpty())
			throw new PresentationException("error.venta.producto.noExiste");
		
		return resultado;
	}
	
	public List<MarcaDto> obtenerMarcas() {
		try {
			return productoServicio.obtenerMarcas();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener las marcas");
		}
	}
	
	public List<TipoDto> obtenerTipos() {
		try {
			return productoServicio.obtenerTipos();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener los tipos");
		}
	}
	
	public List<ProductoDto> getAllProductos() {
		try {
			return productoServicio.obtenerProductos();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar obtener los productos");
		}
	}

	public void setProductoServicio(IProductoServicio productoServicio) {
		this.productoServicio = productoServicio;
	}
	
	private IProductoServicio getProductoServicio() {
		if(productoServicio == null)
			throw new PresentationException("error.unknown", "El servicio: " + productoServicio.getClass().getSimpleName() + "");
		return productoServicio;
	}

}