import java.awt.Graphics;

public class Rectangle2 extends Shape{

	public Rectangle2(int o, int l, int j, int k, String type) {
		super(o, l, j, k, type);
	}
	
	public void draw(Graphics g) {
		g.fillRect(x, y, width, height);
	}
}
