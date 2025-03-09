package net.entersol.indexeddb;

import org.itemscript.core.values.JsonArray;
import org.itemscript.core.values.JsonObject;

import com.google.gwt.user.client.Window;

public class IndexedDB {
	public Object database;

	public IndexedDB(String databaseName){
		browserFixing();
		openDatabase( databaseName, "");
	}
	public IndexedDB(String databaseName, String discription){
		browserFixing();
		openDatabase( databaseName, discription);
	}

	public native void synchronize(IndexedDB me, String version)/*-{
		
	}-*/;

	private native void indexGet(IndexedDB me, String objectName, String index, String properity)/*-{
		var db = me.@net.entersol.indexeddb.IndexedDB::database;
		var objectStore = db.transaction(objectName).objectStore(objectName);
		var objects = [];
		var index = objectStore.index(index);
		var cc = index.openCursor();
		cc.onsuccess = function(event) {
			var cursor = event.target.result;
			if (cursor) {
				objects.push(cursor.value);
				eval("cursor.continue()");
	  		}
	  		else{
	  			onIndexGetSuccess(objects);
	  		}
		};
		cc.onerror = function(event){
			onIndexGetFailed(event.code, event.message);
		};
	
	}-*/;
	
	private native void indexGetKey(String objectName, String index, String properity)/*-{
		var db = this.@net.entersol.indexeddb.IndexedDB::database;
		var objectStore = db.transaction(objectName).objectStore(objectName);
		var objects = [];
		var index = objectStore.index(index);
		var cc = index.openKeyCursor();
		cc.onsuccess = function(event) {
			var cursor = event.target.result;
			if (cursor) {
				objects.push(cursor.value);
				eval("cursor.continue()");
	  		}
	  		else{
	  			onIndexGetSuccess(objects);
	  		}
		};
		cc.onerror = function(event){
			onIndexGetFailed(event.code, event.message);
		};
	
	}-*/;
	
	//This function to bend browsers to work on standards
//---------------- Native Methods that has been used by normal methods   ---------------------------------
	
	// Used by normal createIndex
	public native void createIndex(String objectStore, String properity, boolean isUnique )/*-{
		db = this.@net.entersol.indexeddb.IndexedDB::database;
	
		var versionRequest = db.setVersion( eval(db.version) + 1 );
		versionRequest.onsuccess = function ( e ) {
			var trans = db.transaction([objectStore], IDBTransaction.READ_WRITE, 0);
			var store = trans.objectStore(objectStore);
			store.createIndex(properity,  properity, isUnique);
		};
	}-*/;

	// used by normal createObjectStore
	private native void deleteIndex(IndexedDB me, String objectStore, String properity)/*-{
		db = this.@net.entersol.indexeddb.IndexedDB::database;
	
		var versionRequest = db.setVersion( eval(db.version) + 1 );
		versionRequest.onsuccess = function ( e ) {
			var trans = db.transaction([objectStore], IDBTransaction.READ_WRITE, 0);
			var store = trans.objectStore(objectStore);
			store.deleteIndex(properity);
		};
	}-*/;
	
	private native void clear(IndexedDB me, String objectStore)/*-{
		db = this.@net.entersol.indexeddb.IndexedDB::database;

		var trans = db.transaction([objectStore], IDBTransaction.READ_WRITE, 0);
		var store = trans.objectStore(objectStore);
		store.clear();
	}-*/;
	
	public native void createObjectStore(String name, String id, boolean useAuto)/*-{
		db = this.@net.entersol.indexeddb.IndexedDB::database;
		version = eval (db.version) + 1;
		var versionRequest = db.setVersion(version);
		
		versionRequest.onsuccess = function ( e ) {
			

			if(db.objectStoreNames.contains(name)) {
              db.deleteObjectStore(name);
            }
			
			//DoFuture what to store from the following function
			db.createObjectStore( name, {keyPath:id}, useAuto );
		};
			
		this.@net.entersol.indexeddb.IndexedDB::onCreateObjectStoreSuccess(Ljava/lang/String;)(db.version);
	}-*/;
	
	public native void add(String objectStore, JsonArray rows)/*-{
		rows = eval ( "(" + rows + ")");
		var that = this;
	
		db = that.@net.entersol.indexeddb.IndexedDB::database;
		var transaction;
		try{
			var transaction = db.transaction([objectStore], $wnd.IDBTransaction.READ_WRITE,0);
		}
		catch(ex){			
			$wnd.alert(ex);
		}
		
		// Do something when all the data is added to the database.
		transaction.oncomplete = function(event) {
			that.@net.entersol.indexeddb.IndexedDB::onAddComplete()();
		};
		
		transaction.onerror = function(event) {
			that.@net.entersol.indexeddb.IndexedDB::onAddFailed(Ljava/lang/String;)(event.target.errorCode);
		};
		
		var objectStore = transaction.objectStore(objectStore);
		
		for (i = 0; i < rows.length; i++) {
			try{
				//FIXME onsuccess and onfailed 
				var request = objectStore.put(rows[i]);
			}
			catch(ex){
				$wnd.alert("Add Catch" + ex);
			}
		}
	}-*/;
	
