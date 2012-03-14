package bezier.image;

import bezier.image.sample.*;
public class AppendImage{
		
	
		
	
		
		public static Image<Sample2> append11(final Image<Sample1> l, final Image<Sample1> r){
			return new Image<Sample2>(){
				public Sample2 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	public static class ConcatBoundedImg11 implements BoundedImage<Sample2>{
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
	
	
		
		public static Image<Sample3> append12(final Image<Sample1> l, final Image<Sample2> r){
			return new Image<Sample3>(){
				public Sample3 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	public static class ConcatBoundedImg12 implements BoundedImage<Sample3>{
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



	public static class ConcatBoundedImg21 implements BoundedImage<Sample3>{
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
	
	
		
		public static Image<Sample4> append13(final Image<Sample1> l, final Image<Sample3> r){
			return new Image<Sample4>(){
				public Sample4 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	public static class ConcatBoundedImg13 implements BoundedImage<Sample4>{
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



	public static class ConcatBoundedImg22 implements BoundedImage<Sample4>{
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



	public static class ConcatBoundedImg31 implements BoundedImage<Sample4>{
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
	
	
		
		public static Image<Sample5> append14(final Image<Sample1> l, final Image<Sample4> r){
			return new Image<Sample5>(){
				public Sample5 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	public static class ConcatBoundedImg14 implements BoundedImage<Sample5>{
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



	public static class ConcatBoundedImg23 implements BoundedImage<Sample5>{
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



	public static class ConcatBoundedImg32 implements BoundedImage<Sample5>{
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



	public static class ConcatBoundedImg41 implements BoundedImage<Sample5>{
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
	
	
		
		public static Image<Sample6> append15(final Image<Sample1> l, final Image<Sample5> r){
			return new Image<Sample6>(){
				public Sample6 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	public static class ConcatBoundedImg15 implements BoundedImage<Sample6>{
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



	public static class ConcatBoundedImg24 implements BoundedImage<Sample6>{
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



	public static class ConcatBoundedImg33 implements BoundedImage<Sample6>{
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



	public static class ConcatBoundedImg42 implements BoundedImage<Sample6>{
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



	public static class ConcatBoundedImg51 implements BoundedImage<Sample6>{
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
	
	
		
		public static Image<Sample7> append16(final Image<Sample1> l, final Image<Sample6> r){
			return new Image<Sample7>(){
				public Sample7 get(double x, double y){
					return l.get(x,y).concat(r.get(x,y));
				}
			};
		}



	public static class ConcatBoundedImg16 implements BoundedImage<Sample7>{
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



	public static class ConcatBoundedImg25 implements BoundedImage<Sample7>{
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



	public static class ConcatBoundedImg34 implements BoundedImage<Sample7>{
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



	public static class ConcatBoundedImg43 implements BoundedImage<Sample7>{
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



	public static class ConcatBoundedImg52 implements BoundedImage<Sample7>{
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



	public static class ConcatBoundedImg61 implements BoundedImage<Sample7>{
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
	
	
}
	