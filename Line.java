import java.awt.Graphics;

public class Line extends Shape{
	public Line( int x1, int y1, int x2, int y2, String type) {
		super(x1, y1, x2, y2, type);
	}
	
	public void draw(Graphics g) {
		g.drawLine(x, y, width, height);
	}
}
