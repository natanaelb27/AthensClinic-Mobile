<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['idPasien'])){
	$idPasien = $_POST['idPasien'];
	$q = mysqli_query($conn, "SELECT resep_obat_pasien.Id_Resep AS idResep, obat.Nama_Obat AS namaObat, resep_obat_pasien.Quantity AS jumlah, obat.Harga AS harga FROM appointment JOIN resep_obat_pasien ON appointment.Id_Appointment = resep_obat_pasien.Id_Appointment JOIN obat ON obat.Id_Obat = resep_obat_pasien.Id_Obat WHERE appointment.Id_Pasien = '$idPasien' AND resep_obat_pasien.Status = 'pending'");
	$response = array();
}

if(mysqli_num_rows($q)>0){
		$response["data"] = array();
		while($r = mysqli_fetch_array($q)){ 	
			$data = array();
			$data['idResep'] = $r['idResep'];
			$data['namaObat'] = $r['namaObat'];
			$data['jumlahObat'] = $r['jumlah'];
			$data['hargaObat'] = $r['harga'];
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