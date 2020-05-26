package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import Controller.Controller;

import static org.eclipse.swt.SWT.SHELL_TRIM;

import java.util.ArrayList;
import java.util.List;

public class MainWindow {
	private final Display display = new Display();
	private final TableWithValues tableWithValues;
	private final Controller controller;
	private final GraphicWindow graphicWindow;
	private Button firstPage, previousPage, nextPage, lastPage;
	private Label pagesCounter;
	private Thread thread;
	private Button inputBottomBordText, inputTopBordText;
	int xMin, xMax;

	public MainWindow() {
		Shell shell = new Shell(display, SHELL_TRIM);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);
		shell.setSize(1300, 900);
		Composite composite = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		composite.setLayout(rowLayout);

		Composite composite1 = new Composite(composite, SWT.NONE);
		RowLayout rowLayout1 = new RowLayout(SWT.HORIZONTAL);
		composite1.setLayout(rowLayout1);
		firstPage = new Button(composite1, SWT.PUSH);
		firstPage.setText("<<");
		previousPage = new Button(composite1, SWT.PUSH);
		previousPage.setText("<");
		pagesCounter = new Label(composite1, SWT.LEFT);
		pagesCounter.setText("1/1   ");
		nextPage = new Button(composite1, SWT.PUSH);
		nextPage.setText(">");
		lastPage = new Button(composite1, SWT.PUSH);
		lastPage.setText(">>");
		
		tableWithValues = new TableWithValues();
		tableWithValues.createTable(composite);

		controller = new Controller(this);
		
		Composite composite4 = new Composite(composite, SWT.NONE);
		RowLayout rowLayout4 = new RowLayout(SWT.HORIZONTAL);
		composite4.setLayout(rowLayout4);

		Composite composite3 = new Composite(composite4, SWT.NONE);
		RowLayout rowLayout3 = new RowLayout(SWT.VERTICAL);
		composite3.setLayout(rowLayout3);
		
		Button inputBtn = new Button(composite3, SWT.PUSH);
		inputBtn.setText("Input information");

		Button drawGraphic = new Button(composite3, SWT.PUSH);
		drawGraphic.setText("Draw graphic");

		Button removeGraphic = new Button(composite3, SWT.PUSH);
		removeGraphic.setText("Remove graphic");
		
		Composite composite5 = new Composite(composite4, SWT.NONE);
		RowLayout rowLayout5 = new RowLayout(SWT.VERTICAL);
		composite5.setLayout(rowLayout5);
		
		Button enlarge = new Button(composite5, SWT.PUSH);
		enlarge.setText("+");
		
		Button diminish = new Button(composite5, SWT.PUSH);
		diminish.setText("-");

		Composite composite2 = new Composite(composite, SWT.NONE);
		RowLayout rowLayout2 = new RowLayout(SWT.HORIZONTAL);
		composite2.setLayout(rowLayout2);
		Label zoomRatio = new Label(composite2, SWT.NONE);
		Label unit = new Label(composite2, SWT.NONE);

		graphicWindow = new GraphicWindow(display, shell, zoomRatio, unit, controller, enlarge, diminish);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 400;
		gridData.widthHint = 500;
		graphicWindow.setLayoutData(gridData);
		
