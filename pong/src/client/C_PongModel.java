package client;

import java.util.Observable;
import common.GameObject;
import static common.Global.*;

/**
 * Model of the game of pong (Client)
 */
public class C_PongModel extends Observable
{
  private GameObject ball   = new GameObject( W/2, H/2, BALL_SIZE, BALL_SIZE );
  private GameObject bats[] = new GameObject[2];
  private String     gameName = "+";
  private long	 myPing;	
  private long 	 serverPing;


  public C_PongModel()
  {
    bats[0] = new GameObject(  60, H/2, BAT_WIDTH, BAT_HEIGHT);
    bats[1] = new GameObject(W-60, H/2, BAT_WIDTH, BAT_HEIGHT);
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
   * Return the Game object representing the Bats for player
   * @return Array of two bats
   */
  public GameObject[] getBats()
  {
    return bats;
  }

  /**
   * Set the Bats used
   * @param theBats - Players Bat
   */
  public void setBats( GameObject[] theBats )
  {
    bats = theBats;
  }

  /**
   * Set the game
   * @param game The game name
   */

  public void setGameName( String game )
  {
    gameName = game;
  }

  /**
   * Return the game name
   * @return The game name
   */

  public String getGameName()
  {
    return gameName;
  }

  /**
   * Cause update of view of game
   */
  public void modelChanged()
  {
    setChanged(); notifyObservers();
  }
  
  public void setMyPing(long ping)
  {
	myPing = ping;
  }
  
  public long getMyPing()
  {
	return myPing;
  }

  public long getServerPing() 
  {
	return serverPing;
  }
  
  public void setServerPing(long ping)
  {
	  serverPing = ping;
  }
  
  
}
