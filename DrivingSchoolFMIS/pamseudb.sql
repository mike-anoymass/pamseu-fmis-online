-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Oct 20, 2022 at 05:58 AM
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
(1, 2);

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
(1, 3, 10000, 1, '2022-10-14 09:02:52'),
(2, 5, 8000, 1, '2022-10-14 09:02:52');

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
(22, 12, '2022-08-05 00:12:59', 175000, NULL),
(22, 13, '2022-08-05 00:12:25', 200000, NULL),
(23, 13, '2022-08-17 14:13:57', 100000, NULL),
(24, 12, '2022-10-14 08:48:06', 200000, NULL),
(24, 13, '2022-10-14 08:47:50', 400000, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`code`, `name`, `id`, `governmentFee`) VALUES
('b', 'light goods', 22, 40000),
('a1', 'motocycle', 23, 30000),
('c1', 'Heavy goods', 24, 56000);

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
('half course', 20, 12),
('full course', 40, 13);

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `expense`, `date`, `amount`, `mode`, `user`, `ref`, `dateOfPayment`, `mirage`) VALUES
(1, 'stationery', '2022-08-13 17:05:23', 100000, 'TNM Mpamba', NULL, 'djd9929', '2022-08-31', 'kaya man'),
(3, 'bundle', '2022-09-05 11:49:57', 100000, 'Cheque', NULL, 'ak822839949', '2022-09-12', 'for september'),
(4, 'stationery', '2022-09-06 15:16:48', 50000, 'Cash', NULL, '', '2022-09-21', 'for october'),
(5, 'rent', '2022-09-06 15:20:52', 40000, 'Cheque', NULL, 'dkdkdkf99393030', '2022-09-28', 'for september'),
(6, 'stationery', '2022-09-21 09:56:25', 200000, 'Cash', 'mike', '', '2022-10-04', 'for october'),
(7, 'bundle', '2022-10-14 07:49:10', 10000, NULL, 'mike', '', '2022-10-11', 'oct'),
(8, 'rent', '2022-10-14 07:56:12', 100000, NULL, 'mike', '', '2022-10-04', 'oct');

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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `receipts`
--

INSERT INTO `receipts` (`receiptNo`, `studentID`, `date`, `amount`, `modeOfPayment`, `paymentOf`, `user`, `reference`, `dateOfPayment`) VALUES
(20, 32659, '2022-10-14 09:06:15', 100000, 'Cash', 'Fees', 'mike', '', '2022-10-05'),
(21, 32659, '2022-10-19 10:24:02', 100000, 'Cash', 'Fees', 'mike', '1029293393ka', '2022-10-04');

-- --------------------------------------------------------

--
-- Table structure for table `security_key`
--

DROP TABLE IF EXISTS `security_key`;
CREATE TABLE IF NOT EXISTS `security_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `security_key` text NOT NULL,
  `admin_email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `security_key`
--

INSERT INTO `security_key` (`id`, `security_key`, `admin_email`) VALUES
(1, 'qnkvrswrtbvisavm', 'mikelibamba@gmail.com');

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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `studentnextofkins`
--

