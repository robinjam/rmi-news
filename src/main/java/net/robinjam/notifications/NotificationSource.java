package net.robinjam.notifications;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotificationSource extends Remote {

	public void registerSink(NotificationSink sink) throws RemoteException;
	
	public void unregisterSink(NotificationSink sink) throws RemoteException;
	
	public void notifySinks(Notification notification) throws RemoteException;
	
}
