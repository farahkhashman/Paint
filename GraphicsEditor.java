import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JColorChooser;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import javax.swing.JFileChooser;
import java.awt.image.BufferedImage;
import java.awt.Image;

public class GraphicsEditor extends JPanel {
	public final int width = 800, height = 600;
	ArrayList<Shape> list = new ArrayList<Shape>();
	ArrayList<Color> colorlist = new ArrayList<Color>();
	ArrayList<Image> imgs = new ArrayList<Image>();
	ArrayList<Point> locations = new ArrayList<Point>();
	ArrayList<Integer> widthlist = new ArrayList<Integer>();
	ArrayList<Integer> heightlist = new ArrayList<Integer>();
	
	int x, x2, y, y2;
	Point start, end, p1, p2, check, loc, location, oldloc;
	int diam;
	boolean rectangle = false, circle = false, line = false, color = false, moving = false, deleting = false, icon = false, shape = false, pic = false, resizing = false;
	Color c;
	String undo = "";
	JLabel sampleText = new JLabel("Label");
	Shape g1, g2;
	BufferedImage img;
	private int imgwidth = 50, imgheight = 50;
	Image smallerimg, oldimg;
	String inputwidth, inputheight;
	int imagewidth, imageheight;
	

	public GraphicsEditor(){
		BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(boxlayout);
		setBorder(BorderFactory.createTitledBorder("Graphics Editor"));
		JTextArea displayarea = new JTextArea();
		displayarea.setEditable(true);

		
		JButton rectbutton = new JButton("Rectangle");
		rectbutton.setToolTipText("Draw a Rectangle");
		rectbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				rectangle = true;
			}
		});
		
		JButton linebutton = new JButton("Line");
		linebutton.setToolTipText("Draw a Line");
		linebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				line = true;
			}
		});
		
		JButton circlebutton = new JButton("Circle");
		circlebutton.setToolTipText("Draw a Circle");
		circlebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				circle = true;
			}
		});
		
		JButton delete = new JButton("Delete");
		delete.setToolTipText("select an image or shape you want to delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleting = true;
			}
		});
		
		JButton undomove = new JButton("Undo Move");
		undomove.setToolTipText("Undo Last Move");
		undomove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(undo.equals("shape")) {
				for(int i = 0; i<list.size(); i++) {
					if(list.get(i).equals(g1)) {
						list.remove(i);
						list.add(g2);
					}
				}
			}
			if(undo.equals("picture")) {
				for(int j = 0; j<imgs.size(); j++) {
					if(imgs.get(j).equals(oldimg)) {
						locations.set(j,oldloc);
					}
				}
			}
			repaint();
				
			}
		});
		
		JButton colour = new JButton("Choose Colour");
		colour.setToolTipText("Choose a colour for your next shape");
		colour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c = JColorChooser.showDialog(null, "Choose a Color", sampleText.getForeground());
			      if (c == null)
			        colorlist.add(Color.black);
			      else {
					colorlist.add(c);
			      }
			    }
			});
		
		JButton move = new JButton("Move");
		move.setToolTipText("move your shapes around");
		move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moving = true;
			}
		});
		
		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.clear();
				repaint();
			}
		});
		
		JButton resize = new JButton("Resize Image");
		resize.setToolTipText("select an image to resize");
		resize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resizing = true;
			}
		});
		
		
		JButton image = new JButton("Insert Image");
		image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnValue = fc.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		        File selectedFile = fc.getSelectedFile();
		        img = null;
		        inputwidth = JOptionPane.showInputDialog("Width of Image: ");
		        inputheight = JOptionPane.showInputDialog("Height of Image: ");
		        imagewidth = Integer.parseInt(inputwidth);
		        imageheight = Integer.parseInt(inputheight);
		        widthlist.add(imagewidth);
		        heightlist.add(imageheight);
		        try {
		            img = ImageIO.read(selectedFile);
		            smallerimg = img.getScaledInstance(imagewidth, imageheight, Image.SCALE_SMOOTH);
		            imgs.add(smallerimg);
		        } catch (IOException f) {
		        		System.out.println("no");
		        		}
		        icon = true;
		        }
			}
		});

		
		JPanel innerPanel = new JPanel();
		innerPanel.add(rectbutton);
		innerPanel.add(linebutton);
		innerPanel.add(circlebutton);
		innerPanel.add(delete);
		innerPanel.add(colour);
		innerPanel.add(move);
		innerPanel.add(clear);
		innerPanel.add(undomove);
		innerPanel.add(image);
		innerPanel.add(resize);
		add(innerPanel);
		

		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		this.setFocusable(true);
		
        colorlist.add(Color.BLUE);
		drawing();
	
	}
	
	public void drawing() {
		x = y = x2 = y2 = 0;
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(rectangle) {
					x = e.getX();
		            y = e.getY();  
		            Rectangle2 r = new Rectangle2(x, y, 0, 0, "rectangle");
		            list.add(r);
		            repaint();
				}
				if(line) {
					start = e.getPoint();
					Line li = new Line(start.x, start.y, start.x, start.y, "line");
					list.add(li);
					repaint();
				}
				if(circle) {
					p1 = e.getPoint();
					Circle c = new Circle(p1.x, p1.y, 0, 0, "circle");
					list.add(c);
					repaint();
				}
				if(moving) {
					check = e.getPoint();
					for(int i = 0; i<list.size(); i++) {
						if(list.get(i).contains(check)) {
							if(list.get(i).name.equals("rectangle"))
								g2 = new Rectangle2(list.get(i).x, list.get(i).y, list.get(i).width, list.get(i).height, "rectangle");
							else if(list.get(i).name.equals("line"))
								g2 = new Line(list.get(i).x, list.get(i).y, list.get(i).width, list.get(i).height, "line");
							else if(list.get(i).name.equals("circle"))
								g2 = new Circle(list.get(i).x, list.get(i).y, list.get(i).width, list.get(i).height, "circle");
							g1 = list.get(i);
							c = colorlist.get(i);
							colorlist.remove(i);
							list.remove(i);
							repaint();
							list.add(g1);
							colorlist.add(c);
							shape = true;
							undo = "shape";
						}
					}
					for(int j = 0; j<imgs.size(); j++) {
						Rectangle imgbound = new Rectangle(locations.get(j).x, locations.get(j).y, imgwidth, imgheight);
						if(imgbound.contains(check)) {
							oldloc = locations.get(j);
							oldimg = imgs.get(j);
							imgs.add(imgs.get(j));
							imgs.remove(j);
							locations.add(locations.get(j));
							locations.remove(j);
							repaint();
							pic = true;
							undo = "picture";
						}
					}
				}
				if(deleting) {
					check = e.getPoint();
					for(int i = 0; i<list.size(); i++) {
						if(list.get(i).contains(check)) {
							list.remove(i);
							colorlist.remove(i);
							repaint();
							deleting = false;
						}
					}
					for(int j = 0; j<imgs.size(); j++) {
						Rectangle imgbound = new Rectangle(locations.get(j).x, locations.get(j).y, widthlist.get(j), heightlist.get(j));
						if(imgbound.contains(check)) {
							imgs.remove(j);
							locations.remove(j);
							widthlist.remove(j);
							heightlist.remove(j);
							repaint();
						}
					}
					
				}
				if(icon) {
					location = e.getPoint();
					locations.add(location);
					repaint();
				}
				
				if(resizing) {
					check = e.getPoint();
					for(int i = 0; i<imgs.size(); i++) {
						Rectangle imagebounds = new Rectangle(locations.get(i).x, locations.get(i).y, widthlist.get(i), heightlist.get(i));
						if(imagebounds.contains(check)) {
							String widths = JOptionPane.showInputDialog("Width of Image: ");
					        String heights = JOptionPane.showInputDialog("Height of Image: ");
					        imagewidth = Integer.parseInt(widths);
					        imageheight = Integer.parseInt(heights);
					        widthlist.set(i, imagewidth);
					        heightlist.set(i, imageheight);
					        Image sizingimage = imgs.get(i).getScaledInstance(widthlist.get(i), heightlist.get(i), Image.SCALE_SMOOTH);
					        imgs.set(i, sizingimage);
					        repaint();
						}
					}
				}
			}
			public void mouseReleased(MouseEvent e) {
				if(rectangle) {
					list.remove(list.size()-1);
					int px = Math.min(x,x2);
		            int py = Math.min(y,y2);
		            int pw=Math.abs(x-x2);
		            int ph=Math.abs(y-y2);
		            Rectangle2 r = new Rectangle2(px, py, pw, ph, "rectangle");
		            list.add(r);
		            repaint();
		            rectangle = false;
				}
				if(line) {
					list.remove(list.size()-1);
					Line ls = new Line(start.x, start.y, end.x, end.y, "line");
					list.add(ls);
					repaint();
					line = false;
				}
				if(circle) {
					list.remove(list.size()-1);
					Circle cs = new Circle(p1.x, p1.y, diam, diam, "circle");
					list.add(cs);
					repaint();
					circle = false;
				}
				if(moving) {
					if(!list.isEmpty() && shape) {
						list.remove(list.size()-1);
						g1.x = e.getX();
						g1.y = e.getY();
						list.add(g1);
						repaint();
					}
					
					if(!imgs.isEmpty() && pic) {
						locations.remove(locations.size()-1);
						location = e.getPoint();
						locations.add(location);
						repaint();
					}
					moving = false;
					shape = false;
					pic = false;
				}
				if(icon) {
					icon = false;
				}
				if(deleting)
					deleting = false;
				if(resizing) 
					resizing = false;
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if(rectangle) {
					list.remove(list.size()-1);
		            x2 = e.getX();
		            y2 = e.getY();
		            int px = Math.min(x,x2);
		            int py = Math.min(y,y2);
		            int pw=Math.abs(x-x2);
		            int ph=Math.abs(y-y2);
		            Rectangle2 r = new Rectangle2(px, py, pw, ph, "rectangle");
		            list.add(r);
		            repaint();
				}
				if(line) {
					end = e.getPoint();
	        	  		list.remove(list.size()-1);
	        	  		Line ls = new Line(start.x, start.y, end.x, end.y, "line");
	        	  		list.add(ls);
	        	  		repaint();
				}
				if(circle) {
					list.remove(list.size()-1);
					p2 = e.getPoint();
					diam = (int) Math.abs(Math.sqrt(Math.pow((p1.x-p2.x), 2)+Math.pow((p1.y-p2.y), 2)));
					Circle cs = new Circle(p1.x, p1.y, diam, diam, "circle");
					list.add(cs);
					repaint();
				}
				if(moving) {
					if(!list.isEmpty() && shape) {
						list.remove(list.size()-1);
						loc = e.getPoint();
						g1.x = (int) loc.getX();
						g1.y = (int) loc.getY();
						list.add(g1);
						repaint();	
					}
					if(!imgs.isEmpty() && pic) {
						locations.remove(locations.size()-1);
						location = e.getPoint();
						locations.add(location);
						repaint();
					}
				}
			}
		});
		
	}
	
    public void paint(Graphics g) {
        super.paint(g);
    		for(int i = 0; i<list.size(); i++) {
    			try {
    				g.setColor(colorlist.get(i));
    			}
    			catch(Exception d){
    				colorlist.add(Color.BLACK);
    				g.setColor(colorlist.get(i));
    			}
    			list.get(i).draw(g);
        }
    			for(int i = 0; i<imgs.size(); i++) {
    				g.drawImage(imgs.get(i), locations.get(i).x, locations.get(i).y, null);
    			}
    		
    }


	
	public static void main(String[] args) {
		new GraphicsEditor();

	}

}



