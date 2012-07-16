package demo;

import static paths.paths.factory.PathFactory.createLine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.RepaintManager;

import deform.BBox;
import deform.Vec;
import demo.SuperDemoBase.UnRepaintManager;

import paths.paths.factory.PathFactory;
import paths.paths.paths.Path;
import textures.interfaces.ITexturedPath;
import textures.sample.LocatedBufferedImage;





public abstract class NewDemoBase  extends JFrame implements KeyListener,MouseWheelListener,MouseListener, MouseMotionListener, WindowListener{
	private static final long serialVersionUID = 1478770999111265074L;
	public Vec size;
	public Vec mouse;
	public String textInput;
	public String lastLine;
	public double wheel;
    private BufferStrategy bufferStrategy;
    private boolean isRunning;
    private Graphics2D g;
    private Insets insets;
    
	public NewDemoBase()  {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mouse = new Vec(0,0);
        setVisible(true);
        setIgnoreRepaint(true); // don't need Java painting for us
        setResizable(false); // don't want someone resizing our "game" for us
        // set up our UnRepaintManager
        RepaintManager repaintManager = new UnRepaintManager();
        repaintManager.setDoubleBufferingEnabled(false);
        RepaintManager.setCurrentManager(repaintManager);
        size = new Vec(1000,800);
        setSize((int)size.x, (int)size.y);
        insets = this.getInsets();
        int insetWide = insets.left + insets.right;
        int insetTall = insets.top + insets.bottom;
        setSize(getWidth() + insetWide, getHeight() + insetTall);
        textInput = "";
        lastLine ="";
        ((JComponent)getContentPane()).setOpaque(false);
        // Create a frame
        createBufferStrategy(2);
        // set this JFrame's BufferStrategy to our instance variable
        bufferStrategy = getBufferStrategy();
        addWindowListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        isRunning = true;
        gameLoop(); // enter the game loop

       
        
    }
	
	abstract void draw();
	
	private void gameLoop() {
		 BufferedImage drawing = (BufferedImage)this.createImage( (int)size.x, (int)size.y);
		 while(isRunning)
	        {
	 
	            Graphics2D gb = null;
	            try
	            {
	            	
	                
	                gb = (Graphics2D)bufferStrategy.getDrawGraphics();
	                this.g = drawing.createGraphics();
	                this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
	                        RenderingHints.VALUE_ANTIALIAS_ON);
//	           	    this.g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	           	    this.g.setColor(Color.black);
	                this.g.fillRect(0, 0, (int)size.x, (int)size.y);
//	                draw(); // enter the method to draw everything
	               
	                gb.drawImage(drawing, insets.left, insets.top, null);
	                this.g.dispose();
	               
	            }
	            finally
	            {
	            	gb.dispose();
	            }
	            if (!bufferStrategy.contentsLost())
	            {
	                bufferStrategy.show();
	            }
	            Toolkit.getDefaultToolkit().sync(); // prevents possible event queue problems in Linux
	            
	    
	        }
		
	}

	public static int round(double x){
		return (int)(x + 0.5);
	}

	
	
	public void blit(ITexturedPath<textures.sample.Color> texPath){
		BBox b = new BBox(0,0,size.x,size.y);
		texPath.render(g, b);
	}




	public void handleMouseClick(int button) {
	}
	
	public void handleKeyStroke(char key){
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
	
	 /**
     * UnRepaintManager is a RepaintManager that removes the functionality
     * of the original RepaintManager for us so we don't have to worry about
     * Java repainting on it's own.
     */
    class UnRepaintManager extends RepaintManager
    {
        public void addDirtyRegion(JComponent c, int x, int y, int w, int h){}
        public void addInvalidComponent(JComponent invalidComponent){}
        public void markCompletelyDirty(JComponent aComponent){}
        public void paintDirtyRegions(){}    
    }
}
