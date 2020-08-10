-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 09, 2020 at 08:41 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.2.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `haim`
--

-- --------------------------------------------------------

--
-- Table structure for table `retailtype`
--

CREATE TABLE `retailtype` (
  `Guid` int(11) NOT NULL,
  `Name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `retailtype`
--

INSERT INTO `retailtype` (`Guid`, `Name`) VALUES
(1, 'None'),
(2, 'HouseHold'),
(3, 'Food'),
(4, 'Fun'),
(5, 'Clothes'),
(6, 'Sports');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `Guid` int(11) NOT NULL,
  `IsIncome` tinyint(1) NOT NULL,
  `Price` float NOT NULL,
  `Description` text NOT NULL,
  `RetailGuid` int(11) NOT NULL,
  `DateOfTransaction` date NOT NULL,
  `UserGuid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`Guid`, `IsIncome`, `Price`, `Description`, `RetailGuid`, `DateOfTransaction`, `UserGuid`) VALUES
(287, 0, 150, 'Market weekly shopping', 3, '2020-08-01', 8),
(288, 0, 37, 'At the movies', 4, '2020-08-09', 8),
(289, 1, 15, 'for a pizza i ordered for a friend', 3, '2020-08-04', 8),
(290, 0, 150, 'new boots', 6, '2020-07-25', 8),
(291, 0, 65, 'shirt', 5, '2020-08-09', 8),
(292, 1, 10, 'shirt', 5, '2020-08-09', 9);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `Guid` int(11) NOT NULL,
  `UserName` varchar(30) NOT NULL,
  `Email` text NOT NULL,
  `Password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Guid`, `UserName`, `Email`, `Password`) VALUES
(1, 'None', '', '456'),
(2, 'Achi', 'Achi@', 'abc'),
(4, 'Haim', 'Haim@', 'abc 123'),
(5, 'Nir', 'Nir@', '123'),
(7, 'Oren', 'Oren@', '  '),
(8, 'testUser', 'testMail@email.com', 'testPass'),
(9, 'test2', '1@a.com', '123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `retailtype`
--
ALTER TABLE `retailtype`
  ADD PRIMARY KEY (`Guid`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`Guid`),
  ADD KEY `UserGuidRelation` (`UserGuid`),
  ADD KEY `RetailGuidRelation` (`RetailGuid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Guid`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `RetailGuidRelation` FOREIGN KEY (`RetailGuid`) REFERENCES `retailtype` (`Guid`),
  ADD CONSTRAINT `UserGuidRelation` FOREIGN KEY (`UserGuid`) REFERENCES `user` (`Guid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
