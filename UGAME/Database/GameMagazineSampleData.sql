USE `AppMagazine`;

/* Account */
INSERT INTO `account`(`AccountId`,`PhoneNumber`,`ActiveCode`,`Status`) VALUES(1,'0936194388',1235,1);
/* Question*/
INSERT INTO `Question`(`QuestionId`,`QuestionContent`,`Status`) VALUES(1,'Bạn là ai',1);
INSERT INTO `Question`(`QuestionId`,`QuestionContent`,`Status`) VALUES(2,'Sở thích của bạn là gì?',1);
INSERT INTO `Question`(`QuestionId`,`QuestionContent`,`Status`) VALUES(3,'Bạn chơi game vào lúc nào?',1);
/* Answer */
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(1,1,'Lập Trình Viên',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(2,1,'Bác sỹ',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(3,1,'Giáo viên',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(4,1,'Sinh viên',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(5,1,'Nhạc sỹ',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(6,1,'Học sinh',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(7,1,'Vận động viên',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(8,1,'Họa sỹ',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(9,1,'Khác',1);

INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(10,2,'Chơi game',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(11,2,'Xem ti vi',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(12,2,'Đọc báo',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(13,2,'Chơi thể thao',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(14,2,'Lập trình',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(15,2,'Xã hội',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(16,2,'Cafe',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(17,2,'Mua sắm',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(18,2,'Khác',1);

INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(19,3,'Buổi sáng',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(20,3,'Buổi trưa',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(21,3,'Buổi chiều',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(22,3,'Buổi tối',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(23,3,'Sau h học',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(24,3,'Cuối tuần',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(25,3,'Giữa tuần',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(26,3,'Đầu tuần tuần',1);
INSERT INTO `Answer`(`AnswerId`,`QuestionId`,`AnswerContent`,`Status`) VALUES(27,3,'Khác',1);




