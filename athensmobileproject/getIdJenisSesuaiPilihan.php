<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['jenis'])){
	$jenis = $_POST['jenis'];
	$q = mysqli_query($conn, "SELECT * FROM jenis_appointment WHERE Nama_Jenis_Appointment = '$jenis'");
	$response = array();

}

if(mysqli_num_rows($q)>0){
	$response["data"] = array();
	while($r = mysqli_fetch_array($q)){ 	
		$data = array();
		$data['idjenis'] = $r['Id_Jenis'];
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