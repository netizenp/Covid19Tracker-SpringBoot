package com.covid19.models;

public class DataEntity {
	private String state;
	private String country;
	private int latestTotalCases;
	private int lastDayIncrease;
	public int getLastDayIncrease() {
		return lastDayIncrease;
	}
	public void setLastDayIncrease(int lastDayIncrease) {
		this.lastDayIncrease = lastDayIncrease;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
}
