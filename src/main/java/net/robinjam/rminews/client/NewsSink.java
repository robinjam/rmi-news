package net.robinjam.rminews.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;

import net.robinjam.notifications.INotification;
import net.robinjam.notifications.INotificationSource;
import net.robinjam.notifications.NotificationSink;
import net.robinjam.rminews.NewsItem;

/**
 * Receives notifications from one or more news sources and provides list models for both news items and notification sources.
 * 
 * @author robinjam
 */
public class NewsSink extends NotificationSink {

	private static final long serialVersionUID = 1L;

	protected NewsSink() throws RemoteException {
		super();
	}
	
	private static class NewsItemListModel extends AbstractListModel {
		
		private static final long serialVersionUID = 1L;
		
		private List<NewsItem> newsItems = new ArrayList<NewsItem>();

		@Override
		public Object getElementAt(int index) {
			NewsItem newsItem = newsItems.get(getSize() - index - 1);
			return String.format("<html><h1>%s</h1><p>%s</p><p><em>%s</em></p></html>", newsItem.getHeadline(), newsItem.getDescription(), newsItem.getUrl());
		}

		@Override
		public int getSize() {
			return newsItems.size();
		}
		
		public void addNewsItem(NewsItem newsItem) {
			newsItems.add(newsItem);
			fireIntervalAdded(this, 0, 0);
		}
		
	}
	
	private NewsItemListModel newsItemListModel = new NewsItemListModel();

	@Override
	public void notify(INotificationSource source, INotification notification) throws RemoteException {
		newsItemListModel.addNewsItem((NewsItem) notification);
	}
	
	public ListModel getNewsItemListModel() {
		return newsItemListModel;
	}

}
