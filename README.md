# Diet_Exercise_Journal_Application
This is an application that helps the user to keep a journal of their diet and exercise. (This application is completed but could be optimized better)

The user will log what they eat every day, in terms of food items and the application will determine nutrient and calory intake as well as food groups represented in the meals, based on the Canadian Nutrient File (CNF). The application aims at helping the user to break down their calory intake in food groups and monitor how well the align with the Canada Food Guide. Using the logged exercise data, the application will calculate the Basal Metabolic Rate (BMR), based on which the application can calculate the calories burnt based on various levels of exercise. Based on this, the user can find out how much weight they can lose based on their diet and exercise. The user can visualize different data over different time periods for better understanding.

Note to users:

Before you start the application, I have some note for you:

First trying to enable the database. The sql code is provided as txt file, if you can't run it on mySQL, open the .sql file and run only create table statements/insert into statements.

The ingredients are limited, you can have egg and vegetable scrumble, beef, chicken, pork... You can form your meal by those ingredients. The input should be exactly the same as it showed in Abbrivation column, otherwise it will cause issue.

For the exercise we have limit exercise for better calorie burnt calculation, you can choose them in the log page.

1: All the input date should be yyyy-mm-dd format, wrong input will cause error.

2: When you modify profile, PLEASE DO NOT change height/weight and the measurement at the sametime!!! In one modify action, you should choose to change the measurement or the height/weight.

3: When you input the data for diet or exercise, click log diet/exercise. The data will not disappear after you save, you can modify you input and log again. If you click the button again, same food will be logged. When you finish, simply click "back" button.

4: Trying to avoid high speed continuous clicks, errors may appear and the database might be a mess.

5: Sometimes when you close the application and see some exception in the console, don't worry, it is normal.

6: All data will be stored into the database after you close the application, the visualization is still working at runtime.

7: when you input dates for visualization, please enter dates that is in your record. For example, you have a meal on 2023-11-20, and you have a meal on 2023-11-22, these two can be the start date and end date.

Hope you have a good experience on this application :)
