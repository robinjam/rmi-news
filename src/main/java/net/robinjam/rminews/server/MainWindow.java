package net.robinjam.rminews.server;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.robinjam.notifications.ConcreteNotificationSource;
import net.robinjam.rminews.NewsItem;

/**
 * The main window of the server application.
 * 
 * @author James Robinson
 */
public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public MainWindow(String url) throws RemoteException, MalformedURLException {
		super("RMI News Server");
		
		// Create the notification source using the given URL
		final ConcreteNotificationSource source = new ConcreteNotificationSource(url);
		
		// Set up the the title, URL and description fields
		final JTextField titleField = new JTextField(20);
		final JTextField urlField = new JTextField(20);
		final JTextArea descriptionField = new JTextArea(20, 20);
		
		// Set up the submit button, send the notification when clicked
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				source.notifySinks(new NewsItem(titleField.getText(), descriptionField.getText(), urlField.getText()));
			}
		});
		
		// Add the elements to the content pane
		JPanel contentPane = new JPanel(new GridBagLayout());
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add 10px padding around the main content
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); // Add 5px padding between cells
		gbc.anchor = GridBagConstraints.EAST; // Align the labels to the right
		
		contentPane.add(new JLabel("Title"), gbc);
		
		gbc.gridy = 1;
		contentPane.add(new JLabel("URL"), gbc);
		
		gbc.gridy = 2;
		contentPane.add(new JLabel("Description"), gbc);
		
		gbc.fill = GridBagConstraints.BOTH; // Expand the text fields to fill the available space
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
		// Request the bind URL from the user
		String result = JOptionPane.showInputDialog(null, "Please specify a URL for the feed.\nPlease note that an RMI registry must already be running on the specified host.", "//localhost/my_feed");
		if (result == null)
			return; // If the user clicks cancel, exit
		
		try {
			JFrame mainWindow = new MainWindow(result);
			mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainWindow.setLocationRelativeTo(null); // Center the window on the screen
			mainWindow.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to start server.\nPlease ensure that an RMI registry is running on the specified host.");
			System.exit(1);
		}
	}
	
}
