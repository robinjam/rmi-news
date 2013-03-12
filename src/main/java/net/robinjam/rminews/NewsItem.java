package net.robinjam.rminews;

import net.robinjam.notifications.Notification;

/**
 * Represents a single item in a news feed.
 * 
 * @author robinjam
 */
public class NewsItem implements Notification {

	private static final long serialVersionUID = 1L;
	
	private final String text;
	
	public NewsItem(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	
}
