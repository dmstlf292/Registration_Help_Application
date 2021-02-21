<?php
    header("Content-Type: text/html; charset-UTF-8");
    $con = mysqli_connect("localhost", "", "", "");

    $userID = $_GET["userID"];

    $result = mysqli_query($con, "SELECT COURSE.courseID, COURSE.courseGrade, COURSE.courseTitle, COURSE.courseProfessor, COURSE.courseCredit
    , COURSE.courseDivide, COURSE.coursePersonnel, COURSE.courseTime FROM SCHEDULE_UNI, COURSE WHERE COURSE.courseID = SCHEDULE_UNI.courseID AND 
    COURSE.courseArea = '교양및기타' GROUP BY SCHEDULE_UNI.courseID ORDER BY COUNT(SCHEDULE_UNI.courseID) DESC LIMIT 5;");
    $response = array();

    while($row = mysqli_fetch_array($result)){
        array_push($response, array("courseID"=>$row[0], "courseGrade"=>$row[1], "courseTitle"=>$row[2]
        , "courseProfessor"=>$row[3], "courseCredit"=>$row[4], "courseDivide"=>$row[5], "coursePersonnel"=>$row[6]
        , "courseTime"=>$row[7]));
    }

    echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
?> 