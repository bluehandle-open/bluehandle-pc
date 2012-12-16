package my.sunny.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

public class GlassPane extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GlassPane() {
		setOpaque(false);
		this.setBackground(new Color(0f,1.0f,1.0f,0.5f));
		setVisible(true);
		this.setBorder(new LineBorder(Color.GREEN));
	}

	public void paint(Graphics g)// 重载Paint以保证GlassPane不透明
	{
		Graphics2D g2 = (Graphics2D) g;
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		g2.setComposite(composite);
		g2.drawImage(image, 0, 0,null);
	}
}