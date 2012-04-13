package com.mitnick.presentacion.vistas.paneles;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.presentacion.modelos.ClienteTableModel;
import com.mitnick.presentacion.vistas.BaseView;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.Panel;

@Panel("clientePanel")
public class ClientePanel extends BaseView {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtNumeroDocumento;
	private JTextField txtApellido;
	private JButton btnBuscar;
	private JLabel lblNombre;
	private JLabel lblNumeroDocumento;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JLabel lblClientes;
	private JTextField txtNombre;
	private JLabel lblNmeroCtaCte;
	private JTextField txtNumeroCtaCte;
	private JLabel lblApellido;
	private JButton btnEstadoCuenta;
	private ClienteTableModel model;
	private TableRowSorter<ClienteTableModel> sorter;
	
	private ClienteController clienteController;
	
	@Autowired(required = true)
	public ClientePanel(@Qualifier("clienteController") ClienteController clienteController) {
		this.clienteController = clienteController;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ClientePanel(boolean modoDisenio) {
		initializeComponents();
		
		throw new PresentationException("error.unknow", "este constructor es solo parar el plugin de diseño");
	}

	@Override
	protected void limpiarCamposPantalla() {
	}

	@Override
	protected void initializeComponents() {
		setLayout(null);
		setSize(new Dimension(815, 470));
		
		lblNumeroDocumento = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.documento"));
		lblNumeroDocumento.setBounds(125, 15, 70, 20);
		add(lblNumeroDocumento);
		
		txtNumeroDocumento = new JTextField();
		txtNumeroDocumento.setColumns(10);
		txtNumeroDocumento.setBounds(200, 15, 110, 20);
		add(txtNumeroDocumento);
		
		lblApellido = new JLabel(PropertiesManager.getProperty("clientePanel.etiqueta.apellido"));
		lblApellido.setBounds(125, 55, 70, 20);
		add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(200, 55, 110, 20);
		add(txtApellido);
		
		btnBuscar = new JButton("");
		btnBuscar.setToolTipText("Buscar Cliente");
		btnBuscar.setIcon(new ImageIcon(this.getClass().getResource("/img/buscar cliente.png")));
		btnBuscar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnBuscar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnBuscar.setMargin(new Insets(-1, -1, -1, -1));
		btnBuscar.setBounds(570, 15, 60, 60);
		btnBuscar.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				consultarClientes();
			}
		});
		
		add(btnBuscar);
		
		// Creo una tabla con un sorter
		model = new ClienteTableModel();
        sorter = new TableRowSorter<ClienteTableModel>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
		
		table.setBounds(0, 0, 1, 1);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 115, 700, 315);
		add(scrollPane);
		
		btnNuevo = new JButton("");
		btnNuevo.setToolTipText("Agregar Cliente");
		btnNuevo.setIcon(new ImageIcon(this.getClass().getResource("/img/nuevo_cliente.png")));
		btnNuevo.setHorizontalTextPosition( SwingConstants.CENTER );
		btnNuevo.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnNuevo.setMargin(new Insets(-1, -1, -1, -1));
		btnNuevo.setBounds(735, 115, 60, 60);
		add(btnNuevo);
		
		btnModificar = new JButton("");
		btnModificar.setToolTipText("Modificar Cliente");
		btnModificar.setIcon(new ImageIcon(this.getClass().getResource("/img/modificar_cliente.png")));
		btnModificar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnModificar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnModificar.setMargin(new Insets(-1, -1, -1, -1));
		btnModificar.setBounds(735, 185, 60, 60);
		add(btnModificar);
		
		btnEliminar = new JButton("");
		btnEliminar.setToolTipText("Eliminar Cliente");
		btnEliminar.setIcon(new ImageIcon(this.getClass().getResource("/img/eliminar_cliente.png")));
		btnEliminar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnEliminar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnEliminar.setMargin(new Insets(-1, -1, -1, -1));
		btnEliminar.setBounds(735, 255, 60, 60);
		add(btnEliminar);
		
		btnEstadoCuenta = new JButton("");
		btnEstadoCuenta.setToolTipText("Estado de la Cuenta");
		btnEstadoCuenta.setIcon(new ImageIcon(this.getClass().getResource("/img/estado_cuenta.png")));
		btnEstadoCuenta.setHorizontalTextPosition( SwingConstants.CENTER );
		btnEstadoCuenta.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnEstadoCuenta.setMargin(new Insets(-1, -1, -1, -1));
		btnEstadoCuenta.setBounds(735, 325, 60, 60);
		add(btnEstadoCuenta);
		
		lblClientes = new JLabel("Clientes");
		lblClientes.setBounds(25, 90, 70, 20);
		add(lblClientes);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(330, 55, 60, 20);
		add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(420, 55, 110, 20);
		add(txtNombre);
		
		lblNmeroCtaCte = new JLabel("Cuenta Cte");
		lblNmeroCtaCte.setBounds(330, 15, 80, 20);
		add(lblNmeroCtaCte);
		
		txtNumeroCtaCte = new JTextField();
		txtNumeroCtaCte.setColumns(10);
		txtNumeroCtaCte.setBounds(420, 15, 110, 20);
		add(txtNumeroCtaCte);
	}
	
	protected void consultarClientes() {
		try {
			ConsultaClienteDto filtroDto = new ConsultaClienteDto();
			filtroDto.setApellido(txtApellido.getText());
			filtroDto.setDocumento(txtNumeroDocumento.getText());
			filtroDto.setNombre(txtNombre.getText());
			
			model.setClientes(clienteController.obtenerClientesByFilter(filtroDto));
		}
		catch(PresentationException ex) {
			mostrarMensaje(ex);
		}
	}

	@Override
	public void actualizarPantalla() {
		if(Validator.isNotNull(txtNumeroDocumento))
			txtNumeroDocumento.requestFocus();
		consultarClientes();
	}
}