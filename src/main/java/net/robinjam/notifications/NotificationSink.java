package net.robinjam.notifications;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class NotificationSink extends UnicastRemoteObject implements INotificationSink {

	private static final long serialVersionUID = 1L;

	protected NotificationSink() throws RemoteException {
		super();
	}

}
