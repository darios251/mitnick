package com.mitnick.presentacion.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mitnick.exceptions.BaseException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.exceptions.PrinterException;
import com.mitnick.presentacion.controladores.ClienteController;
import com.mitnick.presentacion.controladores.ProductoController;
import com.mitnick.presentacion.controladores.ProveedorController;
import com.mitnick.presentacion.controladores.ReporteMovimientosController;
import com.mitnick.presentacion.controladores.ReportesController;
import com.mitnick.presentacion.controladores.VentaController;
import com.mitnick.presentacion.vistas.controles.DetailPanel;
import com.mitnick.presentacion.vistas.controles.JTabbedPaneConBoton;
import com.mitnick.presentacion.vistas.paneles.ConfiguracionImpresoraDialog;
import com.mitnick.utils.MitnickConstants;
import com.mitnick.utils.PrinterService;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.View;

@View("principalView")
public class PrincipalView extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(PrincipalView.class);
	
	@Autowired
	private VentaController ventaController;
	@Autowired
	private ProductoController productoController;
	@Autowired
	private ClienteController clienteController;
	@Autowired
	private ReporteMovimientosController reporteController;
	@Autowired
	private ReportesController reportesController;
	@Autowired
	private ProveedorController proveedorController;
	@Autowired
	private LoginView loginView;
	@Autowired
	private PrinterService printerService;
	
	private DetailPanel pnlPrincipal;
	private DetailPanel pnlToolBar;
	
	private JTabbedPaneConBoton jTabbedPaneConBoton;
	
	private JPanel jContentPane;
	private JPanel pnlToolBarArrow;
	private JToolBar tlbQuickAccess;
	private JPanel pnlArrow;
	
	private JButton btnVentas;
	private JButton btnProductos;
	private JButton btnClientes;
	private JButton btnReporte;
	private JButton btnProveedores;
	private JButton btnMovimientos;

	private JMenuBar menuBar;
	private JMenu menuProductos;
	private JMenu menuArchivo;
	private JMenu menuAyuda;

	private JLabel lblArrow;
	public static JLabel lblLogo;
	
	private PrincipalView thisView;
	private JMenu menuImpresora;
	private JMenuItem menuItemCierreZ;
	private JMenuItem menuInformeJornada;
	private JMenuItem menuConfiguracion;
	
	public PrincipalView()
	{
		thisView = this;
		initialize();
	}

	private void initialize()
	{
		this.setTitle(PropertiesManager.getProperty("principalView.titulo"));
		this.setMinimumSize(new Dimension(1000, 620));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setJMenuBar(getMenuBarra());
		this.setContentPane(getJContentPane());
	}

	private JMenuBar getMenuBarra() {
		if(menuBar == null)
		{
			menuBar = new JMenuBar();
			menuBar.add(getMenuArchivo());
			menuBar.add(getMenuArticulo());
			menuBar.add(getMenuAyuda());
			menuBar.add(getMenuImpresora());
		}
		return menuBar;
	}

	private JMenu getMenuArticulo() {
		if (menuProductos == null)
		{
			menuProductos = new JMenu();
			menuProductos.setText(PropertiesManager.getProperty("principalView.menu.productos"));
			menuProductos.add(PropertiesManager.getProperty("principalView.menu.productos.nuevo"));
			menuProductos.add(PropertiesManager.getProperty("principalView.menu.productos.consultar"));
		}
		return menuProductos;
	}

	private JMenu getMenuAyuda() {
		if (menuAyuda == null)
		{
			menuAyuda = new JMenu();
			menuAyuda.setText(PropertiesManager.getProperty("principalView.menu.ayuda"));
			menuAyuda.add(PropertiesManager.getProperty("principalView.menu.ayuda.manual"));
		}
		return menuAyuda;
	}

	private JMenu getMenuArchivo() {
		if (menuArchivo == null)
		{
			menuArchivo = new JMenu();
			menuArchivo.setText(PropertiesManager.getProperty("principalView.menu.archivo"));
			JMenuItem item = new JMenuItem();
			item.setText(PropertiesManager.getProperty("principalView.menu.archivo.logout"));
			item.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					thisView.setVisible(false);
					SecurityContextHolder.getContext().setAuthentication(null);
					loginView.setVisible(true);
				}
			});
			menuArchivo.add(item);
			
			item = new JMenuItem();
			item.setText(PropertiesManager.getProperty("principalView.menu.archivo.salir"));
			item.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			menuArchivo.add(item);
		}
		return menuArchivo;
	}

	private JButton getBtnVentas()
	{
		if(btnVentas == null)
		{
			btnVentas = new JButton();
			btnVentas.setSize(new Dimension(MitnickConstants.ACCESS_BAR_BUTTON_WIDTH, MitnickConstants.ACCESS_BAR_BUTTON_HEIGHT));
			btnVentas.setText(PropertiesManager.getProperty("principalView.button.ventas"));
			ImageIcon iconoOriginal = new ImageIcon(this.getClass().getResource("/img/shopping_bag.png"));
		    btnVentas.setIcon(iconoOriginal);
			btnVentas.setHorizontalTextPosition( SwingConstants.CENTER );
			btnVentas.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnVentas.setMargin(new Insets(-1, -1, -1, -1));
			btnVentas.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if (getJTabbedPane().indexOfComponent(ventaController.getVentaView()) == -1) {
						logger.info("Agregando el panel de ventas al tabbedPane");
						getJTabbedPane().addTab(PropertiesManager.getProperty("venta.titulo"), ventaController.getVentaView());
						ventaController.crearNuevaVenta();
					}
					logger.info("Mostrando el panel de ventas");
					
					ventaController.mostrarUltimoPanelMostrado();
					getJTabbedPane().setSelectedComponent(ventaController.getVentaView());
					getJTabbedPane().setVisible(true);
					ventaController.getUltimoPanelMostrado().setVisible(true);
				}
			});
		}

		return btnVentas;
	}
	
	private JButton getBtnArticulos()
	{
		if(btnProductos == null)
		{
			btnProductos = new JButton();
			btnProductos.setSize(new Dimension(MitnickConstants.ACCESS_BAR_BUTTON_WIDTH, MitnickConstants.ACCESS_BAR_BUTTON_HEIGHT));
			btnProductos.setText(PropertiesManager.getProperty("principalView.button.productos"));
			ImageIcon iconoOriginal = new ImageIcon(this.getClass().getResource("/img/productos.png"));
		    btnProductos.setIcon(iconoOriginal);
			btnProductos.setHorizontalTextPosition( SwingConstants.CENTER );
			btnProductos.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnProductos.setMargin(new Insets(-1, -1, -1, -1));
			btnProductos.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if (getJTabbedPane().indexOfComponent(productoController.getProductoView()) == -1) {
						logger.info("Agregando el panel de articulos al tabbedPane");
						getJTabbedPane().addTab(PropertiesManager.getProperty("producto.titulo"), productoController.getProductoView());
					}
					logger.info("Mostrando el panel de articulos");
					
					productoController.mostrarProductosPanel();
					getJTabbedPane().setSelectedComponent(productoController.getProductoView());
					getJTabbedPane().setVisible(true);
					productoController.getUltimoPanelMostrado().setVisible(true);
				}
			});
		}

		return btnProductos;
	}
	
	private JButton getBtnReporte()
	{
		if(btnReporte == null)
		{
			btnReporte = new JButton();
			btnReporte.setSize(new Dimension(MitnickConstants.ACCESS_BAR_BUTTON_WIDTH, MitnickConstants.ACCESS_BAR_BUTTON_HEIGHT));
			
			btnReporte.setText(PropertiesManager.getProperty("principalView.button.reportes"));
			
			ImageIcon iconoOriginal = new ImageIcon(this.getClass().getResource("/img/reportes.png"));
			btnReporte.setIcon(iconoOriginal);
			
			btnReporte.setHorizontalTextPosition( SwingConstants.CENTER );
			btnReporte.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnReporte.setMargin(new Insets(-1, -1, -1, -1));
			
			btnReporte.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e)	{
					if (getJTabbedPane().indexOfComponent(reportesController.getReportesPanel()) == -1) {
						logger.info("Agregando el panel de movimiento de productos al tabbedPane");
						jTabbedPaneConBoton.addTab(PropertiesManager.getProperty("reportePanel.label.reportes"), reportesController.getReportesView());
					}
					logger.info("Mostrando el panel de Movimientos de productos");
					
					reportesController.mostrarReportesPanel();
					getJTabbedPane().setSelectedComponent(reportesController.getReportesView());
					getJTabbedPane().setVisible(true);
					reportesController.getUltimoPanelMostrado().setVisible(true);
				}
			});
		}

		return btnReporte;
	}
	
	private JButton getBtnMovimientos()
	{
		if(btnMovimientos == null)
		{
			btnMovimientos = new JButton();
			btnMovimientos.setSize(new Dimension(MitnickConstants.ACCESS_BAR_BUTTON_WIDTH, MitnickConstants.ACCESS_BAR_BUTTON_HEIGHT));
			
			btnMovimientos.setText(PropertiesManager.getProperty("principalView.button.stock"));
			
			ImageIcon iconoOriginal = new ImageIcon(this.getClass().getResource("/img/data_folder.png"));
			btnMovimientos.setIcon(iconoOriginal);
			
			btnMovimientos.setHorizontalTextPosition( SwingConstants.CENTER );
			btnMovimientos.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnMovimientos.setMargin(new Insets(-1, -1, -1, -1));
			
			btnMovimientos.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e)	{
					if (getJTabbedPane().indexOfComponent(reporteController.getReporteMovimientosPanel()) == -1) {
						logger.info("Agregando el panel de movimiento de productos al tabbedPane");
						jTabbedPaneConBoton.addTab(PropertiesManager.getProperty("reportePanel.label.reportes"), reporteController.getReporteMovimientosView());
					}
					logger.info("Mostrando el panel de Movimientos de productos");
					
					reporteController.mostrarProductosPanel();
					getJTabbedPane().setSelectedComponent(reporteController.getReporteMovimientosView());
					getJTabbedPane().setVisible(true);
					reporteController.getUltimoPanelMostrado().setVisible(true);
				}
			});
		}

		return btnMovimientos;
	}
	
	private JButton getBtnProveedores()
	{
		if(btnProveedores == null)
		{
			btnProveedores = new JButton();
			btnProveedores.setSize(new Dimension(MitnickConstants.ACCESS_BAR_BUTTON_WIDTH, MitnickConstants.ACCESS_BAR_BUTTON_HEIGHT));
			
			btnProveedores.setText(PropertiesManager.getProperty("principalView.button.proveedores"));
			
			ImageIcon iconoOriginal = new ImageIcon(this.getClass().getResource("/img/proveedores.png"));
			btnProveedores.setIcon(iconoOriginal);
			
			btnProveedores.setHorizontalTextPosition( SwingConstants.CENTER );
			btnProveedores.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnProveedores.setMargin(new Insets(-1, -1, -1, -1));
			
			btnProveedores.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e)	{
					if (getJTabbedPane().indexOfComponent(proveedorController.getProveedorView()) == -1) {
						logger.info("Agregando el panel de movimiento de productos al tabbedPane");
						jTabbedPaneConBoton.addTab(PropertiesManager.getProperty("proveedorPanel.label.proveedores"), proveedorController.getProveedorView());
					}
					logger.info("Mostrando el panel de Movimientos de productos");
					
					proveedorController.mostrarProveedorPanel();
					getJTabbedPane().setSelectedComponent(proveedorController.getProveedorView());
					getJTabbedPane().setVisible(true);
					proveedorController.getUltimoPanelMostrado().setVisible(true);
				}
			});
		}

		return btnProveedores;
	}
	
	private JButton getBtnClientes()
	{
		if(btnClientes == null)
		{
			btnClientes = new JButton();
			btnClientes.setSize(new Dimension(MitnickConstants.ACCESS_BAR_BUTTON_WIDTH, MitnickConstants.ACCESS_BAR_BUTTON_HEIGHT));
			
			btnClientes.setText(PropertiesManager.getProperty("principalView.button.clientes"));
			
			ImageIcon iconoOriginal = new ImageIcon(this.getClass().getResource("/img/clientes.png"));
		    btnClientes.setIcon(iconoOriginal);
			
			btnClientes.setHorizontalTextPosition( SwingConstants.CENTER );
			btnClientes.setVerticalTextPosition( SwingConstants.BOTTOM );
			btnClientes.setMargin(new Insets(-1, -1, -1, -1));
			
			btnClientes.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e)	{
					if (getJTabbedPane().indexOfComponent(clienteController.getClientePanel()) == -1) {
						logger.info("Agregando el panel de clientes al tabbedPane");
						jTabbedPaneConBoton.addTab(PropertiesManager.getProperty("cliente.titulo"), clienteController.getClienteView());
					}
					logger.info("Mostrando el panel de clientes");
					
					clienteController.mostrarClientePanel();
					getJTabbedPane().setSelectedComponent(clienteController.getClienteView());
					getJTabbedPane().setVisible(true);
					clienteController.getUltimoPanelMostrado().setVisible(true);
				}
			});
		}

		return btnClientes;
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane()
	{
		if(jContentPane == null)
		{
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBackground(Color.GRAY);
			jContentPane.add(getPnlToolBarArrow(), BorderLayout.WEST);	
			jContentPane.add(getJplPrincipalPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private DetailPanel getJplPrincipalPanel() {
		if(pnlPrincipal == null)
		{
			pnlPrincipal = new DetailPanel();
			pnlPrincipal.setLayout(new BorderLayout());
			pnlPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

			pnlPrincipal.add(getLblLogo(), BorderLayout.WEST);
			pnlPrincipal.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return pnlPrincipal;
	}

	private JLabel getLblLogo() {
		if(lblLogo == null)
		{
			lblLogo = new JLabel();
		}	
		return lblLogo;
	}

	private JPanel getPnlToolBarArrow() {
		if(pnlToolBarArrow == null)
		{
			pnlToolBarArrow = new JPanel();
			pnlToolBarArrow.setLayout(new BoxLayout(pnlToolBarArrow, BoxLayout.X_AXIS));
			pnlToolBarArrow.setBackground(Color.GRAY);
			pnlToolBarArrow.add(getPnlToolBar());
			pnlToolBarArrow.add(getPanelFlecha());
		}
		return pnlToolBarArrow;
	}

	private DetailPanel getPnlToolBar() {
		if(pnlToolBar == null)
		{
			pnlToolBar = new DetailPanel();
			pnlToolBar.setLayout(new BorderLayout());
			pnlToolBar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			pnlToolBar.add(getQuickAccessBar());
		}
		return pnlToolBar;
	}

	/**
	 * This method initializes jLabel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JLabel getLblArrow()
	{
		if(lblArrow == null)
		{
			lblArrow = new JLabel();
			lblArrow.setSize(new Dimension(20,20));
			lblArrow.setIcon(new ImageIcon(this.getClass().getResource("/img/flecha_izquierda.jpg")));

			lblArrow.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if(pnlToolBar.isVisible())
					{
						pnlToolBar.setVisible(false);
						lblArrow.setIcon(new ImageIcon(this.getClass().getResource("/img/flecha_derecha.jpg")));
						pnlArrow.setBorder(BorderFactory.createEmptyBorder(23, 3, 0, 0));
					}
					else
					{
						pnlToolBar.setVisible(true);
						lblArrow.setIcon(new ImageIcon(this.getClass().getResource("/img/flecha_izquierda.jpg")));
						pnlArrow.setBorder(BorderFactory.createEmptyBorder(23, 0, 0, 0));
					}
				}
			});	
		}

		return lblArrow;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPaneConBoton getJTabbedPane()
	{
		if(jTabbedPaneConBoton == null)
		{	
			jTabbedPaneConBoton = new JTabbedPaneConBoton();
			jTabbedPaneConBoton.setTabLayoutPolicy(JTabbedPaneConBoton.CENTER);	
			jTabbedPaneConBoton.setVisible(false);
		}

		return jTabbedPaneConBoton;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getQuickAccessBar()
	{
		if(tlbQuickAccess == null)
		{
			tlbQuickAccess = new JToolBar(null, JToolBar.VERTICAL);
			tlbQuickAccess.setLayout(new BoxLayout(getQuickAccessBar(), BoxLayout.Y_AXIS));
			tlbQuickAccess.add(getBtnVentas());
			tlbQuickAccess.add(getBtnArticulos());
			tlbQuickAccess.add(getBtnClientes());
			tlbQuickAccess.add(getBtnReporte());
			tlbQuickAccess.add(getBtnProveedores());
			tlbQuickAccess.add(getBtnMovimientos());
			
			tlbQuickAccess.setFloatable(false);
		}

		return tlbQuickAccess;
	}

	/**
	 * This method initializes panelFlecha	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelFlecha()
	{
		if(pnlArrow == null)
		{
			pnlArrow = new JPanel();
			pnlArrow.setLayout(new BoxLayout(getPanelFlecha(), BoxLayout.Y_AXIS));
			pnlArrow.add(getLblArrow(), null);
			pnlArrow.setBackground(Color.GRAY);
		}

		return pnlArrow;
	}

	public void setVentaController(VentaController ventaController) {
		this.ventaController = ventaController;
	}

	public void setProductoController(ProductoController productoController) {
		this.productoController = productoController;
	}

	public void setClienteController(ClienteController clienteController) {
		this.clienteController = clienteController;
	}
	
	public void setLoginView(LoginView loginView) {
		this.loginView = loginView;
	}
	
	private void showAdminButtons(boolean show) {
		getBtnReporte().setVisible(show);
		getBtnArticulos().setVisible(show);
		getBtnProveedores().setVisible(show);
		getBtnClientes().setVisible(show);
		getBtnMovimientos().setVisible(show);
	}
	
	@Override
	public void setVisible(boolean b) {
		if(b) {
			showAdminButtons(false);
			for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
				if(MitnickConstants.Role.ADMIN.equals(grantedAuthority.getAuthority()))
					showAdminButtons(true);
			}
		}
		super.setVisible(b);
	}
	private JMenu getMenuImpresora() {
		if (menuImpresora == null) {
			menuImpresora = new JMenu("Impresora");
			menuImpresora.add(getMenuItemCierreZ());
			menuImpresora.add(getMenuInformeJornada());
			menuImpresora.add(getMenuConfiguracion());
		}
		return menuImpresora;
	}
	private JMenuItem getMenuItemCierreZ() {
		if (menuItemCierreZ == null) {
			menuItemCierreZ = new JMenuItem("Cierre Z");
			menuItemCierreZ.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(printerService.imprimirCierreZ())
						throw new PresentationException("error.printer.cierreZ");
				}
			});
		}
		return menuItemCierreZ;
	}
	private JMenuItem getMenuInformeJornada() {
		if (menuInformeJornada == null) {
			menuInformeJornada = new JMenuItem("Informe de Jornada");
			menuInformeJornada.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if(!printerService.imprimirInformeJornada())
							throw new PresentationException("error.printer.informeJornada");
					}
					catch(PrinterException ex) {
						mostrarMensaje(ex);
					}
				}
			});
		}
		return menuInformeJornada;
	}
	private JMenuItem getMenuConfiguracion() {
		if (menuConfiguracion == null) {
			menuConfiguracion = new JMenuItem("Configuración");
			menuConfiguracion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ConfiguracionImpresoraDialog(thisView);
				}
			});
		}
		return menuConfiguracion;
	}
	
	protected int mostrarMensaje(PrinterException ex) {
		switch(ex.getType()) {
		case BaseException.WARNING:
			return mostrarMensajeAdvertencia(ex.getMessage());
		case BaseException.ERROR:
		default:
			return mostrarMensajeError(ex.getMessage());
		}
	}
	
	protected int mostrarMensaje(PresentationException ex) {
		switch(ex.getType()) {
		case BaseException.WARNING:
			return mostrarMensajeAdvertencia(ex.getMessage());
		case BaseException.ERROR:
		default:
			return mostrarMensajeError(ex.getMessage());
		}
	}
	
	protected int mostrarMensajeError ( String message ) {
		Object[] options = { PropertiesManager.getProperty( "dialog.error.okbutton" ) };
		
		return JOptionPane.showOptionDialog( this, message, PropertiesManager.getProperty( "dialog.error.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeAdvertencia ( String message ) {
		//Primero despliego un mensaje para confirmar la operacion
	     Object[] options = { PropertiesManager.getProperty( "dialog.warning.okbutton" ) };
	     
	     return JOptionPane.showOptionDialog( this, message, PropertiesManager.getProperty("dialog.warning.titulo"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeConsulta( String message ) {
		//Primero despliego un mensaje para confirmar la operaci�n
	     Object[] options = { PropertiesManager.getProperty( "dialog.info.okbutton" ), PropertiesManager.getProperty( "dialog.info.cancelbutton" ) };
	     
	     return JOptionPane.showOptionDialog(this, message, PropertiesManager.getProperty( "dialog.info.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeInformativo ( String message ) {
		//Primero despliego un mensaje para confirmar la operaci�n
	     Object[] options = { PropertiesManager.getProperty( "dialog.info.okbutton" ) };
	     
	     return JOptionPane.showOptionDialog(this, message, PropertiesManager.getProperty( "dialog.info.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[ 0 ] );
	}
}