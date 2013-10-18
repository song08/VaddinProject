package com.zoove.enterprise.registration.ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.vaadin.vol.Bounds;
import org.vaadin.vol.Point;


import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractSelect.MultiSelectMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Tree.ExpandEvent;
import com.vaadin.ui.Tree.ExpandListener;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.zoove.enterprise.registration.data.Location;
import com.zoove.enterprise.registration.data.Locations;
import com.zoove.enterprise.registration.ui.map.LocationMap;
import com.zoove.enterprise.registration.ui.map.nelson.LocationKmls;

import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;

public class LocationMapView extends CssLayout implements ClickListener,
		Property.ValueChangeListener, ExpandListener {
	private String dbInstance = new String ("mydb");
	private Kml kml;
	private Locations locations = new Locations();

	CssLayout mainArea = new CssLayout();

	private LocationTreeTable locationTreeTable = null;

	private HorizontalLayout toolbar = new HorizontalLayout();

	private Button refresh = new NativeButton("", this);
	private Button print = new NativeButton("Print Map",this);
	private CheckBox zoomToExtent = new CheckBox ("Zoom?");

	private LocationMap map;
	private LocationKmls locationKmls;
	
	private Vector<Location> selectedLocations;

	public LocationMapView() {
		setSizeFull();
		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		addComponent(horizontalSplitPanel);
		horizontalSplitPanel.setSplitPosition(320, UNITS_PIXELS);
		VerticalLayout vl = new VerticalLayout();
		populateToolbar();
		vl.addComponent(toolbar);
		vl.setSizeFull();
		VerticalSplitPanel verticalSplitPanel = new VerticalSplitPanel();
		
		
		locationTreeTable = new LocationTreeTable ("Location Tree", locations.getLocations());
		verticalSplitPanel.addComponent(locationTreeTable);
		verticalSplitPanel.addComponent(mainArea);

		vl.addComponent(verticalSplitPanel);
		vl.setExpandRatio(verticalSplitPanel, 1);

		mainArea.setSizeFull();
		mainArea.setMargin(true);

		horizontalSplitPanel.addComponent(vl);
		
		//CustomLayout cl = new CustomLayout("contenttoprint");
		try {
		CustomLayout cl = new CustomLayout(
				new ByteArrayInputStream(
						("<div location=\"contenttoprint\" id=\"contenttoprint\" style=\"height: 100%; width: 100%\"></div>"+
						"<iframe id=\"ifmcontentstoprint\" style=\"height: 0px; width: 0px; position: absolute\"></iframe>")
								.getBytes()));
		cl.setSizeFull();
		cl.addComponent(getMap(), "contenttoprint");
		horizontalSplitPanel.addComponent(cl);
		}
		catch (IOException e) {
			
		}

		locationTreeTable.setSelectable(true);
		locationTreeTable.setMultiSelect(true);
		locationTreeTable.setMultiSelectMode(MultiSelectMode.SIMPLE);
		locationTreeTable.setVisibleColumns(new Object[] {"locationName"});
		locationTreeTable.setSizeFull();
		locationTreeTable.setImmediate(true);
		
		locationTreeTable.addListener((Property.ValueChangeListener)this);
		locationTreeTable.addListener((ExpandListener)this);

		init();
	}

	private void init () {
		locations.loadChildren(dbInstance, 11);
		locationKmls = LocationKmls.getInstance();
		selectedLocations = new Vector<Location> ();
	}

	public LocationMap getMap() {
		if (map == null) {
			map = new LocationMap();
		}
		return map;
	}

	private void populateToolbar() {
		toolbar.setWidth("100%");
		
		ComboBox locationModeComboBox = new ComboBox();
		//for (int i=0; i<Locations.modes.length;i++)
		//	locationModeComboBox.addItem(Locations.modes[i]);
		locationModeComboBox.addItem(Locations.modes[0]);
		locationModeComboBox.setValue(Locations.modes[0]);
		locationModeComboBox.setImmediate(true);
		toolbar.addComponent(locationModeComboBox);
		locationModeComboBox.addListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				locations.setMode((String)event.getProperty().getValue());
				locations.getLocations().removeAllItems();
				map.getMarkerLayer().removeAllComponents();
				map.getVectorLayer().removeAllComponents();
				selectedLocations.clear();
				locationTreeTable.setValue(null);
				locations.loadChildren(dbInstance, 11);
			}
		});

//		toolbar.addComponent(refresh);

