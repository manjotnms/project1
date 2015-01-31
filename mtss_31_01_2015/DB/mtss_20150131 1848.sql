-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema mtss
--

CREATE DATABASE IF NOT EXISTS mtss;
USE mtss;

--
-- Definition of table `loginuser`
--

DROP TABLE IF EXISTS `loginuser`;
CREATE TABLE `loginuser` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `userType` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loginuser`
--

/*!40000 ALTER TABLE `loginuser` DISABLE KEYS */;
INSERT INTO `loginuser` (`id`,`username`,`password`,`userType`) VALUES 
 (1,'yashraj','123456','sales');
/*!40000 ALTER TABLE `loginuser` ENABLE KEYS */;


--
-- Definition of table `salesdemo`
--

DROP TABLE IF EXISTS `salesdemo`;
CREATE TABLE `salesdemo` (
  `id` int(10) NOT NULL auto_increment,
  `doNo` varchar(45) NOT NULL,
  `wayBridgeId` decimal(10,0) NOT NULL,
  `purchaserName` varchar(45) NOT NULL,
  `destination` varchar(45) NOT NULL,
  `stateCode` varchar(10) NOT NULL,
  `grade` varchar(10) NOT NULL,
  `doDate` varchar(25) character set latin1 collate latin1_bin NOT NULL,
  `applNo` int(10) unsigned NOT NULL,
  `applDate` varchar(45) character set latin1 collate latin1_bin NOT NULL,
  `doQty` decimal(10,0) NOT NULL,
  `draftNo1` longtext NOT NULL,
  `draftDt1` int(10) unsigned default NULL,
  `draftAmt1` double NOT NULL,
  `bank1` varchar(60) default NULL,
  `draftNo2` longtext,
  `draftDt2` int(11) NOT NULL,
  `draftAmt2` double NOT NULL,
  `bank2` varchar(60) NOT NULL,
  `draftNo3` longtext NOT NULL,
  `draftDt3` int(10) unsigned NOT NULL,
  `draftAmt3` double NOT NULL,
  `bank3` varchar(60) NOT NULL,
  `qtyBalance` varchar(10) NOT NULL,
  `textType` varchar(5) default NULL,
  `custCd` int(10) unsigned NOT NULL,
  `excRegNo` varchar(20) NOT NULL,
  `range` varchar(45) NOT NULL,
  `division` varchar(20) NOT NULL,
  `commission` varchar(30) NOT NULL,
  `vattinNo` longtext NOT NULL,
  `cstNo` varchar(30) NOT NULL,
  `basicRate` decimal(10,0) NOT NULL,
  `pan` varchar(15) NOT NULL,
  `doStartDate` varchar(20) NOT NULL,
  `doEndDate` varchar(20) NOT NULL,
  PRIMARY KEY  USING BTREE (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `salesdemo`
--

/*!40000 ALTER TABLE `salesdemo` DISABLE KEYS */;
INSERT INTO `salesdemo` (`id`,`doNo`,`wayBridgeId`,`purchaserName`,`destination`,`stateCode`,`grade`,`doDate`,`applNo`,`applDate`,`doQty`,`draftNo1`,`draftDt1`,`draftAmt1`,`bank1`,`draftNo2`,`draftDt2`,`draftAmt2`,`bank2`,`draftNo3`,`draftDt3`,`draftAmt3`,`bank3`,`qtyBalance`,`textType`,`custCd`,`excRegNo`,`range`,`division`,`commission`,`vattinNo`,`cstNo`,`basicRate`,`pan`,`doStartDate`,`doEndDate`) VALUES 
 (20,'1234567','123','aaaa','bbbb','123456','w3d',0x33312F30312F32303135,123,0x33312F30312F32303135,'1000','12345',12345,12345,'aaaa','12345',12345,12345,'bbb','12345',12345,12345,'ccc','200',NULL,1111,'1111','222','3333','4444','555','666','12','34','31/01/2015','31/01/2015');
/*!40000 ALTER TABLE `salesdemo` ENABLE KEYS */;


--
-- Definition of table `salesdetail`
--

DROP TABLE IF EXISTS `salesdetail`;
CREATE TABLE `salesdetail` (
  `id` int(11) NOT NULL auto_increment,
  `doNo` varchar(12) NOT NULL,
  `wayBridgeId` decimal(10,0) NOT NULL default '0',
  `purchaserName` varchar(150) default NULL,
  `destination` varchar(15) NOT NULL,
  `stateCode` varchar(8) NOT NULL,
  `grade` varchar(5) NOT NULL,
  `doDate` date NOT NULL,
  `applNo` varchar(10) NOT NULL,
  `applDate` date NOT NULL,
  `doQty` decimal(10,0) NOT NULL default '0',
  `draftNo1` decimal(10,0) unsigned zerofill NOT NULL default '0000000000',
  `draftDt1` decimal(10,0) NOT NULL default '0',
  `draftAmt1` decimal(10,0) unsigned zerofill NOT NULL default '0000000000',
  `bank1` varchar(150) NOT NULL,
  `draftNo2` decimal(10,0) NOT NULL default '0',
  `draftDt2` decimal(10,0) NOT NULL default '0',
  `draftAmt2` decimal(10,0) NOT NULL default '0',
  `bank2` varchar(150) NOT NULL,
  `draftNo3` decimal(10,0) NOT NULL default '0',
  `draftDt3` decimal(10,0) NOT NULL default '0',
  `draftAmt3` decimal(10,0) NOT NULL default '0',
  `bank3` varchar(150) NOT NULL,
  `qtyBalance` decimal(10,0) NOT NULL,
  `textType` varchar(3) NOT NULL,
  `textPercent` varchar(5) NOT NULL,
  `custCd` decimal(10,0) NOT NULL default '0',
  `excRegNo` decimal(10,0) NOT NULL default '0',
  `range` decimal(10,0) NOT NULL default '0',
  `division` decimal(10,0) NOT NULL,
  `commission` decimal(10,0) NOT NULL,
  `vatTinNo` decimal(10,0) NOT NULL,
  `cstNo` decimal(10,0) NOT NULL,
  `basicRate` decimal(10,0) NOT NULL,
  `royalty` decimal(10,0) NOT NULL,
  `sed` decimal(10,0) NOT NULL,
  `cleanEngycess` decimal(10,0) NOT NULL,
  `weighMebt` decimal(10,0) NOT NULL,
  `slc` decimal(10,0) NOT NULL,
  `wrc` decimal(10,0) NOT NULL,
  `bazarFee` decimal(10,0) NOT NULL,
  `pan` decimal(10,0) NOT NULL,
  `centExcRate` decimal(10,0) NOT NULL,
  `eduCessRate` decimal(10,0) NOT NULL,
  `higheduRate` decimal(10,0) default NULL,
  `doStartDate` date NOT NULL,
  `doEndDate` date NOT NULL,
  `roadCess` decimal(10,0) NOT NULL,
  `ambhCess` decimal(10,0) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `salesdetail`
--

/*!40000 ALTER TABLE `salesdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `salesdetail` ENABLE KEYS */;


