package net.robinjam.notifications;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Concrete implementation of {@link NotificationSink}.
 * 
 * @author James Robinson
 */
public final class ConcreteNotificationSink extends UnicastRemoteObject implements NotificationSink {

	private static final long serialVersionUID = 1L;
	
	private final NotificationListener listener;

	/**
	 * Creates a new instance and notifies the given listener whenever a notification is received.
	 * 
	 * @param listener The listener that should be notified whenever a notification is received.
	 */
	public ConcreteNotificationSink(NotificationListener listener) throws RemoteException {
		super();
		
		this.listener = listener;
	}

	@Override
	synchronized public void notify(NotificationSource source, Notification notification) {
		// Forward the notification to the listener
		listener.notificationReceived(source, notification);
	}

}
