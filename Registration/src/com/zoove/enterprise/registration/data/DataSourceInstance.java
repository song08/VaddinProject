package com.zoove.enterprise.registration.data;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DataSourceInstance {
	Logger log = Logger.getLogger(DataSourceInstance.class);
	static DataSource ds = null;

	protected DataSourceInstance() {
		ds = null;
	}

	public static DataSource dataSource() {
		try {
			InitialContext ctx = new InitialContext ();
			ds = (DataSource) ctx.lookup ("java:comp/env/zapapps/mydb");
			if (ds == null) {
				System.out.println("Could not obtain data source for location");
				return null;
			}
		}
		catch (NamingException e) {
			System.out.println("Could not obtain data source for location");
			System.out.println(e.getMessage());
			return null;
		}
		
		return ds;
	}
}
