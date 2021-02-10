<?php
header('Content-type:application/json;charset=utf-8');
include 'conn.php';

if(isset($_POST['hari'])){
    $hari = $_POST['hari'];
    $q = mysqli_query($conn, "SELECT jadwal_dokter.Id_Dokter as id, dokter.Nama_Dokter as nama FROM dokter JOIN jadwal_dokter ON dokter.Id_Dokter = jadwal_dokter.Id_Dokter WHERE jadwal_dokter.Hari = '$hari'");
    $response = array();
}

if(mysqli_num_rows($q)>0){
    $response["data"] = array();
    while($r = mysqli_fetch_array($q)){     
        $data = array();
        $data['iddokter'] = $r['id'];
        $data['namadokter'] = $r['nama'];
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