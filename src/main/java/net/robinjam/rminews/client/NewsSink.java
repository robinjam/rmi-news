package net.robinjam.rminews.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.robinjam.notifications.Notification;
import net.robinjam.notifications.NotificationSink;
import net.robinjam.notifications.NotificationSource;
import net.robinjam.rminews.NewsItem;

public class NewsSink extends UnicastRemoteObject implements NotificationSink, ListModel {

	private static final long serialVersionUID = 1L;

	protected NewsSink() throws RemoteException {
		super();
	}

	@Override
	public void notify(NotificationSource source, Notification notification) throws RemoteException {
		messages.add(((NewsItem) notification).getText());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				for (ListDataListener listener : listeners) {
					listener.contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, 0, 0));
				}
			}
		});
	}
	
	private Set<ListDataListener> listeners = new HashSet<ListDataListener>();
	private List<String> messages = new ArrayList<String>();

	@Override
	public void addListDataListener(ListDataListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listener);
	}

	@Override
	public Object getElementAt(int index) {
		return messages.get(getSize() - index - 1);
	}

	@Override
	public int getSize() {
		return messages.size();
	}

}
