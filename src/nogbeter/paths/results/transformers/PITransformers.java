package nogbeter.paths.results.transformers;

import nogbeter.paths.PathIndex;
import nogbeter.paths.compound.SetIndex;
import nogbeter.paths.compound.SplitIndex;

public class PITransformers{

	public static IPathIndexTransformer unit = new UnitPITransformer();
	public static IPathIndexTransformer<SplitIndex> splitLeft = new SplitPITransformer(SplitIndex.SplitChoice.Left);
	public static IPathIndexTransformer<SplitIndex> splitRight = new SplitPITransformer(SplitIndex.SplitChoice.Right);
	public static IPathIndexTransformer<SetIndex> setTrans(int i){
		return new SetPITransformer(i);
	}
	
	public static class UnitPITransformer implements IPathIndexTransformer<PathIndex> {
		public PathIndex transform(PathIndex p){
			return p;
		}
		public boolean doesNothing(){
			return true;
		}
	}
	
	public static class SplitPITransformer implements IPathIndexTransformer<SplitIndex> {
		
		final SplitIndex.SplitChoice choice;
		
		public SplitPITransformer(SplitIndex.SplitChoice choice) {
			this.choice = choice;
		}
		public SplitIndex transform(PathIndex p){
			return new SplitIndex(choice, p);
		}
		public boolean doesNothing(){
			return false;
		}
	}
	
	public static class SetPITransformer implements IPathIndexTransformer<SetIndex> {
		
		final int choice;
		
		public SetPITransformer(int choice) {
			this.choice = choice;
		}
		public SetIndex transform(PathIndex p){
			return new SetIndex(choice, p);
		}
		public boolean doesNothing(){
			return false;
		}
	}
}
