package deform.library;
import deform.Transform;
import static deform.Library.*;
import deform.Vec;

public class Camera{

	Transform real;
	
	double zoom;
	double shrink;
	Vec leftTop;
	Vec centerWorld;
	Vec ref;
	public Camera(){
		this.leftTop = vec(0,0);
		real = translate(vec(0,0));
		zoom = 1.0;
		shrink = 1.0;
	}
	

	public void zoom(Vec zoomOnWorld, double zoomDelta){
		
		double factor = 1/zoomDelta;
		Vec zoomLocal = zoomOnWorld.mul(shrink);
		this.leftTop= leftTop.add(zoomLocal.mul(1-factor));
		shrink*=factor;
		zoom*=zoomDelta;
		this.real = scale(zoom).compose(translate(leftTop.negate()));
		
	}
	
	public void move(Vec delta){

			leftTop = leftTop.add(delta.mul(shrink));
			this.real = scale(zoom).compose(translate(leftTop.negate()));
	}
	
	public Transform getTransform(){
		return real;
	}
	
}
