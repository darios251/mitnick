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
import com.mitnick.persistence.daos.ICuotaDao;
import com.mitnick.persistence.daos.IDiscriminacionIVADao;
import com.mitnick.persistence.daos.IMarcaDao;
import com.mitnick.persistence.daos.IMedioPagoDAO;
import com.mitnick.persistence.daos.IProductoDAO;
import com.mitnick.persistence.daos.IProveedorDAO;
import com.mitnick.persistence.daos.IProvinciaDao;
import com.mitnick.persistence.daos.ITipoDao;
import com.mitnick.persistence.entities.Ciudad;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Credito;
import com.mitnick.persistence.entities.Cuota;
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
import com.mitnick.utils.DateHelper;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.Validator;
import com.mitnick.utils.VentaHelper;
import com.mitnick.utils.dtos.BaseDto;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CreditoDto;
import com.mitnick.utils.dtos.CuotaDto;
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
import com.mitnick.utils.dtos.TipoCompradorDto;
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
	@Autowired
	protected IDiscriminacionIVADao discriminacionIVADao;
	@Autowired
	protected ICuotaDao cuotaDao;

	private String AJUSTE = "Ajuste Manual";
	private String DEVOLUCION = "Devolución";
	private String CREACION = "Creación";
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
		else if(entity instanceof Cuota)
			return (D) getDtoFromEntity((Cuota) entity);
		else if(entity instanceof Pago)
			return (D) getDtoFromEntity((Pago) entity);
		else if(entity instanceof Credito)
			return (D) getDtoFromEntity((Credito) entity);
		else if(entity instanceof ProductoVenta)
			return (D) getDtoFromEntity((ProductoVenta) entity);
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
		else if(dto instanceof CuotaDto)
			return (E) getEntityFromDto((CuotaDto) dto);
		else if(dto instanceof PagoDto)
			return (E) getEntityFromDto((PagoDto) dto);
		else return null;
	}
	
	private CreditoDto getDtoFromEntity(Credito credito) {
		CreditoDto creditoDto = new CreditoDto();
		creditoDto.setId(credito.getId());
		creditoDto.setMontoTotal(credito.getMonto());
		creditoDto.setMontoUsado(credito.getMontoUsado());
		creditoDto.setNroNC(credito.getNumeroNC());
		return creditoDto;
	}
	
	private Cliente getEntityFromDto(ClienteDto clienteDto) {
		Cliente cliente = null;
		if (Validator.isNotNull(clienteDto.getId()))
			cliente = clienteDao.get(clienteDto.getId());
		else
			cliente = new Cliente();

		cliente.setActividad(clienteDto.getActividad());
		cliente.setNombre(clienteDto.getNombre());
		if (Validator.isNotBlankOrNull(clienteDto.getCuit()))
			cliente.setCuit(clienteDto.getCuit().trim());
		else
			cliente.setCuit(null);
		
		if (Validator.isNotBlankOrNull(clienteDto.getDocumento()))
			cliente.setDocumento(clienteDto.getDocumento().trim());
		else
			cliente.setDocumento(null);
		
		cliente.setEmail(clienteDto.getEmail());
		
		if (Validator.isNotNull(clienteDto.getFechaNacimiento()) && Validator.isDate(clienteDto.getFechaNacimiento(), MitnickConstants.DATE_FORMAT, true))
			cliente.setFechaNacimiento(DateHelper.getFecha(clienteDto.getFechaNacimiento()));
		else
			cliente.setFechaNacimiento(null);
		
		cliente.setTelefono(clienteDto.getTelefono());
		
		//si se desea asignar una direccion para el cliente
		if (Validator.isNotNull(clienteDto.getDireccion())&&Validator.isNotBlankOrNull(clienteDto.getDireccion().getDomicilio())){
			Direccion direccion = cliente.getDireccion();
			
			if (Validator.isNull(direccion))
				direccion = new Direccion();
			
			Ciudad ciudad = null;
			if (Validator.isNotNull(clienteDto.getDireccion().getCiudad()))
				ciudad = (Ciudad) ciudadDao.get(new Long(clienteDto.getDireccion().getCiudad().getId()));
			
			direccion.setCodigoPostal(clienteDto.getDireccion().getCodigoPostal());
			direccion.setDomicilio(clienteDto.getDireccion().getDomicilio());
			direccion.setCiudad(ciudad);
			cliente.setDireccion(direccion);
		} else
			cliente.setDireccion(null);

		

		return cliente;
	}

	private ClienteDto getDtoFromEntity(Cliente cliente) {
		ClienteDto clienteDto = new ClienteDto();
		clienteDto.setId(cliente.getId());
		clienteDto.setActividad(cliente.getActividad());
		clienteDto.setNombre(cliente.getNombre());
		if (Validator.isNotNull(cliente.getCuit()))
			clienteDto.setCuit(cliente.getCuit().toString());
		if (Validator.isNotNull(cliente.getDocumento()))
			clienteDto.setDocumento(cliente.getDocumento().toString());
		clienteDto.setEmail(cliente.getEmail());
		if (Validator.isNotNull(cliente.getFechaNacimiento()))
			clienteDto.setFechaNacimiento(DateHelper.getFecha(cliente.getFechaNacimiento()));
		clienteDto.setTelefono(cliente.getTelefono());
		if (Validator.isNotNull(cliente.getComprobantes()))
			clienteDto.setCantidadComprobantes(cliente.getComprobantes().size());
		if(Validator.isNotNull(cliente.getDireccion()))
			clienteDto.setDireccion(getDtoFromEntity(cliente.getDireccion()));

		return clienteDto;
	}

	private MedioPagoDto getDtoFromEntity(MedioPago medioPago) {
		MedioPagoDto medioPagoDto = new MedioPagoDto();
		medioPagoDto.setDescripcion(medioPago.getDescripcion());
		medioPagoDto.setId(medioPago.getId());
		medioPagoDto.setCodigo(medioPago.getCodigo());
		return medioPagoDto;
	}

	private ProductoDto getDtoFromEntity(Producto producto) {
		ProductoDto productoDto = new ProductoDto();

		productoDto.setId(producto.getId());
		productoDto.setTalle(producto.getTalle());
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
		BigDecimal precio = producto.getPrecioVenta();
		if (Validator.isNull(precio))
			productoDto.setPrecioVenta("");
		else
			productoDto.setPrecioVenta(precio.toString());
		
		precio = producto.getPrecioCompra();
		if (Validator.isNull(precio))
			productoDto.setPrecioCompra("");
		else
			productoDto.setPrecioCompra(precio.toString());
				
		productoDto.setIva(producto.getIva().toString());
		productoDto.setStock(producto.getStock() + "");
		if (Validator.isNotNull(producto.getTalle()))
			productoDto.setTalle(producto.getTalle() + "");
		productoDto.setStockMinimo(producto.getStockMinimo() + "");
		productoDto.setStockCompra(producto.getStockCompra() + "");
		productoDto.setMarca(getDtoFromEntity(producto.getMarca()));
		productoDto.setTipo(getDtoFromEntity(producto.getTipo()));
		productoDto.setProveedor(getDtoFromEntity(producto.getProveedor()));

		return productoDto;
	}
	
	private ProveedorDto getDtoFromEntity(Proveedor proveedor) {
		if (Validator.isNull(proveedor))
			return null;
		ProveedorDto proveedorDto = new ProveedorDto();

		proveedorDto.setId(proveedor.getId());
		proveedorDto.setCodigo(proveedor.getCodigo());
		proveedorDto.setNombre(proveedor.getNombre());
		proveedorDto.setTelefono(proveedor.getTelefono());

		return proveedorDto;
	}
	
	@SuppressWarnings("unchecked")
	private CuotaDto getDtoFromEntity(Cuota cuota) {
		CuotaDto cuotaDto = new CuotaDto();
		cuotaDto.setId(cuota.getId());
		cuotaDto.setFecha_pagar(DateHelper.getFecha(cuota.getFecha_pagar()));
		cuotaDto.setNroCuota(cuota.getNroCuota());
		cuotaDto.setTotal(cuota.getTotal());
		cuotaDto.setFaltaPagar(cuota.getFaltaPagar());
		cuotaDto.setClienteDto(getDtoFromEntity(cuota.getCliente()));
		cuotaDto.setPagado(cuota.isPagado());
		cuotaDto.setPagos((List<PagoDto>) getDtosFromEntities((List<E>) cuota.getPagos()));
		VentaHelper.calcularTotales(cuotaDto);
		return cuotaDto;
	}
	private TipoDto getDtoFromEntity(Tipo tipo) {
		if (Validator.isNull(tipo))
			return null;
		TipoDto tipoDto = new TipoDto();
		tipoDto.setDescripcion(tipo.getDescripcion());
		tipoDto.setId(tipo.getId());
		return tipoDto;
	}

	private MarcaDto getDtoFromEntity(Marca marca) {
		if (Validator.isNull(marca))
			return null;
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

		producto.setTalle(productoDto.getTalle());
		producto.setIva(productoDto.getIva());

		producto.setStock(productoDto.getStock());
		producto.setStockMinimo(productoDto.getStockMinimo());
		producto.setStockCompra(productoDto.getStockCompra());

		if (Validator.isNotNull(productoDto.getMarca())){
			Marca marca = marcaDao.get(new Long(productoDto.getMarca().getId()));
			producto.setMarca(marca);
		} else
			producto.setMarca(null);

		if (Validator.isNotNull(productoDto.getTipo())){		
			Tipo tipo = tipoDao.get(new Long(productoDto.getTipo().getId()));
			producto.setTipo(tipo);
		} else
			producto.setTipo(null);

		if (Validator.isNotNull(productoDto.getProveedor()))
			producto.setProveedor(getEntityFromDto(productoDto.getProveedor()));
		else
			producto.setProveedor(null);
		
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
		
		if (Validator.isNotBlankOrNull(productoDto.getPrecioCompra()))
			producto.setPrecioCompra(new BigDecimal(productoDto.getPrecioCompra()));
		else
			producto.setPrecioCompra(new BigDecimal(0));
		
		producto.setTalle(productoDto.getTalle());


		producto.setIva(new BigDecimal(productoDto.getIva()));

		producto.setStock(Integer.parseInt(productoDto.getStock()));
		if (Validator.isNotBlankOrNull(productoDto.getStockMinimo()))
			producto.setStockMinimo(Integer.parseInt(productoDto.getStockMinimo()));
		else
			producto.setStockMinimo(0);
		if (Validator.isNotBlankOrNull(productoDto.getStockCompra()))
			producto.setStockCompra(Integer.parseInt(productoDto.getStockCompra()));
		else
			producto.setStockCompra(0);
		
		if (Validator.isNotNull(productoDto.getMarca())){
			Marca marca = marcaDao.get(new Long(productoDto.getMarca().getId()));
			producto.setMarca(marca);
		} else
			producto.setMarca(null);

		if (Validator.isNotNull(productoDto.getTipo())){
			Tipo tipo = tipoDao.get(new Long(productoDto.getTipo().getId()));
			producto.setTipo(tipo);
		} else
			producto.setTipo(null);
		
		if (Validator.isNotNull(productoDto.getProveedor()))
			producto.setProveedor(getEntityFromDto(productoDto.getProveedor()));
		else
			producto.setProveedor(null);
		
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
		else if (movimiento.getTipo() == Movimiento.VENTA){
			movimientoDto.setTipo(VENTA);
			movimientoDto.setCantidad(movimiento.getCantidad()*-1);
		} else if (movimiento.getTipo() == Movimiento.DEVOLUCION) {
			movimientoDto.setTipo(DEVOLUCION);
		} else if (movimiento.getTipo() == Movimiento.CREACION) {
			movimientoDto.setTipo(CREACION);
		}
			
		return movimientoDto;
	}


	@SuppressWarnings("unchecked")
	private VentaDto getDtoFromEntity(Venta venta) {
		VentaDto ventaDto = new VentaDto();
		
		ventaDto.setProductos((List<ProductoVentaDto>) getDtosFromEntities((List<E>) venta.getProductos()));
		
		ventaDto.setTipo(venta.getTipo());
		ventaDto.setNumeroTicketOriginal(venta.getNumeroTicketOriginal());
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
		ventaDto.setImpresa(venta.isPrinted());
		ventaDto.setCancelada(venta.isCanceled());
		ventaDto.setAjusteRedondeo(venta.getAjusteRedondeo());
		ventaDto.setTipoResponsabilidad(new TipoCompradorDto(venta.getDiscriminacionIVA().getCodigo(), venta.getDiscriminacionIVA().getDescripcion()));
		
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
		if (Validator.isNotNull(ventaDto.getCliente())){
			Cliente cliente = clienteDao.get(ventaDto.getCliente().getId());
			venta.setCliente(cliente);
			if (Validator.isNotEmptyOrNull(ventaDto.getCuotas())) {
				List<Cuota> cuotas = new ArrayList<Cuota>();
				for (CuotaDto cuotaDto : ventaDto.getCuotas()){
					Cuota cuota = getEntityFromDto(cuotaDto);
					cuota.setVenta(venta);
					cuota.setCliente(cliente);
					cuotas.add(cuota);
				}
				venta.setCuotas(cuotas);
			}
		}
			

		venta.setDescuento(VentaHelper.getDescuentoTotal(ventaDto));
		venta.setFecha(new Date());
		venta.setTipo(ventaDto.getTipo());
		venta.setNumeroTicketOriginal(ventaDto.getNumeroTicketOriginal());
		List<ProductoVenta> productos = new ArrayList<ProductoVenta>();
		for (ProductoVentaDto productoDto : ventaDto.getProductos())
			productos.add(getEntityFromDto(productoDto));

		venta.setProductos(productos);

		List<Pago> pagos = new ArrayList<Pago>();
		for (PagoDto pagoDto : ventaDto.getPagos())
			pagos.add(getEntityFromDto(pagoDto));

		venta.setPagos(pagos);

		venta.setDiscriminacionIVA(discriminacionIVADao.findDiscriminacionIVAporCodigo((ventaDto.getTipoResponsabilidad().getTipoComprador())));

		venta.setImpuesto(ventaDto.getImpuesto());
		venta.setSubtotal(ventaDto.getSubTotal());
		venta.setTotal(ventaDto.getTotal());
		venta.setCanceled(ventaDto.isCancelada());
		venta.setPrinted(ventaDto.isImpresa());
		venta.setAjusteRedondeo(ventaDto.getAjusteRedondeo());

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
	
	private ProductoVentaDto getDtoFromEntity(ProductoVenta productoVenta) {
		ProductoVentaDto productoVentaDto = new ProductoVentaDto();
		productoVentaDto.setProducto(getDtoFromEntity(productoVenta.getProducto()));
		productoVentaDto.setCantidad(productoVenta.getCantidad());
		productoVentaDto.setIva(productoVenta.getIva());
		productoVentaDto.setPrecioTotal(productoVenta.getPrecio());
		return productoVentaDto;
	}
	
	private Cuota getEntityFromDto(CuotaDto cuotaDto) {
		Cuota cuota = new Cuota();
		if (Validator.isNotNull(cuotaDto.getId()))
			cuota = cuotaDao.get(cuotaDto.getId());
		if (Validator.isDate(cuotaDto.getFecha_pagar(), MitnickConstants.DATE_FORMAT, true))
			cuota.setFecha_pagar(DateHelper.getFecha(cuotaDto.getFecha_pagar()));
		cuota.setNroCuota(cuotaDto.getNroCuota());
		cuota.setTotal(cuotaDto.getTotal());
		cuota.setFaltaPagar(cuotaDto.getFaltaPagar());
		cuota.setCliente(getEntityFromDto(cuotaDto.getClienteDto()));
		List<Pago> pagos = new ArrayList<Pago>();
		if (Validator.isNotNull(cuotaDto.getPagos()))		
			pagos = cuota.getPagos();
		
		for (PagoDto pagoDto : cuotaDto.getPagos()){
			if (Validator.isNull(pagoDto.getId()))
				pagos.add(getEntityFromDto(pagoDto));
		}

		cuota.setPagos(pagos);
		cuota.setPagado(cuotaDto.isPagado());
		return cuota;
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
		if (Validator.isNotNull(direccion.getCiudad()))
			direccionDto.setCiudad(getDtoFromEntity(direccion.getCiudad()));
		return direccionDto;
	}
	
	private PagoDto getDtoFromEntity(Pago pago) {
		PagoDto pagoDto = new PagoDto();
		pagoDto.setId(pago.getId());
		pagoDto.setComprobante(pago.isComprobante());
		pagoDto.setMedioPago(getDtoFromEntity(pago.getMedioPago()));
		pagoDto.setMonto(pago.getPago());		
		return pagoDto;
	}
	
	private Pago getEntityFromDto(PagoDto pagoDto) {
		Pago pago = new Pago();
		pago.setId(pagoDto.getId());
		pago.setComprobante(pagoDto.isComprobante());
		pago.setMedioPago(getEntityFromDto(pagoDto.getMedioPago()));
		pago.setPago(pagoDto.getMonto());		
		return pago;
	}
	
	private MedioPago getEntityFromDto(MedioPagoDto medioPagoDto) {
		MedioPago medioPago = new MedioPago();
		medioPago.setDescripcion(medioPagoDto.getDescripcion());
		medioPago.setId(medioPagoDto.getId());
		medioPago.setCodigo(medioPagoDto.getCodigo());
		return medioPago;
	}

}
