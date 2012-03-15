package bezier.image.generated;
import bezier.image.BoundedImage;
import bezier.image.Image;
import bezier.image.generated.SampleInstances.*;
public class ImageSampleOpers{
		
	
		
		public static Image<Sample2> append11(final Image<Sample1> l, final Image<Sample1> r){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg11 implements BoundedImage<Sample2>{
		int x, y, w, h;
		Image<Sample1> l;
		Image<Sample1> r;
		
		ConcatBoundedImg11(int x, int y, int w, int h, Image<Sample1> l, Image<Sample1> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample2 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample2(0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample2> appendb11(final BoundedImage<Sample1> l, final Image<Sample1> r){
		return new ConcatBoundedImg11(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample2> append1b1(final Image<Sample1> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg11(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample2> appendb1b1(final BoundedImage<Sample1> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg11(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	


		
		public static Image<Sample1> del2_2(final Image<Sample2> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del2();
				}
			};
		}
		
		public static Image<Sample1> delb2_2(final BoundedImage<Sample2> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del2();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del2_1(final Image<Sample2> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del1();
				}
			};
		}
		
		public static Image<Sample1> delb2_1(final BoundedImage<Sample2> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del1();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
	
		
		public static Image<Sample3> append12(final Image<Sample1> l, final Image<Sample2> r){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg12 implements BoundedImage<Sample3>{
		int x, y, w, h;
		Image<Sample1> l;
		Image<Sample2> r;
		
		ConcatBoundedImg12(int x, int y, int w, int h, Image<Sample1> l, Image<Sample2> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample3 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample3(0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample3> appendb12(final BoundedImage<Sample1> l, final Image<Sample2> r){
		return new ConcatBoundedImg12(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample3> append1b2(final Image<Sample1> l, final BoundedImage<Sample2> r){
		return new ConcatBoundedImg12(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample3> appendb1b2(final BoundedImage<Sample1> l, final BoundedImage<Sample2> r){
		return new ConcatBoundedImg12(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample3> append21(final Image<Sample2> l, final Image<Sample1> r){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg21 implements BoundedImage<Sample3>{
		int x, y, w, h;
		Image<Sample2> l;
		Image<Sample1> r;
		
		ConcatBoundedImg21(int x, int y, int w, int h, Image<Sample2> l, Image<Sample1> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample3 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample3(0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample3> appendb21(final BoundedImage<Sample2> l, final Image<Sample1> r){
		return new ConcatBoundedImg21(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample3> append2b1(final Image<Sample2> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg21(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample3> appendb2b1(final BoundedImage<Sample2> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg21(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	


		
		public static Image<Sample2> del3_3(final Image<Sample3> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del3();
				}
			};
		}
		
		public static Image<Sample2> delb3_3(final BoundedImage<Sample3> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del3();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del3_12(final Image<Sample3> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del12();
				}
			};
		}
		
		public static Image<Sample1> delb3_12(final BoundedImage<Sample3> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del12();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del3_13(final Image<Sample3> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del13();
				}
			};
		}
		
		public static Image<Sample1> delb3_13(final BoundedImage<Sample3> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del13();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del3_2(final Image<Sample3> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del2();
				}
			};
		}
		
		public static Image<Sample2> delb3_2(final BoundedImage<Sample3> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del2();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del3_23(final Image<Sample3> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del23();
				}
			};
		}
		
		public static Image<Sample1> delb3_23(final BoundedImage<Sample3> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del23();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del3_1(final Image<Sample3> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1();
				}
			};
		}
		
		public static Image<Sample2> delb3_1(final BoundedImage<Sample3> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
	
		
		public static Image<Sample4> append13(final Image<Sample1> l, final Image<Sample3> r){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg13 implements BoundedImage<Sample4>{
		int x, y, w, h;
		Image<Sample1> l;
		Image<Sample3> r;
		
		ConcatBoundedImg13(int x, int y, int w, int h, Image<Sample1> l, Image<Sample3> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample4 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample4(0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample4> appendb13(final BoundedImage<Sample1> l, final Image<Sample3> r){
		return new ConcatBoundedImg13(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample4> append1b3(final Image<Sample1> l, final BoundedImage<Sample3> r){
		return new ConcatBoundedImg13(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample4> appendb1b3(final BoundedImage<Sample1> l, final BoundedImage<Sample3> r){
		return new ConcatBoundedImg13(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample4> append22(final Image<Sample2> l, final Image<Sample2> r){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg22 implements BoundedImage<Sample4>{
		int x, y, w, h;
		Image<Sample2> l;
		Image<Sample2> r;
		
		ConcatBoundedImg22(int x, int y, int w, int h, Image<Sample2> l, Image<Sample2> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample4 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample4(0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample4> appendb22(final BoundedImage<Sample2> l, final Image<Sample2> r){
		return new ConcatBoundedImg22(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample4> append2b2(final Image<Sample2> l, final BoundedImage<Sample2> r){
		return new ConcatBoundedImg22(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample4> appendb2b2(final BoundedImage<Sample2> l, final BoundedImage<Sample2> r){
		return new ConcatBoundedImg22(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample4> append31(final Image<Sample3> l, final Image<Sample1> r){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg31 implements BoundedImage<Sample4>{
		int x, y, w, h;
		Image<Sample3> l;
		Image<Sample1> r;
		
		ConcatBoundedImg31(int x, int y, int w, int h, Image<Sample3> l, Image<Sample1> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample4 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample4(0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample4> appendb31(final BoundedImage<Sample3> l, final Image<Sample1> r){
		return new ConcatBoundedImg31(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample4> append3b1(final Image<Sample3> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg31(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample4> appendb3b1(final BoundedImage<Sample3> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg31(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	


		
		public static Image<Sample2> del4_34(final Image<Sample4> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del34();
				}
			};
		}
		
		public static Image<Sample2> delb4_34(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del34();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del4_124(final Image<Sample4> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del124();
				}
			};
		}
		
		public static Image<Sample1> delb4_124(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del124();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del4_134(final Image<Sample4> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del134();
				}
			};
		}
		
		public static Image<Sample1> delb4_134(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del134();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del4_24(final Image<Sample4> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del24();
				}
			};
		}
		
		public static Image<Sample2> delb4_24(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del24();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del4_234(final Image<Sample4> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del234();
				}
			};
		}
		
		public static Image<Sample1> delb4_234(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del234();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del4_14(final Image<Sample4> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del14();
				}
			};
		}
		
		public static Image<Sample2> delb4_14(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del14();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del4_4(final Image<Sample4> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del4();
				}
			};
		}
		
		public static Image<Sample3> delb4_4(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del4();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del4_3(final Image<Sample4> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del3();
				}
			};
		}
		
		public static Image<Sample3> delb4_3(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del3();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del4_12(final Image<Sample4> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12();
				}
			};
		}
		
		public static Image<Sample2> delb4_12(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del4_13(final Image<Sample4> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del13();
				}
			};
		}
		
		public static Image<Sample2> delb4_13(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del13();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del4_2(final Image<Sample4> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2();
				}
			};
		}
		
		public static Image<Sample3> delb4_2(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del4_23(final Image<Sample4> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del23();
				}
			};
		}
		
		public static Image<Sample2> delb4_23(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del23();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del4_1(final Image<Sample4> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1();
				}
			};
		}
		
		public static Image<Sample3> delb4_1(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del4_123(final Image<Sample4> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del123();
				}
			};
		}
		
		public static Image<Sample1> delb4_123(final BoundedImage<Sample4> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del123();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
	
		
		public static Image<Sample5> append14(final Image<Sample1> l, final Image<Sample4> r){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg14 implements BoundedImage<Sample5>{
		int x, y, w, h;
		Image<Sample1> l;
		Image<Sample4> r;
		
		ConcatBoundedImg14(int x, int y, int w, int h, Image<Sample1> l, Image<Sample4> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample5 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample5(0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample5> appendb14(final BoundedImage<Sample1> l, final Image<Sample4> r){
		return new ConcatBoundedImg14(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample5> append1b4(final Image<Sample1> l, final BoundedImage<Sample4> r){
		return new ConcatBoundedImg14(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample5> appendb1b4(final BoundedImage<Sample1> l, final BoundedImage<Sample4> r){
		return new ConcatBoundedImg14(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample5> append23(final Image<Sample2> l, final Image<Sample3> r){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg23 implements BoundedImage<Sample5>{
		int x, y, w, h;
		Image<Sample2> l;
		Image<Sample3> r;
		
		ConcatBoundedImg23(int x, int y, int w, int h, Image<Sample2> l, Image<Sample3> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample5 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample5(0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample5> appendb23(final BoundedImage<Sample2> l, final Image<Sample3> r){
		return new ConcatBoundedImg23(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample5> append2b3(final Image<Sample2> l, final BoundedImage<Sample3> r){
		return new ConcatBoundedImg23(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample5> appendb2b3(final BoundedImage<Sample2> l, final BoundedImage<Sample3> r){
		return new ConcatBoundedImg23(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample5> append32(final Image<Sample3> l, final Image<Sample2> r){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg32 implements BoundedImage<Sample5>{
		int x, y, w, h;
		Image<Sample3> l;
		Image<Sample2> r;
		
		ConcatBoundedImg32(int x, int y, int w, int h, Image<Sample3> l, Image<Sample2> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample5 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample5(0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample5> appendb32(final BoundedImage<Sample3> l, final Image<Sample2> r){
		return new ConcatBoundedImg32(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample5> append3b2(final Image<Sample3> l, final BoundedImage<Sample2> r){
		return new ConcatBoundedImg32(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample5> appendb3b2(final BoundedImage<Sample3> l, final BoundedImage<Sample2> r){
		return new ConcatBoundedImg32(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample5> append41(final Image<Sample4> l, final Image<Sample1> r){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg41 implements BoundedImage<Sample5>{
		int x, y, w, h;
		Image<Sample4> l;
		Image<Sample1> r;
		
		ConcatBoundedImg41(int x, int y, int w, int h, Image<Sample4> l, Image<Sample1> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample5 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample5(0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample5> appendb41(final BoundedImage<Sample4> l, final Image<Sample1> r){
		return new ConcatBoundedImg41(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample5> append4b1(final Image<Sample4> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg41(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample5> appendb4b1(final BoundedImage<Sample4> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg41(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	


		
		public static Image<Sample2> del5_135(final Image<Sample5> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del135();
				}
			};
		}
		
		public static Image<Sample2> delb5_135(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del135();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del5_25(final Image<Sample5> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del25();
				}
			};
		}
		
		public static Image<Sample3> delb5_25(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del25();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del5_34(final Image<Sample5> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del34();
				}
			};
		}
		
		public static Image<Sample3> delb5_34(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del34();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del5_124(final Image<Sample5> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del124();
				}
			};
		}
		
		public static Image<Sample2> delb5_124(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del124();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del5_35(final Image<Sample5> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del35();
				}
			};
		}
		
		public static Image<Sample3> delb5_35(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del35();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del5_125(final Image<Sample5> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del125();
				}
			};
		}
		
		public static Image<Sample2> delb5_125(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del125();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del5_134(final Image<Sample5> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del134();
				}
			};
		}
		
		public static Image<Sample2> delb5_134(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del134();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del5_24(final Image<Sample5> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del24();
				}
			};
		}
		
		public static Image<Sample3> delb5_24(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del24();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del5_1235(final Image<Sample5> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del1235();
				}
			};
		}
		
		public static Image<Sample1> delb5_1235(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del1235();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del5_5(final Image<Sample5> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del5();
				}
			};
		}
		
		public static Image<Sample4> delb5_5(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del5();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del5_234(final Image<Sample5> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del234();
				}
			};
		}
		
		public static Image<Sample2> delb5_234(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del234();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del5_14(final Image<Sample5> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del14();
				}
			};
		}
		
		public static Image<Sample3> delb5_14(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del14();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del5_235(final Image<Sample5> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del235();
				}
			};
		}
		
		public static Image<Sample2> delb5_235(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del235();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del5_15(final Image<Sample5> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del15();
				}
			};
		}
		
		public static Image<Sample3> delb5_15(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del15();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del5_1234(final Image<Sample5> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del1234();
				}
			};
		}
		
		public static Image<Sample1> delb5_1234(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del1234();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del5_4(final Image<Sample5> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del4();
				}
			};
		}
		
		public static Image<Sample4> delb5_4(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del4();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del5_1345(final Image<Sample5> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del1345();
				}
			};
		}
		
		public static Image<Sample1> delb5_1345(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del1345();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del5_245(final Image<Sample5> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del245();
				}
			};
		}
		
		public static Image<Sample2> delb5_245(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del245();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del5_3(final Image<Sample5> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del3();
				}
			};
		}
		
		public static Image<Sample4> delb5_3(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del3();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del5_12(final Image<Sample5> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del12();
				}
			};
		}
		
		public static Image<Sample3> delb5_12(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del12();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del5_345(final Image<Sample5> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del345();
				}
			};
		}
		
		public static Image<Sample2> delb5_345(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del345();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del5_1245(final Image<Sample5> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del1245();
				}
			};
		}
		
		public static Image<Sample1> delb5_1245(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del1245();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del5_13(final Image<Sample5> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del13();
				}
			};
		}
		
		public static Image<Sample3> delb5_13(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del13();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del5_2(final Image<Sample5> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del2();
				}
			};
		}
		
		public static Image<Sample4> delb5_2(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del2();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del5_45(final Image<Sample5> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del45();
				}
			};
		}
		
		public static Image<Sample3> delb5_45(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del45();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del5_23(final Image<Sample5> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del23();
				}
			};
		}
		
		public static Image<Sample3> delb5_23(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del23();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del5_1(final Image<Sample5> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del1();
				}
			};
		}
		
		public static Image<Sample4> delb5_1(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del1();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del5_2345(final Image<Sample5> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del2345();
				}
			};
		}
		
		public static Image<Sample1> delb5_2345(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del2345();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del5_145(final Image<Sample5> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del145();
				}
			};
		}
		
		public static Image<Sample2> delb5_145(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del145();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del5_123(final Image<Sample5> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del123();
				}
			};
		}
		
		public static Image<Sample2> delb5_123(final BoundedImage<Sample5> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del123();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
	
		
		public static Image<Sample6> append15(final Image<Sample1> l, final Image<Sample5> r){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg15 implements BoundedImage<Sample6>{
		int x, y, w, h;
		Image<Sample1> l;
		Image<Sample5> r;
		
		ConcatBoundedImg15(int x, int y, int w, int h, Image<Sample1> l, Image<Sample5> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample6 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample6(0,0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample6> appendb15(final BoundedImage<Sample1> l, final Image<Sample5> r){
		return new ConcatBoundedImg15(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample6> append1b5(final Image<Sample1> l, final BoundedImage<Sample5> r){
		return new ConcatBoundedImg15(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample6> appendb1b5(final BoundedImage<Sample1> l, final BoundedImage<Sample5> r){
		return new ConcatBoundedImg15(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample6> append24(final Image<Sample2> l, final Image<Sample4> r){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg24 implements BoundedImage<Sample6>{
		int x, y, w, h;
		Image<Sample2> l;
		Image<Sample4> r;
		
		ConcatBoundedImg24(int x, int y, int w, int h, Image<Sample2> l, Image<Sample4> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample6 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample6(0,0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample6> appendb24(final BoundedImage<Sample2> l, final Image<Sample4> r){
		return new ConcatBoundedImg24(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample6> append2b4(final Image<Sample2> l, final BoundedImage<Sample4> r){
		return new ConcatBoundedImg24(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample6> appendb2b4(final BoundedImage<Sample2> l, final BoundedImage<Sample4> r){
		return new ConcatBoundedImg24(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample6> append33(final Image<Sample3> l, final Image<Sample3> r){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg33 implements BoundedImage<Sample6>{
		int x, y, w, h;
		Image<Sample3> l;
		Image<Sample3> r;
		
		ConcatBoundedImg33(int x, int y, int w, int h, Image<Sample3> l, Image<Sample3> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample6 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample6(0,0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample6> appendb33(final BoundedImage<Sample3> l, final Image<Sample3> r){
		return new ConcatBoundedImg33(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample6> append3b3(final Image<Sample3> l, final BoundedImage<Sample3> r){
		return new ConcatBoundedImg33(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample6> appendb3b3(final BoundedImage<Sample3> l, final BoundedImage<Sample3> r){
		return new ConcatBoundedImg33(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample6> append42(final Image<Sample4> l, final Image<Sample2> r){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg42 implements BoundedImage<Sample6>{
		int x, y, w, h;
		Image<Sample4> l;
		Image<Sample2> r;
		
		ConcatBoundedImg42(int x, int y, int w, int h, Image<Sample4> l, Image<Sample2> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample6 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample6(0,0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample6> appendb42(final BoundedImage<Sample4> l, final Image<Sample2> r){
		return new ConcatBoundedImg42(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample6> append4b2(final Image<Sample4> l, final BoundedImage<Sample2> r){
		return new ConcatBoundedImg42(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample6> appendb4b2(final BoundedImage<Sample4> l, final BoundedImage<Sample2> r){
		return new ConcatBoundedImg42(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample6> append51(final Image<Sample5> l, final Image<Sample1> r){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg51 implements BoundedImage<Sample6>{
		int x, y, w, h;
		Image<Sample5> l;
		Image<Sample1> r;
		
		ConcatBoundedImg51(int x, int y, int w, int h, Image<Sample5> l, Image<Sample1> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample6 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample6(0,0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample6> appendb51(final BoundedImage<Sample5> l, final Image<Sample1> r){
		return new ConcatBoundedImg51(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample6> append5b1(final Image<Sample5> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg51(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample6> appendb5b1(final BoundedImage<Sample5> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg51(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	


		
		public static Image<Sample3> del6_456(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del456();
				}
			};
		}
		
		public static Image<Sample3> delb6_456(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_236(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del236();
				}
			};
		}
		
		public static Image<Sample3> delb6_236(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del236();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_16(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del16();
				}
			};
		}
		
		public static Image<Sample4> delb6_16(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del16();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_135(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del135();
				}
			};
		}
		
		public static Image<Sample3> delb6_135(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del135();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_25(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del25();
				}
			};
		}
		
		public static Image<Sample4> delb6_25(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del25();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_34(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del34();
				}
			};
		}
		
		public static Image<Sample4> delb6_34(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del34();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_124(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del124();
				}
			};
		}
		
		public static Image<Sample3> delb6_124(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del124();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del6_23456(final Image<Sample6> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del23456();
				}
			};
		}
		
		public static Image<Sample1> delb6_23456(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del23456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_1456(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1456();
				}
			};
		}
		
		public static Image<Sample2> delb6_1456(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_1236(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1236();
				}
			};
		}
		
		public static Image<Sample2> delb6_1236(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1236();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del6_6(final Image<Sample6> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del6();
				}
			};
		}
		
		public static Image<Sample5> delb6_6(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del6();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_35(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del35();
				}
			};
		}
		
		public static Image<Sample4> delb6_35(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del35();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_125(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del125();
				}
			};
		}
		
		public static Image<Sample3> delb6_125(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del125();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_134(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del134();
				}
			};
		}
		
		public static Image<Sample3> delb6_134(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del134();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_24(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del24();
				}
			};
		}
		
		public static Image<Sample4> delb6_24(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del24();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del6_13456(final Image<Sample6> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del13456();
				}
			};
		}
		
		public static Image<Sample1> delb6_13456(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del13456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_2456(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del2456();
				}
			};
		}
		
		public static Image<Sample2> delb6_2456(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del2456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_36(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del36();
				}
			};
		}
		
		public static Image<Sample4> delb6_36(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del36();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_126(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del126();
				}
			};
		}
		
		public static Image<Sample3> delb6_126(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del126();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_1235(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1235();
				}
			};
		}
		
		public static Image<Sample2> delb6_1235(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1235();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del6_5(final Image<Sample6> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del5();
				}
			};
		}
		
		public static Image<Sample5> delb6_5(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del5();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_234(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del234();
				}
			};
		}
		
		public static Image<Sample3> delb6_234(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del234();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_14(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del14();
				}
			};
		}
		
		public static Image<Sample4> delb6_14(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del14();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_3456(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del3456();
				}
			};
		}
		
		public static Image<Sample2> delb6_3456(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del3456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del6_12456(final Image<Sample6> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del12456();
				}
			};
		}
		
		public static Image<Sample1> delb6_12456(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del12456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_136(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del136();
				}
			};
		}
		
		public static Image<Sample3> delb6_136(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del136();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_26(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del26();
				}
			};
		}
		
		public static Image<Sample4> delb6_26(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del26();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_235(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del235();
				}
			};
		}
		
		public static Image<Sample3> delb6_235(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del235();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_15(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del15();
				}
			};
		}
		
		public static Image<Sample4> delb6_15(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del15();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_1234(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1234();
				}
			};
		}
		
		public static Image<Sample2> delb6_1234(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1234();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del6_4(final Image<Sample6> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del4();
				}
			};
		}
		
		public static Image<Sample5> delb6_4(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del4();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del6_12356(final Image<Sample6> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del12356();
				}
			};
		}
		
		public static Image<Sample1> delb6_12356(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del12356();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_56(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del56();
				}
			};
		}
		
		public static Image<Sample4> delb6_56(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del56();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_2346(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del2346();
				}
			};
		}
		
		public static Image<Sample2> delb6_2346(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del2346();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_146(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del146();
				}
			};
		}
		
		public static Image<Sample3> delb6_146(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del146();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_1345(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1345();
				}
			};
		}
		
		public static Image<Sample2> delb6_1345(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1345();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_245(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del245();
				}
			};
		}
		
		public static Image<Sample3> delb6_245(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del245();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del6_3(final Image<Sample6> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del3();
				}
			};
		}
		
		public static Image<Sample5> delb6_3(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del3();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_12(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del12();
				}
			};
		}
		
		public static Image<Sample4> delb6_12(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del12();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_2356(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del2356();
				}
			};
		}
		
		public static Image<Sample2> delb6_2356(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del2356();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_156(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del156();
				}
			};
		}
		
		public static Image<Sample3> delb6_156(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del156();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del6_12346(final Image<Sample6> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del12346();
				}
			};
		}
		
		public static Image<Sample1> delb6_12346(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del12346();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_46(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del46();
				}
			};
		}
		
		public static Image<Sample4> delb6_46(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del46();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_345(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del345();
				}
			};
		}
		
		public static Image<Sample3> delb6_345(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del345();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_1245(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1245();
				}
			};
		}
		
		public static Image<Sample2> delb6_1245(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1245();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_13(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del13();
				}
			};
		}
		
		public static Image<Sample4> delb6_13(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del13();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del6_2(final Image<Sample6> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del2();
				}
			};
		}
		
		public static Image<Sample5> delb6_2(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del2();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_1356(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1356();
				}
			};
		}
		
		public static Image<Sample2> delb6_1356(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1356();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_256(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del256();
				}
			};
		}
		
		public static Image<Sample3> delb6_256(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del256();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_346(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del346();
				}
			};
		}
		
		public static Image<Sample3> delb6_346(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del346();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_1246(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1246();
				}
			};
		}
		
		public static Image<Sample2> delb6_1246(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1246();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del6_12345(final Image<Sample6> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del12345();
				}
			};
		}
		
		public static Image<Sample1> delb6_12345(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del12345();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_45(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del45();
				}
			};
		}
		
		public static Image<Sample4> delb6_45(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del45();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del6_23(final Image<Sample6> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del23();
				}
			};
		}
		
		public static Image<Sample4> delb6_23(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del23();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del6_1(final Image<Sample6> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del1();
				}
			};
		}
		
		public static Image<Sample5> delb6_1(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del1();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_356(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del356();
				}
			};
		}
		
		public static Image<Sample3> delb6_356(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del356();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_1256(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1256();
				}
			};
		}
		
		public static Image<Sample2> delb6_1256(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1256();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_1346(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1346();
				}
			};
		}
		
		public static Image<Sample2> delb6_1346(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del1346();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_246(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del246();
				}
			};
		}
		
		public static Image<Sample3> delb6_246(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del246();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del6_2345(final Image<Sample6> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del2345();
				}
			};
		}
		
		public static Image<Sample2> delb6_2345(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del2345();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_145(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del145();
				}
			};
		}
		
		public static Image<Sample3> delb6_145(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del145();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del6_123(final Image<Sample6> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del123();
				}
			};
		}
		
		public static Image<Sample3> delb6_123(final BoundedImage<Sample6> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del123();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
	
		
		public static Image<Sample7> append16(final Image<Sample1> l, final Image<Sample6> r){
			return new Image<Sample7>(){
				public Sample7 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg16 implements BoundedImage<Sample7>{
		int x, y, w, h;
		Image<Sample1> l;
		Image<Sample6> r;
		
		ConcatBoundedImg16(int x, int y, int w, int h, Image<Sample1> l, Image<Sample6> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample7 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample7(0,0,0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample7> appendb16(final BoundedImage<Sample1> l, final Image<Sample6> r){
		return new ConcatBoundedImg16(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample7> append1b6(final Image<Sample1> l, final BoundedImage<Sample6> r){
		return new ConcatBoundedImg16(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample7> appendb1b6(final BoundedImage<Sample1> l, final BoundedImage<Sample6> r){
		return new ConcatBoundedImg16(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample7> append25(final Image<Sample2> l, final Image<Sample5> r){
			return new Image<Sample7>(){
				public Sample7 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg25 implements BoundedImage<Sample7>{
		int x, y, w, h;
		Image<Sample2> l;
		Image<Sample5> r;
		
		ConcatBoundedImg25(int x, int y, int w, int h, Image<Sample2> l, Image<Sample5> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample7 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample7(0,0,0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample7> appendb25(final BoundedImage<Sample2> l, final Image<Sample5> r){
		return new ConcatBoundedImg25(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample7> append2b5(final Image<Sample2> l, final BoundedImage<Sample5> r){
		return new ConcatBoundedImg25(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample7> appendb2b5(final BoundedImage<Sample2> l, final BoundedImage<Sample5> r){
		return new ConcatBoundedImg25(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample7> append34(final Image<Sample3> l, final Image<Sample4> r){
			return new Image<Sample7>(){
				public Sample7 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg34 implements BoundedImage<Sample7>{
		int x, y, w, h;
		Image<Sample3> l;
		Image<Sample4> r;
		
		ConcatBoundedImg34(int x, int y, int w, int h, Image<Sample3> l, Image<Sample4> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample7 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample7(0,0,0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample7> appendb34(final BoundedImage<Sample3> l, final Image<Sample4> r){
		return new ConcatBoundedImg34(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample7> append3b4(final Image<Sample3> l, final BoundedImage<Sample4> r){
		return new ConcatBoundedImg34(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample7> appendb3b4(final BoundedImage<Sample3> l, final BoundedImage<Sample4> r){
		return new ConcatBoundedImg34(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample7> append43(final Image<Sample4> l, final Image<Sample3> r){
			return new Image<Sample7>(){
				public Sample7 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg43 implements BoundedImage<Sample7>{
		int x, y, w, h;
		Image<Sample4> l;
		Image<Sample3> r;
		
		ConcatBoundedImg43(int x, int y, int w, int h, Image<Sample4> l, Image<Sample3> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample7 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample7(0,0,0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample7> appendb43(final BoundedImage<Sample4> l, final Image<Sample3> r){
		return new ConcatBoundedImg43(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample7> append4b3(final Image<Sample4> l, final BoundedImage<Sample3> r){
		return new ConcatBoundedImg43(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample7> appendb4b3(final BoundedImage<Sample4> l, final BoundedImage<Sample3> r){
		return new ConcatBoundedImg43(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample7> append52(final Image<Sample5> l, final Image<Sample2> r){
			return new Image<Sample7>(){
				public Sample7 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg52 implements BoundedImage<Sample7>{
		int x, y, w, h;
		Image<Sample5> l;
		Image<Sample2> r;
		
		ConcatBoundedImg52(int x, int y, int w, int h, Image<Sample5> l, Image<Sample2> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample7 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample7(0,0,0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample7> appendb52(final BoundedImage<Sample5> l, final Image<Sample2> r){
		return new ConcatBoundedImg52(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample7> append5b2(final Image<Sample5> l, final BoundedImage<Sample2> r){
		return new ConcatBoundedImg52(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample7> appendb5b2(final BoundedImage<Sample5> l, final BoundedImage<Sample2> r){
		return new ConcatBoundedImg52(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	
		public static Image<Sample7> append61(final Image<Sample6> l, final Image<Sample1> r){
			return new Image<Sample7>(){
				public Sample7 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	private static class ConcatBoundedImg61 implements BoundedImage<Sample7>{
		int x, y, w, h;
		Image<Sample6> l;
		Image<Sample1> r;
		
		ConcatBoundedImg61(int x, int y, int w, int h, Image<Sample6> l, Image<Sample1> r){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.l = l;
			this.r = r;
		}

		public int getX() { return x; }
		public int getY() { return y; }
		public int getWidth() { return w; }
		public int getHeight() { return h; }
		public Sample7 get(double x, double y){
			if(x < this.x || y < this.y || x > this.x + this.w -1 || y > this.y + this.h -1){
				return new Sample7(0,0,0,0,0,0,0);
			}
			return l.get(x,y).concat(r.get(x,y));
		}
	
	}
	public static  BoundedImage<Sample7> appendb61(final BoundedImage<Sample6> l, final Image<Sample1> r){
		return new ConcatBoundedImg61(l.getX(),l.getY(),l.getWidth(),l.getHeight(),l,r);
	}

	public static  BoundedImage<Sample7> append6b1(final Image<Sample6> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg61(r.getX(),r.getY(),r.getWidth(),r.getHeight(),l,r);
	}

	public static BoundedImage<Sample7> appendb6b1(final BoundedImage<Sample6> l, final BoundedImage<Sample1> r){
		return new ConcatBoundedImg61(Math.max(l.getX(),r.getX()),Math.max(l.getY(),r.getY()),Math.min(l.getWidth(),r.getWidth()),Math.min(l.getHeight(),r.getHeight()),l,r);
	}
	


		
		public static Image<Sample3> del7_3567(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del3567();
				}
			};
		}
		
		public static Image<Sample3> delb7_3567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del3567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_12567(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12567();
				}
			};
		}
		
		public static Image<Sample2> delb7_12567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_13467(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del13467();
				}
			};
		}
		
		public static Image<Sample2> delb7_13467(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del13467();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_2467(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2467();
				}
			};
		}
		
		public static Image<Sample3> delb7_2467(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2467();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_23457(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del23457();
				}
			};
		}
		
		public static Image<Sample2> delb7_23457(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del23457();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1457(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1457();
				}
			};
		}
		
		public static Image<Sample3> delb7_1457(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1457();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1237(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1237();
				}
			};
		}
		
		public static Image<Sample3> delb7_1237(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1237();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample6> del7_7(final Image<Sample7> l){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del7();
				}
			};
		}
		
		public static Image<Sample6> delb7_7(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del7();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del7_123456(final Image<Sample7> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del123456();
				}
			};
		}
		
		public static Image<Sample1> delb7_123456(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del123456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_456(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del456();
				}
			};
		}
		
		public static Image<Sample4> delb7_456(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_236(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del236();
				}
			};
		}
		
		public static Image<Sample4> delb7_236(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del236();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_16(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del16();
				}
			};
		}
		
		public static Image<Sample5> delb7_16(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del16();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_135(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del135();
				}
			};
		}
		
		public static Image<Sample4> delb7_135(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del135();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_25(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del25();
				}
			};
		}
		
		public static Image<Sample5> delb7_25(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del25();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_34(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del34();
				}
			};
		}
		
		public static Image<Sample5> delb7_34(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del34();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_124(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del124();
				}
			};
		}
		
		public static Image<Sample4> delb7_124(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del124();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_13567(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del13567();
				}
			};
		}
		
		public static Image<Sample2> delb7_13567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del13567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_2567(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2567();
				}
			};
		}
		
		public static Image<Sample3> delb7_2567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_3467(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del3467();
				}
			};
		}
		
		public static Image<Sample3> delb7_3467(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del3467();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_12467(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12467();
				}
			};
		}
		
		public static Image<Sample2> delb7_12467(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12467();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del7_123457(final Image<Sample7> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del123457();
				}
			};
		}
		
		public static Image<Sample1> delb7_123457(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del123457();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_457(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del457();
				}
			};
		}
		
		public static Image<Sample4> delb7_457(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del457();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_237(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del237();
				}
			};
		}
		
		public static Image<Sample4> delb7_237(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del237();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_17(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del17();
				}
			};
		}
		
		public static Image<Sample5> delb7_17(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del17();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_23456(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del23456();
				}
			};
		}
		
		public static Image<Sample2> delb7_23456(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del23456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1456(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1456();
				}
			};
		}
		
		public static Image<Sample3> delb7_1456(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1236(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1236();
				}
			};
		}
		
		public static Image<Sample3> delb7_1236(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1236();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample6> del7_6(final Image<Sample7> l){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del6();
				}
			};
		}
		
		public static Image<Sample6> delb7_6(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del6();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_35(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del35();
				}
			};
		}
		
		public static Image<Sample5> delb7_35(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del35();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_125(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del125();
				}
			};
		}
		
		public static Image<Sample4> delb7_125(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del125();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_134(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del134();
				}
			};
		}
		
		public static Image<Sample4> delb7_134(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del134();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_24(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del24();
				}
			};
		}
		
		public static Image<Sample5> delb7_24(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del24();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_23567(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del23567();
				}
			};
		}
		
		public static Image<Sample2> delb7_23567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del23567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1567(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1567();
				}
			};
		}
		
		public static Image<Sample3> delb7_1567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del7_123467(final Image<Sample7> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del123467();
				}
			};
		}
		
		public static Image<Sample1> delb7_123467(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del123467();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_467(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del467();
				}
			};
		}
		
		public static Image<Sample4> delb7_467(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del467();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_3457(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del3457();
				}
			};
		}
		
		public static Image<Sample3> delb7_3457(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del3457();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_12457(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12457();
				}
			};
		}
		
		public static Image<Sample2> delb7_12457(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12457();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_137(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del137();
				}
			};
		}
		
		public static Image<Sample4> delb7_137(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del137();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_27(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del27();
				}
			};
		}
		
		public static Image<Sample5> delb7_27(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del27();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_13456(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del13456();
				}
			};
		}
		
		public static Image<Sample2> delb7_13456(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del13456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_2456(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2456();
				}
			};
		}
		
		public static Image<Sample3> delb7_2456(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_36(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del36();
				}
			};
		}
		
		public static Image<Sample5> delb7_36(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del36();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_126(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del126();
				}
			};
		}
		
		public static Image<Sample4> delb7_126(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del126();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1235(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1235();
				}
			};
		}
		
		public static Image<Sample3> delb7_1235(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1235();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample6> del7_5(final Image<Sample7> l){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del5();
				}
			};
		}
		
		public static Image<Sample6> delb7_5(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del5();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_234(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del234();
				}
			};
		}
		
		public static Image<Sample4> delb7_234(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del234();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_14(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del14();
				}
			};
		}
		
		public static Image<Sample5> delb7_14(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del14();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del7_123567(final Image<Sample7> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del123567();
				}
			};
		}
		
		public static Image<Sample1> delb7_123567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del123567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_567(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del567();
				}
			};
		}
		
		public static Image<Sample4> delb7_567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_23467(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del23467();
				}
			};
		}
		
		public static Image<Sample2> delb7_23467(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del23467();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1467(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1467();
				}
			};
		}
		
		public static Image<Sample3> delb7_1467(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1467();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_13457(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del13457();
				}
			};
		}
		
		public static Image<Sample2> delb7_13457(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del13457();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_2457(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2457();
				}
			};
		}
		
		public static Image<Sample3> delb7_2457(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2457();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_37(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del37();
				}
			};
		}
		
		public static Image<Sample5> delb7_37(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del37();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_127(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del127();
				}
			};
		}
		
		public static Image<Sample4> delb7_127(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del127();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_3456(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del3456();
				}
			};
		}
		
		public static Image<Sample3> delb7_3456(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del3456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_12456(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12456();
				}
			};
		}
		
		public static Image<Sample2> delb7_12456(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12456();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_136(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del136();
				}
			};
		}
		
		public static Image<Sample4> delb7_136(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del136();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_26(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del26();
				}
			};
		}
		
		public static Image<Sample5> delb7_26(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del26();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_235(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del235();
				}
			};
		}
		
		public static Image<Sample4> delb7_235(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del235();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_15(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del15();
				}
			};
		}
		
		public static Image<Sample5> delb7_15(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del15();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1234(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1234();
				}
			};
		}
		
		public static Image<Sample3> delb7_1234(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1234();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample6> del7_4(final Image<Sample7> l){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del4();
				}
			};
		}
		
		public static Image<Sample6> delb7_4(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del4();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_34567(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del34567();
				}
			};
		}
		
		public static Image<Sample2> delb7_34567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del34567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del7_124567(final Image<Sample7> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del124567();
				}
			};
		}
		
		public static Image<Sample1> delb7_124567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del124567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1367(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1367();
				}
			};
		}
		
		public static Image<Sample3> delb7_1367(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1367();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_267(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del267();
				}
			};
		}
		
		public static Image<Sample4> delb7_267(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del267();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_2357(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2357();
				}
			};
		}
		
		public static Image<Sample3> delb7_2357(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2357();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_157(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del157();
				}
			};
		}
		
		public static Image<Sample4> delb7_157(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del157();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_12347(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12347();
				}
			};
		}
		
		public static Image<Sample2> delb7_12347(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12347();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_47(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del47();
				}
			};
		}
		
		public static Image<Sample5> delb7_47(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del47();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_12356(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12356();
				}
			};
		}
		
		public static Image<Sample2> delb7_12356(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12356();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_56(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del56();
				}
			};
		}
		
		public static Image<Sample5> delb7_56(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del56();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_2346(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2346();
				}
			};
		}
		
		public static Image<Sample3> delb7_2346(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2346();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_146(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del146();
				}
			};
		}
		
		public static Image<Sample4> delb7_146(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del146();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1345(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1345();
				}
			};
		}
		
		public static Image<Sample3> delb7_1345(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1345();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_245(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del245();
				}
			};
		}
		
		public static Image<Sample4> delb7_245(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del245();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample6> del7_3(final Image<Sample7> l){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del3();
				}
			};
		}
		
		public static Image<Sample6> delb7_3(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del3();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_12(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del12();
				}
			};
		}
		
		public static Image<Sample5> delb7_12(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del12();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del7_134567(final Image<Sample7> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del134567();
				}
			};
		}
		
		public static Image<Sample1> delb7_134567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del134567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_24567(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del24567();
				}
			};
		}
		
		public static Image<Sample2> delb7_24567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del24567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_367(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del367();
				}
			};
		}
		
		public static Image<Sample4> delb7_367(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del367();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1267(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1267();
				}
			};
		}
		
		public static Image<Sample3> delb7_1267(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1267();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_12357(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12357();
				}
			};
		}
		
		public static Image<Sample2> delb7_12357(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12357();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_57(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del57();
				}
			};
		}
		
		public static Image<Sample5> delb7_57(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del57();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_2347(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2347();
				}
			};
		}
		
		public static Image<Sample3> delb7_2347(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2347();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_147(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del147();
				}
			};
		}
		
		public static Image<Sample4> delb7_147(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del147();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_2356(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2356();
				}
			};
		}
		
		public static Image<Sample3> delb7_2356(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2356();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_156(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del156();
				}
			};
		}
		
		public static Image<Sample4> delb7_156(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del156();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_12346(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12346();
				}
			};
		}
		
		public static Image<Sample2> delb7_12346(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12346();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_46(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del46();
				}
			};
		}
		
		public static Image<Sample5> delb7_46(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del46();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_345(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del345();
				}
			};
		}
		
		public static Image<Sample4> delb7_345(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del345();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1245(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1245();
				}
			};
		}
		
		public static Image<Sample3> delb7_1245(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1245();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_13(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del13();
				}
			};
		}
		
		public static Image<Sample5> delb7_13(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del13();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample6> del7_2(final Image<Sample7> l){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del2();
				}
			};
		}
		
		public static Image<Sample6> delb7_2(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del2();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample1> del7_234567(final Image<Sample7> l){
			return new Image<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del234567();
				}
			};
		}
		
		public static Image<Sample1> delb7_234567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample1>(){
				public Sample1 get(double x, double y){
					return l.get(x,y).del234567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_14567(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del14567();
				}
			};
		}
		
		public static Image<Sample2> delb7_14567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del14567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_12367(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12367();
				}
			};
		}
		
		public static Image<Sample2> delb7_12367(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12367();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_67(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del67();
				}
			};
		}
		
		public static Image<Sample5> delb7_67(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del67();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_357(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del357();
				}
			};
		}
		
		public static Image<Sample4> delb7_357(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del357();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1257(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1257();
				}
			};
		}
		
		public static Image<Sample3> delb7_1257(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1257();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1347(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1347();
				}
			};
		}
		
		public static Image<Sample3> delb7_1347(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1347();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_247(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del247();
				}
			};
		}
		
		public static Image<Sample4> delb7_247(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del247();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1356(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1356();
				}
			};
		}
		
		public static Image<Sample3> delb7_1356(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1356();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_256(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del256();
				}
			};
		}
		
		public static Image<Sample4> delb7_256(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del256();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_346(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del346();
				}
			};
		}
		
		public static Image<Sample4> delb7_346(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del346();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1246(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1246();
				}
			};
		}
		
		public static Image<Sample3> delb7_1246(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1246();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample2> del7_12345(final Image<Sample7> l){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12345();
				}
			};
		}
		
		public static Image<Sample2> delb7_12345(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).del12345();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_45(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del45();
				}
			};
		}
		
		public static Image<Sample5> delb7_45(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del45();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample5> del7_23(final Image<Sample7> l){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del23();
				}
			};
		}
		
		public static Image<Sample5> delb7_23(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).del23();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample6> del7_1(final Image<Sample7> l){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del1();
				}
			};
		}
		
		public static Image<Sample6> delb7_1(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).del1();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_4567(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del4567();
				}
			};
		}
		
		public static Image<Sample3> delb7_4567(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del4567();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_2367(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2367();
				}
			};
		}
		
		public static Image<Sample3> delb7_2367(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2367();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_167(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del167();
				}
			};
		}
		
		public static Image<Sample4> delb7_167(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del167();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1357(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1357();
				}
			};
		}
		
		public static Image<Sample3> delb7_1357(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1357();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_257(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del257();
				}
			};
		}
		
		public static Image<Sample4> delb7_257(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del257();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_347(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del347();
				}
			};
		}
		
		public static Image<Sample4> delb7_347(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del347();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1247(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1247();
				}
			};
		}
		
		public static Image<Sample3> delb7_1247(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1247();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_356(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del356();
				}
			};
		}
		
		public static Image<Sample4> delb7_356(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del356();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1256(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1256();
				}
			};
		}
		
		public static Image<Sample3> delb7_1256(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1256();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_1346(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1346();
				}
			};
		}
		
		public static Image<Sample3> delb7_1346(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del1346();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_246(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del246();
				}
			};
		}
		
		public static Image<Sample4> delb7_246(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del246();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample3> del7_2345(final Image<Sample7> l){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2345();
				}
			};
		}
		
		public static Image<Sample3> delb7_2345(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).del2345();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_145(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del145();
				}
			};
		}
		
		public static Image<Sample4> delb7_145(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del145();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
		public static Image<Sample4> del7_123(final Image<Sample7> l){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del123();
				}
			};
		}
		
		public static Image<Sample4> delb7_123(final BoundedImage<Sample7> l){
			return new BoundedImage<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).del123();
				}
			
			public int getX() { return l.getX(); }
			public int getY() { return l.getY(); }
			public int getWidth() { return l.getWidth(); }
			public int getHeight() { return l.getHeight(); }
			};
		}



	
	
}
	