package my.sunny.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class AbstractTabPanel extends JPanel {

	private static final long serialVersionUID = -671198142143840691L;
	protected int width;
	protected int height;
	protected JFrame parentFrame;
	public AbstractTabPanel(int width, int height, JFrame parentFrame) {
		this.width = width;
		this.height = height;
		this.parentFrame = parentFrame;
		init();
		addEventListener();
	}	
	
	protected abstract void init();
	
	protected abstract void addEventListener();
	
	protected abstract void close();
	
}
