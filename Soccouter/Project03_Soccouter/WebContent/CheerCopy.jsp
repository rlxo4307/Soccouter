<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.css">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script type="text/javascript">
		var searchRequest = new XMLHttpRequest(); // 웹사이트에 요청을 보내는 역할을 하는 인스턴스
		var registerRequest = new XMLHttpRequest();
		function searchFunction() {
			searchRequest.open("Post", "./UserSearchServlet?team=" + encodeURIComponent(document.getElementById("team").value), true); // js에서 ajax 통신시 URI(o), URL(x)
			searchRequest.onreadystatechange = searchProcess;
			searchRequest.send(null);
		}
		function searchProcess() {
			var table = document.getElementById("ajaxTable"); // ajaxTable에서 가져옴
			table.innerHTML = ""; // 안의 내용을 모두 지운다
			if(searchRequest.readyState = 4 && searchRequest.status == 200) { // 일 때만 수행 (성공적 통신)
				var object = eval('(' + searchRequest.responseText + ')'); // JSON
				var result = object.result; // result 한 것을 가져오겠다 (배열)
				for(var i = 0; i < result.length; i++) {
					var row = table.insertRow(0); // 현재의 테이블에 하나의 행을 만듬
					for(var j = 0; j < result[i].length; j++) { // 하나의 배열의 값이 끝날 때까지
						var cell = row.insertCell(j); // 하나의 셀을 만들어서 해당 행의 셀을 추가 j번째에
						cell.innerHTML = result[i][j].value;
					}
				}
			}
		}
		
		function registerFunction() { // 회원을 등록하는 요청을 서블릿으로 보내는 js함수
			registerRequest.open("Post", "./UserRegisterServlet?Id=" + encodeURIComponent(document.getElementById("registerId").value) + 
									     "&teamName=" + encodeURIComponent(document.getElementById("registerTeamName").value) + 
									     "&comm=" + encodeURIComponent(document.getElementById("registercomm").value), true); // js에서 ajax 통신시 URI(o), URL(x)
			registerRequest.onreadystatechange = registerProcess;
			registerRequest.send(null);
		}
		function registerProcess() {
			if(registerRequest.readyState == 4 && registerRequest.status == 200) {
				var result = registerRequest.responseText;
				if(result != 1) {
					alert('등록에 실패했습니다.');
				} 
				else {
					var teamName = document.getElementById("userName");
					var registerId = document.getElementById("registerId");
					var registerTeamName = document.getElementById("registerTeamName");
					var registerComm = document.getElementById("registerComm");
					teamName.value = "";
					registerId.value = "";
					register.TeamName = "";
					register.Comm = "";
					searchFunction();
				}
			}
		}
		window.onload = function() {
			searchFunction();
		}
	</script>
<title>Insert title here</title>
</head>
<body>
	<br>
	<div class="container">
	<!-- 
		<div class="form-group row-pull-right">			입력할 때마다 searchFunction 실행
			<input class="form-control" id="userName" onkeyup="searchFunction()" type="text" size="20">
 		</div>
 		<div class="col-xs-2">
 			<button class="btn btn-primary" onclick="searchFunction();" type="button">검색</button>
 		</div> 
 	-->
 		<table class="table" style="text-align: center; border:1px solid #dddddd">
 			<thead>
 				<tr>
 					<th style="background-color: #fafafa; text-align: center;">아이디</th>
 					<th style="background-color: #fafafa; text-align: center;">팀이름</th>
 					<th style="background-color: #fafafa; text-align: center;">댓글</th>
 					<th style="background-color: #fafafa; text-align: center;">날짜</th>
 				</tr>	
 			</thead>
 			<tbody id="ajaxTable">
 			</tbody>
 		</table>
	</div>
	<div class="container">
		<table class="table" style="text-align: center; border: 1px solid #dddddd">
			<tbody>
				<tr>
					<td style="background-color: #fafafa; text-align:center;"><h5>댓글입력</h5></td>
					<td><input class="form-control" type="hidden" id="registerTeamName" value=""></td>
					<td><input class="form-control" type="hidden" id="registerId" value=""></td>
					<td><input class="form-control" type="text" id="registerComm" size="20"></td>	
				</tr>
				<tr>
					<td colspan="2"><button class="btn btn-primary pull-right" onclick="registerFunction();" type="button">등록</button>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>