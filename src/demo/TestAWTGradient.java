package demo;

import static transform.AffineTransformation.id;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;

import deform.Vec;

import paths.paths.factory.CircleFactory;
import paths.paths.paths.Path;
import textures.examples.LinearHorizontalGradient;
import textures.texturedpaths.TexturedPath;
import transform.AffineTransformation;

public class TestAWTGradient extends DemoBase{
	public static void main(String[] argv){
		new TestAWTGradient();
	}
	
	@Override
	public void draw() {
		double size = Math.abs(20 + wheel * 10);
		Path p = CircleFactory.makeCircle()
				.transform(AffineTransformation.id.scale(size).translate(mouse));
		Vec half = new Vec(size/2,0);
		Vec lv = mouse.sub(half);
		Vec rv = mouse.add(half);
		Point2D l = new Point2D.Double(lv.x,0);
		Point2D r = new Point2D.Double(rv.x,0);
		
		g.setPaint(new LinearGradientPaint(l,r, new float[]{0.0f,1.0f}, new Color[]{Color.green, Color.red}));
		g.fill(new DummyAWTSHape(p));
		
	}
	

}
