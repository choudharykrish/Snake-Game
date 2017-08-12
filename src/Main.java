import java.awt.Color;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) 
	{
		JFrame frame = new JFrame();
		GamePlay panel = new GamePlay();
		
		frame.setBounds(10, 10, 905,700);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
	}
}