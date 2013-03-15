package net.robinjam.notifications;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NotificationSource extends UnicastRemoteObject implements INotificationSource {

	public NotificationSource() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;
	
	private Set<INotificationSink> sinks = new HashSet<INotificationSink>();

	@Override
	public void registerSink(INotificationSink sink) throws RemoteException {
		sinks.add(sink);
	}
	
	@Override
	public void unregisterSink(INotificationSink sink) throws RemoteException {
		sinks.remove(sink);
	}

	@Override
	public void notifySinks(INotification notification) throws RemoteException {
		// Iterate over the notification sinks
		Iterator<INotificationSink> iter = sinks.iterator();
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
