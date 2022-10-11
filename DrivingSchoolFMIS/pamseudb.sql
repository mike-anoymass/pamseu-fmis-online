-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Oct 11, 2022 at 01:22 PM
-- Server version: 5.7.36
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pamseudb`
--

-- --------------------------------------------------------

--
-- Table structure for table `allocations`
--

DROP TABLE IF EXISTS `allocations`;
CREATE TABLE IF NOT EXISTS `allocations` (
  `vehicle` varchar(30) NOT NULL,
  `student` int(11) NOT NULL,
  `pending` int(11) DEFAULT NULL,
  `staff` int(11) NOT NULL,
  `date` varchar(30) NOT NULL,
  `less` int(5) NOT NULL,
  `bal` int(5) NOT NULL,
  `time` varchar(30) NOT NULL,
  `dateAdded` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`date`,`vehicle`,`staff`,`time`),
  KEY `vehicle` (`vehicle`),
  KEY `student` (`student`),
  KEY `pending` (`pending`),
  KEY `staff` (`staff`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `allocations`
--

INSERT INTO `allocations` (`vehicle`, `student`, `pending`, `staff`, `date`, `less`, `bal`, `time`, `dateAdded`) VALUES
('MHG1020', 32654, 32655, 4, '2022-10-10', 34, 12, '06:30-7:00', '2022-10-11 12:54:02'),
('MHG1020', 32651, 32654, 4, '2022-10-10', 12, 12, '07:00-07:30', '2022-10-11 09:57:42'),
('MHG1020', 32654, 32655, 4, '2022-10-10', 34, 12, '07:30-08:00', '2022-10-11 12:55:14'),
('MHG1020', 32652, 32656, 4, '2022-10-10', 1, 1, '08:00-08:30', '2022-10-11 09:58:03'),
('MHG1020', 32651, 32653, 4, '2022-10-10', 34, 12, '09:00-09:30', '2022-10-11 13:00:40'),
('MHG1020', 32657, 32655, 4, '2022-10-10', 23, 10, '09:30-10:00', '2022-10-11 13:09:46'),
('MHG1020', 32656, 32653, 4, '2022-10-10', 12, 10, '10:30-11:00', '2022-10-11 13:19:24'),
('MHG1020', 32653, 32656, 4, '2022-10-10', 10, 10, '11:30-12:00', '2022-10-11 14:26:28'),
('MHG1020', 32655, 32653, 4, '2022-10-10', 34, 34, '12:00-12:30', '2022-10-11 15:12:01'),
('MHG1020', 32657, 32658, 4, '2022-10-10', 40, 12, '17:00-17:30', '2022-10-11 13:02:01');

-- --------------------------------------------------------

--
-- Table structure for table `commisionrate`
--

DROP TABLE IF EXISTS `commisionrate`;
CREATE TABLE IF NOT EXISTS `commisionrate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rate` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `commisionrate`
--

INSERT INTO `commisionrate` (`id`, `rate`) VALUES
(1, 2.5);

-- --------------------------------------------------------

--
-- Table structure for table `commisions`
--

DROP TABLE IF EXISTS `commisions`;
CREATE TABLE IF NOT EXISTS `commisions` (
  `test` int(11) NOT NULL,
  `employee` int(11) NOT NULL,
  `commision` float DEFAULT NULL,
  `isPaid` tinyint(1) NOT NULL,
  `datePaid` varchar(30) DEFAULT NULL,
  KEY `employee` (`employee`),
  KEY `test` (`test`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `commisions`
--

INSERT INTO `commisions` (`test`, `employee`, `commision`, `isPaid`, `datePaid`) VALUES
(1, 3, 4400, 0, NULL),
(4, 4, 4400, 0, NULL),
(6, 5, 4730, 1, '2022-10-05 22:43:16'),
(10, 5, 2200, 0, NULL),
(11, 5, 3902.5, 1, '2022-10-06 07:51:07');

-- --------------------------------------------------------

--
-- Table structure for table `courseduration`
--

DROP TABLE IF EXISTS `courseduration`;
CREATE TABLE IF NOT EXISTS `courseduration` (
  `courseID` int(11) NOT NULL,
  `duration` int(11) NOT NULL,
  `date` varchar(30) NOT NULL,
  `fees` float NOT NULL,
  `updatedOn` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`courseID`,`duration`),
  KEY `duration` (`duration`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `courseduration`
--

INSERT INTO `courseduration` (`courseID`, `duration`, `date`, `fees`, `updatedOn`) VALUES
(21, 12, '2022-08-05 00:13:44', 280000, NULL),
(21, 13, '2022-08-05 00:13:26', 350000, NULL),
(22, 12, '2022-08-05 00:12:59', 175000, NULL),
(22, 13, '2022-08-05 00:12:25', 200000, NULL),
(23, 13, '2022-08-17 14:13:57', 100000, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
CREATE TABLE IF NOT EXISTS `courses` (
  `code` varchar(10) NOT NULL,
  `name` varchar(30) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `governmentFee` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`code`, `name`, `id`, `governmentFee`) VALUES
('c1', 'heavy goods', 21, 50000),
('b', 'light goods', 22, 40000),
('a1', 'motocycle', 23, 30000);

-- --------------------------------------------------------

--
-- Table structure for table `discounts`
--

DROP TABLE IF EXISTS `discounts`;
CREATE TABLE IF NOT EXISTS `discounts` (
  `student` int(11) NOT NULL,
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`student`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `discounts`
--

INSERT INTO `discounts` (`student`, `amount`) VALUES
(32645, 5000);

-- --------------------------------------------------------

--
-- Table structure for table `durations`
--

DROP TABLE IF EXISTS `durations`;
CREATE TABLE IF NOT EXISTS `durations` (
  `name` varchar(30) NOT NULL,
  `days` int(5) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `durations`
--

INSERT INTO `durations` (`name`, `days`, `id`) VALUES
('half course', 40, 12),
('full course', 60, 13);

-- --------------------------------------------------------

--
-- Table structure for table `employeenextofkins`
--

DROP TABLE IF EXISTS `employeenextofkins`;
CREATE TABLE IF NOT EXISTS `employeenextofkins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee` int(11) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `physicalAddress` varchar(30) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employeenextofkins`
--

