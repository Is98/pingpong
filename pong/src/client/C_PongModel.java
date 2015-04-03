package client;

import java.util.ArrayList;
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
  private ArrayList<Double> arrayPing;


  public C_PongModel()
  {
    bats[0] = new GameObject(  60, H/2, BAT_WIDTH, BAT_HEIGHT);
    bats[1] = new GameObject(W-60, H/2, BAT_WIDTH, BAT_HEIGHT);
    arrayPing = new ArrayList<Double>();
    arrayPing.add((double) 0);
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
  
  public void setPing(double p){
	  if(arrayPing.size() >= 10000){
		  arrayPing.remove(0);
	  }
	  arrayPing.add(p);
  }
  
  public double getPing(){
	  
	  double ping = 0;
	  for (int i = 0; i < arrayPing.size(); i++){
		  ping += arrayPing.get(i);
	  }
	  ping = ping/arrayPing.size();
	  
	  
	  return ping;
  }

  /**
   * Cause update of view of game
   */
  public void modelChanged()
  {
    setChanged(); notifyObservers();
  }

}
