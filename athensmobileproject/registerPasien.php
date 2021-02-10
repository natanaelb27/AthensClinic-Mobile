<?php
header('Content-type:application/json;charset=utf-8');
include "conn.php";

if(isset($_POST['namaPasien']) && isset($_POST['alamatPasien']) && isset($_POST['phonePasien']) && isset($_POST['jkPasien']) && isset($_POST['tglLahirPasien']) && isset($_POST['emailPasien']) && isset($_POST['passwordPasien'])){

	$nama = $_POST['namaPasien'];
	$alamat = $_POST['alamatPasien'];
	$phone = $_POST['phonePasien'];
	$jk = $_POST['jkPasien'];
	$tglLahir = $_POST['tglLahirPasien'];
	$email = $_POST['emailPasien'];
	$password = $_POST['passwordPasien'];
	

	$q = mysqli_query($conn, "INSERT INTO pasien(Nama_Pasien, Jenis_Kelamin, Tanggal_Lahir, No_Telpon, Alamat, Email, Password) VALUES('$nama', '$jk' , '$tglLahir', '$phone' , '$alamat' , '$email' , '$password')");
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