//		refresh.setIcon(new ThemeResource("../zoove/refresh.png"));
//		refresh.setStyleName(BaseTheme.BUTTON_LINK);
		
		zoomToExtent.addListener((ClickListener)this);
		zoomToExtent.setImmediate(true);
		
		toolbar.addComponent(zoomToExtent);
		toolbar.setComponentAlignment(zoomToExtent, Alignment.MIDDLE_RIGHT);
		
		print.setStyleName(BaseTheme.BUTTON_LINK);
		toolbar.addComponent(print);
		toolbar.setComponentAlignment(print, Alignment.MIDDLE_RIGHT);
	}

	public void buttonClick(ClickEvent event) {
		if (event.getButton() == refresh) {
			locations.getLocations().removeAllItems();
			locations.loadChildren(dbInstance, 11);
		}
		else if (event.getButton() == print) {
			print.getWindow().executeJavaScript(
					"var content = document.getElementById(\"contenttoprint\");\n"+
					"var pri = document.getElementById(\"ifmcontentstoprint\").contentWindow;\n"+
					"pri.document.open();\n"+
					"pri.document.write(content.innerHTML);\n"+
					"pri.document.close();\n"+
					"pri.focus();\n"+
					"pri.print();"
				);
		}
		else if (event.getButton()==zoomToExtent) {
			boolean b = zoomToExtent.booleanValue();
			if (b) map.showAllVectors();
		}
		else {
			System.err.println("Unhandled click");
		}
	}

	public void valueChange(ValueChangeEvent event) {
		if (event.getProperty() == locationTreeTable && locationTreeTable.getValue() != null) {
				Set<Long> values = (Set<Long>)locationTreeTable.getValue();
				if (values==null || values.size()==0) {
					System.out.println ("No selection");
					for (int i=0;i<selectedLocations.size();i++) {
						Location location = selectedLocations.get(i);
						Placemark pm = locationKmls.getGeometry(location.getLocationId());
						map.showFeature(pm, 0);
					}
					selectedLocations.clear();
				}
				else {
					Vector<Location> newLocations = new Vector<Location>(values.size());
//	                Bounds bounds = new Bounds();
					
					for (Iterator<Long> it = values.iterator(); it.hasNext();) {
						long locationId = it.next();
						Item item = locationTreeTable.getItem(locationId);
						String locationName = (String)item.getItemProperty("locationName").getValue();
						
						Location location = new Location ((Long)item.getItemProperty("locationId").getValue(), 
								(String)item.getItemProperty("locationName").getValue(), 
								(String)item.getItemProperty("locationLevel").getValue(),
								(Long)item.getItemProperty("locationParentId").getValue() );
						
						System.out.println ("Selected: "+locationId+"/"+locationName);

						newLocations.add(location);
						
						if (selectedLocations.contains(location)) {
							selectedLocations.remove(location);
							continue;
						}
						
						Placemark pm = locationKmls.getGeometry(location.getLocationId());
/*						if (location.getLocationLevel().compareToIgnoreCase("STATE")==0) {
							Polygon p = (Polygon)pm.getGeometry();
							Boundary ob = p.getOuterBoundaryIs();
							LinearRing r = ob.getLinearRing();
							List<Coordinate> cs = r.getCoordinates();
							for (Coordinate c : cs)
								bounds.extend(new Point(c.getLongitude(), c.getLatitude()));
						}
						else if (location.getLocationLevel().compareToIgnoreCase("DMA")==0
								|| location.getLocationLevel().compareToIgnoreCase("COUNTY")==0) {
							Long pid = (Long)locationTreeTable.getParent(location.getLocationId());
							Placemark ppm = null;
							if (pid!=null && (ppm=locationKmls.getGeometry(pid))!=null) {
								Polygon p = (Polygon)ppm.getGeometry();
								Boundary ob = p.getOuterBoundaryIs();
								LinearRing r = ob.getLinearRing();
								List<Coordinate> cs = r.getCoordinates();
								for (Coordinate c : cs)
									bounds.extend(new Point(c.getLongitude(), c.getLatitude()));
							}
						}
*/
						
						map.showFeature(pm, 1);
					}
					
					for (int i=0;i<selectedLocations.size();i++) {
						Location location = selectedLocations.get(i);
						Placemark pm = locationKmls.getGeometry(location.getLocationId());
						map.showFeature(pm, 0);
					}
					
					selectedLocations.clear();
					selectedLocations.addAll(newLocations);
					if (zoomToExtent.booleanValue()) map.showAllVectors();
				}
		}
		else 
			System.out.println("Value change fired");
	}

	
	public void nodeExpand(ExpandEvent event) {
		Long itemId = (Long)event.getItemId();
		
		if (locations.getLocations().getChildren(itemId)==null || locations.getLocations().getChildren(itemId).size()==0)
			locations.loadChildren(dbInstance, itemId);
	}

}
