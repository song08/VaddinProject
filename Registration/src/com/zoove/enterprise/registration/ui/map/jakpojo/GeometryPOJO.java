package com.zoove.enterprise.registration.ui.map.jakpojo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Model;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.Polygon;

public class GeometryPOJO {
	String type;
	Point point;
	Polygon polygon;
	LineString lineString;
	LinearRing linearRing;
	Model model;
	
	ArrayList<GeometryPOJO> geometries;

	public GeometryPOJO() {
		super();
		
		this.type = null;
		this.point = null;
		this.polygon = null;
		this.lineString = null;
		this.linearRing = null;
		this.model = null;
		this.geometries = null;
	}

	public GeometryPOJO(String type, Point point, Polygon polygon,
			LineString lineString, LinearRing linearRing, Model model,
			ArrayList<GeometryPOJO> multiGeometry) {
		super();
		this.type = type;
		this.point = point;
		this.polygon = polygon;
		this.lineString = lineString;
		this.linearRing = linearRing;
		this.model = model;
		this.geometries = multiGeometry;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}

	public LineString getLineString() {
		return lineString;
	}

	public void setLineString(LineString lineString) {
		this.lineString = lineString;
	}

	public LinearRing getLinearRing() {
		return linearRing;
	}

	public void setLinearRing(LinearRing linearRing) {
		this.linearRing = linearRing;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public ArrayList<GeometryPOJO> getMultiGeometry() {
		return geometries;
	}

	public void setMultiGeometry(ArrayList<GeometryPOJO> geometries) {
		this.geometries = geometries;
	}
	
	public GeometryPOJO fromJakGeometry (Geometry p) {
		if (p==null) return this;
		
		if (p instanceof Point) {
			this.type="Point";
			this.point=(Point)p;
		}
		else if (p instanceof Polygon) {
			this.type="Polygon";
			this.polygon=(Polygon)p;
		}
		else if (p instanceof LineString) {
			this.type="LineString";
			this.lineString=(LineString)p;
		}
		else if (p instanceof LinearRing) {
			this.type="LinearRing";
			this.linearRing=(LinearRing)p;
		}
		else if (p instanceof Model) {
			this.type="Model";
			this.model=(Model)p;
		}
		else if (p instanceof MultiGeometry) {
			this.type="MultiGeometry";
			List<Geometry> glist = ((MultiGeometry)p).getGeometry();
			for (Iterator<Geometry> it = glist.iterator();it.hasNext();) {
				Geometry g = it.next();
				GeometryPOJO pojo = new GeometryPOJO();
				pojo.fromJakGeometry(g);
				if (geometries==null)
					geometries = new ArrayList<GeometryPOJO>();
				geometries.add(pojo);
			}
		}
		
		return this;
	}
	
	public Geometry toJakGeometry () {
		if (this.type==null) return null;
		
		if (this.type.equals("Point"))
			return this.point;
		else if (this.type.equals("Polygon"))
			return this.polygon;
		else if (this.type.equals("LineString")) 
			return this.lineString;
		else if (this.type.equals("LinearRing"))
			return this.linearRing;
		else if (this.type.equals("Model"))
			return this.model;
		else if (this.type.equals("MultiGeometry")) {
			MultiGeometry g = new MultiGeometry();
			if (this.geometries==null)
				return null;
			for (Iterator<GeometryPOJO> it = this.geometries.iterator(); it.hasNext();) {
				GeometryPOJO pojo = it.next();
				Geometry p = pojo.toJakGeometry();
				if (p!=null) g.addToGeometry(p);
			}
			return g;
		}
		else
			return null;
	}
}
