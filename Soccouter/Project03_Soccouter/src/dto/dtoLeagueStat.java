package dto;

public class dtoLeagueStat {
	private String state_bar;		// 진출 체크
	private String league;			// 리그
	private String season;			// 시즌
	private int rank;				// 순위
	private String teamName;		// 팀
	private int gameCount;			// 경기 수
	private int gainPoint;			// 승점
	private int won;				// 승
	private int drawn;				// 무
	private int lost;				// 패
	private int gainGoal;			// 득점
	private int loseGoal;			// 실점
	private int goalGap;			// 득실차
	
	public dtoLeagueStat () {}
	
	public dtoLeagueStat (String state_bar, String league, String season, int rank, String teamName, int gameCount, int gainPoint, int won, int drawn, int lost, int gainGoal, int loseGoal, int goalGap) {
		this.state_bar = state_bar;
		this.league = league;
		this.season = season;
		this.rank = rank;
		this.teamName = teamName;
		this.gameCount = gameCount;
		this.gainPoint = gainPoint;
		this.won = won;
		this.drawn = drawn;
		this.lost = lost;
		this.gainGoal = gainGoal;
		this.loseGoal = loseGoal;
		this.goalGap = goalGap;
	}
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public int getGameCount() {
		return gameCount;
	}
	public void setGameCount(int gameCount) {
		this.gameCount = gameCount;
	}
	public int getGainPoint() {
		return gainPoint;
	}
	public void setGainPoint(int gainPoint) {
		this.gainPoint = gainPoint;
	}
	public int getWon() {
		return won;
	}
	public void setWon(int won) {
		this.won = won;
	}
	public int getDrawn() {
		return drawn;
	}
	public void setDrawn(int drawn) {
		this.drawn = drawn;
	}
	public int getLost() {
		return lost;
	}
	public void setLost(int lost) {
		this.lost = lost;
	}
	public int getGainGoal() {
		return gainGoal;
	}
	public void setGainGoal(int gainGoal) {
		this.gainGoal = gainGoal;
	}
	public int getLoseGoal() {
		return loseGoal;
	}
	public void setLoseGoal(int loseGoal) {
		this.loseGoal = loseGoal;
	}
	public int getGoalGap() {
		return goalGap;
	}
	public void setGoalGap(int goalGap) {
		this.goalGap = goalGap;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public String getState_bar() {
		return state_bar;
	}

	public void setState_bar(String state_bar) {
		this.state_bar = state_bar;
	}

}

