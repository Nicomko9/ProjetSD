package domain;

import java.util.HashSet;
import java.util.Set;

public class Airport {
	
	private String iata;
	private String name;
	private String city;
	private String country;
	
	private double latitude;
	private double longitude;
	
	private Set<String> routes;

	public Airport(String iata, String name, String city, String country, double latitude, double longitude) {
		this.iata = iata;
		this.name = name;
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.routes = new HashSet<String>();
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Set<String> getRoutes() {
		return routes;
	}

	public void setRoutes(Set<String> routes) {
		this.routes = routes;
	}
	
}
