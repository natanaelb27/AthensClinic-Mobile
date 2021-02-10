<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['idDokter']) && isset($_POST['hari'])){
	$idDokter = $_POST['idDokter'];
	$hari = $_POST['hari'];
	$q = mysqli_query($conn, "SELECT * FROM jadwal_dokter WHERE Id_Dokter = '$idDokter' AND Hari = '$hari'");
	$response = array();

}

if(mysqli_num_rows($q)>0){
	$response["data"] = array();
	while($r = mysqli_fetch_array($q)){ 	
		$data = array();
		$data['waktuMulai'] = $r['Waktu_Mulai'];
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