package net.robinjam.notifications;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the interface that all notification sinks must adhere to.
 * Each notification sink may be registered to multiple sources.
 * Clients should not implement this interface directly, instead they should implement {@link NotificationListener} and pass their implementation into an instance of {@link ConcreteNotificationSink}.
 * 
 * @author James Robinson
 */
public interface NotificationSink extends Remote {
	
	/**
	 * Called by the notification source in order to transfer the notification.
	 * 
	 * @param source The source of the notification.
	 * @param notification The notification.
	 */
	public void notify(NotificationSource source, Notification notification) throws RemoteException;
	
}
