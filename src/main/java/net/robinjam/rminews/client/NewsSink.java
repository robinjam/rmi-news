package net.robinjam.rminews.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.robinjam.notifications.Notification;
import net.robinjam.notifications.NotificationSink;
import net.robinjam.notifications.NotificationSource;
import net.robinjam.rminews.NewsItem;

public class NewsSink extends UnicastRemoteObject implements NotificationSink {

	private static final long serialVersionUID = 1L;

	protected NewsSink() throws RemoteException {
		super();
	}

	@Override
	public void notify(NotificationSource source, Notification notification) throws RemoteException {
		System.out.println(((NewsItem) notification).getText());
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
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
