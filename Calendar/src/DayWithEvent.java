import java.time.YearMonth;

/**
 * Class that represents a date with events in the calendar.
 * 
 * @author V-ed
 */
public class DayWithEvent{
	
	private int day;
	private int month;
	private int year;
	
	private Task[] tasks;
	
	public DayWithEvent(int day, int month, int year){
		
		this.setMonth(month);
		this.setYear(year);
		this.setDay(day);
		
	}
	
	public int getNumberOfTask(){
		
		int numberOfTasks = 0;
		
		try{
			numberOfTasks = this.getTasks().length;
		}
		catch(NullPointerException e){
			numberOfTasks = 0;
		}
		
		return numberOfTasks;
		
	}
	
	public Task[] getTasks(){
		return tasks;
	}
	
	public String[] getTasksNames(){
		
		String[] tasksNames = new String[getTasks().length];
		
		for(int i = 0; i < tasksNames.length; i++){
			tasksNames[i] = getTasks()[i].getName();
		}
		
		return tasksNames;
		
	}
	
	public void setTasks(Task[] tasks){
		this.tasks = tasks;
	}
	
	public void addTask(Task taskToAdd){
		
		if(this.getTasks() != null){
			
			Task[] oldTasks = tasks.clone();
			
			tasks = new Task[oldTasks.length + 1];
			
			for(int i = 0; i < oldTasks.length; i++){
				
				tasks[i] = oldTasks[i];
				
			}
			
			tasks[oldTasks.length] = taskToAdd;
			
		}
		else{
			tasks = new Task[1];
			tasks[0] = taskToAdd;
			
		}
		
	}
	
	public boolean modifyTask(int taskNumber, Task modifiedTask){
		
		boolean success = false;
		
		try{
			
			this.tasks[taskNumber] = modifiedTask;
			success = true;
			
		}
		catch(Exception e){}
		
		return success;
		
	}
	
	public boolean removeTask(int taskNumber){
		
		boolean success = false;
		
		try{
			
			Task[] lessTasks = new Task[tasks.length - 1];
			
			for(int i = 0; i < taskNumber; i++){
				lessTasks[i] = tasks[i];
			}
			
			for(int i = taskNumber; i < tasks.length - 1; i++){
				lessTasks[i] = tasks[i + 1];
			}
			
			tasks = lessTasks;
			
			success = true;
			
		}
		catch(Exception e){
			this.setTasks(null);
		}
		
		return success;
		
	}
	
	public int getDay(){
		return day;
	}
	
	public void setDay(int day){
		
		int lengthOfMonth = YearMonth.of(getYear(), getMonth() + 1)
				.lengthOfMonth();
		
		if(day > 0 && day <= lengthOfMonth){
			this.day = day;
		}
	}
	
	public int getMonth(){
		return month;
	}
	
	public void setMonth(int month){
		if(month >= 0 && month < 12){
			this.month = month;
		}
	}
	
	public int getYear(){
		return year;
	}
	
	public void setYear(int year){
		this.year = year;
	}
	
}
