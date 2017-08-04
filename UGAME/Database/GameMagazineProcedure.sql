USE `AppMagazine`;

delimiter //
CREATE PROCEDURE findLastestID(
	TableName VARCHAR(50),
	OUT LastestId INT
)
BEGIN
	DECLARE query VARCHAR(4000);

	SET query = CONCAT("SELECT t.column_name INTO @primary_column_name FROM information_schema.table_constraints c JOIN information_schema.key_column_usage t USING (constraint_name, table_schema, table_name) WHERE c.table_schema='AppMagazine' AND c.constraint_type='PRIMARY KEY' AND c.table_name='", TableName, "' GROUP BY t.column_name;");

	SET @sql=query;
	PREPARE stmt FROM @sql;
	EXECUTE stmt ;
	DEALLOCATE PREPARE stmt;

	SET @Id = -1;
	SET query = CONCAT("SELECT `", @primary_column_name, "` INTO @Id FROM ", TABLENAME, " ORDER BY `", @primary_column_name, "` DESC LIMIT 1");
	SET @sql2=query;
	PREPARE stmt2 FROM @sql2;
	EXECUTE stmt2 ;
	DEALLOCATE PREPARE stmt2;
	SET LastestId = @Id;
END//



/*** Procedure add account***/
/*	
Status
	0:Inactive;
	1:Active;
*/
delimiter //
CREATE PROCEDURE addAccount(
	_accountId INT,
	_phoneNumber VARCHAR(100),
	_activeCode INT
	)
BEGIN
	INSERT INTO `account` (`AccountId`,`PhoneNumber`,`ActiveCode`,`STATUS`) 
	VALUES(_accountId,_phoneNumber,_activeCode,0);
END//

/***Procedure find account by account***/
delimiter //
CREATE PROCEDURE findAccountByPhoneNumber(
	_phoneNumber VARCHAR(100))
BEGIN
	SELECT acc.* FROM `account` acc WHERE `PhoneNumber` = _phoneNumber;
END //

/***Procedure active account ***/
delimiter //
CREATE PROCEDURE activeAccount(
	_phoneNUmber VARCHAR(100))
BEGIN
	UPDATE `account` SET `Status` = 1 WHERE `PhoneNumber` = _phoneNUmber;
END//

/***Procedure lock account***/
delimiter  //
CREATE PROCEDURE lockAccount(
	_phoneNumber VARCHAR(100))
BEGIN
	UPDATE `account` SET `Status`  = 0 WHERE `PhoneNumber` = _phoneNumber;
END//

/***Procedure getAllQuestion***/
delimiter //
CREATE PROCEDURE getAllQuestion()
BEGIN
	SELECT 	`question`.* FROM Question;
END//

delimiter //
CREATE PROCEDURE findAnswerByQuestionId(
	_questionId INT)
BEGIN
	SELECT `answer`.* FROM `answer` WHERE `QuestionId` = _questionId;
END//

delimiter //
CREATE PROCEDURE addUserAnswer(
	_accountId INT,
	_answerId INT)
BEGIN
	INSERT INTO `accountanswer`(`AccountId`,`AnswerId`,`Status`) VALUES(_accountId,_answerId,1);
END//

delimiter  //
CREATE PROCEDURE deleteUserAnswerByAccountId(
	_accountId INT)
BEGIN
	DELETE FROM `accountanswer` WHERE `AccountId` = _accountId;
END//


/*** Procedure add installingapp***/

delimiter //
CREATE PROCEDURE addInstallingApp(
	_installingAppId INT,
	_accountId INT,
	_packageName VARCHAR(250)
	)
BEGIN
	INSERT INTO `InstallingApp` (`InstallingAppId`,`AccountId`,`PackageName`,`STATUS`) 
		VALUES(_installingAppId,_accountId,_packageName,1);
END//

delimiter //
CREATE PROCEDURE deleteUserInstallingAppByAccount(
	_accountId INT
	)
BEGIN
	DELETE FROM `appmagazine`.`installingapp` WHERE `AccountId`=_accountId;
END//
	
