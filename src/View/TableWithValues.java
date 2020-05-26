package View;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.*;

public class TableWithValues {
    private Table tableWithValues;
	private int pagesOverall;
	private int currentPage;
	private int counter;
	

	TableWithValues(){
		currentPage = 1;
		pagesOverall = 1;
		counter = 0;
		
	}
	
    public void createTable(Composite shell) {
        tableWithValues = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
        tableWithValues.setHeaderVisible(true);
        tableWithValues.setLinesVisible(true);
        tableWithValues.setLayoutData(new RowData(220, 500));
        TableColumn columnWithInputX = new TableColumn(tableWithValues, SWT.NONE);
        columnWithInputX.setText("x");
        columnWithInputX.setWidth(80);

        TableColumn columnWithResult = new TableColumn(tableWithValues, SWT.NONE);
        columnWithResult.setText("F(x)");
        columnWithResult.setWidth(100);

        tableWithValues.setSize(110, 250);
    }

    public void updateTable(Point point) {
    	if(counter < 10) {
            TableItem tableItem = new TableItem(tableWithValues, SWT.NONE);
            tableItem.setText(0, String.valueOf(point.getX()/10.0));
            tableItem.setText(1, String.valueOf(point.getY()/10.0));
            }
            counter ++;
            
    }
    
    
    int getCurrentPage(){
    	return currentPage;
    }
    
    int getPagesOverall(){
    	return pagesOverall;
    }
    
    public void setCurrentPage(int num) {
	currentPage = num;
    }

    public void setPagesOverall(int num) {
    pagesOverall = num;
    }
    
    public void removeAll() {
        tableWithValues.removeAll();
        counter = 0;
    }

    public Table getTableWithValues() {
        return tableWithValues;
    }
}

