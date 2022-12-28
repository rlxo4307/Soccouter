package dto;

public class dtoPlayerStat {

	private int tid_sofa;
	private int pid_sofa;
	private String tko;
	private String timg;
	private String pko;
	private String pen;
	private String pimg;
	private String pslug;
	private int pheight;
	private String pbirth;
	private String pnation;
	private String pnationimg;
	private String pfoot;
	private String pposition;
	
	private int appearances;
	private double rating;
	private int shooting;
	private int passes;
	private int dribbles;
	private int minutes_played;
	private int saves;
	private int clean_sheets;
	private double conceded_per_game;
	
	private int individual_skill;
	private int genius;
	private int concentration;
	private int active;
	private int boldness;
	private int composure;
	private int cooperation;
	private int physicals;
	private int punches;
	private int runs_out;
	private int high_claims;
	private int saves_from_inside_box;
	private int saved_shots_from_outside_the_box;
	private int penalties_saved;
	
	private int goal_conversion_per;
	private int headed_goals;
	private int free_kick_goals;
	private int penalty_goals;
	private int crosses;
	private int one_on_one_mark;
	private int tackles;
	private int sight;
	private int defense_position;
	private int interceptions;
	private int clearances;
	private int blocked_shots;
	
	
	public dtoPlayerStat() {}
	
