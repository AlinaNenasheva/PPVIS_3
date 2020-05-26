package Controller;


import org.eclipse.swt.widgets.Display;

import Model.Graphic;
import View.MainWindow;
import View.Point;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Runnable {
	private final List<Integer> xList;
	private final Graphic graphic;
	private final MainWindow mainWindow;
	private Point addingPoint;
	private int a, b, c; 

	public Controller(MainWindow mainWindow) {
		graphic = new Graphic();
		this.mainWindow = mainWindow;
		xList = new ArrayList<>();
	}

	@Override
	public void run() {
		for (Integer x : xList) {
			addPoint(calculateY(x, 1), 1);
			addPoint(calculateY(x, 2), 2);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private int calculateAFunc(int x) {
		double xD = x/10.0;
		int y = (int)(xD*xD*10);
		return y;
	}
	
	private int calculateBFunc(int x) {
		double xD = x/10.0;
		if(xD*xD*xD == c) return 1000;
		double valueD = 10/(Math.pow(Math.E, (a*xD+b))*(xD*xD*xD-c));
		int value = (int)(valueD);
		if(Math.abs(valueD*10) % 10 >= 5) 
		{
			if(valueD >= 0)value++;	
			else value--;
		}
		return value;
	}
	
	private Point calculateY(int x, int type) {
		if (type == 1)
			addingPoint = new Point(x, calculateBFunc(x));
		if (type == 2)
			addingPoint = new Point(x, calculateAFunc(x));
		return addingPoint;
	}
	
//	// —умма р€да незначительно измен€етс€
//	private int anotherCalculateY(int xN) {
//		double x = xN/10;
//		double y = 0;
//		double tempY = 0;
//		for (int recursIndex = 0; recursIndex < 100000; recursIndex++) {
//			int result = 1;
//			for (int i = 1; i <= recursIndex; i++) {
//				result = result * i;
//			}
//			tempY = Math.pow(-1, recursIndex) * Math.pow(x, recursIndex) / (result*result);
//			if (tempY <= -0.0001|| tempY <= 0.0001)
//				break;
//			else {
//				y = y + tempY;
//			}
//		}
//		return (int)y;
//	}

	public void addPoint(Point point, int type) {
		Display.getDefault().asyncExec(() -> {
			int index = graphic.addValueTo(point, type);
			mainWindow.updateShell(index, type);
		});
	}

	public List<Integer> getxList() {
		return xList;
	}

	public void addValToXList(int x) {
		xList.add(x);
	}

	public Graphic getGraphic() {
		return graphic;
	}

	public Point getPointFromGraphic(int index, int type) {
		return graphic.getPoint(index, type);
	}

	public int getC() {
		return c;
	}
	
	public void setParametres(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}	
	
}
