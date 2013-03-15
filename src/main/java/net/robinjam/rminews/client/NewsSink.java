package net.robinjam.rminews.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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

/**
 * Receives notifications from one or more news sources and provides list models for both news items and notification sources.
 * 
 * @author robinjam
 */
public class NewsSink extends UnicastRemoteObject implements NotificationSink {

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
	
	private static class NewsSourceListModel extends AbstractListModel {
		
		private static final long serialVersionUID = 1L;
		
		private List<String> urls = new ArrayList<String>();

		@Override
		public Object getElementAt(int index) {
			return urls.get(index);
		}

		@Override
		public int getSize() {
			return urls.size();
		}
		
		public void addNewsSource(String url) {
			urls.add(url);
			fireIntervalAdded(this, urls.size() - 1, urls.size() - 1);
		}
		
	}
	
	private NewsItemListModel newsItemListModel = new NewsItemListModel();
	private NewsSourceListModel newsSourceListModel = new NewsSourceListModel();

	@Override
	public void notify(NotificationSource source, Notification notification) throws RemoteException {
		newsItemListModel.addNewsItem((NewsItem) notification);
	}
	
	public ListModel getNewsItemListModel() {
		return newsItemListModel;
	}
	
	public ListModel getNewsSourceListModel() {
		return newsSourceListModel;
	}
	
	public void addSubscription(String url) throws MalformedURLException, RemoteException, NotBoundException {
		NotificationSource source = (NotificationSource) Naming.lookup(url);
		source.registerSink(this);
		newsSourceListModel.addNewsSource(url);
	}

}
