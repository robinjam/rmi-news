package net.robinjam.rminews.client;

import java.awt.Dimension;
import java.rmi.Naming;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import net.robinjam.notifications.NotificationSource;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	public MainWindow(NewsSink sink) {
		super("RMI News Client");
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		JScrollPane scrollPane = new JScrollPane(new JList(sink));
		scrollPane.setPreferredSize(new Dimension(500, 400));
		contentPane.add(scrollPane);
		
		setContentPane(contentPane);
		setResizable(false);
		pack();
	}
	
	public static void main(String[] args) {
		// Check that the correct number of arguments was passed
		if (args.length < 1) {
			System.out.println("Please provide a list of bind addresses as command-line arguments.");
			return;
		}
		
		try {
			NewsSink sink = new NewsSink();
			for (int i = 0; i < args.length; ++i) {
				NotificationSource source = (NotificationSource) Naming.lookup(args[i]);
				source.registerSink(sink);
			}
			
			JFrame mainWindow = new MainWindow(sink);
			mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainWindow.setLocationRelativeTo(null);
			mainWindow.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
