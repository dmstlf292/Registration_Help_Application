<?php
	$con = mysqli_connect("localhost", "", "","");
	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];
	$userGender = $_POST["userGender"];
	$userMajor = $_POST["userMajor"];
	$userEmail = $_POST["userEmail"];

	$checkedPassword = password_hash($userPassword, PASSWORD_DEFAULT);//암호형식의 비밀번호로 바뀌어서 들어간다.
	$statement = mysqli_prepare ($con, "INSERT INTO MEMBER VALUES (?,?,?,?,?)");
	mysqli_stmt_bind_param($statement, "sssss", $userID, $checkedPassword, $userGender,$userMajor,$userEmail);
	mysqli_stmt_execute($statement);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);

?>