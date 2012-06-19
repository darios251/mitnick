package com.mitnick.presentacion.controladores;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.ProductoView;
import com.mitnick.presentacion.vistas.paneles.ProductoNuevoPanel;
import com.mitnick.presentacion.vistas.paneles.ProductoPanel;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProveedorDto;
import com.mitnick.utils.dtos.TipoDto;

@Controller("productoController")
public class ProductoController extends BaseController {
	
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
	
	public void mostrarProductoNuevoPanel() {
		logger.info("Mostrando el panel de producto nuevo");
		productoPanel.setVisible(false);
		productoNuevoPanel.setVisible(true);
		productoNuevoPanel.actualizarPantalla();
	}

	public void mostrarProductosPanel() {
		logger.info("Mostrando el panel de productos");
		productoNuevoPanel.setVisible(false);
		productoPanel.setVisible(true);
		productoPanel.actualizarPantalla();
	}
	
	public List<ProductoDto> getProductosByFilter(ConsultaProductoDto dto) {
		logger.debug("Entrando al método getProductosByFilter con: " + dto);
		
		List<ProductoDto> resultado = null;
		try {
			resultado = getProductoServicio().consultaProducto(dto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e);
		}
		
		// chequeo si se encontro o no algo en la busqueda
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
	
	public void guardarProducto(ProductoDto producto, String codigo, String descripcion, TipoDto tipo, MarcaDto marca, 
			String stock, String stockMinimo, String stockCompra, String precioVenta, String precioCompra, ProveedorDto proveedor) {
		
		if(Validator.isBlankOrNull(codigo))
			throw new PresentationException("error.producto.nuevo.codigo.null");
		if(Validator.isBlankOrNull(descripcion))
			throw new PresentationException("error.producto.nuevo.descripcion.null");
		if(Validator.isNull(tipo))
			throw new PresentationException("error.producto.nuevo.tipo.null");
		if(Validator.isNull(marca))
			throw new PresentationException("error.producto.nuevo.marca.null");
		if(Validator.isBlankOrNull(stock))
			throw new PresentationException("error.producto.nuevo.stock.null");
		if(!Validator.isInt(stock))
			throw new PresentationException("error.producto.nuevo.stock.format");
		if(Validator.isBlankOrNull(stockMinimo))
			throw new PresentationException("error.producto.nuevo.stockMinimo.null");
		if(!Validator.isInt(stockMinimo))
			throw new PresentationException("error.producto.nuevo.stockMinimo.format");
		if(Validator.isBlankOrNull(stockCompra))
			throw new PresentationException("error.producto.nuevo.stockCompra.null");
		if(!Validator.isInt(stockCompra))
			throw new PresentationException("error.producto.nuevo.stockCompra.format");
		if(!Validator.isBlankOrNull(stock) && !Validator.isInRange(Integer.parseInt(stock), 0, 10000))
			throw new PresentationException("error.producto.nuevo.stock.rangoEntero", new Object[]{"0", "10000"});
		if(Validator.isBlankOrNull(precioVenta))
			throw new PresentationException("error.producto.nuevo.precioVenta.null");
		if(!Validator.isDouble(precioVenta))
			throw new PresentationException("error.producto.nuevo.precioVenta.format");
		if(Validator.isBlankOrNull(precioCompra))
			throw new PresentationException("error.producto.nuevo.precioCompra.null");
		if(!Validator.isDouble(precioCompra))
			throw new PresentationException("error.producto.nuevo.precioCompra.format");
		
		if(Validator.isNull(producto))
			producto = new ProductoDto();
		
		producto.setCodigo(codigo);
		producto.setDescripcion(descripcion);
		producto.setPrecioVenta(new BigDecimal(Double.parseDouble(precioVenta)));
		producto.setPrecioCompra(new BigDecimal(Double.parseDouble(precioCompra)));
		producto.setTipo(tipo);
		producto.setMarca(marca);
		producto.setStock(Integer.parseInt(stock));
		producto.setStockMinimo(Integer.parseInt(stockMinimo));
		producto.setStockCompra(Integer.parseInt(stockCompra));
		producto.setProveedor(proveedor);
		
		try {
			getProductoServicio().guardarProducto(producto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar dar del alta el producto: " + producto);
		}
	}
	
	public void eliminarProducto() {
		ProductoDto productoDto = null;
		try {
			int index = getProductoPanel().getTable().getSelectedRow();
			productoDto = getProductoPanel().getTableModel().getProducto(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getProductoPanel().getTableModel().getRowCount() == 0) {
				throw new PresentationException("error.productoPanel.productos.vacio");
			}
			else {
				throw new PresentationException("error.productoPanel.producto.noSeleccionado");
			}
		}
		
		try {
			getProductoServicio().bajaProducto(productoDto);
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar eliminar el producto");
		}
	}
	
	public void editarProducto() {
		ProductoDto productoDto = null;
		try {
			int index = getProductoPanel().getTable().getSelectedRow();
			productoDto = getProductoPanel().getTableModel().getProducto(index);
		}
		catch (IndexOutOfBoundsException exception) {
			if(getProductoPanel().getTableModel().getRowCount() == 0) {
				throw new PresentationException("error.productoPanel.productos.vacio");
			}
			else {
				throw new PresentationException("error.productoPanel.producto.noSeleccionado");
			}
		}
		
		try {
			productoNuevoPanel.setProducto(productoDto);
			mostrarProductoNuevoPanel();
		}
		catch(BusinessException e) {
			throw new PresentationException(e.getMessage(), "Hubo un error al intentar editar el producto");
		}
	}
	
	public ProductoView getProductoView() {
		return productoView;
	}

	public ProductoPanel getProductoPanel() {
		return productoPanel;
	}

	public ProductoNuevoPanel getProductoNuevoPanel() {
		return productoNuevoPanel;
	}

	protected IProductoServicio getProductoServicio() {
		if(productoServicio == null)
			throw new PresentationException("error.unknown", "El servicio: " + productoServicio.getClass().getSimpleName() + "");
		return productoServicio;
	}

	public ProductoDto reloadProducto(ProductoDto producto) {
		
		//productoServicio.obtenerProductoPorId(producto.get)
		
		return null;
	}

}