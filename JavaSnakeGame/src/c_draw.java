import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


public class c_draw extends JPanel implements KeyListener{
	private static ArrayList<int[]> m_lSnake = new ArrayList<>();
	private static ArrayList<fruit> fruits = new ArrayList<>();
	private static int m_iScore = 0;

	public void gameOver() {
		m_lSnake.clear();
		fruits.clear();
		m_iScore = 0;
		m_mLastMove = 2;
		m_iLastKeyPressed = -1;
	}
	
	public class fruit {
		int points = 0;
		int posx = 0;
		int	posy = 0;
				
		fruit() {
			posx = (int)Math.round(Math.random() * 17.);
			posy = (int)Math.round(Math.random() * 17.);

			for ( int i = 0; i < m_lSnake.size(); ++i ) {
				int[] curSnake = m_lSnake.get(i);
				
				if ( curSnake[0] == posy && curSnake[1] == posx ) {
					posx = (int)Math.round(Math.random() * 17.);
					posy = (int)Math.round(Math.random() * 17.);
					i = 0;
				}
					
			}
			
			points = 1;
		}
	}
	
	//////////////////////////////////////////////
	//              MOVEMENT VALUES             //
	//                RIGHT == 2                //
	//                LEFT  == 1                //
	//                UP    == 1                //
	//                DOWN  == 3                //
	//////////////////////////////////////////////
	
	private static int m_iLastKeyPressed = -1;
	int m_mLastMove = 2;
	
	public void keyPressed(KeyEvent e) {
		if ( e.getKeyChar() == 'd' ) {
			m_iLastKeyPressed = 2;
		}
		else if ( e.getKeyChar() == 'w' ) {
			m_iLastKeyPressed = 1;
		}
		else if ( e.getKeyChar() == 'a' ) {
			m_iLastKeyPressed = 3;
		}
		else if ( e.getKeyChar() == 's' ) {
			m_iLastKeyPressed = 0;
		}
		
	}
	
	public void move(int moveType) {
		int[] holder = new int[2];

		if ( m_lSnake.isEmpty() ) {
			holder[0] = 2;
			holder[1] = 1;
			m_lSnake.add(holder);
			holder[0] = 1;
			holder[1] = 1;
			m_lSnake.add(holder);
			return;
		}
		
		int currentX = m_lSnake.get(m_lSnake.size()-1)[0];
		int currentY = m_lSnake.get(m_lSnake.size()-1)[1];

		if (m_mLastMove == -1)
			m_mLastMove = moveType;
		else if (moveType == 3 && m_mLastMove != 2)
			m_mLastMove = 3;
		else if (moveType == 1 && m_mLastMove != 0)
			m_mLastMove = 1;
		else if (moveType == 2 && m_mLastMove != 3)
			m_mLastMove = 2;
		else if (moveType == 0 && m_mLastMove != 1)
			m_mLastMove = 0;

		switch (m_mLastMove) {
		case 0: {
			holder[0] = currentX + 1;
			holder[1] = currentY;

			m_lSnake.add(holder);
			break;
		}
		case 1: {
			holder[0] = currentX - 1;
			holder[1] = currentY;

			m_lSnake.add(holder);
			break;
		}
		case 2: {
			holder[0] = currentX;
			holder[1] = currentY + 1;

			m_lSnake.add(holder);
			break;
		}
		case 3: {
			holder[0] = currentX;
			holder[1] = currentY - 1;

			m_lSnake.add(holder);
			break;
		}
		default: {
			System.out.println("Bad movement value!");
			break;
		}

		}
		
		m_lSnake.remove(0);
				
		for ( int i = 0; i < m_lSnake.size(); ++i ) {
			for ( int j = 0; j < m_lSnake.size(); ++j ) {
				if ( i != j ) {
					if ( m_lSnake.get(i)[0] ==  m_lSnake.get(j)[0] && m_lSnake.get(i)[1] ==  m_lSnake.get(j)[1] ) {
						gameOver();
						return;

					}
				}
			}
		}
		
		if ( m_lSnake.get(m_lSnake.size() - 1)[0] < 0 || m_lSnake.get(m_lSnake.size() - 1)[1] < 0) {
			gameOver();
			return;
		}
		else if ( m_lSnake.get(m_lSnake.size() - 1)[0] > 17 || m_lSnake.get(m_lSnake.size() - 1)[1] > 17) {
			gameOver();
			return;

		}
		
		
		if ( m_lSnake.get(m_lSnake.size() - 1)[1] == fruits.get(0).posx && m_lSnake.get(m_lSnake.size() - 1)[0] == fruits.get(0).posy ) {
			add_score(fruits.get(0).points);
			fruits.remove(0);
			holder[0] = m_lSnake.get(m_lSnake.size() - 1)[0];
			holder[1] = m_lSnake.get(m_lSnake.size() - 1)[1];
			m_lSnake.add(0, holder);
		}
	}
	
	public void add_score(int amount) {
		m_iScore += amount;
	}	
	protected void paintComponent(Graphics g) {
		for(Component c : getComponents())
	        c.paint(g);
	}
	public void paint(Graphics g) {
		// This is the border
		{
			g.setColor(Color.red);
			g.fillRect(0, 25, 25, 500);
			g.fillRect(475, 25, 25, 500);
			g.fillRect(25, 25, 450, 25);
			g.fillRect(25, 500, 450, 25);
		}
		
		if ( fruits.isEmpty() )
			fruits.add( new fruit( ) );
		
		
		move(m_iLastKeyPressed);
		
		
		g.setColor(Color.magenta);
		
		for ( fruit item : fruits )
			g.fillRect((item.posx * 25) + 25, (item.posy * 25) + 50, 25, 25);
		
		g.setColor(Color.green);
		for ( int[] item : m_lSnake ) 
			g.fillRect((item[1] * 25) + 25, (item[0] * 25) + 50, 25, 25);
		
		g.drawString("Fruit collected: " + m_iScore, 0, 15);
	
	}
	
	//garbage functions for the key listener because i don't want to make an abstract class.
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}