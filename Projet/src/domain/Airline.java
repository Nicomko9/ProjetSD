package domain;

public class Airline {
	private String iata;
	private String country;
	private String name;
	
	public Airline(String iata, String country) {
		this.iata = iata;
		this.country = country;
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
		Airline other = (Airline) obj;
		if (iata == null) {
			if (other.iata != null)
				return false;
		} else if (!iata.equals(other.iata))
			return false;
		return true;
	}	
	
}
