DROP DATABASE IF EXISTS `AppMagazine`;
CREATE DATABASE `AppMagazine`;
USE `AppMagazine`;

/*** Object: Table `Account`  Script Date: 12/09/2013 ****/
CREATE TABLE `Account`(
	`AccountId`	INT NOT NULL PRIMARY KEY,
	`PhoneNumber` VARCHAR(100) NOT NULL,
	`ActiveCode`  INT NOT NULL,
	`Status` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `Question` Script Date: 09/30/2013 17:41:49 ******/
CREATE TABLE `Question`(
	`QuestionId` INT PRIMARY KEY,
	`QuestionContent` VARCHAR(1000) NOT NULL,
	`Status` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `Answer` Script Date: 09/30/2013 17:41:49 ******/
CREATE TABLE `Answer`(
	`AnswerId` INT PRIMARY KEY NOT NULL,
	`QuestionId` INT NOT NULL,
	`AnswerContent` VARCHAR(1000) NOT NULL,
	`Status` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `AccountAnswer` Script Date: 09/30/2013 17:41:49 ******/
CREATE TABLE `AccountAnswer`(
	`AccountId`	INT NOT NULL ,
	`AnswerId` INT NOT NULL,
	`Status` INT NOT NULL,
	PRIMARY KEY(AccountId, AnswerId)
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `AccountApp` Script Date: 09/30/2013 17:41:49 ******/
CREATE TABLE `AccountApp`(
	`AccountAppId` INT NOT NULL PRIMARY KEY,
	`AccountId`	INT NOT NULL,
	`ProductId` INT NOT NULL,
	`Status` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `InstallingApp` Script Date: 09/30/2013 17:41:49 ******/
CREATE TABLE `InstallingApp`(
	`InstallingAppId` INT NOT NULL PRIMARY KEY,
	`AccountId`	INT NOT NULL,
	`PackageName` VARCHAR(250) NOT NULL,
	`Status` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `Product` Script Date: 09/30/2013 17:41:49 ******/
CREATE TABLE `Product`(
	`ProductId` INT NOT NULL PRIMARY KEY,
	`PublisherId` INT NOT NULL,
	`ProductName` VARCHAR(250) NOT NULL,
	`Description` TEXT NOT NULL,
	`Price` FLOAT NOT NULL,
	`Package` VARCHAR(250) UNIQUE NOT NULL,
	`Rate` FLOAT NOT NULL,
	`RateNumber` INT NOT NULL,	
	`IconId` INT NOT NULL,
	`BannerId` INT NOT NULL,
	`ViewNumber` INT NOT NULL,	
	`DateCreated` DATETIME NOT NULL,
	`LastUpdated` DATETIME NOT NULL,
	`Creator` INT NOT NULL,
	`Status` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `Image` Script Date: 09/30/2013 17:41:49 ******/
CREATE TABLE `Image`(
	`ImageId` INT NOT NULL PRIMARY KEY,
	`Path` VARCHAR(500) NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `Product_Category` Script Date: 09/30/2013 17:41:49 ******/
CREATE TABLE `Product_Category`(
	`ProductId` INT NOT NULL,
	`CategoryId` INT NOT NULL,
	PRIMARY KEY(ProductId, CategoryId)
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `Category` Script Date: 09/30/2013 17:41:49 ******/
CREATE TABLE `Category`(
	`CategoryId` INT NOT NULL PRIMARY KEY ,
	`ParentCategoryId` INT NULL,
	`CategoryName` VARCHAR(50) NOT NULL,
	`Priority` INT NOT NULL,
	`Type` INT NOT NULL,
	`Status` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `ProductFile` Script Date: 06/25/2013 17:41:49 ******/
CREATE TABLE `ProductFile`(
	`FileId` INT NOT NULL PRIMARY KEY,
	`ProductId` INT NOT NULL,
	`FileVersionId` INT NOT NULL,
	`MinimumOSVersion` INT NOT NULL,
	`FilePath` VARCHAR(500) NOT NULL,
	`FileSize` BIGINT NOT NULL,
	`Trailer` VARCHAR(500) NOT NULL,
	`Status` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `FileVersion` Script Date: 06/25/2013 17:41:49 ******/
CREATE TABLE `FileVersion`(
	`FileVersionId` INT NOT NULL PRIMARY KEY ,
	`ProductId` INT NOT NULL,
	`FileVersionName` VARCHAR(50) NOT NULL,
	`FileVersionCode` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `ScreenShot` Script Date: 06/25/2013 17:41:49 ******/
CREATE TABLE `ScreenShot`(
	`ScreenShotId` INT NOT NULL PRIMARY KEY ,
	`FileId` INT NOT NULL,
	`ImageId` INT NOT NULL,
	`Type` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `FileData` Script Date: 06/25/2013 17:41:49 ******/
CREATE TABLE `FileData`(
	`FileDataId` INT NOT NULL PRIMARY KEY,
	`FileId` INT NOT NULL,
	`FileName` VARCHAR(500) NOT NULL,
	`FileSize` BIGINT NOT NULL,
	`Price` FLOAT NOT NULL,
	`FilePath` VARCHAR(500) NOT NULL,
	`InstallGuide` TEXT NOT NULL,
	`Status` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;
/****** Object: Table `OSVersion` Script Date: 06/25/2013 17:41:49 ******/
CREATE TABLE `OSVersion`(
	`VersionId` INT NOT NULL PRIMARY KEY,
	`OSId` INT NOT NULL,
	`VersionCode` INT NOT NULL,
	`VersionName` VARCHAR(150) NOT NULL,
	`BriefDesc` TEXT NULL,
	`Status` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;
/****** Object: Table `OperatingSystem` Script Date: 06/25/2013 17:41:49 ******/
CREATE TABLE `OperatingSystem`(
	`OSId` INT NOT NULL PRIMARY KEY,
	`OSName` VARCHAR(150) NOT NULL,
	`Status` INT NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: Table `ProductTag` Script Date: 06/25/2013 17:41:49 ******/
CREATE TABLE `ProductTag`(
	`TagId` INT NOT NULL PRIMARY KEY,
	`ProductId` INT NOT NULL,
	`Tag` VARCHAR(250) NOT NULL
)CHARACTER SET utf8 COLLATE utf8_general_ci;

/****** Object: ForeignKey `FK_AccountApp_Account` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `AccountApp` ADD CONSTRAINT `FK_AccountApp_Account` FOREIGN KEY(`AccountId`) REFERENCES `Account`(`AccountId`);
/****** Object: ForeignKey `FK_InstallingApp_Account` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `InstallingApp` ADD CONSTRAINT `FK_InstallingApp_Account` FOREIGN KEY(`AccountId`) REFERENCES `Account`(`AccountId`);

/****** Object: ForeignKey `FK_AccountAnswer_Account` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `AccountAnswer` ADD CONSTRAINT `FK_AccountAnswer_Account` FOREIGN KEY(`AccountId`) REFERENCES `Account`(`AccountId`);
/****** Object: ForeignKey `FK_AccountAnswer_Answer` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `AccountAnswer` ADD CONSTRAINT `FK_AccountAnswer_Answer` FOREIGN KEY(`AnswerId`) REFERENCES `Answer`(`AnswerId`);
/****** Object: ForeignKey `FK_Question_Answer` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `Answer` ADD CONSTRAINT `FK_Question_Answer` FOREIGN KEY(`QuestionId`) REFERENCES `Question`(`QuestionId`);
/****** Object: ForeignKey `FK_Product_Icon` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `Product` ADD CONSTRAINT `FK_ProductIcon_Image` FOREIGN KEY(`IconId`) REFERENCES `Image` (`ImageId`);
/****** Object: ForeignKey `FK_Product_Banner` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `Product` ADD CONSTRAINT `FK_ProductBanner_Image` FOREIGN KEY(`BannerId`) REFERENCES `Image` (`ImageId`);

/****** Object: ForeignKey `FK_ProductTag_Product` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `ProductTag` ADD CONSTRAINT `FK_ProductTag_Product` FOREIGN KEY(`ProductId`) REFERENCES `Product` (`ProductId`);
/****** Object: ForeignKey `FK_ProductFile_FileVersion` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `ProductFile` ADD CONSTRAINT `FK_ProductFile_FileVersion` FOREIGN KEY(`FileVersionId`) REFERENCES `FileVersion` (`FileVersionId`);
/****** Object: ForeignKey `FK_ProductFile_OSVersion` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `ProductFile` ADD CONSTRAINT `FK_ProductFile_OSVersion` FOREIGN KEY(`MinimumOSVersion`) REFERENCES `OSVersion` (`VersionId`);
/****** Object: ForeignKey `FK_ProductFile_Product` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `ProductFile` ADD CONSTRAINT `FK_ProductFile_Product` FOREIGN KEY(`ProductId`) REFERENCES `Product` (`ProductId`);
/****** Object: ForeignKey `FK_ScreenShot_Image` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `ScreenShot` ADD CONSTRAINT `FK_ScreenShot_Image` FOREIGN KEY(`ImageId`) REFERENCES `Image` (`ImageId`);
/****** Object: ForeignKey `FK_ScreenShot_ProductFile` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `ScreenShot` ADD CONSTRAINT `FK_ScreenShot_ProductFile` FOREIGN KEY(`FileId`) REFERENCES `ProductFile` (`FileId`);
/****** Object: ForeignKey `FK_FileData_ProductFile` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `FileData` ADD CONSTRAINT `FK_FileData_ProductFile` FOREIGN KEY(`FileId`) REFERENCES `ProductFile` (`FileId`);
/****** Object: ForeignKey `FK_Product_Category_Category` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `Product_Category` ADD CONSTRAINT `FK_Product_Category_Category` FOREIGN KEY(`CategoryId`) REFERENCES `Category` (`CategoryId`);
/****** Object: ForeignKey `FK_Product_Category_Product` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `Product_Category` ADD CONSTRAINT `FK_Product_Category_Product` FOREIGN KEY(`ProductId`) REFERENCES `Product` (`ProductId`);
/****** Object: ForeignKey `FK_OSVersion_OperatingSystem` Script Date: 06/25/2013 17:41:49 ******/
ALTER TABLE `OSVersion` ADD CONSTRAINT `FK_OSVersion_OperatingSystem` FOREIGN KEY(`OSId`) REFERENCES `OperatingSystem` (`OSId`);