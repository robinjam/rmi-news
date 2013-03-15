package net.robinjam.notifications;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConcreteNotificationSink extends UnicastRemoteObject implements NotificationSink {

	private static final long serialVersionUID = 1L;
	
	private final NotificationListener listener;

	public ConcreteNotificationSink(NotificationListener listener) throws RemoteException {
		super();
		
		this.listener = listener;
	}

	@Override
	public void notify(NotificationSource source, Notification notification) throws RemoteException {
		listener.notificationReceived(source, notification);
	}

}
