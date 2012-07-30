package com.mitnick.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.appfuse.model.BaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mitnick.persistence.daos.ICiudadDao;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.persistence.daos.IMarcaDao;
import com.mitnick.persistence.daos.IMedioPagoDAO;
import com.mitnick.persistence.daos.IProductoDAO;
import com.mitnick.persistence.daos.IProveedorDAO;
import com.mitnick.persistence.daos.IProvinciaDao;
import com.mitnick.persistence.daos.ITipoDao;
import com.mitnick.persistence.entities.Ciudad;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Direccion;
import com.mitnick.persistence.entities.Marca;
import com.mitnick.persistence.entities.MedioPago;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Pago;
import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.ProductoVenta;
import com.mitnick.persistence.entities.Proveedor;
import com.mitnick.persistence.entities.Provincia;
import com.mitnick.persistence.entities.Tipo;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.DescuentoDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.VentaHelper;
import com.mitnick.utils.dtos.BaseDto;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.DireccionDto;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.MedioPagoDto;
import com.mitnick.utils.dtos.MovimientoDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoNuevoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.ProveedorDto;
import com.mitnick.utils.dtos.ProvinciaDto;
import com.mitnick.utils.dtos.TipoDto;
import com.mitnick.utils.dtos.VentaDto;

@Scope
@Component("entityDTOParser")
public class EntityDTOParser<E extends BaseObject, D extends BaseDto> {
	@Autowired
	protected IClienteDao clienteDao;
	@Autowired
	protected ICiudadDao ciudadDao;
	@Autowired
	protected IProductoDAO productoDao;
	@Autowired
	protected IProveedorDAO proveedorDao;
	@Autowired
	protected IMarcaDao marcaDao;
	@Autowired
	protected ITipoDao tipoDao;
	@Autowired
	protected IMedioPagoDAO medioPagoDao;
	@Autowired
	protected IProvinciaDao provinciaDao;

	private String AJUSTE = "Ajuste Manual";

	private String VENTA = "Venta";
	