		Canvas currentRegime = new Canvas(shell, SWT.NONE );
		currentRegime.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				currentRegime.setBounds(550, 696, 360, 50);
				e.gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				e.gc.drawText("f(x)=x^2", 15, 14);
				e.gc.drawRectangle(1, 18, 10, 10);
				e.gc.setBackground(display.getSystemColor(SWT.COLOR_RED)); 
				e.gc.fillRectangle(2, 19, 9, 9);
				e.gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				e.gc.drawText("f(x)=1/((e^(ax+b))*(x^3 - c))", 95, 14);
				e.gc.drawRectangle(80, 18, 10, 10);
				e.gc.setBackground(display.getSystemColor(SWT.COLOR_GREEN)); 
				e.gc.fillRectangle(81, 19, 9, 9);

			}
		});
	

		firstPage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toFirstPage();
				tableWithValues.removeAll();
				ArrayList<Point> points = extractBlock();
				for(int i = 0; i < points.size(); i++) {
					tableWithValues.updateTable(points.get(i));
				}
				disableButtons();
			}
		});
		
		previousPage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				decrementPage();
				tableWithValues.removeAll();
				ArrayList<Point> points = extractBlock();
				for(int i = 0; i < points.size(); i++) {
					tableWithValues.updateTable(points.get(i));
				}
				disableButtons();
			}
		});
		
		nextPage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				incrementPage();
				tableWithValues.removeAll();
				ArrayList<Point> points = extractBlock();
				for(int i = 0; i < points.size(); i++) {
					tableWithValues.updateTable(points.get(i));
				}
				disableButtons();
			}
		});
		
		lastPage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toLastPage();
				tableWithValues.removeAll();
				ArrayList<Point> points = extractBlock();
				for(int i = 0; i < points.size(); i++) {
					tableWithValues.updateTable(points.get(i));
				}
				disableButtons();
			}
		});
		
		
		inputBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tableWithValues.removeAll();
				graphicWindow.removeAll();
				controller.getxList().clear();
				inputBtnShell(gridLayout);
			}
		});

		drawGraphic.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				thread = new Thread(controller);
				thread.start();
			}
		});

		removeGraphic.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tableWithValues.removeAll();
				graphicWindow.removeAll();
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	
	private void inputBtnShell(GridLayout gridLayout) {
		Shell inputBorderDialog = new Shell(display, SWT.DIALOG_TRIM);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		inputBorderDialog.setLayout(rowLayout);

		Group parameteres1 = new Group(inputBorderDialog, SWT.SHADOW_ETCHED_IN);
		parameteres1.setLayout(new RowLayout(SWT.HORIZONTAL));
		Label enterA = new Label(parameteres1, SWT.NONE);
		enterA.setText("Enter a");
		Text enterAText = new Text(parameteres1, SWT.NONE);
		Label enterB = new Label(parameteres1, SWT.NONE);
		enterB.setText("Enter b");
		Text enterBText = new Text(parameteres1, SWT.NONE);
		Label enterC = new Label(parameteres1, SWT.NONE);
		enterC.setText("Enter c");
		Text enterCText = new Text(parameteres1, SWT.NONE);
		
		Group bottomBord = new Group(inputBorderDialog, SWT.NONE);
		bottomBord.setLayout(gridLayout);

		Label inputBottomBordLabel = new Label(bottomBord, SWT.NONE);
		inputBottomBordLabel.setText(" Bottom x:");

		Text inputBottomBordText = new Text(bottomBord, SWT.NONE);

		Group topBord = new Group(inputBorderDialog, SWT.NONE);
		topBord.setLayout(gridLayout);

		Label inputTopBordLabel = new Label(topBord, SWT.NONE);
		inputTopBordLabel.setText("        Top x:");

		Text inputTopBordText = new Text(topBord, SWT.NONE);

		Button inputOkButton = new Button(inputBorderDialog, SWT.PUSH);
		inputOkButton.setText("Input");

		inputOkButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!inputBottomBordText.getText().equals("") && !inputTopBordText.getText().equals("") && !enterAText.getText().equals("") && !enterBText.getText().equals("") && !enterCText.getText().equals("")) {
					 xMin = (Integer.parseInt(inputBottomBordText.getText()));
						xMax = Integer.parseInt(inputTopBordText.getText());
						int a = Integer.parseInt(enterAText.getText()),
						b = Integer.parseInt(enterBText.getText()),
						c = Integer.parseInt(enterCText.getText());
						xMin *= 10;
						xMax *= 10;
						for (int xMassIndex = xMin; xMassIndex <= xMax; xMassIndex++) {
							controller.addValToXList(xMassIndex);
						}
						controller.setParametres(a, b, c);
						inputBorderDialog.close();

				} else {
					MessageBox messageBox = new MessageBox(inputBorderDialog, SWT.APPLICATION_MODAL);
					messageBox.setMessage("Error input!");
					messageBox.open();
				}
			}
		});

		inputBorderDialog.setSize(400, 200);
		inputBorderDialog.open();
	}
	
	int getInputBottomBordText() {
		return Integer.parseInt(inputBottomBordText.getText());
	}
	
	int getInputTopBordText() {
		return Integer.parseInt(inputTopBordText.getText());
	}

	public ArrayList<Point> extractBlock() {
	int x = (int)(((xMax - xMin))+1);
	if (x % 10 == 0)
		tableWithValues.setPagesOverall(x / 10);
	else
		tableWithValues.setPagesOverall(x / 10 + 1);
	pagesCounter.setText(tableWithValues.getCurrentPage() + "/" + tableWithValues.getPagesOverall());
	if (x > 10) {
		ArrayList<Point> temporary = new ArrayList<>();
		int counter = 0;
		for (int i = (tableWithValues.getCurrentPage() - 1) * 10; i < x; i++) {
			counter++;
			temporary.add(graphicWindow.getList().get(i));
			if (counter == 10)
				break;
		}
		return temporary;
	} else
		return graphicWindow.getList();
}
	

   public void incrementPage() {
	   tableWithValues.setCurrentPage(tableWithValues.getCurrentPage()+1);
}

public void decrementPage() {
	   tableWithValues.setCurrentPage(tableWithValues.getCurrentPage()-1);
}

public void toFirstPage() {
	tableWithValues.setCurrentPage(1);
}

public void toLastPage() {
	tableWithValues.setCurrentPage(tableWithValues.getPagesOverall());
}

	
	private void disableButtons() {
		if (tableWithValues.getCurrentPage() == 1) {
			firstPage.setEnabled(false);
			previousPage.setEnabled(false);
		} else {
			firstPage.setEnabled(true);
			previousPage.setEnabled(true);
		}
		if (tableWithValues.getCurrentPage() == tableWithValues.getPagesOverall()) {
			lastPage.setEnabled(false);
			nextPage.setEnabled(false);
		} else {
			lastPage.setEnabled(true);
			nextPage.setEnabled(true);
		}

	}
	
	public void updateShell(int index, int type) {
		Point showingPoint = controller.getPointFromGraphic(index, type);
		if (showingPoint == null)
			return;
		if (type == 1) {
			
			tableWithValues.updateTable(showingPoint);
			
			graphicWindow.addPointToPointViewList(new Point(showingPoint.getX(), showingPoint.getY()));
		}
		if (type == 2)
			graphicWindow.anotherAddPointToPointViewList(new Point(showingPoint.getX(), showingPoint.getY()));

	}
}

