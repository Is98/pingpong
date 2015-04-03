package server;

import java.io.EOFException;
import java.net.*;

import static common.Global.*;
import common.*;

/**
 * Individual player run as a separate thread to allow
 * updates to the model when a player moves there bat
 */
class S_Player extends Thread
{
	private Socket          theSocket;         // Socket used
	private NetStringReader theIn;             // Input object stream
	private NetStringWriter theOut;            // Output object stream
	private int             playerNum;
	private S_PongModel     model;

	/**
	 * Constructor
	 * @param player PlayerS 0 or 1
	 * @param model Model of the game
	 * @param in Reader
	 */
	public S_Player( int player, S_PongModel model, NetStringReader in )
	{
		this.model       = model;
		this.playerNum   = player;
		this.theIn       = in;
		DEBUG.trace( "PlayerS.constuctor %d %s %s", playerNum, model, in );
	}


	public NetStringWriter getWriter()
	{
		return theOut;
	}

	public NetStringReader getReader()
	{
		return theIn;
	}

	/**
	 * Get and update the model with the latest bat movement
	 */
	public void run()                            // Execution
	{
		try
		{
			while ( true )                           // Loop
			{

				/*theOut.put("ping");
				long start = System.nanoTime();
				String Ping = theIn.get();
				if (Ping != null && Ping.equals("ping")){
					long end = System.nanoTime();
					double ping = (double)(end-start)/1_000;
					System.out.println(ping);
				}*/
				String move = theIn.get();				// From Client
				if ( move == null ) break;             // No more data
				DEBUG.trace( "S Move %s", move );

				//GameObject bat = model.getBat(playerNum);
				// ERASED code to process bat movement
				// know player 
				if(move.equals("update received")){
					model.getActiveModel().setNEnd(System.nanoTime());
				}
				else if ( move.equals( "up") )				
				{
					model.moveBat(playerNum, -10);  //was -3
				} else {
					model.moveBat(playerNum, 10);
				}
				
				/*GameObject[] bats = model.getBats();
				GameObject ball = model.getBall();
					
				String mes = String.format( "%6.2f %6.2f %6.2f %6.2f %6.2f %6.2f",
						bats[0].getX(), bats[0].getY(),
						bats[1].getX(), bats[1].getY(),
						ball.getX(), ball.getY() );
				theOut.put(mes);
				long start = System.nanoTime();*/

			}
			//----------------------
			while ( true )                           // Loop
			{
				String move = theIn.get();				// From Client
				if ( move == null ) break;             // No more data
				DEBUG.trace( "S Move %s", move );

				//GameObject bat = model.getBat(playerNum);
				// ERASED code to process bat movement
				// know player 
				if ( move.equals( "right") )                             // the right key going down
				{
					model.moveBat(playerNum, -5);  //was -3
				} else {
					model.moveBat(playerNum, 5);
				}



			}
			//---------------    



			theIn.close();                            // Close Read
			theOut.close();                           // Close Write

			theSocket.close();                        // Close Socket
		}
		catch (Exception err)
		{
			DEBUG.error( "PlayerS.run [%d]\n%s",
					playerNum, err.getMessage() );
		}
	}
}
