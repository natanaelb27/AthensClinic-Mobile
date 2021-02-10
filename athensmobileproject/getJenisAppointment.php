<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

$q = mysqli_query($conn, "SELECT * FROM jenis_appointment");
$response = array();

if(mysqli_num_rows($q)>0){
	$response["data"] = array();
	while($r = mysqli_fetch_array($q)){ 	
		$data = array();
		$data['idjenis'] = $r['Id_Jenis'];
		$data['jenisappointment'] = $r['Nama_Jenis_Appointment'];
		$data['harga'] = $r['Harga'];
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