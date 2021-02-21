<?php
	header("Content-Type : text/html; charset=UTF-8");
	$con = mysqli_connect("localhost", "", "","");

 
  $userID = $_GET["userID"];

  $result = mysqli_query($con, "SELECT COURSE.courseID, COURSE.courseGrade, COURSE.courseTitle, COURSE.courseDivide, COURSE.coursePersonnel
    , COUNT(SCHEDULE_UNI.courseID), COURSE.courseCredit FROM SCHEDULE_UNI, COURSE WHERE SCHEDULE_UNI.courseID IN (SELECT SCHEDULE_UNI.courseID FROM 
    SCHEDULE_UNI WHERE SCHEDULE_UNI.userID = '$userID') AND SCHEDULE_UNI.courseID = COURSE.courseID GROUP BY SCHEDULE_UNI.courseID");

  $response = array();
  while ($row = mysqli_fetch_array($result)) {
    array_push($response, array("courseID"=>$row[0], "courseGrade"=>$row[1], "courseTitle"=>$row[2], "courseDivide" => $row[3],
      "coursePersonnel"=>$row[4], "COUNT(SCHEDULE_UNI.courseID)"=>$row[5], "courseCredit"=>$row[6]));

  }

    echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
?>

