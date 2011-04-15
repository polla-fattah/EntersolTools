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
	public void createObjectStore(String name, String id, boolean useAuto){
		Window.alert("Elaf " + database);
		createObjectStore( this, name, id, useAuto);
	}
	
	public native void synchronize(IndexedDB me, String version)/*-{
		
	}-*/;

	public void add(String objectStore, JsonArray rows){
		add(this, objectStore, rows);
	}
	public void put(String objectStore, JsonArray rows){
		put(this, objectStore, rows);
	}
	public void delete(String objectStore, JsonArray keys){
		delete(this, objectStore, keys);
	}
	public void deleteIndex(String objectStore, String properity){
		deleteIndex(this, objectStore, properity);
	}
	public void clear(String objectStore){
		clear(this, objectStore);
	}
	public void get(String objectStore, String key ){
		get(this,objectStore, key );
	}

	public void getAll(String objectStore){
		getAll(this, objectStore);
	}
	public void indexGet( String objectName, String index, String properity){
		indexGet(this, objectName, index, properity);
	}
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
	public void indexGetKey( String objectName, String index, String properity){
		indexGet(this, objectName, index, properity);
	}
	private native void indexGetKey(IndexedDB me, String objectName, String index, String properity)/*-{
		var db = me.@net.entersol.indexeddb.IndexedDB::database;
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
		db = me.@net.entersol.indexeddb.IndexedDB::database;
	
		var versionRequest = db.setVersion( eval(db.version) + 1 );
		versionRequest.onsuccess = function ( e ) {
			var trans = db.transaction([objectStore], IDBTransaction.READ_WRITE, 0);
			var store = trans.objectStore(objectStore);
			store.deleteIndex(properity);
		};
	}-*/;
	private native void clear(IndexedDB me, String objectStore)/*-{
		db = me.@net.entersol.indexeddb.IndexedDB::database;

		var trans = db.transaction([objectStore], IDBTransaction.READ_WRITE, 0);
		var store = trans.objectStore(objectStore);
		store.clear();
	}-*/;
	private native void createObjectStore(IndexedDB me, String name, String id, boolean useAuto)/*-{
		
		db = me.@net.entersol.indexeddb.IndexedDB::database;
		$wnd.alert("Sanar" + db);
		version = eval (db.version) + 1;
		var versionRequest = db.setVersion(version);
			
		versionRequest.onsuccess = function ( e ) {
			//TODO what to store from the following function
			db.createObjectStore( name, id, useAuto );
		};
			
		me.@net.entersol.indexeddb.IndexedDB::onCreateObjectStoreSuccess(Ljava/lang/String;)(db.version);
	}-*/;	
	
	//Used by normal add
	private native void add(IndexedDB me, String objectStore, JsonArray rows)/*-{
		rows = eval ("(" + rows +")");
		db = me.@net.entersol.indexeddb.IndexedDB::database;
		var transaction = db.transaction([objectStore], IDBTransaction.READ_WRITE);
		
		// Do something when all the data is added to the database.
		transaction.oncomplete = function(event) {
			me.@net.entersol.indexeddb.IndexedDB::onAddComplete()();
		};
		
		transaction.onerror = function(event) {
			me.@net.entersol.indexeddb.IndexedDB::onAddFailed(Ljava/lang/String;)(db.version);
		};
		
		var objectStore = transaction.objectStore(objectStore);
		for (row in rows) {
		  var request = objectStore.add(row);
		}
	}-*/;
	//Used by normal put
	private native void put(IndexedDB me, String objectStore, JsonArray rows)/*-{
		rows = eval ("(" + rows +")");
		db = me.@net.entersol.indexeddb.IndexedDB::database;
		var transaction = db.transaction([objectStore], IDBTransaction.READ_WRITE);
		
		// Do something when all the data is added to the database.
		transaction.oncomplete = function(event) {
			me.@net.entersol.indexeddb.IndexedDB::onAddComplete()();
		};
		
		transaction.onerror = function(event) {
			me.@net.entersol.indexeddb.IndexedDB::onAddFailed(Ljava/lang/String;)(db.version);
		};
		
		var objectStore = transaction.objectStore(objectStore);
		for (row in rows) {
		  var request = objectStore.put(row);
		}
	}-*/;
	//Used by normal delete
	private native void delete(IndexedDB me, String objectStore, JsonArray keys)/*-{
		rows = eval ("(" + rows +")");
		db = me.@net.entersol.indexeddb.IndexedDB::database;
		var transaction = db.transaction([objectStore], IDBTransaction.READ_WRITE);
		
		// Do something when all the data is added to the database.
		transaction.oncomplete = function(event) {
			me.@net.entersol.indexeddb.IndexedDB::onAddComplete()();
		};
		
		transaction.onerror = function(event) {
			me.@net.entersol.indexeddb.IndexedDB::onAddFailed(Ljava/lang/String;)(db.version);
		};
		
		var objectStore = transaction.objectStore(objectStore);
		for (key in keys) {
		  eval("objectStore.delete(key)");
		}
	}-*/;

	//Used by normal get
	private native void get(IndexedDB me, String objectStore, String key )/*-{
		db = me.@net.entersol.indexeddb.IndexedDB::database;
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
	private native void getAll(IndexedDB me, String objectStore)/*-{
	db = me.@net.entersol.indexeddb.IndexedDB::database;
	var objectStore = db.transaction(objectStore).objectStore(objectStore);
	var objects = [];
	var cc = objectStore.openCursor();
	cc.onsuccess = function(event) {
	  var cursor = event.target.result;
	  if (cursor) {
	    objects.push(cursor.value);
	    eval("cursor.continue()");
	  }
	  else {
	    onGetAllSuccess(objects);
	  }
	};
	cc.onerror = function(event){
		onGetAllFailed(event.code, event.message);
	};
}-*/;

	
	//Used by constructors
	private native void openDatabase(String name, String discription)/*-{
		$wnd.alert("inside openDatabase");
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
		Window.alert("Hi");
	}

	public void onAddComplete(){
		
	}
	public void onAddFailed(String message){
		
	}
	
	public void onGetSuccess(JsonObject row){
		
	}
	public void onGetAllFailed(int code, String exception){
		
	}
	public void onGetAllSuccess(JsonArray rows){
		
	}
	public void onGetFailed(int code, String exception){
		
	}
	
	public void onIndexGetSuccess(JsonArray rows){
		
	}
	public void onIndexGetFailed(int code, String exception){
		
	}

}

