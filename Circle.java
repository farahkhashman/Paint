import java.awt.Graphics;

public class Circle extends Shape{
	
	public Circle(int x, int y, int width, int height, String type) {
		super(x, y, width, height, type);
	}
	public void draw(Graphics g) {
		g.fillOval(x, y, width, height);
	}

}
