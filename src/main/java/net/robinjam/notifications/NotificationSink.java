package net.robinjam.notifications;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the interface that notification sinks must adhere to.
 * Each notification sink may receive notifications from one or more sources.
 * 
 * @author robinjam
 */
public interface NotificationSink extends Remote {
	
	/**
	 * Called when a new notification is sent by one of the sources that this sink is subscribed to.
	 * 
	 * @param source The source of the notification.
	 * @param notification The notification.
	 */
	public void notify(NotificationSource source, Notification notification) throws RemoteException;
	
}
