package net.robinjam.notifications;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NotificationSink extends UnicastRemoteObject implements INotificationSink {

	private static final long serialVersionUID = 1L;
	
	private final INotificationListener listener;

	public NotificationSink(INotificationListener listener) throws RemoteException {
		super();
		
		this.listener = listener;
	}

	@Override
	public void notify(INotificationSource source, INotification notification) throws RemoteException {
		listener.notificationReceived(source, notification);
	}

}
