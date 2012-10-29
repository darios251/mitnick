package com.mitnick.presentacion.vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

import com.mitnick.utils.LoginUtils;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.anotaciones.View;

@View("loginView")
public class LoginView extends JDialog {

	private static final long serialVersionUID = 1L;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JButton btnNewButton;
	
	private LoginView thisView;
	
	@Autowired
	private PrincipalView principalView;
	
	@Autowired
	private LoginUtils loginUtils;
	
	public LoginView() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		thisView = this;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(PropertiesManager.getProperty("loginView.dialog.title"));
		setSize(264, 175);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel(PropertiesManager.getProperty("loginView.label.username"));
		lblNewLabel.setBounds(36, 28, 85, 14);
		getContentPane().add(lblNewLabel);
		
		txtUser = new JTextField();
		txtUser.setBounds(131, 25, 86, 20);
		getContentPane().add(txtUser);
		txtUser.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel(PropertiesManager.getProperty("loginView.label.password"));
		lblNewLabel_1.setBounds(36, 63, 85, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(131, 60, 86, 20);
		getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if((int)e.getKeyChar() == KeyEvent.VK_ENTER)
					doLogin();
			}
		});
		
		setLocationRelativeTo(null);
		
		btnNewButton = new JButton(PropertiesManager.getProperty("loginView.button.login"));
		btnNewButton.setBounds(131, 91, 85, 23);
		btnNewButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		});
		getContentPane().add(btnNewButton);
		
		InicioView.getInstance().dispose();
		setVisible(true);
	}

	@SuppressWarnings("deprecation")
	protected void doLogin() {
		try {
			// Este while loco hay que dejarlo, porque a veces spring tarda en inyectar la dependencia
			while(loginUtils == null) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
			if(loginUtils.authenticate(txtUser.getText(), txtPassword.getText()) != null) {
				logger.info("Iniciando la pantalla principal...");
				thisView.setVisible(false);
				principalView.setVisible(true);
				thisView.dispose();
			}
			else {
				JOptionPane.showConfirmDialog((java.awt.Component) null, PropertiesManager.getProperty("error.loginView.auth"), "Error", JOptionPane.DEFAULT_OPTION);
			}
		}
		catch(BadCredentialsException e) {
			JOptionPane.showConfirmDialog((java.awt.Component) null, PropertiesManager.getProperty("error.loginView.auth"), "Error", JOptionPane.DEFAULT_OPTION);
		}
		txtUser.setText("");
		txtPassword.setText("");
		txtUser.requestFocus();
	}
}
