<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['appID'])){
	$id = $_POST['appID'];
	$q = mysqli_query($conn, "SELECT pasien.Nama_Pasien AS namaPasien, dokter.Nama_Dokter AS namaDokter, jenis_appointment.Harga AS hargaAppointment, appointment.Tanggal_Appointment AS tgl, appointment.Waktu_Appointment AS waktu, jenis_appointment.Nama_Jenis_Appointment as jenis FROM appointment JOIN dokter ON appointment.Id_Dokter = dokter.Id_Dokter JOIN jenis_appointment ON appointment.Id_Jenis = jenis_appointment.Id_Jenis JOIN pasien ON appointment.Id_Pasien = pasien.Id_Pasien WHERE appointment.Id_Appointment = '$id'");
	$response = array();

}


if(mysqli_num_rows($q)>0){
		$response["data"] = array();
		while($r = mysqli_fetch_array($q)){ 	
			$data = array();
			$data['namaPasien'] = $r['namaPasien'];
			$data['namaDokter'] = $r['namaDokter'];
			$data['hargaAppointment'] = $r['hargaAppointment'];
			$data['tglAppointment'] = $r['tgl'];
			$data['jenisAppointment'] = $r['jenis'];
			$data['waktuAppointment'] = $r['waktu'];
			array_push($response['data'], $data);
		}
		$response["success"] = 1;
		$response["message"] = "Data berhasil dibaca";
		echo json_encode($response);
	} else {
		$response["success"] = 0;
		$response["message"] = "Tidak ada data";
		echo json_encode($response);
	}


?>