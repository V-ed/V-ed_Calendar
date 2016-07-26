import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Class that process the interior of a tile through it's methods.
 * 
 * @author V-ed
 */
public class Tile{
	
	private int tileDay;
	private int tileMonth;
	private int tileYear;
	
	private boolean isActive;
	
	private boolean hasTasks = false;
	private DayWithEvent tileEvents;
	
	private JLabel dateLabel;
	private String[] taskNames;
	private JButton button;
	
	private JPanel tilePanel;
	private JPanel tasksPanel = new JPanel();
	private JLabel[] taskLabels;
	
	private final Color colorOfToday = new Color(175, 245, 250); // Custom pale turquoise
	public static Color defaultColor;
	
	private final String noTask = "No current tasks";
	private final String tooManyTasks = "[...]";
	private final String butNoTask = "New Task";
	private final String butHasTasks = "View Task(s)";
	
	public Tile(){
		
		initiateTilePanel();
		
	}
	
	/**
	 * Returns the day that said tile is at, in the range of <b>0-<i>[month's
	 * max day - 1]</i></b>.
	 * <p>
	 * <u>Exemple</u>: Current month: <b>1-31</b><br>
	 * First day will return <b>0</b>, last day will return <b>30</b>.
	 */
	public int getTileDay(){
		return tileDay;
	}
	
	/**
	 * @see {@link #getTileDay()}
	 */
	public void setTileDay(int day){
		
		if(day <= YearMonth.of(this.getYear(), this.getTileMonth() + 1)
				.lengthOfMonth() && day > 0){
			
			this.tileDay = day;
			
			this.setDayLabel();
			
		}
		else{
			throw new IllegalArgumentException("No " + day
					+ " day(s) in month \""
					+ Calendar_Main.monthsOfYear[this.getTileMonth()]
					+ "\" of year " + this.getYear());
		}
		
	}
	
	public void testDay(){
		
		try{
			
			boolean tileHasDay = false;
			
			for(int i = 0; i < Calendar_Main.days.length; i++){
				if(Calendar_Main.days[i].getYear() == this.getYear()){
					if(Calendar_Main.days[i].getMonth() == this.getTileMonth()){
						if(Calendar_Main.days[i].getDay() == this.getTileDay()){
							
							tileHasDay = true;
							
							this.setTileEvents(Calendar_Main.days[i]);
							
							i = Calendar_Main.days.length;
							
						}
					}
				}
			}
			
			if(!tileHasDay){
				this.setTasks(null);
			}
			
		}
		catch(NullPointerException e){
			
			this.setTasks(null);
			
		}
		
		Color labelsColor;
		
		if(isActive()){
			labelsColor = Color.BLACK;
		}
		else{
			labelsColor = Color.GRAY;
		}
		
		this.getDayLabel().setForeground(labelsColor);
		
		// Set the color of the first 3 or less tasks to the correct color
		for(int i = 0; (i < this.getTaskNames().length) && (i < 4); i++){
			this.getTaskLabels()[i].setForeground(labelsColor);
		}
		
		if(this.getTaskNames().length == 4 && this.isActive()){
			// Exactly 4 tasks, the last one shown needs the same color as the others
			this.getTaskLabels()[3].setForeground(Color.BLACK);
		}
		else if(this.getTaskNames().length > 4 && this.isActive()){
			// "Too many tasks" message is lighter than the tasks themselves
			this.getTaskLabels()[3].setForeground(Color.GRAY);
		}
		
		// Test if the tile's date is today's date, and sets the color of the
		// tile appropriately.
		int currentDay = Calendar_Main.date.get(Calendar.DAY_OF_MONTH);
		int currentMonth = Calendar_Main.date.get(Calendar.MONTH);
		int currentYear = Calendar_Main.date.get(Calendar.YEAR);
		
		if(this.getYear() == currentYear && this.getTileMonth() == currentMonth
				&& this.getTileDay() == currentDay){
			
			this.tilePanel.setBackground(colorOfToday);
			this.tasksPanel.setBackground(colorOfToday);
			
		}
		else if(this.tilePanel.getBackground() != defaultColor){
			this.tilePanel.setBackground(defaultColor);
			this.tasksPanel.setBackground(defaultColor);
		}
		
	}
	
	public DayWithEvent getTileEvents(){
		return tileEvents;
	}
	
	public void setTileEvents(DayWithEvent newDayWithEvents){
		
		this.tileEvents = newDayWithEvents;
		
		this.setTasks(this.getTileEvents().getTasks());
		
	}
	
	/**
	 * Returns the month that said tile is at, in the range of <b>0-11</b>.
	 * <p>
	 * <u>Exemple</u>: Current month: <b>January</b>. Returns <b>0</b>.<br>
	 * Therefore, <b>December</b> will return <b>11</b>.
	 */
	public int getTileMonth(){
		return tileMonth;
	}
	
	/**
	 * @see {@link #getTileMonth()}
	 */
	public void setTileMonth(int month){
		
		if(month < 12 && month >= 0){
			this.tileMonth = month;
		}
		else{
			throw new IllegalArgumentException("\"" + month
					+ "\" isn't between 0 and 11");
		}
		
	}
	
