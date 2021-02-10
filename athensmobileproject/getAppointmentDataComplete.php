<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['appID'])){
	$id = $_POST['appID'];
	$q = mysqli_query($conn, "SELECT dokter.Nama_Dokter AS namaDokter, jenis_appointment.Harga AS hargaAppointment, appointment.Tanggal_Appointment AS tgl, appointment.Waktu_Appointment AS waktu, jenis_appointment.Nama_Jenis_Appointment as jenis,  pembayaran.Total_Pembayaran AS totalPembayaran FROM appointment JOIN dokter ON appointment.Id_Dokter = dokter.Id_Dokter JOIN jenis_appointment ON appointment.Id_Jenis = jenis_appointment.Id_Jenis JOIN pembayaran ON pembayaran.Id_Appointment = appointment.Id_Appointment WHERE appointment.Id_Appointment = '$id'");
	$response = array();

}


if(mysqli_num_rows($q)>0){
		$response["data"] = array();
		while($r = mysqli_fetch_array($q)){ 	
			$data = array();
			$data['namaDokter'] = $r['namaDokter'];
			$data['hargaAppointment'] = $r['hargaAppointment'];
			$data['tglAppointment'] = $r['tgl'];
			$data['jenisAppointment'] = $r['jenis'];
			$data['waktuAppointment'] = $r['waktu'];
			$data['totalPembayaran'] = $r['totalPembayaran'];
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