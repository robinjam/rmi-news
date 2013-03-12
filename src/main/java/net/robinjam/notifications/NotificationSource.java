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
	 * It is not necessary to unregister the sink since this will be done automatically when the sink is no longer available.
	 * 
	 * @param sink The notification source to register.
	 */
	public void registerSink(NotificationSink sink) throws RemoteException;
	
	/**
	 * Sends the given notification to all of the sinks registered with this notification source.
	 * If any of the sinks registered to this source are unavailable (for example, if they have lost connection), they will be automatically removed from the list.
	 * 
	 * @param notification The notification to send.
	 */
	public void notifySinks(Notification notification) throws RemoteException;
	
}
