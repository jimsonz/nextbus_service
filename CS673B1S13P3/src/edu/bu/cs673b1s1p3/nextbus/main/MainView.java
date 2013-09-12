package edu.bu.cs673b1s1p3.nextbus.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//import javax.swing.WideComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXMapKit;

import edu.bu.cs673b1s1p3.nextbus.map.MapControl;
import edu.bu.cs673b1s1p3.nextbus.map.MapControlInterface;
import edu.bu.cs673b1s1p3.nextbus.map.MapModel;
import edu.bu.cs673b1s1p3.nextbus.service.ServiceUnavailableException;

public class MainView {

	public final static String NEXTBUS_SERVICE_UNAVAILABLE = "The NextBus Service is unavailable. Please try again later.";
	public final static String INVALID_ADDRESS = "Invalid address entered.";
	public final static String DEFAULT_STATUS = "";
	
	private JFrame frmNextbus;
	private JTable tblStopList;
	
	private MapModel mapModel;
	private MapControlInterface mapControl;
	
	private JXMapKit mapViewer;

	private JLabel lblStatus = null;
	
	private WideComboBox cmbRouteDirections = null;
	private WideComboBox cmbRoutes = null;
	
	private String[][] stopData = new String [0][5];
	private CustomTableModel dataModel;
	
	private MainModel mainModel = null;
	private MainController mainControl = null;
	
	private JTextField txtAddressSearch = null;
	
	private JRadioButton rdbtnByAddress = null;
	private JRadioButton rdbtnByRoute = null;
	private JRadioButton selectedRadio = null;
	
	private JButton btnSearch = null;
	private JButton btnDisplayRefresh = null;
	
	JProgressBar progressBar = null;

	private String[] tableColumns = {"Stop","Stop Name", "Next Bus Time","",""};
	
	final String AGENCY_NAME = "mbta";
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frmNextbus.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
		startBackgroundThread();	
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNextbus = new JFrame();
		frmNextbus.setResizable(false);
		frmNextbus.setTitle("NextBus Real-time Transit Information");
		frmNextbus.setBounds(100, 100, 1024, 600);
		frmNextbus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Throw up a splash screen
		SplashScreen splash = new SplashScreen("images/NextBus.jpg", frmNextbus, 8000);
		splash.setLocation(275, 350);
		
		//---- JXMAPVIEWER (MVC)
		mapViewer = new JXMapKit();
		mapViewer.getMainMap().setBorder(new LineBorder(Color.LIGHT_GRAY));
		mapModel = new MapModel(mapViewer);
		mapControl = new MapControl(getMapModel());
		mapControl.createMapView();
		
