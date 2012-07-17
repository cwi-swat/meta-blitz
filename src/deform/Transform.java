package deform;

import deform.transform.CompositeTransform;

public abstract class Transform {

	
	public abstract Vec to(Vec from);
	public abstract Vec from(Vec to);
	public BBox affectedArea(){
		return BBox.everything;
	}
	public boolean isAffine(){
		return false;
	}
	
	public Transform compose(Transform rhs){
		return new CompositeTransform(this,rhs);
	}
	
	public Transform getForArea(BBox b){
		return this;
	}
	
}
