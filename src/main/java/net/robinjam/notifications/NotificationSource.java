package net.robinjam.notifications;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the interface that notification sources must adhere to.
 * Notification sources may be subscribed to by more than one sink.
 * Clients should not implement this interface directly, instead they should instantiate {@link ConcreteNotificationSource}.
 * 
 * @author James Robinson
 */
public interface NotificationSource extends Remote {

	/**
	 * Registers the sink so that it may receive notifications from this source.
	 * Duplicate registrations are not allowed and will fail silently.
	 * 
	 * @param sink The notification sink to register to this source.
	 */
	public void registerSink(NotificationSink sink) throws RemoteException;
	
	/**
	 * Unregisters the sink so it no longer receives notifications from this source.
	 * 
	 * @param sink The notification sink to unregister.
	 */
	public void unregisterSink(NotificationSink sink) throws RemoteException;
	
	/**
	 * Forwards the given notification to every sink registered to this source.
	 * If any of the sinks registered to this source are unavailable (due to connection loss, for example), they will automatically be unregistered.
	 * 
	 * @param notification The notification to send.
	 */
	public void notifySinks(Notification notification) throws RemoteException;
	
}
