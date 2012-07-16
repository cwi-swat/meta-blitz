package deform;

public abstract class Transform {

	
	public abstract Vec to(Vec from);
	public abstract Vec from(Vec to);
	public BBox affectedArea(){
		return BBox.everything;
	}
	public boolean isAffine(){
		return false;
	}
	
	
}
