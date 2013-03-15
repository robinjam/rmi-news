package net.robinjam.rminews.server;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.robinjam.rminews.NewsItem;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	public MainWindow(String url) throws RemoteException, MalformedURLException {
		super("RMI News Server");
		
		final NewsSource source = new NewsSource();
		Naming.rebind(url, source);
		
		final JTextField titleField = new JTextField(20);
		final JTextField urlField = new JTextField(20);
		final JTextArea descriptionField = new JTextArea(20, 20);
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewsItem notification = new NewsItem(titleField.getText(), descriptionField.getText(), urlField.getText());
				try {
					source.notifySinks(notification);
				} catch (RemoteException ex) {
					ex.printStackTrace();
					System.exit(1);
				}
			}
		});
		
		JPanel contentPane = new JPanel(new GridBagLayout());
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.BOTH;
		contentPane.add(new JLabel("Title"), gbc);
		gbc.gridy = 1;
		contentPane.add(new JLabel("URL"), gbc);
		gbc.gridy = 2;
		contentPane.add(new JLabel("Description"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		contentPane.add(titleField, gbc);
		gbc.gridy = 1;
		contentPane.add(urlField, gbc);
		gbc.gridy = 2;
		contentPane.add(new JScrollPane(descriptionField), gbc);
		gbc.gridy = 3;
		contentPane.add(submitButton, gbc);
		
		setContentPane(contentPane);
		setResizable(false);
		pack();
	}
	
	public static void main(String[] args) {
		// Check that the correct number of arguments was passed
		if (args.length != 1) {
			System.out.println("Please provide the bind address as a command-line argument.");
			return;
		}
		
		try {
			JFrame mainWindow = new MainWindow(args[0]);
			mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainWindow.setLocationRelativeTo(null); // Center the window on the screen
			mainWindow.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}