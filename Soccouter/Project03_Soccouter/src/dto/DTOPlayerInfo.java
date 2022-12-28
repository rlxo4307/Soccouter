package dto;

public class DTOPlayerInfo {

	private String pheight;
	private String pnation;
	private String pnationimg;
	private String pbirth;
	private String pfoot;
	private String pposition;
	
	public DTOPlayerInfo() {}

	public DTOPlayerInfo(String pheight, String pnation, String pnationimg, String pbirth, String pfoot, String pposition) {
		this.pheight = pheight;
		this.pnation = pnation;
		this.pnationimg = pnationimg;
		this.pbirth = pbirth;
		this.pfoot = pfoot;
		this.pposition = pposition;
	}
	
	
	public String getPheight() {
		return pheight;
	}

	public void setPheight(String pheight) {
		this.pheight = pheight;
	}

	public String getPnation() {
		return pnation;
	}

	public void setPnation(String pnation) {
		this.pnation = pnation;
	}

	public String getPnationimg() {
		return pnationimg;
	}

	public void setPnationimg(String pnationimg) {
		this.pnationimg = pnationimg;
	}

	public String getPbirth() {
		return pbirth;
	}

	public void setPbirth(String pbirth) {
		this.pbirth = pbirth;
	}

	public String getPfoot() {
		return pfoot;
	}

	public void setPfoot(String pfoot) {
		this.pfoot = pfoot;
	}

	public String getPposition() {
		return pposition;
	}

	public void setPposition(String pposition) {
		this.pposition = pposition;
	}
	
	
}
