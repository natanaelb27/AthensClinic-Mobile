-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 17, 2020 at 03:53 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `athens`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `Id_Appointment` int(11) NOT NULL,
  `Id_Pasien` int(11) NOT NULL,
  `Id_Dokter` int(11) NOT NULL,
  `Id_Jenis` int(11) NOT NULL,
  `Tanggal_Appointment` date NOT NULL,
  `Waktu_Appointment` time NOT NULL,
  `Status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`Id_Appointment`, `Id_Pasien`, `Id_Dokter`, `Id_Jenis`, `Tanggal_Appointment`, `Waktu_Appointment`, `Status`) VALUES
(1, 1, 1, 3, '2020-04-29', '13:00:00', 'done');

-- --------------------------------------------------------

--
-- Table structure for table `dokter`
--

CREATE TABLE `dokter` (
  `Id_Dokter` int(5) NOT NULL,
  `Nama_Dokter` varchar(30) NOT NULL,
  `No_Telpon` varchar(13) NOT NULL,
  `Password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dokter`
--

INSERT INTO `dokter` (`Id_Dokter`, `Nama_Dokter`, `No_Telpon`, `Password`) VALUES
(1, 'Dr. Akbar, Sp. KG', '0812345678912', 'akbargigi'),
(2, 'Dr. Budi, Sp. KG', '087856392541', 'budigigi'),
(3, 'Dr. Siti, Sp. KG', '081963254718', 'sitigigi'),
(4, 'Dr. Nanda, Sp. KG', '085596321587', 'nandagigi'),
(5, 'Dr. Tuti, Sp. KG', '089825148719', 'tutigigi');

-- --------------------------------------------------------

--
-- Table structure for table `jadwal_dokter`
--

CREATE TABLE `jadwal_dokter` (
  `Id_Jadwal` int(11) NOT NULL,
  `Id_Dokter` int(11) NOT NULL,
  `Hari` varchar(10) NOT NULL,
  `Waktu_Mulai` time NOT NULL,
  `Waktu_Akhir` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jadwal_dokter`
--

