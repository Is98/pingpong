package server;

import static common.Global.*;
import common.*;

/**
 * A class used by the server model to give it an active part
 *  which continuously moves the ball and decides what to
 *  do on a collision
 */
class S_ActiveModel implements Runnable
{
  S_PongModel pongModel;
  private long start;
  private long end;
  private long nStart;
  private long nEnd;

  public S_ActiveModel( S_PongModel aPongModel )
  {
    DEBUG.assertTrue( aPongModel != null,
                      "ActiverModel constructor param null" );
    pongModel = aPongModel;
    start = 0;
    end = 0;
  }

  /**
   * Code to position the ball after time interval
   * runs as a separate thread
   */
  public void run()
  {
    final double S = 1;           // Units to move
    try
    {
      GameObject ball    = pongModel.getBall();
      GameObject bats[]  = pongModel.getBats();

      DEBUG.assertTrue( ball != null,
                       "ActimeModel.run ball null" );
      DEBUG.assertTrue( bats != null,
                        "ActimeModel.run bats array null" );
      DEBUG.assertTrue( bats[0] != null && bats[1] != null,
                        "ActimeModel.run bats[0/1] null" );

      while ( true )
      {
        double x = ball.getX(); double y = ball.getY();
        // Deal with possible edge of board hit
        if ( x >= W-B-BALL_SIZE ) ball.changeDirectionX();
        if ( x <= 0+B           ) ball.changeDirectionX();
        if ( y >= H-B-BALL_SIZE ) ball.changeDirectionY();
        if ( y <= 0+M           ) ball.changeDirectionY();

        ball.moveX( S );  ball.moveY( S );

        // As only a hit on the bat is detected it is assumed to be
        // on the front or back of the bat
        // A hit on the top or bottom has an interesting affect

        if ( bats[0].collision( ball ) == GameObject.Collision.HIT  ||
             bats[1].collision( ball ) == GameObject.Collision.HIT )
        {
          ball.changeDirectionX();
        }

        if(nStart < nEnd){
        	start = nStart;
        	end = nEnd;
        }
        
        long delay = (end - start)/2000000;
        
        pongModel.modelChanged();      // Model changed refresh screen
        
        if(delay < 20){
        	Thread.sleep( 20 - delay);            // About 50 Hz
        }
        
      }
    } catch ( Exception e )
    {
      DEBUG.trace( "ActimeModel.run %s", e.getMessage() );
    }
  }
  
  public void setNStart(long s){
	  nStart = s;
  }
  
  public void setNEnd(long e){
	  nEnd = e;
  }
  
  public double getPing(){
	  return (end - start);
  }

}

