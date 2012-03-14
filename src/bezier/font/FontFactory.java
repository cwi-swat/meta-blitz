package bezier.font;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import bezier.composite.Path;
import bezier.composite.Paths;
import bezier.points.Transformation;
import bezier.points.Vec;
import bezier.segment.curve.CubicCurve;
import bezier.segment.curve.Curve;
import bezier.segment.curve.Line;
import bezier.segment.curve.QuadCurve;

public class FontFactory {

	
	public static List<String> getAvailableFontNames(){
		List<String> result = new ArrayList<String>();
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    java.awt.Font[] fonts = e.getAllFonts(); // Get the fonts
	    for (java.awt.Font f : fonts) {
	      result.add(f.getFontName());
	    }
	    return result;
	}
	
	public static Paths text2Paths(String font, String text){
		Font f = new Font(font, Font.PLAIN, 50);
		FontRenderContext ctx = new FontRenderContext(new AffineTransform(), true, true);
		GlyphVector v = f.createGlyphVector(ctx, text);
	    PathIterator p  = v.getOutline().getPathIterator(new AffineTransform());
	    double[] curs = new double[6];
	    Vec prev = new Vec(100,100);
	    List<Path> result = new ArrayList<Path>();
	    List<Curve> curves = new ArrayList<Curve>();
        while(!p.isDone()){
        	 Vec cur;
        	switch(p.currentSegment(curs)){
        		case PathIterator.SEG_CLOSE:
        			result.add(new Path(new ArrayList<Curve>(curves)));
        			curves.clear();

        		break;
        		case PathIterator.SEG_MOVETO:
        			prev = new Vec(curs[0],curs[1]); 
        		break; 
        		case PathIterator.SEG_LINETO: {  
        			cur = new Vec(curs[0],curs[1]);
        			if(!cur.isEqError(prev)){
        			curves.add(new Line(prev, cur));
        			prev = cur;
        			}
        		break;
        		}
        		case PathIterator.SEG_QUADTO: {
        			cur = new Vec(curs[2],curs[3]);
        			Vec control = new Vec(curs[0],curs[1]);
        			curves.add(new QuadCurve(prev, control , cur));
        			prev = cur;
        		break;
        		}
        		case PathIterator.SEG_CUBICTO: {
        			cur = new Vec(curs[4],curs[5]);
        			Vec control1 = new Vec(curs[0],curs[1]);
        			Vec control2 = new Vec(curs[2],curs[3]);
        			curves.add(new CubicCurve(prev, control1 , control2, cur));
        			prev = cur;
        			break;
        		}
        	}
        	p.next();
        }
        
        Paths res = new Paths(result);
        return res.transform(Transformation.id.translate(-res.bbox.x,-res.bbox.y));
	}
	

	public static Paths text2Paths(String text){
		return text2Paths(defaultFontName(),text);
	}
	
	private static String defaultFontName(){
		return UIManager.getDefaults().getFont("Label.font").getName();
	}
//
//	public static void main(String[] argv){
//		for(String s : getAvailableFontNames()){
//			System.out.println(s);
//		}
//		for(Shape s : text2Paths("Arial Bold Italic","Atze")){
//			System.out.print(s.toString());
//		}
//	}
}