--
-- Definition of table `tax_and_price`
--

DROP TABLE IF EXISTS `tax_and_price`;
CREATE TABLE `tax_and_price` (
  `id` int(10) unsigned NOT NULL,
  `taxPercent` double NOT NULL,
  `royalty` double NOT NULL,
  `sed` double NOT NULL,
  `cleanEngyCess` double NOT NULL,
  `weighMeBt` double NOT NULL,
  `slc` double NOT NULL,
  `wrc` double NOT NULL,
  `bazarFee` double NOT NULL,
  `centExcRate` double NOT NULL,
  `eduCessRate` double NOT NULL,
  `highEduRate` double NOT NULL,
  `roadCess` double NOT NULL,
  `ambhCess` double NOT NULL,
  `otherCharges` double NOT NULL,
  `doNo` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tax_and_price`
--

/*!40000 ALTER TABLE `tax_and_price` DISABLE KEYS */;
INSERT INTO `tax_and_price` (`id`,`taxPercent`,`royalty`,`sed`,`cleanEngyCess`,`weighMeBt`,`slc`,`wrc`,`bazarFee`,`centExcRate`,`eduCessRate`,`highEduRate`,`roadCess`,`ambhCess`,`otherCharges`,`doNo`) VALUES 
 (8,1,2,7,8,2,8,7,6,2,1,3,6,2,0,'123456'),
 (9,1,1,1,1,1,1,1,1,1,1,1,1,1,0,'1234567');
/*!40000 ALTER TABLE `tax_and_price` ENABLE KEYS */;


--
-- Definition of table `taxesdetails`
--

DROP TABLE IF EXISTS `taxesdetails`;
CREATE TABLE `taxesdetails` (
  `id` int(10) unsigned NOT NULL,
  `doNo` varchar(45) NOT NULL,
  `taxesDetails` varchar(120) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `taxesdetails`
--

/*!40000 ALTER TABLE `taxesdetails` DISABLE KEYS */;
INSERT INTO `taxesdetails` (`id`,`doNo`,`taxesDetails`) VALUES 
 (20,'1234567','WLC:1;XYZ:1;ABC:2;CESS:1;ROYAL:1;AAA:1;');
/*!40000 ALTER TABLE `taxesdetails` ENABLE KEYS */;


--
-- Definition of table `taxnames`
--

DROP TABLE IF EXISTS `taxnames`;
CREATE TABLE `taxnames` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `taxName` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `taxnames`
--

/*!40000 ALTER TABLE `taxnames` DISABLE KEYS */;
INSERT INTO `taxnames` (`id`,`taxName`) VALUES 
 (1,'wlc'),
 (2,'xyz'),
 (5,'Royal'),
 (8,'null'),
 (9,'null'),
 (10,'gngm');
/*!40000 ALTER TABLE `taxnames` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
