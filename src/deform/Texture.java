package deform;

public abstract class Texture {
	
	public abstract Color sample(Vec point);
	public BBox bounds(){
		return BBox.everything;
	}

}
