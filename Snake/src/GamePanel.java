import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.*;
import javax.swing.*;
//import java.awt.event.*;


public class GamePanel extends JPanel implements ActionListener{
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	int DELAY = 70;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 5;
	int eggsEaten;
	int eggX;
	int eggY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
		GamePanel(){
			random = new Random();
			this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
			this.setBackground(Color.black);
			this.setFocusable(true);
			this.addKeyListener(new MyKeyAdapter());
			startGame();
		}
		
		public void startGame() {																																								
			newEgg();
			running = true;
			timer = new Timer(DELAY,this);
			timer.start();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			draw(g);
		}
		
		public void draw(Graphics g) {
			if(running) {
			    for(int i = 0; i < SCREEN_WIDTH/UNIT_SIZE; i++) {
				    g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				    g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			    }
			    g.setColor(Color.RED);
			    g.fillOval(eggX, eggY, UNIT_SIZE, UNIT_SIZE);
			
			    for(int i = 0; i < bodyParts; i++) {
			    	if(i == 0) {
			    		g.setColor(Color.green);
			    		g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			    	}
			    	else {
			    		g.setColor(new Color(45,180,0));
			    		g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			    	}
			    }
			    g.setColor(Color.red);
				g.setFont(new Font("Monaco",Font.BOLD, 20));
				FontMetrics metrices = getFontMetrics(g.getFont());
				g.drawString("Speed(ms): " + DELAY,(SCREEN_WIDTH - metrices.stringWidth("Speed(ms): "+DELAY))/1,g.getFont().getSize());
				g.drawString("Score : "+eggsEaten, (SCREEN_WIDTH - metrices.stringWidth("Score : "+eggsEaten))/10,g.getFont().getSize());

			}
			else {
				gameOver(g);
			}
		}
		
		public void newEgg() {
			eggX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
			eggY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		}

		public void move() {
			for(int i = bodyParts; i > 0; i--) {
				x[i] = x[i-1];
				y[i] = y[i-1];
			}
			
			switch(direction) {
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
			}
		}
		
		public void checkEgg() {
			if((x[0] == eggX) && (y[0] == eggY)) {
				bodyParts++;
				eggsEaten++;
				if(eggsEaten > 0 && eggsEaten % 5 == 0)
				{
					DELAY += 10;
					timer.setDelay(DELAY);
				}
				newEgg();
			}
		}
		
		public void checkCollisions() {
			// if snake head collides with tail
			for(int i = bodyParts; i > 0; i--) {
				if((x[0] == x[i]) && (y[0] == y[i])) {
					running = false;
				}
			}
			// if snake head touches the left border
			if(x[0] < 0) {
				running = false;
			}
			// if head touches the right border
			if(x[0] > SCREEN_WIDTH) {
				running = false;
			}
			// if head touches top border
			if(y[0] < 0) {
				running = false;
			}
			// if head touches the bottom
			if(y[0] > SCREEN_HEIGHT) {
				running = false;
			}
			if(!running) {
				timer.stop();  
			}
		}
		
		public void gameOver(Graphics g) {
			
			//Score
			g.setColor(Color.red);
			g.setFont(new Font("Monaco",Font.BOLD, 70));
			FontMetrics metrices1 = getFontMetrics(g.getFont());
			g.drawString("Score : "+eggsEaten, (SCREEN_WIDTH - metrices1.stringWidth("Score : "+eggsEaten))/2,g.getFont().getSize());

			// Game Over text
			g.setColor(Color.red);
			g.setFont(new Font("Monaco",Font.BOLD, 70));
			FontMetrics metrices2 = getFontMetrics(g.getFont());
			g.drawString("GAME OVER", (SCREEN_WIDTH - metrices2.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);
			
			//RESTART
//			g.setColor(Color.red);
//			g.setFont(new Font("Monaco",Font.BOLD, 25));
//			FontMetrics metrices3 = getFontMetrics(g.getFont());
//			g.drawString("TO RESTART PRESS 'S'", (SCREEN_WIDTH - metrices3.stringWidth("TO RESTART PRESS 'S'"))/2,(int)(SCREEN_HEIGHT/1.5));
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(running) {
				move();
				checkEgg();
				checkCollisions();
			}
			repaint();
		}
		
		public class MyKeyAdapter extends KeyAdapter{
						
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if(direction != 'R') {
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(direction != 'L') {
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP:
					if(direction != 'D') {
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if(direction != 'U') {
						direction = 'D';
					}
					break;
				}
			}
		}
		
		
}
