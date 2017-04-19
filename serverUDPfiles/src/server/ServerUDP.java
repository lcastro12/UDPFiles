package server;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
class ServerUDP {

	static DatagramSocket serverSocket;
	static boolean nuevo;
	static ArrayList<String> clientes;

	private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	private static byte[] getKeyedDigest(byte[] buffer){
		try{
			MessageDigest md5= MessageDigest.getInstance("MD5");
			md5.update(buffer);
			return md5.digest();
		}
		catch(Exception e){
			return null;
		}
	}

	public static byte[] calcular(byte[] text){
		try{
			String s1= new String(text);
			System.out.println("dato original:" + s1 );
			byte [] digest= getKeyedDigest(text);
			String s2= new String(digest);
			System.out.println("digest:"+ s2);
			return digest;
		}
		catch(Exception e){
			System.out.println("Excepcion:"+ e.getMessage());
			return null;
		}
	}
	
	public static void main(String args[]) throws Exception
	{
		// TODO Auto-generated method stub
		//int port= Integer.parseInt(args[0]);

		serverSocket = new DatagramSocket(5000);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		System.out.println("before receive packet");
		nuevo=true;
		clientes=new ArrayList<String>();
		DatagramPacket receivePacket =
				new DatagramPacket(receiveData, receiveData.length);

		while(true)

		{

			serverSocket.receive(receivePacket);
			String cliente= receivePacket.getAddress() + "," + receivePacket.getPort();
			if(clientes.size()!=0){
				for(int i=0;i<clientes.size()&& nuevo;i++){
					if(cliente.equals(clientes.get(i))){
						nuevo=false;
					}
					else{
						nuevo=true;
					}
				}
			}

			if(nuevo){
				clientes.add(cliente);

			}
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(receiveData));
			

			int sizeSeq = in.readInt();
			byte[] seq1 = new byte[sizeSeq];
			in.read(seq1, 0, sizeSeq);
			
			int sizeEspacio1 = in.readInt();
			byte[] espacio1 = new byte[sizeEspacio1];
			in.read(espacio1, 0, sizeEspacio1);

			int sizeFecha = in.readInt();
			byte[] fecha1 = new byte[sizeFecha];
			in.read(fecha1, 0, sizeFecha);
			
			/*int sizeEspacio2 = in.readInt();
			byte[] espacio2 = new byte[sizeEspacio2];
			in.read(espacio2, 0, sizeEspacio2);*/
		
			int sizeContenido = in.readInt();
			byte[] contenido = new byte[sizeContenido];
			in.read(contenido, 0, sizeContenido);
			

			

			// read done

			String seq2 = new String(seq1);
			String[] s1 = seq2.split(":");
			String seq = s1[0];
			String fecha = new String(fecha1);
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			Date fechaSalida = df.parse(fecha);
			Date actual = new Date();
			long dif = getDateDiff(fechaSalida,actual,TimeUnit.MILLISECONDS);
			String content = seq + ":" + dif + "ms";
			new ServerThreadU(receivePacket.getAddress(), receivePacket.getPort(),nuevo,content).escribir();
			if(clientes.size()!=0){
				for(int i=0;i<clientes.size();i++){
					int numRecibidos = 0;
					int numPerdidos = 0;
					int tiempoPromedio=0;
					try{


						FileReader inputFile = new FileReader("./data/"+ clientes.get(i)+".txt");

						BufferedReader bufferReader = new BufferedReader(inputFile);

						String line;
						String ultima = "";

						while ((line = bufferReader.readLine()) != null)   {
							String[] t = line.split(":");
							String[] t2=t[1].split("ms");
							String tiempo = t2[0];
							int tiempoF=Integer.parseInt(tiempo);
							tiempoPromedio+=tiempoF;
							System.out.println(line);
							numRecibidos+=1;
							ultima = line;
						}

						String[] s2 = ultima.split(":");
						int num = Integer.parseInt(s2[0]);
						numPerdidos= num-numRecibidos;

						bufferReader.close();
					}catch(Exception e){
						System.out.println("Error while reading file line by line:" + e.getMessage());                      
					}
					//int promedio= tiempoPromedio/(numRecibidos+numPerdidos);
					System.out.println("RESUMEN: Para el cliente " + clientes.get(i) );
					System.out.println("Se recibieron " + numRecibidos + " objetos" );
					System.out.println("Se perdieron " + numPerdidos + " objetos" );
					//System.out.println("El tiempo promedio es " + promedio + "ms");
				}	
			}
		}
	}

}