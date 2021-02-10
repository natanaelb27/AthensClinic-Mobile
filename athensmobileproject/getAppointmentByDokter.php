<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['idDokter'])){
	$idDokter = $_POST['idDokter'];
	$q = mysqli_query($conn, "SELECT appointment.Id_Appointment AS id, pasien.Nama_Pasien AS namaPasien, appointment.Tanggal_Appointment AS tgl, appointment.Waktu_Appointment AS waktu, jenis_appointment.Nama_Jenis_Appointment as jenis FROM appointment JOIN pasien ON appointment.Id_Pasien = pasien.Id_Pasien JOIN jenis_appointment ON appointment.Id_Jenis = jenis_appointment.Id_Jenis WHERE status = 'pending' AND appointment.Id_Dokter = '$idDokter' ORDER BY appointment.Tanggal_Appointment");
	$response = array();
}

if(mysqli_num_rows($q)>0){
		$response["data"] = array();
		while($r = mysqli_fetch_array($q)){ 	
			$data = array();
			$data['idAppointment'] = $r['id'];
			$data['namaPasien'] = $r['namaPasien'];	
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