<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

$q = mysqli_query($conn, "SELECT appointment.Id_Appointment AS idAppointment, resep_obat_pasien.Id_Resep AS idResep, pasien.Nama_Pasien AS namaPasien,  appointment.Tanggal_Appointment AS tgl, obat.Nama_Obat AS namaObat, resep_obat_pasien.Quantity AS jumlah FROM appointment JOIN resep_obat_pasien ON appointment.Id_Appointment = resep_obat_pasien.Id_Appointment JOIN obat ON obat.Id_Obat = resep_obat_pasien.Id_Obat JOIN pasien ON appointment.Id_Pasien = pasien.Id_Pasien WHERE resep_obat_pasien.Status = 'pending' ORDER BY appointment.Tanggal_Appointment");
	$response = array();

if(mysqli_num_rows($q)>0){
		$response["data"] = array();
		while($r = mysqli_fetch_array($q)){ 	
			$data = array();
			$data['idAppointment'] = $r['idAppointment'];
			$data['Pasien'] = $r['namaPasien'];
			$data['idResep'] = $r['idResep'];
			$data['tglAppointment'] = $r['tgl'];
			$data['namaObat'] = $r['namaObat'];
			$data['jumlah'] = $r['jumlah'];
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