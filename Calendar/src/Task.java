/**
 * Class that process Tasks and gives useful tools when creating / modifying a
 * task.
 * 
 * @author V-ed
 */
public class Task{
	
	private String name;
	private String content;
	private String timeStart;
	private String timeEnd;
	private String where;
	
	/**
	 * Generate a new Task.
	 */
	public Task(){
		// There just to add the javadoc.
	}
	
	/**
	 * Generate a new Task with the content of the parameters.
	 */
	public Task(String name, String content, String timeStart, String timeEnd,
			String where){
		
		this.setName(name);
		this.setContent(content);
		this.setTimeStart(timeStart);
		this.setTimeEnd(timeEnd);
		this.setWhere(where);
		
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		
		// Make the first character Uppercase.
		name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		
		this.name = name;
		
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getTimeStart(){
		return timeStart;
	}
	
	public void setTimeStart(String timeStart){
		this.timeStart = timeStart;
	}
	
	public String getTimeEnd(){
		return timeEnd;
	}
	
	public void setTimeEnd(String timeEnd){
		this.timeEnd = timeEnd;
	}
	
	public String getWhere(){
		return where;
	}
	
	public void setWhere(String where){
		this.where = where;
	}
	
}
