package net.robinjam.notifications;

public interface INotificationListener {
	
	public void notificationReceived(INotificationSource source, INotification notification);
	
}
