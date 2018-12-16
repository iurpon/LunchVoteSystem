### curl samples (application deployed in application context `voteSystem`).

#### delete User
`curl -s -X DELETE http://localhost:8080/voteSystem/rest/users/100000`

#### create User
`curl -s -X POST -d '{"name":"NotAdmin","email":"newUser@gmail.com","password":"nopass","roles":["ROLE_ADMIN"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteSystem/rest/users`

#### update User
`curl -s -X PUT -d '{"id":100001,"name":"Admin","email":"adminNew@gmail.com","password":"admin","registered":1544519655801,"roles":["ROLE_ADMIN"]}' -H 'Content-Type: application/json' http://localhost:8080/voteSystem/rest/users/100001`

#### User vote 
`curl -s -X POST -d '{"id":100005,"description1":"Burger","description2":"Kola","description3":"empty","description4":"empty","description5":"empty","registered":1544475600000,"restaurant":{"id":100004,"name":"BURGER KING","address":"NY, Washington street 2"}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteSystem/rest/users/100000/vote`

#### update User vote 
`curl -s -X PUT -d '{"id":100008,"registered":1544475600000,"restaurant":{"id":100004,"name":"BURGER KING","address":"NY, Washington street 2"},"user":{"id":100001,"name":"Admin","email":"admin@gmail.com","password":"admin","registered":1544527179828,"roles":["ROLE_ADMIN"]}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteSystem/rest/users/100001/vote`

#### get vote statistics
`curl -s http://localhost:8080/voteSystem/rest/users/100000/statistics`


#### get All Users
`curl -s http://localhost:8080/voteSystem/rest/admin/users`

#### get User 100001
`curl -s http://localhost:8080/voteSystem/rest/admin/users/100001`


#### get User not found
`curl -s -v http://localhost:8080/voteSystem/rest/admin/users/1`

#### get All Restaurants
`curl -s http://localhost:8080/voteSystem/rest/admin/restaurants`

#### get Restaurant 100002
`curl -s http://localhost:8080/voteSystem/rest/admin/restaurants/100002`

#### delete Restaurant 100002
`curl -s -X DELETE http://localhost:8080/voteSystem/rest/admin/restaurants/100002`

#### update Restaurant 100003
`curl -s -X PUT -d '{"id":100003,"name":"MADDISON SQUARE PAVENIU","address":"NY, 2-nd street 39"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteSystem/rest/admin/restaurants/100003`

#### create Restaurant
`curl -s -X POST -d '{"name":"Delicious","address":"Brooklyn, Bussines Center"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteSystem/rest/admin/restaurants`

#### get Today Menus
`curl -s http://localhost:8080/voteSystem/rest/admin/menus`

#### get  Menu 100005
`curl -s http://localhost:8080/voteSystem/rest/admin/menus/100005`

#### update  Menu 100005
`curl -s -X PUT  -d '{"id":100005,"description1":"Burger","description2":"Kola","description3":"IceCream","description4":"Apple Cake","description5":"empty","restaurant":{"id":100004,"name":"BURGER KING","address":"NY, Washington street 2"}}'  -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteSystem/rest/admin/menus/100005`

#### create  Menu
`curl -s -v -X POST  -d '{"description1":"Becon","description2":"Kola","description3":"IceCream","description4":"empty","description5":"empty","restaurant":{"id":100002,"name":"OLIVE GARDEN","address":"NY, 1-st street 58"}}'  -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteSystem/rest/admin/menus`

#### when Menu is ready start Voting
`curl -s -X POST http://localhost:8080/voteSystem/rest/admin/startVote`