import java.awt.*;

import javax.swing.JFrame;
import javax.swing.Timer;

public class c_run {

	public static void main(String args[]) throws InterruptedException {

		// initialize our drawing component
		c_draw game_panel = new c_draw();

		// initialize our JFrame
		JFrame frame = new JFrame("JavaSnakeGame");
		{
			//offset of 5 because JFrames are stupid
			frame.setSize(505, 550);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setBackground(Color.black);
			
			//game doesn't scale to window size so don't let them change it
			frame.setResizable(false);
			frame.setVisible(true);
			
			//add our key listener for the movement of the snake
			frame.addKeyListener(game_panel);
			
			//add our panel that we are going to draw on
			frame.add(game_panel);
			
		}

		while (true) {
			//cap the paint function to run at 8fps
			Thread.sleep(100);
			frame.repaint();
		}
	}

}
