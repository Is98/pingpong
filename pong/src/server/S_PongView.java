package server;

import common.*;

import java.util.Observable;
import java.util.Observer;

/**
 * Displays a graphical view of the game of pong
 */
class S_PongView implements Observer {
	//private S_PongController pongController;
	private GameObject ball;
	private GameObject[] bats;
	private NetStringWriter left = null;
	private NetStringWriter right = null;
	@SuppressWarnings("unused")
	private NetStringWriter mc = null;
	@SuppressWarnings("unused")
	private int gameNumber = 0;

	
	/**
	 * Constructor
	 */
	public S_PongView() {
		Counter.inc(); // next Game identifier
		gameNumber = Counter.get(); // Remember
	}

	public void setStreams(NetStringWriter c1, NetStringWriter c2,
			NetStringWriter mc) {
		DEBUG.assertTrue(c1 != null && c2 != null, // && mc != null,
				"S_PongView.setStreams - c1 or c2 null");  // or c3 null");
		left = c1;
		right = c2;
		//this.mc = mc;
	}

	public void setStreams(NetStringWriter mc) {
		DEBUG.assertTrue(mc != null, "S_PongView.setStreams - mc null");
		this.mc = mc;
	}

	/**
	 * Called from the model when its state is changed
	 * 
	 * @param aPongModel
	 *            Model of game
	 * @param arg
	 *            Arguments - not used
	 */
	public void update(Observable aPongModel, Object arg) {
		S_PongModel model = (S_PongModel) aPongModel;
		ball = model.getBall();
		bats = model.getBats();

		DEBUG.assertTrue(ball != null, "S_PongView.update ball null");
		DEBUG.assertTrue(bats != null, "S_PongView.update bats array null");
		DEBUG.assertTrue(bats[0] != null && bats[1] != null,
				"S_PongView.update bats[0/1] null");

		// ERASED code to send position of game objects to the client
		// as the model on the server has changed
		String output = 
				String.format( "%6.2f %6.2f %6.2f %6.2f %6.2f %6.2f %6.2f",
						bats[0].getX(),	//pos0
						bats[0].getY(), //pos1
						bats[1].getX(), //pos2
						bats[1].getY(), //pos3
						ball.getX(), 	//pos4
						ball.getY(), 	//pos5
						model.getActiveModel().getPing() //pos6.
				);
		
		left.put( output );
		right.put( output );
		model.getActiveModel().setPingStart(System.nanoTime());

	}

	
}
