DROP TABLE IF EXISTS Meeting;
DROP TABLE IF EXISTS Map;
DROP TABLE IF EXISTS Expert_Role;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS Attempt;
DROP TABLE IF EXISTS Hint;
DROP TABLE IF EXISTS Choice;
DROP TABLE IF EXISTS Question;
DROP TABLE IF EXISTS Expert;
DROP TABLE IF EXISTS Quiz;
DROP TABLE IF EXISTS User;

CREATE TABLE Expert(
   id INT NOT NULL AUTO_INCREMENT,
   firstName VARCHAR(200),
   lastName VARCHAR(200),
   email VARCHAR(200),
   password VARCHAR(255),
   PRIMARY KEY(id)
 );
CREATE TABLE Quiz(
   quizId INT NOT NULL AUTO_INCREMENT,
   title VARCHAR(200),
   expertId INT NOT NULL,
   PRIMARY KEY (quizId),
   FOREIGN KEY (expertId) REFERENCES Expert(id)
 );
 CREATE TABLE User (
  id INT NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(200),
  lastName VARCHAR(200),
  email VARCHAR(200) UNIQUE,
  code VARCHAR(5) UNIQUE,
  score INT(4),
  quizId INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (quizId) REFERENCES Quiz(quizId)
 );
CREATE TABLE Question (
  id INT NOT NULL AUTO_INCREMENT,
  question VARCHAR(200),
  quizId INT,
  code VARCHAR(10),
  x_coord INT,
  y_coord INT,
  PRIMARY KEY (id),
  FOREIGN KEY (quizId) REFERENCES Quiz(quizId)
);
CREATE TABLE Choice(
   id INT NOT NULL AUTO_INCREMENT,
   question INT NOT NULL,
   choiceText VARCHAR(200) NOT NULL,
   answer BIT,
   PRIMARY KEY(id),
   FOREIGN KEY (question) REFERENCES Question(id)
 );
CREATE TABLE Hint(
  id INT NOT NULL AUTO_INCREMENT,
  hintText VARCHAR(100),
  question INT NOT NULL,
  x_coord INT,
  y_coord INT,
  FOREIGN KEY (question) REFERENCES Question(id),
  PRIMARY KEY (id)
);

CREATE TABLE Attempt(
    userId INT NOT NULL,
    choiceId INT NOT NULL,
    attemptNo INT,
    PRIMARY KEY (userId,choiceID),
    FOREIGN KEY (userId) REFERENCES User(id),
    FOREIGN KEY (choiceId) REFERENCES Choice(id)
);
CREATE TABLE Role(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40),
    PRIMARY KEY (id)
);
CREATE TABLE Expert_Role(
    expertId INT NOT NULL,
    roleId INT NOT NULL,
    PRIMARY KEY (expertId,roleId),
    FOREIGN KEY (expertId) REFERENCES Expert(id),
    FOREIGN KEY (roleId) REFERENCES Role(id)
);
CREATE TABLE Map(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40),
    picture BLOB,
    PRIMARY KEY(id)
);
CREATE TABLE Meeting(
    userId INT NOT NULL,
    expertId INT NOT NULL,
    meeting_time TIME,
    location VARCHAR(200),
    FOREIGN KEY (userId) REFERENCES User(id),
    FOREIGN KEY (expertId) REFERENCES Expert(id)
);
INSERT INTO Expert (firstName,lastName,email)
VALUES ('Jane','Smith','Jane.Smith@hotmail.co.uk');
INSERT INTO Quiz(title,expertId) VALUES('testQuiz',1);
INSERT INTO User(firstName,lastName,email,code,score,quizId)
VALUES ('John','Adam','JA@gmail.com','RAcJG',0,1),
('Harry','Calway','HC@hotmail.com','3qRku',0,1),
('Ashley','Johnson','AJ@yahoo.com','T5va9',0,1),
('Loretta','Andrews','LA@gmail.com','N4Kax',0,1);
