package com.mitnick.presentacion.vistas.paneles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;

public class NuevaCuotaDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private ClienteDto cliente;
	
	private ClienteController clienteController;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private JTextField txtMontoCuota;
	private JTextField txtFecha;
	private JLabel lblMontoCuota;
	private JLabel lblFecha;
	
	private CuotaDto cuotaDto;
	
	
	public NuevaCuotaDialog(JFrame frame, ClienteDto cliente, CuotaDto cuotaDto, ClienteController clienteController) {
		super(frame, true);
		this.clienteController = clienteController;
		getContentPane().setLayout(null);
		setSize(446, 245);
		
		setLocationRelativeTo(null);
				
		getContentPane().add(getTxtFecha());
		getContentPane().add(getTxtMontoCuota());
		getContentPane().add(getLblFecha());
		getContentPane().add(getLblMontoCuota());
		getContentPane().add(getBtnAceptar());
		getContentPane().add(getBtnCancelar());
		
		String fecha = "";
		if (cuotaDto.getFaltaPagar()!=null)
			fecha = new SimpleDateFormat(MitnickConstants.DATE_FORMAT).format(cuotaDto.getFecha_pagar());
		
		String monto = "";
		if (cuotaDto.getTotal()!=null)
			monto = cuotaDto.getTotal().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		
		getTxtFecha().setText(fecha);
		getTxtMontoCuota().setText(monto);
		
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

			btnAceptar.setBounds(84, 134, 100, 45);
			btnAceptar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					String monto = getTxtMontoCuota().getText();
					String fecha = getTxtFecha().getText();
					try {
						cuotaDto.setFecha_pagar(new SimpleDateFormat(MitnickConstants.DATE_FORMAT).parse(fecha));
					} catch (ParseException ex) {
						throw new PresentationException("Ingrese una fecha correcta: dd/MM/yyyy", "Ingrese una fecha correcta: dd/MM/yyyy");
					}
					try {
						cuotaDto.setTotal(new BigDecimal(monto));
					} catch (Exception ex) {
						throw new PresentationException("Ingrese una fecha correcta: dd/MM/yyyy", "Ingrese un monto correcto");
					}

					clienteController.guardarCuota(cuotaDto);
					setVisible(false);
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
			btnCancelar = new JButton(PropertiesManager.getProperty("cuentaCorrienteDialog.cancelar"));;
			btnCancelar.setIcon(new ImageIcon(this.getClass().getResource("/img/cancelar.png")));
			btnCancelar.setBounds(232, 134, 100, 45);
			btnCancelar.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
		}
		
		return btnCancelar;
	}
	
	public JLabel getLblMontoCuota() {
		if (lblMontoCuota == null) {
			lblMontoCuota = new JLabel("Monto: ");
			lblMontoCuota.setBounds(110, 86, 70, 20);
		}
		return lblMontoCuota;
	}
	
	public JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha: ");
			lblFecha.setBounds(110, 55, 70, 20);
		}
		return lblFecha;
	}

	public JTextField getTxtMontoCuota() {
		if (txtMontoCuota == null) {
			txtMontoCuota = new JTextField();
			txtMontoCuota.setColumns(10);
			txtMontoCuota.setBounds(190, 86, 110, 20);
		}
		return txtMontoCuota;
	}
	public JTextField getTxtFecha() {
		if (txtFecha == null) {
			txtFecha = new JTextField();
			txtFecha.setColumns(10);
			txtFecha.setBounds(190, 55, 110, 20);
		}
		return txtFecha;
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

	
}
