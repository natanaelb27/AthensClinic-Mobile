<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['idDokter'])){
	$idDokter = $_POST['idDokter'];
	$tglHariIni = date('Y-m-d');
	$q = mysqli_query($conn, "SELECT appointment.Id_Appointment AS idAppointment, pasien.Nama_Pasien AS namaPasien, pasien.No_Telpon AS NoTelpPasien FROM appointment JOIN pasien ON appointment.Id_pasien = pasien.Id_Pasien WHERE appointment.Tanggal_Appointment = '$tglHariIni' AND appointment.Id_Dokter = '$idDokter' AND appointment.Status = 'pending' ORDER BY appointment.Id_Appointment");
	$response = array();
}

if(mysqli_num_rows($q)>0){
	$response["data"] = array();
	while($r = mysqli_fetch_array($q)){ 	
		$data = array();
		$data['idAppointment'] = $r['idAppointment'];
		$data['namaPasien'] = $r['namaPasien'];
		$data['notelp'] = $r['NoTelpPasien'];
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