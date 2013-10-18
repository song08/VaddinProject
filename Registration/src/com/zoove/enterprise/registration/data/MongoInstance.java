package com.zoove.enterprise.registration.data;

import java.net.UnknownHostException;
import java.util.Arrays;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class MongoInstance {
	static Mongo m = null;

	protected MongoInstance() {
		m = null;
	}

	public static Mongo mongo() {
		if (m == null) {
			try {
				m = new Mongo(Arrays.asList(new ServerAddress("172.16.216.131",
						27017)));
			} catch (UnknownHostException e) {
				System.out.println("Mongo DB server address unknown, location not saved");
				m = null;
			}
		}

		return m;
	}
}
