package net.entersol.client;

import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;

import net.entersol.indexeddb.IndexedDB;
import net.entersol.iogate.IOGate;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class EntersolTools implements EntryPoint {
	private IndexedDB idb;
	private Button buton, getAll;
	public void onModuleLoad() {
		buton = new Button("add");
		buton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				JsonArray array = IOGate.SYSTEM.createArray();
				JsonObject obj1 = IOGate.SYSTEM.createObject();
				obj1.p("id", "" + (int) (Math.random() * 10000));
				obj1.p("Name", "Polla");
				obj1.p("Age", "30");
				array.a(obj1);

				idb.add("testing",array);
				
			}});
		
		idb = new IndexedDB("test"){
			@Override
			public void onGetAllSuccess(JsonArray arr){
				Window.alert("Get is All " + arr.toJsonString());
				
			}
			@Override 
			public void onOpenDatabaseSuccess(String version){
				idb.createObjectStore("testing", "id", false);
			}
		};
		getAll = new Button("GetAll");
		getAll.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				idb.getAll("testing");
				
			}
		});
		
		VerticalPanel v = new VerticalPanel();
		v.add(buton);
		v.add(new HTML("Hi there"));
		v.add(getAll);
		
		RootPanel.get("entersolTool").add(v);

	}
}
