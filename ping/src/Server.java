import java.lang.*;
import java.net.*;

import common.*;

@SuppressWarnings("unused")
class Server {
	public int clientnum[];
	public int clientping[];

	public static void main(String args[]) {
		final int port = 1987;
		(new Server()).process(port);
	}

	public void process(final int port) {
		while (true) {
			try {
				
				@SuppressWarnings("resource")
				ServerSocket ss = new ServerSocket(port);// Server Socket

				Socket socket = ss.accept(); // Wait for connection
				long start = System.nanoTime();
				NetStringReader in = new NetTCPReader(socket); // Input
				NetStringWriter out = new NetTCPWriter(socket); // Output

				long stop = System.nanoTime();
				double tt = (double) (stop - start) / 2000000; // ms
				System.out.printf("Ping %12.9f seconds\n", tt);

				while (true) // Loop
				{
					String message = in.get(); // From Client
					if (message == null)
						break; // No more data
					System.out.println(message); // Print message
					out.put("" + tt); // Return ping
				}

				in.close(); // Close Read
				out.close(); // Close Write

				socket.close(); // Close Socket
				
			} catch (Exception err) {
				DEBUG.error("Error: " + err.getMessage());
			}
		}

	}
}
