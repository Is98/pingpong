package client;

import common.*;

import java.net.Socket;

/**
 * Individual player run as a separate thread to allow updates immediately the
 * bat is moved
 */
class C_Player extends Thread {
	private Socket theSocket; // Socket used
	private NetStringReader theIn = null; // Input stream
	private NetStringWriter theOut = null; // Output stream
	private C_PongModel model;
	private int gameToView = 0; // 1 or more

	/**
	 * Constructor
	 * 
	 * @param model
	 *            - model of the game
	 * @param s
	 *            - Socket used to communicate with server
	 */
	public C_Player(C_PongModel model, Socket s) {
		// The player needs to know this to be able to work
		this.model = model;
		theSocket = s; // Remember socket
		// ERASED code to open an input and an output stream
		// Attempt to set input and outputs to socket
		try {
			theOut = new NetTCPWriter( s );        // Output  
			theIn  = new NetTCPReader( s );        // Input
		} catch ( Exception e ) {
			DEBUG.error( "C_Player : \n %s", e.getMessage() );
		}
	}

	/**
	 * Constructor for multicast versions
	 * 
	 * @param model
	 *            - model of the game
	 * @param reader
	 *            - Multicast reader
	 * @param gameToView
	 *            - Game to view
	 */
	public C_Player(C_PongModel model, NetMCReader reader, String gameToView) {
		// ERASED code for constructor to save values for multicast read
	}

	/**
	 * Constructor for multicast versions
	 * 
	 * @param model
	 *            - model of the game
	 * @param s
	 *            - Socket used
	 * @param reader
	 *            - Multicast reader
	 * @param gameToView
	 *            - Game to view
	 */
	public C_Player(C_PongModel model, Socket s, NetMCReader reader,
			String gameToView) {
		// ERASED code for using multicast reader TCP writer
	}

	public NetStringWriter getWriter() {
		return theOut;
	}

	public NetStringReader getReader() {
		return theIn;
	}

	/**
	 * Get and update the model with the latest bat movement sent by the server
	 */
	public void run() // Execution
	{
		// Listen to network to get the latest state of the
		// game from the server
		// Update model with this information, Redisplay model
		
		//Variables for bat and ball
		GameObject[] bats = model.getBats();
		GameObject ball = model.getBall();
		
		DEBUG.trace("PlayerC.run");
		try {
			while (true) // Loop
			{
				String mes = theIn.get(); // From Server
				theOut.put("Change made");// To server
				if (mes == null)
					break; // No more data
				DEBUG.trace("PlayerC.run Received [%s]", mes);
				// ERASED code to process new game positions
				// Trim extra spaces from received message and populate array
				String numbers[] = mes.trim().split( "  *" ); 
				// convert strings to doubles
				double received[] = new double[ numbers.length ];
				try {
				  for ( int i=0; i<numbers.length; i ++ )
				  {
				  	//Convert to double and add to received values
					received[i] = Double.parseDouble( numbers[i] );
				  }
				  //move bats to new position
				  bats[0].setX( received[0] );
				  bats[0].setY( received[1] );
				  bats[1].setX( received[2] );
				  bats[1].setY( received[3] );
				  // move ball to new position
				  ball.setX( received[4] ); 
				  ball.setY( received[5] );
				  // Send ping time
				  model.setPing(received[6]);
				  
				} catch ( Exception e ) {
					DEBUG.error( "PlayerC.run - Game Position \n %s", e.getMessage() );
				}
				//update the view
				model.modelChanged();
			}

			theIn.close(); // Close Read
			theOut.close(); // Close Write

			theSocket.close(); // Close Socket
		} catch (Exception err) {
			DEBUG.error("PlayerC.run \n %s", err.getMessage());
		}

	}
}
