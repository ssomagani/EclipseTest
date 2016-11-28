package data;

import java.util.UUID;

/**
 * Domain object for Principal.
 * @author jflowers@intralinks.com
 */
public class Principal {
	// id is a UUID
	private UUID id;
	// kind
	
	private String kind;
	
	// note is temp text to make a principal easier to find
	private String note;
	
	public Principal() {
		 this.id = UUID.randomUUID();
	}
	
	public Principal(String kind, String note) {
		 this.id = UUID.randomUUID();
		 this.kind = kind;
		 this.note = note;
	}
		
	public Principal(UUID id) {
		this.id = id;
	}
		
	// id
	public UUID getId() {
		return this.id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
		
	// kind
	public String getKind() {
		return this.kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
		
	// note
	public String getNote() {
		return this.note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(getClass().getSimpleName() + "{");
		builder.append("id:" + getId());
		builder.append(",kind:" + getKind());
		builder.append(",note:" + getNote());
		//...
		
		return builder.toString();
	}
}
		
