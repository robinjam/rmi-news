package net.robinjam.rminews.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;

import net.robinjam.notifications.Notification;
import net.robinjam.notifications.NotificationSink;
import net.robinjam.notifications.NotificationSource;
import net.robinjam.rminews.NewsItem;

public class NewsSink extends UnicastRemoteObject implements NotificationSink {

	private static final long serialVersionUID = 1L;

	protected NewsSink() throws RemoteException {
		super();
	}
	
	@SuppressWarnings("serial")
	private static class NewsItemListModel extends AbstractListModel {
		
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
	public void notify(NotificationSource source, Notification notification) throws RemoteException {
		newsItemListModel.addNewsItem((NewsItem) notification);
	}
	
	public ListModel getNewsItemListModel() {
		return newsItemListModel;
	}

}
