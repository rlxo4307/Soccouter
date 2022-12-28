package dto;

public class dtoTeam {

	private String tko;
	private String timg;
	private int tid_daum;
	private int tid_sofa;
	
	public dtoTeam() {}
	
	public dtoTeam(String tko, String timg, int tid_daum, int tid_sofa) {
		this.tko = tko;
		this.timg = timg;
		this.tid_daum = tid_daum;
		this.tid_sofa = tid_sofa;
	}

	public String getTko() {
		return tko;
	}

	public void setTko(String tko) {
		this.tko = tko;
	}

	public String getTimg() {
		return timg;
	}

	public void setTimg(String timg) {
		this.timg = timg;
	}

	public int getTid_daum() {
		return tid_daum;
	}

	public void setTid_daum(int tid_daum) {
		this.tid_daum = tid_daum;
	}

	public int getTid_sofa() {
		return tid_sofa;
	}

	public void setTid_sofa(int tid_sofa) {
		this.tid_sofa = tid_sofa;
	}
	
	
	
}

