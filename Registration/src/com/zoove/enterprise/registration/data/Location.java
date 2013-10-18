package com.zoove.enterprise.registration.data;

import java.util.Vector;

public class Location {
	private long locationId;
	private String locationName, locationLevel;
	private long locationParentId;
	
	public Location(long locationId, String locationName, String locationLevel,
			long locationParentId) {
		super();
		this.locationId = locationId;
		this.locationName = locationName;
		this.locationLevel = locationLevel;
		this.locationParentId = locationParentId;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationLevel() {
		return locationLevel;
	}

	public void setLocationLevel(String locationLevel) {
		this.locationLevel = locationLevel;
	}

	public long getLocationParentId() {
		return locationParentId;
	}

	public void setLocationParentId(long locationParentId) {
		this.locationParentId = locationParentId;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.locationId == ((Location)obj).getLocationId();
	}
	
}
