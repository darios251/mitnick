package com.mitnick.presentacion.vistas.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ProductoNuevoDto;

public class ActualizarStockDialog extends BaseDialog {
	
	private static final long serialVersionUID = 1L;
	
	private ProductoController productoController;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private JTextField txtProductoStock; //texto para ingresar producto y stock
	
	private JLabel lblProductoStock; //etiqueta para pedir ingreso de codigo y de stock
	private JLabel lblCodigo;//etiqueta para mostrar codigo producto
	private JLabel lblProducto; //etiqueta para mostrar producto
	private JLabel lblPrecio; //etiqueta para mostrar el precio del producto
	private JLabel lblStock; //etiqueta para mostrar stock del producto
	
	private JLabel lblEstadoProceso; //etiqueta para mostrar el estado del proceso
	
	private ProductoNuevoDto producto;
	
	public ActualizarStockDialog(JFrame frame, ProductoController productoController) {
		super(frame, true);
		this.productoController = productoController;
		getContentPane().setLayout(null);
		setSize(450, 245);
		
		setLocationRelativeTo(null);
				
		getContentPane().add(getLblEstadoProceso());
		getContentPane().add(getLblProductoStock());
		getContentPane().add(getTxtProductoStock());
		getContentPane().add(getLblCodigo());
		getContentPane().add(getLblProducto());
		getContentPane().add(getLblPrecio());
		getContentPane().add(getLblStock());
		getContentPane().add(getBtnAceptar());
		getContentPane().add(getBtnCancelar());

		this.setTitle(PropertiesManager.getProperty("actualizarStock.dialog.title"));
		
		setVisible(true);
	}

	public JButton getBtnAceptar() {
		if(btnAceptar == null) {
			btnAceptar = new JButton(PropertiesManager.getProperty("cuentaCorrienteDialog.aceptar"));;
			btnAceptar.setIcon(new ImageIcon(this.getClass().getResource("/img/aceptar.png")));
			btnAceptar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnAceptar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAceptar.setMargin(new Insets(-1, -1, -1, -1));
			btnAceptar.setBounds(95, 140, 60, 60);
			
			btnAceptar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					try {
						keyIntro();
					} catch (PresentationException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public JButton getBtnCancelar() {
		if(btnCancelar == null) {
			btnCancelar = new JButton(PropertiesManager.getProperty("cuentaCorrienteDialog.cancelar"));
			btnCancelar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
			btnCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnCancelar.setMargin(new Insets(-1, -1, -1, -1));

			btnCancelar.setBounds(275, 140, 60, 60);
			btnCancelar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					keyEscape();
				}
			});
		}
		
		return btnCancelar;
	}
	
	private static String INGRESE_CODIGO = PropertiesManager.getProperty("actualizarStock.dialog.label.ingreseCodigo");
	private static String INGRESE_STOCK = PropertiesManager.getProperty("actualizarStock.dialog.label.ingreseStock");
	private static String CONFIRME_ACTUALIZACION = PropertiesManager.getProperty("actualizarStock.dialog.label.confirme");
	private static String ACTUALIZACION_EXITO = PropertiesManager.getProperty("actualizarStock.dialog.label.exito");
	
	public JLabel getLblEstadoProceso() {
		if (lblEstadoProceso == null) {
			lblEstadoProceso = new JLabel(INGRESE_CODIGO);
			//actualizarStock.dialog.label.stock
			lblEstadoProceso.setBounds(40, 10, 350, 20);
			lblEstadoProceso.setForeground(Color.BLUE);
		}
		return lblEstadoProceso;
	}

	public JLabel getLblProductoStock() {
		if (lblProductoStock == null) {
			lblProductoStock = new JLabel(PropertiesManager.getProperty("actualizarStock.dialog.label.producto"));
			//actualizarStock.dialog.label.stock
			lblProductoStock.setBounds(110, 35, 70, 20);
		}
		return lblProductoStock;
	}
	
	public JTextField getTxtProductoStock() {
		if (txtProductoStock== null) {
				txtProductoStock =  new JTextField();	
				txtProductoStock.setColumns(10);
				txtProductoStock.setBounds(190, 35, 110, 20);
		}
		return txtProductoStock;
	}
	
