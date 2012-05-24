package nogbeter.demo.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import static nogbeter.paths.factory.PathFactory.createAppends;
import static nogbeter.paths.factory.PathFactory.createLine;


import nogbeter.demo.DummyAWTSHape;
import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.factory.PathFactory;
import nogbeter.paths.iterators.AWTPathIterator;
import nogbeter.points.twod.Vec;

import bezier.image.generated.ColorsAlpha;
import bezier.image.generated.SampleInstances.Sample4;



public abstract class DemoBase implements KeyListener, MouseMoveListener, PaintListener, MouseWheelListener{
	private static final long serialVersionUID = 1478770999111265074L;
	public Vec size;
	public Vec mouse;
	public String textInput;
	public String lastLine;
	public double wheel;
	Shell shell;
	GC g;
	Device device;
	private Canvas canvas;

	
	public DemoBase()  {
        mouse = new Vec(0,0);
        size = new Vec(1000,800);

        textInput = "";
        lastLine ="";
        // Create a frame
    }
	
	public void run(){
		final Display display = new Display ();
		final Shell shell = new Shell (display, SWT.SHELL_TRIM | SWT.NO_BACKGROUND);
		this.shell = shell;
		 shell.setLayout(new FillLayout());
		this.canvas = new Canvas (shell, SWT.NO_BACKGROUND);
		
		 canvas.addPaintListener(this);
		 canvas.addKeyListener(this);
		 canvas.addMouseMoveListener(this);
		 shell.setSize (1000,800);
//		 canvas.redraw();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		display.dispose ();
		System.out.println("Terminate");
	}
	
	Color getColor(Sample4 sample){
		return new Color(device, round(sample.a * 255),round(sample.b * 255),round(sample.c * 255));
	}
	
	public static int round(double x){
		return (int)(x + 0.5);
	}

	public void fillOval(Vec location, double w){
		fillOval(location, w, w);
	}
	
	public void fillOval(Vec location, double w, double h){
		fillOval(location, w, h,ColorsAlpha.black);
	}
	
	public void fillOval(Vec location, double w, Sample4 inside){
		fillOval(location, w, w, inside);
	}
	
	public void fillOval(Vec location, double w, double h, Sample4 inside){
		drawOval(location, w, h,ColorsAlpha.transparent,inside);
	}
	
	public void drawOval(Vec location, double w){
		drawOval(location, w,w);
	}
	
	public void drawOval(Vec location, double w, double h){
		drawOval(location, w, h, ColorsAlpha.black);
	}
	
	public void drawOval(Vec location, double w, double h, Sample4 border){
		drawOval(location, w, h, border,ColorsAlpha.black);
	}
	
	public void drawOval(Vec location, double w, Sample4 border){
		drawOval(location, w, border, ColorsAlpha.transparent);
	}
	
	public void drawOval(Vec location, double w, Sample4 border, Sample4 inside){
		drawOval(location, w,w, border, inside);
	}
	
	public void drawOval(Vec location, double w, double h, Sample4 border, Sample4 inside){
		if(inside.last() > 0.0 ){
			Color c = getColor(inside);
			setFGColor(c,inside.d);
			g.fillOval(round(location.x)-round(w/2.0),round(location.y)-round(h/2.0), round(w), round(h));
			c.dispose();
		}
		if(border.last() > 0.0){
			Color c = getColor(border);
			setBGColor(c,border.d);
			g.drawOval(round(location.x)-round(w/2.0),round(location.y)-round(h/2.0), round(w), round(h));
			c.dispose();
		}
	}
	


	private void setFGColor(Color c, double d) {
		g.setForeground(c);
		g.setAlpha(round(d * 255));
	}
	

	private void setBGColor(Color c, double d) {
		g.setBackground(c);
		g.setAlpha(round(d * 255));
	}


	public void drawLine(Vec at, Vec mouse) {
		drawLine(at, mouse,ColorsAlpha.black);
	}

	public void drawLine(Vec at, Vec mouse, Sample4 color) {
		Color c = getColor(color);
		setFGColor(c,color.d);
		g.drawLine(round(at.x), round(at.y),round(mouse.x), round(mouse.y));
		c.dispose();
	}
	
	public void fillRect(Vec location, double w){
		fillRect(location, w, w);
	}
	
	public void fillRect(Vec location, double w, double h){
		fillRect(location, w, h,ColorsAlpha.black);
	}
	
	public void fillRect(Vec location, double w, Sample4 inside){
		fillRect(location, w, w, inside);
	}
	
	public void fillRect(Vec location, double w, double h, Sample4 inside){
		drawRect(location, w, h,ColorsAlpha.transparent,inside);
	}
	
	public void drawRect(Vec location, double w, double h){
		drawRect(location, w, h, ColorsAlpha.black);
	}
	
	public void drawRect(Vec location, double w, double h, Sample4 border){
		drawRect(location, w, h, border,ColorsAlpha.transparent);
	}
	
	public void drawRect(Vec location, double w, Sample4 border){
		drawRect(location, w, border, ColorsAlpha.transparent);
	}
	
