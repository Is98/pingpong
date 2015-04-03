package common;

import java.io.*;
import java.net.Socket;

/**
 * Wrapper to allow reading of Strings from a socket
 */

public class NetTCPReader extends ObjectInputStream implements NetStringReader {
	/**
	 * @param s
	 *            Socket to read from
	 * @throws IOException
	 *             when can not create the connection
	 */
	public NetTCPReader(Socket s) throws IOException {
		super(s.getInputStream());
		DEBUG.traceA("new NetTCPStringReader()");
	}

	// Get object return null on 'error'
	public synchronized String get() // Get object from stream
	{
		try //
		{
			return (String) readObject(); // Return read object
		} catch (Exception e) // Reading error
		{
			DEBUG.error("NetObjectReader.get %s", e.getMessage());
			return null; // On error return null
		}
	}

	public void close() {
		try {
			super.close();
		} catch (Exception e) {
			DEBUG.error("NetTCPReader.close %s", e.getMessage());
		}
	}
}
