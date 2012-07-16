package deform;

public abstract class Texture {
	
	public abstract Color sample();
	public BBox bounds(){
		return BBox.everything;
	}

}
