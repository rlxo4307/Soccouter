package dto;

import java.sql.Date;

public class dtoCheer {

	private String id;
	private String team;
	private String comm;
	private Date updateDate;
	
	public dtoCheer() {}
	
	public dtoCheer(String id, String team, String comm, Date updateDate) {
		this.id = id;
		this.team = team;
		this.comm = comm;
		this.updateDate = updateDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
