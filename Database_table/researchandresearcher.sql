-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 20, 2021 at 07:47 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `researchandresearcher`
--

-- --------------------------------------------------------

--
-- Table structure for table `proposal`
--

CREATE TABLE `proposal` (
  `proposal_ID` int(11) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `submit_date` datetime NOT NULL,
  `research_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `proposal`
--

INSERT INTO `proposal` (`proposal_ID`, `description`, `submit_date`, `research_ID`) VALUES
(2, 'New seat heating system with engine bleed air.', '2021-04-19 22:45:10', 4),
(3, 'Electric SUV model with high efficiency.', '2021-04-19 22:47:02', 2),
(4, 'Patient satisfaction about govenment holpitals in Sri Lanka', '2021-04-19 22:47:46', 5),
(5, 'AI Robot for resaurents.', '2021-04-19 22:49:08', 6);

-- --------------------------------------------------------

--
-- Table structure for table `research`
--

CREATE TABLE `research` (
  `research_ID` int(11) NOT NULL,
  `research_topic` varchar(200) NOT NULL,
  `research_area` varchar(200) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `progress` varchar(45) DEFAULT NULL,
  `stakeholder_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `research`
--

INSERT INTO `research` (`research_ID`, `research_topic`, `research_area`, `status`, `progress`, `stakeholder_ID`) VALUES
(2, 'Electric SUV', 'AutoMobile', 'Stage 2 in progress', 'Stage1', 3),
(4, 'New Seat Heating System', 'Aviation', 'Searching for funding bodies', NULL, 8),
(5, 'Patient satisfaction in Sri Lanka', 'HealthCare', 'Stage 3 in progress', 'Stage2', 4),
(6, 'AI Robot', 'InfromationTechnology', 'Searching for funding bodies', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `researcher`
--

CREATE TABLE `researcher` (
  `stakeholder_ID` int(11) NOT NULL,
  `name` varchar(500) NOT NULL,
  `email` varchar(100) NOT NULL,
  `address` varchar(300) NOT NULL,
  `phone_no` varchar(10) NOT NULL,
  `interest_area` varchar(200) NOT NULL,
  `researcher_type` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `researcher`
--

INSERT INTO `researcher` (`stakeholder_ID`, `name`, `email`, `address`, `phone_no`, `interest_area`, `researcher_type`) VALUES
(1, 'Chandrakumara', 'chandrakumara@gmail.com', 'Colombo', '0778767654', 'InfromationTechnology', 'Individual'),
(2, 'Axil Innovations', 'axilinnovatios@gmail.com', 'Colombo', '0715654543', 'Construction', 'Organization'),
(3, 'Vega Innovations', 'vegainnovatios@gmail.com', 'Colombo', '0753452415', 'AutoMobile', 'Organization'),
(4, 'Sasanka Kulasekara', 'sasankakulasekara@gmail.com', 'Colombo', '0716633610', 'HealthCare', 'Individual'),
(8, 'Kavishka Jayasekara', 'kavishka@gmail.com', 'Colombo', '0753324321', 'Aviation', 'Individual');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `proposal`
--
ALTER TABLE `proposal`
  ADD PRIMARY KEY (`proposal_ID`),
  ADD KEY `research_ID` (`research_ID`);

--
-- Indexes for table `research`
--
ALTER TABLE `research`
  ADD PRIMARY KEY (`research_ID`),
  ADD KEY `stakeholder_ID` (`stakeholder_ID`);

--
-- Indexes for table `researcher`
--
ALTER TABLE `researcher`
  ADD PRIMARY KEY (`stakeholder_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `proposal`
--
ALTER TABLE `proposal`
  MODIFY `proposal_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `research`
--
ALTER TABLE `research`
  MODIFY `research_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `researcher`
--
ALTER TABLE `researcher`
  MODIFY `stakeholder_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `proposal`
--
ALTER TABLE `proposal`
  ADD CONSTRAINT `proposal_ibfk_1` FOREIGN KEY (`research_ID`) REFERENCES `research` (`research_ID`);

--
-- Constraints for table `research`
--
ALTER TABLE `research`
  ADD CONSTRAINT `research_ibfk_1` FOREIGN KEY (`stakeholder_ID`) REFERENCES `researcher` (`stakeholder_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
