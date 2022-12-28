package dto;

public class dtoSquad {

	private int pid_sofa;
	private String pimg;
	private String pnum;
	private String pko;
	private String pposition;
	private int ranking;
	private int win;
	private int draw;
	private int loss;
	private int pts;
	private int gf;
	private int ga;
	private int gd;
	private int total_game;
	
	public dtoSquad() {}
	
	public dtoSquad(int pid_sofa, String pimg, String pnum, String pko, String pposition, int ranking, int win, 
			int draw, int loss, int pts, int gf, int ga,int gd, int total_game) {
		this.pid_sofa = pid_sofa;
		this.pimg = pimg;
		this.pnum = pnum;
		this.pko = pko;
		this.pposition = pposition;
		this.ranking = ranking;
		this.win = win;
		this.draw = draw;
		this.loss = loss;
		this.pts = pts;
		this.gf = gf;
		this.ga = ga;
		this.gd = gd;
		this.total_game = total_game;
	}

	public int getPid_sofa() {
		return pid_sofa;
	}

	public void setPid_sofa(int pid_sofa) {
		this.pid_sofa = pid_sofa;
	}

	public String getPimg() {
		return pimg;
	}

	public void setPimg(String pimg) {
		this.pimg = pimg;
	}

	public String getPnum() {
		return pnum;
	}

	public void setPnum(String pnum) {
		this.pnum = pnum;
	}

	public String getPko() {
		return pko;
	}

	public void setPko(String pko) {
		this.pko = pko;
	}

	public String getPposition() {
		return pposition;
	}

	public void setPposition(String pposition) {
		this.pposition = pposition;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
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

	public int getPts() {
		return pts;
	}

	public void setPts(int pts) {
		this.pts = pts;
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

	public int getTotal_game() {
		return total_game;
	}

	public void setTotal_game(int total_game) {
		this.total_game = total_game;
	}
	
}
