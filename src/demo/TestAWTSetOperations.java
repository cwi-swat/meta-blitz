package demo;

import static transform.AffineTransformation.id;

import java.awt.Shape;
import java.awt.geom.Area;

import demo.awt.DemoBase;
import paths.paths.factory.TextFactory;
import paths.paths.paths.Path;
import paths.paths.paths.compound.ClosedPathIndex;

public class TestAWTSetOperations extends DemoBase {
	
	Path<ClosedPathIndex> r;
	private Path<ClosedPathIndex> z;
	public static void main(String[] argv){
		new TestAWTSetOperations();
	}
	
	public TestAWTSetOperations() {
		r = rectangle().transform(id.scale(200).translate(400,400));
		r = TextFactory.text2Paths("atZe").transform(id.scale(5).translate(200, 200));
		System.out.println("r");
//		r = rectangle().transform(id.scale(30).translate(200, 200));
		z = TextFactory.text2Paths("Jurgen").transform(id.scale(5));
//		z = rectangle().transform(id.scale(200));
	}


	@Override
	public void draw() {
		Area rs = new Area(new DummyAWTSHape(r));
		Path<ClosedPathIndex> q =
		z.transform(
				id.scale(1 + wheel / 100).translate(mouse));
		Area zs = new Area(new DummyAWTSHape(q));
		long start = System.currentTimeMillis();
		rs.add(zs);
		System.out.printf("Took %d\n", System.currentTimeMillis() - start);
		g.fill(rs);
		
		
	}
}
