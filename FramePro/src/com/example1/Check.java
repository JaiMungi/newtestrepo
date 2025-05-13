package com.example1;
import javax.swing.JFrame;
public class Check {
	JFrame frame;
	Check()
	{
		frame = new JFrame("My Frame");
		frame.setSize(300,300);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		Check c = new Check();
	}

}
