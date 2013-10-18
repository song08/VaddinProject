package com.zoove.enterprise.registration.ui;

import org.apache.log4j.Logger;

import com.vaadin.data.Container;
import com.vaadin.ui.TreeTable;

public class LocationTreeTable extends TreeTable {
	Logger log = Logger.getLogger(LocationTreeTable.class);

	public LocationTreeTable(String caption, Container dataSource) {
		super(caption, dataSource);
	}
}
