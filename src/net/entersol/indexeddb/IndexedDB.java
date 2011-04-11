package net.entersol.indexeddb;

public class IndexedDB {
	public IndexedDB(String database){
		createDatabase(database);
	}
	public native void createDatabase(String database)/*-{
		$wnd.alert(database);
	}-*/;
}
