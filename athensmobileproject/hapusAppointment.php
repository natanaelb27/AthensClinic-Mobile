<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['id'])){
	$id = $_POST['id'];
	$q = mysqli_query($conn, "DELETE FROM appointment WHERE Id_Appointment = '$id'");
	$response = array();
}
?>