package server;

import common.*;
import static common.Global.*;

import java.util.Observable;

/**
 * Model of the game of pong
 *  The active object ActiveModel does the work of moving the ball
 */
public class S_PongModel extends Observable
{
  private GameObject ball   = new GameObject( W/2, H/2, BALL_SIZE, BALL_SIZE ); //ball size
  private GameObject bats[] = new GameObject[2];
  private S_ActiveModel aModel;

  private Thread activeModel;

  public S_PongModel()
  {
    bats[0] = new GameObject(  60, H/2, BAT_WIDTH, BAT_HEIGHT);  // bats position
    bats[1] = new GameObject(W-60, H/2, BAT_WIDTH, BAT_HEIGHT);
    aModel =  new S_ActiveModel( this );
    activeModel = new Thread(aModel);
  }

  /**
   * Start the thread that moves the ball and detects collisions
   */
  public void makeActiveObject()
  {
    activeModel.start();
  }
  
  public S_ActiveModel getActiveModel(){
	 return aModel;
  }

  /**
   * Return the Game object representing the ball
   * @return the ball
   */
  public GameObject getBall()
  {
    return ball;
  }

  /**
   * Set a new Ball object
   * @param aBall - Ball to be set
   */
  public void setBall( GameObject aBall )
  {
    ball = aBall;
  }

  /**
   * Return the Game object representing the Bat for player
   * @param player 0 or 1
   * @return a gane object
   */
  public GameObject getBat(int player )
  {
    return bats[player];
  }

  /**
   * Return the Game object representing the Bats
   * @return Array of two bats
   */
  public GameObject[] getBats()
  {
    return bats;
  }

  /**
   * Set the Bat for a player
   * @param player  0 or 1
   * @param theBat  Players Bat
   */
  public void setBat( int player, GameObject theBat )
  {
    bats[player] = theBat;
  }
  
  public void moveBat( int player, int distance )
  {
	 bats[player].moveY( distance );
  }

  /**
   * Cause update of view of game
   */
  public void modelChanged()
  {
    DEBUG.trace( "S_PongModel.modelChanged");
    setChanged(); notifyObservers();
  }

}
