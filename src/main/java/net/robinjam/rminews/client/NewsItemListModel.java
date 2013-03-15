package net.robinjam.rminews.client;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import net.robinjam.notifications.INotification;
import net.robinjam.notifications.INotificationListener;
import net.robinjam.notifications.INotificationSource;
import net.robinjam.rminews.NewsItem;

public class NewsItemListModel extends AbstractListModel implements INotificationListener {

	private static final long serialVersionUID = 1L;
	
	private List<NewsItem> newsItems = new ArrayList<NewsItem>();

	@Override
	public void notificationReceived(INotificationSource source, INotification notification) {
		if (notification instanceof NewsItem) {
			newsItems.add((NewsItem) notification);
			fireIntervalAdded(this, 0, 0);
		}
	}

	@Override
	public Object getElementAt(int index) {
		NewsItem newsItem = newsItems.get(getSize() - index - 1);
		return String.format("<html><h1>%s</h1><p>%s</p><p><em>%s</em></p></html>", newsItem.getHeadline(), newsItem.getDescription(), newsItem.getUrl());
	}

	@Override
	public int getSize() {
		return newsItems.size();
	}

}
