package my.sunny.ui;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * 状态栏 <br>
 * 使用java建立一个状态栏
 * 
 * @author ASLY.XYLZ.YYML
 * 
 */
public class StatusBar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8031043213330260875L;

	public static final String VERSION = "v1.0 build20091112";

	/**
	 * 状态栏的数目
	 */
	private int statusNumber = 1;
	/**
	 * 状态栏的JLabel标签，也就是分栏
	 */
	private JLabel[] status = null;
	/**
	 * 每一个栏目的宽度
	 */
	private int[] eachWidthes = null;

	private static int statusBarHeight = 20;

	private static int titleBarHeight = 30;

	/**
	 * 在窗口底部创建一个状态栏，分栏数为1<br>
	 * 状态栏默认宽度为窗口宽度
	 * 
	 * @param window
	 *            要创建状态栏的窗口
	 */
	public StatusBar(JFrame window) {
		this(1, window);
	}

	/**
	 * 在窗口底部创建一个状态栏，分栏数为status_number<br>
	 * 这里每个栏目占用的宽度相等<br>
	 * 状态栏默认宽度为窗口宽度 <br>
	 * 注意有效分栏数为1~5，大于5取5，小于1取1
	 * 
	 * @param status_number
	 *            分栏数
	 * @param window
	 *            要创建状态栏的窗口
	 */
	public StatusBar(int status_number, JFrame window) {
		this(averageWidth(status_number), window);
	}

	/**
	 * 在窗口底部创建一个状态栏，每个栏目的宽度将以百分比形式的整数给出<br>
	 * 状态栏默认宽度为窗口宽度 <br>
	 * 注意有效分栏数为1~5，大于5取5，小于1取1
	 * 
	 * @param widthes
	 *            栏目的宽度列表，每个数字为整数
	 * @param window
	 *            要创建状态栏的窗口
	 */
	public StatusBar(int[] widthes, JFrame window) {
		this(widthes, new Rectangle(0, window.getHeight() - statusBarHeight
				- titleBarHeight, window.getWidth(), statusBarHeight));
	}

	/**
	 * 在给定位置创建一个状态栏<br>
	 * 状态栏默认宽度为窗口宽度 <br>
	 * 注意有效分栏数为1~5，大于5取5，小于1取1
	 * 
	 * @param widthes
	 *            栏目的宽度列表，每个数字为整数
	 * @param rectangle
	 *            状态栏的位置，相对父控件的位置
	 */
	public StatusBar(int[] widthes, Rectangle rectangle) {
		super();
		processWidthes(widthes);
		init(rectangle);
	}

	/**
	 * 处理宽度数组
	 * 
	 * @param widthes
	 */
	private void processWidthes(int[] widthes) {
		if (widthes == null) {
			eachWidthes = new int[1];
			eachWidthes[0] = 100;
			setStatusNumber(1);
		} else {
			eachWidthes = widthes;
			int widthSum = 0;
			for (int index = 0; index < eachWidthes.length; index++) {
				if (widthSum >= 100)
					break;
				if (eachWidthes[index] < 1 || eachWidthes[index] > 99)
					eachWidthes[index] = 0;
				widthSum += eachWidthes[index];
				if (widthSum > 100)
					eachWidthes[index] = 100 - (widthSum - eachWidthes[index]);
			}
			setStatusNumber(eachWidthes.length);
		}
	}

	/**
	 * 初始化组件
	 * 
	 * @param rectangle
	 */
	private void init(Rectangle rectangle) {
		setBounds(rectangle);
		System.out.println(rectangle.toString());
		//setBorder(new LineBorder(Color.black, 1, false));
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//		setLayout(null);
		//setLayout(new FlowLayout());
//		JPanel statusContainer = new JPanel();
//		add(statusContainer);
//		statusContainer.setLayout(null);
		

		status = new JLabel[statusNumber];

		int currentX = 0;
		int currentWidth = 0;
		for (int index = 0; index < statusNumber; index++) {
			if (index == statusNumber - 1)
				currentWidth = rectangle.width - currentX;
			else
				currentWidth = eachWidthes[index] * rectangle.width / 100;

			if (currentWidth == 0)
				break;

			status[index] = new JLabel("就绪" + index);
//			status[index]
//					.setBounds(currentX, 0, currentWidth, rectangle.height);
			System.out.println("currentWidth:"+currentWidth);
			status[index].setPreferredSize(new Dimension(currentWidth, rectangle.height));
//			status[index].setLocation(currentX, 0);
			status[index].setHorizontalAlignment(SwingConstants.CENTER);
			if (index % 2 == 0) {
				//status[index].setBorder(new LineBorder(Color.black, 1, false));
			}
			status[index].setBorder(new EtchedBorder());
			add(status[index]);
			currentX += currentWidth;
			
		}
	}

	/**
	 * 设定状态栏分栏的数目,最多支持5个状态栏<br>
	 * 之所以限制数目是因为过多栏目没什么意义
	 * 
	 * @param status_number
	 *            状态栏的数目,有效数字为1~5,大于5、小于1时将设定为1
	 */
	private void setStatusNumber(int status_number) {
		if (status_number > 1 && status_number <= 5)
			this.statusNumber = status_number;
		else
			this.statusNumber = 1;
	}

	/**
	 * 获取一个状态栏目，如果索引不在有效栏目数之内将返回第一个栏目
	 * 
	 * @param index
	 *            栏目索引,从0开始计数
	 * @return 如果index大于栏目数目，将返回第一个栏目
	 */
	public JLabel getStatus(int index) {
		if (checkIndex(index))
			return status[index];
		else
			return status[0];
	}

	/**
	 * 获得第一个状态栏目
	 * 
	 * @return
	 */
	public JLabel getFirstStatus() {
		return status[0];
	}

	/**
	 * 获得最后一个状态栏目
	 * 
	 * @return
	 */
	public JLabel getLastStatus() {
		return status[statusNumber - 1];
	}

	/**
	 * 获得所有的状态栏目
	 * 
	 * @return 所有的状态栏目，每一个状态栏目是一个JLabel
	 */
	public JLabel[] getAllStatus() {
		return status;
	}

	/**
	 * 获得最后一个栏目的索引
	 * 
	 * @return
	 */
	public int getLastStatusIndex() {
		return statusNumber - 1;
	}

	/**
	 * 检查索引是否有效<br>
	 * 判断索引状态栏是否存在
	 * 
	 * @param index
	 * @return
	 */
	private boolean checkIndex(int index) {
		return index >= 0 && index < statusNumber;
	}

	/**
	 * 设置状态栏的信息
	 * 
	 * @param index
	 *            状态栏的索引
	 * @param message
	 *            信息
	 */
	public void setStatus(int index, String message) {
		if (checkIndex(index))
			status[index].setText(message);
	}

	/**
	 * 获得平均分整数组<br>
	 * 获得的整数数组大小为times,每个值为100/times。<br>
	 * 注意这里只处理1~5，大于5取5，小于1取1
	 * 
	 * @param times
	 *            分组次数
	 * @return 获得的分组数组
	 */
	private static int[] averageWidth(int times) {
		if (times < 1)
			times = 1;
		else if (times > 5)
			times = 5;

		int[] result = new int[times];
		int each_width = 100 / times;
		for (int index = 0; index < times; index++)
			result[index] = each_width;
		return result;
	}

	/**
	 * 获得状态栏目的数目
	 * 
	 * @return 状态栏目的数目
	 */
	public int getStatusNumber() {
		return statusNumber;
	}

}
