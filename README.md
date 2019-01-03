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
<p>Returns json data about all restaurants.</p>
<ul>
<li>
<p><strong>Usage: </strong> <code> GET /rest/admin/restaurants</code></p>
</li>
<li><strong>Success Response:</strong> <strong>Code:</strong> 200 <br>
<strong>Content:</strong> <div> [{"id": 100002,"name": "OLIVE GARDEN","address": "NY, 1-st street 58","dishList": null},</div>
     <div> {"id": 100003,"name": "MADDISON SQUARE AVENIU","address": "NY, 2-nd street 39","dishList": null},</div>
     <div>{"id": 100004,"name": "BURGER KING","address": "NY, Washington street 2","dishList": null}]</div>
</li>
</ul>

<h3><strong>Show Restaurant</strong></h3>
<p>Returns json data about a single restaurant.</p>
<ul>
<li>
<p><strong>Usage: </strong> <code> GET /rest/admin/restaurants/{id}</code></p>
</li>
<li><strong>Success Response:</strong> <strong>Code:</strong> 200 <br>
<strong>Content:</strong> <div>[{"id": 100002,"name": "OLIVE GARDEN","address": "NY, 1-st street 58","dishList": null}]</div>
</li>
<li><strong>Error Response:</strong> <strong>Code:</strong> 422 UNPROCESSABLE_ENTITY <br>
<strong>Content:</strong> <code>{
   "url": "http://localhost:8080/voteSystem/rest/admin/restaurants/1",
   "type": "DATA_NOT_FOUND",
   "detail": "ru.firstproject.util.exception.NotFoundException: Not found object with id = 1"
}</code>
</li>
</ul>


<h3><strong>Create Restaurant</strong></h3>
<p>Returns json data about created restaurant restaurant.</p>
<ul>
<li>
<p><strong>Usage: </strong> <code> POST /rest/admin/restaurants</code></p>
</li>
<li>
<p><strong>Consume Media Type Json: </strong> <code><div> {"name": "New Restaurant","address": "LA, "Ocean street 1"}</div></code></p>
</li>      
<li><strong>Success Response:</strong> <strong>Code:</strong> 201 <br>
<strong>Content:</strong> <div>[{"id": 100016,"name": "New Restaurant","address": "LA, Ocean street 1"}]</div>
</li>
<li><strong>Error Response:</strong> <strong>Code:</strong> 422 UNPROCESSABLE_ENTITY <br>
<strong>Content:</strong> <code>{
   "url": "http://localhost:8080/voteSystem/rest/admin/restaurants",
   "type": "VALIDATION_ERROR",
   "detail": "org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 ,default message [размер должен быть между 2 и 100]] "
}</code>
</li>
</ul>

<h3><strong>Show Today Restaurant Menu</strong></h3>
<p>Returns json data about a single restaurant menu</p>
<ul>
<li>
<p><strong>Usage: </strong> <code> GET /rest/admin/restaurants/{id}/menu</code></p>
</li>
<li><strong>Success Response:</strong> <strong>Code:</strong> 200 <br>
<strong>Content:</strong> 
     <div>[{"id": 100004, "name": "BURGER KING","address": "NY, Washington street 2",</div>
      <div> "dishList":[{"id": 100008,"name": "Big Burger","registered": 1546462800000,"price": 5.2},</div>
                 <div>  {"id": 100009,"name": "Kola","registered": 1546462800000,"price": 2}]}]</div>
</li>
</ul>

<h3><strong>Show Today All Restaurants Menu</strong></h3>
<p>Returns json data about all restaurants menu</p>
<ul>
<li>
<p><strong>Usage: </strong> <code> GET /rest/admin/restaurants/menu</code></p>
</li>
<li><strong>Success Response:</strong> <strong>Code:</strong> 200 <br>
<strong>Content:</strong> 
      <div>[{"id": 100002,"name": "OLIVE GARDEN","address": "NY, 1-st street 58",</div>
      <div> "dishList":[{"id": 100010,"name": "Steak","registered": 1546549200000,"price": 10},</div>
             <div>     {"id": 100011,"name": "Milk Cocktail","registered": 1546549200000,"price": 1.5},</div>
              <div>    {"id": 100012,"name": "Pancake","registered": 1546549200000,"price": 2}]},</div>
     <div> {"id": 100004,"name": "BURGER KING","address": "NY, Washington street 2",</div>
     <div>  "dishList":[{"id": 100008,"name": "Big Burger","registered": 1546549200000,"price": 5.2},</div>
          <div>        {"id": 100009,"name": "Kola","registered": 1546549200000,"price": 2}]}]</div>
</li>
</ul>

<h3><strong>Create Dish</strong></h3>
<p>Returns json data about today restaurant menu include created dish</p>
<ul>
<li>
<p><strong>Usage: </strong> <code> POST /rest/admin/restaurants/{id}/menu</code></p>
</li>
<li>
<p><strong>Consume Media Type Json: </strong> <div> {"name": "Very Big Burger","price": 8.8,</div>
     <div>"restaurant":{"id": 100002,"name": "OLIVE GARDEN","address": "NY, 1-st street 58"}}</div></p>
</li>      
<li><strong>Success Response:</strong> <strong>Code:</strong> 201 <br>
<strong>Content:</strong><div>{"id": 100004,"name": "BURGER KING","address": "NY, Washington street 2",</div>
   <div>"dishList":[{"id": 100008,"name": "Big Burger","registered": 1546549200000,"price": 5.2},</div>
            <div>{"id": 100009,"name": "Kola","registered": 1546549200000,"price": 2},</div>
            <div>{"id": 100016,"name": "Very Big Burger","registered": 1546551720198,"price": 8.8}]}</div>
</li>
<li><strong>Error Response:</strong> <strong>Code:</strong> 422 UNPROCESSABLE_ENTITY <br>
<strong>Content:</strong> <code>{"url": "http://localhost:8080/voteSystem/rest/admin/restaurants",
   "type": "VALIDATION_ERROR",
   "detail": "org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 ,default message [размер должен быть между 2 и 100]] "}</code>
</li>
</ul>

<h3><strong>Update Dish</strong></h3>
<p>Returns json data about today restaurant menu include updated dish</p>
<ul>
<li>
<p><strong>Usage: </strong> <code> PUT /rest/admin/restaurants/{id}/menu/{dishId}</code></p>
</li>
<li>
<p><strong>Consume Media Type Json: </strong><div>  {"name": "Very Small Burger","price": 2.8,</div>
     <div>"restaurant":{"id": 100002,"name": "OLIVE GARDEN","address": "NY, 1-st street 58"}}</div></p>
</li>      
<li><strong>Success Response:</strong> <strong>Code:</strong> 201 <br>
<strong>Content:</strong> <div>{"id": 100004,"name": "BURGER KING","address": "NY, Washington street 2",</div>
   <div>"dishList":[{"id": 100008,"name": "Big Burger","registered": 1546549200000,"price": 5.2},</div>
            <div>{"id": 100009,"name": "Kola","registered": 1546549200000,"price": 2},</div>
            <div>{"id": 100016,"name": "Very Small Burger","registered": 1546551720198,"price": 2.8}]}</div>
</li>
<li><strong>Error Response:</strong> <strong>Code:</strong> 422 UNPROCESSABLE_ENTITY <br>
<strong>Content:</strong> <code>{   "url": "http://localhost:8080/voteSystem/rest/admin/restaurants",
   "type": "VALIDATION_ERROR",
   "detail": "org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 ,default message [размер должен быть между 2 и 100]] "}</code>
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


