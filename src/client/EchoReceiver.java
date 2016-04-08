package client;

import java.io.IOException;
import java.net.*;

import util.EchoUtil;

public class EchoReceiver implements Runnable {

	@Override
	public void run() {

		byte[] buffer = new byte[1000];

		DatagramSocket socket;
		
		try {
			socket = new DatagramSocket(EchoUtil.clientPort, InetAddress.getLocalHost());
			DatagramPacket echo = new DatagramPacket(buffer, buffer.length);

			while (true) {
				try {
					socket.receive(echo);
					
					String receivedString = new String(echo.getData(), 0, echo.getLength());
					System.out.println("			Echo[" + echo.getAddress() + "] = " + receivedString);

					echo.setLength(buffer.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
