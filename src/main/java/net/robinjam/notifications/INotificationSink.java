package net.robinjam.notifications;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface INotificationSink extends Remote {
	
	public void notify(INotificationSource source, INotification notification) throws RemoteException;
	
}