INSERT INTO `jadwal_dokter` (`Id_Jadwal`, `Id_Dokter`, `Hari`, `Waktu_Mulai`, `Waktu_Akhir`) VALUES
(1, 1, 'Monday', '08:00:00', '10:00:00'),
(2, 1, 'Tuesday', '11:00:00', '12:00:00'),
(3, 1, 'Wednesday', '13:00:00', '15:00:00'),
(4, 1, 'Thursday', '10:00:00', '11:00:00'),
(5, 1, 'Friday', '13:00:00', '14:00:00'),
(6, 1, 'Saturday', '10:00:00', '11:00:00'),
(7, 2, 'Monday', '11:00:00', '12:00:00'),
(8, 2, 'Tuesday', '08:00:00', '10:00:00'),
(9, 2, 'Wednesday', '16:00:00', '18:00:00'),
(10, 2, 'Thursday', '08:00:00', '09:00:00'),
(11, 2, 'Friday', '10:00:00', '12:00:00'),
(12, 3, 'Monday', '13:00:00', '15:00:00'),
(13, 3, 'Tuesday', '15:00:00', '18:00:00'),
(14, 3, 'Friday', '08:00:00', '09:00:00'),
(15, 3, 'Saturday', '08:00:00', '09:00:00'),
(16, 4, 'Monday', '16:00:00', '18:00:00'),
(17, 4, 'Tuesday', '16:00:00', '18:00:00'),
(18, 4, 'Wednesday', '11:00:00', '12:00:00'),
(19, 4, 'Friday', '17:00:00', '18:00:00'),
(20, 4, 'Saturday', '12:00:00', '14:00:00'),
(21, 5, 'Tuesday', '13:00:00', '15:00:00'),
(22, 5, 'Wednesday', '08:00:00', '10:00:00'),
(23, 5, 'Thursday', '12:00:00', '14:00:00'),
(24, 5, 'Friday', '15:00:00', '16:00:00'),
(25, 5, 'Saturday', '15:00:00', '18:00:00'),
(26, 1, 'Sunday', '08:00:00', '10:00:00'),
(27, 5, 'Sunday', '11:00:00', '12:00:00'),
(28, 2, 'Sunday', '13:00:00', '15:00:00'),
(29, 3, 'Sunday', '16:00:00', '18:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `jenis_appointment`
--

CREATE TABLE `jenis_appointment` (
  `Id_Jenis` int(11) NOT NULL,
  `Nama_Jenis_Appointment` varchar(30) NOT NULL,
  `Harga` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jenis_appointment`
--

INSERT INTO `jenis_appointment` (`Id_Jenis`, `Nama_Jenis_Appointment`, `Harga`) VALUES
(1, 'Cabut Gigi ', 350000),
(2, 'Membersihan Karang Gigi', 200000),
(3, 'Menambal Gigi', 200000),
(4, 'Whitening Tooth', 560000),
(5, 'Pasang Behel', 1500000),
(6, 'Check Up', 300000);

-- --------------------------------------------------------

--
-- Table structure for table `obat`
--

CREATE TABLE `obat` (
  `Id_Obat` int(11) NOT NULL,
  `Nama_Obat` varchar(15) NOT NULL,
  `Harga` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `obat`
--

INSERT INTO `obat` (`Id_Obat`, `Nama_Obat`, `Harga`) VALUES
(1, 'Naproxen', 165000),
(2, 'Acetaminophen', 150000),
(3, 'Dentasol', 250000),
(4, 'Ponstan Tablet', 350000),
(5, 'Asam Mefemanat', 50000),
(6, 'Kalium Diclofen', 300000),
(7, 'Antaglin', 50000);

-- --------------------------------------------------------

--
-- Table structure for table `pasien`
--

CREATE TABLE `pasien` (
  `Id_Pasien` int(11) NOT NULL,
  `Nama_Pasien` varchar(50) NOT NULL,
  `Jenis_Kelamin` varchar(10) NOT NULL,
  `Tanggal_Lahir` date NOT NULL,
  `No_Telpon` varchar(20) NOT NULL,
  `Alamat` varchar(100) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pasien`
--

INSERT INTO `pasien` (`Id_Pasien`, `Nama_Pasien`, `Jenis_Kelamin`, `Tanggal_Lahir`, `No_Telpon`, `Alamat`, `Email`, `Password`) VALUES
(1, 'Adi Sucipto', 'Pria', '1995-04-01', '081234512345', 'Jalan Merak No. 32', 'adisucipto123@gmail.com', 'adisucipto123'),
(2, 'Eko Logi', 'Pria', '1991-01-05', '081234567890', 'Jalan Merpati No. 23', 'ekologi157@gmail.com', 'ekologi12345'),
(3, 'Bobi Subakti', 'Pria', '1990-02-17', '081122334455', 'Jalan Gajah No. 3', 'bobisubakti12@gmail.com', 'bobisubakti123');

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

CREATE TABLE `pembayaran` (
  `Id_Pembayaran` int(11) NOT NULL,
  `Id_Appointment` int(11) NOT NULL,
  `Total_Pembayaran` double NOT NULL,
  `Id_Resepsionis` int(11) NOT NULL,
  `Cara_Pembayaran` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pembayaran`
--

INSERT INTO `pembayaran` (`Id_Pembayaran`, `Id_Appointment`, `Total_Pembayaran`, `Id_Resepsionis`, `Cara_Pembayaran`) VALUES
(1, 1, 2375000, 1, 'Debit');

-- --------------------------------------------------------

--
-- Table structure for table `resepsionis`
--

CREATE TABLE `resepsionis` (
  `Id_Resepsionis` int(11) NOT NULL,
  `Nama_Resepsionis` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `resepsionis`
--

INSERT INTO `resepsionis` (`Id_Resepsionis`, `Nama_Resepsionis`, `Password`) VALUES
(1, 'Bella Gita', 'bella123'),
(2, 'Leon Wijaya', 'leon123');

-- --------------------------------------------------------

--
-- Table structure for table `resep_obat_pasien`
--

CREATE TABLE `resep_obat_pasien` (
  `Id_Resep` int(11) NOT NULL,
  `Id_Obat` int(11) NOT NULL,
  `Quantity` double NOT NULL,
  `Status` varchar(10) NOT NULL,
  `Id_Appointment` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `resep_obat_pasien`
--

INSERT INTO `resep_obat_pasien` (`Id_Resep`, `Id_Obat`, `Quantity`, `Status`, `Id_Appointment`) VALUES
(1, 4, 3, 'done', 1),
(2, 2, 2, 'done', 1),
(3, 1, 5, 'done', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`Id_Appointment`),
  ADD KEY `Id_Pasien` (`Id_Pasien`),
  ADD KEY `Id_Jenis` (`Id_Jenis`),
  ADD KEY `Id_Dokter` (`Id_Dokter`);

--
-- Indexes for table `dokter`
--
ALTER TABLE `dokter`
  ADD PRIMARY KEY (`Id_Dokter`);

--
-- Indexes for table `jadwal_dokter`
--
ALTER TABLE `jadwal_dokter`
  ADD PRIMARY KEY (`Id_Jadwal`),
  ADD KEY `Id_Dokter` (`Id_Dokter`);

--
-- Indexes for table `jenis_appointment`
--
ALTER TABLE `jenis_appointment`
  ADD PRIMARY KEY (`Id_Jenis`);

--
-- Indexes for table `obat`
--
ALTER TABLE `obat`
  ADD PRIMARY KEY (`Id_Obat`);

--
-- Indexes for table `pasien`
--
ALTER TABLE `pasien`
  ADD PRIMARY KEY (`Id_Pasien`);

--
-- Indexes for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`Id_Pembayaran`),
  ADD KEY `Id_Appointment` (`Id_Appointment`),
  ADD KEY `Id_Resepsionis` (`Id_Resepsionis`);

--
-- Indexes for table `resepsionis`
--
ALTER TABLE `resepsionis`
  ADD PRIMARY KEY (`Id_Resepsionis`);

--
-- Indexes for table `resep_obat_pasien`
--
ALTER TABLE `resep_obat_pasien`
  ADD PRIMARY KEY (`Id_Resep`),
  ADD KEY `Id_Appointment` (`Id_Appointment`),
  ADD KEY `Id_Obat` (`Id_Obat`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment`
--
ALTER TABLE `appointment`
  MODIFY `Id_Appointment` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `dokter`
--
ALTER TABLE `dokter`
  MODIFY `Id_Dokter` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `jadwal_dokter`
--
ALTER TABLE `jadwal_dokter`
  MODIFY `Id_Jadwal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `jenis_appointment`
--
ALTER TABLE `jenis_appointment`
  MODIFY `Id_Jenis` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `obat`
--
ALTER TABLE `obat`
  MODIFY `Id_Obat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `pasien`
--
ALTER TABLE `pasien`
  MODIFY `Id_Pasien` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `Id_Pembayaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `resepsionis`
--
ALTER TABLE `resepsionis`
  MODIFY `Id_Resepsionis` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `resep_obat_pasien`
--
ALTER TABLE `resep_obat_pasien`
  MODIFY `Id_Resep` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`Id_Pasien`) REFERENCES `pasien` (`Id_Pasien`),
  ADD CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`Id_Jenis`) REFERENCES `jenis_appointment` (`Id_Jenis`),
  ADD CONSTRAINT `appointment_ibfk_3` FOREIGN KEY (`Id_Dokter`) REFERENCES `dokter` (`Id_Dokter`);

--
-- Constraints for table `jadwal_dokter`
--
ALTER TABLE `jadwal_dokter`
  ADD CONSTRAINT `jadwal_dokter_ibfk_1` FOREIGN KEY (`Id_Dokter`) REFERENCES `dokter` (`Id_Dokter`);

--
-- Constraints for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD CONSTRAINT `pembayaran_ibfk_2` FOREIGN KEY (`Id_Appointment`) REFERENCES `appointment` (`Id_Appointment`),
  ADD CONSTRAINT `pembayaran_ibfk_3` FOREIGN KEY (`Id_Resepsionis`) REFERENCES `resepsionis` (`Id_Resepsionis`);

--
-- Constraints for table `resep_obat_pasien`
--
ALTER TABLE `resep_obat_pasien`
  ADD CONSTRAINT `resep_obat_pasien_ibfk_3` FOREIGN KEY (`Id_Appointment`) REFERENCES `appointment` (`Id_Appointment`),
  ADD CONSTRAINT `resep_obat_pasien_ibfk_4` FOREIGN KEY (`Id_Obat`) REFERENCES `obat` (`Id_Obat`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
