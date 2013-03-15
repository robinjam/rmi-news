package net.robinjam.notifications;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConcreteNotificationSource extends UnicastRemoteObject implements NotificationSource {

	public ConcreteNotificationSource() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;
	
	private Set<NotificationSink> sinks = new HashSet<NotificationSink>();

	@Override
	public void registerSink(NotificationSink sink) throws RemoteException {
		sinks.add(sink);
	}
	
	@Override
	public void unregisterSink(NotificationSink sink) throws RemoteException {
		sinks.remove(sink);
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
