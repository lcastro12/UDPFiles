package mundo;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Interfaz.VentanaPrincipal;

public class UDPClient {
	
	private static int PORT;
	private static String IP;
	private static String file;
	private VentanaPrincipal app;
	
	public UDPClient(VentanaPrincipal app1, int PORT1, String IP1, String file){
		this.app = app1;
		this.file=file;
		IP=IP1;
		PORT=PORT1;
		System.out.println(this.PORT + " " + this.IP+ " " + this.file);
		try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sendFile(String file, InetAddress IPAddress, DatagramSocket clientSocket) throws IOException{
		File archivo = new File(file);
		FileInputStream fi = new FileInputStream(archivo);
		int tamañoArchivo=(int)archivo.length();
		byte fileContent[] = new byte[tamañoArchivo];
		fi.read(fileContent);
		int sendBufferSize=5000;
		boolean termino=false;
			byte[] sendData = new byte[sendBufferSize];
			for(int i=0;i<fileContent.length && !termino;i++){
				
				    String schemaName = i+":";
				    Date lastModifiedDate = new Date();
				    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					String reportDate = df.format(lastModifiedDate);
				

				    byte[] schemaNameBytes = schemaName.getBytes();
				    byte[] reportDateBytes = reportDate.getBytes();
				    
				    int cant = (fileContent.length + schemaNameBytes.length + reportDateBytes.length)/sendBufferSize;
				    if(cant <1){
				    	sendData = fileContent;
				    	termino=true;
				    }
				    else{
				    	for (int j=0;j<fileContent.length;j++){
				    		sendData[j] = fileContent[j];
				    		fileContent[j] = fileContent[j+sendBufferSize];
				    	}
				    
				    }

				    ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
				    DataOutputStream out = new DataOutputStream(byteOs);
				    out.writeInt(schemaNameBytes.length);
				    out.write(schemaNameBytes);
				    System.out.println(new String(schemaName));
				    out.writeLong(reportDateBytes.length);
				    System.out.println(new String(reportDate));
				    out.write(reportDateBytes);
				    out.writeInt(sendData.length);
				    System.out.println(new String(sendData));
				    out.write(sendData);
				    byte[] allWrittenBytes = byteOs.toByteArray();
				  
				   
				    System.out.println("ESCRIBIO");
				    DatagramPacket dp = new DatagramPacket(allWrittenBytes, allWrittenBytes.length, IPAddress, PORT);
				    clientSocket.send(dp);
				    System.out.println("TERMINO");
			}
			
			
		
	}
	public static void setObjects(String i){
		file=i;
	}
	public static void init() throws Exception
	{
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName(IP);
		byte[] receiveData = new byte[1024];
		sendFile(file, IPAddress, clientSocket);
		
		/*DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		//response debe contener: nuÌ�mero de objetos recibidos, numero de objetos faltantes, tiempo promedio de envioÌ� de objetos
		String response = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + response);
		*/
			
		
		clientSocket.close();
	}
}