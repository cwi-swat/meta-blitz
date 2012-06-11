package transform;

public interface ITransform extends IToTransform, IBackTransform {
	boolean isAffine();
	AffineTransformation getAffine();
}
