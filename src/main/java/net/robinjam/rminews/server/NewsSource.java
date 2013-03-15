package net.robinjam.rminews.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.robinjam.notifications.Notification;
import net.robinjam.notifications.NotificationSink;
import net.robinjam.notifications.NotificationSource;

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

}
