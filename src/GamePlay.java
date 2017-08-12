import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePlay extends JPanel implements KeyListener, ActionListener 
{
	private ImageIcon titleImage;
	private int[] snakeXpos = new int[750];
	private int[] snakeYpos = new int[750];
	private int[] enemyXpos = new int[34];
	private int[] enemyYpos = new int[34];
	
	private int enemyX;
	private int enemyY;
	private int count = 0;
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean gameover = false;
	
	private ImageIcon rightMouth;
	private ImageIcon leftMouth;
	private ImageIcon upMouth;
	private ImageIcon downMouth;
	
	private int lengthOfSnake = 3;
	boolean started = false;
	
	private Timer timer;
	private int delay = 100;
	
	private ImageIcon snakeImage;
	private ImageIcon enemy;
	
	private Random random=new Random();;
	
	
	public GamePlay()
	{
		leftMouth = new ImageIcon("leftmouth.png");
		rightMouth = new ImageIcon("rightmouth.png");
		upMouth = new ImageIcon("upmouth.png");
		downMouth = new ImageIcon("downmouth.png");
		snakeImage = new ImageIcon("snakeimage.png");
		enemy = new ImageIcon("enemy.png");
				
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
		
		//initialize enemy coordinates array
		for(int x=25,y=75,i=0;x<875||y<650;x+=25,y+=25,i++)
		{
			enemyXpos[i] = x;
			if(y<650)
				enemyYpos[i] = y;			
		}
		
		//randomise enemy coordinates
		enemyX = enemyXpos[random.nextInt(34)];
		enemyY = enemyYpos[random.nextInt(23)];
	}
	public void paint(Graphics g)
	{
		if(!started)
		{
			System.out.println("Game restarted");
			gameover = false;
			//set default position of snake
			snakeXpos[0] = 100;
			snakeXpos[1] = 75;
			snakeXpos[2] = 50;
			
			snakeYpos[0] = 100;
			snakeYpos[1] = 100;
			snakeYpos[2] = 100;
			
			right = true;
			up = false;
			down = false;
			left = false;
			
		}
		
		//border of title image
		g.setColor(Color.WHITE);
		g.drawRect(24, 10, 852, 55);
		
		//draw title image
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		//Score and length
		g.setColor(Color.lightGray);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		g.drawString("Score  : "+ (lengthOfSnake-3), 780, 30);
		g.drawString("Length : "+ lengthOfSnake, 780, 50);
		
		//border of gameplay
		g.setColor(Color.WHITE);
		g.clearRect(24, 74, 852, 577);
		
		//background for gameplay
		g.setColor(Color.BLACK);
		g.fillRect(25,75 ,850 ,575);
		
		rightMouth.paintIcon(this, g, snakeXpos[0], snakeYpos[0]);
		
		//drawing snake
		for(int i=0;i<lengthOfSnake;i++)
		{
			if(i==0)
			{
				if(left)
				{
					leftMouth.paintIcon(this, g, snakeXpos[0], snakeYpos[0]);
				}
				else if(right)
				{
					rightMouth.paintIcon(this, g, snakeXpos[0], snakeYpos[0]);
				}
				else if(up)
				{
					upMouth.paintIcon(this, g, snakeXpos[0], snakeYpos[0]);
				}
				else if(down)
				{
					downMouth.paintIcon(this, g, snakeXpos[0], snakeYpos[0]);
				}
			}
			else
			{
				snakeImage.paintIcon(this, g, snakeXpos[i], snakeYpos[i]);
			}
		}
		
		//drawing enemy
		enemy.paintIcon(this, g, enemyX, enemyY);
		if(enemyX==snakeXpos[0]&&enemyY==snakeYpos[0])
		{
			lengthOfSnake++;
			count = 0;
			if(count<725)
			{
				valid();
			}
			else
			{
				g.setColor(Color.WHITE);
				g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
				g.drawString("You Won!", 300, 300);
				g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
				g.drawString("Press Space to RESTART", 320, 340);
			}
			
		}
		
		//Game Over
		for(int i=1;i<lengthOfSnake;i++)
		{
			if(snakeXpos[0]==snakeXpos[i]&&snakeYpos[0]==snakeYpos[i])
			{
				System.out.println("Game Over");
				gameover = true;
				lengthOfSnake = 3;
				count=0;
				started = false;
				
				right = false;
				up = false;
				down = false;
				left = false;
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
				g.drawString("Game Over!", 300, 300);
				g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
				g.drawString("Press Space to RESTART", 320, 340);
			}
		}
		
		if(started)
			g.dispose();
	}
	
	void valid()
	{
		enemyX = enemyXpos[random.nextInt(34)];
		enemyY = enemyYpos[random.nextInt(23)];
		if(count<725)
		{
			for(int i=1;i<lengthOfSnake;i++)
			{
				if(enemyX==snakeXpos[i]&&enemyY==snakeYpos[i])
				{
					count++;
					valid();
				}
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("Action: "+e.getActionCommand());
		if(right&&!gameover)
		{
			for(int i=lengthOfSnake-1; i>=0; i--)
			{
				if(i==0)
					snakeXpos[0] += 25;	
				else
					snakeXpos[i] = snakeXpos[i-1];
				if(snakeXpos[0]>850)
				{
					snakeXpos[0]=25;
				}
			}
			
			for(int i=lengthOfSnake-1; i>=1; i--)
			{
					snakeYpos[i] = snakeYpos[i-1];
			}
			repaint();
		}
		if(left&&!gameover)
		{
			for(int i=lengthOfSnake-1; i>=0; i--)
			{
				if(i==0)
					snakeXpos[0] -= 25;	
				else
					snakeXpos[i] = snakeXpos[i-1];
				if(snakeXpos[0]<25)
				{
					snakeXpos[0]=850;
				}
			}
			
			for(int i=lengthOfSnake-1; i>=1; i--)
			{
					snakeYpos[i] = snakeYpos[i-1];
			}
			repaint();
		}
		if(up&&!gameover)
		{
			for(int i=lengthOfSnake-1; i>=0; i--)
			{
				if(i==0)
					snakeYpos[0] -= 25;	
				else
					snakeYpos[i] = snakeYpos[i-1];
				if(snakeYpos[0]<75)
				{
					snakeYpos[0]=625;
				}
			}
			
			for(int i=lengthOfSnake-1; i>=1; i--)
			{
					snakeXpos[i] = snakeXpos[i-1];
			}
			repaint();
		}
		if(down&&!gameover)
		{
			for(int i=lengthOfSnake-1; i>=0; i--)
			{
				if(i==0)
					snakeYpos[0] += 25;	
				else
					snakeYpos[i] = snakeYpos[i-1];
				if(snakeYpos[0]>625)
				{
					snakeYpos[0]=75;
				}
			}
			
			for(int i=lengthOfSnake-1; i>=1; i--)
			{
					snakeXpos[i] = snakeXpos[i-1];
			}
			repaint();
		}
	}
	@Override
	public void keyPressed(KeyEvent e) 
	{
		System.out.println("keyPressed");
		started = true;
		timer.start();
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(!left)
			{
				right = true;
				up = false;
				down = false;
				
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			if(!right)
			{
				left = true;
				up = false;
				down = false;
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			if(!down)
			{
				up = true;
				left = false;
				right = false;
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			if(!up)
			{
				down = true;
				left = false;
				right = false;
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			lengthOfSnake = 3;
			count=0;
			started = false;
			right = false;
			left = false;
			up = false;
			down = false;
			repaint();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}