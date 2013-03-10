package ie.smartcommuter.models;

/**
 * This is a class is to create instances of Public Transport Companies.
 * 
 * @author Shane Doyle
 */
public class Company {

	private String mName;
	private ContactDetails mDetails;
	private String mMode;

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public ContactDetails getDetails() {
		return mDetails;
	}

	public void setDetails(ContactDetails details) {
		this.mDetails = details;
	}

	public String getMode() {
		return mMode;
	}

	public void setMode(String mode) {
		this.mMode = mode;
	}
}