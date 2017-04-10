package server;
import java.io.*;
import java.net.*;
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
			System.out.println(new String(receivePacket.getData()) + ":: data receive packet");
			System.out.println(receivePacket.getPort());
			String cliente= receivePacket.getAddress() + "," + receivePacket.getPort();
			if(clientes.size()!=0){
				for(int i=0;i<clientes.size()&& nuevo;i++){
					System.out.println(clientes.get(i));
					System.out.println(cliente);
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

			int sizeFecha = in.readInt();
			byte[] fecha1 = new byte[sizeFecha];
			in.read(fecha1, 0, sizeFecha);

			byte lastModifiedDate = in.readByte();

			// read done

			System.out.println(new String(seq1));
			System.out.println(new String(fecha1));
			System.out.println(lastModifiedDate);
			/*String linea = new String(receiveData);
			String[] s=linea.split("=");
			String[] s1 = s[1].split("t");
			String seq = s1[0];
			String fecha = s[2];
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			Date fechaSalida = df.parse(fecha);
			Date actual = new Date();
			long dif = getDateDiff(fechaSalida,actual,TimeUnit.MILLISECONDS);
			String content = seq + ":" + dif + "ms";
			new ServerThreadU(receivePacket.getAddress(), receivePacket.getPort(),nuevo,content).escribir();*/
			/*if(clientes.size()!=0){
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
					int promedio= tiempoPromedio/(numRecibidos+numPerdidos);
					System.out.println("RESUMEN: Para el cliente " + clientes.get(i) );
					System.out.println("Se recibieron " + numRecibidos + " objetos" );
					System.out.println("Se perdieron " + numPerdidos + " objetos" );
					System.out.println("El tiempo promedio es " + promedio + "ms");
				}	
			}*/
		}
	}

}