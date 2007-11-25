package org.zlibrary.core.library;

import java.io.InputStream;

import java.util.Map;
import java.util.HashMap;

import org.zlibrary.core.xml.ZLXMLReader;
import org.zlibrary.core.view.ZLPaintContext;

public abstract class ZLibrary {
	private final Map<String,String> myProperties = new HashMap<String,String>();

	public static ZLibrary getInstance() {
		return ourImplementation;
	}
	
	private static ZLibrary ourImplementation;

	protected ZLibrary() {
		ourImplementation = this;
	}

	public final String getApplicationName() {
		return myProperties.get("applicationName");
	}

	protected final Class getApplicationClass() {
		try {
			return Class.forName(myProperties.get("applicationClass"));
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	abstract public ZLPaintContext createPaintContext();
	abstract public InputStream getResourceInputStream(String fileName);

	protected final void loadProperties() {
		new ZLXMLReader() {
			public void startElementHandler(String tag, String [] attributes) {
				if (tag.equals("property")) {
					myProperties.put(attributeValue(attributes, "name"), attributeValue(attributes, "value"));
				}
			}
		}.read("data/application.xml");
	}
}
