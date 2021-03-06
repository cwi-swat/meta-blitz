package paths.paths.factory;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import deform.Vec;

import paths.paths.paths.Path;
import paths.paths.paths.compound.ClosedPath;

public class TextFactory {

	public static List<String> getAvailableFontNames() {
		List<String> result = new ArrayList<String>();
		GraphicsEnvironment e = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		java.awt.Font[] fonts = e.getAllFonts(); // Get the fonts
		for (java.awt.Font f : fonts) {
			result.add(f.getFontName());
		}
		return result;
	}

	public static Path text2Paths(String font, String text) {
		try {
			Font f = new Font(font, Font.PLAIN, 50);
			FontRenderContext ctx = new FontRenderContext(
					new AffineTransform(), true, true);
			GlyphVector v = f.createGlyphVector(ctx, text);
			PathIterator p = v.getOutline().getPathIterator(
					new AffineTransform());
			double[] curs = new double[6];
			Vec prev = new Vec(100, 100);
			List<Path> result = new ArrayList<Path>();
			List<Path> curves = new ArrayList<Path>();
			while (!p.isDone()) {
				Vec cur;
				switch (p.currentSegment(curs)) {
				case PathIterator.SEG_CLOSE:
					ClosedPath newPath = PathFactory.createClosedPath(curves);
					result.add(newPath);
					curves = new ArrayList<Path>();
					break;
				case PathIterator.SEG_MOVETO:
					prev = new Vec(curs[0], curs[1]);
					break;
				case PathIterator.SEG_LINETO: {
					cur = new Vec(curs[0], curs[1]);
					if (!cur.isEqError(prev)) {
						curves.add(PathFactory.createLine(prev, cur));
						prev = cur;
					}
					break;
				}
				case PathIterator.SEG_QUADTO: {
					cur = new Vec(curs[2], curs[3]);
					Vec control = new Vec(curs[0], curs[1]);
					curves.add(PathFactory.createQuad(prev, control, cur));
					prev = cur;
					break;
				}
				case PathIterator.SEG_CUBICTO: {
					cur = new Vec(curs[4], curs[5]);
					Vec control1 = new Vec(curs[0], curs[1]);
					Vec control2 = new Vec(curs[2], curs[3]);
					curves.add(PathFactory.createCubic(prev, control1,
							control2, cur));
					prev = cur;
					break;
				}
				}
				p.next();
			}
			return PathFactory.createSet(result);
		} catch (Exception e) {
			throw new Error("Something went wrong during text consturction :"
					+ e.getMessage());
		}
	}

	public static Path text2Paths(String text) {
		return text2Paths(defaultFontName(), text);
	}

	private static String defaultFontName() {
		return UIManager.getDefaults().getFont("Label.font").getName();
	}

}
