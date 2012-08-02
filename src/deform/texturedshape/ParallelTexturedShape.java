package deform.texturedshape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import paths.points.oned.Interval;

import deform.BBox;
import deform.Color;
import deform.Texture;
import deform.Transform;
import deform.render.RenderContext;
import deform.render.ScanLiner;
import deform.shapes.Shape;

public class ParallelTexturedShape extends SimpleTexturedShape {

	static final ExecutorService threadPool = Executors.newFixedThreadPool(48);
	
	final int blockSize;
	
	public ParallelTexturedShape(int blockSize,Texture tex, Shape shape) {
		super(tex, shape);
		this.blockSize = blockSize;
	}
	
	static class RenderJob implements Runnable {
		final RenderContext ctx;
		final BBox b;
		final Texture tex;
		
		public RenderJob(RenderContext ctx, BBox b, Texture tex) {
			super();
			this.ctx = ctx;
			this.b = b;
			this.tex = tex;
		}

		public void run() {
			SimpleTexturedShape.setPixelsReal(ctx,b,tex);
		}
	}

	@Override
	void setPixels(final RenderContext ctx, BBox actual, final Texture tex) {
		int nrJobs = (actual.getWidthInt() * actual.getHeightInt()) / blockSize;
		double heightPerJob = (double)actual.getHeightInt() / nrJobs;
		heightPerJob = Math.max(1, Math.floor(heightPerJob));
		nrJobs = (int)Math.ceil(actual.getHeightInt() / heightPerJob);
		List<Future> allRes = new ArrayList<Future>();
//		System.out.println(nrJobs);
		for(int i = 0 ; i < nrJobs ; i++){
			final BBox b = i == nrJobs -1 ? 
					new BBox(actual.xInterval, 
							new Interval(actual.getYInt() + i * heightPerJob, 
								actual.getYInt() + actual.getHeightInt()))
					: new BBox(actual.xInterval, 
					new Interval(actual.getYInt() + i * heightPerJob, 
						actual.getYInt() + (i+1) * heightPerJob));
			allRes.add(threadPool.submit(new RenderJob(ctx, b, tex)));
		}
		for(Future f : allRes){
			try {
				f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}



}
