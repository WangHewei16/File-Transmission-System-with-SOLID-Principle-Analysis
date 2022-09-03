package view;

import javax.swing.*;
import java.awt.*;

class MyDialog extends JDialog {
	public MyDialog() {
		this.setVisible(true);
		this.setBounds(100,100,200,200);
		//this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// set text using JLabel
		JLabel label = new JLabel("Transmission has finished :-)");
		this.add(label);

		// center text labels to set horizontal alignment
		label.setHorizontalAlignment(SwingConstants.CENTER);

		// get a container
		Container container = this.getContentPane();
		container.setBackground(Color.YELLOW);
	}
}
