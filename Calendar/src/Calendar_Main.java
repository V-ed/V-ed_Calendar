import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Calendar_Main{
	
	static GregorianCalendar date = new GregorianCalendar();
	
	public final static int numberOfDates = 6 * 7;
	
	static JLabel currentMonth = new JLabel();
	
	static JLabel[] daysOfWeekLabels = new JLabel[7];
	
	static Tile[] tiles = new Tile[numberOfDates];
	
	static JButton buttonPastMonth = new JButton("\u2190");	//left arrow
	static JButton buttonNextMonth = new JButton("\u2192");	//right arrow
	
	static String[] daysOfWeek = { "Dimanche", "Lundi", "Mardi", "Mercredi",
			"Jeudi", "Vendredi", "Samedi" };
	
	static String[] monthsOfYear = { "Janvier", "Février", "Mars", "Avril",
			"Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre",
			"Novembre", "Décembre" };
	
	static int currentAppMonth = date.get(Calendar.MONTH);
	static int currentAppYear = date.get(Calendar.YEAR);
	static int currentStartWeekDate = 0;
	
	/**
	 * Variable that receives the days that has tasks.
	 */
	static DayWithEvent[] days;
	
	static MyCalendarFrame mainFrame;
	
	public static void main(String[] args){
		
		buttonPastMonth.addActionListener(pastMonth);
		buttonNextMonth.addActionListener(nextMonth);
		
		mainFrame = new MyCalendarFrame();
		
		updateDates();
		
		
		
	}
	
	public static void addDay(DayWithEvent newDayWithEvents){
		
		if(newDayWithEvents != null){
			
			if(days == null){
				
				days = new DayWithEvent[1];
				days[0] = newDayWithEvents;
				
			}
			else{
				
				DayWithEvent[] temp = days.clone();
				
				days = new DayWithEvent[temp.length + 1];
				
				for(int i = 0; i < temp.length; i++){
					
					days[i] = temp[i];
					
				}
				
				days[temp.length] = newDayWithEvents;
			}
			
			refreshTiles();
			
		}
		
	}
	
	public static void removeDay(int day, int month, int year){
		
		int dayIndexToRemove = Calendar_Main.getDayIndex(day, month, year);
		
		DayWithEvent[] lessDays = new DayWithEvent[Calendar_Main.days.length - 1];
		
		for(int i = 0; i < dayIndexToRemove; i++){
			
			lessDays[i] = Calendar_Main.days[i];
			
		}
		
		for(int i = dayIndexToRemove; i < Calendar_Main.days.length - 1; i++){
			
			lessDays[i] = Calendar_Main.days[i + 1];
			
		}
		
		Calendar_Main.days = lessDays;
		
		if(Calendar_Main.days.length == 0){
			Calendar_Main.days = null;
		}
		
	}
	
	public static DayWithEvent getDay(int day, int month, int year){
		
		DayWithEvent dayToReturn = null;
		
		try{
			for(int i = 0; i < days.length; i++){
				if(days[i].getDay() == day && days[i].getMonth() == month
						&& days[i].getYear() == year){
					dayToReturn = days[i];
					i = days.length;
				}
			}
		}
		catch(NullPointerException e){}
		
		return dayToReturn;
		
	}
	
	public static int getDayIndex(int day, int month, int year){
		
		int dayIndex = -1;
		
		try{
			for(int i = 0; i < days.length; i++){
				if(days[i].getDay() == day && days[i].getMonth() == month
						&& days[i].getYear() == year){
					
					dayIndex = i;
					i = days.length;
					
				}
			}
		}
		catch(NullPointerException e){}
		
		return dayIndex;
		
	}
	
	private static ActionListener pastMonth = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent ae){
			
			if(currentAppMonth > 0){
				currentAppMonth--;
			}
			else{
				currentAppMonth = 11;
				currentAppYear--;
			}
			
			updateDates();
			
		}
	};
	
	private static ActionListener nextMonth = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent ae){
			
			if(currentAppMonth < 11){
				currentAppMonth++;
			}
			else{
				currentAppMonth = 0;
				currentAppYear++;
			}
			
			updateDates();
			
		}
	};
	
	public static void refreshTiles(){
		
		for(int i = 0; i < tiles.length; i++){
			
			tiles[i].testDay();
			
		}
		
	}
	
	/**
	 * Update every days in the calendar.
	 * <p>
	 * When not the current month, the days are greyed out.
	 */
	public static void updateDates(){
		
		//set month and year on top
		currentMonth.setText(monthsOfYear[currentAppMonth] + " "
				+ currentAppYear);
		
		//get the current month's length in days for scoping measures
		int lengthOfActualMonth = YearMonth.of(currentAppYear,
				currentAppMonth + 1).lengthOfMonth();
		int lengthOfPastMonth;
		
		//prevent errors by forcing the use of 1-12 parameter range
		if(currentAppMonth == 0){
			lengthOfPastMonth = YearMonth.of(currentAppYear - 1, 12)
					.lengthOfMonth();
		}
		else{
			lengthOfPastMonth = YearMonth.of(currentAppYear, currentAppMonth)
					.lengthOfMonth();
		}
		
		int currentMonthFirstDay = getFirstDay(currentAppMonth, currentAppYear);
		
		//prevent under ranged array utilization, and make the dates always start
		//on the first row.
		if(currentMonthFirstDay - currentStartWeekDate < 0){
			currentMonthFirstDay += 7;
		}
		
		//starts at first day of the month, ends at the length of the current
		//month's length. CURRENT MONTH DISPLAY
		for(int i = currentMonthFirstDay - currentStartWeekDate; i < lengthOfActualMonth
				+ currentMonthFirstDay - currentStartWeekDate; i++){
			
			tiles[i].setTileMonth(currentAppMonth);
			tiles[i].setYear(currentAppYear);
			
			tiles[i].setTileDay((i - currentMonthFirstDay + currentStartWeekDate) + 1);
			
			tiles[i].setActiveState(true);
			
			tiles[i].testDay();
			
		}
		
		//starts at the beginning, stop before the first day of current month.
		//PAST MONTH DISPLAY
		for(int i = 0; i < currentMonthFirstDay - currentStartWeekDate; i++){
			
			if(currentAppMonth == 0){
				tiles[i].setTileMonth(11);
			}
			else{
				tiles[i].setTileMonth(currentAppMonth - 1);
			}
			
			if(currentAppMonth == 0){
				tiles[i].setYear(currentAppYear - 1);
			}
			else{
				tiles[i].setYear(currentAppYear);
			}
			
			tiles[i].setTileDay(lengthOfPastMonth
					- (currentMonthFirstDay - currentStartWeekDate) + i + 1);
			
			tiles[i].setActiveState(false);
			
			tiles[i].testDay();
			
		}
		
		//start at the end of the current month's length, only increments until
		//the last dsplayed date. FAKE REPRESENTATION OF NEXT MONTH
		for(int i = currentMonthFirstDay + lengthOfActualMonth
				- currentStartWeekDate; i < numberOfDates; i++){
			
			if(currentAppMonth == 11){
				tiles[i].setTileMonth(0);
			}
			else{
				tiles[i].setTileMonth(currentAppMonth + 1);
			}
			
			if(currentAppMonth == 11){
				tiles[i].setYear(currentAppYear + 1);
			}
			else{
				tiles[i].setYear(currentAppYear);
			}
			
			tiles[i].setTileDay(i
					- (currentMonthFirstDay + lengthOfActualMonth - currentStartWeekDate)
					+ 1);
			
			tiles[i].setActiveState(false);
			
			tiles[i].testDay();
			
		}
		
	}
	
	/**
	 * Given the parameters, creates a temporary calendar and make it determine
	 * the first date of said month and year.
	 * 
	 * <pre>
	 * 0 - SUNDAY
	 * 1 - MONDAY
	 * 2 - TUESDAY
	 * 3 - WEDNESDAY
	 * 4 - THURSDAY
	 * 5 - FRIDAY
	 * 6 - SATURDAY
	 * </pre>
	 * 
	 * @param monthToWatch
	 *            : Month to process.
	 * @param yearToWatch
	 *            : Year to process.
	 * @return The first day of said month and year, in a range of 0-6.
	 */
	public static int getFirstDay(int monthToWatch, int yearToWatch){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, monthToWatch);
		cal.set(Calendar.YEAR, yearToWatch);
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
}
