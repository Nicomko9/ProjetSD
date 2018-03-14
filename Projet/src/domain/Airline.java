package domain;

public class Airline {
	private String iata;
	private String country;
	private String name;
	
	public Airline(String iata, String country) {
		
		this.iata = iata;
		this.country = country;
		this.name = name;
	}

	
	public String getIata() {
		return iata;
	}
	public String getCountry() {
		return country;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}
