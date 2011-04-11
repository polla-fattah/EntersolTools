package net.entersol.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;


public class EntersolTools implements EntryPoint {

	public void onModuleLoad() {
		RootPanel.get("entersolTool").add(new HTML("Hi there"));

	}
}
