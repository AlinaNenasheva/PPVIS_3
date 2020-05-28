package model;

public class GraphicB extends GraphicA{
	private int a, b, c;
	
	public GraphicB(int a, int b, int c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	@Override
	public int calculateFunc(int x) {
		double xD = x / 10.0;
		if (xD * xD * xD == c)
			return 1000;
		double valueD = 10 / (Math.pow(Math.E, (a * xD + b)) * (xD * xD * xD - c));
		int value = (int) (valueD);
		if (Math.abs(valueD * 10) % 10 >= 5) {
			if (valueD >= 0)
				value++;
			else
				value--;
		}
		return value;
	}

	
}