	public void drawRect(Vec location, double w, Sample4 border, Sample4 inside){
		drawRect(location, w,w, border, inside);
	}
	
	public void drawRect(Vec location, double w, double h, Sample4 border, Sample4 inside){
		
		if(inside.last() > 0.0){
			Color c = getColor(inside);
			setFGColor(c,inside.d);
			g.fillRectangle(round(location.x)-round(w/2.0),round(location.y)-round(h/2.0), round(w), round(h));
			c.dispose();
		}
		if(inside.last() > 0.0){
			Color c = getColor(border);
			setBGColor(c,border.d);
			g.drawRectangle(round(location.x)-round(w/2.0),round(location.y)-round(h/2.0), round(w), round(h));
			c.dispose();
		}
	}
	
	public void fill(Path p){
		draw(p,ColorsAlpha.transparent,ColorsAlpha.black);
	}
	
	public void fill(Path p,Sample4 inside){
		draw(p,ColorsAlpha.transparent,inside);
	}
	
	public void draw(Path p){
		draw(p,ColorsAlpha.black);
	}
	
	public void draw(Path p, Sample4 border){
		draw(p,border,ColorsAlpha.transparent);
	}
	
	public void draw(Path p, Sample4 border, Sample4 inside){
		org.eclipse.swt.graphics.Path sp = new org.eclipse.swt.graphics.Path(device);
		SWTPathFactory.makeSWTPath(sp, p);
		if(inside.last() > 0){
			Color c = getColor(inside);
			setBGColor(c,inside.d);
			g.fillPath(sp);
			c.dispose();
		}
		if(border.last() > 0){
			Color c = getColor(border);
			setFGColor(c,border.d);
			g.drawPath(sp);
			c.dispose();
		}
		sp.dispose();
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
	
	
	public Path<PathIndex> rectangle(){
		Vec a = new Vec(-1,-1);
		Vec b = new Vec(1,-1);
		Vec c = new Vec(1,1);
		Vec d = new Vec(-1,1);
//		Vec e = new Vec(150,50);
		return PathFactory.createClosedPath(createLine(a,b), createLine(b,c), createLine(c,d),createLine(d,a));
	}

	@Override
	public void paintControl(PaintEvent e) {
		device = e.gc.getDevice();
		Image image = new Image(shell.getDisplay(), canvas.getBounds());
		GC gcImage = new GC(image);
		gcImage.setAdvanced(true);
		Rectangle rect = shell.getClientArea ();
		size = new Vec(rect.width,rect.height);
		gcImage.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		gcImage.fillRectangle(0, 0, rect.width, rect.height);
		this.g = gcImage;
		draw();
		e.gc.drawImage(image, 0, 0);
		gcImage.dispose();
		image.dispose();
		e.gc.dispose();
	}

	@Override
	public void mouseMove(MouseEvent e) {
		mouse = new Vec(e.x,e.y);
		canvas.redraw();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		handleKeyStroke(e.character);
		if(e.character == '\b'){
			if(!textInput.isEmpty()){
				textInput = textInput.substring(0, textInput.length()-1);
			}
			if(!lastLine.isEmpty()){
				lastLine = lastLine.substring(0, lastLine.length()-1);
			}
		}
		else if(e.character == '\n'){
			lastLine = "";
			textInput+=e.character;
		} else {
			lastLine+=e.character;
			textInput+=e.character;
		}
		canvas.redraw();
		
	}

	void handleKeyStroke(char character) {}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseScrolled(MouseEvent e) {
		wheel += e.count;
		shell.redraw();
	}
	

}


//
//public class DemoBase {
//
//public static void main (String [] args) {
//	final Display display = new Display ();
//	final Shell shell = new Shell (display, SWT.SHELL_TRIM | SWT.NO_BACKGROUND);
//	shell.setLayout(new FillLayout ());
//	shell.addListener (SWT.Paint, new Listener () {
//		public void handleEvent (Event e) {
//			GC gc = e.gc;
//			gc.setAdvanced(true);
//			gc.setInterpolation(SWT.HIGH);
//			Rectangle rect = shell.getClientArea ();
//			Color color1 = new Color (display, 234, 246, 253);
//			Color color2 = new Color (display, 217, 240, 252);
//			gc.setForeground(color1);
//			gc.setBackground(color2);
//			gc.fillGradientRectangle (rect.x, rect.y, rect.width, rect.height / 2, true);
//			color1.dispose ();
//			color2.dispose ();
//			Color color3 = new Color (display, 190, 230, 253);
//			Color color4 = new Color (display, 167, 217, 245);
//			gc.setForeground(color3);
//			gc.setBackground(color4);
//			gc.fillGradientRectangle (rect.x, rect.y + (rect.height / 2), rect.width, rect.height / 2 + 1, true);
//			color3.dispose ();
//			color4.dispose ();
//			
//		}
//	});
//	shell.setSize (200, 64);
//	shell.open ();
//	while (!shell.isDisposed ()) {
//		if (!display.readAndDispatch ())
//			display.sleep ();
//	}
//	display.dispose ();
//}
//}
