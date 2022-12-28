package backup;

public class getteamlist {
	/*
	// SOFA -> 리그에 속한 팀ID 구하기
		String season = "37036";	//21/22 시즌
			response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/17/season/"+season+"/standings/total")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			document = response.parse();
			
			html = document.html();
			text = document.text();		
			text = text.replace("\n", "");
			html = html.replace("\n", "");
			
			
			String tmpSofaTeam = text.substring(text.indexOf("[{\"team\":")+3, text.indexOf(",\"updatedAtTimestamp"));
			String [] tmpSofaTeamID = new String [30];	
			String [] tmpSofaEnName = new String [30];
			tmpSofaTeamID = tmpSofaTeam.split("\\},\\{\"");
			
			
			
			//tmpSofaTeamID.length
			for (int i = 0; i <1 ; i++) {
				if ( tmpSofaTeamID[i] != null ) {
					
					//10	ten
					String tmpTeamEnName = tmpSofaTeamID[i].substring(tmpSofaTeamID[i].indexOf("slug")+"slug".length()+3);
					tmpSofaEnName[i] = tmpTeamEnName.substring(0, tmpTeamEnName.indexOf("\",\""));	
					
					//12	tid_sofa
					String tmpSofaTid = tmpSofaTeamID[i].substring(tmpSofaTeamID[i].indexOf("id")+"id".length());
					tmpSofaTid = tmpSofaTid.substring(tmpSofaTid.indexOf("id")+"id".length()+2);
					tmpSofaTeamID[i] = tmpSofaTid.substring(0, tmpSofaTid.indexOf(",\""));	
					
					String tmpSofaTeamURL = tmpSofaEnName[i]+"/"+tmpSofaTeamID[i];
					System.out.println(tmpSofaTeamURL);
					
					
					
					response = Jsoup.connect("https://www.sofascore.com/team/football/"+tmpSofaTeamURL)
			                .method(Connection.Method.GET)
			                .ignoreContentType(true)
			                .execute();
					document = response.parse();
					
					html = document.html();
					text = document.text();		
					text = text.replace("\n", "");
					html = html.replace("\n", "");
					
					
					System.out.println(html);
				
				}
			}
	 	*/
}
