package com.zoove.enterprise.registration.ui.map.nelson;

import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.zoove.enterprise.registration.data.DataSourceInstance;
import com.zoove.enterprise.registration.data.MongoInstance;
import com.zoove.enterprise.registration.ui.map.jakpojo.GeometryPOJO;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

public class LocationKmls {
	Logger logger = Logger.getLogger(LocationKmls.class);

	private String dbInstance = new String("mydb");
	private Map<Long, Placemark> locationKmls = null;
	
	static LocationKmls instance = null;

	protected LocationKmls() {

	}

	public static LocationKmls getInstance() {
		if (instance==null) {
			instance = new LocationKmls();
			instance.loadGeometryFromMySQL();
		}
		return instance;
	}
	
	public void loadKmls() {
		loadGeometryFromMySQL();
	}

	public void loadGeometryFromMongo() {
		Mongo m = MongoInstance.mongo();
		;
		DB mongodb = null;
		DBCollection collection = null;
		Gson gson = new Gson();

		if (m == null) {
			logger.error("Mongo DB server address unknown, cannot load KML from DB");
			return;
		}

		mongodb = m.getDB("Location");
		if (mongodb == null) {
			logger.error("Could not get location mongo DB");
			return;
		}

		collection = mongodb.getCollection("Placemarks");

		if (collection != null) {
			BasicDBObject query = new BasicDBObject();
			query.put("level", "STATE");

			DBCursor cursor = collection.find(query);

			if (locationKmls == null)
				locationKmls = new HashMap<Long, Placemark>();
			while (cursor.hasNext()) {
				DBObject o = cursor.next();
				Placemark pm = new Placemark();

				pm.setName((String) o.get("name"));
				pm.setDescription((String) o.get("description"));
				pm.setId((String) o.get("id"));
				String jsonString = (String) o.get("geometry");
				GeometryPOJO pojo = gson.fromJson(jsonString,
						GeometryPOJO.class);
				Geometry p = pojo.toJakGeometry();
				pm.setGeometry(p);

				locationKmls.put(Long.parseLong(pm.getId()), pm);
			}

			cursor.close();

			query.clear();
			query.put("level", "DMA");

			cursor = collection.find(query);

			while (cursor.hasNext()) {
				DBObject o = cursor.next();
				Placemark pm = new Placemark();

				pm.setName((String) o.get("name"));
				pm.setDescription((String) o.get("description"));
				pm.setId((String) o.get("id"));
				String jsonString = (String) o.get("geometry");
				GeometryPOJO pojo = gson.fromJson(jsonString,
						GeometryPOJO.class);
				Geometry p = pojo.toJakGeometry();
				pm.setGeometry(p);

				locationKmls.put(Long.parseLong(pm.getId()), pm);
			}

			cursor.close();

		}

	}

