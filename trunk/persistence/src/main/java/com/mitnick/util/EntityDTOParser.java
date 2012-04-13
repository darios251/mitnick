package com.mitnick.util;

import java.math.BigDecimal;
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
import com.mitnick.persistence.entities.Tipo;
import com.mitnick.persistence.entities.Venta;
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
import com.mitnick.utils.dtos.ProductoVentaDto;
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
	protected IMarcaDao marcaDao;
	@Autowired
	protected ITipoDao tipoDao;
	@Autowired
	protected IMedioPagoDAO medioPagoDao;

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
		else if(dto instanceof VentaDto)
			return (E) getEntityFromDto((VentaDto) dto);
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
		cliente.setCuit(new Long(clienteDto.getCuit()));
		cliente.setDocumento(new Long(clienteDto.getDocumento()));
		cliente.setEmail(clienteDto.getEmail());
		cliente.setFechaNacimiento(clienteDto.getFechaNacimiento());
		cliente.setTelefono(clienteDto.getTelefono());
		Ciudad ciudad = null;
		ciudad = (Ciudad) ciudadDao.get(new Long(clienteDto.getDireccion()
				.getCiudad().getId()));

		Direccion direccion = new Direccion();
		direccion.setCiudad(ciudad);
		direccion.setDomicilio(clienteDto.getDireccion().getDomicilio());
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

		if(Validator.isNotNull(cliente.getDireccion())) {
			CiudadDto ciudadDto = new CiudadDto();
			ciudadDto.setDescripcion(cliente.getDireccion().getCiudad().getDescripcion());
			ciudadDto.setId(cliente.getDireccion().getCiudad().getId());
			ProvinciaDto provinciaDto = new ProvinciaDto();
			provinciaDto.setDescripcion(cliente.getDireccion().getCiudad().getProvincia().getDescripcion());
			provinciaDto.setId(cliente.getDireccion().getCiudad().getProvincia().getId());
			ciudadDto.setPrinvinciaDto(provinciaDto);
			String pais = cliente.getDireccion().getCiudad().getProvincia().getPais().getDescripcion();
	
			DireccionDto direccionDto = new DireccionDto();
			direccionDto.setId(cliente.getDireccion().getId());
			direccionDto.setDomicilio(cliente.getDireccion().getDomicilio());
			direccionDto.setCiudad(ciudadDto);
			direccionDto.setPais(pais);
			clienteDto.setDireccion(direccionDto);
		}

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
		productoDto.setPrecio(new BigDecimal(producto.getPrecio()));
		productoDto.setIva(new BigDecimal(producto.getIva()));
		productoDto.setStock(producto.getStock());

		productoDto.setMarca(getDtoFromEntity(producto.getMarca()));

		productoDto.setTipo(getDtoFromEntity(producto.getTipo()));

		return productoDto;
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
		producto.setPrecio(new Long(productoDto.getPrecio().longValue()));

		producto.setIva(new Long(productoDto.getIva().longValue()));

		producto.setStock(productoDto.getStock());
		producto.setStockMinimo(productoDto.getStockMinimo());

		Marca marca = marcaDao.get(new Long(productoDto.getMarca().getId()));
		producto.setMarca(marca);

		Tipo tipo = tipoDao.get(new Long(productoDto.getTipo().getId()));
		producto.setTipo(tipo);
		return producto;
	}

	private MovimientoDto getDtoFromEntity(Movimiento movimiento) {
		MovimientoDto movimientoDto = new MovimientoDto();
		movimientoDto.setCantidad(movimiento.getCantidad());
		movimientoDto.setFecha(movimiento.getFecha());
		movimientoDto.setId(movimiento.getId());
		movimientoDto.setStock(movimiento.getStockAlaFecha());
		if (movimiento.getTipo() == Movimiento.AJUSTE)
			movimientoDto.setTipo(AJUSTE);
		else
			movimientoDto.setTipo(VENTA);
		return movimientoDto;
	}

	//TODO:
	private VentaDto getDtoFromEntity(Venta venta) {
		VentaDto ventaDto = new VentaDto();
		ventaDto.setCliente(getDtoFromEntity(venta.getCliente()));
		return ventaDto;
	}

	private Venta getEntityFromDto(VentaDto ventaDto) {

		Venta venta = new Venta();
		if (Validator.isNotNull(ventaDto.getCliente()))
			venta.setCliente(clienteDao.get(ventaDto.getCliente().getId()));

		venta.setDescuento(new Long(VentaHelper.getDescuentoTotal(ventaDto).longValue()));
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

		venta.setImpuesto(new Long(ventaDto.getImpuesto().longValue()));
		venta.setSubtotal(new Long(ventaDto.getSubTotal().longValue()));
		venta.setTotal(new Long(ventaDto.getTotal().longValue()));

		return venta;
	}
	
	private ProductoVenta getEntityFromDto(ProductoVentaDto productoVentaDto) {
		ProductoVenta productoVenta = new ProductoVenta();
		productoVenta.setProducto(productoDao.get(productoVentaDto
				.getProducto().getId()));
		productoVenta.setCantidad(productoVentaDto.getCantidad());
		productoVenta.setIva(new Long(productoVentaDto.getIva().longValue()));
		productoVenta.setPrecio(new Long(productoVentaDto.getPrecioTotal()
				.longValue()));
		return productoVenta;
	}

	private Pago getPagoFromPagoDto(PagoDto pagoDto) {
		Pago pago = new Pago();
		pago.setMedioPago(medioPagoDao.get(pagoDto.getMedioPago().getId()));
		pago.setPago(new Long(pagoDto.getMonto().longValue()));
		return pago;
	}

}
