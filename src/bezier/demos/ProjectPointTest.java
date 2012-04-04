package bezier.demos;


import bezier.demos.SetOperations.SetOperationChoices;
import bezier.image.generated.ColorsAlpha;
import bezier.paths.Path;
import bezier.paths.factory.PathFactory;
import bezier.paths.factory.TextFactory;
import bezier.paths.util.PathParameter;
import bezier.points.Transformation;

public class ProjectPointTest extends DemoBase {

	private static final long serialVersionUID = -5092734143006002266L;
	public static boolean dump = false;
	public static void main(String[] args) {
        new ProjectPointTest();
    }

	@Override
	public void draw() {
		Path ts = TextFactory.text2Paths(lastLine);
		ts = ts.transform(Transformation.id.rotate(wheel / 100.0 * Math.PI).scale(2).translate(100,400));
		PathParameter t = ts.project(mouse);
		draw(ts,ColorsAlpha.black,ColorsAlpha.green.lerp(0.5, ColorsAlpha.black));
		if(t != null){
			draw(PathFactory.createLine(mouse, ts.getAt(t)));
			drawOval(ts.getAt(t),10);
		}
	}

}
