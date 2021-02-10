<?php
header('Content-type:application/json;charset=utf-8');
include "conn.php";

if(isset($_POST['idApp']) && isset($_POST['totalPembayaran']) && isset($_POST['idResepsionis']) && isset($_POST['caraPembayaran'])){
	$idApp = $_POST['idApp'];
	$totalPembayaran = $_POST['totalPembayaran'];
	$idResepsionis = $_POST['idResepsionis'];
	$caraPembayaran = $_POST['caraPembayaran'];


	$q = mysqli_query($conn, "INSERT INTO pembayaran(Id_Appointment, Total_Pembayaran, Id_Resepsionis, Cara_Pembayaran) VALUES('$idApp', '$totalPembayaran' , '$idResepsionis', '$caraPembayaran')");
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