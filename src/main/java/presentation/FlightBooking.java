package presentation;


import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import businessLogic.FlightManager;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

import domain.ConcreteFlight;

public class FlightBooking extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane= null;
	private JLabel lblDepartCity = new JLabel("Departing city:");
	private JLabel lblArrivalCity = new JLabel("Arrival City");
	private JLabel lblYear = new JLabel("Year:");
	private JLabel lblRoomType = new JLabel("Room Type:");
	private JLabel lblMonth = new JLabel("Month:");
	private JLabel lblDay = new JLabel("Day:");;
	private JLabel jLabelResult = new JLabel();
	private JLabel searchResult =   new JLabel();
	
	private JComboBox<String> departCity;
	private JComboBox<String> arrivalCity;
	private DefaultComboBoxModel<String> departures = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> arrivals = new DefaultComboBoxModel<String>();

	
	private JTextField day = null;
	private JComboBox<String> months = null;
	private DefaultComboBoxModel<String> monthNames = new DefaultComboBoxModel<String>();

	private JTextField year = null;
	
	private JRadioButton bussinesTicket = null;
	private JRadioButton firstTicket = null;
	private JRadioButton touristTicket = null;

	private ButtonGroup fareButtonGroup = new ButtonGroup();  
	
	private JButton lookforFlights = null;
	private DefaultComboBoxModel<ConcreteFlight> flightInfo = new DefaultComboBoxModel<ConcreteFlight>();

	
	private JComboBox<ConcreteFlight> flightList = null;
	private JButton bookFlight = null;
	
	

	
	private Collection<ConcreteFlight> concreteFlightCollection;
	
	private FlightManager businessLogic;  //  @jve:decl-index=0:;
	
	
	private ConcreteFlight selectedConcreteFlight;
	
	

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlightBooking frame = new FlightBooking(new FlightManager());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//Custom operations
	public void setBusinessLogic(FlightManager g) {
		businessLogic = g;
	}
	
	private Date newDate(int year,int month,int day) {

	     Calendar calendar = Calendar.getInstance();
	     calendar.set(year, month, day,0,0,0);
	     calendar.set(Calendar.MILLISECOND, 0);

	     return calendar.getTime();
	}
	/**
	 * Create the frame
	 * 
	 * @return void
	 */
	private  FlightBooking(FlightManager logic) {
		
	    this.businessLogic = logic;
		setTitle("Book Flight");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDepartCity = new JLabel("Depart City");
		lblDepartCity.setBounds(21, 11, 103, 16);
		contentPane.add(lblDepartCity);
		
		
		//1 Aldaketa
		departCity = new JComboBox<String>();
		departCity.setBounds(99, 9, 204, 20);
		contentPane.add(departCity);
		departCity.setModel(departures);

		List<String > cities = businessLogic.getAllDepartingCities();
		departures.addAll(cities);
		
		

		//2 Aldaketa
		arrivalCity = new JComboBox<String>();
		arrivalCity.setBounds(99, 37, 204, 20);
		contentPane.add(arrivalCity);
		arrivalCity.setModel(arrivals);
		
		departCity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				arrivals.removeAllElements();

				String selectedDepartCity = (String) departCity.getSelectedItem();
				if (selectedDepartCity != null) {
					List<String> arrivalCities = businessLogic.getArrivalCitiesFrom(selectedDepartCity);
					arrivals.addAll(arrivalCities);
				}
			}
		});
		
		
		
		lblYear = new JLabel("Year:");
		lblYear.setBounds(21, 62, 33, 16);
		contentPane.add(lblYear);
		
		lblMonth = new JLabel("Month:");
		lblMonth.setBounds(117, 62, 50, 16);
		contentPane.add(lblMonth);
	    
		months = new JComboBox<String>();
		months.setBounds(163, 58, 116, 27);
		contentPane.add(months);
		months.setModel(monthNames);
		
		monthNames.addElement("January");
		monthNames.addElement("February");
		monthNames.addElement("March");
		monthNames.addElement("April");
		monthNames.addElement("May");
		monthNames.addElement("June");
		monthNames.addElement("July");
		monthNames.addElement("August");
		monthNames.addElement("September");
		monthNames.addElement("October");
		monthNames.addElement("November");
		monthNames.addElement("December");
		months.setSelectedIndex(1);
		
		lblDay = new JLabel("Day:");
		lblDay.setBounds(291, 62, 38, 16);
		contentPane.add(lblDay);
		
		day = new JTextField();
		day.setText("23");
		day.setBounds(331, 57, 50, 26);
		contentPane.add(day);
		day.setColumns(10);
		
		lblRoomType = new JLabel("Seat Type:");
		lblRoomType.setBounds(21, 242, 84, 16);
		contentPane.add(lblRoomType);
		
		
		
		bussinesTicket = new JRadioButton("Business");
		bussinesTicket.setSelected(true);
		fareButtonGroup.add(bussinesTicket);
		bussinesTicket.setBounds(99, 238, 101, 23);
		contentPane.add(bussinesTicket);
		
		firstTicket = new JRadioButton("First");
		fareButtonGroup.add(firstTicket);
		firstTicket.setBounds(202, 238, 77, 23);
		contentPane.add(firstTicket);
		
		touristTicket = new JRadioButton("Tourist");
		fareButtonGroup.add(touristTicket);
		touristTicket.setBounds(278, 238, 77, 23);
		contentPane.add(touristTicket);
		
		ActionListener seatListener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (selectedConcreteFlight != null) {
		            bookFlight.setEnabled(true);
		        }
		    }
		};

		bussinesTicket.addActionListener(seatListener);
		firstTicket.addActionListener(seatListener);
		touristTicket.addActionListener(seatListener);
		
		
		lookforFlights = new JButton("Look for Concrete Flights");
		lookforFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookFlight.setEnabled(true);
				flightInfo.removeAllElements();
				
				java.util.Date date =newDate(Integer.parseInt(year.getText()),months.getSelectedIndex(),Integer.parseInt(day.getText()));
				 
				concreteFlightCollection=businessLogic.getConcreteFlights(departCity.getSelectedItem().toString(),arrivalCity.getSelectedItem().toString(),date);
				Iterator<ConcreteFlight> flights=concreteFlightCollection.iterator();
				while (flights.hasNext()) 
					flightInfo.addElement(flights.next()); 
				if (concreteFlightCollection.isEmpty()) searchResult.setText("No flights in that city in that date");
				else searchResult.setText("Choose an available flight in this list:");

				bookFlight.setText("Book: " + (String)flightList.getSelectedItem().toString());

			}
		});
		lookforFlights.setBounds(81, 90, 261, 40);
		contentPane.add(lookforFlights);	
		
		jLabelResult = new JLabel("");
		jLabelResult.setBounds(99, 205, 251, 27);
		contentPane.add(jLabelResult);
		
		
		bookFlight = new JButton("Book flight");
		bookFlight.setEnabled(false);
		bookFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num=0;
				boolean error=false;
				if (bussinesTicket.isSelected()) { 
				    num=selectedConcreteFlight.getBussinesNumber();
					if (num>0) selectedConcreteFlight.setBusinessNumber(num-1); else error=true; 
				}
				if (firstTicket.isSelected()) {
					num=selectedConcreteFlight.getFirstNumber();
					if (num>0) selectedConcreteFlight.setFirstNumber(num-1); else error=true;
				}
				if (touristTicket.isSelected()) {
					num=selectedConcreteFlight.getTouristNumber();
					if (num>0) selectedConcreteFlight.setTouristNumber(num-1); else error=true;
				}
				if (error) bookFlight.setText("Error: There were no seats available!");
				else bookFlight.setText("Booked. #seat left: "+(num-1));
				bookFlight.setEnabled(false);
			}
		});
		bookFlight.setBounds(31, 273, 399, 40);
		contentPane.add(bookFlight);

		year = new JTextField();
		year.setText("2026");
		year.setBounds(57, 57, 50, 26);
		contentPane.add(year);
		year.setColumns(10);
		
		lblArrivalCity.setBounds(21, 39, 84, 16);
		contentPane.add(lblArrivalCity);
		
		searchResult.setBounds(57, 130, 314, 16);
		contentPane.add(searchResult);
		
		flightList = new JComboBox<ConcreteFlight>();
		flightList.setBounds(57, 161, 324, 34);
		contentPane.add(flightList);
		flightList.setModel(flightInfo);
		flightList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selectedConcreteFlight = (ConcreteFlight) flightList.getSelectedItem();

				if (selectedConcreteFlight == null) { 
					return;
				}

				bussinesTicket.setEnabled(selectedConcreteFlight.getBussinesNumber() > 0);

				firstTicket.setEnabled(selectedConcreteFlight.getFirstNumber() > 0);

				touristTicket.setEnabled(selectedConcreteFlight.getTouristNumber() > 0);

				fareButtonGroup.clearSelection();
				bookFlight.setEnabled(false);
			}
		});


	}
}  //  @jve:decl-index=0:visual-constraint="18,9"
