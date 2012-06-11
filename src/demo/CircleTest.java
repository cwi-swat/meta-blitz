package demo;

import paths.paths.factory.CircleFactory;
import paths.paths.paths.Path;

public class CircleTest extends DemoBase{
	public static void main(String[] argv){
		new CircleTest();
	}

	@Override
	public void draw() {
//
		Path c = CircleFactory.makeCircle(mouse, wheel * 10 + 100);
		draw(c);
	}
}
