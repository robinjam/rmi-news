package net.robinjam.rminews.server;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import net.robinjam.notifications.Notification;
import net.robinjam.notifications.NotificationSink;
import net.robinjam.notifications.NotificationSource;
import net.robinjam.rminews.NewsItem;

import org.junit.Test;

public class NewsSourceTest {
	
	private final NewsItem dummyItem = new NewsItem("Headline", "Description", "http://www.google.com");
	
	/**
	 * Verifies that if the same sink is registered to the same source multiple times, it only receives each unique notification once.
	 */
	@Test
	public void testDuplicateRegistriesFailSilently() throws RemoteException {
		NewsSource source = new NewsSource();
		NotificationSink sink = new NotificationSink() {
			private int numNotifications;
			
			@Override
			public void notify(NotificationSource source, Notification notification) throws RemoteException {
				assertEquals(1, ++numNotifications);
			}
		};
		source.registerSink(sink);
		source.registerSink(sink);
		source.notifySinks(dummyItem);
	}
	
	/**
	 * Verifies that if a sink becomes unavailable, no exception is thrown by the source when notifySinks is called.
	 */
	@Test
	public void testDisconnectedSinksDoNotCauseFailure() throws RemoteException {
		NewsSource source = new NewsSource();
		source.registerSink(new NotificationSink() {
			@Override
			public void notify(NotificationSource source, Notification notification) throws RemoteException {
				throw new RemoteException();
			}
		});
		source.notifySinks(dummyItem);
	}

}
