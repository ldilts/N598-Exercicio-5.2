package server;

import java.io.IOException;
import java.net.*;

import util.EchoUtil;

public class EchoServer {

	public static void main(String[] args) {
		DatagramSocket socket;
		byte[] buffer = new byte[1000];
		
		try {
			socket = new DatagramSocket(EchoUtil.serverPort, InetAddress.getLocalHost());
			
			InetAddress client = InetAddress.getLocalHost();

			System.out.print("Server: Online	@ ");
			System.out.println(socket.getLocalAddress() + ":" + socket.getLocalPort() + "\n");

			// New packet to receive data into the buffer
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			// Loop to receive data and print to console
			while (true) {
				try {
					socket.receive(packet);
					
					String receivedString = new String(packet.getData(), 0, packet.getLength());
					System.out.println("Message[" + packet.getAddress() + "] = " + receivedString);

					receivedString = receivedString.toUpperCase();
					
					// New packet from message data
					byte[] receivedStringData = receivedString.getBytes();
					DatagramPacket echoPacket = new DatagramPacket(receivedStringData, 
							receivedStringData.length, client, EchoUtil.clientPort);

					socket.send(echoPacket);
					System.out.println("Server: Echoed\n");

					packet.setLength(buffer.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
