package demo;

import static transform.AffineTransformation.id;

import java.awt.GradientPaint;

import demo.awt.DemoBase;
import paths.paths.factory.CircleFactory;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.results.project.BestProject;
import textures.examples.LinearHorizontalGradient;
import textures.examples.RadialGradient;
import textures.examples.TransformedTexture;
import textures.interfaces.ITexture;
import textures.old.generated.ColorsAlpha;
import textures.sample.Color;
import textures.sample.Colors;
import textures.texturedpaths.TexturedPath;
import transform.AffineTransformation;
import transform.Matrix;

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
		ITexture<Color> tex = new TransformedTexture<Color>(
				AffineTransformation.id.scale(size),
				new LinearHorizontalGradient(new Color(255,0,0),new Color(0,255,0)));
		blit(new TexturedPath<Color>(CircleFactory.makeCircle(mouse, size),
				tex));
	}
}
