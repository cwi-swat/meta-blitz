package bezier.demos;

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

import bezier.image.Image;
import bezier.image.Rasterize;
import bezier.image.Sample;
import bezier.image.Sample;
import bezier.composite.Path;
import bezier.composite.Paths;
import bezier.composite.Shapes;
import bezier.image.RasterImage;
import bezier.points.Vec;
import bezier.util.DummySWTSHape;


public abstract class DemoBase  extends JComponent implements KeyListener,MouseWheelListener,MouseListener, MouseMotionListener, WindowListener{
	Vec size;
	Vec mouse;
	String textInput;
	String lastLine;
	double wheel;
	Graphics2D g;
	
	DemoBase()  {
        // Create a frame
        JFrame frame = new JFrame();

        // Add a component with a custom paint method
        frame.getContentPane().add(this);

        // Display the frame
        int frameWidth = 1000;
        int frameHeight = 800;
        size = new Vec(1000,800);
        mouse = new Vec(0,0);
        textInput = "";
        lastLine ="";
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

	public void fillOval(Vec location, double w){
		fillOval(location, w, w);
	}
	
	public void fillOval(Vec location, double w, double h){
		fillOval(location, w, h,Sample.BLACK);
	}
	
	public void fillOval(Vec location, double w, Sample inside){
		fillOval(location, w, w, inside);
	}
	
	public void fillOval(Vec location, double w, double h, Sample inside){
		drawOval(location, w, h,Sample.TRANSPARENT,inside);
	}
	
	public void drawOval(Vec location, double w){
		drawOval(location, w,w);
	}
	
	public void drawOval(Vec location, double w, double h){
		drawOval(location, w, h, Sample.BLACK);
	}
	
	public void drawOval(Vec location, double w, double h, Sample border){
		drawOval(location, w, h, border,Sample.TRANSPARENT);
	}
	
	public void drawOval(Vec location, double w, Sample border){
		drawOval(location, w, border, Sample.TRANSPARENT);
	}
	
	public void drawOval(Vec location, double w, Sample border, Sample inside){
		drawOval(location, w,w, border, inside);
	}
	
	public void drawOval(Vec location, double w, double h, Sample border, Sample inside){
		if(!inside.isFullyTransparent()){
			g.setColor(inside.toAWT());
			g.fillOval(round(location.x)-round(w/2.0),round(location.y)-round(h/2.0), round(w), round(h));
		}
		if(!border.isFullyTransparent()){
			g.setColor(border.toAWT());
			g.drawOval(round(location.x)-round(w/2.0),round(location.y)-round(h/2.0), round(w), round(h));
		}
	}
	
	public void fillRect(Vec location, double w){
		fillRect(location, w, w);
	}
	
	public void fillRect(Vec location, double w, double h){
		fillRect(location, w, h,Sample.BLACK);
	}
	
	public void fillRect(Vec location, double w, Sample inside){
		fillRect(location, w, w, inside);
	}
	
	public void fillRect(Vec location, double w, double h, Sample inside){
		drawRect(location, w, h,Sample.TRANSPARENT,inside);
	}
	
	public void drawRect(Vec location, double w, double h){
		drawRect(location, w, h, Sample.BLACK);
	}
	
	public void drawRect(Vec location, double w, double h, Sample border){
		drawRect(location, w, h, border,Sample.TRANSPARENT);
	}
	
	public void drawRect(Vec location, double w, Sample border){
		drawRect(location, w, border, Sample.TRANSPARENT);
	}
	
	public void drawRect(Vec location, double w, Sample border, Sample inside){
		drawRect(location, w,w, border, inside);
	}
	
	public void drawRect(Vec location, double w, double h, Sample border, Sample inside){
		
		if(!inside.isFullyTransparent()){
			g.setColor(inside.toAWT());
			g.fillRect(round(location.x)-round(w/2.0),round(location.y)-round(h/2.0), round(w), round(h));
		}
		if(!border.isFullyTransparent()){
			g.setColor(border.toAWT());
			g.drawRect(round(location.x)-round(w/2.0),round(location.y)-round(h/2.0), round(w), round(h));
		}
	}
	
	public void fill(Paths p){
		draw(p,Sample.TRANSPARENT,Sample.BLACK);
	}
	
	public void fill(Paths p,Sample inside){
		draw(p,Sample.TRANSPARENT,inside);
	}
	
	public void fill(Path p){
		draw(p,Sample.TRANSPARENT,Sample.BLACK);
	}
	
	public void fill(Path p,Sample inside){
		draw(p,Sample.TRANSPARENT,inside);
	}
	
	public void draw(Paths p){
		draw(p,Sample.BLACK);
	}
	
	public void draw(Paths p, Sample border){
		draw(p,border,Sample.TRANSPARENT);
	}
	
	public void draw(Paths p, Sample border, Sample inside){
		
		if(!inside.isFullyTransparent()){
			g.setColor(inside.toAWT());
			g.fill(new DummySWTSHape(p.getPathIterator()));
		}
		if(!border.isFullyTransparent()){
			g.setColor(border.toAWT());
			g.draw(new DummySWTSHape(p.getPathIterator()));
		}
	}
	
	
	
	public void blit(RasterImage img){
		g.drawImage(img.toAWT(), img.x, img.y, null);
	}
	
	
	public void draw(Image img){
		blit(Rasterize.rasterize(img));
	}
	
	public void draw(Path p){
		draw(p,Sample.BLACK);
	}
	
	public void draw(Path p, Sample border){
		draw(p,border,Sample.TRANSPARENT);
	}
	
	public void draw(Path p, Sample border, Sample inside){
		draw(new Paths(p),border,inside);
	}
	
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

}
