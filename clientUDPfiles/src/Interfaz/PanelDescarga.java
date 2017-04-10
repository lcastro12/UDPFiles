package Interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class PanelDescarga extends JPanel implements ActionListener {
	
	
	private VentanaPrincipal principal;
	private JLabel solicitar;
	private JTextField conexion;
	private JButton aceptar;
	private static String ENVIAR = "ENVIAR";
	public int numero;
	 
	    public PanelDescarga( VentanaPrincipal ventana )
	    {
	    	principal = ventana;
		    setLayout(new GridLayout(1,3));

	        setBorder( BorderFactory.createTitledBorder( "Archivo a enviar" ) );

	        solicitar = new JLabel( "Ruta del archivo a enviar" );
	        add( solicitar );
	        
	        conexion = new JTextField( );
	        add( conexion );
			
	        aceptar = new JButton();
	        aceptar.setText("Enviar");
	        aceptar.setActionCommand(ENVIAR);
	        aceptar.addActionListener(this);
	        add(aceptar);
	    }
	    
	    public String darFile(){
	    	String num = conexion.getText();
	    	return num;
	    }


	    public void actionPerformed( ActionEvent evento )
	    {
	        String comando = evento.getActionCommand( );
	        String num = conexion.getText();
	        if(num == null){
	        	
	        }
	        if( comando.equals( ENVIAR ) )
	        {
	            principal.inciarEnvio();
	        }
	      
	    }
}
