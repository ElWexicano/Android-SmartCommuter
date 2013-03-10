package ie.smartcommuter.models;

/**
 * This is a class is to create instances of Contact Details for Companies.
 * 
 * @author Shane Doyle
 */
public class ContactDetails {

	private String mEmail;
	private String mTelephone;
	private String mWebsite;
	private String mFacebook;
	private String mTwitter;

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		this.mEmail = email;
	}

	public String getTelephone() {
		return mTelephone;
	}

	public void setTelephone(String telephone) {
		this.mTelephone = telephone;
	}

	public String getWebsite() {
		return mWebsite;
	}

	public void setWebsite(String website) {
		this.mWebsite = website;
	}

	public String getFacebook() {
		return mFacebook;
	}

	public void setFacebook(String facebook) {
		this.mFacebook = facebook;
	}

	public String getTwitter() {
		return mTwitter;
	}

	public void setTwitter(String twitter) {
		this.mTwitter = twitter;
	}

}