	public void loadGeometryIntoMongo() {
		Kml kml;
		Feature feature;
		Mongo m = MongoInstance.mongo();
		;
		DB mongodb = null;
		DBCollection collection = null;
		Gson gson = new Gson();

		if (m == null) {
			logger.error("Mongo DB server address unknown, location not saved");
		} else {
			mongodb = m.getDB("Location");
			collection = mongodb.getCollection("Placemarks");
		}

		kml = Kml.unmarshal(new File("STATE.kml"), false);

		feature = kml.getFeature();
		if (feature instanceof Document) {
			Document doc = (Document) feature;
			List<Feature> features = doc.getFeature();
			for (Feature f : features) {
				if (f instanceof Folder) {
					Folder folder = (Folder) f;
					List<Feature> features_folder = folder.getFeature();
					for (Feature f_folder : features_folder) {
						if (f_folder instanceof Placemark) {
							Placemark pm = (Placemark) f_folder;
							if (collection != null) {
								BasicDBObject dbdoc = new BasicDBObject();
								dbdoc.put("name", pm.getName());
								dbdoc.put("level", "STATE");
								dbdoc.put("description", pm.getDescription());
								dbdoc.put("id", pm.getId());
								Geometry p = pm.getGeometry();
								String jsonString = gson
										.toJson(new GeometryPOJO()
												.fromJakGeometry(p));
								dbdoc.put("geometry", jsonString);
								collection.insert(dbdoc);
							}
						}
					}
				} else if (f instanceof Placemark) {
					Placemark pm = (Placemark) f;
					if (collection != null) {
						BasicDBObject dbdoc = new BasicDBObject();
						dbdoc.put("name", pm.getName());
						dbdoc.put("level", "STATE");
						dbdoc.put("description", pm.getDescription());
						dbdoc.put("id", pm.getId());
						Geometry p = pm.getGeometry();
						String jsonString = gson.toJson(new GeometryPOJO()
								.fromJakGeometry(p));
						dbdoc.put("geometry", jsonString);
						collection.insert(dbdoc);
					}
				}
			}

			/*
			 * List<StyleSelector> styleSelectors = doc.getStyleSelector();
			 * 
			 * styleMap = new StyleMap(); for (StyleSelector styleSelector2 :
			 * styleSelectors) { de.micromata.opengis.kml.v_2_2_0.Style s =
			 * (de.micromata.opengis.kml.v_2_2_0.Style) styleSelector2;
			 * styles.addItem("#" + styleSelector2.getId());
			 * 
			 * Style style = new Style();
			 * styleMap.setStyle(styleSelector2.getId(), style);
			 * 
			 * String substring = s.getLineStyle().getColor();
			 * style.setStrokeColor(kmlColorToStdHex(substring));
			 * style.setStrokeWidth(1); substring = s.getPolyStyle().getColor();
			 * double d = kmlColorToOpacity(substring); String color =
			 * kmlColorToStdHex(substring); style.setFillColor(color);
			 * style.setFillOpacity(d);
			 * 
			 * } getMap().setStyleMap(styleMap);
			 */
		}

		kml = Kml.unmarshal(new File("DMA.kml"), false);

		feature = kml.getFeature();
		if (feature instanceof Document) {
			Document doc = (Document) feature;
			List<Feature> features = doc.getFeature();
			for (Feature f : features) {
				if (f instanceof Folder) {
					Folder folder = (Folder) f;
					List<Feature> features_folder = folder.getFeature();
					for (Feature f_folder : features_folder) {
						if (f_folder instanceof Placemark) {
							Placemark pm = (Placemark) f_folder;
							if (collection != null) {
								BasicDBObject dbdoc = new BasicDBObject();
								dbdoc.put("name", pm.getName());
								dbdoc.put("level", "DMA");
								dbdoc.put("description", pm.getDescription());
								dbdoc.put("id", pm.getId());
								Geometry p = pm.getGeometry();
								String jsonString = gson
										.toJson(new GeometryPOJO()
												.fromJakGeometry(p));
								dbdoc.put("geometry", jsonString);
								collection.insert(dbdoc);
							}
						}
					}
				} else if (f instanceof Placemark) {
					Placemark pm = (Placemark) f;
					if (collection != null) {
						BasicDBObject dbdoc = new BasicDBObject();
						dbdoc.put("name", pm.getName());
						dbdoc.put("level", "DMA");
						dbdoc.put("description", pm.getDescription());
						dbdoc.put("id", pm.getId());
						Geometry p = pm.getGeometry();
						String jsonString = gson.toJson(new GeometryPOJO()
								.fromJakGeometry(p));
						dbdoc.put("geometry", jsonString);
						collection.insert(dbdoc);
					}
				}
			}
		}

		kml = Kml.unmarshal(new File("COUNTY.kml"), false);

		feature = kml.getFeature();
		if (feature instanceof Document) {
			Document doc = (Document) feature;
			List<Feature> features = doc.getFeature();
			for (Feature f : features) {
				if (f instanceof Folder) {
					Folder folder = (Folder) f;
					List<Feature> features_folder = folder.getFeature();
					for (Feature f_folder : features_folder) {
						if (f_folder instanceof Placemark) {
							Placemark pm = (Placemark) f_folder;
							if (collection != null) {
								BasicDBObject dbdoc = new BasicDBObject();
								dbdoc.put("name", pm.getName());
								dbdoc.put("level", "COUNTY");
								dbdoc.put("description", pm.getDescription());
								dbdoc.put("id", pm.getId());
								Geometry p = pm.getGeometry();
								String jsonString = gson
										.toJson(new GeometryPOJO()
												.fromJakGeometry(p));
								dbdoc.put("geometry", jsonString);
								collection.insert(dbdoc);
							}
						}
					}
				} else if (f instanceof Placemark) {
					Placemark pm = (Placemark) f;
					if (collection != null) {
						BasicDBObject dbdoc = new BasicDBObject();
						dbdoc.put("name", pm.getName());
						dbdoc.put("level", "COUNTY");
						dbdoc.put("description", pm.getDescription());
						dbdoc.put("id", pm.getId());
						Geometry p = pm.getGeometry();
						String jsonString = gson.toJson(new GeometryPOJO()
								.fromJakGeometry(p));
						dbdoc.put("geometry", jsonString);
						collection.insert(dbdoc);
					}
				}
			}
		}

	}

