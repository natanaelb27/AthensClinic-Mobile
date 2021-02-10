<?php
header('Content-type:application/json;charset=utf-8');
include "conn.php";

if(isset($_POST['idAppointment']) && isset($_POST['idObat']) && isset($_POST['qObat'])){
	$idAppointment = $_POST['idAppointment'];
	$idObat = $_POST['idObat'];
	$q = $_POST['qObat'];

	$q = mysqli_query($conn, "INSERT INTO resep_obat_pasien(Id_Obat, Quantity, Id_Appointment, Status) VALUES('$idObat', '$q', '$idAppointment', 'pending')");
	$response = array();

	if($q){
		$response["success"] = 1;
		$response["message"] = "Data berhasil ditambah";
		echo json_encode($response);
	} else {
		$response["success"] = 0;
		$response["message"] = "Data gagal ditambah";
		echo json_encode($response);
	}
} else {
	$response["success"] = -1;
	$response["message"] = "Data kosong";
	echo json_encode($response);
}
?>