package net.robinjam.notifications;

/**
 * The interface that classes should implement in order to be notified when a {@link NotificationSink} receives a {@link Notification}.
 * 
 * @author James Robinson
 */
public interface NotificationListener {
	
	/**
	 * Called whenever the notification sink this listener is attached to receives a notification.
	 * 
	 * @param source The source of the notification.
	 * @param notification The notification.
	 */
	public void notificationReceived(NotificationSource source, Notification notification);
	
}
