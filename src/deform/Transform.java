package deform;

import deform.transform.CompositeTransform;

public abstract class Transform {

	
	public abstract Vec to(Vec from);
	public abstract Vec from(Vec to);
	
	public BBox transformBBox(BBox b){
		return BBox.everything;
	}
	
	public boolean isAffine(BBox b){
		return false;
	}
	
	public Transform compose(Transform rhs){
		return new CompositeTransform(this,rhs);
	}
}
