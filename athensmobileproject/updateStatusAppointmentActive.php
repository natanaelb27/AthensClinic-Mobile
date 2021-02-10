<?php
header('Content-type:application/json;charset=utf-8');
include "conn.php";

if(isset($_POST['idAppointment'])){
	$idAppointment = $_POST['idAppointment'];

	$q = mysqli_query($conn, "UPDATE appointment SET Status = 'active' WHERE Id_Appointment = '$idAppointment'");
	$response = array();

	if($q){
		$response["success"] = 1;
		$response["message"] = "Data berhasil diupdate";
		echo json_encode($response);
	} else {
		$response["success"] = 0;
		$response["message"] = "Data gagal diupdate";
		echo json_encode($response);
	}
} else {
	$response["success"] = -1;
	$response["message"] = "Data kosong";
	echo json_encode($response);
}
?>