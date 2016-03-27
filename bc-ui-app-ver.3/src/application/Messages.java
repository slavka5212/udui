package application;

import java.util.*;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "application.translation.messages"; //$NON-NLS-1$
   
	public static String key_one;
	public static String key_two;
  
	private static ResourceBundle messages;
	static final Locale enLocale = new Locale("en","US"); //$NON-NLS-1$ //$NON-NLS-2$
	static final Locale skLocale = new Locale("sk","SK"); //$NON-NLS-1$ //$NON-NLS-2$

	public static String MainApp_title;
  
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	  
	private Messages() {
	}

	public static ResourceBundle getMessages() {
		return messages;
	}
	
	public static void setMessages(Locale currentLocale) {
		Messages.messages = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale); //$NON-NLS-1$
	}
	  
}