package net.robinjam.rminews;

import net.robinjam.notifications.Notification;

public class NewsItem implements Notification {

	private static final long serialVersionUID = 1L;
	
	private final String headline, description, url;
	
	public NewsItem(String headline, String description, String url) {
		this.headline = headline;
		this.description = description;
		this.url = url;
	}

	public String getHeadline() {
		return headline;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getUrl() {
		return url;
	}
	
}
