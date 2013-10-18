package com.zoove.enterprise.registration.ui.map;

import java.util.Iterator;
import java.util.List;

import org.vaadin.vol.Area;
import org.vaadin.vol.BingMapLayer;
import org.vaadin.vol.Bounds;
import org.vaadin.vol.GoogleHybridMapLayer;
import org.vaadin.vol.Marker;
import org.vaadin.vol.MarkerLayer;
import org.vaadin.vol.OpenLayersMap;
import org.vaadin.vol.OpenStreetMapLayer;
import org.vaadin.vol.Point;
import org.vaadin.vol.Popup;
import org.vaadin.vol.Popup.CloseEvent;
import org.vaadin.vol.Popup.CloseListener;
import org.vaadin.vol.Popup.PopupStyle;
import org.vaadin.vol.StyleMap;
import org.vaadin.vol.Vector;
import org.vaadin.vol.VectorLayer;
import org.vaadin.vol.VectorLayer.DrawingMode;
import org.vaadin.vol.VectorLayer.SelectionMode;
import org.vaadin.vol.VectorLayer.VectorDrawnEvent;
import org.vaadin.vol.VectorLayer.VectorDrawnListener;
import org.vaadin.vol.VectorLayer.VectorModifiedEvent;
import org.vaadin.vol.VectorLayer.VectorModifiedListener;
import org.vaadin.vol.VectorLayer.VectorSelectedEvent;
import org.vaadin.vol.VectorLayer.VectorSelectedListener;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;

