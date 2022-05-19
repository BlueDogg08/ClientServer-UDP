import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class ServerUDP{

	public static void main(String[] args) {
		int port=2000;
		DatagramSocket dSocket;
		DatagramPacket inPacket;
		DatagramPacket outPacket;
		byte[] buffer;
		String messageIn, messageOut;
		Date d;
		
		try {
			dSocket = new DatagramSocket(port);
			System.out.println("Apertura porta in corso!");                 
			while(true){
				System.out.println("Server in ascolto sulla porta " + port + "!\n");
				buffer = new byte[256];
				inPacket = new DatagramPacket(buffer, buffer.length);
				dSocket.receive(inPacket);
				
				//si recupera l'indirizzo IP e la porta UDP del client
				InetAddress clientAddress = inPacket.getAddress();
				int clientPort = inPacket.getPort();
				
				//si stampa a video il messaggio ricevuto dal client
				messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
				System.out.println("SONO IL CLIENT " + clientAddress + 
						":" + clientPort + "> " + messageIn);
				
				//si crea un oggetto Date con la data corrente
				d = new Date();
				//si crea il messaggio del server in uscita associandolo alla connessione aperta con il client
				messageOut = d.toString();
				
				//si crea un datagramma UDP in cui trasportare il messaggio di lunghezza length
				outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress, clientPort);
				//si invia il messaggio al client
				dSocket.send(outPacket);
				System.out.println("Risposta inviata!");
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}