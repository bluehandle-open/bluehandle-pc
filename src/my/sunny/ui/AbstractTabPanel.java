package my.sunny.ui;

import java.awt.Point;

import javax.swing.JPanel;

public abstract class AbstractTabPanel extends JPanel {

	private static final long serialVersionUID = -671198142143840691L;
	protected int width;
	protected int height;
	protected BlueHandle parentFrame;
	public AbstractTabPanel(int width, int height, BlueHandle parentFrame) {
		this.width = width;
		this.height = height;
		this.parentFrame = parentFrame;
		init();
		addEventListener();
//		Point p = this.getLocationOnScreen();
//		System.out.println(p);
	}	
	
	protected abstract void init();
	
	protected abstract void addEventListener();
	
	protected abstract void close();
	
	protected void disableUI() {
		Point p = this.getLocation();
		System.out.println(this.getPreferredSize()+":"+this.getBounds());
		System.out.println(p);
	}
	
	protected void enableUI() {
		
	}
}