	public void loadGeometryFromMySQL() {
		DataSource ds = DataSourceInstance.dataSource();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		Gson gson = new Gson();

		try {
			if (ds == null || (conn = ds.getConnection()) == null) {
				logger.error("cannot get db datasource or connection, cannot load KML from DB");
				return;
			}

			if (locationKmls == null)
				locationKmls = new HashMap<Long, Placemark>();

			stmt = conn
					.prepareStatement("select aa.areaname, aa.description, aa.locationid, aa.geometryjson from "
							+ dbInstance
							+ ".TBLLOCATIONAREAS aa left join "
							+ dbInstance
							+ ".TBLLOCATIONS bb on aa.locationid = bb.locationid "
							+ " where bb.locationlevel=?");

			stmt.setString(1, "STATE");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Placemark pm = new Placemark();

				pm.setName(rs.getString(1));
				pm.setDescription(rs.getString(2));
				pm.setId(rs.getString(3));
				String jsonString = rs.getString(4);
				GeometryPOJO pojo = gson.fromJson(jsonString,
						GeometryPOJO.class);
				Geometry p = pojo.toJakGeometry();
				pm.setGeometry(p);

				locationKmls.put(Long.parseLong(pm.getId()), pm);
			}

			rs.close();
			rs = null;

			stmt.setString(1, "DMA");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Placemark pm = new Placemark();

				pm.setName(rs.getString(1));
				pm.setDescription(rs.getString(2));
				pm.setId(rs.getString(3));
				String jsonString = rs.getString(4);
				GeometryPOJO pojo = gson.fromJson(jsonString,
						GeometryPOJO.class);
				Geometry p = pojo.toJakGeometry();
				pm.setGeometry(p);

				locationKmls.put(Long.parseLong(pm.getId()), pm);
			}
			rs.close();
		} catch (SQLException e) {
			logger.error("Expcetion while load geometries from DB: "
					+ e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				logger.error("couldnot close java.sql object: "
						+ e.getMessage());
			}
		}

	}

	public void loadGeometryIntoMySQL(DataSource ds) {
		Kml kml;
		Feature feature;
		Connection conn = null;
		PreparedStatement stmt = null;
		String sqlString = null;

		try {
			conn = ds.getConnection();
			sqlString = "insert into "
					+ dbInstance
					+ ".TBLLOCATIONAREAS (locationid, areaname, description, geometryjson, nelsonid)"
					+ " select locationid, ?, ?, ?, ? from " + dbInstance
					+ ".TBLLOCATIONS where locationname=? and locationlevel=?";
			stmt = conn.prepareStatement(sqlString);

			Gson gson = new Gson();

			kml = Kml.unmarshal(new File("STATE.kml"), false);

			feature = kml.getFeature();
			if (feature instanceof Document) {
				Document doc = (Document) feature;
				List<Feature> features = doc.getFeature();
				for (Feature f : features) {
					if (f instanceof Folder) {
						Folder folder = (Folder) f;
						List<Feature> features_folder = folder.getFeature();
						for (Feature f_folder : features_folder) {
							if (f_folder instanceof Placemark) {
								Placemark pm = (Placemark) f_folder;
								stmt.setString(1, pm.getName());
								stmt.setString(2, pm.getDescription());
								Geometry p = pm.getGeometry();
								String jsonString = gson
										.toJson(new GeometryPOJO()
												.fromJakGeometry(p));
								stmt.setString(3, jsonString);
								stmt.setString(4, pm.getId());
								stmt.setString(5, pm.getName().toUpperCase());
								stmt.setString(6, "STATE");

								stmt.executeUpdate();

							}
						}
					} else if (f instanceof Placemark) {
						Placemark pm = (Placemark) f;
						stmt.setString(1, pm.getName());
						stmt.setString(2, pm.getDescription());
						Geometry p = pm.getGeometry();
						String jsonString = gson.toJson(new GeometryPOJO()
								.fromJakGeometry(p));
						stmt.setString(3, jsonString);
						stmt.setString(4, pm.getId());
						stmt.setString(5, pm.getName().toUpperCase());
						stmt.setString(6, "STATE");

						stmt.executeUpdate();
					}
				}

				/*
				 * List<StyleSelector> styleSelectors = doc.getStyleSelector();
				 * 
				 * styleMap = new StyleMap(); for (StyleSelector styleSelector2
				 * : styleSelectors) { de.micromata.opengis.kml.v_2_2_0.Style s
				 * = (de.micromata.opengis.kml.v_2_2_0.Style) styleSelector2;
				 * styles.addItem("#" + styleSelector2.getId());
				 * 
				 * Style style = new Style();
				 * styleMap.setStyle(styleSelector2.getId(), style);
				 * 
				 * String substring = s.getLineStyle().getColor();
				 * style.setStrokeColor(kmlColorToStdHex(substring));
				 * style.setStrokeWidth(1); substring =
				 * s.getPolyStyle().getColor(); double d =
				 * kmlColorToOpacity(substring); String color =
				 * kmlColorToStdHex(substring); style.setFillColor(color);
				 * style.setFillOpacity(d);
				 * 
				 * } getMap().setStyleMap(styleMap);
				 */
			}

			kml = Kml.unmarshal(new File("DMA.kml"), false);

			feature = kml.getFeature();
			if (feature instanceof Document) {
				Document doc = (Document) feature;
				List<Feature> features = doc.getFeature();
				for (Feature f : features) {
					if (f instanceof Folder) {
						Folder folder = (Folder) f;
						List<Feature> features_folder = folder.getFeature();
						for (Feature f_folder : features_folder) {
							if (f_folder instanceof Placemark) {
								Placemark pm = (Placemark) f_folder;
								stmt.setString(1, pm.getName());
								stmt.setString(2, pm.getDescription());
								Geometry p = pm.getGeometry();
								String jsonString = gson
										.toJson(new GeometryPOJO()
												.fromJakGeometry(p));
								stmt.setString(3, jsonString);
								stmt.setString(4, pm.getId());
								stmt.setString(5, pm.getName().toUpperCase());
								stmt.setString(6, "DMA");

								stmt.executeUpdate();
							}
						}
					} else if (f instanceof Placemark) {
						Placemark pm = (Placemark) f;
						stmt.setString(1, pm.getName());
						stmt.setString(2, pm.getDescription());
						Geometry p = pm.getGeometry();
						String jsonString = gson.toJson(new GeometryPOJO()
								.fromJakGeometry(p));
						stmt.setString(3, jsonString);
						stmt.setString(4, pm.getId());
						stmt.setString(5, pm.getName().toUpperCase());
						stmt.setString(6, "DMA");

						stmt.executeUpdate();
					}
				}
			}

			kml = Kml.unmarshal(new File("USA.kml"), false);

			feature = kml.getFeature();
			if (feature instanceof Document) {
				Document doc = (Document) feature;
				List<Feature> features = doc.getFeature();
				for (Feature f : features) {
					if (f instanceof Folder) {
						Folder folder = (Folder) f;
						List<Feature> features_folder = folder.getFeature();
						for (Feature f_folder : features_folder) {
							if (f_folder instanceof Placemark) {
								Placemark pm = (Placemark) f_folder;
								stmt.setString(1, pm.getName());
								stmt.setString(2, pm.getDescription());
								Geometry p = pm.getGeometry();
								String jsonString = gson
										.toJson(new GeometryPOJO()
												.fromJakGeometry(p));
								stmt.setString(3, jsonString);
								stmt.setString(4, pm.getId());
								stmt.setString(5, "UNITED STATES");
								stmt.setString(6, "NATION");

								stmt.executeUpdate();
							}
						}
					} else if (f instanceof Placemark) {
						Placemark pm = (Placemark) f;
						stmt.setString(1, pm.getName());
						stmt.setString(2, pm.getDescription());
						Geometry p = pm.getGeometry();
						String jsonString = gson.toJson(new GeometryPOJO()
								.fromJakGeometry(p));
						stmt.setString(3, jsonString);
						stmt.setString(4, pm.getId());
						stmt.setString(5, pm.getName().toUpperCase());
						stmt.setString(6, "DMA");

						stmt.executeUpdate();
					}
				}
			}
			
			stmt.close();
			stmt = null;
			conn.close();
			conn = null;

			sqlString = "insert into "
					+ dbInstance
					+ ".TBLLOCATIONAREAS (locationid, areaname, description, geometryjson, nelsonid)"
					+ " select distinct aa.locationid, ?, ?, ?, ? from "
					+ dbInstance
					+ ".TBLLOCATIONS aa, "
					+ dbInstance
					+ ".TBLLOCATIONS bb, "
					+ dbInstance
					+ ".TBLCOUNTY_NELSON cc "
					+ "where aa.locationname=? and aa.locationlevel='COUNTY' and aa.locationparentid = bb.locationid "
					+ "		and bb.name2 = cc.state and cc.nelsonid = ?";

			kml = Kml.unmarshal(new File("COUNTY.kml"), false);

			conn = ds.getConnection();
			stmt = conn.prepareStatement(sqlString);

			feature = kml.getFeature();
			if (feature instanceof Document) {
				Document doc = (Document) feature;
				List<Feature> features = doc.getFeature();
				for (Feature f : features) {
					if (f instanceof Folder) {
						Folder folder = (Folder) f;
						List<Feature> features_folder = folder.getFeature();
						for (Feature f_folder : features_folder) {
							if (f_folder instanceof Placemark) {
								Placemark pm = (Placemark) f_folder;
								stmt.setString(1, pm.getName());
								stmt.setString(2, pm.getDescription());
								Geometry p = pm.getGeometry();
								String jsonString = gson
										.toJson(new GeometryPOJO()
												.fromJakGeometry(p));
								stmt.setString(3, jsonString);
								stmt.setString(4, pm.getId());
								stmt.setString(5, pm.getName().toUpperCase());
								stmt.setLong(6, Long.parseLong(pm.getId()));

								stmt.executeUpdate();
							}
						}
					} else if (f instanceof Placemark) {
						Placemark pm = (Placemark) f;
						stmt.setString(1, pm.getName());
						stmt.setString(2, pm.getDescription());
						Geometry p = pm.getGeometry();
						String jsonString = gson.toJson(new GeometryPOJO()
								.fromJakGeometry(p));
						stmt.setString(3, jsonString);
						stmt.setString(4, pm.getId());
						stmt.setString(5, pm.getName().toUpperCase());
						stmt.setLong(6, Long.parseLong(pm.getId()));

						stmt.executeUpdate();
					}
				}
			}
			stmt.close();
			stmt = null;
		} catch (SQLException e) {
			logger.error("Expcetion while load geometries into DB: "
					+ e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				logger.error("couldnot close java.sql object: "
						+ e.getMessage());
			}
		}

	}

	public Placemark getGeometry(long locationId) {
		Placemark pm = locationKmls.get(locationId);
		if (pm == null)
			pm = getGeometryFromMySQL(locationId);

		return pm;
	}

	public Placemark getGeometryFromMongo(long locationId) {
		Mongo m = MongoInstance.mongo();
		DB mongodb = null;
		DBCollection collection = null;
		Placemark pm = null;
		Gson gson = new Gson();

		if (m == null) {
			logger.error("Mongo DB server address unknown, cannot get KML from DB");
			return null;
		}

		mongodb = m.getDB("Location");
		if (mongodb == null) {
			logger.error("Could not get location mongo DB");
			return null;
		}

		collection = mongodb.getCollection("Placemarks");

		if (collection != null) {
			BasicDBObject query = new BasicDBObject();
			query.put("id", locationId);

			DBCursor cursor = collection.find(query);

			if (cursor.hasNext()) {
				DBObject o = cursor.next();
				pm = new Placemark();

				pm.setName((String) o.get("name"));
				pm.setDescription((String) o.get("description"));
				pm.setId((String) o.get("id"));
				String jsonString = (String) o.get("geometry");
				GeometryPOJO pojo = gson.fromJson(jsonString,
						GeometryPOJO.class);
				Geometry p = pojo.toJakGeometry();
				pm.setGeometry(p);
			}

			cursor.close();
		}

		return pm;
	}

	public Placemark getGeometryFromMySQL(long locationId) {
		DataSource ds = DataSourceInstance.dataSource();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		Placemark pm = null;
		Gson gson = new Gson();
		try {

			if (ds == null || (conn = ds.getConnection()) == null) {
				logger.error("cannot get db datasource or connection, cannot load KML from DB");
				return null;
			}

			stmt = conn
					.prepareStatement("select aa.areaname, aa.description, aa.locationid, aa.geometryjson from "
							+ dbInstance
							+ ".TBLLOCATIONAREAS aa "
							+ " where aa.locationid=?");

			stmt.setLong(1, locationId);

			rs = stmt.executeQuery();
			if (rs.next()) {
				pm = new Placemark();

				pm.setName(rs.getString(1));
				pm.setDescription(rs.getString(2));
				pm.setId(rs.getString(3));
				String jsonString = rs.getString(4);
				GeometryPOJO pojo = gson.fromJson(jsonString,
						GeometryPOJO.class);
				Geometry p = pojo.toJakGeometry();
				pm.setGeometry(p);
				locationKmls.put(Long.parseLong(pm.getId()), pm);
			}

			rs.close();
			rs = null;
		} catch (SQLException e) {
			logger.error("Expcetion while load geometries into DB: "
					+ e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				logger.error("couldnot close java.sql object: "
						+ e.getMessage());
			}
		}
		return pm;
	}

	public static void main(String[] args) {
		LocationKmls kml = new LocationKmls();

		try {
			ComboPooledDataSource ds = new ComboPooledDataSource();

			if (ds == null)
				return;
			ds.setDriverClass("com.mysql.jdbc.Driver");
			ds.setJdbcUrl("jdbc:mysql://172.16.0.212/mydb");
			ds.setUser("zapapps");
			ds.setPassword("zoove123");
			ds.setMaxStatements(0);
			ds.setMaxPoolSize(3);
			ds.setAcquireIncrement(1);
			ds.setMinPoolSize(1);

			kml.loadGeometryIntoMySQL(ds);
		} catch (PropertyVetoException e) {
			System.out.println(e.getMessage());
		}
	}

}
