<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['idAppointment'])){
	$id = $_POST['idAppointment'];
	$q = mysqli_query($conn, "SELECT obat.Nama_Obat AS namaObat, resep_obat_pasien.Quantity AS jumlah, obat.Harga AS harga FROM resep_obat_pasien JOIN obat ON obat.Id_Obat = resep_obat_pasien.Id_Obat WHERE resep_obat_pasien.Status = 'done' AND resep_obat_pasien.Id_Appointment = '$id'");
	$response = array();
}



if(mysqli_num_rows($q)>0){
		$response["data"] = array();
		while($r = mysqli_fetch_array($q)){ 	
			$data = array();
			$data['namaObat'] = $r['namaObat'];
			$data['jumlah'] = $r['jumlah'];
			$data['hargaobatsatuan'] = $r['harga'];
			$data['totalhargaobat'] = $r['jumlah'] * $r['harga'];
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