package com.bowlong.third;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.TimeUnit;

import org.mapdb.Atomic;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Store;

import com.bowlong.lang.NumEx;
import com.bowlong.lang.StrEx;
import com.bowlong.lang.task.ThreadEx;
@SuppressWarnings("rawtypes")
public class MapDB {

	// ///////////////////////////////
	public static final DB newDirectMemoryDB(int cacheSize, int maxSize) {
		DBMaker maker = DBMaker.newMemoryDirectDB();
		maker.cacheSize(cacheSize);
		maker.sizeLimit(maxSize);
		return maker.make();
	}

	public static final DB newFileDB(File fn, int cacheSize, int maxSize) {
		if (fn == null)
			return null;

		return DBMaker.newFileDB(fn).sizeLimit(maxSize).cacheSize(cacheSize)
				.closeOnJvmShutdown().make();
	}

	public static final DB newMemoryDB(int cacheSize, int maxSize) {
		return DBMaker.newMemoryDB().sizeLimit(maxSize).cacheSize(cacheSize)
				.compressionEnable().deleteFilesAfterClose()
				.closeOnJvmShutdown().make();
	}

	public static final DB newTempFileDB(int cacheSize, int maxSize,
			int asyncDelay) {
		return DBMaker.newTempFileDB().sizeLimit(maxSize).cacheSize(cacheSize)

		.deleteFilesAfterClose().closeOnJvmShutdown().make();
	}

	public static final HTreeMap<Object, Object> newTempHashMap(int maxSize) {
		return DBMaker.newTempHashMap();
	}

	public static final Set<Object> newTempHashSet() {
		return DBMaker.newTempHashSet();
	}

	public static final BTreeMap<Object, Object> newTempTreeMap() {
		return DBMaker.newTempTreeMap();
	}

	public static final NavigableSet<Object> newTempTreeSet() {
		return DBMaker.newTempTreeSet();
	}

	public static final HTreeMap<Object, Object> makeOrGetHashMap(DB db,
			String name, int maxSize) {
		if (db == null || StrEx.isEmpty(name))
			return null;

		return db.createHashMap(name).expireMaxSize(maxSize).makeOrGet();
	}

	public static final HTreeMap<Object, Object> getHashMap(DB db, String name) {
		if (db == null || StrEx.isEmpty(name))
			return null;

		return db.getHashMap(name);
	}

	public static final Set<Object> getHashSet(DB db, String name) {
		return db.getHashSet(name);
	}

	public static final Set<Object> makeOrGetHashSet(DB db, String name,
			int maxSize) {
		if (db == null || StrEx.isEmpty(name))
			return null;

		return db.createHashSet(name).expireMaxSize(maxSize).makeOrGet();
	}

	public static final Atomic.Integer makeOrGetAtomicInteger(DB db,
			String name, int initValue) {
		if (db == null || StrEx.isEmpty(name))
			return null;

		Atomic.Integer r2 = getAtomicInteger(db, name);
		if (r2 == null)
			r2 = db.createAtomicInteger(name, initValue);
		return r2;
	}

	public static final Atomic.Integer getAtomicInteger(DB db, String name) {
		if (db == null || StrEx.isEmpty(name))
			return null;

		return db.getAtomicInteger(name);
	}

	public static final Atomic.Long getAtomicLong(DB db, String name) {
		if (db == null || StrEx.isEmpty(name))
			return null;

		return db.getAtomicLong(name);
	}

	public static final Atomic.Long makeOrGetAtomicLong(DB db, String name,
			int initValue) {
		if (db == null || StrEx.isEmpty(name))
			return null;

		Atomic.Long r2 = db.getAtomicLong(name);
		if (r2 == null)
			r2 = db.createAtomicLong(name, initValue);
		return r2;
	}

	public static final void commitAndClose(DB db) {
		if (db == null)
			return;
		db.commit();
		db.close();
	}

	// ///////////////////////////////
	public static class D1 implements Serializable {
		private static final long serialVersionUID = 5261992871319054470L;
		private int id;
		private String name;

		public D1(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		@Override
		public String toString() {
			return "D1 [id=" + id + ", name=" + name + "]";
		}
	}

	public static boolean mkdirs(String fn) throws IOException {
		return mkdirs(new File(fn));
	}

	public static boolean mkdirs(File fn) throws IOException {
		if (fn == null)
			return false;
		if (fn.exists())
			return true;
		File pfn = fn.getParentFile();
		if (!pfn.exists())
			pfn.mkdirs();
		return true;
	}

