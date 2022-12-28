<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
	#left { float:right; }
	#right { float:right; margin-left:2px; }
</style>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.css">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script type="text/javascript">
		var searchRequest = new XMLHttpRequest(); // 웹사이트에 요청을 보내는 역할을 하는 인스턴스
		var registerRequest = new XMLHttpRequest();
		function searchFunction() {
			request.open("Post", "./UserSearchServlet?teamName=" + encodeURIComponent(document.getElementById("teamName").value), true); // js에서 ajax 통신시 URI(o), URL(x)
			request.onreadystatechange = searchProcess;
			request.send(null);
		}
		function searchProcess() {
			var table = document.getElementById("ajaxTable"); // ajaxTable에서 가져옴
			table.innerHTML = ""; // 안의 내용을 모두 지운다
			if(request.readyState = 4 && request.status == 200) { // 일 때만 수행 (성공적 통신)
				var object = eval('(' + request.responseText + ')'); // JSON
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
			registerRequest.open("Post", "./UserRegisterServlet?id=" + encodeURIComponent(document.getElementById("registerName").value) + 
										"&team=" + encodeURIComponent(document.getElementById("registerId").value) + 				     
										"&team=" + encodeURIComponent(document.getElementById("registerTeam").value) + 
									     "&comm=" + encodeURIComponent(document.getElementById("registerComm").value), true); // js에서 ajax 통신시 URI(o), URL(x)
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
					var teamName = document.getElementById("teamName");
					var registerId = document.getElementById("registerId");
					var registerTeam = document.getElementById("registerTeam");
					var registerComm = document.getElementById("registerComm");
					teamName.value = "";
					registerId.value = "";
					registerTeam.value = "";
					registerComm.value = "";
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
		<div>
			<div id="right" class="col-xs-2">
	 			<button class="btn btn-primary" onclick="searchFunction();" type="button">검색</button>
	 		</div>		
			<div id="left">
								<!-- 입력할 때마다 searchFunction 실행 -->
				<input class="form-control" id="teamName" onkeyup="searchFunction()" type="text" size="20">
			</div>
 		</div>
 		
 		<table class="table" style="text-align: center; border:1px solid #dddddd">
 			<thead>
 				<tr>
 					<th style="background-color: #fafafa; text-align: center;">이름</th>
 					<th style="background-color: #fafafa; text-align: center;">나이</th>
 					<th style="background-color: #fafafa; text-align: center;">성별</th>
 					<!-- <th style="background-color: #fafafa; text-align: center;">이메일</th> -->
 				</tr>	
 			</thead>
 			<tbody id="ajaxTable">
 			</tbody>
 		</table>
	</div>
	<div class="container">
		<table class="table" style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="2" style="background-color: #fafafa; text-align:center;">회원 등록 양식</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="background-color: #fafafa; text-align:center;"><h5>아이디</h5></td>
					<td><input class="form-control" type="text" id="registerId" size="20"></td>
				</tr>
				<tr>
					<td style="background-color: #fafafa; text-align:center;"><h5>팀이름</h5></td>
					<td><input class="form-control" type="text" id="registerTeam" size="20"></td>
				</tr>
				<tr>
					<td style="background-color: #fafafa; text-align:center;"><h5>댓글</h5>
					<td>
						<div class="form-group" style="text-align:center; margin:0 auto;">
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary active">
									<input type="radio" name="registerComm" autocomplete="off" value="남자" checked>남자
								</label>
								<label class="btn btn-primary">
									<input type="radio" name="registerGender" autocomplete="off" value="여자">여자
								</label>
							</div>
						</div>
					</td>
				</tr>
				<!-- <tr>
					<td style="background-color: #fafafa; text-align:center;"><h5>이메일</h5></td>
					<td><input class="form-control" type="text" id="registerEmail" size="20"></td>
				</tr> -->
				<tr>
					<td colspan="2"><button class="btn btn-primary pull-right" onclick="registerFunction();" type="button">등록</button>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>