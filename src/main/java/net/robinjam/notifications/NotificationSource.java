package net.robinjam.notifications;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the interface that all notification sources must implement.
 * Each notification source must manage a list of registered sinks, and handle forwarding notifications to them.
 * 
 * @author robinjam
 */
public interface NotificationSource extends Remote {
	
	/**
	 * Registers the given notification sink to receive notifications from this source.
	 * Implementing classes are required to automatically unregister sinks when they become unavailable (for example, due to connection loss).
	 * 
	 * @param sink The notification sink to register.
	 */
	public void registerSink(NotificationSink sink) throws RemoteException;
	
	/**
	 * Unregisters the given notification sink from this source.
	 * 
	 * @param sink The notification sink to remove.
	 */
	public void unregisterSink(NotificationSink sink) throws RemoteException;
	
	/**
	 * Sends the given notification to all of the sinks registered with this notification source.
	 * If any of the sinks registered to this source are unavailable (for example, if they have lost connection), they will be automatically removed from the list.
	 * 
	 * @param notification The notification to send.
	 */
	public void notifySinks(Notification notification) throws RemoteException;
	
}
