package net.robinjam.rminews.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import net.robinjam.notifications.NotificationSource;

/**
 * Manages the news sources that a given sink is subscribed to, and provides a JList model listing these subscriptions.
 * 
 * @author robinjam
 */
public class NewsSourceManager extends AbstractListModel {
	
	private static class Item {
		private final String url;
		private final NotificationSource source;
		
		private Item(String url, NotificationSource source) {
			this.url = url;
			this.source = source;
		}
	}

	private static final long serialVersionUID = 1L;
	
	private NewsSink sink;
	private List<Item> items = new ArrayList<Item>();
	
	public NewsSourceManager(NewsSink sink) {
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
	 * Subscribes the NewsSink wrapped in this instance to the feed at the given URL, and adds the feed to the list managed by this instance.
	 * 
	 * @param url The URL for the feed the user wants to subscribe to.
	 */
	public void subscribe(String url) throws MalformedURLException, RemoteException, NotBoundException {
		NotificationSource source = (NotificationSource) Naming.lookup(url);
		source.registerSink(sink);
		items.add(new Item(url, source));
		fireIntervalAdded(this, items.size() - 1, items.size() - 1);
	}
	
	/**
	 * Unsubscribes the NewsSink wrapped in this instance from the feed at the given index, and removes the feed from the list managed by this instance.
	 * 
	 * @param id The index of the feed to be removed.
	 */
	public void unsubscribe(int id) throws RemoteException {
		Item item = items.remove(id);
		item.source.unregisterSink(sink);
		fireIntervalRemoved(this, id, id);
	}

}
