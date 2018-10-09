import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
public class WorldsHardestGame extends JPanel implements KeyListener,Runnable
{
	private float angle;
	private int x;
	private int y;
	private int deaths, lv;
	private JFrame frame;
	private Thread t;
	private boolean gameOn, up, down, left, right, gotBall;
	private Font f;
	private Polygon bgShape, fgShape, winArea;
	private GradientPaint gp;
	private Color color, sq1, sq2, sq3;
	private ArrayList<Ball> ballList;
	private ArrayList<Ellipse2D.Double> circleList;
	public WorldsHardestGame()
	{
		frame = new JFrame();
		frame.setResizable(false);
		x = 86;
		y = 208;
		gameOn = true;
		color = new Color(180,181,254);
		sq1 = new Color(247, 247, 255);
		sq2 = new Color(230, 230, 255);
		sq3 = new Color(181, 254, 180);
		f = new Font("Arial",Font.BOLD,16);
		frame.addKeyListener(this);
		frame.add(this);
		frame.setSize(630,400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		t = new Thread(this);
		t.start();
		levelOne();


	}
	public void levelOne()
	{
		lv = 1;
		bgShape= new Polygon();
				bgShape.addPoint(53,134);
				bgShape.addPoint(138,134);
				bgShape.addPoint(138,270);
				bgShape.addPoint(162,270);
				bgShape.addPoint(162,162);
				bgShape.addPoint(396,162);
				bgShape.addPoint(396,134);
				bgShape.addPoint(567,134);
				bgShape.addPoint(567,162);
				bgShape.addPoint(567,300);
				bgShape.addPoint(452,300);
				bgShape.addPoint(452,167);
				bgShape.addPoint(428,167);
				bgShape.addPoint(428,272);
				bgShape.addPoint(193,272);
				bgShape.addPoint(193,300);
				bgShape.addPoint(53,300);

		fgShape= new Polygon();
				fgShape.addPoint(56,137);
				fgShape.addPoint(135,137);
				fgShape.addPoint(135,273);
				fgShape.addPoint(165,273);
				fgShape.addPoint(165,165);
				fgShape.addPoint(399,165);
				fgShape.addPoint(399,137);
				fgShape.addPoint(564,137);
				fgShape.addPoint(564,165);
				fgShape.addPoint(564,297);
				fgShape.addPoint(455,297);
				fgShape.addPoint(455,164);
				fgShape.addPoint(425,164);
				fgShape.addPoint(425,269);
				fgShape.addPoint(190,269);
				fgShape.addPoint(190,297);
				fgShape.addPoint(56,297);

		winArea = new Polygon();
				winArea.addPoint(455,137);
				winArea.addPoint(564,137);
				winArea.addPoint(564,297);
				winArea.addPoint(455,297);


		ballList = new ArrayList<Ball>();
				ballList.add(new Ball(172, 172, 12, 0, fgShape));
				ballList.add(new Ball(406, 198, 12, 1, fgShape));
				ballList.add(new Ball(172, 223, 12, 0, fgShape));
				ballList.add(new Ball(406, 250, 12, 1, fgShape));

	}

	public void levelTwo()
	{
		lv = 2;
		bgShape = new Polygon();
				bgShape.addPoint(53,189);
				bgShape.addPoint(135,189);
				bgShape.addPoint(135,135);
				bgShape.addPoint(465,135);
				bgShape.addPoint(465,189);
				bgShape.addPoint(547,189);
				bgShape.addPoint(547,246);
				bgShape.addPoint(465,246);
				bgShape.addPoint(465,303);
				bgShape.addPoint(135,303);
				bgShape.addPoint(135,246);
				bgShape.addPoint(53,246);

		fgShape = new Polygon();
				fgShape.addPoint(56,192);
				fgShape.addPoint(138,192);
				fgShape.addPoint(138,138);
				fgShape.addPoint(462,138);
				fgShape.addPoint(462,192);
				fgShape.addPoint(544,192);
				fgShape.addPoint(544,243);
				fgShape.addPoint(462,243);
				fgShape.addPoint(462,300);
				fgShape.addPoint(138,300);
				fgShape.addPoint(138,243);
				fgShape.addPoint(56,243);

		winArea = new Polygon();
				winArea.addPoint(462,192);
				winArea.addPoint(544,192);
				winArea.addPoint(544,243);
				winArea.addPoint(462,243);

		ballList = new ArrayList<Ball>();
				ballList.add(new Ball(143, 143, 12, 3, fgShape));
				ballList.add(new Ball(198, 143, 12, 3, fgShape));
				ballList.add(new Ball(253, 143, 12, 3, fgShape));
				ballList.add(new Ball(307, 143, 12, 3, fgShape));
				ballList.add(new Ball(362, 143, 12, 3, fgShape));
				ballList.add(new Ball(416, 143, 12, 3, fgShape));
				ballList.add(new Ball(198, 143, 12, 3, fgShape));
				ballList.add(new Ball(170, 278, 12, 2, fgShape));
				ballList.add(new Ball(224, 278, 12, 2, fgShape));
				ballList.add(new Ball(280, 278, 12, 2, fgShape));
				ballList.add(new Ball(330, 278, 12, 2, fgShape));
				ballList.add(new Ball(389, 278, 12, 2, fgShape));
				ballList.add(new Ball(444, 278, 12, 2, fgShape));
	}








	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;

		g2d.setColor(color);
		g2d.fillRect(0,0,800,500);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0,0,800, 30);
		g2d.setColor(Color.WHITE);
		g2d.setFont(f);

