import java.awt.Color;
import java.awt.Font;
import java.awt.KeyboardFocusManager;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

/**
 * Class extending {@link javax.swing.JTextArea JTextArea} with the addition of
 * a default text within the component.
 * 
 * @author V-ed
 */
public class Field extends JTextArea{
	
	/**
	 * Default serial number.
	 */
	private static final long serialVersionUID = 1L;
	
	private String defaultText;
	private boolean isChanged = false;
	
	/**
	 * Creates a {@link javax.swing.JTextArea JTextArea} but with an addition:
	 * the ability to set a default text for this Field.
	 */
	public Field(){
		
		this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
				null);
		this.setFocusTraversalKeys(
				KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
		
		this.setForeground(Color.BLACK);
		this.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
	}
	
	/**
	 * Creates a {@link javax.swing.JTextArea JTextArea} with the bounds set as
	 * parameters, but with an addition: the ability to set a default text for
	 * this Field.
	 */
	public Field(int x, int y, int width, int height){
		
		this.setBounds(x, y, width, height);
		
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		
		this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
				null);
		this.setFocusTraversalKeys(
				KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
		
		this.setForeground(Color.BLACK);
		this.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
	}
	
	/**
	 * Sets the visual of the current Field depending on the boolean parameter.
	 * <p>
	 * <b>true</b> : Plain black text, remove the current text.
	 * <p>
	 * <b>false</b> : Italic gray text and the default Field's text is shown.
	 * Also sets this Field's {@link #setChangedState(boolean)} to
	 * <code>false</code>.
	 */
	public void setVisualState(boolean state){
		
		if(state){
			this.setForeground(Color.BLACK);
			this.setFont(new Font("Tahoma", Font.PLAIN, 11));
			this.setText("");
		}
		else{
			this.setForeground(Color.LIGHT_GRAY);
			this.setFont(new Font("Tahoma", Font.ITALIC, 11));
			this.setText(getDefaultText());
			setChangedState(false);
		}
		
	}
	
	public void setVisualEditable(boolean state){
		
		this.setEditable(false);
		this.setBackground(Color.LIGHT_GRAY);
		this.setFont(new Font("Tahoma", Font.ITALIC, 11));
		
	}
	
	/**
	 * Returns the default text for this Field.
	 */
	public String getDefaultText(){
		return defaultText;
	}
	
	/**
	 * Sets the default text for this Field. Sets at the same time the same text
	 * for the current shown text.
	 */
	public void setDefaultText(String defaultText){
		
		this.defaultText = defaultText;
		this.setText(defaultText);
		
	}
	
	/**
	 * Returns the state of whether this Field has been modified or not.
	 */
	public boolean isChanged(){
		return isChanged;
	}
	
	/**
	 * Change the state of this Field to know whether this Field's value's the
	 * default one or not.
	 */
	public void setChangedState(boolean isChanged){
		this.isChanged = isChanged;
	}
	
}
