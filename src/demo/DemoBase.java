package demo;

import static paths.paths.factory.PathFactory.createLine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

import deform.BBox;
import deform.Vec;

import paths.paths.factory.PathFactory;
import paths.paths.paths.Path;
import textures.interfaces.ITexturedPath;
import textures.sample.LocatedBufferedImage;





public abstract class DemoBase  extends JComponent implements KeyListener,MouseWheelListener,MouseListener, MouseMotionListener, WindowListener{
	private static final long serialVersionUID = 1478770999111265074L;
	public Vec size;
	public Vec mouse;
	public String textInput;
	public String lastLine;
	public double wheel;
	public Graphics2D g;
	
	public DemoBase()  {
        mouse = new Vec(0,0);
        size = new Vec(1000,800);

        textInput = "";
        lastLine ="";
        // Create a frame
        JFrame frame = new JFrame();

        // Add a component with a custom paint method
        frame.getContentPane().add(this);

        // Display the frame
        int frameWidth = 1000;
        int frameHeight = 800;

        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
        frame.addWindowListener(this);
        addMouseMotionListener(this);
        frame.addMouseListener(this);
        frame.addMouseWheelListener(this);
        frame.addKeyListener(this);
        
    }
	
	public static int round(double x){
		return (int)(x + 0.5);
	}

	
	
	public void blit(ITexturedPath<textures.sample.Color> texPath){
		BBox b = new BBox(0,0,size.x,size.y);
		texPath.render(g, b);
	}
//	
//	public void draw(Image<Sample4> img){
//		blit(new Raster4(new Mul<Sample4>(img,255.0)));
//	}
//	
//	
//	public void blit(RasterInstances.Raster4 img){
//		if(img.area.width == 0 || img.area.height == 0){
//			return;
//		}
//		BufferedImage bi = new BufferedImage(img.getArea().width, img.getArea().height, BufferedImage.TYPE_INT_ARGB);
//		bi.getRaster().setPixels(0, 0, img.getArea().width, img.getArea().height, img.data);
//		g.drawImage(bi, img.getArea().x, img.getArea().y, null);
//	}
//	

	
	public abstract void draw();
	


	public void handleMouseClick(int button) {
	}
	
	public void handleKeyStroke(char key){
	}
	
	@Override
   	public void paint(Graphics g) {
   		this.g = (Graphics2D)g;
   	    this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
   	    this.g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int width = getSize().width-1;
        int height = getSize().height-1;
        size = new Vec(width,height);
        g.setColor(Color.black);
//        g.fillRect(0, 0, width+1, height);
   		draw();
   	}
	
	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		this.setEnabled(false);
		System.exit(0);
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {	
		mouse = new Vec(e.getX(),e.getY());
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		handleMouseClick(e.getButton());
		repaint();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		handleKeyStroke(e.getKeyChar());
		if(e.getKeyChar() == '\b'){
			if(!textInput.isEmpty()){
				textInput = textInput.substring(0, textInput.length()-1);
			}
			if(!lastLine.isEmpty()){
				lastLine = lastLine.substring(0, lastLine.length()-1);
			}
		}
		else if(e.getKeyChar() == '\n'){
			lastLine = "";
			textInput+=e.getKeyChar();
		} else {
			lastLine+=e.getKeyChar();
			textInput+=e.getKeyChar();
		}
		repaint();
	}


	@Override
	public void keyPressed(KeyEvent e) {
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		wheel += e.getUnitsToScroll();
		repaint();
	}
	
	public void run() {
		
	}
	
	public Path rectangle(){
		Vec a = new Vec(-1,-1);
		Vec b = new Vec(1,-1);
		Vec c = new Vec(1,1);
		Vec d = new Vec(-1,1);
//		Vec e = new Vec(150,50);
		
			return PathFactory.createClosedPath(createLine(a,b), createLine(b,c), createLine(c,d),createLine(d,a));
	
	}
	

}