		g2d.drawString("MENU",10,20);
		g2d.drawString(lv+"/30",295,20);
		g2d.drawString("DEATHS: "+deaths,524,20);

		g2d.setColor(Color.BLACK);
		g2d.fill(bgShape);
		g2d.setColor(sq3);
		g2d.fill(fgShape);

		if(lv == 1)
		{
			g2d.setColor(sq1);
			g2d.fillRect(136, 273, 28, 24);
			g2d.setColor(sq2);
			g2d.fillRect(164, 273, 26, 24);
			g2d.fillRect(165, 270, 24, 4);
			g2d.setColor(sq1);
			g2d.fillRect(165, 268, 25, 3);
			int newX = 165;
			int newY = 165;
			int count = 0;
			for(int i = 0; i  < 40; i++)
			{
				count++;
				if(count%2==0)
					g2d.setColor(sq1);
				else g2d.setColor(sq2);
				g2d.fillRect(newX, newY, 26, 26);

				if(i%10==9)
				{
					newY += 26;
					newX = 165;
					count++;
				}
				else newX+=26;

			}
			g2d.fillRect(399, 137, 26, 28);
			g2d.setColor(sq1);
			g2d.fillRect(425, 137, 28, 27);



		}
		if(lv == 2)
		{
			int newX = 138;
			int newY = 138;
			int count = 0;
			for(int i = 0; i  < 72; i++)
			{
				count++;
				if(count%2==0)
					g2d.setColor(sq1);
				else g2d.setColor(sq2);
				g2d.fillRect(newX, newY, 27, 27);

				if(i%12==11)
				{
					newY += 27;
					newX = 138;
					count++;
				}
				else newX+=27;

			}
		}
		if(lv == 2 && !gotBall)
		{
			g2d.setColor(Color.BLACK);
			g2d.fillOval(293,211,12,12);
			g2d.setColor(Color.YELLOW);
			g2d.fillOval(295,213,8,8);
		}

		for(Ball b: ballList)
		{
			g2d.setColor(Color.BLACK);
			g2d.fillOval(b.getX(),b.getY(),12,12);
			g2d.setColor(Color.BLUE);
			g2d.fillOval(b.getX()+2,b.getY()+2,8,8);
		}

		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(Color.BLACK);
		g2d.drawRect(x,y,15,15);
		g2d.setColor(Color.RED);
		g2d.fillRect(x+1,y+1,14,14);
	}
	public void run()
	{
		while(true)
		{
			while(gameOn)
			{


				if(winArea.contains(new Rectangle(x+2, y, 15, 15)))
				{	if(lv == 1)
					{	deaths = 0;
						x = 86;
						y = 208;
						levelTwo();
					}
					if(lv == 2)
					{
						if(!gotBall)
						{
							x = 86;
							y = 208;
						}
					}


				}

					if(right && fgShape.contains(new Rectangle(x+2, y, 15, 15)))
						x++;
					if(left && fgShape.contains(new Rectangle(x-2, y, 15, 15)))
						x--;
					if(up && fgShape.contains(new Rectangle(x, y-2, 15, 15)))
						y--;
					if(down && fgShape.contains(new Rectangle(x, y+2, 15, 15)))
						y++;
				circleList = new ArrayList<Ellipse2D.Double>();
				for(Ball b : ballList)
				{
					b.move();
					circleList.add(new Ellipse2D.Double(b.getX(),b.getY(),12,12));
				}

				if(lv == 2)
				{
					Ellipse2D.Double yBall = new Ellipse2D.Double(293,211,12,12);
					if(yBall.intersects(new Rectangle(x, y, 15, 15)))
						gotBall = true;
				}

				if(isIntersecting())
				{
					deaths++;
					x = 86;
					y = 208;
				}


				try
				{
					t.sleep(5);
				}
				catch(InterruptedException e)
				{

				}
				repaint();
			}
		}
	}

public boolean isIntersecting()
{
	for(Ellipse2D.Double c: circleList)
	{
		if(c.intersects(new Rectangle(x,y,15,15)))
			return true;
	}
	return false;
}

	public void keyPressed(KeyEvent ke)
	{
		System.out.println(ke.getKeyCode());
		if(ke.getKeyCode()==37)
			left = true;
		if(ke.getKeyCode()==38)
			up = true;
		if(ke.getKeyCode()==39)
			right = true;
		if(ke.getKeyCode()==40)
			down = true;

	}
	public void keyReleased(KeyEvent ke)
	{

		right = false;
		left = false;
		up = false;
		down = false;
	}
	public void keyTyped(KeyEvent ke)
	{
	}
	public static void main(String args[])
	{
		WorldsHardestGame app=new WorldsHardestGame();
	}
}

