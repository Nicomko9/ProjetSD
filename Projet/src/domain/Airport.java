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
	
	private Set<Route> routes;

	public Airport(String iata, String name, String city, String country) {
		this.iata = iata;
		this.name = name;
		this.city = city;
		this.country = country;
	
		this.routes = new HashSet<Route>();
	}

	public String getIata() {
		return iata;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double lat) {
		 latitude = lat;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double lon) {
		longitude = lon;
	}

	public Set<Route> getRoutes() {
		return routes;
	}
	
	public boolean addRoute(Route route) {
		return this.routes.add(route);
	}
		
}
