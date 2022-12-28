<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title></title>
<script src="js/jquery-3.3.1.js"></script>

<script>
	//javascript를 jQuery로 //아이디 중복체크할 때 많이 사용함. 화면 안 바뀌므로
    function kaja(){
        $.ajax({ // $.ajax()  //$.get        // $.post
            url:"check.jsp", //url:"hello.jsp" //open(method,url,async)
            data:{ //json (javascript object notation)
                irum:"hong", //open,send, name:value
                na2:35 // kaja.jsp?irum=hong&na2=35
            },         //<input type=text name=irum>
            dataType:"text", // responseText, responseXML
            type:"get", //open
            success:function(result1){ //json //4,200
                alert("응답을 받는다"+ result1)
            },
            error:function(xhr1,status){
                alert("에러상태 : "+ "\t" + xhr1.status); //statusText
            }
        }); // $.ajax-end
    }// kaja()-end
</script>
</head>
<body>
	<form>
		<h2>아이디 : </h2><input type="text" name="id">
		<h2>비밀번호 : </h2><input type="text" name="pw">
		<input type="button" value="가자 서버로" onclick="kaja()">
	</form>
</body>
</html>




