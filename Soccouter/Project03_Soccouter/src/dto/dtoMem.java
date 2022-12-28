package dto;

public class dtoMem {

	private String id;
	private String pw;
	private String type;

	public dtoMem() {}
	
	public dtoMem(String id, String pw, String type) {
		this.id = id;
		this.pw = pw;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
