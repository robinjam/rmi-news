package net.robinjam.rminews.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import net.robinjam.notifications.ConcreteNotificationSource;
import net.robinjam.notifications.NotificationSink;
import net.robinjam.notifications.NotificationSource;

/**
 * Manages the news feeds that a given sink is subscribed to, and provides a JList model listing these subscriptions.
 * 
 * @author James Robinson
 */
public class NewsFeedManager extends AbstractListModel {
	
	private static class Item {
		private final String url;
		private final NotificationSource source;
		
		private Item(String url, NotificationSource source) {
			this.url = url;
			this.source = source;
		}
	}

	private static final long serialVersionUID = 1L;
	
	private NotificationSink sink;
	private List<Item> items = new ArrayList<Item>();
	
	/**
	 * Wraps the given notification sink.
	 * This instance will manage adding and removing subscriptions via the subscribe and unsubscribe methods.
	 * 
	 * @param sink The notification sink to manage.
	 */
	public NewsFeedManager(NotificationSink sink) {
		this.sink = sink;
	}

	@Override
	public Object getElementAt(int index) {
		return items.get(index).url;
	}

	@Override
	public int getSize() {
		return items.size();
	}
	
	/**
	 * Subscribes the NotificationSink wrapped in this instance to the feed at the given URL, and adds the feed to the list managed by this instance.
	 * 
	 * @param url The URL for the feed the user wants to subscribe to.
	 */
	public void subscribe(String url) throws MalformedURLException, RemoteException, NotBoundException {
		NotificationSource source = ConcreteNotificationSource.getNotificationSource(url);
		source.registerSink(sink);
		items.add(new Item(url, source));
		fireIntervalAdded(this, items.size() - 1, items.size() - 1);
	}
	
	/**
	 * Unsubscribes the NotificationSink wrapped in this instance from the feed at the given index, and removes the feed from the list managed by this instance.
	 * 
	 * @param id The index of the feed to be removed.
	 */
	public void unsubscribe(int id) {
		try {
			Item item = items.remove(id);
			fireIntervalRemoved(this, id, id);
			item.source.unregisterSink(sink);
		} catch (RemoteException e) {
			// If a remote exception is raised whilst trying to unsubscribe, assume the connection has been lost and do nothing.
		}
	}
	
	/**
	 * Checks whether the given URL is present in the feed list.
	 * 
	 * @param url The URL to check.
	 * @return true if the URL has already been added, false otherwise.
	 */
	public boolean contains(String url) {
		for (Item item : items) {
			if (item.url.equals(url))
				return true;
		}
		
		return false;
	}

}
