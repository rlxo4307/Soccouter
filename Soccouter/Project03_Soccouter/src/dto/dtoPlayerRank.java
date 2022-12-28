package dto;

public class dtoPlayerRank {

	private int rank;
	private int tid_sofa;
	private int pid_sofa;
	private String pko;
	private String pimg;
	private String tko;
	private String timg;
	private int appearances;
	private int goals;
	private int assists;
	private int attack_points;
	private int total_shots;
	private int shots_on_target;
	private int penalties_taken;
	private int fouls;
	private int yellow_cards;
	private int red_cards;
	private int offsides;
	
	public dtoPlayerRank() { }
	
	public dtoPlayerRank (int rank, int tid_sofa, int pid_sofa, String pko, String pimg, String tko, String timg, int appearances, int goals, int assists, int attack_points,
							int total_shots, int shots_on_target, int penalties_taken, int fouls, int yellow_cards, int red_cards, int offsides) {
		this.rank = rank;
		this.tid_sofa = tid_sofa;
		this.pid_sofa = pid_sofa;
		this.pko = pko;
		this.pimg = pimg;
		this.tko = tko;
		this.timg = timg;
		this.appearances = appearances;
		this.goals = goals;
		this.assists = assists;
		this.attack_points = attack_points;
		this.total_shots = total_shots;
		this.shots_on_target = shots_on_target;
		this.penalties_taken = penalties_taken;
		this.fouls = fouls;
		this.yellow_cards = yellow_cards;
		this.red_cards = red_cards;
		this.offsides = offsides;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public int getTid_sofa() {
		return tid_sofa;
	}

	public void setTid_sofa(int tid_sofa) {
		this.tid_sofa = tid_sofa;
	}
	
	public int getPid_sofa() {
		return pid_sofa;
	}

	public void setPid_sofa(int pid_sofa) {
		this.pid_sofa = pid_sofa;
	}
	
	public String getPko() {
		return pko;
	}

	public void setPko(String pko) {
		this.pko = pko;
	}

	public String getPimg() {
		return pimg;
	}

	public void setPimg(String pimg) {
		this.pimg = pimg;
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

	public int getAppearances() {
		return appearances;
	}

	public void setAppearances(int appearances) {
		this.appearances = appearances;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public int getAttack_points() {
		return attack_points;
	}

	public void setAttack_points(int attack_points) {
		this.attack_points = attack_points;
	}

	public int getTotal_shots() {
		return total_shots;
	}

	public void setTotal_shots(int total_shots) {
		this.total_shots = total_shots;
	}

	public int getShots_on_target() {
		return shots_on_target;
	}

	public void setShots_on_target(int shots_on_target) {
		this.shots_on_target = shots_on_target;
	}

	public int getPenalties_taken() {
		return penalties_taken;
	}

	public void setPenalties_taken(int penalties_taken) {
		this.penalties_taken = penalties_taken;
	}

	public int getFouls() {
		return fouls;
	}

	public void setFouls(int fouls) {
		this.fouls = fouls;
	}

	public int getYellow_cards() {
		return yellow_cards;
	}

	public void setYellow_cards(int yellow_cards) {
		this.yellow_cards = yellow_cards;
	}

	public int getRed_cards() {
		return red_cards;
	}

	public void setRed_cards(int red_cards) {
		this.red_cards = red_cards;
	}

	public int getOffsides() {
		return offsides;
	}

	public void setOffsides(int offsides) {
		this.offsides = offsides;
	}

}