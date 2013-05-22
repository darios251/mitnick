package com.mitnick.presentacion.vistas.paneles;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.utils.AllowBlankMaskFormatter;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;

public class NuevaCuotaDialog extends BaseDialog {
	
	private static final long serialVersionUID = 1L;
	
	private ClienteDto cliente;
	
	private ClienteController clienteController;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private JTextField txtMontoCuota;
	private JLabel lblErrorTxtMontoCuota;

	private JFormattedTextField txtFecha;
	private JLabel lblErrorTxtFecha;

	private JTextField txtDescripcion;
	
	private JLabel lblFecha;
	private JLabel lblMontoOriginal;
	private JLabel lblMontoCuota;
	private JLabel lblDescripcion;
	
	private JLabel lblMontoOriginalValor;
	
	private CuotaDto cuotaDto;
	
	public NuevaCuotaDialog(JFrame frame, ClienteDto cliente, CuotaDto cuotaDto, ClienteController clienteController) {
		super(frame, true);
		this.clienteController = clienteController;
		getContentPane().setLayout(null);
		setSize(500, 245);
		
		setLocationRelativeTo(null);
				
		getContentPane().add(getTxtFecha());
		getContentPane().add(getTxtMontoCuota());
		getContentPane().add(getTxtDescripcion());
		getContentPane().add(getLblFecha());
		getContentPane().add(getLblMontoCuota());
		getContentPane().add(getLblDescripcion());
		getContentPane().add(getBtnAceptar());
		getContentPane().add(getBtnCancelar());
		getContentPane().add(getLblMontoCuotaOriginal());
		getContentPane().add(getLblMontoCuotaOriginalValor());
		
		getContentPane().add(getLblErrorTxtFecha());
		getContentPane().add(getLblErrorTxtMontoCuota());
		
		String fecha = "";
		if (cuotaDto.getFaltaPagar()!=null)
			fecha = cuotaDto.getFecha_pagar();
		
		String monto = "";
		if (cuotaDto.getTotal()!=null)
			monto = cuotaDto.getTotal().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		
		String montoAPagar = "";
		if (cuotaDto.getFaltaPagar()!=null)
			montoAPagar = cuotaDto.getFaltaPagar().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		
		String descripcion = "";
		if (cuotaDto.getDescripcion()!=null)
			descripcion = cuotaDto.getDescripcion();
		
		getTxtFecha().setText(fecha);
		getLblMontoCuotaOriginalValor().setText(monto);
		getTxtMontoCuota().setText(montoAPagar);
		getTxtDescripcion().setText(descripcion);
		
		setCuotaDto(cuotaDto);
		setCliente(cliente);
		if (cuotaDto!=null && cuotaDto.getId()!=null)
			this.setTitle(PropertiesManager.getProperty("nuevaCuotaDialog.edit.title"));
		else
			this.setTitle(PropertiesManager.getProperty("nuevaCuotaDialog.new.title"));
		
		setVisible(true);
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
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
					keyIntro();
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

			btnCancelar.setBounds(250, 140, 60, 60);
			btnCancelar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					keyEscape();
				}
			});
		}
		
		return btnCancelar;
	}
	
	public JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel(PropertiesManager.getProperty("nuevaCuotaDialog.label.fecha"));
			lblFecha.setBounds(110, 15, 70, 20);
		}
		return lblFecha;
	}

	public JLabel getLblMontoCuotaOriginal() {
		if (lblMontoOriginal == null) {
			lblMontoOriginal = new JLabel(PropertiesManager.getProperty("nuevaCuotaDialog.label.monto"));
			lblMontoOriginal.setBounds(110, 45, 70, 20);
		}
		return lblMontoOriginal;
	}
	
	public JLabel getLblMontoCuota() {
		if (lblMontoCuota == null) {
			lblMontoCuota = new JLabel(PropertiesManager.getProperty("nuevaCuotaDialog.label.montoAPagar"));
			lblMontoCuota.setBounds(110, 75, 70, 20);
		}
		return lblMontoCuota;
	}
	
	public JLabel getLblDescripcion() {
		if (lblDescripcion == null) {
			lblDescripcion = new JLabel(PropertiesManager.getProperty("nuevaCuotaDialog.label.descripcion"));
			lblDescripcion.setBounds(110, 105, 70, 20);
		}
		return lblDescripcion;
	}
	
	public JTextField getTxtFecha() {
		if (txtFecha == null) {
			try{
				txtFecha =  new JFormattedTextField(new AllowBlankMaskFormatter(MitnickConstants.DATE_MASKFORMAT)); 	
				txtFecha.setColumns(10);
				txtFecha.setBounds(190, 15, 110, 20);
			} catch (ParseException e) {}
		}
		return txtFecha;
	}

	public JLabel getLblMontoCuotaOriginalValor() {
		if (lblMontoOriginalValor == null) {
			lblMontoOriginalValor = new JLabel("");
			lblMontoOriginalValor.setBounds(190, 45, 70, 20);
		}
		return lblMontoOriginalValor;
	}

	public JTextField getTxtMontoCuota() {
		if (txtMontoCuota == null) {
			txtMontoCuota = new JTextField();
			txtMontoCuota.setColumns(10);
			txtMontoCuota.setBounds(190, 75, 110, 20);
			txtMontoCuota.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
				}
				@Override
				public void keyReleased(KeyEvent e) {
					actualizarTotales();
				}
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
				}
			});
		}
		return txtMontoCuota;
	}
	
	private void actualizarTotales(){
		try {
			BigDecimal aPagarOriginal = new BigDecimal(0);
			BigDecimal totalOriginal =  new BigDecimal(0);
			BigDecimal nuevoApagar =  new BigDecimal(txtMontoCuota.getText());
			BigDecimal nuevoTotal = nuevoApagar;
			if (Validator.isNotNull(cuotaDto) && Validator.isNotNull(cuotaDto.getFaltaPagar())  && Validator.isNotNull(cuotaDto.getTotal()) ){
				aPagarOriginal = cuotaDto.getFaltaPagar();	
				totalOriginal = cuotaDto.getTotal();
				BigDecimal diferencia = nuevoApagar.subtract(aPagarOriginal);
				nuevoTotal = totalOriginal.add(diferencia);		
			} 
			getLblMontoCuotaOriginalValor().setText(nuevoTotal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		} catch (Exception e) {			
		}
	}
	
	public JTextField getTxtDescripcion() {
		if (txtDescripcion == null) {
			txtDescripcion = new JTextField();
			txtDescripcion.setColumns(10);
			txtDescripcion.setBounds(190, 105, 250, 20);
		}
		return txtDescripcion;
	}
		
	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public CuotaDto getCuotaDto() {
		return cuotaDto;
	}

	public void setCuotaDto(CuotaDto cuotaDto) {
		this.cuotaDto = cuotaDto;
	}
	
	public JLabel getLblErrorTxtMontoCuota() {
		if (lblErrorTxtMontoCuota == null) {
			lblErrorTxtMontoCuota = new JLabel("");
			lblErrorTxtMontoCuota.setBounds(310, 86, 200, 20);
		}
		return lblErrorTxtMontoCuota;
	}

	public JLabel getLblErrorTxtFecha() {
		if (lblErrorTxtFecha == null) {
			lblErrorTxtFecha = new JLabel("");
			lblErrorTxtFecha.setBounds(310, 55, 200, 20);
		}
		return lblErrorTxtFecha;
	}

	protected void keyEscape() {
		setVisible(false);
	}
	
	protected void keyIntro() {
		try {
			clienteController.guardarCuota(cuotaDto, getLblMontoCuotaOriginalValor().getText(), getTxtFecha().getText(), getTxtDescripcion().getText());
			setVisible(false);
		} catch (PresentationException ex) {
			mostrarMensaje(ex);
		}
	}
	
}
