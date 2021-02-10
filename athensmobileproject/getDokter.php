<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

$q = mysqli_query($conn, "SELECT * FROM dokter");
$response = array();

if(mysqli_num_rows($q)>0){
	$response["data"] = array();
	while($r = mysqli_fetch_array($q)){ 	
		$data = array();
		$data['iddokter'] = $r['Id_Dokter'];
		$data['namadokter'] = $r['Nama_Dokter'];
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