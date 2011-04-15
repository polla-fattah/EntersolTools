package net.entersol.client;

import net.entersol.indexeddb.IndexedDB;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;


public class EntersolTools implements EntryPoint {
	private IndexedDB idb;
	public void onModuleLoad() {
		idb = new IndexedDB("test"){
			@Override 
			public void onOpenDatabaseSuccess(String version){
				Window.alert(version);
				idb.createObjectStore("testing", "id", false);
			}
		};
		
		
		RootPanel.get("entersolTool").add(new HTML("Hi there"));

	}
}
