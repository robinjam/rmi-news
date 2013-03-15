package net.robinjam.rminews;

import net.robinjam.notifications.Notification;

/**
 * Represents a news item with a title, a brief description and a URL the user may visit to read more.
 * 
 * @author James Robinson
 */
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
