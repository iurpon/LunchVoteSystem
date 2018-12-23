# LunchVoteSystem
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/875192eba1e046609cd994c0e2f2caa9)](https://www.codacy.com/app/iurpon/LunchVoteSystem?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=iurpon/LunchVoteSystem&amp;utm_campaign=Badge_Grade)

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend. The task is: Build a voting system for deciding where to have lunch. •	2 types of users: admin and regular users •	Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price) •	Menu changes each day (admins do the updates) • Users can vote on which restaurant they want to have lunch at •	Only one vote counted per user •	If user votes again the same day: o	If it is before 11:00 we asume that he changed his mind. o	If it is after 11:00 then it is too late, vote can't be changed Each restaurant provides new menu each day. As a result, provide a link to github repository. It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

Technologies used:
•	Java 8
•	Spring 5: Security, Test , DATA-JPA
•	Hibernate 5 ORM
•	Logback
•	JUnit, AssertJ, JsonAssert, Hamcrest
•	Jackson
•	Postgres database
•	Tomcat 8
•	Maven 3
Application API:
Show User
Returns json data about a single user.
•	URL:  rest/users/{id}/users/{userId}
•	Method: GET
•	URL Params  : Required  id=[integer], userId = [integer]
•	Data Params : None
•	Success Response:  
o	Code: 200 , Content: { id : 12, name : "Michael Bloom" }
•	Error Response:
o	Code: 404 NOT FOUND , Content: { error : "User doesn't exist" }
OR
o	Code: 401 UNAUTHORIZED , Content: { error : "You are unauthorized to make this request." }
