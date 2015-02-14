-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema mtss
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mtss
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mtss` DEFAULT CHARACTER SET latin1 ;
USE `mtss` ;

-- -----------------------------------------------------
-- Table `mtss`.`taxdetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`taxdetails` ;

CREATE TABLE IF NOT EXISTS `mtss`.`taxdetails` (
  `id` INT(10) UNSIGNED NOT NULL,
  `taxPercent` DECIMAL(5,2) NULL DEFAULT NULL,
  `royalty` DECIMAL(5,2) NULL DEFAULT NULL,
  `sed` DECIMAL(5,2) NULL DEFAULT NULL,
  `cleanEngyCess` DECIMAL(5,2) NULL DEFAULT NULL,
  `weighMeBt` DECIMAL(5,2) NULL DEFAULT NULL,
  `slc` DECIMAL(5,2) NULL DEFAULT NULL,
  `wrc` DECIMAL(5,2) NULL DEFAULT NULL,
  `bazarFee` DECIMAL(5,2) NULL DEFAULT NULL,
  `centExcRate` DECIMAL(5,2) NULL DEFAULT NULL,
  `eduCessRate` DECIMAL(5,2) NULL DEFAULT NULL,
  `highEduRate` DECIMAL(5,2) NULL DEFAULT NULL,
  `roadCess` DECIMAL(5,2) NULL DEFAULT NULL,
  `ambhCess` DECIMAL(5,2) NULL DEFAULT NULL,
  `otherCharges` DECIMAL(5,2) NULL DEFAULT NULL,
  `doNo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `mtss`.`deliveryorderdetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`deliveryorderdetails` ;

CREATE TABLE IF NOT EXISTS `mtss`.`deliveryorderdetails` (
  `id` INT(11) NOT NULL,
  `doNo` VARCHAR(45) NOT NULL,
  `wayBridgeId` INT(11) NOT NULL,
  `purchaserName` VARCHAR(45) NOT NULL,
  `destination` VARCHAR(45) NOT NULL,
  `stateCode` VARCHAR(10) NOT NULL,
  `grade` VARCHAR(10) NOT NULL,
  `doDate` DATETIME NOT NULL,
  `applNo` INT(10) UNSIGNED NOT NULL,
  `applDate` DATETIME NOT NULL,
  `doQty` BIGINT(20) NOT NULL,
  `draftNo1` LONGTEXT NOT NULL,
  `draftDt1` DATETIME NULL DEFAULT NULL,
  `draftAmt1` DOUBLE NOT NULL,
  `draftNo2` LONGTEXT NULL DEFAULT NULL,
  `draftDt2` DATETIME NOT NULL,
  `draftAmt2` DOUBLE NOT NULL,
  `bank2` VARCHAR(60) NOT NULL,
  `draftNo3` LONGTEXT NOT NULL,
  `draftDt3` DATETIME NOT NULL,
  `draftAmt3` DOUBLE NOT NULL,
  `bank3` VARCHAR(60) NOT NULL,
  `QtyBalance` BIGINT(20) NOT NULL,
  `range` VARCHAR(45) NOT NULL,
  `division` VARCHAR(20) NOT NULL,
  `commission` VARCHAR(30) NOT NULL,
  `vattinNo` LONGTEXT NOT NULL,
  `cstNo` VARCHAR(30) NOT NULL,
  `basicRate` DECIMAL(10,5) NOT NULL,
  `pan` VARCHAR(15) NOT NULL,
  `doStartDate` DATETIME NOT NULL,
  `doEndDate` DATETIME NOT NULL,
  `tax_and_price_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY USING BTREE (`id`),
  INDEX `fk_salesdemo_tax_and_price_idx` (`tax_and_price_id` ASC),
  CONSTRAINT `fk_salesdemo_tax_and_price`
    FOREIGN KEY (`tax_and_price_id`)
    REFERENCES `mtss`.`taxdetails` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `mtss`.`GpsInventory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`GpsInventory` ;

CREATE TABLE IF NOT EXISTS `mtss`.`GpsInventory` (
  `IMEI` INT(20) NOT NULL,
  `Avail` TINYINT(1) NULL DEFAULT NULL,
  `InUse` TINYINT(1) NULL DEFAULT NULL,
  `DateofIssue` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`IMEI`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mtss`.`EpcInventory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`EpcInventory` ;

CREATE TABLE IF NOT EXISTS `mtss`.`EpcInventory` (
  `EpcNo` INT(11) NOT NULL,
  `DateofIssue` DATETIME NULL,
  `Avail` TINYINT(1) NULL,
  `InUse` TINYINT(1) NULL DEFAULT NULL,
  `ActivityNo` INT(11) NULL DEFAULT NULL,
  `EpcType` INT NULL DEFAULT NULL,
  PRIMARY KEY (`EpcNo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mtss`.`DispatchExternal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`DispatchExternal` ;

CREATE TABLE IF NOT EXISTS `mtss`.`DispatchExternal` (
  `ActivityNo` INT(11) NOT NULL AUTO_INCREMENT,
  `DoNo` VARCHAR(45) NOT NULL,
  `VechNo` VARCHAR(45) NOT NULL,
  `IssueTime` DATETIME NULL,
  `RLW` BIGINT(20) NOT NULL,
  `EpcNo` INT(11) NOT NULL,
  `IMEI` INT(20) NOT NULL,
  `FirstWeightTime` DATETIME NULL DEFAULT NULL,
  `FirstWeight` BIGINT(20) NULL DEFAULT NULL,
  `SecondWeightTime` DATETIME NULL DEFAULT NULL,
  `SecondWeight` BIGINT(20) NULL DEFAULT NULL,
  `NetWeight` BIGINT(20) NULL DEFAULT NULL,
  `FirstImageFront` LONGBLOB NULL DEFAULT NULL,
  `FirstImageMiddle` LONGBLOB NULL DEFAULT NULL,
  `FirstImageRear` LONGBLOB NULL DEFAULT NULL,
  `SecondImageFront` LONGBLOB NULL DEFAULT NULL,
  `SecondImageMiddle` LONGBLOB NULL DEFAULT NULL,
  `SecondImageRear` LONGBLOB NULL DEFAULT NULL,
  `ReturnTime` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`ActivityNo`),
  INDEX `FK1_idx` (`IMEI` ASC),
  INDEX `FK2_idx` (`EpcNo` ASC),
  CONSTRAINT `FK1`
    FOREIGN KEY (`IMEI`)
    REFERENCES `mtss`.`GpsInventory` (`IMEI`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK2`
    FOREIGN KEY (`EpcNo`)
    REFERENCES `mtss`.`EpcInventory` (`EpcNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mtss`.`invoicedetail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`invoicedetail` ;

CREATE TABLE IF NOT EXISTS `mtss`.`invoicedetail` (
  `InvoiceNo` VARCHAR(45) NOT NULL,
  `BasicAmount` DECIMAL(20,10) NULL DEFAULT NULL,
  `Royalty` DECIMAL(20,10) NULL DEFAULT NULL,
  `Exicse` DECIMAL(20,10) NULL DEFAULT NULL,
  `EducationCess` DECIMAL(20,10) NULL DEFAULT NULL,
  `SecEducationCess` DECIMAL(20,10) NULL DEFAULT NULL,
  `VAT1` DECIMAL(20,10) NULL DEFAULT NULL,
  `VAT2` DECIMAL(20,10) NULL DEFAULT NULL,
  `StowExicse` DECIMAL(20,10) NULL DEFAULT NULL,
  `SizingCharge` DECIMAL(20,10) NULL DEFAULT NULL,
  `SurfaceTransCharge` DECIMAL(20,10) NULL DEFAULT NULL,
  `TotalAmount` DECIMAL(20,10) NULL DEFAULT NULL,
  `ActivityNo` INT(20) NOT NULL DEFAULT '0',
  `ChallanNo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ActivityNo`),
  INDEX `FK5_idx` (`ActivityNo` ASC),
  CONSTRAINT `FK5`
    FOREIGN KEY (`ActivityNo`)
    REFERENCES `mtss`.`DispatchExternal` (`ActivityNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mtss`.`loginuser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`loginuser` ;

CREATE TABLE IF NOT EXISTS `mtss`.`loginuser` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `userType` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `mtss`.`othergood`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`othergood` ;

CREATE TABLE IF NOT EXISTS `mtss`.`othergood` (
  `OtherActivityNo` INT(11) NOT NULL AUTO_INCREMENT,
  `DateTimeIssue` DATETIME NULL,
  `Weight1` BIGINT NULL,
  `QuantityType` VARCHAR(45) NULL DEFAULT NULL,
  `Weight2` BIGINT NULL DEFAULT NULL,
  `NetWeight` BIGINT NULL DEFAULT NULL,
  `DateReturn` DATETIME NULL DEFAULT NULL,
  `ChallenNo` VARCHAR(45) NULL,
  PRIMARY KEY (`OtherActivityNo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `mtss`.`productiondetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`productiondetails` ;

CREATE TABLE IF NOT EXISTS `mtss`.`productiondetails` (
  `ProductionID` INT(11) NOT NULL AUTO_INCREMENT,
  `VechNo` VARCHAR(45) NULL DEFAULT NULL,
  `TotalProd` BIGINT(20) NULL DEFAULT NULL,
  `Date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ProductionID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `mtss`.`productionvechdetail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`productionvechdetail` ;

CREATE TABLE IF NOT EXISTS `mtss`.`productionvechdetail` (
  `Id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `EPCNo` INT(11) UNSIGNED NULL DEFAULT NULL,
  `VechNo` VARCHAR(45) NULL DEFAULT NULL,
  `TareWeight` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `mtss`.`productionws1`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`productionws1` ;

CREATE TABLE IF NOT EXISTS `mtss`.`productionws1` (
  `TransactionId` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `VechNo` VARCHAR(45) NULL DEFAULT NULL,
  `NetWeight` BIGINT(20) NULL DEFAULT NULL,
  `Date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`TransactionId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `mtss`.`productionws2`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`productionws2` ;

CREATE TABLE IF NOT EXISTS `mtss`.`productionws2` (
  `TransactionId` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `VechNo` VARCHAR(45) NULL DEFAULT NULL,
  `NetWeight` BIGINT(20) NULL DEFAULT NULL,
  `Date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`TransactionId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `mtss`.`taxesdetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`taxesdetails` ;

CREATE TABLE IF NOT EXISTS `mtss`.`taxesdetails` (
  `id` INT(10) UNSIGNED NOT NULL,
  `doNo` VARCHAR(45) NOT NULL,
  `taxesDetails` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `mtss`.`taxnames`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`taxnames` ;

CREATE TABLE IF NOT EXISTS `mtss`.`taxnames` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `taxName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `mtss`.`useractivity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`useractivity` ;

CREATE TABLE IF NOT EXISTS `mtss`.`useractivity` (
  `ActivityId` INT(11) NOT NULL,
  `UserId` INT(10) UNSIGNED NULL DEFAULT NULL,
  `LoginDate` DATETIME NULL DEFAULT NULL,
  `LogoutDate` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`ActivityId`),
  INDEX `FK3_idx` (`UserId` ASC),
  CONSTRAINT `FK3`
    FOREIGN KEY (`UserId`)
    REFERENCES `mtss`.`loginuser` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `mtss`.`intercoaltransfer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`intercoaltransfer` ;

CREATE TABLE IF NOT EXISTS `mtss`.`intercoaltransfer` (
  `DNo` INT NOT NULL,
  `DateEntry` DATETIME NULL,
  `Quantity` BIGINT NULL,
  `Source` VARCHAR(45) NULL,
  `Destination` VARCHAR(45) NULL,
  `Type` INT NULL,
  PRIMARY KEY (`DNo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mtss`.`internalcoaltrans`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mtss`.`internalcoaltrans` ;

CREATE TABLE IF NOT EXISTS `mtss`.`internalcoaltrans` (
  `ActivityNo` INT(11) NOT NULL,
  `DoNo` INT NULL,
  `EpcNo` INT(11) NULL,
  `IMEIno` INT(20) NULL,
  `FirstWeight` BIGINT NULL,
  `FirstWeightTime` DATETIME NULL,
  `SecondWeight` BIGINT NULL,
  `SecondWeightTime` DATETIME NULL,
  `Netweight` BIGINT NULL,
  PRIMARY KEY (`ActivityNo`),
  INDEX `FK10_idx` (`EpcNo` ASC),
  INDEX `FK11_idx` (`IMEIno` ASC),
  CONSTRAINT `FK10`
    FOREIGN KEY (`EpcNo`)
    REFERENCES `mtss`.`EpcInventory` (`EpcNo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK11`
    FOREIGN KEY (`IMEIno`)
    REFERENCES `mtss`.`GpsInventory` (`IMEI`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
