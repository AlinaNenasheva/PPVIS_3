package Model;


import java.util.ArrayList;
import java.util.List;

import View.Point;

public class Graphic {
	private final List<Point> graphic;
	private final List<Point> anotherGraphic;

	public Graphic() {
		graphic = new ArrayList<>();
		anotherGraphic = new ArrayList<>();
	}

	public int addValueTo(Point value, int type) {
		synchronized (graphic) {
			if (type == 1) {
				graphic.add(value);
				return graphic.size() - 1;
			}
			else if (type == 2) {
				anotherGraphic.add(value);
				return anotherGraphic.size() - 1;
			}
			else return 0;
		}
	}

	public Point getPoint(int index, int type) {
		synchronized (graphic) {
			if (type == 1) {
				return graphic.get(index);
			}
			else if (type == 2) {
				return anotherGraphic.get(index);
			}
			else return null;
		}
	}
}


