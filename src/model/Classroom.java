package model;

import java.io.Serializable;

public class Classroom implements Serializable{

	private String roomNumber;
	private BuildingName buildingName;
	
	private static int roomCounter = 1;

	public Classroom(BuildingName buildingName) {
		super();
		this.roomNumber = String.format("%03d", roomCounter++);
		this.buildingName = buildingName;
	}
	
	public Classroom(BuildingName buildingName, String roomNumber) {
		this.roomNumber = roomNumber;
		this.buildingName = buildingName;
		
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public BuildingName getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(BuildingName buildingName) {
		this.buildingName = buildingName;
	}

}
