package bezier.demos;

import bezier.composite.Path;
import bezier.composite.Paths;
import bezier.composite.TPaths;
import bezier.image.generated.ColorsAlpha;
import bezier.paths.factory.FontFactory;
import bezier.paths.simple.Line;
import bezier.points.Transformation;
import bezier.util.STuple;

public class ProjectTest extends DemoBase {

	private static final long serialVersionUID = -5092734143006002266L;

	public static void main(String[] args) {
        new ProjectTest();
    }
	
	@Override
	public void draw() {
		Paths ts = FontFactory.text2Paths(lastLine);
		Paths t2 = FontFactory.text2Paths("Atze");
		ts = ts.transform(Transformation.id.scale(5).rotate(wheel / 100.0 * Math.PI).translate(400,400)).makeMonotomous();
		t2 = t2.transform(Transformation.id.scale(5).translate(mouse)).makeMonotomous();
		STuple<TPaths> tp = ts.project(t2);


		draw(ts,ColorsAlpha.black,ColorsAlpha.green.lerp(0.5, ColorsAlpha.black));
		draw(t2,ColorsAlpha.black,ColorsAlpha.green.lerp(0.5, ColorsAlpha.black));
		if(tp != null){
			draw(new Line(ts.getAt(tp.l),t2.getAt(tp.r)));
		}
	}

}