	public static void main(String[] args) throws IOException {
		String dbname = "a/b/c/d/e/aa";
		File fn = new File(dbname);
		System.out.println(mkdirs(dbname));
		DB db = newFileDB(fn, 1000, 100000);
		HTreeMap<Object, Object> map = makeOrGetHashMap(db, "d1", 1000);
		System.out.println(map.size());
		for (int i = 0; i < 999; i++) {
			map.put(i, "value-" + i);
		}
		db.commit();
		ThreadEx.Sleep(1000);
		db.close();
	}

	public static void main3(String[] args) {

		// DB db = DBMaker.newFileDB(new File("db/map/testdb"))
		// .closeOnJvmShutdown().encryptionEnable("password").make();
		DBMaker maker = DBMaker.newFileDB(new File("db/map/testdb"))
				.compressionEnable().cacheLRUEnable();
		DB db = maker.make();
		ConcurrentNavigableMap<Integer, D1> map = db
				.getTreeMap("collectionName");

		System.out.println(map.size());
		System.out.println(map);

		// map.clear();

		// Pump.sort(source, batchSize, comparator, serializer)

		// for (int i = 0; i < 1 * 10000; i++) {
		// System.out.println(i);
		// int id = 100 + i;
		// map.put(id, new D1(id, "v" + i));
		// }

		db.commit();
		return;
		//
		// DB db = DBMaker.newFileDB(new File("db/map/testdb")).make();
		// // open existing an collection (or create new)
		// ConcurrentNavigableMap<Integer, D1> map = db
		// .getTreeMap("collectionName");
		// // map.clear();
		// System.out.println(map.size());
		// // System.out.println(map);
		//
		// map.put(1, new D1(1, "ont"));
		// map.put(2, new D1(2, "two"));
		//
		// System.out.println(map.get(1000));
		// // // map.keySet() is now [1,2]
		// // db.commit(); // persist changes into disk
		// // map.put(3, "three");
		// // // map.keySet() is now [1,2,3]
		// // db.rollback(); // revert recent changes
		// // map.keySet() is now [1,2]
		//
		// // map.put(3, new D1(2, "three"));
		// for (int i = 0; i < 1 * 10000; i++) {
		// System.out.println(i);
		// int id = 100 + i;
		// map.put(id, new D1(id, "v" + i));
		// }
		// db.commit();
		// db.close();
	}

	public static void main2(String[] args) {
		Runtime rt = Runtime.getRuntime();
		// init off-heap store with 2GB size limit
		DBMaker maker = DBMaker.newMemoryDirectDB();
		DB db = maker // use off-heap memory, on-heap is
						// `.newMemoryDB()`
				.sizeLimit(2) // limit store size to 2GB
				.transactionDisable() // better performance
				.make();

		// create map, entries are expired if not accessed (get,iterate) for 10
		// seconds or 30 seconds after 'put'
		// There is also maximal size limit to prevent OutOfMemoryException
		HTreeMap<Integer, String> map = db.createHashMap("cache")
				.expireMaxSize(1000000).expireAfterWrite(30, TimeUnit.SECONDS)
				.expireAfterAccess(10, TimeUnit.SECONDS).make();
		// load stuff
		for (int i = 0; i < 200000; i++) {
			map.put(i, randomString(1000));
		}

		// one can monitor two space usage numbers:

		for (int i = 0; i < 1000000; i++) {
			if (i % 1000 == 0) {
				db.compact();
			}

			// free space in store
			long freeSize = Store.forDB(db).getFreeSize();

			// current size of store (how much memory it has allocated
			long currentSize = Store.forDB(db).getCurrSize();

			System.out.println("map size:" + map.size() + "  freeSize:"
					+ NumEx.MB(freeSize) + "MB currentSize:"
					+ (currentSize / NumEx.MB) + "MB totalMemory:"
					+ NumEx.MB(rt.totalMemory()) + "MB");
			ThreadEx.Sleep(1000);

			for (int j = 0; j < 100; j++) {
				if (map.size() < 1000)
					break;

				Object key2 = map.keySet().iterator().next();
				map.remove(key2);
			}

		}

	}

	public static String randomString(int size) {
		String chars = "0123456789abcdefghijklmnopqrstuvwxyz !@#$%^&*()_+=-{}[]:\",./<>?|\\";
		StringBuilder b = new StringBuilder(size);
		Random r = new Random();
		for (int i = 0; i < size; i++) {
			b.append(chars.charAt(r.nextInt(chars.length())));
		}
		return b.toString();
	}
}