//rejected code
/*public void addrect() {
x = y = x2 = y2 = 0;
addMouseListener(new MouseAdapter() {
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();  
        Rectangle2 r = new Rectangle2(x, y, 0, 0);
        list.add(r);
        repaint();
      }
    public void mouseReleased(MouseEvent e) {
        int px = Math.min(x,x2);
        int py = Math.min(y,y2);
        int pw=Math.abs(x-x2);
        int ph=Math.abs(y-y2);
        Rectangle2 r = new Rectangle2(px, py, pw, ph);
        list.add(r);
        repaint();
      }
    });

  addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
    	  		list.remove(list.size()-1);
            x2 = e.getX();
            y2 = e.getY();
            int px = Math.min(x,x2);
            int py = Math.min(y,y2);
            int pw=Math.abs(x-x2);
            int ph=Math.abs(y-y2);
            Rectangle2 r = new Rectangle2(px, py, pw, ph);
            list.add(r);
            repaint();
      }
    });
}
*/

/*public void addline() {
	addMouseListener(new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			start = e.getPoint();
			Line li = new Line(start.x, start.y, start.x, start.y);
			list.add(li);
			repaint();
		}
		public void mouseReleased(MouseEvent e) {
			list.remove(list.size()-1);
			Line ls = new Line(start.x, start.y, end.x, end.y);
			list.add(ls);
			repaint();
		}
	});
      addMouseMotionListener(new MouseMotionAdapter() {
          public void mouseMoved(MouseEvent e) {
        	  	end = e.getPoint();
          }
          public void mouseDragged(MouseEvent e) {
        	  	end = e.getPoint();
        	  	list.remove(list.size()-1);
			Line ls = new Line(start.x, start.y, end.x, end.y);
			list.add(ls);
			
        	  	repaint();
          }
        });
	
}*/

