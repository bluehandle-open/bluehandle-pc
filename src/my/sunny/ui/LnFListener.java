package my.sunny.ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LnFListener implements ActionListener {
	Frame frame;

	public LnFListener(Frame f) {
		frame = f;
	}

	public void actionPerformed(ActionEvent e) {
		String lnfName = null;
		if (e.getActionCommand().equals("Metal")) {
			lnfName = "javax.swing.plaf.metal.MetalLookAndFeel";

		} else if (e.getActionCommand().equals("Motif")) {
			lnfName = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		} else {
			lnfName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		}

		try {
			UIManager.setLookAndFeel(lnfName);
			SwingUtilities.updateComponentTreeUI(frame);
		} catch (UnsupportedLookAndFeelException ex1) {
			System.err.println("Unsupported LookAndFeel: " + lnfName);
		} catch (ClassNotFoundException ex2) {
			System.err.println("LookAndFeel class not found: " + lnfName);
		} catch (InstantiationException ex3) {
			System.err.println("Could not load LookAndFeel: " + lnfName);
		} catch (IllegalAccessException ex4) {
			System.err.println("Cannot use LookAndFeel: " + lnfName);
		}
	}
}
