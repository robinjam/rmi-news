package net.robinjam.notifications;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Concrete implementation of {@link NotificationSource}.
 * 
 * @author James Robinson
 */
public final class ConcreteNotificationSource extends UnicastRemoteObject implements NotificationSource {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Looks up the given bound URL and returns the matching notification source.
	 * 
	 * @param url The URL to look up.
	 * @return The notification source.
	 */
	synchronized public static NotificationSource getNotificationSource(String url) throws MalformedURLException, RemoteException, NotBoundException {
		return (NotificationSource) Naming.lookup(url);
	}
	
	/**
	 * Creates a new notification source and binds it to the given RMI URL.
	 * If the given URL is already bound, the old binding will be overwritten.
	 * 
	 * @param url The RMI URL this source should bind to.
	 */
	public ConcreteNotificationSource(String url) throws RemoteException, MalformedURLException {
		super();
		
		Naming.rebind(url, this);
	}
	
	private Set<NotificationSink> sinks = new HashSet<NotificationSink>();

	@Override
	synchronized public void registerSink(NotificationSink sink) {
		sinks.add(sink);
	}
	
	@Override
	synchronized public void unregisterSink(NotificationSink sink) {
		sinks.remove(sink);
	}

	@Override
	synchronized public void notifySinks(Notification notification) {
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
