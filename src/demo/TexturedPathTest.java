package demo;

import static transform.AffineTransformation.id;
import paths.paths.factory.CircleFactory;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import textures.examples.LinearHorizontalGradient;
import textures.sample.Color;
import textures.texturedpaths.TexturedPath;
import transform.AffineTransformation;

public class TexturedPathTest extends DemoBase{

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
		TexturedPath<Color> t = new TexturedPath<Color>(
				CircleFactory.makeCircle(),
				new LinearHorizontalGradient(new Color(255,0,0),new Color(0,255,0)).transform(id.translate(-0.5,0)))
				.transform(AffineTransformation.id.scale(size).translate(mouse));
		blit(t);
	}
}
