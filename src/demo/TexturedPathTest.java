package demo;

import static transform.AffineTransformation.id;
import paths.paths.factory.CircleFactory;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import textures.examples.LinearHorizontalGradient;
import textures.interfaces.ITexturedPath;
import textures.sample.Color;
import textures.texturedpaths.AWTPaintTexturedPath;
import textures.texturedpaths.TexturedPath;
import transform.AffineTransformation;
import util.Tuple;
import static util.SyntaxSugar.*;

public class TexturedPathTest extends SuperDemoBase{

	public static void main(String[] argv){
		new TexturedPathTest();
	}
	
	private Path r;
	
	public TexturedPathTest() {
		r = TextFactory.text2Paths("wso").transform(id.scale(5).translate(400, 400));
	}
	
	@Override
	public void draw() {
		double size = Math.abs(20 + wheel * 10);
		ITexturedPath<Color> t = new AWTPaintTexturedPath<Color>(
				CircleFactory.makeCircle(),
				new LinearHorizontalGradient(
						tupleList(
								tupdl(-0.7,new Color(255,0,0)),
								tupdl(0.7,new Color(0,255,0))
						)
				)
			).transform(AffineTransformation.id.scale(size).translate(mouse));
		blit(t);
	}
}