		//---- MAIN (MVC)
		try {
			mainModel = new MainModel(AGENCY_NAME);
			mainControl = new MainController(this, mainModel);
		} 
		catch (ServiceUnavailableException e1) {
			notifyAndExit(NEXTBUS_SERVICE_UNAVAILABLE);
		}
		
		
		//---- MENU (Layout)
		JMenuBar menuBar = new JMenuBar();
		frmNextbus.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
	    //Exit button code - by Matt Marchand
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	    //End exit button code
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		// Throw up a splash screen
		mntmAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SplashScreen splash = new SplashScreen("images/NextBus.jpg", frmNextbus, 5000);
				splash.setLocation(275, 350);
			}
		});
		
		frmNextbus.getContentPane().setLayout(new MigLayout("", "[10px:n:10px][410.00px:n:410px][10px:n:10px][380.00px:n:380.00px,fill][175px:n:175px,fill]", "[10px:n:10px][80px:n:80px][10px:n:10px][130px:n:130px][10px:n:10px][252px:n:252px,baseline][9px:n:9px]"));
		
		//---- APP LOGO (Layout)
		JLabel lblLogo = new JLabel("");
		Icon iconLogo = new ImageIcon(getClass().getResource("/logo-mbta.png"));
		lblLogo.setIcon((Icon) iconLogo);
		frmNextbus.getContentPane().add(lblLogo, "flowx,cell 1 1,alignx center");
		
		//---- MAP VIEWER (Layout)
		frmNextbus.getContentPane().add(mapViewer, "cell 3 1 2 5,grow");
		
		//---- SELECT BUS ROUTE (Layout)
		JPanel pnlSelectBusRoute = new JPanel();
		pnlSelectBusRoute.setBackground(SystemColor.menu);
		frmNextbus.getContentPane().add(pnlSelectBusRoute, "cell 1 3,grow");
		pnlSelectBusRoute.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		pnlSelectBusRoute.setLayout(null);
		
		JLabel lblSelectBusBy = new JLabel("Select Bus Route");
		Font fntSelectBusBy =new Font(lblSelectBusBy.getFont().getName(),Font.BOLD,lblSelectBusBy.getFont().getSize()); 
		lblSelectBusBy.setBounds(10, 11, 124, 14);
		lblSelectBusBy.setFont(fntSelectBusBy);
		pnlSelectBusRoute.add(lblSelectBusBy);
		
		//Combo Boxes
		cmbRoutes = new WideComboBox();
		cmbRoutes.setModel(new DefaultComboBoxModel(mainControl.getRouteListItems()));
		cmbRoutes.setBounds(155, 11, 220, 20);
		cmbRoutes.setRenderer(new ItemRenderer());
		pnlSelectBusRoute.add(cmbRoutes);
		
		cmbRoutes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					mainControl.selectRouteAction(e);
				}
			}
		});
		
		//Radio Buttons
		rdbtnByRoute = new JRadioButton("Select Bus Route");
		rdbtnByRoute.setFont(new Font("Tahoma", Font.PLAIN, 9));
		rdbtnByRoute.setSelected(true);
		rdbtnByRoute.setBounds(30, 33, 115, 23);
		pnlSelectBusRoute.add(rdbtnByRoute);
		
		cmbRouteDirections = new WideComboBox();
		cmbRouteDirections.setBounds(155, 36, 220, 20);
		cmbRouteDirections.setRenderer(new ItemRenderer());
		cmbRouteDirections.setEnabled(false);
		
		pnlSelectBusRoute.add(cmbRouteDirections);
		
		cmbRouteDirections.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					try {
						mainControl.selectDirectionAction(e);
					} catch (ServiceUnavailableException e1) {
						notifyAndExit(NEXTBUS_SERVICE_UNAVAILABLE);
					}
				}
			}
		});
		
		rdbtnByAddress = new JRadioButton("Search By Address");
		rdbtnByAddress.setFont(new Font("Tahoma", Font.PLAIN, 9));
		rdbtnByAddress.setBounds(30, 59, 115, 23);
		pnlSelectBusRoute.add(rdbtnByAddress);
		rdbtnByAddress.setEnabled(false);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnByRoute);
		group.add(rdbtnByAddress);
		
		selectedRadio = rdbtnByRoute;
		
		//Address Search Box
		txtAddressSearch = new JTextField();
		txtAddressSearch.setEditable(false);
		txtAddressSearch.setEnabled(false);
		txtAddressSearch.setBounds(155, 60, 220, 20);
		txtAddressSearch.setColumns(10);
		txtAddressSearch.setBackground(Color.LIGHT_GRAY);
		pnlSelectBusRoute.add(txtAddressSearch);
		
		//Buttons
		btnDisplayRefresh = new JButton("Refresh");
		btnDisplayRefresh.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnDisplayRefresh.setBounds(100, 96, 89, 23);
		btnDisplayRefresh.setEnabled(false);
		pnlSelectBusRoute.add(btnDisplayRefresh);
		
		btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSearch.setBounds(210, 96, 89, 23);
		btnSearch.setEnabled(false);
		pnlSelectBusRoute.add(btnSearch);

		
		//---- BUS STOPS TABLE (Layout)
		dataModel = new CustomTableModel(stopData, tableColumns);
		
		tblStopList = new JTable();
		tblStopList.setModel(dataModel);
		tblStopList.setFillsViewportHeight(true);
		
		removeLastColumns(tblStopList);
		
		JScrollPane scrollPane = new JScrollPane(tblStopList);
		scrollPane.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		frmNextbus.getContentPane().add(scrollPane, "cell 1 5,grow");
		
		//---- STATUS (Layout)
		JPanel pnlStatus = new JPanel();
		pnlStatus.setBorder(null);
		pnlStatus.setBackground(new Color(248, 248, 255));
		pnlStatus.setMinimumSize(new Dimension(1024, 10));
		frmNextbus.getContentPane().add(pnlStatus, "dock south");
		pnlStatus.setLayout(new MigLayout("", "[410.00px:n:410px][480px:n:480px][100.00px:n:100px]", "[10px:n:10px]"));
		
		lblStatus = new JLabel("");
		pnlStatus.add(lblStatus, "cell 0 0");
		
		// Progress Bar
		progressBar = new JProgressBar();
		pnlStatus.add(progressBar, "cell 2 0,grow");
		
		
		//---- ACTIONS
		btnDisplayRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					mainControl.refreshPressedAction();
				} catch (ServiceUnavailableException e1) {
					notifyAndExit(NEXTBUS_SERVICE_UNAVAILABLE);
				}
			}
		});
		
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainControl.searchPressedAction(txtAddressSearch.getText());
			}
		});

		txtAddressSearch.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				updateStatus(DEFAULT_STATUS);
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		rdbtnByRoute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if((JRadioButton)e.getSource() != selectedRadio){
					selectedRadio = (JRadioButton)e.getSource();
					mainControl.rdBusRouteAction();
				}
			}
		});
		
		rdbtnByAddress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if((JRadioButton)e.getSource() != selectedRadio){
					selectedRadio = (JRadioButton)e.getSource();
					mainControl.rdSearchAddressAction();
				}
			}
		});
		
		tblStopList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				mainControl.rowSelectedAction(tblStopList);
			}
		});
		
	}
	
	/**
	 * Thread to grab All Route Configs in the background
	 */
	public boolean run = true;
	
	Thread t = new Thread(){
	    public void run()
	    {
	    	mainControl.processAllRouteConfigs();
	    	rdbtnByAddress.setEnabled(true);
	    }
	};
	
	public void startBackgroundThread(){
	    t.start(); //starts the thread
	}
	
	//---- GETTERS and SETTERS	
	/**
	 * Returns instance of the Map Model
	 * @return the Map Model
	 */
	public MapModel getMapModel() {
		return this.mapModel;
	}

	/**
	 * Returns instance of the Map Controller
	 * @return the Map Controller
	 */
	public MapControlInterface getMapControl()
	{
		return this.mapControl;
	}
	
	/**
	 * Allows you to define a list of columns by passing in an array of Strings.
	 * Every value in the Array is the column header name for each respective column.
	 * @param columns - a String[] of column names
	 */
	public void setTableColumns(String[] columns)
	{
		this.tableColumns = columns;
		this.dataModel = new CustomTableModel(columns, 0);
		this.tblStopList.setModel(this.dataModel);
		resizeColumns(this.tblStopList, 2);
		removeLastColumns(this.tblStopList);
		this.tblStopList.revalidate();
	}
	
	/**
	 * Get StopList Table
	 * @return JTable 
	 */
	public JTable getStopListTable()
	{
		return tblStopList;

	}
	
	/**
	 * Get Select By Bus Route JRadioButton
	 * @return JRadioButton 
	 */
	public JRadioButton getRadioByRoute() {
		return rdbtnByRoute;
	}
	
	/**
	 * Get Select By Address JRadioButton
	 * @return JRadioButton 
	 */
	public JRadioButton getRadioByAddress() {
		return rdbtnByAddress;
	}
	
	//---- UPDATE VIEW
	/**
	 * Updates the status bar with passed in String argument.
	 * 
	 * @param status text you would like to display.
	 */
	public void updateStatus(String status)
	{
		lblStatus.setText(status);
	}
	
	/**
	 * Enables/Disables BusRoute drop-down list
	 * 
	 * @param toggle - boolean true or false
	 */
	public void enableRoute(boolean toggle)
	{
		cmbRoutes.setEnabled(toggle);
		if(toggle == false){
			cmbRoutes.setSelectedIndex(0);
		}
	}
	
	/**
	 * Enables/Disables Directions drop-down list
	 * 
	 * @param toggle - boolean true or false
	 */
	public void enableRouteDirections(boolean toggle)
	{
		cmbRouteDirections.setEnabled(toggle);
	}
	
	/**
	 * Enables/Disables Refresh Button
	 * 
	 * @param toggle - boolean true or false
	 */
	public void enableRefresh(boolean toggle)
	{
		btnDisplayRefresh.setEnabled(toggle);
	}
	
	/**
	 * Enables/Disables BusRoute drop-down list
	 * 
	 * @param toggle - boolean true or false
	 */
	public void enableAddressSearch(boolean toggle)
	{
		txtAddressSearch.setEnabled(toggle);
		txtAddressSearch.setEditable(toggle);
		txtAddressSearch.setBackground((toggle == true) ? Color.WHITE : Color.LIGHT_GRAY);
    	btnSearch.setEnabled(toggle);
	}
	
	
	/**
	 * Adds new Route Direction Item to Route Direction drop down list
	 * 
	 * @param item - Item Object with Id and Description (Tag and Title)
	 */
	public void addRouteDirectionItem(Item item)
	{
		cmbRouteDirections.addItem(item);
	}
	
	/**
	 * Clears all values from the Route Direction drop down list
	 */
	public void clearDirections()
	{
		cmbRouteDirections.removeAllItems();
	}
	
	/**
	 * Update Bus Stops Table with passed in stopData and tableColumns.  
	 * stopData is made up of a 2D String Array.
	 * tableColumns is made up of a 1D String Array.
	 * 
	 * @param stopData - 2D String array 
	 * @param newTableColumn - 1D String Array
	 */
	public void updateStopsTable(String[][] stopData, String[] newTableColumn)
	{
		this.dataModel = new CustomTableModel(stopData, newTableColumn);
		this.tblStopList.setModel(this.dataModel);
		resizeColumns(this.tblStopList, 4);
		removeLastColumns(this.tblStopList);
		this.tblStopList.revalidate();
	}
	
	/**
	 * Update Bus Stops Table with passed in stopData.  
	 * stopData is made up of a 2D String Array.
	 * 
	 * @param stopData - 2D String array 
	 */
	public void updateStopsTable(String[][] stopData)
	{
		updateStopsTable(stopData, this.tableColumns);	
	}
	

	/**
	 * Update Bus Stops Table with passed in tableColumns.  
	 * tableColumns is made up of a 1D String Array.
	 * 
	 * @param newTableColumn - 1D String Array
	 */
	public void updateStopsTable(String[] newTableColumn)
	{
		this.tableColumns = newTableColumn;
		this.dataModel = new CustomTableModel(newTableColumn,0);
		this.tblStopList.setModel(this.dataModel);
		resizeColumns(this.tblStopList, 4);
		this.tblStopList.revalidate();
	}
	
	/**
	 * Update Bus Stops Table with passed in stopData.  
	 * stopData is made up of a 2D String Array.
	 * 
	 * @param stopData - 2D String array 
	 */
	public void clearStops()
	{
		this.dataModel.setRowCount(0);
		this.tblStopList.revalidate();	
	}
	
	/**
	 * Clears out Routes from Map
	 */
	public void resetMapViewer()
	{
		mapControl.clearOverlays();
		updateMapViewer();
	}
	
	/**
	 * Setup Progress Bar
	 */
	public void setupProgressBar(int newLength)
	{
		progressBar.setMaximum(newLength);
	}
	
	/**
	 * Update Progress Bar
	 */
	public void updateProgressBar(int newLength)
	{
		if(newLength <= progressBar.getMaximum())
		{
			progressBar.setValue(newLength);
		}
	}
	
	/**
	 * Updates MainView JFrame
	 */
	public void updateMapViewer()
	{
		frmNextbus.validate();
		frmNextbus.repaint();
	}
	
	//---- PRIVATE METHODS
	/**
	 * Removes the last 2 columns of the passed in table
	 * @param table - a {@link JTable} object
	 */
	private void removeLastColumns(JTable table)
	{
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(tableColumns.length - 1));
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(tableColumns.length -2));
	}
	
	/**
	 * Removes the last 2 columns of the passed in table
	 * @param table - a {@link JTable} object
	 * @param columnsBack - column to resize. Column selected is the total column count minus the columnsBack value.
	 */
	private void resizeColumns(JTable table, int columnsBack)
	{
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(tableColumns.length - columnsBack).setMinWidth(200);
	}
	
	
	//----- EXCEPTION
	/**
	 * Notifies the user of an ExceptionCondition
	 * with an alert dialog and exits the application
	 * when the user clicks OK.
	 * 
	 */
	public void notifyAndExit(String message) {
		JDialog alertBox = new JDialog(frmNextbus, ModalityType.APPLICATION_MODAL);
		if (frmNextbus != null) {
			Point location = frmNextbus.getLocation();
			Point newLocation = new Point((int) location.getX() + 400,  (int) location.getY() + 400);
			alertBox.setLocation(newLocation);
		}
		JPanel messagePane = new JPanel();
	    messagePane.add(new JLabel(message));
	    alertBox.getContentPane().add(messagePane);
	    JPanel buttonPane = new JPanel();
	    JButton button = new JButton("OK"); 
	    buttonPane.add(button); 
	    button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
	    alertBox.getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    alertBox.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    alertBox.pack(); 
	    alertBox.setVisible(true);
	  }
}
