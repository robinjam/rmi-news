package net.robinjam.notifications;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotificationSink extends Remote {
	
	public void notify(NotificationSource source, Notification notification) throws RemoteException;
	
}
