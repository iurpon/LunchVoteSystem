# LunchVoteSystem
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/875192eba1e046609cd994c0e2f2caa9)](https://www.codacy.com/app/iurpon/LunchVoteSystem?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=iurpon/LunchVoteSystem&amp;utm_campaign=Badge_Grade)

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend. The task is: Build a voting system for deciding where to have lunch. •	2 types of users: admin and regular users •	Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price) •	Menu changes each day (admins do the updates) • Users can vote on which restaurant they want to have lunch at •	Only one vote counted per user •	If user votes again the same day: o	If it is before 11:00 we asume that he changed his mind. o	If it is after 11:00 then it is too late, vote can't be changed Each restaurant provides new menu each day. As a result, provide a link to github repository. It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

</path></svg></a>Technologies used:</h2>
<ul>
<li>Java 8</li>
<li>Spring 5: Data JPA, Security, Test</li>
<li>Hibernate 5 ORM</li>
<li>Logback</li>
<li>JUnit , AssertJ, Hamcrest, JsonAssert</li>
<li>Jackson</li>
<li>Postgres database</li>
<li>Maven 3</li>
<li>Tomcat 8</li>
</ul>


<h2>Application API:</h2>

<h3><strong>Show Restaurants</strong></h3>
<p>Returns json data about a all restaurants.</p>
<ul>
<li>
<p><strong>Usage: </strong> <code> GET /rest/admin/restaurants</code></p>
</li>
<li><strong>Success Response:</strong> <strong>Code:</strong> 200 <br>
<strong>Content:</strong> <code>[
      {"id": 100002,"name": "OLIVE GARDEN","address": "NY, 1-st street 58","dishList": null},
      {"id": 100003,"name": "MADDISON SQUARE AVENIU","address": "NY, 2-nd street 39","dishList": null},
      {"id": 100004,"name": "BURGER KING","address": "NY, Washington street 2","dishList": null}
]</code>
</li>
<li><strong>Error Response:</strong> <strong>Code:</strong> 404 NOT FOUND <br>
<strong>Content:</strong> <code>{ error : "User doesn't exist" }</code>
</li>
<li><strong>Error Response:</strong> <strong>Code:</strong> 401 UNAUTHORIZED <br>
<strong>Content:</strong> <code>{ error : "You are unauthorized to make this request." }</code>
</li>
</ul>

<h3><strong>Show Restaurant</strong></h3>
<p>Returns json data about a single restaurant.</p>
<ul>
<li>
<p><strong>Usage: </strong> <code> GET /rest/admin/restaurants/{id}</code></p>
</li>
<li><strong>Success Response:</strong> <strong>Code:</strong> 200 <br>
<strong>Content:</strong> <code>[
      {"id": 100002,"name": "OLIVE GARDEN","address": "NY, 1-st street 58","dishList": null},
]</code>
</li>
<li><strong>Error Response:</strong> <strong>Code:</strong> 422 UNPROCESSABLE_ENTITY <br>
<strong>Content:</strong> <code>{
   "url": "http://localhost:8080/voteSystem/rest/admin/restaurants/1",
   "type": "DATA_NOT_FOUND",
   "detail": "ru.firstproject.util.exception.NotFoundException: Not found object with id = 1"
}</code>
</li>
<li><strong>Error Response:</strong> <strong>Code:</strong> 401 UNAUTHORIZED <br>
<strong>Content:</strong> <code>{ error : "You are unauthorized to make this request." }</code>
</li>
</ul>

<h3><strong>Show User</strong></h3>
<p>Returns json data about a single user.</p>
<ul>
<li>
<p><strong>Usage: </strong> <code> GET rest/users/{id}</code></p>
</li>
<li><strong>Success Response:</strong> <strong>Code:</strong> 200 <br>
<strong>Content:</strong> <code>{ id : 12, name : "Michael Bloom" }</code>
</li>
<li><strong>Error Response:</strong> <strong>Code:</strong> 404 NOT FOUND <br>
<strong>Content:</strong> <code>{ error : "User doesn't exist" }</code>
</li>
<li><strong>Error Response:</strong> <strong>Code:</strong> 401 UNAUTHORIZED <br>
<strong>Content:</strong> <code>{ error : "You are unauthorized to make this request." }</code>
</li>
</ul>


<h2>cURL command to get data for voting and vote</h2>
<ul>
  <li><strong> Get Today Menus </strong> </li>
  <code>curl -s http://localhost:8080/voteSystem/rest/users/{id}/menu --user user@yandex.ru:password</code>
 

  <li><strong> User vote </strong></li>
<code>curl -s -X POST -d '{"id":100005,"description1":"Burger","description2":"Kola","description3":"empty","description4":"empty","description5":"empty","registered":1544475600000,"restaurant":{"id":100004,"name":"BURGER KING","address":"NY, Washington street 2"}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteSystem/rest/users/100000/vote --user user@yandex.ru:password</code>

<li><strong> Update User vote </strong></li>
<code>curl -s -X PUT -d '{"id":100008,"registered":1544475600000,"restaurant":{"id":100004,"name":"BURGER KING","address":"NY, Washington street 2"},"user":{"id":100001,"name":"Admin","email":"admin@gmail.com","password":"admin","registered":1544527179828,"roles":["ROLE_ADMIN"]}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteSystem/rest/users/100001/vote --user user@yandex.ru:password</code>
</ul>


