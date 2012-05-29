package nogbeter.paths.factory;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.UIManager;

import bezier.composite.Shape;
import bezier.util.Tuple;

import nogbeter.paths.Path;
import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.ClosedPath;
import nogbeter.paths.compound.ShapeIndex;
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
	    List<Path> result = new ArrayList<Path>();
	    Set<Path> unkownHoles = new HashSet<Path>();
	    List<List<Path>> knownHoles = new ArrayList<List<Path>>();
	    List<SimplePath> curves = new ArrayList<SimplePath>();
        while(!p.isDone()){
        	 Vec cur;
        	switch(p.currentSegment(curs)){
        		case PathIterator.SEG_CLOSE:
        			ClosedPath newPath = PathFactory.createClosedPathUnsafe((List)curves);
        			System.out.printf("Creating: %s\n", newPath);
        			if(newPath.isDefindedClockwise()){
        				knownHoles.add(new ArrayList<Path>(4));
        				result.add(newPath);
        				determineHoles(result,unkownHoles, knownHoles);
        			} else {
        				unkownHoles.add(newPath);
        			}
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
        if(unkownHoles.size() > 0){
        	throw new Error("Floating hole!" + unkownHoles.iterator().next());
        }
       
        return PathFactory.createSet(createShapes(result,knownHoles));
	}
	


	private static List<Path> createShapes(List<Path> borders,
			List<List<Path>> knownHoles) {
		List<Path> res = new ArrayList<Path>(borders.size());
		for(int i = 0 ; i < borders.size() ; i++){
			res.add(PathFactory.createShape(borders.get(i), knownHoles.get(i)));
		}
		return res;
	}

	private static void determineHoles(List<Path> result,
			Set<Path> unkownHoles, List<List<Path>> knownHoles) {
		Iterator<Path> hit = unkownHoles.iterator();
		while(hit.hasNext()){
			Path hole = hit.next();
			for(int i = result.size()-1; i >= 0 ; i--){
				Path p = result.get(i);
				if(p.contains(hole)){
					knownHoles.get(i).add(hole);
					hit.remove();
					break;
				}
			}
		}
		
	}
	public static Path text2Paths(String text){
		return text2Paths(defaultFontName(),text);
	}
	
	private static String defaultFontName(){
		return UIManager.getDefaults().getFont("Label.font").getName();
	}

}
