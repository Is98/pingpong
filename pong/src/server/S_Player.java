package server;

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
    this.model     = model;
    playerNum      = player;
    theIn          = in;
    DEBUG.trace( "PlayerS.constuctor %d", playerNum );
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
        String move = theIn.get();             // From Client
        if ( move == null ) break;             // No more data
        DEBUG.trace( "S Move %s", move );

        //GameObject bat = model.getBat(playerNum);
        // ERASED code to process bat movement
        if (move.equals("Change made")){
			model.getActiveModel().setPingFinish(System.nanoTime());
        } else if ( move.equals( "up") ) {
        	model.moveBat(playerNum, (int) -BAT_MOVE);
		} else {
			model.moveBat(playerNum, (int) BAT_MOVE);
		}
      }

      theIn.close();                            // Close Read
      theOut.close();                           // Close Write

      theSocket.close();                        // Close Socket
    }
    catch ( Exception err )
    {
      DEBUG.error( "PlayerS.run [%d]\n%s",
                   playerNum, err.getMessage() );
    }
  }
}
