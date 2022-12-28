<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
	
	body { background-image:url('image/스타디움.jpg');
	 		background-repeat:no-repeat;
	 		background-size:100% 360%;}
	 		
	#title { margin-left:10%; margin-top:10vh; border-radius:0px; }
	
</style>
<meta charset="UTF-8">
<title> 리그/시즌 선택 </title>
</head>
<body>  
<div id="title">
	<form action="Team.do">
		<h3 style="color:white;">2021-2022 시즌 리그 선택</h3>
		    <div>
		      <input type="radio" id="epl" name="league" value="epl" checked>
		      <input type="hidden" name="lslid" value="20222023">
		      <input type="hidden" name="sid_daum" value="20222023">
		      <input type="hidden" name="sid_sofa" value="41886">
		      <label for="epl" style="color:gold;">프리미어리그</label>
		    </div>
			
		    <div>
		      <input type="radio" id="primera" name="league" value="primera">
		      <input type="hidden" name="lslid" value="20222023">
		      <input type="hidden" name="sid_daum" value="20222023">
		      <input type="hidden" name="sid_sofa" value="42409">
		      <label for="primera" style="color:gold;">라리가</label>
		    </div>
			
		    <div>
		      <input type="radio" id="bundesliga" name="league" value="bundesliga">
		      <input type="hidden" name="lslid" value="20222023">
		      <input type="hidden" name="sid_daum" value="20222023">
		      <input type="hidden" name="sid_sofa" value="42268">
		      <label for="bundesliga" style="color:gold;">분데스리가</label>
		    </div>
		    
			<div>
		      <input type="radio" id="seria" name="league" value="seriea">
		      <input type="hidden" name="lslid" value="20222023">
		      <input type="hidden" name="sid_daum" value="20222023">
		      <input type="hidden" name="sid_sofa" value="42415">
		      <label for="seria" style="color:gold;">세리에 A</label>
		    </div>
		    
			<div>
		      <input type="radio" id="ligue1" name="league" value="ligue1">
		      <input type="hidden" name="lslid" value="20222023">
		      <input type="hidden" name="sid_daum" value="20222023">
		      <input type="hidden" name="sid_sofa" value="42273">
		      <label for="ligue1" style="color:gold;">리그 1</label>
		    </div>
		<input type="submit" style="width:200px;" value="이동">
	</form>
</div>

</body>
</html>