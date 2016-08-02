import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class MyCalendarFrame extends JFrame{
	
	static JPanel[] daysOfWeekPanels = new JPanel[7];
	
	static JPanel contentPane = new JPanel();
	static JPanel topPane = new JPanel();
	
	static JButton buttonPrevious = new JButton("\u2190");	//left arrow
	static JButton buttonNext = new JButton("\u2192");	//right arrow
	
	private JFrame myFrame = new JFrame();
	
	private final int screenMargins = 50;
	
	/**
	 * Launch the application.
	 */
	public MyCalendarFrame(){
		initUI();
	}
	
	private void setMenuActions(){
		
		JMenuBar menuBar = new JMenuBar();
		myFrame.setJMenuBar(menuBar);
		
		JMenu optionMenu = new JMenu("Options");
		menuBar.add(optionMenu);
		
		JMenu startingDate = new JMenu("Date to start");
		optionMenu.add(startingDate);
		
		JMenuItem gotoToday = new JMenuItem("Go to today's month");
		optionMenu.add(gotoToday);
		
		gotoToday.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				
				//resets the date to today
				Calendar_Main.currentAppMonth = Calendar_Main.date
						.get(Calendar.MONTH);
				Calendar_Main.currentAppYear = Calendar_Main.date
						.get(Calendar.YEAR);
				
				//when going to today, things move, so we need to update
				Calendar_Main.updateDates();
				
			}
		});
		
		JMenuItem[] possibleDates = new JMenuItem[7];
		
		for(int i = 0; i < possibleDates.length; i++){
			possibleDates[i] = new JMenuItem(Calendar_Main.daysOfWeek[i]);
			
			startingDate.add(possibleDates[i]);
			
			int workaround = i;
			
			possibleDates[i].addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent ae){
					
					//Action attributed to an item change the starting date to its number
					Calendar_Main.currentStartWeekDate = workaround;
					
					//Change the labels of the weekdays name to reflect the change
					for(int j = 0; j < Calendar_Main.daysOfWeek.length; j++){
						
						Calendar_Main.daysOfWeekLabels[j]
								.setText(Calendar_Main.daysOfWeek[(j + Calendar_Main.currentStartWeekDate) % 7]);
						
					}
					
					//when changing the start date, things move, so we need to update
					Calendar_Main.updateDates();
					
				}
			});
			
		}
		
	}
	
	private void cleanFrame(){
		
		try{
			myFrame.remove(topPane);
			myFrame.remove(contentPane);
			
			topPane.removeAll();
			contentPane.removeAll();
		}
		catch(NullPointerException e){}
		
		this.setMenuActions();
		
	}
	
	private void setWeekView(){
		
		// TODO Program setWeekView() method
		
		cleanFrame();
		
		JPanel daysLabelPanel = new JPanel();
		
		// daysLabelPanel.setBorder(null);	//in case I want a border around the whole calendar dates
		
		daysLabelPanel.setLayout(new GridLayout(1, 7, 4, 0));
		
		for(int i = 0; i < Calendar_Main.daysOfWeek.length; i++){
			daysLabelPanel.add(daysOfWeekPanels[i]);
		}
		
		//JPanel getting the month / buttons / year / weekdays labels on.
		topPane.setLayout(new BorderLayout());
		
		//Layout for the topPanel, month/year top, button sides and weekdays under
		Calendar_Main.currentMonth.setHorizontalAlignment(JLabel.CENTER);
		Calendar_Main.currentMonth.setFont(Calendar_Main.currentMonth.getFont()
				.deriveFont(24f));
		
		topPane.add(Calendar_Main.currentMonth, BorderLayout.CENTER);
		topPane.add(buttonPrevious, BorderLayout.WEST);
		topPane.add(buttonNext, BorderLayout.EAST);
		topPane.add(daysLabelPanel, BorderLayout.SOUTH);
		
		contentPane.setLayout(new GridLayout(1, 7, 4, 4));
		
		Tile.defaultColor = contentPane.getBackground();
		
		for(int i = 0; i < 7; i++){
			
			Calendar_Main.tiles[i] = new Tile();
			
			contentPane.add(Calendar_Main.tiles[i].getTilePanel());
			
		}
		
		buttonPrevious.addActionListener(pastMonth);
		buttonNext.addActionListener(nextMonth);
		
		myFrame.add(topPane, BorderLayout.NORTH);
		myFrame.add(contentPane);
		
	}
	
	private void setMonthView(){
		
		cleanFrame();
		
		JPanel daysLabelPanel = new JPanel();
		
		// daysLabelPanel.setBorder(null);	//in case I want a border around the whole calendar dates
		
		daysLabelPanel.setLayout(new GridLayout(1, 7, 4, 0));
		
		for(int i = 0; i < Calendar_Main.daysOfWeek.length; i++){
			daysLabelPanel.add(daysOfWeekPanels[i]);
		}
		
		//JPanel getting the month / buttons / year / weekdays labels on.
		topPane.setLayout(new BorderLayout());
		
		//Layout for the topPanel, month/year top, button sides and weekdays under
		Calendar_Main.currentMonth.setHorizontalAlignment(JLabel.CENTER);
		Calendar_Main.currentMonth.setFont(Calendar_Main.currentMonth.getFont()
				.deriveFont(24f));
		
		topPane.add(Calendar_Main.currentMonth, BorderLayout.CENTER);
		topPane.add(buttonPrevious, BorderLayout.WEST);
		topPane.add(buttonNext, BorderLayout.EAST);
		topPane.add(daysLabelPanel, BorderLayout.SOUTH);
		
		contentPane.setLayout(new GridLayout(6, 7, 4, 4));
		
		Tile.defaultColor = contentPane.getBackground();
		
		for(int i = 0; i < Calendar_Main.tiles.length; i++){
			
			Calendar_Main.tiles[i] = new Tile();
			
			contentPane.add(Calendar_Main.tiles[i].getTilePanel());
			
		}
		
		buttonPrevious.addActionListener(pastMonth);
		buttonNext.addActionListener(nextMonth);
		
		//add the content to the main frame
		myFrame.getContentPane().add(topPane, BorderLayout.NORTH);
		myFrame.getContentPane().add(contentPane);
		
	}
	
	/**
	 * Create the frame.
	 */
	protected void initUI(){
		
		//Gets the width/height of the current screen's resoltion and apply a margin
		//that affects the 4 axis.
		final int width = (int)Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth()
				- 2 * screenMargins;
		final int height = (int)Toolkit.getDefaultToolkit().getScreenSize()
				.getHeight()
				- 2 * screenMargins;
		
		myFrame.setTitle("Calendar Test");
		
		//Sets the default size to half the current sceen's resolution, and place
		//it centered in the screen upon launch.
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setBounds(((int)Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth() / 2 - width / 2), ((int)Toolkit.getDefaultToolkit()
				.getScreenSize().getHeight() / 2 - height / 2), width, height);
		
		for(int i = 0; i < Calendar_Main.daysOfWeek.length; i++){
			daysOfWeekPanels[i] = new JPanel();
			
			Calendar_Main.daysOfWeekLabels[i] = new JLabel(
					Calendar_Main.daysOfWeek[i]);
			
			daysOfWeekPanels[i].add(Calendar_Main.daysOfWeekLabels[i]);
		}
		
		this.setMenuActions();
		
		this.setMonthView();
		this.setWeekView(); // TODO Remove testing code snippet
		
		//setVisible at the end or elements changes won't be affected
		myFrame.setVisible(true);
		
	}
	
	private ActionListener pastMonth = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent ae){
			
			if(Calendar_Main.currentAppMonth > 0){
				Calendar_Main.currentAppMonth--;
			}
			else{
				Calendar_Main.currentAppMonth = 11;
				Calendar_Main.currentAppYear--;
			}
			
			Calendar_Main.updateDates();
			
		}
	};
	
	private ActionListener nextMonth = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent ae){
			
			if(Calendar_Main.currentAppMonth < 11){
				Calendar_Main.currentAppMonth++;
			}
			else{
				Calendar_Main.currentAppMonth = 0;
				Calendar_Main.currentAppYear++;
			}
			
			Calendar_Main.updateDates();
			
		}
	};
	
}
