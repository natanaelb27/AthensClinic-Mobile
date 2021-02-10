<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['id'])){
	$id = $_POST['id'];
	$q = mysqli_query($conn, "SELECT appointment.Id_Appointment AS id, dokter.Nama_Dokter AS namaDokter, appointment.Tanggal_Appointment AS tgl, appointment.Waktu_Appointment AS waktu, jenis_appointment.Nama_Jenis_Appointment as jenis FROM appointment JOIN dokter ON appointment.Id_Dokter = dokter.Id_Dokter JOIN jenis_appointment ON appointment.Id_Jenis = jenis_appointment.Id_Jenis WHERE appointment.Status = 'active' AND appointment.Id_Pasien = '$id'");
	$response = array();
}

if(mysqli_num_rows($q)>0){
		$response["data"] = array();
		while($r = mysqli_fetch_array($q)){ 	
			$data = array();
			$data['idAppointment'] = $r['id'];
			$data['Dokter'] = $r['namaDokter'];
			$data['tglAppointment'] = $r['tgl'];
			$data['jenisAppointment'] = $r['jenis'];
			$data['waktuAppointment'] = $r['waktu'];
			array_push($response['data'], $data);
		}
		$response["success"] = 1;
		$response["message"] = "Data mahasiswa berhasil dibaca";
		echo json_encode($response);
	} else {
		$response["success"] = 0;
		$response["message"] = "Tidak ada data";
		echo json_encode($response);
	}


?>