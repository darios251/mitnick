package com.mitnick.presentacion.vistas;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JProgressBar;

public class InicioView extends JDialog {

	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private JDialog thisDialog;
	
	public InicioView() {
		thisDialog = this;
		setUndecorated(true);
		setSize(640, 480);
		setLocationRelativeTo(null);
		
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/img/Mitnick-StartScreen.png"));
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(imageIcon);
		getContentPane().add(lblNewLabel, BorderLayout.CENTER);
		
		JLabel lblTodosLosDerechos = new JLabel("Todos los derechos de esta aplicación estan reservados por Mitnick - (c) 2012");
		lblTodosLosDerechos.setBounds(211, 300, 400, 14);
		getContentPane().add(lblTodosLosDerechos, BorderLayout.SOUTH);
		progressBar = new JProgressBar();
		getContentPane().add(progressBar, BorderLayout.SOUTH);
		Thread progressBarThread = new ProgressBarThread();
		progressBarThread.start();
	}
	
	public static void main(String[] args) {
		InicioView view = new InicioView();
		view.setVisible(true);
	}
	
	public class ProgressBarThread extends Thread {
		
		@Override
		public void run() {
			for(int i = 0; i <= 100; i++) {
				try {
					if(i < 50 || i > 80)
						progressBar.setValue(i);
					Thread.sleep(50);
				}
				catch (Exception e) {
				}
			}
			thisDialog.dispose();
		}
	}
}
