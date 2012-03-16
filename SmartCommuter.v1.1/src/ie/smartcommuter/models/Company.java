package ie.smartcommuter.models;

public class Company {
	
	private String name;
	private ContactDetails details;
	private String mode;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ContactDetails getDetails() {
		return details;
	}
	public void setDetails(ContactDetails details) {
		this.details = details;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
}