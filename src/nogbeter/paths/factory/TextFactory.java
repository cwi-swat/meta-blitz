package nogbeter.paths.factory;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.UIManager;

import nogbeter.paths.Path;
import nogbeter.paths.simple.SimplePath;
import nogbeter.points.twod.Vec;


public class TextFactory {

	
	public static List<String> getAvailableFontNames(){
		List<String> result = new ArrayList<String>();
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    java.awt.Font[] fonts = e.getAllFonts(); // Get the fonts
	    for (java.awt.Font f : fonts) {
	      result.add(f.getFontName());
	    }
	    return result;
	}
	
	public static Path text2Paths(String font, String text){
		Font f = new Font(font, Font.PLAIN, 50);
		FontRenderContext ctx = new FontRenderContext(new AffineTransform(), true, true);
		GlyphVector v = f.createGlyphVector(ctx, text);
	    PathIterator p  = v.getOutline().getPathIterator(new AffineTransform());
	    double[] curs = new double[6];
	    Vec prev = new Vec(100,100);
	    Set<Path> result = new HashSet<Path>();
	    List<SimplePath> curves = new ArrayList<SimplePath>();
        while(!p.isDone()){
        	 Vec cur;
        	switch(p.currentSegment(curs)){
        		case PathIterator.SEG_CLOSE:
        			result.add(PathFactory.createClosedPath((List)curves));
        			curves = new ArrayList<SimplePath>();
        		break;
        		case PathIterator.SEG_MOVETO:
        			prev = new Vec(curs[0],curs[1]); 
        		break; 
        		case PathIterator.SEG_LINETO: {  
        			cur = new Vec(curs[0],curs[1]);
        			if(!cur.isEqError(prev)){
        				curves.add(PathFactory.createLine(prev, cur));
        				prev = cur;
        			}
        		break;
        		}
        		case PathIterator.SEG_QUADTO: {
        			cur = new Vec(curs[2],curs[3]);
        			Vec control = new Vec(curs[0],curs[1]);
        			curves.add(PathFactory.createQuad(prev, control , cur));
        			prev = cur;
        		break;
        		}
        		case PathIterator.SEG_CUBICTO: {
        			cur = new Vec(curs[4],curs[5]);
        			Vec control1 = new Vec(curs[0],curs[1]);
        			Vec control2 = new Vec(curs[2],curs[3]);
        			curves.add(PathFactory.createCubic(prev, control1 , control2, cur));
        			prev = cur;
        			break;
        		}
        	}
        	p.next();
        }
        // TODO: nu kunnen letters geen gaten hebben!
        return PathFactory.createSet(result);
	}
	

	public static Path text2Paths(String text){
		return text2Paths(defaultFontName(),text);
	}
	
	private static String defaultFontName(){
		return UIManager.getDefaults().getFont("Label.font").getName();
	}

}
