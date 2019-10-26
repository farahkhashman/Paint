import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Shape extends Rectangle{
	String name;

	public Shape(int n, int m, int w, int h, String type) {
		x = n;
		y = m; 
		width = w; 
		height = h;
		name = type;
	}

	public abstract void draw(Graphics g);

	
}
