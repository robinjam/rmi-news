package net.robinjam.rminews.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.robinjam.notifications.NotificationSink;
import net.robinjam.notifications.ConcreteNotificationSink;

/**
 * The main window of the client application.
 * 
 * @author James Robinson
 */
public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public MainWindow() throws RemoteException {
		super("RMI News Client");
		
		// Set up the notification sink and the list models
		final NewsItemListModel newsItemListModel = new NewsItemListModel();
		final NotificationSink sink = new ConcreteNotificationSink(newsItemListModel);
		final NewsFeedManager sourceManager = new NewsFeedManager(sink);
		
		// Add the controls to the content pane
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add 10px padding around the main content
		
		JScrollPane scrollPane = new JScrollPane(new JList(newsItemListModel));
		scrollPane.setPreferredSize(new Dimension(500, 400));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel addFeedPanel = new JPanel(new FlowLayout());
		addFeedPanel.add(new JLabel("Add feed:"));
		final JTextField feedUrlField = new JTextField("//localhost/my_feed");
		addFeedPanel.add(feedUrlField);
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!sourceManager.contains(feedUrlField.getText()))
						sourceManager.subscribe(feedUrlField.getText());
					feedUrlField.setText("");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Unable to add the feed. Please check that the URL is correct.");
				}
			}
		});
		addFeedPanel.add(addButton);
		
		contentPane.add(addFeedPanel, BorderLayout.SOUTH);
		
		final JList sourceList = new JList(sourceManager);
		contentPane.add(new JScrollPane(sourceList), BorderLayout.EAST);
		
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!sourceList.isSelectionEmpty())
					sourceManager.unsubscribe(sourceList.getSelectedIndex());
			}
		});
		addFeedPanel.add(removeButton);
		
		setContentPane(contentPane);
		setResizable(false);
		pack();
	}
	
	public static void main(String[] args) {
		try {
			JFrame mainWindow = new MainWindow();
			mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainWindow.setLocationRelativeTo(null);
			mainWindow.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
