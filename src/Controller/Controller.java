package controller;

import org.eclipse.swt.widgets.Display;

import model.GraphicA;
import model.GraphicB;
import model.Point;
import view.MainWindow;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Runnable {
	private final List<Integer> xList;
	private GraphicA graphicA;
	private GraphicB graphicB;
	private final MainWindow mainWindow;
	private int a, b, c;

	public Controller(MainWindow mainWindow) {
		this.graphicB = null;
		this.mainWindow = mainWindow;
		xList = new ArrayList<>();
	}

	@Override
	public void run() {
		for (Integer x : xList) {
			addPointA(graphicA.calculateY(x));
			addPointB(graphicB.calculateY(x));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

	public void addPointA(Point point) {
		Display.getDefault().asyncExec(() -> {
			int index1 = graphicA.addValueTo(point);
			mainWindow.updateShellA(index1);

		});
	}

	public void addPointB(Point point) {
		Display.getDefault().asyncExec(() -> {
			int index2 = graphicB.addValueTo(point);
			mainWindow.updateShellB(index2);
		});
	}
	
	public List<Integer> getxList() {
		return xList;
	}

	public void addValToXList(int x) {
		xList.add(x);
	}

	public GraphicA getGraphicA() {
		return graphicA;
	}
	
	public GraphicB getGraphicB() {
		return graphicB;
	}

	public Point getPointFromGraphicA(int index) {
		return graphicA.getPoint(index);
	}
	
	public Point getPointFromGraphicB(int index) {
		return graphicB.getPoint(index);
	}

	public int getC() {
		return c;
	}

	public void setParametres(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	    graphicA = new GraphicA();
		graphicB = new GraphicB(a, b, c);
	}

}
