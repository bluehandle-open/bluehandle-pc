package my.sunny.ui.component;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.whyun.util.ResourceUtil;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image image;

	public ImagePanel(String relativePath) {
		super();
		this.setOpaque(false);//不绘制组件的空白区域，从而让我们自己绘制的背景图片显现
		image = ResourceUtil.getImageIcon(relativePath).getImage();
	}	
	
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
		super.paint(g);//这行代码不可省略，否则无法画出放在容器里的组件
	}
}
