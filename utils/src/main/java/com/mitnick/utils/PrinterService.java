package com.mitnick.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProductoVentaDto;
import com.mitnick.utils.dtos.VentaDto;

@Component(value="printerService")
public class PrinterService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private static final String TICKET_TAG = "[TICKET]";
	private static final String FIN_TICKET_TAG = "[FIN-TICKET]";
	private static final String ITEM_TAG = "[ITEM]";
	private static final String FIN_ITEM_TAG = "[FIN-ITEM]";
	private static final String ITEM_CANTIDAD = "[ITEM-CANTIDAD]";
	private static final String ITEM_DESCRIPCION = "[ITEM-DESCRIPTION]";
	private static final String FIN_ITEM_DESCRIPCION = "[FIN-ITEM-DESCRIPTION]";
	private static final String ITEM_PRECIO = "[ITEM-PRECIO]";
	private static final String ITEM_IVA = "[ITEM-IVA]";
	private static final String SUBTOTAL = "[SUBTOTAL]";
	private static final String PAYMENT = "[PAYMENT]";
	private static final String PAGO_MONTO = "[PAGO-MONTO]";
	private static final String PAGO_DESCRIPCION = "[PAGO-DESCRIPTION]";
	private static final String FIN_PAGO_DESCRIPCION = "[FIN-PAGO-DESCRIPTION]";
	private static final String FIN_PAGO = "[FIN-PAGO]";
	private static final String CLOSE_COLA = "[CLOSE-COLA]";
	private static final String FIN_COLA = "[FIN-COLA]";
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
	
	public boolean imprimirTicket(VentaDto venta) {
		PrintStream output = null;
		DataInputStream input = null;
		Socket socket = null;
		
		try {
			socket = connect();
			output = new PrintStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			
			output.println(TICKET_TAG);
			output.println(BLANK_LINE);
			
			for(ProductoVentaDto producto : venta.getProductos()) {
				output.println(ITEM_TAG);
				
				output.println(ITEM_DESCRIPCION); //extra 1
				output.println(producto.getProducto().getDescripcion());
				output.println(ITEM_DESCRIPCION); //extra 2
				output.println("");
				output.println(ITEM_DESCRIPCION); //extra 3
				output.println("");
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
			
			output.println(SUBTOTAL);
			
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
			
			output.println(FIN_TICKET_TAG);
			
			output.println(CLOSE_COLA);
			output.println("Este comprobante es válido para");
			output.println(CLOSE_COLA);
			output.println("utilizar en devoluciones en el local");
			output.println(CLOSE_COLA);
			output.println("que se presente");
			output.println("[FIN-COLA-TICKET]");
			
			output.println(FIN_TICKET_TAG);
			
		    String line = "";
		    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	logger.error(line);
		    	return false;
		    }
			
		    input.close();
			output.close();
			socket.close();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		
		return true;
	}
	
	public boolean imprimirTicketFactura(VentaDto venta) {
		PrintStream output = null;
		DataInputStream input = null;
		Socket socket = null;
		
		try {
			socket = connect();
			output = new PrintStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			
			output.println(TICKET_FACTURA_TAG);
			
			output.println(DATOS_COMPRADOR);
			
			ClienteDto cliente = venta.getCliente();
			output.println(NOMBRE_COMPRADOR);
			output.println(cliente.getApellido() + " " + cliente.getNombre());
			output.println(NOMBRE_COMPRADOR);
			output.println("");
			output.println(DIRECCION_COMPRADOR);
			output.println(cliente.getDireccion().getDomicilio());
			output.println(DIRECCION_COMPRADOR);
			output.println(cliente.getDireccion().getCodigoPostal() + " - " + cliente.getDireccion().getCiudad().getDescripcion());
			output.println(DIRECCION_COMPRADOR);
			output.println(cliente.getDireccion().getCiudad().getPrinvincia().getDescripcion());
			output.println(TIPO_DOCUMENTO_COMPRADOR);
			output.println(Validator.isBlankOrNull(cliente.getCuit()) ? "D" : "T");
			output.println(NUMERO_DOCUMENTO_COMPRADOR);
			output.println(Validator.isBlankOrNull(cliente.getCuit()) ? cliente.getDocumento() : cliente.getCuit().replaceAll("-", ""));
			output.println(TIPO_IVA_COMPRADOR);
			output.println("I"); //TODO: ARREGLAR ESTO
			output.println(LINEA_REMITOS_ASOCIADOS);
			output.println("............");
			output.println(LINEA_REMITOS_ASOCIADOS);
			output.println("............");
			output.println(FIN_DATOS_COMPRADOR);
			
			for(ProductoVentaDto producto : venta.getProductos()) {
				output.println(ITEM_TAG);
				
				output.println(ITEM_DESCRIPCION); //extra 1
				output.println(producto.getProducto().getDescripcion());
				output.println(ITEM_DESCRIPCION); //extra 2
				output.println("");
				output.println(ITEM_DESCRIPCION); //extra 3
				output.println("");
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
			
			output.println(SUBTOTAL);
			
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
			
			output.println(FIN_TICKET_TAG);
			
			output.println(CLOSE_COLA);
			output.println("Este comprobante es válido para");
			output.println(CLOSE_COLA);
			output.println("utilizar en devoluciones en el local");
			output.println(CLOSE_COLA);
			output.println("que se presente");
			output.println("[FIN-COLA-TICKET]");
			
			output.println(FIN_TICKET_TAG);
			
		    String line = "";
		    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	logger.error(line);
		    	return false;
		    }
			
		    input.close();
			output.close();
			socket.close();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		
		return true;
	}
	
	public boolean imprimirCierreZ() {
		PrintStream output = null;
		DataInputStream input = null;
		Socket socket = null;
		
		try {
			socket = connect();
			output = new PrintStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			
			output.println(CIERRE_Z_TAG);
			
			output.println(FIN_TICKET_TAG);
			
			 String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	logger.error(line);
		    	return false;
		    }
			
			input.close();
			output.close();
			socket.close();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	public boolean imprimirCierreX() {
		PrintStream output = null;
		DataInputStream input = null;
		Socket socket = null;
		
		try {
			socket = connect();
			output = new PrintStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			
			output.println(CIERRE_X_TAG);
			
			output.println(FIN_TICKET_TAG);
			
			String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	logger.error(line);
		    	return false;
		    }
			
			input.close();
			output.close();
			socket.close();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	public boolean imprimirInformeJornada() {
		PrintStream output = null;
		DataInputStream input = null;
		Socket socket = null;
		
		try {
			socket = connect();
			output = new PrintStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			
			output.println(INFORME_JORNADA_TAG);
			
			output.println(FIN_TICKET_TAG);
			
			String line = "";
			    
		    while(!(line = input.readLine()).equals("<FIN DE IMPRESION>")) {
		    	logger.error(line);
		    	return false;
		    }
			
			input.close();
			output.close();
			socket.close();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	protected Socket connect() throws UnknownHostException, IOException {
		Socket socket = new Socket("192.168.1.105", 9095);
		socket.setSoTimeout(15000);
		return socket;
	}
	
}