INSERT INTO `studentnextofkins` (`id`, `studentID`, `phone`, `physicalAddress`, `name`) VALUES
(28, 32659, '3030304040', 'kawale', 'banda ');

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
) ENGINE=InnoDB AUTO_INCREMENT=32660 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`studentID`, `fullname`, `postalAddress`, `phone`, `course`, `date`, `duration`, `gender`, `trn`, `dateRegistered`, `anyDiscount`, `anyGovernmentFee`, `graduated`, `addedBy`, `updatedOn`) VALUES
(32649, 'dean', 'sksks	', '03939949', 'b', '2022-08-17 11:31:18', 'full course', 'male', NULL, '2022-08-09', 0, 0, 0, 'Mirriam Mhango', '2022-08-17 11:31:18'),
(32659, 'john banda', 'kawale', '0949494949', 'c1', '2022-10-14 08:52:15', 'full course', 'male', NULL, '2022-10-14', 0, 0, 0, 'Mike Mhango', '2022-10-14 08:52:15');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tests`
--

INSERT INTO `tests` (`id`, `student`, `testName`, `date`, `dateOfTest`, `passOrFail`, `user`) VALUES
(1, 32659, 'Road test', '2022-10-14 08:59:22', '2022-10-13', 'pass', 'Mike Mhango'),
(2, 32659, 'Road test', '2022-10-14 09:02:08', '2022-10-04', 'pass', 'Mike Mhango');

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
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `trail`
--

INSERT INTO `trail` (`id`, `username`, `date`) VALUES
(2, 'mike', '2022-09-01 14:02:59'),
(4, 'mike', '2022-09-01 14:06:48'),
(5, 'mike', '2022-09-01 14:08:51'),
(7, 'mike', '2022-09-01 15:28:50'),
(8, 'mike', '2022-09-03 14:16:43'),
(9, 'mike', '2022-09-05 08:52:57'),
(10, 'mike', '2022-09-05 08:57:34'),
(12, 'mike', '2022-09-05 09:32:18'),
(13, 'mike', '2022-09-05 10:37:42'),
(14, 'mike', '2022-09-05 10:44:05'),
(16, 'mike', '2022-09-05 11:40:41'),
(19, 'mike', '2022-09-06 15:14:35'),
(21, 'mike', '2022-09-06 15:18:42'),
(23, 'mike', '2022-09-06 15:30:28'),
(24, 'mike', '2022-09-06 15:31:21'),
(25, 'mike', '2022-09-20 16:20:58'),
(26, 'mike', '2022-09-20 16:38:24'),
(27, 'mike', '2022-09-20 16:42:44'),
(28, 'mike', '2022-09-20 17:33:45'),
(29, 'mike', '2022-09-20 17:38:22'),
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
(106, 'mike', '2022-10-11 14:55:09'),
(108, 'mike', '2022-10-12 09:22:05'),
(109, 'mike', '2022-10-12 10:15:54'),
(110, 'mike', '2022-10-12 10:44:30'),
(111, 'mike', '2022-10-12 13:28:08'),
(112, 'mike', '2022-10-12 13:39:45'),
(113, 'mike', '2022-10-12 13:57:43'),
(114, 'mike', '2022-10-12 13:59:03'),
(115, 'mike', '2022-10-12 14:05:47'),
(116, 'mike', '2022-10-12 14:11:30'),
(117, 'mike', '2022-10-12 14:14:01'),
(118, 'mike', '2022-10-12 14:16:24'),
(119, 'mike', '2022-10-12 14:20:25'),
(120, 'mike', '2022-10-12 14:22:15'),
(121, 'mike', '2022-10-12 16:22:40'),
(122, 'mike', '2022-10-13 11:18:37'),
(123, 'mike', '2022-10-13 11:32:43'),
(124, 'mike', '2022-10-13 11:38:21'),
(125, 'mike', '2022-10-13 11:47:59'),
(126, 'mike', '2022-10-13 11:51:37'),
(127, 'mike', '2022-10-13 12:28:32'),
(128, 'mike', '2022-10-14 07:48:26'),
(129, 'mike', '2022-10-14 07:55:38'),
(130, 'mike', '2022-10-14 08:38:27'),
(131, 'mike', '2022-10-14 08:38:28'),
(132, 'davie', '2022-10-14 08:53:33'),
(133, 'mike', '2022-10-14 08:55:35'),
(134, 'mike', '2022-10-18 17:03:13'),
(135, 'mike', '2022-10-18 17:41:43'),
(136, 'mike', '2022-10-18 17:44:08'),
(137, 'mike', '2022-10-18 17:45:12'),
(138, 'mike', '2022-10-18 17:49:06'),
(139, 'mike', '2022-10-18 17:50:42'),
(140, 'mike', '2022-10-18 17:52:45'),
(141, 'mike', '2022-10-18 17:56:14'),
(142, 'mike', '2022-10-18 17:57:38'),
(143, 'mike', '2022-10-19 10:23:27'),
(144, 'mike', '2022-10-19 19:28:44'),
(145, 'mike', '2022-10-19 19:29:50'),
(146, 'mike', '2022-10-19 19:31:51'),
(147, 'mike', '2022-10-19 19:35:45'),
(148, 'mike', '2022-10-19 19:38:39'),
(149, 'mike', '2022-10-19 19:39:33'),
(150, 'mike', '2022-10-19 19:40:47'),
(151, 'mike', '2022-10-19 19:48:23'),
(152, 'mike', '2022-10-19 19:50:33'),
(153, 'mike', '2022-10-19 19:52:08'),
(154, 'mike', '2022-10-19 19:53:16'),
(155, 'mike', '2022-10-19 19:57:53'),
(156, 'mike', '2022-10-19 19:59:06'),
(157, 'mike', '2022-10-19 20:01:35');

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
('davies', 'mhago', '08833939', 'davie@gmail.com', '2022-10-14 08:43:08', '1234', 'Receptionist', 'C:\\Users\\ANOYMASS\\Pictures\\149071.png', 'davie', 1),
('Mike', 'Mhango', '0884799203', 'mikelibamba@gmail.com', '12/12/12', 'Mike123@', 'admin', '\"\"', 'mike', 1),
('mirrie', 'banda', '9393939040', 'mirrie@gmai.com', '2022-10-14 08:44:14', '1234', 'Accountant', '', 'mirrie', 1);

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE IF NOT EXISTS `vehicle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `regNo` varchar(30) NOT NULL,
  `insurance_date` varchar(40) NOT NULL,
  `cof_date` varchar(40) NOT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `regNo` (`regNo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`id`, `regNo`, `insurance_date`, `cof_date`, `date`) VALUES
(7, 'KB1212', '2022-10-21', '2022-10-22', '2022-10-18 17:46:14');

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