	/**
	 * Etiqueta para mostrar el codigo del producto seleccioado para modificar stock. 
	 * @return
	 */
	public JLabel getLblCodigo() {
		if (lblCodigo == null) {
			lblCodigo = new JLabel("");
			lblCodigo.setBounds(110, 70, 70, 20);
		}
		return lblCodigo;
	}

	
	/**
	 * Etiqueta para mostrar la descripcion del producto seleccioado para modificar stock. 
	 * @return
	 */
	public JLabel getLblProducto() {
		if (lblProducto == null) {
			lblProducto = new JLabel("");
			lblProducto.setBounds(190, 70, 250, 20);
		}
		return lblProducto;
	}

	/**
	 * Etiqueta para mostrar el precio del producto seleccioado para modificar stock. 
	 * @return
	 */
	public JLabel getLblPrecio() {
		if (lblPrecio == null) {
			lblPrecio = new JLabel("");
			lblPrecio.setBounds(110, 100, 70, 20);
		}
		return lblPrecio;
	}
	
	/**
	 * Etiqueta para mostrar el stock del producto. 
	 * @return
	 */
	public JLabel getLblStock() {
		if (lblStock == null) {
			lblStock = new JLabel("");
			lblStock.setFont(new java.awt.Font("Tahoma", Font.BOLD, 24));
			lblStock.setBounds(190, 100, 70, 20);
		}
		return lblStock;
	}
	
	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	private void mostrarNuevoStock(){
		if (Validator.isBlankOrNull(getTxtProductoStock().getText()) || !Validator.isNumeric(getTxtProductoStock().getText())){
			mostrarMensaje(new PresentationException("error.actualizarStock.stock.invalido"));			
			txtProductoStock.setText("");
			txtProductoStock.requestFocus();
		} else {
			lblEstadoProceso.setText(CONFIRME_ACTUALIZACION);
			lblStock.setText(getTxtProductoStock().getText());
			step = 2;
		}
	}
	
	private void getProducto(){
		try {
			lblEstadoProceso.setText(INGRESE_STOCK);
			producto = productoController.getProductoByCode(txtProductoStock.getText().trim());
			lblProducto.setText(producto.getDescripcion());
			lblCodigo.setText(producto.getCodigo());
			lblPrecio.setText("$".concat(producto.getPrecioVentaConIva().toString()));
			lblStock.setText(String.valueOf(producto.getStock()));
			lblProductoStock.setText(PropertiesManager.getProperty("actualizarStock.dialog.label.stock"));
			txtProductoStock.setText("");
			txtProductoStock.requestFocus();
			step = 1;
		} catch (PresentationException ex) {
			keyEscape();
			mostrarMensaje(ex);
		}
	}
	
	private void actualizarStock(){
		try {
			productoController.guardarProducto(producto, txtProductoStock.getText());
			keyEscape();
			lblEstadoProceso.setText(ACTUALIZACION_EXITO);
		} catch (PresentationException ex) {
			if (ex.getMessage().equals("producto.edit.max.cantidad")){
				int opcion = mostrarMensajeConsulta(
						PropertiesManager.getProperty("productoPanel.dialog.confirm.edit"));
				if (opcion == JOptionPane.YES_OPTION) {
					producto.setConfirmado(true);
					productoController.guardarProducto(producto, txtProductoStock.getText());
					keyEscape();
				} 
				estadoError = true;
					
			}  else {
				txtProductoStock.requestFocus();
				mostrarMensaje(ex);
			}
		}
	}
	
	protected void keyEscape() {
		//limpiar y esperar nuevos datos	
		lblEstadoProceso.setText(INGRESE_CODIGO);
		lblProductoStock.setText(PropertiesManager.getProperty("actualizarStock.dialog.label.producto"));
		lblProducto.setText("");
		lblPrecio.setText("");
		lblCodigo.setText("");
		lblStock.setText("");
		txtProductoStock.setText("");
		txtProductoStock.requestFocus();
		producto = null;
		step = 0;
	}
	
	int step = 0;
	
	protected void keyIntro() {
		if (estadoError)
			estadoError = false;
		else {
			switch (step) {
			case 0:
				//buscar producto y mostrar datos
				getProducto();			
				break;
			case 1:
				//actualizar stock y preguntar si confirma
				mostrarNuevoStock();		
				break;
			case 2:
				//confirmo - limpiar y esperar nuevos datos
				actualizarStock();		
				break;
			default:
				break;
			}
		}

	}
	
	boolean estadoError = false;
	protected int mostrarMensaje(PresentationException ex) {
		estadoError = true;
		return super.mostrarMensaje(ex);
	}
	
}
