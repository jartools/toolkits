package com.bowlong.sql;

import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

public class DB4oEx {

	public static final ObjectContainer openDb(final String db) {
		// EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		ObjectContainer container = Db4oEmbedded.openFile(db);
		return container;
	}

	public static final void closeDb(final ObjectContainer container) {
		container.close();
	}

	public static class Pilot {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Pilot(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Pilot [name=" + name + "]";
		}
	}

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		ObjectContainer container = openDb("db/db4o/databaseFile.db4o");
		try {
			// store a new pilot
			Pilot pilot = new Pilot("Joe");
			container.store(pilot);

			// query for pilots
			List<Pilot> pilots = container.query(new Predicate<Pilot>() {
				@Override
				public boolean match(Pilot pilot) {
					return pilot.getName().startsWith("Jo");
				}
			});

			System.out.println(pilots);

			// update pilot
			Pilot toUpdate = pilots.get(0);
			toUpdate.setName("New Name");
			container.store(toUpdate);

			// delete pilot
			container.delete(toUpdate);
		} finally {
			container.close();
		}
	}

}