	//Used by normal put
	public native void put(String objectStore, JsonArray rows)/*-{
		rows = eval ("(" + rows +")");
		var that = this;
		db = that.@net.entersol.indexeddb.IndexedDB::database;
		var transaction = db.transaction([objectStore], $wnd.IDBTransaction.READ_WRITE);
		
		// Do something when all the data is added to the database.
		transaction.oncomplete = function(event) {
			that.@net.entersol.indexeddb.IndexedDB::onAddComplete()();
		};
		
		transaction.onerror = function(event) {
			that.@net.entersol.indexeddb.IndexedDB::onAddFailed(Ljava/lang/String;)(db.version);
		};
		
		var objectStore = transaction.objectStore(objectStore);
		for (i = 0; i < rows.length; i++) {
			try{
				var request = objectStore.put(rows[i]);
			}
			catch(ex){
				$wnd.alert("put catch"+ex);
			}
		}	}-*/;
	//Used by normal delete
	private native void delete(IndexedDB me, String objectStore, JsonArray keys)/*-{
		rows = eval ("(" + rows +")");
		var that = this;
		db = that.@net.entersol.indexeddb.IndexedDB::database;
		var transaction = db.transaction([objectStore], $wnd.IDBTransaction.READ_WRITE);
		
		// Do something when all the data is added to the database.
		transaction.oncomplete = function(event) {
			that.@net.entersol.indexeddb.IndexedDB::onAddComplete()();
		};
		
		transaction.onerror = function(event) {
			that.@net.entersol.indexeddb.IndexedDB::onAddFailed(Ljava/lang/String;)(db.version);
		};
		
		var objectStore = transaction.objectStore(objectStore);
		for (key in keys) {
		  eval("objectStore.delete(key)");
		}
	}-*/;

	//Used by normal get
	private native void get(IndexedDB me, String objectStore, String key )/*-{
		db = this.@net.entersol.indexeddb.IndexedDB::database;
		var transaction = db.transaction([objectStore]);
		var objectStore = transaction.objectStore(objectStore);
		var request = objectStore.get(key);
		request.onerror = function(event) {
		 onGetFailed(event.code, event.message);
		};
		request.onsuccess = function(event) {
		  onGetSuccess (request.result);
		};
	}-*/;
	public native void getAll(String objectStore)/*-{
		var that = this;
		var objectStore;
		db = that.@net.entersol.indexeddb.IndexedDB::database;
		try{
			objectStore = db.transaction(objectStore).objectStore(objectStore);	
		}
		catch(ex){
			$wnd.alert("getAll catch " + ex);
		}
		
		var objects = [];
		var cc = objectStore.openCursor();
		
		cc.onsuccess = function(event) {
		  cursor = event.target.result;
		  $wnd.alert(eval("cursor"));
		  if (cursor) {
		  	try{
		    	objects.push(cursor.value);
		    	//$wnd.alert(cursor.value);
		  	}
		  	catch(exce){
		  		$wnd.alert(exce);
		  	}
		   // $wnd.alert("Polla");
		  }
		  else{
		  	$wnd.alert(objects[5].Age + "This is true");
		    that.@net.entersol.indexeddb.IndexedDB::onGetAllSuccess(Lorg/itemscript/core/values/JsonArray;)(objects);
		  }
		};
		cc.onerror = function(event){
			//$wnd.alert("onGetAllFailed");
			that.@net.entersol.indexeddb.IndexedDB::onGetFailed(Ljava/lang/String;I)(event.message, event.code);
		};
	}-*/;

	//Used by constructors
	private native void openDatabase(String name, String discription)/*-{
		var that = this;
		var dbRequest = $wnd.indexedDB.open(name, discription);
		
		dbRequest.onerror   = function ( e ) {
				$wnd.alert("Erorr");
		};
		
		dbRequest.onsuccess = function ( e ) {
			db = e.target.result;
			that.@net.entersol.indexeddb.IndexedDB::database = db;
			that.@net.entersol.indexeddb.IndexedDB::onOpenDatabaseSuccess(Ljava/lang/String;)(db.version);
		};
		//$wnd.alert(database);
	}-*/;	//used by constructor
	
	private native void browserFixing()/*-{
		if ( "webkitIndexedDB" in $wnd ) {
			$wnd.indexedDB      = $wnd.webkitIndexedDB;
			$wnd.IDBTransaction = $wnd.webkitIDBTransaction;
			$wnd.IDBIndex = $wnd.webkitIDBIndex;
			$wnd.IDBDatabaseError = $wnd.webkitIDBDatabaseError;
			$wnd.IDBDatabaseException = $wnd.webkitIDBDatabaseException;
			$wnd.IDBFactory = $wnd.webkitIDBFactory;
			$wnd.IDBCursor = $wnd.webkitIDBCursor;
			$wnd.IDBDatabase = $wnd.webkitIDBDatabase;
			$wnd.IDBRequest = $wnd.webkitIDBRequest;
			$wnd.IDBObjectStore = $wnd.webkitIDBObjectStore;
			$wnd.IDBKeyRange = $wnd.webkitIDBKeyRange;
			$wnd.IDBEvent = $wnd.webkitIDBEvent;
		
		}
		else if ( "mozIndexedDB" in $wnd ) {
		  $wnd.indexedDB = $wnd.mozIndexedDB;
		}
		if ( !$wnd.indexedDB ){
			$wnd.alert("Please, use Google Chrome or Firefox browsers instead of your browser.");
		}
	
	}-*/;
//------------------Handlers ------------------------

	public void onOpenDatabaseSuccess(String version){
		
	}
	public void onCreateObjectStoreSuccess(String version){
	}

	public void onAddComplete(){
		Window.alert("Sucssess");
	}
	public void onAddFailed(String message){
		Window.alert("onAddFailed " + message);
	}
	
	public void onGetSuccess(JsonObject row){
		
	}
	public void onGetAllSuccess(JsonArray rows){
		Window.alert("Get " + rows.toJsonString());
	}
	public void onGetFailed(String exception, int code){
		
	}
	
	public void onIndexGetSuccess(JsonArray rows){
		
	}
}

