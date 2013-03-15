package net.robinjam.notifications;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConcreteNotificationSource extends UnicastRemoteObject implements NotificationSource {

	public ConcreteNotificationSource(String url) throws RemoteException, MalformedURLException {
		super();
		
		Naming.rebind(url, this);
	}

	private static final long serialVersionUID = 1L;
	
	private Set<NotificationSink> sinks = new HashSet<NotificationSink>();
	
	public static NotificationSource getNotificationSource(String url) throws MalformedURLException, RemoteException, NotBoundException {
		return (NotificationSource) Naming.lookup(url);
	}

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