public class LocationMap extends OpenLayersMap implements VectorDrawnListener,
        VectorSelectedListener {
    static final double METERS_ABOVE_GROUND = 30;
    private VectorLayer vectorLayer;
    private MarkerLayer markerLayer;
    
    private Bounds displayBounds = new Bounds();

    private FeatureDrawnCallback drawingListener;

	class MarkerClickListener implements ClickListener {
		OpenLayersMap m;
		Marker marker;
		String prompt;
		
    	public MarkerClickListener (OpenLayersMap m, Marker marker, String prompt) {
    		this.m = m;
    		this.marker = marker;
    		this.prompt = prompt;
    	}
    	
		public void click(ClickEvent event) {
            final Popup popup = new Popup(marker.getLon(), marker.getLat(),
                    prompt);
            popup.setAnchor(marker);
            popup.setPopupStyle(PopupStyle.FRAMED);
            popup.addListener(new CloseListener() {
                public void onClose(CloseEvent event) {
                    m.removeComponent(popup);
                }
            });
            m.addPopup(popup);
		}	
	}
	
    public LocationMap() {
    	setJsMapOptions("{projection: new OpenLayers.Projection(\"EPSG:900913\")," +
    			"maxResolution: 156543.0339,units: \"m\"," +
    			"maxExtent: new OpenLayers.Bounds(-20037508, -20037508, 20037508, 20037508.34)" +
    			"}");
        setSizeFull();

        addBaseLayers();
        
        setDefaultCenterAndZoom();
        
        markerLayer = new MarkerLayer ();
        addLayer(markerLayer);
        
        vectorLayer = new VectorLayer();
        vectorLayer.addListener((VectorDrawnListener) this);
        addLayer(vectorLayer);

        vectorLayer.addListener((VectorSelectedListener) this);
        vectorLayer.setSelectionMode(SelectionMode.SIMPLE);
        
    }

    protected void setDefaultCenterAndZoom() {
        setCenter(-98.907997, 39.450001);
        setZoom(5);
    }

    protected void addBaseLayers() {
    	
    	//OpenStreetMapLayer openStreets = new OpenStreetMapLayer();
    	//addLayer(openStreets);
    	
        //GoogleStreetMapLayer googleStreets = new GoogleStreetMapLayer();
    	//addLayer(googleStreet);

    	BingMapLayer bingmap = new BingMapLayer();
    	bingmap.setApikey("AiWRSPyZToJToxEsY9dhNCCSxZ7QBYXFp9ZeEekM6bWaNLprHnLKzWg5MFY_z88e");
        addLayer(bingmap);
        
        //GoogleHybridMapLayer googleHybrid = new GoogleHybridMapLayer();
    	//addLayer(googleHybrid);
    }
    
    public VectorLayer getVectorLayer() {
		return vectorLayer;
	}

	public MarkerLayer getMarkerLayer() {
		return markerLayer;
	}

	public void drawFeature(final FeatureDrawnCallback listener) {
        if (drawingListener != null) {
            cancelDrawing();
        }
        drawingListener = listener;

        vectorLayer.removeAllComponents();
        vectorLayer.setDrawindMode(DrawingMode.AREA);

    }

    private void cancelDrawing() {
        drawingListener = null;
    }

    public void vectorDrawn(VectorDrawnEvent event) {
        drawingListener.drawingDone(event.getVector());
    }

    // op=0:	hide feature
    // op=1:	show feature
    public void showFeature(Feature value, int op) {
        if (value instanceof Folder) {
            List<Feature> features = ((Folder) value).getFeature();
            for (Feature feature : features) {
                showFeature(feature, op);
            }
        } else if (value instanceof Document) {
            List<Feature> features = ((Document) value).getFeature();
            for (Feature feature : features) {
                showFeature(feature, op);
            }
        } else if (value instanceof Placemark) {
            Placemark pm = (Placemark) value;
            Geometry geometry = pm.getGeometry();
            showFeature(geometry, pm, op);
        } else {
            System.err.println("Unhandled feature type.");
        }

    }

    public Vector getVectorFor(Geometry g) {
        Iterator<Component> componentIterator = vectorLayer
                .getComponentIterator();
        while (componentIterator.hasNext()) {
            AbstractComponent next = (AbstractComponent) componentIterator
                    .next();
            if (next.getData() == g) {
                return (Vector) next;
            }
        }
        return null;
    }
    
    public Marker getMarkerFor(Geometry g) {
        Iterator<Component> componentIterator = markerLayer
                .getComponentIterator();
        while (componentIterator.hasNext()) {
            AbstractComponent next = (AbstractComponent) componentIterator
                    .next();
            if (next.getData() == g) {
                return (Marker) next;
            }
        }
        return null;
    }

    public void showPolygon(Polygon p, Placemark pm, int op) {
        Vector v = getVectorFor(p);
        if (v!=null && op==1 
        		|| v==null && op==0)
        	return;
        
        if (op==1) {
	        List<Coordinate> coordinates = (p).getOuterBoundaryIs().getLinearRing()
	                .getCoordinates();
	        Point[] points = new Point[coordinates.size()];
	        int i = 0;
	        for (Coordinate coordinate : coordinates) {
	            Point point = new Point(coordinate.getLongitude(),
	                    coordinate.getLatitude());
	            points[i++] = point;
	        }
	
	        if (points.length > 0) {
	            Area area = new Area();
	            area.setPoints(points);
	            area.setData(p);
	            displayBounds.extend(points);
	            String styleUrl = pm.getStyleUrl();
	            if(styleUrl!=null && styleUrl.startsWith("#")) {
	            	styleUrl = styleUrl.substring(1);
	            }
	            area.setRenderIntent(styleUrl);
	            vectorLayer.addComponent(area);
	        }
        }
        else if (op==0) {
        	vectorLayer.removeComponent(v);
        }
    }

    public void showPoint(de.micromata.opengis.kml.v_2_2_0.Point p, Placemark pm, int op) {
        Marker v = getMarkerFor(p);
        if (v!=null && op==1 
        		|| v==null && op==0)
        	return;
        
        List<Coordinate> coordinates = p.getCoordinates();
        if (coordinates.size()==0 || coordinates.get(0)==null) 
        	return;
        
        if (op==1) {
	        Coordinate co = coordinates.get(0);
	        final Marker marker = new Marker (co.getLongitude(), co.getLatitude());
	        final String markerName = pm.getName();
	        marker.setIcon(new ThemeResource("../zoove/zoovelogo.png"), 10, 12);
	
	        marker.setData(p);
	        marker.addClickListener(new MarkerClickListener(this,marker,pm.getName()));
	        
	        displayBounds.extend(new Point(co.getLongitude(),co.getLatitude()));
	        markerLayer.addMarker(marker);
        }
        else if (op==0) {
        	markerLayer.removeComponent(v);
        }
    }
    
    public void showFeature(Geometry p, Placemark pm, int op) {
        if (p instanceof Polygon) {
            showPolygon((Polygon) p, pm, op);
        }
        else if (p instanceof de.micromata.opengis.kml.v_2_2_0.Point) {
        	showPoint((de.micromata.opengis.kml.v_2_2_0.Point)p, pm, op);
        }
        else if (p instanceof MultiGeometry) {
        	List<Geometry> geometries = ((MultiGeometry) p).getGeometry();
        	for (Iterator<Geometry> it=geometries.iterator(); it.hasNext();) {
        		Geometry g = it.next();
        		showFeature(g, pm, op);
        	}
        }

    }

    public void clear() {
        vectorLayer.removeAllComponents();
        displayBounds = new Bounds();
        vectorLayer.requestRepaint();
    }

    public void vectorSelected(VectorSelectedEvent event) {
        Vector vector = event.getVector();
        System.out.println ("vector selected"+vector.getCaption());
    }

    public void showAllVectors() {
        Iterator<Component> componentIterator = vectorLayer
                .getComponentIterator();
        Bounds bounds = new Bounds();
        boolean emptyBounds = true;
        while (componentIterator.hasNext()) {
            Vector vector = (Vector) componentIterator.next();
            bounds.extend(vector.getPoints());
            if (emptyBounds) {
                if (vector.getPoints().length < 2) {
                    bounds.extend(vector.getPoints());
                }
                emptyBounds = false;
            }
        }
        if (!emptyBounds) {
            zoomToExtent(bounds);
        }

    }

    public void setStyleMap(StyleMap styleMap) {
        vectorLayer.setStyleMap(styleMap);
    }

	public Bounds getDisplayBounds() {
		return displayBounds;
	}

	public void setDisplayBounds(Bounds displayBounds) {
		this.displayBounds = displayBounds;
	}

}
