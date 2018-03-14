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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iata == null) ? 0 : iata.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		if (iata == null) {
			if (other.iata != null)
				return false;
		} else if (!iata.equals(other.iata))
			return false;
		return true;
	}
	
}
