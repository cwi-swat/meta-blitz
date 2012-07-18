package deform.tests;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

import deform.BBox;
import deform.Combinators;
import deform.Vec;
import deform.texturedshape.TexturedShape;

import textures.interfaces.ITexture;


import java.util.Random;

public abstract class DemoBase extends JFrame implements KeyListener,MouseWheelListener,MouseListener, MouseMotionListener, WindowListener //, ComponentListener
{
	public Vec size;
    private Insets insets;
    private BufferStrategy bufferStrategy;
    private boolean isRunning;
    private boolean isFpsLimited;
    private BufferedImage drawing;
    private int fps;
    private Graphics2D lg;
	public Vec mouse;
	public String textInput;
	public String lastLine;
	public double wheel;
     
    
    public DemoBase()
    {
        super();
        mouse = new Vec(0,0);

        textInput = "";
        lastLine ="";
        size = new Vec(1400,1000);
        setTitle("Superawesome demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setIgnoreRepaint(true); // don't need Java painting for us
        setResizable(false); // don't want someone resizing our "game" for us
        ((JComponent)getContentPane()).addMouseMotionListener(this);
       ((JComponent)getContentPane()).addMouseListener(this);
       ((JComponent)getContentPane()). addMouseWheelListener(this);
        addKeyListener(this);
        // set up our UnRepaintManager
        RepaintManager repaintManager = new UnRepaintManager();
        repaintManager.setDoubleBufferingEnabled(false);
        RepaintManager.setCurrentManager(repaintManager);
                
        // Correct change width and height of window so that the available
        // screen space actually cooresponds to what is passed, another
        // method is the Canvas object + pack()
        setSize((int)size.x, (int)size.y);
        insets = this.getInsets();
        int insetWide = insets.left + insets.right;
        int insetTall = insets.top + insets.bottom;
        setSize(getWidth() + insetWide, getHeight() + insetTall);
    

        
        isFpsLimited = false;
        
        // The JFrame's content pane's background will paint over any other graphics
        // we painted ourselves, so let's turn it transparent
        ((JComponent)getContentPane()).setOpaque(false);
       
        
        // create a buffer strategy using two buffers
        createBufferStrategy(2);
        // set this JFrame's BufferStrategy to our instance variable
        bufferStrategy = getBufferStrategy();
        
        isRunning = true;
        gameLoop(); // enter the game loop
    }
    
    /**
     * Method containing the game's loop.
     * Each iteration of the loop updates all animations and sprite locations
     * and draws the graphics to the screen
     */
    public void gameLoop()
    {
        long oldTime = System.nanoTime();
        long nanoseconds = 0;
        int frames = 0;
        fps = 0;
        
        // create a image to draw to to match 0,0 up correctly.
        drawing = (BufferedImage) this.createImage(getWidth(),getHeight());
        
        while(isRunning)
        {
            // relating to updating animations and calculating FPS
            long elapsedTime = System.nanoTime() - oldTime;
            oldTime = oldTime + elapsedTime; //update for the next loop iteration
            nanoseconds = nanoseconds + elapsedTime;
            frames = frames + 1;
            if (nanoseconds >= 1000000000)
            {
                fps = frames;
                nanoseconds = nanoseconds - 1000000000;
                frames = 0;
            }
            // enter the method to update everything
            update(elapsedTime);
            
            // related to drawing
            Graphics2D g = null;
            try
            {
                g = (Graphics2D)bufferStrategy.getDrawGraphics();
                this.lg = g;
                g.setBackground(Color.black);
                g.clearRect(0, 0, (int)size.x, (int)size.y);
                draw(); // enter the method to draw everything
                
            }
            finally
            {
                g.dispose();
            }
            if (!bufferStrategy.contentsLost())
            {
                bufferStrategy.show();
            }
            Toolkit.getDefaultToolkit().sync(); // prevents possible event queue problems in Linux
            
            if (isFpsLimited)
            {
                // sleep to let the processor handle other programs running
                try
                {
                    // comment this out to not limit the FPS
                    Thread.sleep(10);
                }
            catch (Exception e){};
            }
        }
    }
    
    /**
     * Updates any objects that need to know how much time has elapsed
     * to update animations and locations
     * @param elapsedTime How much time has elapsed since the last update
     */
    public void update(long elapsedTime)
    {
    }
    

    abstract void draw();
    /**
     * Draws the whole program, including all animations and Swing components
     * @param g The program's window's graphics object to draw too
     */
    public void draw(Graphics2D g)
    {
        Graphics2D drawingBoard = drawing.createGraphics();
        drawingBoard.setColor(Color.black);
        drawingBoard.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                      RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        drawingBoard.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                      RenderingHints.VALUE_ANTIALIAS_ON);
        // draw over it to create a blank background again, (or you could draw
        // a background image if you had one
        drawingBoard.fillRect(0, 0, drawing.getWidth(), drawing.getHeight());
        this.lg = drawingBoard;
        draw();
        // now draw everything to drawingBoard, location 0,0 will be top left corner
        // within the borders of the window

        drawingBoard.setColor(Color.WHITE);
        drawingBoard.drawString("FPS: " + fps, 0, drawingBoard.getFont().getSize());
        // NOTE: this will now cap the FPS (frames per second), of the program to
        // a max of 100 (1000 nanoseconds in a second, divided by 10 nanoseconds
        // of rest per update = 100 updates max).
    
        // now draw the drawing board to correct area of the JFrame's buffer
        g.drawImage(drawing, insets.left, insets.top, null);
        
        drawingBoard.dispose();
    }
    
    public void draw(TexturedShape s){
    	Combinators.render(lg, new BBox(0,0,size.x,size.y), s);
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
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		handleMouseClick(e.getButton());
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