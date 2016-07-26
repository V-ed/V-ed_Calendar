import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JButton;

import java.text.DecimalFormat;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class NewTaskUI extends JFrame{
	
	private int day;
	private int month;
	private int year;
	
	/**
	 * DayWithEvent of the day of which the UI has been opened from.
	 */
	private DayWithEvent workingDay;
	
	private JPanel contentPane;
	
	private Field view_nameTextArea;
	private Field view_contentTextArea;
	private JLabel view_timeStartValue;
	private JLabel view_timeEndValue;
	private Field view_whereTextArea;
	
	private Field add_nameTextArea;
	private Field add_contentTextArea;
	private JSpinner add_timeStartSpinner;
	private JSpinner add_timeEndSpinner;
	private Field add_whereTextArea;
	
	private Field mod_nameTextArea;
	private Field mod_contentTextArea;
	private JSpinner mod_timeStartSpinner;
	private JSpinner mod_timeEndSpinner;
	private Field mod_whereTextArea;
	
	private Field del_nameTextArea;
	private Field del_contentTextArea;
	private JLabel del_timeStartValue;
	private JLabel del_timeEndValue;
	private Field del_whereTextArea;
	
	private final String DEFAULT_TEXT_NAME = "Name to be displayed";
	private final String DEFAULT_TEXT_CONTENT = "Description of task";
	private final String DEFAULT_TEXT_WHERE = "General location keywords";
	
	@SuppressWarnings("rawtypes")
	private JComboBox view_chooseTaskBox = new JComboBox();
	
	@SuppressWarnings("rawtypes")
	private JComboBox mod_chooseTaskBox = new JComboBox();
	
	@SuppressWarnings("rawtypes")
	private JComboBox del_chooseTaskBox = new JComboBox();
	
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	private int hour = 0;
	private int minutes = 0;
	private int minuteSteps = 15;
	private String[] spinnersOptions = new String[(60 / minuteSteps) * 24];
	
	public void reset(){
		
		this.add_nameTextArea.setVisualState(false);
		this.add_contentTextArea.setVisualState(false);
		this.add_timeStartSpinner.setValue("0:00");
		this.add_timeEndSpinner.setValue("0:00");
		this.add_whereTextArea.setVisualState(false);
		
	}
	
	private String getNewTaskName(){
		return add_nameTextArea.getText();
	}
	
	private String getNewTaskContent(){
		
		if(add_contentTextArea.isChanged()){
			return add_contentTextArea.getText();
		}
		else{
			return "";
		}
		
	}
	
	private String getNewTaskStartTime(){
		return add_timeStartSpinner.getValue().toString();
	}
	
	private String getNewTaskEndTime(){
		return add_timeEndSpinner.getValue().toString();
	}
	
	private String getNewTaskWhere(){
		
		if(add_whereTextArea.isChanged()){
			return add_whereTextArea.getText();
		}
		else{
			return "";
		}
		
	}
	
	/**
	 * Returns the current data from the text fields of the NewTaskUI's
	 * <b><i>add</i></b> tab.
	 * <p>
	 * Return order:
	 * 
	 * <pre>
	 * New Task Name
	 * New Task Content
	 * New Task Start Time
	 * New Task End Time
	 * New Task Where (keywords)
	 * </pre>
	 */
	public String[] getDataNewTask(){
		
		String[] data = { this.getNewTaskName(), this.getNewTaskContent(),
				this.getNewTaskStartTime(), this.getNewTaskEndTime(),
				this.getNewTaskWhere() };
		
		return data;
		
	}
	
	private void sendTaskData(Task taskToAdd){
		
		workingDay.addTask(taskToAdd);
		Calendar_Main.addDay(workingDay);
		reset();
		updateFrame();
		
	}
	
	private FocusListener contentListener = new FocusListener(){
		
		@Override
		public void focusGained(FocusEvent e){
			
			if(((Field)e.getComponent()).isChanged() == false){
				
				((Field)e.getComponent()).setVisualState(true);
				
			}
			
		}
		
		@Override
		public void focusLost(FocusEvent e){
			
			if(((Field)e.getComponent()).getText().matches("\\s*+")){
				
				((Field)e.getComponent()).setVisualState(false);
				
			}
			else{
				
				((Field)e.getComponent()).setChangedState(true);
				
			}
			
		}
		
	};
	
	private ActionListener addAndRestoreButtonListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent ae){
			
			if(add_nameTextArea.isChanged()){
				
				Task newTask = new Task();
				
				newTask.setName(getNewTaskName());
				newTask.setContent(getNewTaskContent());
				newTask.setTimeStart(getNewTaskStartTime());
				newTask.setTimeEnd(getNewTaskEndTime());
				newTask.setWhere(getNewTaskWhere());
				
				sendTaskData(newTask);
				
			}
			else{
				
				JOptionPane.showMessageDialog(new JFrame(),
						"Please enter a name for your new Task.", "No name!",
						JOptionPane.WARNING_MESSAGE);
				
			}
			
		}
		
	};
	
	private ActionListener addAndCloseButtonListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent ae){
			
			if(add_nameTextArea.isChanged()){
				
				Task newTask = new Task();
				
				newTask.setName(getNewTaskName());
				newTask.setContent(getNewTaskContent());
				newTask.setTimeStart(getNewTaskStartTime());
				newTask.setTimeEnd(getNewTaskEndTime());
				newTask.setWhere(getNewTaskWhere());
				
				sendTaskData(newTask);
				
				NewTaskUI.this.dispose();	// Close the current window
				
			}
			else{
				
				JOptionPane.showMessageDialog(new JFrame(),
						"Please enter a name for your new Task.", "No name!",
						JOptionPane.WARNING_MESSAGE);
				
			}
			
		}
		
	};
	
	private ActionListener view_comboBoxListener = new ActionListener(){
		
		@Override
		public void actionPerformed(ActionEvent e){
			
			updateViewTab();
			
		}
		
	};
	
	private ActionListener mod_comboBoxListener = new ActionListener(){
		
		@Override
		public void actionPerformed(ActionEvent e){
			
			updateModifyTab();
			
		}
		
	};
	
	private ActionListener del_comboBoxListener = new ActionListener(){
		
		@Override
		public void actionPerformed(ActionEvent e){
			
			updateDeleteTab();
			
		}
		
	};
	
	private ActionListener modifyTaskListener = new ActionListener(){
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void actionPerformed(ActionEvent e){
			
			int taskNumber = mod_chooseTaskBox.getSelectedIndex();
			
			Task modifiedTask = new Task();
			
			modifiedTask.setName(mod_nameTextArea.getText());
			modifiedTask.setContent(mod_contentTextArea.getText());
			modifiedTask.setTimeStart((String)mod_timeStartSpinner.getValue());
			modifiedTask.setTimeEnd((String)mod_timeEndSpinner.getValue());
			modifiedTask.setWhere(mod_whereTextArea.getText());
			
			Calendar_Main.getDay(day, month, year).modifyTask(taskNumber,
					modifiedTask);
			
			// Update the JComboBox for both the Modify tab and the Delete tab.
			int mod_chooseBoxIndex = mod_chooseTaskBox.getSelectedIndex();
			mod_chooseTaskBox.setModel(new DefaultComboBoxModel(workingDay
					.getTasksNames()));
			mod_chooseTaskBox.setSelectedIndex(mod_chooseBoxIndex);
			
			int del_chooseBoxIndex = del_chooseTaskBox.getSelectedIndex();
			del_chooseTaskBox.setModel(new DefaultComboBoxModel(workingDay
					.getTasksNames()));
			del_chooseTaskBox.setSelectedIndex(del_chooseBoxIndex);
			
			updateViewTab();
			updateDeleteTab();
			
			Calendar_Main.refreshTiles();
			
		}
	};
	
	private ActionListener deleteTaskListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			int taskNumber = del_chooseTaskBox.getSelectedIndex();
			
			if(Calendar_Main.getDay(day, month, year).removeTask(taskNumber)){
				
				updateFrame();
				
				updateViewTab();
				updateModifyTab();
				updateDeleteTab();
				
			}
			else{
				workingDay.removeTask(taskNumber);
				updateFrame();
			}
			
			Calendar_Main.refreshTiles();
			
		}
	};
	
	/**
	 * Update the view tab with the task of the current selected task.
	 */
	private void updateViewTab(){
		
		try{
			view_nameTextArea.setText(workingDay.getTasks()[view_chooseTaskBox
					.getSelectedIndex()].getName());
			view_contentTextArea
					.setText(workingDay.getTasks()[view_chooseTaskBox
							.getSelectedIndex()].getContent());
			view_timeStartValue
					.setText(workingDay.getTasks()[view_chooseTaskBox
							.getSelectedIndex()].getTimeStart());
			view_timeEndValue.setText(workingDay.getTasks()[view_chooseTaskBox
					.getSelectedIndex()].getTimeEnd());
			view_whereTextArea.setText(workingDay.getTasks()[view_chooseTaskBox
					.getSelectedIndex()].getWhere());
		}
		catch(Exception e){}
		
	}
	
	/**
	 * Update the modify tab with the task of the current selected task.
	 */
	private void updateModifyTab(){
		
		try{
			mod_nameTextArea.setText(workingDay.getTasks()[mod_chooseTaskBox
					.getSelectedIndex()].getName());
			mod_contentTextArea.setText(workingDay.getTasks()[mod_chooseTaskBox
					.getSelectedIndex()].getContent());
			mod_timeStartSpinner
					.setValue(workingDay.getTasks()[mod_chooseTaskBox
							.getSelectedIndex()].getTimeStart());
			mod_timeEndSpinner.setValue(workingDay.getTasks()[mod_chooseTaskBox
					.getSelectedIndex()].getTimeEnd());
			mod_whereTextArea.setText(workingDay.getTasks()[mod_chooseTaskBox
					.getSelectedIndex()].getWhere());
		}
		catch(Exception e){}
		
	}
	
	/**
	 * Update the modify tab with the task of the current selected task.
	 */
	private void updateDeleteTab(){
		
		try{
			del_nameTextArea.setText(workingDay.getTasks()[del_chooseTaskBox
					.getSelectedIndex()].getName());
			del_contentTextArea.setText(workingDay.getTasks()[del_chooseTaskBox
					.getSelectedIndex()].getContent());
			del_timeStartValue.setText(workingDay.getTasks()[del_chooseTaskBox
					.getSelectedIndex()].getTimeStart());
			del_timeEndValue.setText(workingDay.getTasks()[del_chooseTaskBox
					.getSelectedIndex()].getTimeEnd());
			del_whereTextArea.setText(workingDay.getTasks()[del_chooseTaskBox
					.getSelectedIndex()].getWhere());
		}
		catch(Exception e){}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateFrame(){
		
		//Comment "if" statements to design the frame
		if(Calendar_Main.getDay(day, month, year) != null){
			
			JPanel viewExisting = new JPanel();
			viewExisting.setLayout(null);
			
			JPanel modifyExisting = new JPanel();
			modifyExisting.setLayout(null);
			
			JPanel deleteTasks = new JPanel();
			deleteTasks.setLayout(null);
			
			try{
				
				DefaultComboBoxModel comboBoxOptions = new DefaultComboBoxModel(
						workingDay.getTasksNames());
				
				view_chooseTaskBox.setModel(comboBoxOptions);
				view_chooseTaskBox.setBounds(100, 15, 300, 20);
				view_chooseTaskBox.addActionListener(view_comboBoxListener);
				
				mod_chooseTaskBox.setModel(comboBoxOptions);
				mod_chooseTaskBox.setBounds(100, 15, 300, 20);
				mod_chooseTaskBox.addActionListener(mod_comboBoxListener);
				
				del_chooseTaskBox.setModel(comboBoxOptions);
				del_chooseTaskBox.setBounds(100, 15, 300, 20);
				del_chooseTaskBox.addActionListener(del_comboBoxListener);
			}
			catch(Exception e){}
			
			boolean hasTasksAndFresh = (tabbedPane.getTabCount() == 0 || tabbedPane
					.getTabCount() == 1) && workingDay.getNumberOfTask() != 0;
			
			if(hasTasksAndFresh){
				
				// VIEW TAB COMPONENTS
				
				JLabel view_chooseTaskLabel = new JLabel("Choose Task :");
				view_chooseTaskLabel.setBounds(15, 15, 80, 20);
				viewExisting.add(view_chooseTaskLabel);
				
				viewExisting.add(view_chooseTaskBox);
				
				JLabel view_nameLabel = new JLabel("Name :");
				view_nameLabel.setBounds(15, 50, 80, 20);
				viewExisting.add(view_nameLabel);
				
				view_nameTextArea = new Field(100, 50, 300, 20);
				view_nameTextArea.setVisualEditable(false);
				viewExisting.add(view_nameTextArea);
				
				JLabel view_contentLabel = new JLabel("Description :");
				view_contentLabel.setBounds(15, 80, 80, 20);
				viewExisting.add(view_contentLabel);
				
				view_contentTextArea = new Field();
				view_contentTextArea.setLineWrap(true);
				view_contentTextArea.setVisualEditable(false);
				
				JScrollPane view_scrollPane_Content = new JScrollPane(
						view_contentTextArea);
				view_scrollPane_Content.setBounds(100, 80, 300, 160);
				viewExisting.add(view_scrollPane_Content);
				
				JLabel view_timeStartLabel = new JLabel("Start Time :");
				view_timeStartLabel.setBounds(15, 250, 80, 20);
				viewExisting.add(view_timeStartLabel);
				
				view_timeStartValue = new JLabel();
				view_timeStartValue.setBounds(100, 250, 100, 20);
				view_timeStartValue
						.setFont(new Font("Tahoma", Font.ITALIC, 11));
				viewExisting.add(view_timeStartValue);
				
				JLabel view_timeEndLabel = new JLabel("  End Time :");
				view_timeEndLabel.setBounds(215, 250, 80, 20);
				viewExisting.add(view_timeEndLabel);
				
				view_timeEndValue = new JLabel();
				view_timeEndValue.setBounds(300, 250, 100, 20);
				view_timeEndValue.setFont(new Font("Tahoma", Font.ITALIC, 11));
				viewExisting.add(view_timeEndValue);
				
				JLabel view_whereLabel = new JLabel("Where :");
				view_whereLabel.setBounds(15, 280, 80, 20);
				viewExisting.add(view_whereLabel);
				
				view_whereTextArea = new Field(100, 280, 300, 20);
				view_whereTextArea.setColumns(10);
				view_whereTextArea.setVisualEditable(false);
				viewExisting.add(view_whereTextArea);
				
				tabbedPane.addTab("View Task(s)", null, viewExisting, null);
				
				addAddTab();
				
				if(tabbedPane.getTabCount() == 3){
					tabbedPane.removeTabAt(0);
					tabbedPane.setSelectedIndex(1);
				}
				
				// MODIFY TAB COMPONENTS
				
				JLabel mod_chooseTaskLabel = new JLabel("Choose Task :");
				mod_chooseTaskLabel.setBounds(15, 15, 80, 20);
				
				modifyExisting.add(mod_chooseTaskLabel);
				modifyExisting.add(mod_chooseTaskBox);
				
				JLabel mod_nameLabel = new JLabel("Name :");
				mod_nameLabel.setBounds(15, 50, 80, 20);
				modifyExisting.add(mod_nameLabel);
				
				mod_nameTextArea = new Field(100, 50, 300, 20);
				modifyExisting.add(mod_nameTextArea);
				
				JLabel mod_contentLabel = new JLabel("Description :");
				mod_contentLabel.setBounds(15, 80, 80, 20);
				modifyExisting.add(mod_contentLabel);
				
				mod_contentTextArea = new Field();
				mod_contentTextArea.setLineWrap(true);
				
				JScrollPane mod_scrollPane_Content = new JScrollPane(
						mod_contentTextArea);
				mod_scrollPane_Content.setBounds(100, 80, 300, 115);
				modifyExisting.add(mod_scrollPane_Content);
				
				JLabel mod_timeStartLabel = new JLabel("Start Time :");
				mod_timeStartLabel.setBounds(15, 205, 80, 20);
				modifyExisting.add(mod_timeStartLabel);
				
				mod_timeStartSpinner = new JSpinner();
				mod_timeStartSpinner.setBounds(100, 205, 100, 20);
				mod_timeStartSpinner.setModel(new SpinnerListModel(
						spinnersOptions));
				modifyExisting.add(mod_timeStartSpinner);
				
				JLabel mod_timeEndLabel = new JLabel("  End Time :");
				mod_timeEndLabel.setBounds(215, 205, 80, 20);
				modifyExisting.add(mod_timeEndLabel);
				
				mod_timeEndSpinner = new JSpinner();
				mod_timeEndSpinner.setBounds(300, 205, 100, 20);
				mod_timeEndSpinner.setModel(new SpinnerListModel(
						spinnersOptions));
				modifyExisting.add(mod_timeEndSpinner);
				
				JLabel mod_whereLabel = new JLabel("Where :");
				mod_whereLabel.setBounds(15, 235, 80, 20);
				modifyExisting.add(mod_whereLabel);
				
				mod_whereTextArea = new Field(100, 235, 300, 20);
				mod_whereTextArea.setColumns(10);
				modifyExisting.add(mod_whereTextArea);
				
				JButton mod_modifyButton = new JButton("Modify Task");
				mod_modifyButton.addActionListener(modifyTaskListener);
				mod_modifyButton.setBounds(15, 265, 385, 35);
				modifyExisting.add(mod_modifyButton);
				
				tabbedPane.addTab("Modify Existing Tasks", null,
						modifyExisting, null);
				
				// DELETE TAB COMPONENTS
				
				JLabel del_chooseTaskLabel = new JLabel("Choose Task :");
				del_chooseTaskLabel.setBounds(15, 15, 80, 20);
				deleteTasks.add(del_chooseTaskLabel);
				
				deleteTasks.add(del_chooseTaskBox);
				
				JLabel del_nameLabel = new JLabel("Name :");
				del_nameLabel.setBounds(15, 50, 80, 20);
				deleteTasks.add(del_nameLabel);
				
				del_nameTextArea = new Field(100, 50, 300, 20);
				del_nameTextArea.setVisualEditable(false);
				deleteTasks.add(del_nameTextArea);
				
				JLabel del_contentLabel = new JLabel("Description :");
				del_contentLabel.setBounds(15, 80, 80, 20);
				deleteTasks.add(del_contentLabel);
				
				del_contentTextArea = new Field();
				del_contentTextArea.setLineWrap(true);
				del_contentTextArea.setVisualEditable(false);
				
				JScrollPane del_scrollPane_Content = new JScrollPane(
						del_contentTextArea);
				del_scrollPane_Content.setBounds(100, 80, 300, 115);
				deleteTasks.add(del_scrollPane_Content);
				
				JLabel del_timeStartLabel = new JLabel("Start Time :");
				del_timeStartLabel.setBounds(15, 205, 80, 20);
				deleteTasks.add(del_timeStartLabel);
				
				del_timeStartValue = new JLabel();
				del_timeStartValue.setBounds(100, 205, 100, 20);
				del_timeStartValue.setFont(new Font("Tahoma", Font.ITALIC, 11));
				deleteTasks.add(del_timeStartValue);
				
				JLabel del_timeEndLabel = new JLabel("  End Time :");
				del_timeEndLabel.setBounds(215, 205, 80, 20);
				deleteTasks.add(del_timeEndLabel);
				
				del_timeEndValue = new JLabel();
				del_timeEndValue.setBounds(300, 205, 100, 20);
				del_timeEndValue.setFont(new Font("Tahoma", Font.ITALIC, 11));
				deleteTasks.add(del_timeEndValue);
				
				JLabel del_whereLabel = new JLabel("Where :");
				del_whereLabel.setBounds(15, 235, 80, 20);
				deleteTasks.add(del_whereLabel);
				
				del_whereTextArea = new Field(100, 235, 300, 20);
				del_whereTextArea.setColumns(10);
				del_whereTextArea.setVisualEditable(false);
				deleteTasks.add(del_whereTextArea);
				
				JButton del_modifyButton = new JButton("Delete Task");
				del_modifyButton.addActionListener(deleteTaskListener);
				del_modifyButton.setBounds(15, 265, 385, 35);
				deleteTasks.add(del_modifyButton);
				
				tabbedPane.addTab("Delete Task(s)", null, deleteTasks, null);
				
				updateViewTab();
				updateModifyTab();
				updateDeleteTab();
				
			}
			else if((tabbedPane.getTabCount() != 0 && tabbedPane.getTabCount() != 1)
					&& workingDay.getNumberOfTask() == 0){
				
				tabbedPane.removeTabAt(3);	// Remove Delete Tab
				tabbedPane.removeTabAt(2);	// Remove Modify Tab
				tabbedPane.removeTabAt(0);	// Remove View Tab
				
				Calendar_Main.removeDay(day, month, year);
				
			}
			
		}
		else if(tabbedPane.getTabCount() != 4){
			addAddTab();
		}
		
	}
	
	private void addAddTab(){
		
		JPanel addTab = new JPanel();
		addTab.setLayout(null);
		
		JLabel add_nameLabel = new JLabel("Name :");
		add_nameLabel.setBounds(15, 15, 80, 20);
		addTab.add(add_nameLabel);
		
		add_nameTextArea = new Field(100, 15, 300, 20);
		add_nameTextArea.setDefaultText(DEFAULT_TEXT_NAME);
		add_nameTextArea.setVisualState(false);
		add_nameTextArea.addFocusListener(contentListener);
		addTab.add(add_nameTextArea);
		
		JLabel add_contentLabel = new JLabel("Description :");
		add_contentLabel.setBounds(15, 50, 80, 20);
		addTab.add(add_contentLabel);
		
		add_contentTextArea = new Field();
		add_contentTextArea.setDefaultText(DEFAULT_TEXT_CONTENT);
		add_contentTextArea.setVisualState(false);
		add_contentTextArea.setLineWrap(true);
		add_contentTextArea.addFocusListener(contentListener);
		JScrollPane add_scrollPane_Content = new JScrollPane(
				add_contentTextArea);
		add_scrollPane_Content.setLocation(100, 50);
		add_scrollPane_Content.setSize(300, 115);
		
		addTab.add(add_scrollPane_Content);
		
		JLabel add_timeStartLabel = new JLabel("Start Time :");
		add_timeStartLabel.setBounds(15, 180, 80, 20);
		addTab.add(add_timeStartLabel);
		add_timeStartSpinner = new JSpinner();
		add_timeStartSpinner.setModel(new SpinnerListModel(spinnersOptions));
		add_timeStartSpinner.setBounds(100, 180, 100, 20);
		addTab.add(add_timeStartSpinner);
		
		JLabel add_timeEndLabel = new JLabel("  End Time :");
		add_timeEndLabel.setBounds(215, 180, 80, 20);
		addTab.add(add_timeEndLabel);
		
		add_timeEndSpinner = new JSpinner();
		add_timeEndSpinner.setModel(new SpinnerListModel(spinnersOptions));
		add_timeEndSpinner.setBounds(300, 180, 100, 20);
		addTab.add(add_timeEndSpinner);
		
		JLabel add_whereLabel = new JLabel("Where :");
		add_whereLabel.setBounds(15, 215, 80, 20);
		addTab.add(add_whereLabel);
		
		add_whereTextArea = new Field(100, 215, 300, 20);
		add_whereTextArea.setDefaultText(DEFAULT_TEXT_WHERE);
		add_whereTextArea.setVisualState(false);
		add_whereTextArea.setColumns(10);
		add_whereTextArea.addFocusListener(contentListener);
		addTab.add(add_whereTextArea);
		
		JButton add_buttonAddRestore = new JButton("Add and Restore (test)");
		add_buttonAddRestore.setBounds(15, 255, 185, 45);
		add_buttonAddRestore.addActionListener(addAndRestoreButtonListener);
		addTab.add(add_buttonAddRestore);
		
		JButton add_buttonAddClose = new JButton("Add and Close");
		add_buttonAddClose.setBounds(215, 255, 185, 45);
		add_buttonAddClose.addActionListener(addAndCloseButtonListener);
		addTab.add(add_buttonAddClose);
		
		tabbedPane.addTab("Add Task", addTab);
		
	}
	
	/**
	 * Launch the new task UI that contains every components of said UI.
	 */
	public void launchUI(){
		
		this.setVisible(true);
		
	}
	
	/**
	 * Create the frame.
	 */
	public NewTaskUI(DayWithEvent dayToEdit){
		
		this.workingDay = dayToEdit;
		this.day = dayToEdit.getDay();
		this.month = dayToEdit.getMonth();
		this.year = dayToEdit.getYear();
		
		setTitle("New task(s) for " + day + " "
				+ Calendar_Main.monthsOfYear[month] + " " + year);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		final int width = 440;
		final int height = 380;
		
		//Sets the default size to half the current sceen's resolution, and place
		//it centered in the screen upon launch
		setBounds(
				((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - width / 2),
				((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - height / 2),
				width, height);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		// Create an array that contains all of the values of time the user can
		// select upon creating a task in the Spinners. The three values down
		// here determines how the starting point is (variables hour/minutes)
		// and how much steps gets taken into account for the spinner, and for
		// the actual choices for the user (variable minuteSteps).
		for(int i = 0; i < spinnersOptions.length; i++){
			
			spinnersOptions[i] = ((hour + (i / (60 / minuteSteps))) % 24)
					+ ":"
					+ (new DecimalFormat("00"))
							.format((minutes + (i * minuteSteps)) % 60);
			
		}
		// The values are stored in the array "spinnersOptions".
		
		updateFrame();
		
	}
}
