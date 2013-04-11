package pacific.hero;

public class CCoord {
	public double x, y;
	public CCoord(double arg1, double arg2){
		x = arg1;
		y = arg2;
	}
	public CCoord copy(){
		return new CCoord(x,y);
	}
	public void add(CCoord add){
		this.x += add.x;
		this.y += add.y;
	}
	public CCoord getDifference(CCoord greater, double damp){
		return new CCoord((greater.x - this.x)/damp, (greater.y - this.y)/damp);
	}
}