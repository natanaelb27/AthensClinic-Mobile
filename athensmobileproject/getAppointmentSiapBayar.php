<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

$q = mysqli_query($conn, "SELECT appointment.Id_Appointment AS id, pasien.Nama_Pasien AS namaPasien, dokter.Nama_Dokter AS namaDokter, pasien.No_Telpon as notelp, appointment.Tanggal_Appointment AS tgl, appointment.Waktu_Appointment AS waktu, jenis_appointment.Nama_Jenis_Appointment as jenis FROM appointment JOIN dokter ON appointment.Id_Dokter = dokter.Id_Dokter JOIN jenis_appointment ON appointment.Id_Jenis = jenis_appointment.Id_Jenis JOIN pasien ON appointment.Id_Pasien = pasien.Id_Pasien WHERE appointment.Status = 'active' ORDER BY appointment.Tanggal_Appointment");
	$response = array();

if(mysqli_num_rows($q)>0){
		$response["data"] = array();
		while($r = mysqli_fetch_array($q)){ 	
			$data = array();
			$data['idAppointment'] = $r['id'];
			$data['Pasien'] = $r['namaPasien'];
			$data['Dokter'] = $r['namaDokter'];
			$data['notelp'] = $r['notelp'];
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