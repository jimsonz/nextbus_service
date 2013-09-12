package edu.bu.cs673b1s1p3.nextbus.main;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel {
	
	public CustomTableModel(){
		super();
	}
	
	public CustomTableModel(int rowCount, int columnCount){
		super(rowCount, columnCount);
	}
	
	public CustomTableModel(Object[][] data, Object[] columnNames){
		super(data, columnNames);
	}
	
	public CustomTableModel(Object[] columnNames, int rowCount){
		super(columnNames, rowCount);
	}
	
	public CustomTableModel(Vector columnNames, int rowCount){
		super(columnNames, rowCount);
	}
	
	public CustomTableModel(Vector data, Vector columnNames){
		super(data, columnNames);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
	    return false;
	}
}