	public dtoPlayerStat(int tid_sofa, int pid_sofa, String tko, String timg, String pko, String pen, String pimg, String pslug, int pheight, String pbirth, String pnation, String pnationimg, String pfoot, String pposition, 
			int appearances, double rating, int shooting, int passes, int dribbles, int minutes_played, int saves, int clean_sheets, double conceded_per_game, 
			int individual_skill, int genius, int concentration, int active, int boldness, int composure, int cooperation, int physicals, int punches, int runs_out, int high_claims, int saves_from_inside_box, int saved_shots_from_outside_the_box, int penalties_saved, 
			int goal_conversion_per, int headed_goals, int free_kick_goals, int penalty_goals, int crosses, int one_on_one_mark, int tackles, int sight, int defense_position, int interceptions, int clearances, int blocked_shots) {
		
		this.tid_sofa = tid_sofa;
		this.pid_sofa = pid_sofa;
		this.tko = tko;
		this.timg = timg;
		this.pko = pko;
		this.pen = pen;
		this.pimg = pimg;
		this.pslug = pslug;
		this.pheight = pheight;
		this.pbirth = pbirth;
		this.pnation = pnation;
		this.pnationimg = pnationimg;
		this.pfoot = pfoot;
		this.pposition = pposition;
		
		this.appearances = appearances;
		this.rating = rating;
		this.shooting = shooting;
		this.passes = passes;
		this.dribbles = dribbles;
		this.minutes_played = minutes_played;
		this.saves = saves;
		this.clean_sheets = clean_sheets;
		this.conceded_per_game = conceded_per_game;
		
		this.individual_skill = individual_skill;
		this.genius = genius;
		this.concentration = concentration;
		this.active = active;
		this.boldness = boldness;
		this.composure = composure;
		this.cooperation = cooperation;
		this.physicals = physicals;
		this.punches = punches;
		this.runs_out = runs_out;
		this.high_claims = high_claims;
		this.saves_from_inside_box = saves_from_inside_box;
		this.saved_shots_from_outside_the_box = saved_shots_from_outside_the_box;
		this.penalties_saved = penalties_saved;
		
		this.goal_conversion_per = goal_conversion_per;
		this.headed_goals = headed_goals;
		this.free_kick_goals = free_kick_goals;
		this.penalty_goals = penalty_goals;
		this.crosses = crosses;
		this.one_on_one_mark = one_on_one_mark;
		this.tackles = tackles;
		this.sight = sight;
		this.defense_position = defense_position;
		this.interceptions = interceptions;
		this.clearances = clearances;
		this.blocked_shots = blocked_shots;
		
		
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

	public String getPko() {
		return pko;
	}

	public void setPko(String pko) {
		this.pko = pko;
	}

	public String getPen() {
		return pen;
	}

	public void setPen(String pen) {
		this.pen = pen;
	}

	public String getPimg() {
		return pimg;
	}

	public void setPimg(String pimg) {
		this.pimg = pimg;
	}
	
	public String getPslug() {
		return pslug;
	}

	public void setPslug(String pslug) {
		this.pslug = pslug;
	}

	public int getPheight() {
		return pheight;
	}

	public void setPheight(int pheight) {
		this.pheight = pheight;
	}

	public String getPbirth() {
		return pbirth;
	}

	public void setPbirth(String pbirth) {
		this.pbirth = pbirth;
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

	public int getAppearances() {
		return appearances;
	}

	public void setAppearances(int appearances) {
		this.appearances = appearances;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getShooting() {
		return shooting;
	}

	public void setShooting(int shooting) {
		this.shooting = shooting;
	}

	public int getPasses() {
		return passes;
	}

	public void setPasses(int passes) {
		this.passes = passes;
	}

	public int getDribbles() {
		return dribbles;
	}

	public void setDribbles(int dribbles) {
		this.dribbles = dribbles;
	}

	public int getMinutes_played() {
		return minutes_played;
	}

	public void setMinutes_played(int minutes_played) {
		this.minutes_played = minutes_played;
	}

	public int getSaves() {
		return saves;
	}

	public void setSaves(int saves) {
		this.saves = saves;
	}

	public int getClean_sheets() {
		return clean_sheets;
	}

	public void setClean_sheets(int clean_sheets) {
		this.clean_sheets = clean_sheets;
	}

	public double getConceded_per_game() {
		return conceded_per_game;
	}

	public void setConceded_per_game(double conceded_per_game) {
		this.conceded_per_game = conceded_per_game;
	}

	public int getIndividual_skill() {
		return individual_skill;
	}

	public void setIndividual_skill(int individual_skill) {
		this.individual_skill = individual_skill;
	}

	public int getGenius() {
		return genius;
	}

	public void setGenius(int genius) {
		this.genius = genius;
	}

	public int getConcentration() {
		return concentration;
	}

	public void setConcentration(int concentration) {
		this.concentration = concentration;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getBoldness() {
		return boldness;
	}

	public void setBoldness(int boldness) {
		this.boldness = boldness;
	}

	public int getComposure() {
		return composure;
	}

	public void setComposure(int composure) {
		this.composure = composure;
	}

	public int getCooperation() {
		return cooperation;
	}

	public void setCooperation(int cooperation) {
		this.cooperation = cooperation;
	}

	public int getPhysicals() {
		return physicals;
	}

	public void setPhysicals(int physicals) {
		this.physicals = physicals;
	}

	public int getPunches() {
		return punches;
	}

	public void setPunches(int punches) {
		this.punches = punches;
	}

	public int getRuns_out() {
		return runs_out;
	}

	public void setRuns_out(int runs_out) {
		this.runs_out = runs_out;
	}

	public int getHigh_claims() {
		return high_claims;
	}

	public void setHigh_claims(int high_claims) {
		this.high_claims = high_claims;
	}

	public int getSaves_from_inside_box() {
		return saves_from_inside_box;
	}

	public void setSaves_from_inside_box(int saves_from_inside_box) {
		this.saves_from_inside_box = saves_from_inside_box;
	}

	public int getSaved_shots_from_outside_the_box() {
		return saved_shots_from_outside_the_box;
	}

	public void setSaved_shots_from_outside_the_box(int saved_shots_from_outside_the_box) {
		this.saved_shots_from_outside_the_box = saved_shots_from_outside_the_box;
	}

	public int getPenalties_saved() {
		return penalties_saved;
	}

	public void setPenalties_saved(int penalties_saved) {
		this.penalties_saved = penalties_saved;
	}

	public int getGoal_conversion_per() {
		return goal_conversion_per;
	}

	public void setGoal_conversion_per(int goal_conversion_per) {
		this.goal_conversion_per = goal_conversion_per;
	}

	public int getHeaded_goals() {
		return headed_goals;
	}

	public void setHeaded_goals(int headed_goals) {
		this.headed_goals = headed_goals;
	}

	public int getFree_kick_goals() {
		return free_kick_goals;
	}

	public void setFree_kick_goals(int free_kick_goals) {
		this.free_kick_goals = free_kick_goals;
	}

	public int getPenalty_goals() {
		return penalty_goals;
	}

	public void setPenalty_goals(int penalty_goals) {
		this.penalty_goals = penalty_goals;
	}

	public int getCrosses() {
		return crosses;
	}

	public void setCrosses(int crosses) {
		this.crosses = crosses;
	}

	public int getOne_on_one_mark() {
		return one_on_one_mark;
	}

	public void setOne_on_one_mark(int one_on_one_mark) {
		this.one_on_one_mark = one_on_one_mark;
	}

	public int getTackles() {
		return tackles;
	}

	public void setTackles(int tackles) {
		this.tackles = tackles;
	}

	public int getSight() {
		return sight;
	}

	public void setSight(int sight) {
		this.sight = sight;
	}

	public int getDefense_position() {
		return defense_position;
	}

	public void setDefense_position(int defense_position) {
		this.defense_position = defense_position;
	}

	public int getInterceptions() {
		return interceptions;
	}

	public void setInterceptions(int interceptions) {
		this.interceptions = interceptions;
	}

	public int getClearances() {
		return clearances;
	}

	public void setClearances(int clearances) {
		this.clearances = clearances;
	}

	public int getBlocked_shots() {
		return blocked_shots;
	}

	public void setBlocked_shots(int blocked_shots) {
		this.blocked_shots = blocked_shots;
	}

}