	/**
	 * Returns the year that said tile is at.
	 */
	public int getYear(){
		return tileYear;
	}
	
	/**
	 * @see {@link #getYear()}
	 */
	public void setYear(int year){
		this.tileYear = year;
	}
	
	public void setTasks(Task[] tasks){
		
		if(tasks == null || tasks.length == 0){
			
			hasTasks(false);
			this.getButton().setText(butNoTask);
			
		}
		else{
			
			hasTasks(true);
			this.getButton().setText(butHasTasks);
			
		}
		
		this.setTaskLabels(tasks);
		
	}
	
	public JButton getButton(){
		return button;
	}
	
	public void setButtonName(String newText){
		this.button.setText(newText);
	}
	
	public boolean isEmpty(){
		return !hasTasks;
	}
	
	public void hasTasks(boolean hasTasks){
		this.hasTasks = hasTasks;
	}
	
	public JLabel getDayLabel(){
		return dateLabel;
	}
	
	public void setDayLabel(){
		this.dateLabel.setText("" + getTileDay());
	}
	
	public String[] getTaskNames(){
		return taskNames;
	}
	
	private void setTaskLabels(Task[] tasks){
		
		if(this.isEmpty()){
			
			String[] noLabel = { noTask };
			
			this.taskNames = noLabel;
			
		}
		else{
			
			String[] newLabels = new String[tasks.length];
			
			for(int i = 0; i < tasks.length; i++){
				
				newLabels[i] = "" + tasks[i].getName();
				
			}
			
			this.taskNames = newLabels;
			
		}
		
		updateTileTasks();
		
	}
	
	public JPanel getTasksPanel(){
		return tasksPanel;
	}
	
	public JLabel[] getTaskLabels(){
		return taskLabels;
	}
	
	public void setTaskLabels(JLabel[] taskLabels){
		this.taskLabels = taskLabels;
	}
	
	public JPanel getTilePanel(){
		return tilePanel;
	}
	
	private void initiateButton(JButton buttonToInitiate){
		
		buttonToInitiate.addActionListener(buttonAction);
		
	}
	
	public boolean isActive(){
		return isActive;
	}
	
	public void setActiveState(boolean isActive){
		this.isActive = isActive;
	}
	
	private ActionListener buttonAction = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent ae){
			
			DayWithEvent dayToModify = Calendar_Main.getDay(getTileDay(),
					getTileMonth(), getYear());
			
			if(dayToModify == null){
				dayToModify = new DayWithEvent(getTileDay(), getTileMonth(),
						getYear());
			}
			
			NewTaskUI gui = new NewTaskUI(dayToModify);
			
			gui.launchUI();
			
		}
	};
	
	public void updateTileTasks(){
		
		this.getTasksPanel().removeAll();
		
		if(this.getTaskNames().length < 5){
			taskLabels = new JLabel[this.getTaskNames().length];
		}
		else{
			taskLabels = new JLabel[4];
		}
		
		for(int i = 0; (i < this.getTaskNames().length) && (i < 4); i++){
			
			taskLabels[i] = new JLabel();
			
			String textToAdd = this.getTaskNames()[i];
			
			this.getTaskLabels()[i].setText(textToAdd);
			this.getTaskLabels()[i].setHorizontalAlignment(JLabel.CENTER);
			
			this.getTasksPanel().add(this.getTaskLabels()[i]);
			
		}
		
		if(this.getTaskNames().length > 4){
			taskLabels[3].setText(tooManyTasks);
		}
		
		if(this.getTaskNames().length > 2){
			this.getTasksPanel().setLayout(new GridLayout(2, 2, 0, 0));
		}
		else{
			this.getTasksPanel().setLayout(new GridLayout(0, 1, 0, 0));
		}
		
		this.getTasksPanel().revalidate();
		this.getTasksPanel().repaint();
		
	}
	
	private void initiateTilePanel(){
		
		this.button = new JButton("New Task");
		
		this.setTasks(null);
		
		this.tilePanel = new JPanel();
		this.dateLabel = new JLabel();
		MyCalendarFrame.contentPane.add(this.tilePanel);
		
		final int inlineMargin = 3;
		
		//		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.tilePanel.setBorder(new CompoundBorder(new LineBorder(new Color(0,
				0, 0)), new EmptyBorder(inlineMargin, inlineMargin,
				inlineMargin, inlineMargin)));
		
		this.getDayLabel().setHorizontalAlignment(JLabel.CENTER);
		
		//Layout for each day panels
		this.tilePanel.setLayout(new BorderLayout());
		
		this.tilePanel.add(this.getDayLabel(), BorderLayout.NORTH);
		
		updateTileTasks();
		
		this.tilePanel.add(this.getTasksPanel(), BorderLayout.CENTER);
		
		//		this.getTasksPanel().setBorder(new LineBorder(new Color(0, 0, 0)));
		
		this.tilePanel.add(this.getButton(), BorderLayout.SOUTH);
		
		initiateButton(this.getButton());
		
	}
}
