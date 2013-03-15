package net.robinjam.rminews.client;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import net.robinjam.notifications.Notification;
import net.robinjam.notifications.NotificationListener;
import net.robinjam.notifications.NotificationSource;
import net.robinjam.rminews.NewsItem;

/**
 * Provides a model for a JList based on notifications received from a notification sink.
 * The items in the JList are formatted automatically.
 * 
 * @author James Robinson
 */
public class NewsItemListModel extends AbstractListModel implements NotificationListener {

	private static final long serialVersionUID = 1L;
	
	private List<NewsItem> newsItems = new ArrayList<NewsItem>();

	@Override
	public void notificationReceived(NotificationSource source, Notification notification) {
		// Ensure that the notification was actually a news item (since the framework supports multiple notification types)
		if (notification instanceof NewsItem) {
			// Add the news item to the list
			newsItems.add((NewsItem) notification);
			fireIntervalAdded(this, 0, 0);
		}
	}

	@Override
	public Object getElementAt(int index) {
		// Return the elements in reverse order so that the newest notification appears at the top
		// This is done since prepending elements to an ArrayList is less efficient than appending them
		NewsItem newsItem = newsItems.get(getSize() - index - 1);
		return String.format("<html><h1>%s</h1><p>%s</p><p><em>%s</em></p></html>", newsItem.getHeadline(), newsItem.getDescription(), newsItem.getUrl());
	}

	@Override
	public int getSize() {
		return newsItems.size();
	}

}
