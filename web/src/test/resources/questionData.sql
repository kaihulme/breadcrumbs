INSERT INTO Question (question)
VALUES ('Which of the following is true of papillary thyroid carcinoma'),
('The most common cause of hypothyroidism in underprivileged countries is:'),
('Which of the following hormones, in addition to participating in the initiation of labor, may play a role in trust, monogamy, and the desire to cuddle?'),
(' A patient with Cushing syndrome might present with any of the following EXCEPT:');

INSERT INTO Choice (question,choiceText,answer)
VALUES (1,'May have psammoma bodies',1),
(1,'The least common kind of thyroid carcinoma',0),
(1,'The type of thyroid carcinoma with the worst prognosis',0),
(1,'Occurs in patients with MEN II',0),
(2,'Pituitary dysfunction',0),
(2,'DeQuervain’s thyroiditis',0),
(2,'Graves’ disease',0),
(2,'Iodine deficiency',1),
(3,'Cortisol',0),
(3,'Thyroid hormone',0),
(3,'Parathormone',0),
(3,'Oxytocin',1),
(4,'Obesity',0),
(4,'A buffalo hump',0),
(4,'Moon facies',0),
(4,'Bronze or hyperpigmented skin',1);


INSERT INTO Hint (question,hintText,code)
VALUES (1,'made of calcium','HR4FE'),
(1,'have a laminar appearance','HQ4G9'),
(2,'tuns starch dark blue','HAS83'),
(2,'can be used to treat a certain type of cancer','H4GUP'),
(3,'sounds similar to a pain treatment','HRT93'),
(3,'produced in the brain','HET03'),
(4,'shows on the outside','HWF30'),
(4,'not to be confused with Silver syndrome','HWR52');

INSERT INTO Question (question)
VALUES('After consuming a banana split, which hormones would be expected to increase?'),
('The primary role of the parathyroid gland is:'),
('Antibodies directed against pancreatic cells result in these cells’ destruction. What laboratory abnormality might be seen in this scenario?'),
('Each hormone’s organ specificity is determined by:');

INSERT INTO Choice (question,choiceText,answer)
VALUES (5,'Prolactin',0),
(5,'Glucagon',0),
(5,'Insulin',1),
(5,'Parathyroid Hormone',0),
(6,'To maintain metabolic homeostasis',0),
(6,'To regulate serum calcium levels',1),
(6,'To send hormonal signals to other endocrine organs',0),
(6,'To receive hormonal signals from the hypothalamus',0),
(7,'Depressed serum calcium',0),
(7,'Elevated serum glucose',1),
(7,'Depressed serum sodium',0),
(7,'Elevated serum calcium',0),
(8,'Whether it is a corticosteroid or a gonadotropic hormone',0),
(8,'Its ability to interact with a specific receptor',1),
(8,'The signaling cascade it initiates',0),
(8,'Whether it is lipophilic or lipophobic',0);

UPDATE Question SET quizId = 1;