	public List<D> getDtosFromEntities(List<E> entities) {
		List<D> resultado = new ArrayList<D>();
		
		for (E entity : entities)
			resultado.add(getDtoFromEntity(entity));
		
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public D getDtoFromEntity(E entity) {
		if(entity instanceof Cliente)
			return (D) getDtoFromEntity((Cliente) entity);
		else if(entity instanceof Producto)
			return (D) getDtoFromEntity((Producto) entity);
		else if(entity instanceof MedioPago)
			return (D) getDtoFromEntity((MedioPago) entity);
		else if(entity instanceof Movimiento)
			return (D) getDtoFromEntity((Movimiento) entity);
		else if(entity instanceof Venta)
			return (D) getDtoFromEntity((Venta) entity);
		else if(entity instanceof Tipo)
			return (D) getDtoFromEntity((Tipo) entity);
		else if(entity instanceof Marca)
			return (D) getDtoFromEntity((Marca) entity);
		else if(entity instanceof Provincia)
			return (D) getDtoFromEntity((Provincia) entity);
		else if(entity instanceof Ciudad)
			return (D) getDtoFromEntity((Ciudad) entity);
		else if(entity instanceof Proveedor)
			return (D) getDtoFromEntity((Proveedor) entity);
		else return null;
	}

	public List<E> getEntitesFromDtos(List<D> dtos) {
		List<E> resultado = new ArrayList<E>();
		
		for (D dto : dtos)
			resultado.add(getEntityFromDto(dto));
		
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public E getEntityFromDto(D dto) {
		if(dto instanceof ClienteDto)
			return (E) getEntityFromDto((ClienteDto) dto);
		else if(dto instanceof ProductoDto)
			return (E) getEntityFromDto((ProductoDto) dto);
		else if(dto instanceof ProductoNuevoDto)
			return (E) getEntityFromDto((ProductoNuevoDto) dto);
		else if(dto instanceof ProveedorDto)
			return (E) getEntityFromDto((ProveedorDto) dto);
		else if(dto instanceof VentaDto)
			return (E) getEntityFromDto((VentaDto) dto);
		else if(dto instanceof ProvinciaDto)
			return (E) getEntityFromDto((ProvinciaDto) dto);
		else return null;
	}
	
	private Cliente getEntityFromDto(ClienteDto clienteDto) {
		Cliente cliente = null;
		if (Validator.isNotNull(clienteDto.getId()))
			cliente = clienteDao.get(clienteDto.getId());
		else
			cliente = new Cliente();

		cliente.setApellido(clienteDto.getApellido());
		cliente.setNombre(clienteDto.getNombre());
		cliente.setCuit(clienteDto.getCuit());
		cliente.setDocumento(clienteDto.getDocumento());
		cliente.setEmail(clienteDto.getEmail());
		cliente.setFechaNacimiento(clienteDto.getFechaNacimiento());
		cliente.setTelefono(clienteDto.getTelefono());
		Ciudad ciudad = null;
		ciudad = (Ciudad) ciudadDao.get(new Long(clienteDto.getDireccion().getCiudad().getId()));
		
		Direccion direccion = cliente.getDireccion();

		if(Validator.isNull(direccion)) {
			direccion = new Direccion();
			direccion.setCodigoPostal(clienteDto.getDireccion().getCodigoPostal());
			direccion.setCiudad(ciudad);
			direccion.setDomicilio(clienteDto.getDireccion().getDomicilio());
		}
		else
			direccion.setCiudad(ciudad);
		
		cliente.setDireccion(direccion);

		return cliente;
	}

	private ClienteDto getDtoFromEntity(Cliente cliente) {
		ClienteDto clienteDto = new ClienteDto();
		clienteDto.setId(cliente.getId());
		clienteDto.setApellido(cliente.getApellido());
		clienteDto.setNombre(cliente.getNombre());
		clienteDto.setCuit(cliente.getCuit().toString());
		clienteDto.setDocumento(cliente.getDocumento().toString());
		clienteDto.setEmail(cliente.getEmail());
		clienteDto.setFechaNacimiento(cliente.getFechaNacimiento());
		clienteDto.setTelefono(cliente.getTelefono());

		if(Validator.isNotNull(cliente.getDireccion()))
			clienteDto.setDireccion(getDtoFromEntity(cliente.getDireccion()));

		return clienteDto;
	}

	private MedioPagoDto getDtoFromEntity(MedioPago medioPago) {
		MedioPagoDto medioPagoDto = new MedioPagoDto();
		medioPagoDto.setDescripcion(medioPago.getDescripcion());
		medioPagoDto.setId(medioPago.getId());
		return medioPagoDto;
	}

	private ProductoDto getDtoFromEntity(Producto producto) {
		ProductoDto productoDto = new ProductoDto();

		productoDto.setId(producto.getId());
		productoDto.setCodigo(producto.getCodigo());
		productoDto.setDescripcion(producto.getDescripcion());
		productoDto.setPrecioVenta(producto.getPrecioVenta());
		productoDto.setPrecioCompra(producto.getPrecioCompra());
		productoDto.setIva(producto.getIva());
		productoDto.setStock(producto.getStock());
		productoDto.setStockMinimo(producto.getStockMinimo());
		productoDto.setStockCompra(producto.getStockCompra());
		productoDto.setMarca(getDtoFromEntity(producto.getMarca()));
		productoDto.setTipo(getDtoFromEntity(producto.getTipo()));
		productoDto.setProveedor(getDtoFromEntity(producto.getProveedor()));

		return productoDto;
	}
	
	public ProductoNuevoDto getProductoNuevoDtoFromProducto(Producto producto) {
		ProductoNuevoDto productoDto = new ProductoNuevoDto();

		productoDto.setId(producto.getId());
		productoDto.setCodigo(producto.getCodigo());
		productoDto.setDescripcion(producto.getDescripcion());
		productoDto.setPrecioVenta(producto.getPrecioVenta().toString());
		productoDto.setPrecioCompra(producto.getPrecioCompra().toString());
		productoDto.setIva(producto.getIva().toString());
		productoDto.setStock(producto.getStock() + "");
		productoDto.setStockMinimo(producto.getStockMinimo() + "");
		productoDto.setStockCompra(producto.getStockCompra() + "");
		productoDto.setMarca(getDtoFromEntity(producto.getMarca()));
		productoDto.setTipo(getDtoFromEntity(producto.getTipo()));
		productoDto.setProveedor(getDtoFromEntity(producto.getProveedor()));

		return productoDto;
	}
	
	private ProveedorDto getDtoFromEntity(Proveedor proveedor) {
		ProveedorDto proveedorDto = new ProveedorDto();

		proveedorDto.setId(proveedor.getId());
		proveedorDto.setCodigo(proveedor.getCodigo());
		proveedorDto.setNombre(proveedor.getNombre());
		proveedorDto.setTelefono(proveedor.getTelefono());

		return proveedorDto;
	}
	
	private TipoDto getDtoFromEntity(Tipo tipo) {
		TipoDto tipoDto = new TipoDto();
		tipoDto.setDescripcion(tipo.getDescripcion());
		tipoDto.setId(tipo.getId());
		return tipoDto;
	}

	private MarcaDto getDtoFromEntity(Marca marca) {
		MarcaDto marcaDto = new MarcaDto();
		marcaDto.setDescripcion(marca.getDescripcion());
		marcaDto.setId(marca.getId());
		return marcaDto;
	}

	private Producto getEntityFromDto(ProductoDto productoDto) {
		Producto producto = null;
		if (Validator.isNotNull(productoDto.getId()))
			producto = productoDao.get(productoDto.getId());

		else
			producto = new Producto();

		producto.setCodigo(productoDto.getCodigo());
		producto.setDescripcion(productoDto.getDescripcion());
		producto.setPrecioVenta(productoDto.getPrecioVenta());
		producto.setPrecioCompra(productoDto.getPrecioCompra());

		producto.setIva(productoDto.getIva());

		producto.setStock(productoDto.getStock());
		producto.setStockMinimo(productoDto.getStockMinimo());
		producto.setStockCompra(productoDto.getStockCompra());

		Marca marca = marcaDao.get(new Long(productoDto.getMarca().getId()));
		producto.setMarca(marca);

		Tipo tipo = tipoDao.get(new Long(productoDto.getTipo().getId()));
		producto.setTipo(tipo);
		
		producto.setProveedor(getEntityFromDto(productoDto.getProveedor()));
		
		return producto;
	}
	
	private Producto getEntityFromDto(ProductoNuevoDto productoDto) {
		Producto producto = null;
		if (Validator.isNotNull(productoDto.getId()))
			producto = productoDao.get(productoDto.getId());

		else
			producto = new Producto();

		producto.setCodigo(productoDto.getCodigo());
		producto.setDescripcion(productoDto.getDescripcion());
		producto.setPrecioVenta(new BigDecimal(productoDto.getPrecioVenta()));
		producto.setPrecioCompra(new BigDecimal(productoDto.getPrecioCompra()));

		producto.setIva(new BigDecimal(productoDto.getIva()));

		producto.setStock(Integer.parseInt(productoDto.getStock()));
		producto.setStockMinimo(Integer.parseInt(productoDto.getStockMinimo()));
		producto.setStockCompra(Integer.parseInt(productoDto.getStockCompra()));

		Marca marca = marcaDao.get(new Long(productoDto.getMarca().getId()));
		producto.setMarca(marca);

		Tipo tipo = tipoDao.get(new Long(productoDto.getTipo().getId()));
		producto.setTipo(tipo);
		
		producto.setProveedor(getEntityFromDto(productoDto.getProveedor()));
		
		return producto;
	}
	
	private Proveedor getEntityFromDto(ProveedorDto proveedorDto) {
		Proveedor proveedor = null;
		if (Validator.isNotNull(proveedorDto.getId()))
			proveedor = proveedorDao.get(proveedorDto.getId());

		else
			proveedor = new Proveedor();

		proveedor.setCodigo(proveedorDto.getCodigo());
		proveedor.setNombre(proveedorDto.getNombre());
		proveedor.setTelefono(proveedorDto.getTelefono());
		return proveedor;
	}

	private MovimientoDto getDtoFromEntity(Movimiento movimiento) {
		MovimientoDto movimientoDto = new MovimientoDto();
		movimientoDto.setCantidad(movimiento.getCantidad());
		movimientoDto.setFecha(movimiento.getFecha());
		movimientoDto.setId(movimiento.getId());
		movimientoDto.setStock(movimiento.getStockAlaFecha());
		if (movimiento.getTipo() == Movimiento.AJUSTE)
			movimientoDto.setTipo(AJUSTE);
		else {
			movimientoDto.setTipo(VENTA);
			movimientoDto.setCantidad(movimiento.getCantidad()*-1);
		}
			
		return movimientoDto;
	}


	@SuppressWarnings("unchecked")
	private VentaDto getDtoFromEntity(Venta venta) {
		VentaDto ventaDto = new VentaDto();
		
		ventaDto.setProductos((List<ProductoVentaDto>) getDtosFromEntities((List<E>) venta.getProductos()));
		
		ventaDto.setSubTotal(venta.getSubtotal());
		ventaDto.setTotal(venta.getTotal());
		ventaDto.setImpuesto(venta.getImpuesto());
		
		//el descuento se toma por monto para reportes aun cuando fue por porcentaje
		DescuentoDto descuento = new DescuentoDto();
		descuento.setTipo(DescuentoDto.MONTO);
		descuento.setDescuento(venta.getDescuento());
		ventaDto.setDescuento(descuento);
		
		ventaDto.setPagos((List<PagoDto>) getDtosFromEntities((List<E>) venta.getPagos()));
		ventaDto.setCliente(getDtoFromEntity(venta.getCliente()));

		//estos valores se setean por defecto porq representan el total de dinero ingresado, no es real
		ventaDto.setPagado(true);
		ventaDto.setTotalPagado(venta.getTotal());
		ventaDto.setFaltaPagar(new BigDecimal(0));
		ventaDto.setVuelto(new BigDecimal(0));
		
		return ventaDto;
	}
	
	private Provincia getEntityFromDto(ProvinciaDto dto) {
		Provincia provincia = provinciaDao.get(dto.getId());
		
		if(Validator.isNull(provincia))
			provincia = new Provincia();
		
		provincia.setDescripcion(dto.getDescripcion());
		
		return provincia;
	}

	private Venta getEntityFromDto(VentaDto ventaDto) {

		Venta venta = new Venta();
		if (Validator.isNotNull(ventaDto.getCliente()))
			venta.setCliente(clienteDao.get(ventaDto.getCliente().getId()));

		venta.setDescuento(VentaHelper.getDescuentoTotal(ventaDto));
		venta.setFecha(new Date());

		List<ProductoVenta> productos = new ArrayList<ProductoVenta>();
		for (ProductoVentaDto productoDto : ventaDto.getProductos())
			productos.add(getEntityFromDto(productoDto));

		venta.setProductos(productos);

		List<Pago> pagos = new ArrayList<Pago>();
		for (PagoDto pagoDto : ventaDto.getPagos())
			pagos.add(getPagoFromPagoDto(pagoDto));

		venta.setPagos(pagos);

		// TODO: tipo de cliente
		venta.setDiscriminacionIVA(null);

		venta.setImpuesto(ventaDto.getImpuesto());
		venta.setSubtotal(ventaDto.getSubTotal());
		venta.setTotal(ventaDto.getTotal());

		return venta;
	}
	
	private ProductoVenta getEntityFromDto(ProductoVentaDto productoVentaDto) {
		ProductoVenta productoVenta = new ProductoVenta();
		productoVenta.setProducto(productoDao.get(productoVentaDto.getProducto().getId()));
		productoVenta.setCantidad(productoVentaDto.getCantidad());
		productoVenta.setIva(productoVentaDto.getIva());
		productoVenta.setPrecio(productoVentaDto.getPrecioTotal().setScale(2, BigDecimal.ROUND_HALF_UP));
		return productoVenta;
	}

	private Pago getPagoFromPagoDto(PagoDto pagoDto) {
		Pago pago = new Pago();
		pago.setMedioPago(medioPagoDao.get(pagoDto.getMedioPago().getId()));
		pago.setPago(new Long(pagoDto.getMonto().longValue()));
		return pago;
	}
	
	private ProvinciaDto getDtoFromEntity(Provincia provincia) {
		ProvinciaDto provinciaDto = new ProvinciaDto();
		provinciaDto.setId(provincia.getId());
		provinciaDto.setDescripcion(provincia.getDescripcion());
		return provinciaDto;
	}
	
	private CiudadDto getDtoFromEntity(Ciudad ciudad) {
		CiudadDto ciudadDto = new CiudadDto();
		ciudadDto.setId(ciudad.getId());
		ciudadDto.setDescripcion(ciudad.getDescripcion());
		ciudadDto.setPrinvincia(getDtoFromEntity(ciudad.getProvincia()));
		return ciudadDto;
	}
	
	private DireccionDto getDtoFromEntity(Direccion direccion) {
		DireccionDto direccionDto = new DireccionDto();
		direccionDto.setCodigoPostal(direccion.getCodigoPostal());
		direccionDto.setDomicilio(direccion.getDomicilio());
		direccionDto.setId(direccion.getId());
		direccionDto.setCiudad(getDtoFromEntity(direccion.getCiudad()));
		return direccionDto;
	}

}
