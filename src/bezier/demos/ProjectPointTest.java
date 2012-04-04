package bezier.demos;

import bezier.composite.Path;
import bezier.composite.Paths;
import bezier.composite.TPaths;
import bezier.demos.SetOperations.SetOperationChoices;
import bezier.image.generated.ColorsAlpha;
import bezier.paths.factory.FontFactory;
import bezier.points.Transformation;

public class ProjectPointTest extends DemoBase {

	private static final long serialVersionUID = -5092734143006002266L;
	public static boolean dump = false;
	public static void main(String[] args) {
        new ProjectPointTest();
    }
	
	@Override
	public void handleKeyStroke(char key){
		if(key == 'r'){
			dump = true;
		}
	}
	
	@Override
	public void draw() {
		Paths ts = FontFactory.text2Paths(lastLine);
		ts = ts.transform(Transformation.id.scale(5).rotate(wheel / 100.0 * Math.PI).translate(100,100)).makeMonotomous();
		TPaths t = ts.project(mouse);
//		System.out.printf("%f\n",tt);

		draw(ts,ColorsAlpha.black,ColorsAlpha.green.lerp(0.5, ColorsAlpha.black));
		if(t != null){
			drawOval(ts.getAt(t),10);
		}
	}

}
