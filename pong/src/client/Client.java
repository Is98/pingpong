package client;

import common.*;
import static common.Global.*;

import java.net.Socket;

/**
 * Start the client that will display the game for a player
 */
class Client
{
  private static String role = "TCP";
  private static String gameToView = "0";

  public static void main( String args[] )
  {
    if ( args.length >= 1 )
      role = args[0];
    if ( args.length >= 2 )
      gameToView = args[1];
    ( new Client() ).start();
  }

  /**
   * Start the Client
   */
  public void start()
  {
    DEBUG.set( true );
    DEBUG.trace( "Pong Client" );
    DEBUG.trace( "Role [%s] gameToView [%s]", role, gameToView );
    DEBUG.set( DEBUG_STATE_CLIENT );
    C_PongModel       model = new C_PongModel();
    C_PongView        view  = new C_PongView( role + " " +  gameToView );
    C_PongController  cont  = new C_PongController( model, view );

    makeContactWithServer( model, cont );

    model.addObserver( view );       // Add observer to the model
    view.setVisible(true);           // Display Screen
  }

  /**
   * Make contact with the Server who controls the game
   * Players will need to know about the model
   *
   * @param model Of the game
   * @param cont Controller (MVC) of the Game
   */
  public void makeContactWithServer( C_PongModel model,
                                     C_PongController  cont )
  {
    // Also starts the Player task that get the current state
    //  of the game from the server
    C_Player player = null;
    // Socket used to connect to server
    //Socket s;
    try
    {
      switch ( role )
      {
        case "TCP" :
          DEBUG.trace( "Client.makeContactWithServer TCP" );
          // ERASED code to set up TCP connection and instance of C_Player
          
          Socket socket = new Socket( TCP_SERVER_ADDR, TCP_PORT );// Socket  

          player = new C_Player( model, socket ) ;
          
          
          cont.setWriter( player.getWriter() );
          
          DEBUG.assertTrue( player!=null,
                        "Client.makeContactWithPlayer " +
                        "No Player object created" );
          player.start();
          break;
          // ERASED code to process Observer and MC connection
          //             not required for TCP connection
      }
    }
    catch ( Exception err )
    {
      DEBUG.error("Client.makeContactWithServer\n%s",
                  err.getMessage() );
    }
  }
}