/*public void addcircle() {
addMouseListener(new MouseAdapter() {
	public void mousePressed(MouseEvent e) {
		p1 = e.getPoint();
		Circle c = new Circle(p1.x, p1.y, 0, 0);
		list.add(c);
		repaint();
	}
	public void mouseReleased(MouseEvent e) {
		list.remove(list.size()-1);
		Circle cs = new Circle(p1.x, p1.y, diam, diam);
		list.add(cs);
		repaint();
	}
});
addMouseMotionListener(new MouseMotionAdapter() {
	public void mouseDragged(MouseEvent e) {
		list.remove(list.size()-1);
		p2 = e.getPoint();
		diam = (int) Math.abs(Math.sqrt(Math.pow((p1.x-p2.x), 2)+Math.pow((p1.y-p2.y), 2)));
		Circle cs = new Circle(p1.x, p1.y, diam, diam);
		list.add(cs);
		repaint();
	}
});
}*/
/*for(int j = 0; j<imgs.size(); j++) {
for(int i = 0; i<sizes.get(j).length(); i++) {
	if(sizes.get(j).charAt(i)==32) {
		imagewidth = Integer.parseInt(sizes.get(j).substring(0, i));
		imageheight = Integer.parseInt(sizes.get(j).substring(i+1));
		Rectangle imgbound = new Rectangle(locations.get(j).x, locations.get(j).y, imagewidth, imageheight);
		if(imgbound.contains(check)) {
		
    String widths = JOptionPane.showInputDialog("Width of Image: ");
    String heights = JOptionPane.showInputDialog("Height of Image: ");
    String combined = "" +widths+ " " +heights;
    sizes.set(j, combined);
}
}
}
repaint();
}
}*/
