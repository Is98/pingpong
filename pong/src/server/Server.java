package server;

import java.net.*;

import static common.Global.*;
import common.*;

/**
 * Start the game server
 *  The call to makeActiveObject() in the model
 *   starts the play of the game
 */
class Server
{
  private NetStringWriter leftBat, rightBat;
  private ServerSocket      ss = null;
  private NetStringWriter   mc = null;
  private String            serverUsers = "TCP";

  public static void main( String args[] )
  {
    ( new Server() ).start( args );
  }

  /**
   * Start the server
   * @param args passed to the program on command line start
   */
  public void start( String args[] )
  {
    DEBUG.set( true );
    DEBUG.trace("Pong Server");
    DEBUG.set( DEBUG_STATE_SERVER );
    if ( args.length >= 1 )
    {
      serverUsers = args[0];
    }
    try
    {
      ss = new ServerSocket( TCP_PORT );
      // ERASED code to set up TCP connection
      //             and allow several simultaneous games
        S_PongModel model = new S_PongModel();

        makeContactWithClients( model, serverUsers );

        S_PongView  view  = new S_PongView();
        if ( serverUsers.equals("TCP" ) )
          view.setStreams( leftBat, rightBat, mc );
        else
          view.setStreams( mc );

                            new S_PongController( model, view );

        model.addObserver( view );       // Add observer to the model
        model.makeActiveObject();        // Start play
    } catch ( Exception e )
    {
      DEBUG.trace("Server.start\n%s", e.getMessage() );
    }
  }

  /**
   * Make contact with the clients who wish to play
   * Players will need to know about the model
   * @param model  Of the game
   * @param use type of connection TCP/ MultiCast ..
   */
  public void makeContactWithClients( S_PongModel model, String use )
  {
    DEBUG.trace( "Server.makeContactWithClients" );
    // ERASED code to make contact with both players
    // and creating instance of the active object S_Player
  }
}

