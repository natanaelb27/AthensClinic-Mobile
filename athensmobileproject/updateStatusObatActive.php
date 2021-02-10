<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['id'])){
	$id = $_POST['id'];
	$q = mysqli_query($conn, "UPDATE resep_obat_pasien SET Status = 'active' WHERE Id_Resep = '$id'");
	$response = array();

	if($q){
		$response["success"] = 1;
		$response["message"] = "Data berhasil diupdate";
		echo json_encode($response);
	} else {
		$response["success"] = 0;
		$response["message"] = "Data gagal diupdate";
		echo json_encode($response);
	}
} else {
	$response["success"] = -1;
	$response["message"] = "Data kosong";
	echo json_encode($response);
}
?>