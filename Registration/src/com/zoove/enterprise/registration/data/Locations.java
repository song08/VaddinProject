package com.zoove.enterprise.registration.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.HierarchicalContainer;
import com.zoove.enterprise.registration.ui.LocationTreeTable;

public class Locations {
	Logger log = Logger.getLogger(Locations.class);
	
	HierarchicalContainer locations;
	final public static String [] modes = {"DMA", "County"};
	String mode = "DMA";
	
	String temp_locationlevel_filter = "\"COUNTY\",\"POINT\"";
	
	DataSource ds = null;
	
	public Locations() {
		locations = new HierarchicalContainer();
		locations.addContainerProperty("locationId", Long.class, null);
		locations.addContainerProperty("locationName", String.class, null);
		locations.addContainerProperty("locationLevel", String.class, null);
		locations.addContainerProperty("locationParentId", Long.class, null);
	}
	
	public HierarchicalContainer getLocations() {
		return locations;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		if (this.mode.compareToIgnoreCase(mode)!=0) {
			this.mode = mode;
			this.temp_locationlevel_filter = "";
			for (int i=0; i<modes.length;i++) {
				if (modes[i].compareToIgnoreCase(mode)!=0) {
					if (this.temp_locationlevel_filter.length()>0)
						this.temp_locationlevel_filter += ",";
					this.temp_locationlevel_filter = this.temp_locationlevel_filter+"\""+modes[i].toUpperCase()+"\"";
				}
			}
		}
	}

	public int loadChildren (String dbInstance, long parentId) {
		int result = 0;
		
		try {
			InitialContext ctx = new InitialContext ();
			ds = (DataSource) ctx.lookup ("java:comp/env/zapapps/mydb");
			if (ds == null) {
				log.error("Could not obtain data source for location");
				return -1;
			}
		}
		catch (NamingException e) {
			log.error("Could not obtain data source for location");
			log.error(e.getMessage());
			return -1;
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection(); 
			
			String sql = 
					"select * from (select aa.locationid, aa.locationname, aa.locationlevel, " +
					"			sum(if(isnull(bb.locationid),0,1)) numOfChildren " +
					"from "+dbInstance+".TBLLOCATIONS aa left join "+dbInstance+".TBLLOCATIONS bb on aa.locationid = bb.locationparentid " +
					"where aa.locationparentid=? and aa.locationlevel not in (" + temp_locationlevel_filter + ") " +
					"group by aa.locationid, aa.locationname, aa.locationlevel) cc where locationid is not null order by locationname ";

			stmt = conn.prepareStatement(sql);
			
			stmt.setLong(1, parentId);
			rs = stmt.executeQuery();
			
			Item parent = locations.getItem(parentId);
			Item item = null;
			//locations.removeItemRecursively(parentId);
			
			long locationId;
			String locationName, locationLevel;
			
			while (rs.next()) {
				locationId = rs.getLong(1);
				locationName = rs.getString(2);
				locationLevel = rs.getString(3);
				
				item = locations.addItem(locationId);
				item.getItemProperty("locationId").setValue(locationId);
				item.getItemProperty("locationName").setValue(locationName);
				item.getItemProperty("locationLevel").setValue(locationLevel);
				item.getItemProperty("locationParentId").setValue(parentId);
				if (rs.getInt(4)>0)
					locations.setChildrenAllowed(locationId, true);
				else
					locations.setChildrenAllowed(locationId, false);
				
				if (parent!=null)
					locations.setParent(locationId, parentId);
			}
		}
		catch (SQLException e) {
			log.error ("Exception while retrieving location");
			log.error (e.getMessage());
		}
		finally {
			try {
				if (rs!=null) rs.close(); rs = null;
				if (stmt!=null) stmt.close(); stmt=null;
				if (conn!=null) conn.close(); conn=null;
			}
			catch (Exception e) {
				log.error ("Couldnot close DB connection after retrieving location");
				log.error (e.getMessage());
			}
		}
		
		return result;
	}
	
}
