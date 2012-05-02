package bezier.demos;

import nogbeter.transform.AffineTransformation;
import bezier.image.generated.ColorsAlpha;
import bezier.paths.Path;
import bezier.paths.factory.TextFactory;
import bezier.paths.simple.Line;
import bezier.paths.util.PathParameter;
import bezier.util.STuple;

public class ProjectTest extends DemoBase {

	private static final long serialVersionUID = -5092734143006002266L;

	public static void main(String[] args) {
        new ProjectTest();
    }
	
	@Override
	public void draw() {
		Path ts = TextFactory.text2Paths(lastLine);
		Path t2 = TextFactory.text2Paths("Atze");
		ts = ts.transform(AffineTransformation.id.scale(5).rotate(wheel / 100.0 * Math.PI).translate(400,400));
		t2 = t2.transform(AffineTransformation.id.scale(5).translate(mouse));
		STuple<PathParameter> tp = ts.project(t2);
		if(tp != null){
			draw(new Line(ts.getAt(tp.l),t2.getAt(tp.r)));
		}


		draw(ts,ColorsAlpha.black,ColorsAlpha.green.lerp(0.5, ColorsAlpha.black));
		draw(t2,ColorsAlpha.black,ColorsAlpha.green.lerp(0.5, ColorsAlpha.black));
		
	}

}
