package client;

import java.io.IOException;
import java.net.*;

import util.EchoUtil;

public class EchoClient {

	public static void main(String[] args) {
		new Thread(new EchoReceiver()).start();
		
		try {
			// Defines the destinations address (IP and port)
			InetAddress server = InetAddress.getLocalHost();

			DatagramSocket socket;
			try {
				socket = new DatagramSocket();
				
				for (int i = 0; i < 50; i++) {
					String message = "Message " + i;
					
					// New packet from message data
					byte[] messageData = message.getBytes();
					DatagramPacket packet = new DatagramPacket(messageData, messageData.length, server, EchoUtil.serverPort);

					try {
						socket.send(packet);
						System.out.println("Sent: " + new String(packet.getData(), 0, messageData.length));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				socket.close();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
