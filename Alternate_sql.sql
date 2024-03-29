create database Diet_Exercise_Journal_UserProfile;
use Diet_Exercise_Journal_UserProfile;

CREATE TABLE UserProfile (
        UserID INT NOT NULL auto_increment,
        UserName VARCHAR(30) NOT NULL,
        Sex VARCHAR(30) NOT NULL,
        Date_of_birth DATE NOT NULL,
        Height DOUBLE NOT NULL,
        Weight DOUBLE NOT NULL,
        Measurement varchar(20) NOT NULL,
        PRIMARY KEY (UserID)
    );
    select * from UserProfile;
    insert into UserProfile(userid, username, sex, date_of_birth, height, weight, measurement)
    values (100003, 'Mason', 'Male', '2000-7-2', 170, 90, 'Imperial') on duplicate key update 
    username = values(username), sex = values(sex), date_of_birth = values(date_of_birth),
    height = values(height), weight = values(weight), measurement = values(measurement);
    update UserProfile set UserName = 'Mason' where userID = 100003;
RENAME TABLE comments TO UserProfile;
ALTER TABLE snack
rename column Quanitity to Quantity;
select * from Diet_Exercise_Journal_UserProfile.UserProfile;
update UserProfile set Height = 183 where UserID = 100001;
ALTER TABLE UserProfile
modify column UserID INT NOT NULL auto_increment;

CREATE TABLE Breakfast (
        Date date NOT NULL,
        UserID INT NOT NULL,
        Ingredients VARCHAR(255),
        Quantity VARCHAR(255),
        CalorieIntake DOUBLE,
        Constraint PK Primary key (Date, UserID)
    );
    drop table breakfast;
select * from Diet_Exercise_Journal_UserProfile.Breakfast;    
CREATE TABLE Lunch (
        Date date NOT NULL,
        UserID INT NOT NULL,
        Ingredients VARCHAR(255),
        Quantity VARCHAR(255),
        CalorieIntake DOUBLE,
        Constraint lunch_PK Primary key (Date, UserID)
    );
    

CREATE TABLE Dinner (
        Date date NOT NULL,
        UserID INT NOT NULL,
        Ingredients VARCHAR(255),
        Quantity VARCHAR(255),
        CalorieIntake DOUBLE,
        Constraint dinner_PK Primary key (Date, UserID)
    );
    
    alter table Dinner drop constraint dinner_fk;
    alter table Dinner add constraint dinner_fk foreign key (UserID) references UserProfile(UserID) on update cascade;
CREATE TABLE Snack (
        Date date NOT NULL,
        UserID INT NOT NULL,
        Ingredients VARCHAR(255),
        Quantity VARCHAR(255),
        CalorieIntake DOUBLE,
        Constraint snack_PK Primary key (Date, UserID)
    );
    drop table snack;
    alter table Snack drop constraint snack_fk;
    alter table Snack modify column SnackID INT NOT NULL auto_increment;
