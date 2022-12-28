package dto;

public class dtoTeamRank {
	
	private String tko;				// 팀 한글명
	private String timg;			// 팀 이미지
	private int tid_sofa;			// 팀 아이디 (sofa)
	private int ranking;			// 순위
	private int game;				// 경기수
	private int win;				// 승
	private int draw;				// 무
	private int loss;				// 패
	private int gf;					// 득점
	private int ga;					// 실점
	private int gd;					// 득실차
	private int pts;				// 승점
	private int sid_sofa;			// 시즌 아이디 (sofa)
	private int lid_sofa;			// 리그 아이디 (sofa)
	private int tid_daum;			// 팀 아이디 (daum)
	
	public dtoTeamRank() {}
	
	public dtoTeamRank(String tko, String timg, int tid_sofa, int ranking,  int game, int win, int draw, int loss,
							int gf, int ga, int gd, int pts, int sid_sofa, int lid_sofa, int tid_daum) {	
		
		this.tko = tko;
		this.timg = timg;
		this.ranking = ranking;
		this.tid_sofa = tid_sofa;
		this.game = game;
		this.win = win;
		this.draw = draw;
		this.loss = loss;
		this.gf = gf;
		this.ga = ga;
		this.gd = gd;
		this.pts = pts;		
		this.sid_sofa = sid_sofa;
		this.lid_sofa = lid_sofa;
		
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

	public int getTid_sofa() {
		return tid_sofa;
	}

	public void setTid_sofa(int tid_sofa) {
		this.tid_sofa = tid_sofa;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getGame() {
		return game;
	}

	public void setGame(int game) {
		this.game = game;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getLoss() {
		return loss;
	}

	public void setLoss(int loss) {
		this.loss = loss;
	}

	public int getGf() {
		return gf;
	}

	public void setGf(int gf) {
		this.gf = gf;
	}

	public int getGa() {
		return ga;
	}

	public void setGa(int ga) {
		this.ga = ga;
	}

	public int getGd() {
		return gd;
	}

	public void setGd(int gd) {
		this.gd = gd;
	}

	public int getPts() {
		return pts;
	}

	public void setPts(int pts) {
		this.pts = pts;
	}

	public int getSid_sofa() {
		return sid_sofa;
	}

	public void setSid_sofa(int sid_sofa) {
		this.sid_sofa = sid_sofa;
	}

	public int getLid_sofa() {
		return lid_sofa;
	}

	public void setLid_sofa(int lid_sofa) {
		this.lid_sofa = lid_sofa;
	}

	public int getTid_daum() {
		return tid_daum;
	}

	public void setTid_daum(int tid_daum) {
		this.tid_daum = tid_daum;
	}

	
	
}
