package net.robinjam.notifications;

public interface NotificationListener {
	
	public void notificationReceived(NotificationSource source, Notification notification);
	
}