CREATE TABLE Exercise (
		
        ExID INT NOT NULL AUTO_INCREMENT,
        Date date NOT NULL,
        UserID INT NOT NULL,
        Type VARCHAR(255),
        Duration VARCHAR(255),
        Intensity VARCHAR(255),
        CalorieBurnt double,
        Constraint exercise_PK Primary key (ExID)
    );
   
    select * from Diet_Exercise_Journal_UserProfile.exercise where UserID = 100001;
    select  count(distinct Date)from Diet_Exercise_Journal_UserProfile.exercise where UserID = 100001;
	select distinct date from Diet_Exercise_Journal_UserProfile.exercise where UserID = 100001;
    select Type, Duration, Intensity from Diet_Exercise_Journal_UserProfile.Exercise where UserID = 100001 and Date = '2023-11-13';
    insert into exercise values(1,'2023-11-13', 100001, 'running', '10', 'high', '50');
    insert into exercise values(2,'2023-11-13', 100001, 'walking', '10', 'low', '50');
    insert into exercise values(2,'2023-11-14', 100001, 'running, biking', '30, 60', 'high, low', '150');
    drop table Exercise;
    CREATE TABLE Meal (
        Date date NOT NULL,
        UserID INT NOT NULL,
        Breakfast date,
        Lunch date,
        Dinner date,
        Snack date,
        TotalCalorie double,
        Constraint meal_PK Primary key (Date, UserID)
    );
    
    alter table meal drop constraint dinnerfk;
    insert into breakfast values('2023-11-16', 100001, 'muffin, bread', '11, 20', 30);
    insert into lunch values('2023-11-16', 100001, 'noodles, beef', '11, 23', 50);
    insert into dinner values('2023-11-16', 100001, 'pork, chicken, fruit salad, olive oil', '20, 30, 40, 10', 100);
    insert into snack values('2023-11-16', 100001, 'ice cream, cookie', '13, 20', 12);
    insert into snack values(7,'2023-11-16', 100001, 'rice, chicken', '13, 11', 12);
    insert into Meal values('2023-11-16', 100001, '2023-11-16', '2023-11-16', '2023-11-16','2023-11-16', 300);
    delete from snack where snackID = 7;
    
    update snack set Quantity = '50, 100' where UserID = 100001 and Date = '2023-11-15';
    update snack set ingredients = 'banana, fruit salad' where UserID = 100001 and Date = '2023-11-15';
    select * from Diet_Exercise_Journal_UserProfile.Meal where UserID = 100001;
    select * from Diet_Exercise_Journal_UserProfile.breakfast where UserID = 100001;
    select * from Diet_Exercise_Journal_UserProfile.lunch where UserID = 100001;

    insert into meal values('2023-11-20', 100001, '2023-11-20', '2023-11-20','2023-11-20','2023-11-20', null);
    select * from Diet_Exercise_Journal_UserProfile.Dinner where UserID = 100001;
    select * from Diet_Exercise_Journal_UserProfile.Snack where UserID = 100001;
    select Ingredients, Quantity, CalorieIntake from Diet_Exercise_Journal_UserProfile.Breakfast where UserID = 100001;
    select Ingredients, Quantity from Diet_Exercise_Journal_UserProfile.snack where UserID = 100001 and Date = '2023-11-14';
    CREATE TABLE IF NOT EXISTS `FOOD_GROUP` (
    `FoodGroupID` INT,
    `FoodGroupCode` INT,
    `FoodGroupName` VARCHAR(33),
    `FoodGroupNameF` VARCHAR(45)
);
drop table FOOD_GROUP;
select * from food_group;
INSERT INTO `FOOD_GROUP` VALUES (1,1,'Dairy and Egg Products','Produits laitiers et d''oeufs
'),
	(2,2,'Spices and Herbs','Épices et fines herbes'),
	(3,3,'Babyfoods','Aliments pour bébés'),
	(4,4,'Fats and Oils','Matières grasses et huiles'),
	(5,5,'Poultry Products','Produits de volaille'),
	(6,6,'Soups, Sauces and Gravies','Potages et sauces'),
	(7,7,'Sausages and Luncheon meats','Saucisses et viandes froides'),
	(8,8,'Breakfast cereals','Céréales à déjeuner'),
	(9,9,'Fruits and fruit juices','Fruits et jus de fruits'),
	(10,10,'Pork Products','Produits de porc'),
	(11,11,'Vegetables and Vegetable Products','Légumes et produits végétaux'),
	(12,12,'Nuts and Seeds','Noix et graines'),
	(13,13,'Beef Products','Produits de boeuf'),
	(14,14,'Beverages','Boissons'),
	(15,15,'Finfish and Shellfish Products','Produits de poissons, mollusques et crustacés'),
	(16,16,'Legumes and Legume Products','Légumineuses et produits de légumineuses'),
	(17,17,'Lamb, Veal and Game','Ageau, veau et gibier'),
	(18,18,'Baked Products','Produits de boulangerie'),
	(19,19,'Sweets','Sucreries'),
	(20,20,'Cereals, Grains and Pasta','Céréales, grains et pâtes'),
	(21,21,'Fast Foods','Aliments prêts-à-manger'),
	(22,22,'Mixed Dishes','Mets composés'),
	(25,25,'Snacks','Grignotises');

CREATE TABLE IF NOT EXISTS `FOOD_NAME` (
    `FoodID` INT,
    `FoodCode` INT,
    `FoodGroupID` INT,
    `FoodSourceID` INT,
    `FoodDescription` VARCHAR(98),
    `Abbreviation` VARCHAR(12)
);
INSERT INTO `FOOD_NAME` VALUES (83,83,1,0,'Egg, chicken, dried, whole','egg'),
	(114,114,1,0,'Milk, fluid, skim','milk'),
	(198,198,2,0,'Spices, pepper, black','black pepper'),
	(422,422,4,0,'Vegetable oil, olive','olive oil'),
	(919,919,1,0,'Chicken, ground, lean, cooked','chicken'),
	(1558,1558,1,0,'Fruit salad (peach, pear, apricot, pineapple, cherry), canned, heavy syrup pack, solids and liquid','fruit salad'),
	(1590,1590,1,0,'Lemon juice, canned or bottled','lemon juice'),
	(1704,1704,1,3,'Banana, raw','banana'),
	(2062,2062,1,0,'Coleslaw (cabbage salad), with dressing, homemade','coleslaw'),
	(2123,2123,1,0,'Mushroom, boiled, drained','mushroom'),
	(2401,2401,11,3,'Onion, raw','onion'),
	(2417,2417,11,30,'Potato, flesh, raw','potato'),
	(2461,2461,1,3,'Tomato, red, ripe, boiled','tomato'),
	(2698,2698,1,1,'Beef, ground, regular','beef'),
	(3804,3804,1,0,'Cookie, brownie, commercial','cookie'),
	(4017,4017,18,0,'Bread, wheat germ, toasted','bread'),
	(4425,4425,1,0,'Grains, rice, white, glutinous, cooked','rice'),
	(6120,6120,1,23,'Pork, ground, lean, pan-fried','pork');
 drop table FOOD_NAME;   
 select * from food_name;   
    select FoodID from Diet_Exercise_Journal_UserProfile.FOOD_NAME where Abbreviation = 'coleslaw';
    select NutrientValue from Diet_Exercise_Journal_UserProfile.NUTRIENT_AMOUNT where FoodID = 2062 and NutrientID = 208;
    
    
    CREATE TABLE IF NOT EXISTS `NUTRIENT_NAME` (
    `NutrientID` INT,
    `NutrientCode` INT,
    `NutrientSymbol` VARCHAR(10),
    `NutrientUnit` VARCHAR(10),
    `NutrientName` VARCHAR(79),
    `Tagname` VARCHAR(10),
    `NutrientDecimals` INT
);
DROP TABLE NUTRIENT_NAME;
select * from nutrient_name;
INSERT INTO `NUTRIENT_NAME` VALUES (203,203,'PROT','g','PROTEIN','PROCNT',2),
	(204,204,'FAT','g','FAT (TOTAL LIPIDS)','FAT',2),
	(205,205,'CARB','g','CARBOHYDRATE, TOTAL (BY DIFFERENCE)','CHOCDF',2),
	(207,207,'ASH','g','ASH, TOTAL','ASH',2),
	(208,208,'KCAL','kCal','ENERGY (KILOCALORIES)','ENERC_KCAL',0),
	(210,210,'SUCR','g','SUCROSE','SUCS',2),
	(211,211,'GLUC','g','GLUCOSE','GLUS',2),
	(212,212,'FRUC','g','FRUCTOSE','FRUS',2),
	(213,213,'LACT','g','LACTOSE','LACS',2),
	(214,214,'MALT','g','MALTOSE','MALS',2),
	(221,221,'ALCO','g','ALCOHOL','ALC',2),
	(245,245,'OXAL','mg','OXALIC ACID','OXALAC',0),
	(255,255,'H2O','g','MOISTURE','WATER',2),
	(260,260,'MANN','g','MANNITOL','MANTL',2),
	(261,261,'SORB','g','SORBITOL','SORTL',2),
	(262,262,'CAFF','mg','CAFFEINE','CAFFN',0),
	(263,263,'THBR','mg','THEOBROMINE','THEBRN',0),
	(268,268,'KJ','kJ','ENERGY (KILOJOULES)','ENERC_KJ',0),
	(269,269,'TSUG','g','SUGARS, TOTAL','SUGAR',2),
	(287,287,'GAL','g','GALACTOSE','GALS',2),
	(291,291,'TDF','g','FIBRE, TOTAL DIETARY','FIBTG',1),
	(301,301,'CA','mg','CALCIUM','CA',0),
	(303,303,'FE','mg','IRON','FE',2),
	(304,304,'MG','mg','MAGNESIUM','MG',0),
	(305,305,'P','mg','PHOSPHORUS','P',0),
	(306,306,'K','mg','POTASSIUM','K',0),
	(307,307,'NA','mg','SODIUM','NA',0),
	(309,309,'ZN','mg','ZINC','ZN',2),
	(312,312,'CU','mg','COPPER','CU',3),
	(315,315,'MN','mg','MANGANESE','MN',3),
	(317,317,'SE','µg','SELENIUM','SE',1),
	(319,319,'RT-µG','µg','RETINOL','RETOL',0),
	(321,321,'BC-µG','µg','BETA CAROTENE','CARTB',0),
	(323,323,'ATMG','mg','ALPHA-TOCOPHEROL','TOCPHA',2),
	(324,324,'D-IU','IU','VITAMIN D (INTERNATIONAL UNITS)','VITD_IU',0),
	(339,328,'D3+D2-µG','µg','VITAMIN D (D2 + D3)','VITD_ µG',1),
	(401,401,'VITC','mg','VITAMIN C','VITC',1),
	(404,404,'THIA','mg','THIAMIN','THIA',3),
	(405,405,'RIBO','mg','RIBOFLAVIN','RIBF',3),
	(406,406,'N-MG','mg','NIACIN (NICOTINIC ACID) PREFORMED','NIA',3),
	(409,409,'N-NE','NE','TOTAL NIACIN EQUIVALENT','NIAEQ',3),
	(410,410,'PANT','mg','PANTOTHENIC ACID','PANTAC',3),
	(415,415,'B6','mg','VITAMIN B-6','VITB6A',3),
	(416,416,'BIOT','µg','BIOTIN','BIOT',0),
	(417,417,'FOLA','µg','TOTAL FOLACIN','FOL',0),
	(418,418,'B12','µg','VITAMIN B-12','VITB12',2),
	(430,430,'VITK','µg','VITAMIN K','VITK',1),
	(431,431,'FOAC','µg','FOLIC ACID','FOLAC',0),
	(501,501,'TRP','g','TRYPTOPHAN','TRP',3),
	(502,502,'THR','g','THREONINE','THR',3),
	(503,503,'ISO','g','ISOLEUCINE','ILE',3),
	(504,504,'LEU','g','LEUCINE','LEU',3),
	(505,505,'LYS','g','LYSINE','LYS',3),
	(506,506,'MET','g','METHIONINE','MET',3),
	(507,507,'CYS','g','CYSTINE','CYS',3),
	(508,508,'PHE','g','PHENYLALANINE','PHE',3),
	(509,509,'TYR','g','TYROSINE','TYR',3),
	(510,510,'VAL','g','VALINE','VAL',3),
	(511,511,'ARG','g','ARGININE','ARG',3),
	(512,512,'HIS','g','HISTIDINE','HIS',3),
	(513,513,'ALA','g','ALANINE','ALA',3),
	(514,514,'ASP','g','ASPARTIC ACID','ASP',3),
	(515,515,'GLU','g','GLUTAMIC ACID','GLU',3),
	(516,516,'GLY','g','GLYCINE','GLY',3),
	(517,517,'PRO','g','PROLINE','PRO',3),
	(518,518,'SER','g','SERINE','SER',3),
	(550,550,'ASPA','mg','ASPARTAME',NULL,0),
	(601,601,'CHOL','mg','CHOLESTEROL','CHOLE',0),
	(605,605,'TRFA','g','FATTY ACIDS, TRANS, TOTAL','FATRN',3),
	(606,606,'TSAT','g','FATTY ACIDS, SATURATED, TOTAL','FASAT',3),
	(607,607,'4:0','g','FATTY ACIDS, SATURATED, 4:0, BUTANOIC','F4D0',3),
	(608,608,'6:0','g','FATTY ACIDS, SATURATED, 6:0, HEXANOIC','F6D0',3),
	(609,609,'8:0','g','FATTY ACIDS, SATURATED, 8:0, OCTANOIC','F8D0',3),
	(610,610,'10:0','g','FATTY ACIDS, SATURATED, 10:0, DECANOIC','F10D0',3),
	(611,611,'12:0','g','FATTY ACIDS, SATURATED, 12:0, DODECANOIC','F12D0',3),
	(612,612,'14:0','g','FATTY ACIDS, SATURATED, 14:0, TETRADECANOIC','F14D0',3),
	(613,613,'16:0','g','FATTY ACIDS, SATURATED, 16:0, HEXADECANOIC','F16D0',3),
	(614,614,'18:0','g','FATTY ACIDS, SATURATED, 18:0, OCTADECANOIC','F18D0',3),
	(615,615,'20:0','g','FATTY ACIDS, SATURATED, 20:0, EICOSANOIC','F20D0',3),
	(617,617,'18:1undiff','g','FATTY ACIDS, MONOUNSATURATED, 18:1undifferentiated, OCTADECENOIC','F18D1',3),
	(618,618,'18:2undiff','g','FATTY ACIDS, POLYUNSATURATED, 18:2undifferentiated, LINOLEIC, OCTADECADIENOIC','F18D2',3),
	(619,619,'18:3undiff','g','FATTY ACIDS, POLYUNSATURATED, 18:3undifferentiated, LINOLENIC, OCTADECATRIENOIC','F18D3',3),
	(620,620,'20:4undiff','g','FATTY ACIDS, POLYUNSATURATED, 20:4, EICOSATETRAENOIC','F20D4',3),
	(621,621,'22:6n-3DHA','g','FATTY ACIDS, POLYUNSATURATED, 22:6 n-3, DOCOSAHEXAENOIC (DHA)','F22D6N3',3),
	(624,624,'22:0','g','FATTY ACIDS, SATURATED, 22:0, DOCOSANOIC','F22D0',3),
	(625,625,'14:1','g','FATTY ACIDS, MONOUNSATURATED, 14:1, TETRADECENOIC','F14D1',3),
	(626,626,'16:1undiff','g','FATTY ACIDS, MONOUNSATURATED, 16:1undifferentiated, HEXADECENOIC','F16D1',3),
	(627,627,'18:4','g','FATTY ACIDS, POLYUNSATURATED, 18:4, OCTADECATETRAENOIC','F18D4',3),
	(628,628,'20:1','g','FATTY ACIDS, MONOUNSATURATED, 20:1, EICOSENOIC','F20D1',3),
	(629,629,'20:5n-3EPA','g','FATTY ACIDS, POLYUNSATURATED, 20:5 n-3, EICOSAPENTAENOIC (EPA)','F20D5N3',3),
	(630,630,'22:1undiff','g','FATTY ACIDS, MONOUNSATURATED, 22:1undifferentiated, DOCOSENOIC','F22D1',3),
	(631,631,'22:5n-3DPA','g','FATTY ACIDS, POLYUNSATURATED, 22:5 n-3, DOCOSAPENTAENOIC (DPA)','F22D5N3',3),
	(636,636,'TPST','mg','TOTAL PLANT STEROL','PHYSTR',0),
	(638,638,'STIG','mg','STIGMASTEROL','STID7',0),
	(645,645,'MUFA','g','FATTY ACIDS, MONOUNSATURATED, TOTAL','FAMS',3),
	(646,646,'PUFA','g','FATTY ACIDS, POLYUNSATURATED, TOTAL','FAPU',3),
	(652,652,'15:0','g','FATTY ACIDS, SATURATED, 15:0, PENTADECANOIC','F15D0',3),
	(653,653,'17:0','g','FATTY ACIDS, SATURATED, 17:0, HEPTADECANOIC','F17D0',3),
	(654,654,'24:0','g','FATTY ACIDS, SATURATED, 24:0, TETRACOSANOIC','F24D0',3),
	(802,802,'TMOS','g','TOTAL MONOSACCARIDES','MNSAC',2),
	(803,803,'TDIS','g','TOTAL DISACCHARIDES','DISAC',2),
	(806,432,'FOLN','µg','NATURALLY OCCURRING FOLATE','FOLFD',0),
	(810,209,'STAR','g','STARCH','STARCH',2),
	(811,341,'BTMG','mg','BETA-TOCOPHEROL','TOCPHB',2),
	(812,342,'GTMG','mg','GAMMA-TOCOPHEROL','TOCPHG',2),
	(813,343,'DTMG','mg','DELTA-TOCOPHEROL','TOCPHD',2),
	(814,320,'RAE','µg','RETINOL ACTIVITY EQUIVALENTS','VITA_RAE',0),
	(815,435,'DFE','µg','DIETARY FOLATE EQUIVALENTS','FOLDFE',0),
	(816,641,'SITSTR','mg','BETA-SITOSTEROL','SITSTR',0),
	(817,662,'16:1t','g','FATTY ACIDS, MONOUNSATURATED, 16:1t, HEXADECENOIC','F16D1T',3),
	(818,663,'18:1t','g','FATTY ACIDS, MONOUNSATURATED, 18:1t, OCTADECENOIC','F18D1T',3),
	(819,666,'18:2i','g','FATTY ACIDS, POLYUNSATURATED, 18:2i, LINOLEIC, OCTADECADIENOIC',NULL,3),
	(820,671,'24:1c','g','FATTY ACIDS, MONOUNSATURATED, 24:1c, TETRACOSENOIC','F24D1C',3),
	(821,673,'16:1c','g','FATTY ACIDS, MONOUNSATURATED, 16:1c, HEXADECENOIC','F16D1C',3),
	(823,672,'20:2cc','g','FATTY ACIDS, POLYUNSATURATED, 20:2 c,c  EICOSADIENOIC','F20D2CN6',3),
	(824,674,'18:1c','g','FATTY ACIDS, MONOUNSATURATED, 18:1c, OCTADECENOIC','F18D1C',3),
	(825,675,'18:2ccn-6','g','FATTY ACIDS, POLYUNSATURATED, 18:2 c,c n-6,  LINOLEIC, OCTADECADIENOIC','F18D2CN6',3),
	(826,687,'17:1','g','FATTY ACIDS, MONOUNSATURATED, 17:1, HEPTADECENOIC','F17D1',3),
	(827,689,'20:3undiff','g','FATTY ACIDS, POLYUNSATURATED, 20:3, EICOSATRIENOIC','F20D3',3),
	(828,521,'HYP','g','HYDROXYPROLINE','HYP',3),
	(829,693,'TRMO','g','FATTY ACIDS, TOTAL TRANS-MONOENOIC','FATRNM',3),
	(830,696,'13:0','g','FATTY ACIDS, SATURATED, 13:0 TRIDECANOIC','F13D0',3),
	(831,851,'18:3cccn-3','g','FATTY ACIDS, POLYUNSATURATED, 18:3 c,c,c n-3  LINOLENIC, OCTADECATRIENOIC','F18D3CN3',3),
	(832,685,'18:3cccn-6','g','FATTY ACIDS, POLYUNSATURATED, 18:3 c,c,c n-6, g-LINOLENIC, OCTADECATRIENOIC','F18D3CN6',3),
	(833,697,'15:1','g','FATTY ACIDS, MONOUNSATURATED, 15:1, PENTADECENOIC','F15D1',3),
	(834,322,'AC-µG','µg','ALPHA CAROTENE','CARTA',0),
	(835,334,'CRYPX','µg','BETA CRYPTOXANTHIN','CRYPX',0),
	(836,337,'LYCPN','µg','LYCOPENE','LYCPN',0),
	(837,338,'LUT+ZEA','µg','LUTEIN AND ZEAXANTHIN','LUT+ZEA',0),
	(838,670,'18:2cla','g','FATTY ACIDS, POLYUNSATURATED, CONJUGATED, 18:2 cla, LINOLEIC, OCTADECADIENOIC','F18D2CLA',3),
	(840,676,'22:1c','g','FATTY ACIDS, MONOUNSATURATED, 22:1c, DOCOSENOIC',NULL,3),
	(841,856,'18:3i','g','FATTY ACIDS, POLYUNSATURATED, 18:3i, LINOLENIC, OCTADECATRIENOIC',NULL,3),
	(843,857,'21:5','g','FATTY ACIDS, POLYUNSATURATED, 21:5','F21D5',3),
	(845,858,'22:4n-6','g','FATTY ACIDS, POLYUNSATURATED, 22:4 n-6, DOCOSATETRAENOIC','F22D4',3),
	(846,859,'24:1undiff','g','FATTY ACIDS, MONOUNSATURATED,  24:1undifferentiated, TETRACOSENOIC','F24D1',3),
	(847,860,'12:1','g','FATTY ACIDS, MONOUNSATURATED, 12:1, LAUROLEIC','F12D1',3),
	(848,861,'22:3','g','FATTY ACIDS, POLYUNSATURATED, 22:3,','F22D3',3),
	(849,862,'22:2','g','FATTY ACIDS, POLYUNSATURATED, 22:2, DOCOSADIENOIC','F22D2',3),
	(852,664,'22:1t','g','FATTY ACIDS, MONOUNSATURATED, 22:1t, DOCOSENOIC',NULL,3),
	(853,669,'18:2t,t','g','FATTY ACIDS, POLYUNSATURATED, 18:2t,t , OCTADECADIENENOIC','F18D2TT',3),
	(854,853,'20:3n-6','g','FATTY ACIDS, POLYUNSATURATED, 20:3 n-6, EICOSATRIENOIC','F20D3N6',3),
	(855,855,'20:4n-6','g','FATTY ACIDS, POLYUNSATURATED, 20:4 n-6, ARACHIDONIC','F20D4N6',3),
	(859,695,'TRPO','g','FATTY ACIDS, TOTAL TRANS-POLYENOIC','FATRNP',3),
	(861,852,'20:3n-3','g','FATTY ACIDS, POLYUNSATURATED, 20:3 n-3 EICOSATRIENOIC','F20D3N3',3),
	(862,421,'CHOLN','mg','CHOLINE, TOTAL','CHOLN',1),
	(863,454,'BETN','mg','BETAINE','BETN',1),
	(866,639,'CAMSTR','mg','CAMPESTEROL','CAMD5',0),
	(868,902,'TOmega n-3','g','FATTY ACIDS, POLYUNSATURATED, TOTAL OMEGA  N-3','FAPUN3',3),
	(869,903,'TOmega n-6','g','FATTY ACIDS, POLYUNSATURATED, TOTAL OMEGA   N-6','FAPUN6',3),
	(874,578,'B12-A','µg','VITAMIN B12, ADDED',NULL,2),
	(875,573,'ATMG-A','mg','ALPHA-TOCOPHEROL, ADDED',NULL,2),
	(876,325,'D2-µG','µg','VITAMIN D2, ERGOCALCIFEROL','ERGCAL',1);
