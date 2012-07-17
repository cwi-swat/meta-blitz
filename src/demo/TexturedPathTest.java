package demo;

import static transform.AffineTransformation.id;
import paths.paths.factory.CircleFactory;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import textures.examples.LinearHorizontalGradient;
import textures.interfaces.Sample;
import textures.texturedpaths.AWTPaintTexturedPath;
import textures.texturedpaths.ITexturedPath;
import textures.texturedpaths.TexturedPath;
import transform.AffineTransformation;
import util.Tuple;
import static util.SyntaxSugar.*;

public class TexturedPathTest extends SuperDemoBase{

	public static void main(String[] argv){
		new TexturedPathTest();
	}

	@Override
	public void draw() {
		double size = Math.abs(20 + wheel * 10);
		ITexturedPath t = new TexturedPath(
				CircleFactory.makeCircle(),
				new LinearHorizontalGradient(
						tupleList(
								tupdl(-0.7,new Sample(255,0,0,255)),
								tupdl(0.7,new Sample(0,255,0,255))
						)
				)
			).transform(AffineTransformation.id.scale(size).translate(mouse));
		blit(t);
	}
}
