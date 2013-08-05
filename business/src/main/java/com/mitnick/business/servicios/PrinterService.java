package com.mitnick.business.servicios;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mitnick.exceptions.PrinterException;
import com.mitnick.persistence.daos.IClienteDao;
import com.mitnick.servicio.servicios.ICierreZServicio;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.CierreZDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.ConfiguracionImpresoraDto;
import com.mitnick.utils.dtos.DireccionDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

@Component(value="printerService")
public class PrinterService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ICierreZServicio cierreZServicio;
	
	@Autowired
	protected IClienteDao clienteDao;
	
	protected Socket currentConnection;
	protected PrintStream output = null;
	protected DataInputStream input = null;
	
	private static final int TIME_TO_WAIT = 600;
	
	private static final String TICKET_TAG = "[TICKET]";
	private static final String FIN_TICKET_TAG = "[FIN-TICKET]";
	private static final String ITEM_TAG = "[ITEM]";
	private static final String FIN_ITEM_TAG = "[FIN-ITEM]";
	private static final String ITEM_CANTIDAD = "[ITEM-CANTIDAD]";
	private static final String ITEM_DESCRIPCION = "[ITEM-DESCRIPTION]";
	private static final String ITEM_PRECIO = "[ITEM-PRECIO]";
	private static final String ITEM_IVA = "[ITEM-IVA]";
	private static final String SUBTOTAL = "[SUBTOTAL]";
	private static final String PAYMENT = "[PAYMENT]";
	private static final String PAGO_MONTO = "[PAGO-MONTO]";
	private static final String PAGO_DESCRIPCION = "[PAGO-DESCRIPTION]";
	private static final String FIN_PAGO = "[FIN-PAGO]";
	private static final String DESCUENTO = "[DISCOUNT]";
	private static final String RECARGO = "[SURCHARGE]";
	private static final String CLOSE_COLA = "[CLOSE-COLA]";
	private static final String BLANK_LINE = "[BLANK-LINE]";
	
	private static final String CIERRE_Z_TAG = "[CIERRE-Z]";
	private static final String CIERRE_X_TAG = "[CIERRE-X]";
	private static final String INFORME_JORNADA_TAG = "[INFORME-JORNADA]";
	
	private static final String TICKET_FACTURA_TAG = "[TICKET-FACTURA]";
	private static final String DATOS_COMPRADOR = "[DATOS-COMPRADOR]";
	private static final String NOMBRE_COMPRADOR = "[NOMBRE-COMPRADOR]";
	private static final String DIRECCION_COMPRADOR = "[DIRECCION-COMPRADOR]";
	private static final String TIPO_DOCUMENTO_COMPRADOR = "[TIPO-DOCUMENTO-COMPRADOR]";
	private static final String NUMERO_DOCUMENTO_COMPRADOR = "[NUMERO-DOCUMENTO-COMPRADOR]";
	private static final String TIPO_IVA_COMPRADOR = "[TIPO-IVA-COMPRADOR]";
	private static final String LINEA_REMITOS_ASOCIADOS = "[LINEA-REMITOS-ASOCIADOS]";
	private static final String FIN_DATOS_COMPRADOR = "[FIN-DATOS-COMPRADOR]";
	
	private static final String CONFIGURAR = "[CONFIGURACION]";
	private static final String INFO_CONFIGURACION = "[INFO-CONFIGURACION]";
	private static final String DOMICILIO_COMERCIAL_1 = "[DOMICILIO-COMERCIAL-1]";
	private static final String DOMICILIO_COMERCIAL_2 = "[DOMICILIO-COMERCIAL-2]";
	private static final String DOMICILIO_COMERCIAL_3 = "[DOMICILIO-COMERCIAL-3]";
	private static final String DOMICILIO_FISCAL_1 = "[DOMICILIO-FISCAL-1]";
	private static final String DOMICILIO_FISCAL_2 = "[DOMICILIO-FISCAL-2]";
	private static final String DOMICILIO_FISCAL_3 = "[DOMICILIO-FISCAL-3]";
	private static final String INGRESOS_BRUTOS_1 = "[INGRESOS-BRUTOS-1]";
	private static final String INGRESOS_BRUTOS_2 = "[INGRESOS-BRUTOS-2]";
	private static final String INGRESOS_BRUTOS_3 = "[INGRESOS-BRUTOS-3]";
	private static final String INGRESO_ACTIVIDADES = "[INGRESO-ACTIVIDADES]";
	
	private static final String INFO_TICKET_FACTURA = "[INFO-TICKET-FACTURA]";
	private static final String CANCELAR_TICKET_FACTURA = "[CANCELAR-TICKET-FACTURA]";
	private static final String INFO_TICKET = "[INFO-TICKET]";
	private static final String CANCELAR_TICKET = "[CANCELAR-TICKET]";
	
	private static final String NOTA_CREDITO = "[NOTA-CREDITO]";
	private static final String INFO_NOTA_CREDITO = "[INFO-NOTA-CREDITO]";
	private static final String CANCELAR_NOTA_CREDITO = "[CANCELAR-NOTA-CREDITO]";
	private static final String NUMERO_FACTURA_ORIGINAL = "[LINEA-COMPROBANTE-ORIGEN]";
	
	/**
	 * Este metodo se llama cuando el vendedor es monotributista.
	 * @param venta
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean imprimirTicket(VentaDto venta) {
		try {
			connect();
			
			output.println(TICKET_TAG);
			checkStatus();
			output.println(BLANK_LINE);
			
			getInfoTicket(venta, true);
			
			for(ProductoVentaDto producto : venta.getProductos()) {
				output.println(ITEM_TAG);
				
				output.println(ITEM_DESCRIPCION); //extra 1
				output.println(producto.getDescripcion());
				output.println(ITEM_DESCRIPCION); //extra 2
				output.println("");
				output.println(ITEM_DESCRIPCION); //extra 3
				String extra3 = "";
				if (Validator.isNotNull(producto.getDescuento())){
					extra3 = "Descuento: " + producto.getDescuento().getDescuento().toString();
				}
				output.println(extra3);				
				output.println(ITEM_DESCRIPCION); //extra 4
				output.println("");
				output.println(ITEM_DESCRIPCION);
				output.println("Código -- " + producto.getProducto().getCodigo());

				output.println(ITEM_CANTIDAD);
				output.println(producto.getCantidad());
				
				output.println(ITEM_PRECIO);
				output.println(producto.getProducto().getPrecioVenta().setScale (2, BigDecimal.ROUND_HALF_UP));
				
				output.println(ITEM_IVA);
				output.println("21");
				
				output.println(FIN_ITEM_TAG);
			}
			
			checkStatus();
			
			output.println(SUBTOTAL);
			
			checkStatus();
			
			if(venta.getAjusteRedondeo().compareTo(BigDecimal.ZERO) < 0) {
				output.println(DESCUENTO);
				output.println("Ajuste por redondeo");
				output.println(venta.getAjusteRedondeo().abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}
			else if(venta.getAjusteRedondeo().compareTo(BigDecimal.ZERO) > 0) {
				output.println(RECARGO);
				output.println("Ajuste por redondeo");
				output.println(venta.getAjusteRedondeo().abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}
			
			//se aplican los descuentos sobre el total de la venta
			BigDecimal descuentos = BigDecimal.ZERO;
			String tipoFactura = PropertiesManager.getProperty("application.tipoComprador.responsableInscripto");
			if (!Validator.isBlankOrNull(tipoFactura))
				tipoFactura = "I";
			if (Validator.isNotNull(venta.getTipoResponsabilidad()) && tipoFactura.equals(venta.getTipoResponsabilidad().getTipoComprador()))
				descuentos = venta.getDescuentoTotalSinIva();
			else
				descuentos = venta.getDescuentoTotal();
			if(Validator.isMoreThanZero(descuentos)) {
				output.println(DESCUENTO);
				output.println("Descuentos:");
				output.println(descuentos.abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}			
			
			checkStatus();
			
			for(PagoDto pago : venta.getPagos()) {
				output.println(PAYMENT);
				output.println(PAGO_DESCRIPCION);
				output.println(""); // linea extra
				output.println(PAGO_DESCRIPCION);
				output.println(pago.getMedioPago().getDescripcion());
				output.println(PAGO_MONTO);
				output.println(pago.getMonto().setScale (2, BigDecimal.ROUND_HALF_UP));
				output.println(FIN_PAGO);
			}
			
			checkStatus();
			
			output.println(FIN_TICKET_TAG);
			
			output.println(CLOSE_COLA);
			output.println("Este comprobante es válido para");
			output.println(CLOSE_COLA);
			output.println("utilizar en devoluciones en el local");
			output.println(CLOSE_COLA);
			output.println("que se presente");
			output.println("[FIN-COLA-TICKET]");
			
			checkStatus();
			
			output.println(FIN_TICKET_TAG);
			
			checkStatus();
			
		    String line = "";
		    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
		    
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			String errorLine = readErrorLine(e);
			cancelarTicket(venta, true);
			throw new PrinterException(errorLine);
		}
		finally {
			closeConnection();
		}
		
		return true;
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean imprimirTicketFactura(VentaDto venta) {
		try {
			if(venta.isDevolucion())
				return imprimirNotaCredito(venta);
			
			connect();
			output.println(TICKET_FACTURA_TAG);
			checkStatus();
			
			output.println(DATOS_COMPRADOR);
			
			datosComprador(venta);
			
			output.println(TIPO_IVA_COMPRADOR);
			output.println(venta.getTipoResponsabilidad().getTipoComprador());
			output.println(LINEA_REMITOS_ASOCIADOS);
			output.println(getVendedor(venta));
			output.println(LINEA_REMITOS_ASOCIADOS);
			output.println(getSaldoDeudor(venta.getCliente()));
			output.println(NUMERO_DOCUMENTO_COMPRADOR);
			output.println("");
			output.println(FIN_DATOS_COMPRADOR);
			
			checkStatus();
			
			getInfoTicketFactura(venta, true);
			
			checkStatus();
			
			for(ProductoVentaDto producto : venta.getProductos()) {
				output.println(ITEM_TAG);
				
				output.println(ITEM_DESCRIPCION); //extra 1
				output.println(producto.getDescripcion());
				output.println(ITEM_DESCRIPCION); //extra 2
				output.println("");
				output.println(ITEM_DESCRIPCION); //extra 3
				String extra3 = "";
				if (Validator.isNotNull(producto.getDescuento())){
					extra3 = "Descuento: " + producto.getDescuento().getDescuento().toString();
				}
				output.println(extra3);
				output.println(ITEM_DESCRIPCION); //extra 4
				output.println("");
				output.println(ITEM_DESCRIPCION);
				output.println("Código -- " + producto.getProducto().getCodigo());

				output.println(ITEM_CANTIDAD);
				output.println(producto.getCantidad());
				
				output.println(ITEM_PRECIO);
				output.println(producto.getProducto().getPrecioVenta().setScale(2, RoundingMode.HALF_UP));
				
				output.println(ITEM_IVA);
				output.println("21");
				
				output.println(FIN_ITEM_TAG);
			}
			
			checkStatus();
			
			output.println(SUBTOTAL);
			
			checkStatus();
			
			if(venta.getAjusteRedondeo().compareTo(BigDecimal.ZERO) < 0) {
				output.println(DESCUENTO);
				output.println("Ajuste por redondeo");
				output.println(venta.getAjusteRedondeo().abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}
			else if(venta.getAjusteRedondeo().compareTo(BigDecimal.ZERO) > 0) {
				output.println(RECARGO);
				output.println("Ajuste por redondeo");
				output.println(venta.getAjusteRedondeo().abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}
			
			//se aplican los descuentos sobre el total de la venta
			BigDecimal descuentos = BigDecimal.ZERO;
			String tipoFactura = PropertiesManager.getProperty("application.tipoComprador.responsableInscripto");
			if (!Validator.isBlankOrNull(tipoFactura))
				tipoFactura = "I";
			if (Validator.isNotNull(venta.getTipoResponsabilidad()) && tipoFactura.equals(venta.getTipoResponsabilidad().getTipoComprador()))
				descuentos = venta.getDescuentoTotalSinIva();
			else
				descuentos = venta.getDescuentoTotal();
			if(Validator.isMoreThanZero(descuentos)) {
				output.println(DESCUENTO);
				output.println("Descuentos:");
				output.println(descuentos.abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}						
			
			checkStatus();
			
			for(PagoDto pago : venta.getPagos()) {
				output.println(PAYMENT);
				output.println(PAGO_DESCRIPCION);
				output.println(""); // linea extra
				output.println(PAGO_DESCRIPCION);
				output.println(pago.getMedioPago().getDescripcion());
				output.println(PAGO_MONTO);
				output.println(pago.getMonto().setScale (2, BigDecimal.ROUND_HALF_UP));
				output.println(FIN_PAGO);
			}
			
			checkStatus();
			
			output.println(FIN_TICKET_TAG);
			
			output.println(CLOSE_COLA);
			output.println("Este comprobante es válido para");
			output.println(CLOSE_COLA);
			output.println("utilizar en devoluciones en el local");
			output.println(CLOSE_COLA);
			output.println("que se presente");
			output.println("[FIN-COLA-TICKET]");
			
			checkStatus();
			
			output.println(FIN_TICKET_TAG);
			
			checkStatus();
			
		    String line = "";
		    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
			
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			String errorLine = readErrorLine(e);
			cancelarTicketFactura(venta, true);
			throw new PrinterException(errorLine);
		}
		finally {
			closeConnection();
		}
		
		return true;
	}
	
	private String getVendedor(VentaDto venta){
		if (Validator.isNotNull(PropertiesManager.getPropertyAsBoolean("application.venta.vendedor")) && PropertiesManager.getPropertyAsBoolean("application.venta.vendedor").booleanValue()) {
			if (Validator.isNotNull(venta.getVendedor())){
				return PropertiesManager.getProperty("vendedor.leyenda",  new Object[]{venta.getVendedor().getNombre()});
			}
		}
		 return "............";
	}
	private String getSaldoDeudor(ClienteDto cliente){
		if (Validator.isNotNull(cliente)){
			BigDecimal deuda = clienteDao.getSaldoDeudor(cliente);
			if (Validator.isNotNull(deuda) && Validator.isMoreThanZero(deuda))
				return "Tiene un saldo deudor de: ".concat(deuda.toString());
		}
		return "............";
	}
	
	@SuppressWarnings("deprecation")
	public boolean imprimirNotaCredito(VentaDto venta) {
		try {
			
			connect();
			output.println(NOTA_CREDITO);
			checkStatus();
			
			output.println(DATOS_COMPRADOR);
			
			completarDatosClienteCredito(venta);
			datosComprador(venta);
			limpiarDatosClienteCredito(venta);
			
			output.println(TIPO_IVA_COMPRADOR);
			output.println(venta.getTipoResponsabilidad().getTipoComprador());
			output.println(LINEA_REMITOS_ASOCIADOS);
			output.println(getVendedor(venta));
			output.println(LINEA_REMITOS_ASOCIADOS);
			output.println(getSaldoDeudor(venta.getCliente()));
			output.println(NUMERO_FACTURA_ORIGINAL);
			output.println(venta.getNumeroTicketOriginal());
			output.println(FIN_DATOS_COMPRADOR);
			
			checkStatus();
			
			getInfoNotaCredito(venta, true);
			
			checkStatus();
			
			for(ProductoVentaDto producto : venta.getProductos()) {
				output.println(ITEM_TAG);
				
				output.println(ITEM_DESCRIPCION); //extra 1
				output.println(producto.getDescripcion());
				output.println(ITEM_DESCRIPCION); //extra 2
				output.println("");
				output.println(ITEM_DESCRIPCION); //extra 3
				String extra3 = "";
				if (Validator.isNotNull(producto.getDescuento())){
					extra3 = "Descuento: " + producto.getDescuento().getDescuento().toString();
				}
				output.println(extra3);				
				output.println(ITEM_DESCRIPCION); //extra 4
				output.println("");
				output.println(ITEM_DESCRIPCION);
				output.println("Código -- " + producto.getProducto().getCodigo());

				output.println(ITEM_CANTIDAD);
				output.println(producto.getCantidad());
				
				output.println(ITEM_PRECIO);
				output.println(producto.getProducto().getPrecioVenta().setScale(2, RoundingMode.HALF_UP));
				
				output.println(ITEM_IVA);
				output.println("21");
				
				output.println(FIN_ITEM_TAG);
			}
			
			checkStatus();
			
			output.println(SUBTOTAL);
			
			checkStatus();
			
			if(venta.getAjusteRedondeo().compareTo(BigDecimal.ZERO) < 0) {
				output.println(DESCUENTO);
				output.println("Ajuste por redondeo");
				output.println(venta.getAjusteRedondeo().abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}
			else if(venta.getAjusteRedondeo().compareTo(BigDecimal.ZERO) > 0) {
				output.println(RECARGO);
				output.println("Ajuste por redondeo");
				output.println(venta.getAjusteRedondeo().abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}
			
			//se aplican los descuentos sobre el total de la venta
			BigDecimal descuentos = BigDecimal.ZERO;
			String tipoFactura = PropertiesManager.getProperty("application.tipoComprador.responsableInscripto");
			if (!Validator.isBlankOrNull(tipoFactura))
				tipoFactura = "I";
			if (Validator.isNotNull(venta.getTipoResponsabilidad()) && tipoFactura.equals(venta.getTipoResponsabilidad().getTipoComprador()))
				descuentos = venta.getDescuentoTotalSinIva();
			else
				descuentos = venta.getDescuentoTotal();
			if(Validator.isMoreThanZero(descuentos)) {
				output.println(DESCUENTO);
				output.println("Descuentos:");
				output.println(descuentos.abs().setScale (2, BigDecimal.ROUND_HALF_UP));
			}						
			
			checkStatus();
			
			output.println(PAYMENT);
			output.println(PAGO_DESCRIPCION);
			output.println(""); // linea extra
			output.println(PAGO_DESCRIPCION);
			output.println("Nota de crédito por: ");
			output.println(PAGO_MONTO);
			output.println(venta.getTotal().setScale (2, BigDecimal.ROUND_HALF_UP));
			output.println(FIN_PAGO);
			
			checkStatus();
			
			output.println(FIN_TICKET_TAG);
			
			output.println(CLOSE_COLA);
			output.println("Este comprobante es válido para");
			output.println(CLOSE_COLA);
			output.println("utilizar en devoluciones en el local");
			output.println(CLOSE_COLA);
			output.println("que se presente");
			output.println("[FIN-COLA-TICKET]");
			
			checkStatus();
			
			output.println(FIN_TICKET_TAG);
			
			checkStatus();
			
		    String line = "";
		    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
			
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			String errorLine = readErrorLine(e);
			cancelarNotaCredito(venta, true);
			throw new PrinterException(errorLine);
		}
		finally {
			closeConnection();
		}
		
		return true;
	}
	
	private void completarDatosClienteCredito(VentaDto venta){
		ClienteDto cliente = venta.getCliente();
		if (Validator.isNull(cliente)){
			cliente = new ClienteDto();
			cliente.setNombre("....");
			venta.setCliente(cliente);
		}
						
		if (Validator.isBlankOrNull(cliente.getDocumento()))
			cliente.setDocumento("00000000");
		if (Validator.isNull(cliente.getDireccion()) || Validator.isBlankOrNull(cliente.getDireccion().getDomicilio())){
			DireccionDto domicilio = new DireccionDto();
			domicilio.setDomicilio("....");
			cliente.setDireccion(domicilio);
		}
			
	}
	
	private void limpiarDatosClienteCredito(VentaDto venta){
		ClienteDto cliente = venta.getCliente();
		if (Validator.isNull(cliente.getId())){
			venta.setCliente(null);
		} else {
			if ("00000000".equals(cliente.getDocumento()))
				cliente.setDocumento("");
			if (Validator.isNotNull(cliente.getDireccion()) && "....".equals(cliente.getDireccion().getDomicilio())){
				cliente.setDireccion(null);
			}
		}
			
			
	}
	
	@SuppressWarnings("deprecation")
	public boolean imprimirCierreZ() {
		try {
			logger.debug("Ejecutando: cierre Z");
			connect();
			
			output.println(CIERRE_Z_TAG);
			
			checkStatus();
			
			output.println(FIN_TICKET_TAG);
			
			checkStatus();
			
			String cierreNro = "";
			logger.debug("Esperando el número de cierre");
			try{
			if (Validator.isNotNull(input))
				while(!(cierreNro = input.readLine()).startsWith("[CIERRE-NUMERO]:"));
				cierreNro = cierreNro.split(":")[1];
			} catch (Exception e){
				logger.error(e);
				cierreNro = "0";
			}
			logger.info("cierre Nro: " + cierreNro);
			
			
			
			try {
				logger.debug("Se guarda el cierre Z - caja: " + PropertiesManager.getPropertyAsInteger("application.caja.numero"));
				logger.debug("Numero de cierre: " + cierreNro);
				
				CierreZDto cierreZ = new CierreZDto();
				cierreZ.setNumeroCaja(PropertiesManager.getPropertyAsInteger("application.caja.numero"));
				cierreZ.setNumero(cierreNro);
				cierreZ.setFecha(new Date());
				cierreZServicio.guardarCierre(cierreZ);
			} catch (Exception e) {
				logger.error("Error al guardar el cierre Z: " + e);
			}
			String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
		} 
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine(e));
		}
		finally {
			closeConnection();
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean imprimirCierreX() {
		try {
			connect();
			output.println(CIERRE_X_TAG);
			checkStatus();
			
			output.println(FIN_TICKET_TAG);
			checkStatus();
			
			String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
			
		} 
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine(e));
		}
		finally {
			closeConnection();
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean imprimirInformeJornada() {
		try {
			connect();
			output.println(INFORME_JORNADA_TAG);
			checkStatus();
			
			output.println(FIN_TICKET_TAG);
			
			String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
			
		} 
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine(e));
		}
		
		finally {
			closeConnection();
		}
		
		return true;
	}
	
	private void checkStatus() throws Exception {
		logger.debug("Check Printer Input: " + input);
		if(input.available() > 0) {
			@SuppressWarnings("deprecation")
			String line = input.readLine();
			logger.debug("Check Printer Status: " + line);
			if("[OK]".equals(line))
				return;
			else if("[ERROR]".equals(line))
				throw new Exception();
		}
	}

	@SuppressWarnings("deprecation")
	private String readErrorLine(Exception e) {
		logger.error(e);
		try {
			String line = "";
			if(input.available() > 0)
				line = input.readLine();
			
			if(line.startsWith("[ERROR]"))
				return input.readLine();
			else if("".equals(line))
				return "Hubo un error con la impresora";
			else
				return line;
				
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch(Exception ex2) {
			logger.error(ex2);
			return "Hubo un error con la impresora";
		}
	}

	@SuppressWarnings("deprecation")
	public boolean configurarImpresora(ConfiguracionImpresoraDto configuracion) {
		try {
			connect();
			
			if(configuracion.getDomicilioComercial1() != null) {
				output.println(CONFIGURAR);
				output.println(DOMICILIO_COMERCIAL_1);
				output.println(configuracion.getDomicilioComercial1());
				checkStatus();
				Thread.sleep(TIME_TO_WAIT);
			}
			
			if(configuracion.getDomicilioComercial2() != null) {
				output.println(CONFIGURAR);
				output.println(DOMICILIO_COMERCIAL_2);
				output.println(configuracion.getDomicilioComercial2());
				checkStatus();
				Thread.sleep(TIME_TO_WAIT);
			}
			
			if(configuracion.getDomicilioComercial3() != null) {
				output.println(CONFIGURAR);
				output.println(DOMICILIO_COMERCIAL_3);
				output.println(configuracion.getDomicilioComercial3());
				checkStatus();
				Thread.sleep(TIME_TO_WAIT);
			}
			
			if(configuracion.getDomicilioFiscal1() != null) {
				output.println(CONFIGURAR);
				output.println(DOMICILIO_FISCAL_1);
				output.println(configuracion.getDomicilioFiscal1());
				checkStatus();
				Thread.sleep(TIME_TO_WAIT);
			}
			
			if(configuracion.getDomicilioFiscal2() != null) {
				output.println(CONFIGURAR);
				output.println(DOMICILIO_FISCAL_2);
				output.println(configuracion.getDomicilioFiscal2());
				checkStatus();
				Thread.sleep(TIME_TO_WAIT);
			}
			
			if(configuracion.getDomicilioFiscal3() != null) {
				output.println(CONFIGURAR);
				output.println(DOMICILIO_FISCAL_3);
				output.println(configuracion.getDomicilioFiscal3());
				checkStatus();
				Thread.sleep(TIME_TO_WAIT);
			}
			
			if(configuracion.getIngresosBrutos1() != null) {
				output.println(CONFIGURAR);
				output.println(INGRESOS_BRUTOS_1);
				output.println(configuracion.getIngresosBrutos1());
				checkStatus();
				Thread.sleep(TIME_TO_WAIT);
			}
			
			if(configuracion.getIngresosBrutos2() != null) {
				output.println(CONFIGURAR);
				output.println(INGRESOS_BRUTOS_2);
				output.println(configuracion.getIngresosBrutos2());
				checkStatus();
				Thread.sleep(TIME_TO_WAIT);
			}
			
			if(configuracion.getIngresosBrutos3() != null) {
				output.println(CONFIGURAR);
				output.println(INGRESOS_BRUTOS_3);
				output.println(configuracion.getIngresosBrutos3());
				checkStatus();
				Thread.sleep(TIME_TO_WAIT);
			}
			
			if(configuracion.getFechaInicioActividades() != null) {
				output.println(CONFIGURAR);
				output.println(INGRESO_ACTIVIDADES);
				output.println(configuracion.getFechaInicioActividades());
				checkStatus();
				Thread.sleep(TIME_TO_WAIT);
			}
			
			output.println(FIN_TICKET_TAG);
			checkStatus();
			
			String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
			
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine(e));
		}
		finally {
			closeConnection();
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public ConfiguracionImpresoraDto getInfoConfiguracion() {
		ConfiguracionImpresoraDto configuracion = new ConfiguracionImpresoraDto();
		
		try {
			connect();
			
			output.println(INFO_CONFIGURACION);
			output.println(DOMICILIO_COMERCIAL_1);
			configuracion.setDomicilioComercial1(input.readLine());
			Thread.sleep(TIME_TO_WAIT);
			checkStatus();

			output.println(INFO_CONFIGURACION);
			output.println(DOMICILIO_COMERCIAL_2);
			configuracion.setDomicilioComercial2(input.readLine());
			Thread.sleep(TIME_TO_WAIT);
			checkStatus();
			
			output.println(INFO_CONFIGURACION);
			output.println(DOMICILIO_COMERCIAL_3);
			configuracion.setDomicilioComercial3(input.readLine());
			Thread.sleep(TIME_TO_WAIT);
			checkStatus();
			
			output.println(INFO_CONFIGURACION);
			output.println(DOMICILIO_FISCAL_1);
			configuracion.setDomicilioFiscal1(input.readLine());
			Thread.sleep(TIME_TO_WAIT);
			checkStatus();
			
			output.println(INFO_CONFIGURACION);
			output.println(DOMICILIO_FISCAL_2);
			configuracion.setDomicilioFiscal2(input.readLine());
			Thread.sleep(TIME_TO_WAIT);
			checkStatus();
			
			output.println(INFO_CONFIGURACION);
			output.println(DOMICILIO_FISCAL_3);
			configuracion.setDomicilioFiscal3(input.readLine());
			Thread.sleep(TIME_TO_WAIT);
			checkStatus();
			
			output.println(INFO_CONFIGURACION);
			output.println(INGRESOS_BRUTOS_1);
			configuracion.setIngresosBrutos1(input.readLine());
			Thread.sleep(TIME_TO_WAIT);
			checkStatus();
			
			output.println(INFO_CONFIGURACION);
			output.println(INGRESOS_BRUTOS_2);
			configuracion.setIngresosBrutos2(input.readLine());
			Thread.sleep(TIME_TO_WAIT);
			checkStatus();
			
			output.println(INFO_CONFIGURACION);
			output.println(INGRESOS_BRUTOS_3);
			configuracion.setIngresosBrutos3(input.readLine());
			Thread.sleep(TIME_TO_WAIT);
			checkStatus();
			
			output.println(INFO_CONFIGURACION);
			output.println(INGRESO_ACTIVIDADES);
			configuracion.setFechaInicioActividades(input.readLine());
			Thread.sleep(TIME_TO_WAIT);
			checkStatus();
			
			output.println(FIN_TICKET_TAG);
			checkStatus();
			
			String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	if(line.startsWith("[ERROR]")) {
		    		line = input.readLine();
		    		throw new PrinterException(line);
		    	}
		    	else
		    		logger.info(line);
		    }
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine(e), e);
		}
		finally {
			closeConnection();
		}
		
		return configuracion;
	}
	
	@SuppressWarnings("deprecation")
	public boolean getInfoTicketFactura(VentaDto venta, boolean useCurrentConnection) {
		try {
			connect(useCurrentConnection);
			Thread.sleep(1000);
			
			output.println(INFO_TICKET_FACTURA);
			//checkStatus();
			
			String line = "";
			
		    line = input.readLine();
		    
	    	if(line.startsWith("[ERROR]")) {
	    		line = input.readLine();
	    		throw new PrinterException(line);
	    	}
	    	else {
	    		while(line.equals("[OK]"))
	    			line = input.readLine();
	    		logger.info("info tique factura: ");
	    		logger.info(line);
	    		
	    		if(venta != null) {
		    		for(int i = 1; i <= 19; i++) {
		    			if(i == 1)
			    			venta.setNumeroTicket(line.split(":")[1]);
		    			else if(i == 2)
		    				venta.setTipoTicket(line.split(":")[1]);
		    			if(i < 19) {
		    				line = input.readLine();
		    				logger.info(line);
		    			}
		    		}
	    		}
	    	}
	    	Thread.sleep(1000);
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine(e));
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean getInfoNotaCredito(VentaDto venta, boolean useCurrentConnection) {
		try {
			connect(useCurrentConnection);
			Thread.sleep(1000);
			
			output.println(INFO_NOTA_CREDITO);
			//checkErrors();
			
			String line = "";
			
		    line = input.readLine();
		    
	    	if(line.startsWith("[ERROR]")) {
	    		line = input.readLine();
	    		throw new PrinterException(line);
	    	}
	    	else {
	    		while(line.equals("[OK]"))
    				line = input.readLine();
	    		logger.info("info tique factura: ");
	    		logger.info(line);
	    		
	    		if(venta != null) {
		    		for(int i = 1; i <= 19; i++) {
		    			if(i == 1)
			    			venta.setNumeroTicket(line.split(":")[1]);
		    			else if(i == 2)
		    				venta.setTipoTicket(line.split(":")[1]);
		    			if(i < 19) {
		    				line = input.readLine();
		    				logger.info(line);
		    			}
		    		}
	    		}
	    	}
	    	Thread.sleep(1000);
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine(e));
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean getInfoTicket(VentaDto venta, boolean useCurrentConnection) {
		try {
			connect(useCurrentConnection);
			
			output.println(INFO_TICKET);
			//checkErrors();
			
			String line = "";
			
		   line = input.readLine();
		   
		   if(line.startsWith("[ERROR]")) {
	    		line = input.readLine();
	    		throw new PrinterException(line);
	    	}
	    	else {
	    		while(line.equals("[OK]"))
	    			line = input.readLine();
	    		logger.info("info ticket:");
	    		logger.info(line);
	    		
	    		if(venta != null) {
		    		for(int i = 1; i <= 14; i++) {
		    			if(i == 1)
			    			venta.setNumeroTicket(line.split(":")[1]);
		    			if(i < 14) {
		    				line = input.readLine();
		    				logger.info(line);
		    			}
		    		}
	    		}
	    	}
		} 
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine(e));
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean cancelarTicketFactura(VentaDto venta, boolean useCurrentConnection) {
		try {
			connect(useCurrentConnection);
			
			output.println(CANCELAR_TICKET_FACTURA);
			
			String line = input.readLine();
			
			if(line.startsWith("[ERROR]")) {
	    		line = input.readLine();
	    		throw new PrinterException(line);
	    	}
	    	else {
	    		logger.info("Se canceló el tique factura n°:" + line);
	    		line = input.readLine();
	    		logger.info("y el tipo de factura:" + line);
	    	}
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine(e));
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean cancelarNotaCredito(VentaDto venta, boolean useCurrentConnection) {
		try {
			connect(useCurrentConnection);
			
			output.println(CANCELAR_NOTA_CREDITO);
			
			String line = input.readLine();
			
			if(line.startsWith("[ERROR]")) {
	    		line = input.readLine();
	    		throw new PrinterException(line);
	    	}
	    	else {
	    		logger.info("Se canceló el tique factura n°:" + line);
	    		line = input.readLine();
	    		logger.info("y el tipo de factura:" + line);
	    	}
		}
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine(e));
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean cancelarTicket(VentaDto venta, boolean useCurrentConnection) {
		try {
			connect(useCurrentConnection);
			
			output.println(CANCELAR_TICKET);
			checkStatus();
			
			String line = input.readLine();
			
			if(line.startsWith("[ERROR]")) {
	    		line = input.readLine();
	    		throw new PrinterException(line);
	    	}
	    	else
	    		logger.info("Se canceló el tique n°:" + line);
		} 
		catch (PrinterException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new PrinterException(readErrorLine(e));
		}
		return true;
	}
	
	protected Socket connect(boolean getCurrentConnection) throws UnknownHostException, IOException {
		if(getCurrentConnection && currentConnection != null && currentConnection.isConnected())
			return currentConnection;
		else
			return connect();
	}
	
	protected Socket connect() throws UnknownHostException, IOException {
		logger.debug("Conectando....");
		logger.debug("ipaddress: " + PropertiesManager.getProperty("application.printerService.ipaddress"));
		logger.debug("portaddress: " + PropertiesManager.getPropertyAsInteger("application.printerService.portaddress"));
		logger.debug("timeout: " + PropertiesManager.getPropertyAsInteger("application.printerService.openConnectionTimeOut"));
		SocketAddress sockaddr = new InetSocketAddress(PropertiesManager.getProperty("application.printerService.ipaddress"), PropertiesManager.getPropertyAsInteger("application.printerService.portaddress"));
		currentConnection = new Socket();
		currentConnection.connect(sockaddr, PropertiesManager.getPropertyAsInteger("application.printerService.openConnectionTimeOut"));
		currentConnection.setSoTimeout(PropertiesManager.getPropertyAsInteger("application.printerService.connectionTimeOut"));
		output = new PrintStream(currentConnection.getOutputStream());
		input = new DataInputStream(currentConnection.getInputStream());
		logger.debug("Input?? " + input);
		return currentConnection;
	}
	
	protected boolean closeConnection() {
		try {
			logger.debug("Input close");
			input.close();
			output.close();
			currentConnection.close();
			currentConnection = null;
		}
		catch(Exception e) {
			try {
				logger.error(e);
				logger.debug("Input close");
				input.close();
				output.close();
				currentConnection.close();
				currentConnection = null;
				return false;
			}
			catch(Exception e1) {
				logger.error(e1);
			}
			
		}
		return true;
	}
	
	private void datosComprador(VentaDto venta){
		ClienteDto cliente = venta.getCliente();
		String direccionClienteL1 = "";
		String direccionClienteL2 = "";
		String clienteNombre = "";
		String cuitDni = "";
		String tipoDT = "";
		if (Validator.isNotNull(cliente)){
			if (Validator.isNotNull(cliente) && Validator.isNotNull(cliente.getNombre()))
				clienteNombre = cliente.getNombre();
			if (Validator.isNotNull(cliente) && Validator.isNotNull(cliente.getDireccion()) && Validator.isNotNull(cliente.getDireccion().getDomicilio()))
				direccionClienteL1 = cliente.getDireccion().getDomicilio();
			
			if (Validator.isNotNull(cliente) && Validator.isNotNull(cliente.getDireccion())){
				if (Validator.isNotNull(cliente.getDireccion().getCodigoPostal()))
					direccionClienteL2 = direccionClienteL2.concat(cliente.getDireccion().getCodigoPostal());

				if (Validator.isNotNull(cliente.getDireccion().getCiudad())) {
					if (Validator.isNotNull(cliente.getDireccion().getCiudad().getDescripcion()))
						direccionClienteL2 = direccionClienteL2.concat(" - ").concat(cliente.getDireccion().getCiudad().getDescripcion() );
					if (Validator.isNotNull(cliente.getDireccion().getCiudad().getPrinvincia())
						&& Validator.isNotNull(cliente.getDireccion().getCiudad().getPrinvincia().getDescripcion()))
							direccionClienteL2 = direccionClienteL2.concat(" - ").concat(cliente.getDireccion().getCiudad().getPrinvincia().getDescripcion() );
				}
			}
			String cuit = "";
			if (Validator.isNotNull(cliente.getCuit())){
				cuit =  cliente.getCuit().replaceAll("-", "").trim();	
			}
			
			if (Validator.isNotBlankOrNull(cuit)) {
				cuitDni = cuit;
				tipoDT = "T";
			} else if (Validator.isNotBlankOrNull(cliente.getDocumento())) {
				cuitDni = cliente.getDocumento();
				tipoDT = "D";
			}
		}
		
		output.println(NOMBRE_COMPRADOR);
		output.println(clienteNombre);
		output.println(NOMBRE_COMPRADOR);
		output.println("");
		output.println(DIRECCION_COMPRADOR);
		output.println(direccionClienteL1);
		output.println(DIRECCION_COMPRADOR);
		output.println(direccionClienteL2);
		output.println(DIRECCION_COMPRADOR);
		output.println("");
		output.println(TIPO_DOCUMENTO_COMPRADOR);
		output.println(tipoDT);
		output.println(NUMERO_DOCUMENTO_COMPRADOR);
		output.println(cuitDni);
		
	}
	
}
