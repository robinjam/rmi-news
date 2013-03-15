package net.robinjam.notifications;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface INotificationSource extends Remote {

	public void registerSink(INotificationSink sink) throws RemoteException;
	
	public void unregisterSink(INotificationSink sink) throws RemoteException;
	
	public void notifySinks(INotification notification) throws RemoteException;
	
}
