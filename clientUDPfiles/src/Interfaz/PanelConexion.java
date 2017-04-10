package Interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PanelConexion extends JPanel {
	
	private VentanaPrincipal principal;
	private JLabel solicitar;
	private JTextField conexion;
	private JLabel estado;
	private JTextField estadoC;

	
	public PanelConexion(VentanaPrincipal ventana){
		
		principal = ventana;
	    setLayout(new GridLayout(2,2));

        setBorder( BorderFactory.createTitledBorder( "Parámetros" ) );

        solicitar = new JLabel( "IP del servidor" );
        add( solicitar );
        
        conexion = new JTextField( );
        add( conexion );
		
        estado = new JLabel("Puerto en el que escucha");
        add(estado);
        
        estadoC = new JTextField();
        add(estadoC);
        
	}
	
	public String darIp(){
		return conexion.getText();
	}
	
	public int darPuerto(){
		String puerto= estadoC.getText();
		return Integer.parseInt(puerto);
	}
	


}
