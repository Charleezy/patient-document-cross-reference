package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IdentificationDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long identificationDocumentID;

	private String issuer;
	private String id;
	
	public IdentificationDocument(String issuer, String id){
		this.issuer = issuer;
		this.id = id;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}
}