INSERT INTO `employeenextofkins` (`id`, `employee`, `phone`, `physicalAddress`, `name`) VALUES
(1, 3, '0884488484', 'kawale 3', 'mike mhango'),
(2, 4, '0883899393', 'kawale', 'ellube spart'),
(3, 5, '049949494', 'nsambeta', 'isah bdna'),
(4, 6, '0994499494', 'kawale', 'vinjero mhango');

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
CREATE TABLE IF NOT EXISTS `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(30) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `postalAddress` varchar(100) NOT NULL,
  `physicalAddress` varchar(30) NOT NULL,
  `department` varchar(30) NOT NULL,
  `employeeStatus` varchar(30) NOT NULL,
  `workingHours` varchar(15) NOT NULL,
  `dateOfEntry` varchar(30) NOT NULL,
  `date` varchar(30) NOT NULL,
  `gender` varchar(7) DEFAULT NULL,
  `dob` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fullname` (`fullname`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`id`, `fullname`, `phone`, `postalAddress`, `physicalAddress`, `department`, `employeeStatus`, `workingHours`, `dateOfEntry`, `date`, `gender`, `dob`, `email`, `isActive`) VALUES
(1, 'memory mmango', '0993949994', 'kawale', 'ntandire', 'Receptionist', 'Full Time', '12-15hrs', '2022-08-03', '2022-08-13 17:07:30', 'female', '2022-08-04', NULL, 1),
(3, 'kazondo frank', '0883388383', 'kawale 3\nP.O. BOX 201407', 'll', 'Driver', 'Part Time', '12-16hrs', '2022-08-16', '2022-08-13 17:14:30', 'male', '2022-08-11', NULL, 0),
(4, 'joab chakhadza', '0884488484', 'kawale 2\nP.O. BOX 20407', 'area 23', 'Driver', 'Full Time', '12hrs -14hrs', '2022-08-09', '2022-08-23 13:44:05', 'male', '2022-08-03', NULL, 1),
(5, 'chikondi banda', '0994599595', 'nsambeta	', 'lilongwe', 'Driver', 'Full Time', '8hrs', '2022-08-31', '2022-09-20 16:29:43', 'male', '2022-08-30', NULL, 1),
(6, 'blessings libamba', '0884949494', 'kawale', 'kawale', 'Instructor', 'Part Time', '5hrs', '2022-09-07', '2022-09-20 16:43:59', 'male', '2022-09-07', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
CREATE TABLE IF NOT EXISTS `expenses` (
  `name` varchar(40) NOT NULL,
  `date` varchar(30) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `user` (`user`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`name`, `date`, `id`, `user`) VALUES
('bundle', '2022-08-13 17:04:53', 1, NULL),
('stationery', '2022-08-13 17:05:02', 2, NULL),
('rent', '2022-09-06 15:20:27', 5, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expense` varchar(40) NOT NULL,
  `date` varchar(30) NOT NULL,
  `amount` float NOT NULL,
  `mode` varchar(30) DEFAULT NULL,
  `user` varchar(30) DEFAULT NULL,
  `ref` varchar(30) DEFAULT NULL,
  `dateOfPayment` varchar(30) DEFAULT NULL,
  `mirage` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user` (`user`),
  KEY `expense` (`expense`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `expense`, `date`, `amount`, `mode`, `user`, `ref`, `dateOfPayment`, `mirage`) VALUES
(1, 'stationery', '2022-08-13 17:05:23', 100000, 'TNM Mpamba', 'mmhango', 'djd9929', '2022-08-31', 'kaya man'),
(3, 'bundle', '2022-09-05 11:49:57', 100000, 'Cheque', 'mmhango', 'ak822839949', '2022-09-12', 'for september'),
(4, 'stationery', '2022-09-06 15:16:48', 50000, 'Cash', 'davie', '', '2022-09-21', 'for october'),
(5, 'rent', '2022-09-06 15:20:52', 40000, 'Cheque', 'davie', 'dkdkdkf99393030', '2022-09-28', 'for september'),
(6, 'stationery', '2022-09-21 09:56:25', 200000, 'Cash', 'mike', '', '2022-10-04', 'for october');

-- --------------------------------------------------------

--
-- Table structure for table `receipts`
--

DROP TABLE IF EXISTS `receipts`;
CREATE TABLE IF NOT EXISTS `receipts` (
  `receiptNo` int(11) NOT NULL AUTO_INCREMENT,
  `studentID` int(11) DEFAULT NULL,
  `date` varchar(30) NOT NULL,
  `amount` float NOT NULL,
  `modeOfPayment` varchar(30) DEFAULT NULL,
  `paymentOf` varchar(60) DEFAULT NULL,
  `user` varchar(30) DEFAULT NULL,
  `reference` varchar(30) DEFAULT NULL,
  `dateOfPayment` varchar(30) NOT NULL,
  PRIMARY KEY (`receiptNo`),
  KEY `studentID` (`studentID`),
  KEY `user` (`user`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `receipts`
--

INSERT INTO `receipts` (`receiptNo`, `studentID`, `date`, `amount`, `modeOfPayment`, `paymentOf`, `user`, `reference`, `dateOfPayment`) VALUES
(6, 32645, '2022-08-06 15:28:50', 15000, 'Cheque', 'Blue Card', 'mmhango', '', '2022-08-04'),
(7, 32645, '2022-08-06 15:39:41', 85000, 'Cash', 'Blue Card', 'mmhango', '', '2022-08-19'),
(10, 32645, '2022-08-17 13:46:26', 10000, 'Airtel Money', 'Fees', 'mike', '100202993', '2022-08-12'),
(11, 32645, '2022-08-17 13:46:47', 50000, 'Cash', 'Blue Card', 'mike', '', '2022-08-04'),
(12, 32650, '2022-08-17 14:32:34', 150000, 'Cash', 'Fees', 'mmhango', '18192990', '2022-08-24'),
(13, 32645, '2022-09-05 11:38:27', 40000, 'Cash', 'Fees', 'mmhango', '1020203930', '2022-09-07'),
(14, 32645, '2022-09-06 15:17:51', 5000, 'Cheque', 'Fees', 'davie', '2738949404040', '2022-08-31'),
(15, 32651, '2022-09-06 15:19:56', 50000, 'Cash', 'Fees', 'davie', '', '2022-09-01'),
(16, 32656, '2022-09-21 09:08:45', 100000, 'Cash', 'Fees', 'mike', '10203030304', '2022-09-22'),
(17, 32658, '2022-09-21 09:16:16', 100000, 'Cheque', 'Fees', 'mike', '182293004cacg', '2022-09-01'),
(18, 32653, '2022-09-21 09:54:14', 150000, 'Cash', 'Fees', 'mike', '', '2022-08-31');

-- --------------------------------------------------------

--
-- Table structure for table `studentnextofkins`
--

DROP TABLE IF EXISTS `studentnextofkins`;
CREATE TABLE IF NOT EXISTS `studentnextofkins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentID` int(11) DEFAULT NULL,
  `phone` varchar(15) NOT NULL,
  `physicalAddress` varchar(30) NOT NULL,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `studentID` (`studentID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `studentnextofkins`
--

INSERT INTO `studentnextofkins` (`id`, `studentID`, `phone`, `physicalAddress`, `name`) VALUES
(15, 32645, '0884855850', 'nsambeta 4', 'mike mhangolo'),
(19, 32650, '92992', 'kkaks', 'sjssj'),
(20, 32651, '0993949494', 'kawale 4', 'oddeta mmango'),
(21, 32652, '0994999494', 'nsambeta', 'mbelwa ketih'),
(22, 32653, '09393939', 'kawale', 'kalodno'),
(23, 32654, '0993944949', 'chemusa 2', 'israel banda'),
(24, 32655, '0883838383', 'kawale', 'lafick '),
(25, 32656, '0994595959', 'kawale', 'mike mhango'),
(26, 32657, '0884844848', 'kawale', 'kasambala'),
(27, 32658, '0993848439', 'nsambeta', 'mblemho nyirenda');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
CREATE TABLE IF NOT EXISTS `students` (
  `studentID` int(11) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(60) NOT NULL,
  `postalAddress` varchar(100) DEFAULT NULL,
  `phone` varchar(15) NOT NULL,
  `course` varchar(30) NOT NULL,
  `date` varchar(30) DEFAULT NULL,
  `duration` varchar(30) DEFAULT NULL,
  `gender` varchar(7) NOT NULL,
  `trn` varchar(30) DEFAULT NULL,
  `dateRegistered` varchar(30) NOT NULL,
  `anyDiscount` tinyint(1) NOT NULL,
  `anyGovernmentFee` tinyint(1) NOT NULL,
  `graduated` tinyint(1) DEFAULT NULL,
  `addedBy` varchar(40) DEFAULT NULL,
  `updatedOn` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`studentID`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `trn` (`trn`),
  KEY `course` (`course`),
  KEY `duration` (`duration`)
) ENGINE=InnoDB AUTO_INCREMENT=32659 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`studentID`, `fullname`, `postalAddress`, `phone`, `course`, `date`, `duration`, `gender`, `trn`, `dateRegistered`, `anyDiscount`, `anyGovernmentFee`, `graduated`, `addedBy`, `updatedOn`) VALUES
(32645, 'Daves Mhango', 'area 3', '0983000049', 'b', '2022-08-05 02:56:14', 'half course', 'male', '45456556770', '2022-08-03', 1, 1, 1, 'Mike Mhango', '2022-08-23 13:46:00'),
(32649, 'dean', 'sksks	', '03939949', 'b', '2022-08-17 11:31:18', 'full course', 'male', NULL, '2022-08-09', 0, 0, 0, 'Mirriam Mhango', '2022-08-17 11:31:18'),
(32650, 'deannison', 'kawale	', '22990303', 'b', '2022-08-17 11:32:57', 'full course', 'male', NULL, '2022-08-04', 0, 0, 1, 'Mirriam Mhango', '2022-08-17 12:44:49'),
(32651, 'Memory Mmango', 'kawale 2. 	', '09939449494', 'a1', '2022-09-05 08:55:35', 'full course', 'female', NULL, '2022-08-30', 0, 0, 0, 'Mike Mhango', '2022-09-05 08:55:35'),
(32652, 'zondiwe mmbelwa', 'nsambeta', '0993949949', 'b', '2022-09-05 08:58:48', 'half course', 'male', NULL, '2022-08-31', 0, 0, 0, 'Mike Mhango', '2022-09-05 08:58:48'),
(32653, 'umunthu ', 'll', '094949490', 'b', '2022-09-05 09:03:45', 'full course', 'male', NULL, '2022-08-31', 0, 0, 0, 'Mike Mhango', '2022-09-05 09:03:45'),
(32654, 'Stella Banda', 'chemusa', '0993944949', 'c1', '2022-09-05 09:25:14', 'half course', 'female', NULL, '2022-09-13', 0, 0, 0, 'Mirriam Mhango', '2022-09-05 09:25:14'),
(32655, 'wahindra lafick', 'll', '0883388383', 'a1', '2022-09-05 09:35:10', 'full course', 'male', NULL, '2022-09-07', 0, 0, 0, 'Mike Mhango', '2022-09-05 09:35:10'),
(32656, 'faith mussa', 'll	', '08838488848', 'a1', '2022-09-05 10:38:55', 'full course', 'male', NULL, '2022-08-31', 0, 0, 0, 'Mike Mhango', '2022-09-05 10:38:55'),
(32657, 'kasambala john', 'lilngwe', '07747474484', 'b', '2022-09-05 10:44:55', 'half course', 'male', NULL, '2022-09-07', 0, 0, 0, 'Mike Mhango', '2022-09-05 10:44:55'),
(32658, 'Joseph Nyirenda', 'kawale 2', '0993848484', 'b', '2022-09-06 15:23:16', 'half course', 'male', NULL, '2022-09-07', 0, 1, 0, 'davie libamba', '2022-09-06 15:23:16');

-- --------------------------------------------------------

--
-- Stand-in structure for view `studentsview`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `studentsview`;
CREATE TABLE IF NOT EXISTS `studentsview` (
`fullname` varchar(60)
);

-- --------------------------------------------------------

--
-- Table structure for table `tests`
--

DROP TABLE IF EXISTS `tests`;
CREATE TABLE IF NOT EXISTS `tests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student` int(11) NOT NULL,
  `testName` text NOT NULL,
  `date` text NOT NULL,
  `dateOfTest` text NOT NULL,
  `passOrFail` text NOT NULL,
  `user` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `student` (`student`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tests`
--

INSERT INTO `tests` (`id`, `student`, `testName`, `date`, `dateOfTest`, `passOrFail`, `user`) VALUES
(1, 32645, 'Road test', '2022-08-23 09:39:50', '2022-08-10', 'pass', 'Mike Mhango'),
(2, 32650, 'Aptitude test 1', '2022-08-23 09:47:26', '2022-08-03', 'fail', 'Mike Mhango'),
(4, 32650, 'Road test', '2022-08-23 13:44:41', '2022-08-01', 'pass', 'Mike Mhango'),
(6, 32658, 'Road test', '2022-09-20 16:32:24', '2022-08-31', 'fail', 'Mike Mhango'),
(7, 32656, 'Road test', '2022-09-20 16:38:49', '2022-09-07', 'fail', 'Mike Mhango'),
(8, 32656, 'Road test', '2022-09-20 16:39:01', '2022-09-08', 'fail', 'Mike Mhango'),
(9, 32656, 'Road test', '2022-09-20 16:39:20', '2022-08-31', 'fail', 'Mike Mhango'),
(10, 32655, 'Road test', '2022-09-20 16:40:15', '2022-09-01', 'pass', 'Mike Mhango'),
(11, 32652, 'Road test', '2022-10-06 07:48:52', '2022-10-11', 'pass', 'Mike Mhango');

-- --------------------------------------------------------

--
-- Table structure for table `trail`
--

DROP TABLE IF EXISTS `trail`;
CREATE TABLE IF NOT EXISTS `trail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `trail_to_user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `trail`
--

INSERT INTO `trail` (`id`, `username`, `date`) VALUES
(1, 'ebeleko', '2022-09-20 06:20:18'),
(2, 'mike', '2022-09-01 14:02:59'),
(3, 'mmhango', '2022-09-01 14:05:18'),
(4, 'mike', '2022-09-01 14:06:48'),
(5, 'mike', '2022-09-01 14:08:51'),
(6, 'mmhango', '2022-09-01 14:09:08'),
(7, 'mike', '2022-09-01 15:28:50'),
(8, 'mike', '2022-09-03 14:16:43'),
(9, 'mike', '2022-09-05 08:52:57'),
(10, 'mike', '2022-09-05 08:57:34'),
(11, 'mmhango', '2022-09-05 09:24:15'),
(12, 'mike', '2022-09-05 09:32:18'),
(13, 'mike', '2022-09-05 10:37:42'),
(14, 'mike', '2022-09-05 10:44:05'),
(15, 'mmhango', '2022-09-05 11:37:36'),
(16, 'mike', '2022-09-05 11:40:41'),
(17, 'mmhango', '2022-09-05 11:49:10'),
(18, 'mmhango', '2022-09-06 15:10:50'),
(19, 'mike', '2022-09-06 15:14:35'),
(20, 'davie', '2022-09-06 15:15:07'),
(21, 'mike', '2022-09-06 15:18:42'),
(22, 'davie', '2022-09-06 15:19:27'),
(23, 'mike', '2022-09-06 15:30:28'),
(24, 'mike', '2022-09-06 15:31:21'),
(25, 'mike', '2022-09-20 16:20:58'),
(26, 'mike', '2022-09-20 16:38:24'),
(27, 'mike', '2022-09-20 16:42:44'),
(28, 'mike', '2022-09-20 17:33:45'),
(29, 'mike', '2022-09-20 17:38:22'),
(30, 'mmhango', '2022-09-21 09:01:09'),
(31, 'mike', '2022-09-21 09:04:15'),
(32, 'mike', '2022-09-21 09:15:39'),
(33, 'mike', '2022-09-21 09:53:46'),
(34, 'mike', '2022-10-05 22:41:43'),
(35, 'mike', '2022-10-06 02:36:06'),
(36, 'mike', '2022-10-06 07:39:55'),
(37, 'mike', '2022-10-06 07:41:06'),
(38, 'mike', '2022-10-06 07:48:15'),
(39, 'mike', '2022-10-07 12:44:22'),
(40, 'mike', '2022-10-10 09:46:51'),
(41, 'mike', '2022-10-10 09:49:54'),
(42, 'mike', '2022-10-10 09:52:24'),
(43, 'mike', '2022-10-10 09:54:30'),
(44, 'mike', '2022-10-10 09:58:09'),
(45, 'mike', '2022-10-10 10:00:53'),
(46, 'mike', '2022-10-10 10:02:31'),
(47, 'mike', '2022-10-10 10:04:09'),
(48, 'mike', '2022-10-10 10:09:46'),
(49, 'mike', '2022-10-10 10:11:42'),
(50, 'mike', '2022-10-10 10:13:28'),
(51, 'mike', '2022-10-10 10:16:36'),
(52, 'mike', '2022-10-10 10:17:55'),
(53, 'mike', '2022-10-10 10:19:08'),
(54, 'mike', '2022-10-10 10:20:17'),
(55, 'mike', '2022-10-10 10:23:52'),
(56, 'mike', '2022-10-10 10:26:48'),
(57, 'mike', '2022-10-10 10:29:42'),
(58, 'mike', '2022-10-10 10:31:15'),
(59, 'mike', '2022-10-10 10:35:14'),
(60, 'mike', '2022-10-10 10:37:54'),
(61, 'mike', '2022-10-10 10:48:33'),
(62, 'mike', '2022-10-10 10:50:25'),
(63, 'mike', '2022-10-10 10:59:20'),
(64, 'mike', '2022-10-10 11:00:07'),
(65, 'mike', '2022-10-10 11:03:09'),
(66, 'mike', '2022-10-10 11:17:24'),
(67, 'mike', '2022-10-10 12:15:27'),
(68, 'mike', '2022-10-10 13:13:03'),
(69, 'mike', '2022-10-10 13:15:29'),
(70, 'mike', '2022-10-10 13:41:03'),
(71, 'mike', '2022-10-10 13:42:38'),
(72, 'mike', '2022-10-10 13:44:17'),
(73, 'mike', '2022-10-10 13:53:59'),
(74, 'mike', '2022-10-10 13:55:55'),
(75, 'mike', '2022-10-10 14:14:10'),
(76, 'mike', '2022-10-10 14:24:52'),
(77, 'mike', '2022-10-10 14:35:12'),
(78, 'mike', '2022-10-10 14:37:14'),
(79, 'mike', '2022-10-10 14:40:42'),
(80, 'mike', '2022-10-10 14:45:02'),
(81, 'mike', '2022-10-10 15:07:58'),
(82, 'mike', '2022-10-10 15:25:36'),
(83, 'mike', '2022-10-10 15:27:11'),
(84, 'mike', '2022-10-10 15:35:15'),
(85, 'mike', '2022-10-10 15:50:34'),
(86, 'mike', '2022-10-10 16:38:00'),
(87, 'mike', '2022-10-10 16:48:46'),
(88, 'mike', '2022-10-10 16:51:22'),
(89, 'mike', '2022-10-11 08:35:41'),
(90, 'mike', '2022-10-11 08:49:53'),
(91, 'mike', '2022-10-11 08:52:55'),
(92, 'mike', '2022-10-11 09:01:32'),
(93, 'mike', '2022-10-11 09:04:44'),
(94, 'mike', '2022-10-11 09:07:19'),
(95, 'mike', '2022-10-11 09:09:47'),
(96, 'mike', '2022-10-11 09:15:06'),
(97, 'mike', '2022-10-11 09:39:22'),
(98, 'mike', '2022-10-11 12:45:01'),
(99, 'mike', '2022-10-11 12:46:54'),
(100, 'mike', '2022-10-11 12:48:17'),
(101, 'mike', '2022-10-11 12:52:46'),
(102, 'mike', '2022-10-11 13:08:19'),
(103, 'mike', '2022-10-11 13:16:34'),
(104, 'mike', '2022-10-11 14:25:41'),
(105, 'mike', '2022-10-11 14:36:39'),
(106, 'mike', '2022-10-11 14:55:09');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(60) NOT NULL,
  `date` varchar(30) DEFAULT NULL,
  `password` varchar(16) NOT NULL,
  `role` varchar(20) NOT NULL,
  `image` varchar(300) DEFAULT NULL,
  `username` varchar(30) NOT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`firstname`, `lastname`, `phone`, `email`, `date`, `password`, `role`, `image`, `username`, `isActive`) VALUES
('davie', 'libamba', '03939339', 'davie@g.com', '2022-07-16 09:54:17', '1234', 'Accountant', 'C:\\Users\\ANOYMASS\\Pictures\\149071.png', 'davie', 1),
('eliza', 'beleko', '039393939', 'bele@gmai.com', '2022-07-16 14:00:38', 'beleko', 'Receiptionist', '', 'ebeleko', 1),
('Mike', 'Mhango', '0884799203', 'mikelibamba@gmail.com', '12/12/12', 'Mike123@', 'admin', '\"\"', 'mike', 1),
('Mirriam', 'Mhango', '0993459595', 'mirrie@gmail.com', '2022-08-04 19:25:59', '1234', 'Receptionist', 'C:\\Users\\ANOYMASS\\Pictures\\2015\\IMG-20150113-WA000 - Copy.jpg', 'mmhango', 1),
('zondiwe', 'banda', '0994949494', 'zondi@g.com', '2022-07-16 13:54:31', '1234', 'Other', 'C:\\Users\\ANOYMASS\\Pictures\\2014\\WP_20140522_009.jpg', 'zbanda', 1);

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE IF NOT EXISTS `vehicle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `regNo` varchar(30) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `regNo` (`regNo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`id`, `regNo`, `name`, `date`) VALUES
(4, 'kk1020', 'bongo', '2022-08-22 15:06:44'),
(5, 'MHG1020', 'audii', '2022-08-22 15:08:31');

-- --------------------------------------------------------

--
-- Structure for view `studentsview`
--
DROP TABLE IF EXISTS `studentsview`;

DROP VIEW IF EXISTS `studentsview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `studentsview`  AS SELECT `students`.`fullname` AS `fullname` FROM `students` ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `allocations`
--
ALTER TABLE `allocations`
  ADD CONSTRAINT `allocations_ibfk_1` FOREIGN KEY (`vehicle`) REFERENCES `vehicle` (`regNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `allocations_ibfk_2` FOREIGN KEY (`student`) REFERENCES `students` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `allocations_ibfk_3` FOREIGN KEY (`pending`) REFERENCES `students` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `allocations_ibfk_4` FOREIGN KEY (`staff`) REFERENCES `employees` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `commisions`
--
ALTER TABLE `commisions`
  ADD CONSTRAINT `commisions_ibfk_1` FOREIGN KEY (`employee`) REFERENCES `employees` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `commisions_ibfk_2` FOREIGN KEY (`test`) REFERENCES `tests` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `courseduration`
--
ALTER TABLE `courseduration`
  ADD CONSTRAINT `courseduration_ibfk_1` FOREIGN KEY (`courseID`) REFERENCES `courses` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `courseduration_ibfk_2` FOREIGN KEY (`duration`) REFERENCES `durations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `discounts`
--
ALTER TABLE `discounts`
  ADD CONSTRAINT `discounts_ibfk_1` FOREIGN KEY (`student`) REFERENCES `students` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `expenses`
--
ALTER TABLE `expenses`
  ADD CONSTRAINT `expenses_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`username`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`username`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `payments_ibfk_2` FOREIGN KEY (`expense`) REFERENCES `expenses` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `receipts`
--
ALTER TABLE `receipts`
  ADD CONSTRAINT `receipts_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `students` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `receipts_ibfk_2` FOREIGN KEY (`user`) REFERENCES `users` (`username`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `studentnextofkins`
--
ALTER TABLE `studentnextofkins`
  ADD CONSTRAINT `studentnextofkins_ibfk_1` FOREIGN KEY (`studentID`) REFERENCES `students` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`course`) REFERENCES `courses` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `students_ibfk_2` FOREIGN KEY (`duration`) REFERENCES `durations` (`name`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `tests`
--
ALTER TABLE `tests`
  ADD CONSTRAINT `tests_ibfk_1` FOREIGN KEY (`student`) REFERENCES `students` (`studentID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `trail`
--
ALTER TABLE `trail`
  ADD CONSTRAINT `trail_to_user` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
