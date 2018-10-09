import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.Ellipse2D;
public class Ball
{
	private int x, y, dir, size;
	private Polygon bounds;

	public Ball(int x, int y, int size, int dir, Polygon b)
	{

		this.x = x;
		this.y = y;
		this.dir = dir;
		bounds = b;
	}

	public void move()
	{
		if(dir == 0)
		{
			if(bounds.contains(new Rectangle(x+2, y, 12, 12)))
				x++;
			else dir = 1;
		}
		if(dir == 1)
		{
			if(bounds.contains(new Rectangle(x-2, y, 12, 12)))
				x--;
			else dir = 0;
		}

		if(dir == 2)
		{
			if(bounds.contains(new Rectangle(x, y-2, 12, 12)))
				y--;
			else dir = 3;
		}

		if(dir == 3)
		{
			if(bounds.contains(new Rectangle(x, y+2, 12, 12)))
				y++;
			else dir = 2;
		}

	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getSize()
	{
		return size;
	}

	public int getDir()
	{
		return dir;
	}

}