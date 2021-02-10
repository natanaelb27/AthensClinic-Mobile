<?php
header('Content-type:application/json;charset=utf-8');
include "conn.php";

if(isset($_POST['idPasien']) && isset($_POST['idDokter']) && isset($_POST['idJenis']) && isset($_POST['tglAppointment']) && isset($_POST['waktuAppointment'])){
	$idPasien = $_POST['idPasien'];
	$idDokter = $_POST['idDokter'];
	$idJenis = $_POST['idJenis'];
	$tglAppointment = $_POST['tglAppointment'];
	$waktuAppointment = $_POST['waktuAppointment'];

	$q = mysqli_query($conn, "INSERT INTO appointment(Id_Pasien, Id_Dokter, Id_Jenis, Tanggal_Appointment, Waktu_Appointment, Status) VALUES('$idPasien', '$idDokter' , '$idJenis', '$tglAppointment', '$waktuAppointment' , 'pending')");
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