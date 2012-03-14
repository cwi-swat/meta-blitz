package bezier.demos;

import bezier.composite.Path;
import bezier.composite.Paths;
import bezier.composite.TPaths;
import bezier.font.FontFactory;
import bezier.image.old.Sample;
import bezier.points.Transformation;
import bezier.segment.curve.Line;
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


		draw(ts,Sample.BLACK,Sample.GREEN.interpolate(0.5, Sample.BLACK));
		draw(t2,Sample.BLACK,Sample.GREEN.interpolate(0.5, Sample.BLACK));
		if(tp != null){
			draw(new Line(ts.getAt(tp.l),t2.getAt(tp.r)));
		}
	}

}
