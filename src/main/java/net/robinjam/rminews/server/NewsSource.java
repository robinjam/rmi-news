package net.robinjam.rminews.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.robinjam.notifications.Notification;
import net.robinjam.notifications.NotificationSink;
import net.robinjam.notifications.NotificationSource;
import net.robinjam.rminews.NewsItem;

public class NewsSource extends UnicastRemoteObject implements NotificationSource {

	private static final long serialVersionUID = 1L;

	protected NewsSource() throws RemoteException {
		super();
	}

	private Set<NotificationSink> sinks = new HashSet<NotificationSink>();

	@Override
	public void registerSink(NotificationSink sink) throws RemoteException {
		sinks.add(sink);
	}

	@Override
	public void notifySinks(Notification notification) throws RemoteException {
		// Iterate over the notification sinks
		Iterator<NotificationSink> iter = sinks.iterator();
		while (iter.hasNext()) {
			try {
				// Attempt to notify the sink
				iter.next().notify(this, notification);
			} catch (RemoteException e) {
				// If the notification failed, remove the sink from the list
				iter.remove();
			}
		}
	}

	public static void main(String[] args) {
		// Check that the correct number of arguments was passed
		if (args.length != 1) {
			System.out.println("Please provide the bind address as a command-line argument.");
			return;
		}

		try {
			// Initialize the news server
			NewsSource source = new NewsSource();
			Naming.rebind(args[0], source);
			
			// Send a test notification once per second
			int count = 0;
			while (true) {
				System.out.println("Sending notification...");
				source.notifySinks(new NewsItem("Hello world! " + ++count));
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			// Bail out if an exception occurs
			e.printStackTrace();
			System.exit(1);
		}
	}

}
