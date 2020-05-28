package model;

import java.util.ArrayList;
import java.util.List;

public class GraphicA {
	private final List<Point> graphic;

	public GraphicA() {
		graphic = new ArrayList<>();

	}

	public int addValueTo(Point value) {
		synchronized (graphic) {

			graphic.add(value);
			return graphic.size() - 1;

		}
	}

	public Point getPoint(int index) {
		synchronized (graphic) {
			return graphic.get(index);
		}
	}

	public int calculateFunc(int x) {
		double xD = x / 10.0;
		int y = (int) (xD * xD * 10);
		return y;
	}

	public Point calculateY(int x) {
		Point addingPoint = new Point(x, calculateFunc(x));
		return addingPoint;
	}
	
}
