package demo;

import static paths.transform.AffineTransformation.id;

import java.awt.GradientPaint;

import demo.awt.DemoBase;
import paths.paths.factory.CircleFactory;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.paths.PathIndex;
import paths.paths.results.project.BestProject;
import textures.examples.LinearHorizontalGradient;
import textures.old.generated.ColorsAlpha;
import textures.sample.Color;
import textures.sample.Colors;
import textures.texturedpaths.TexturedPath;

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

		blit(new TexturedPath<Color>(CircleFactory.makeCircle(mouse, 20 + wheel * 10),
				new LinearHorizontalGradient(Colors.red,Colors.green)));
	}
}
