DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM users;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM restaurants;
DELETE FROM date_label;


ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO restaurants (name, address) VALUES
  ('OLIVE GARDEN','NY, 1-st street 58'),
  ('MADDISON SQUARE AVENIU','NY, 2-nd street 39'),
  ('BURGER KING','NY, Washington street 2');

INSERT INTO menus (rest_id) VALUES
  (100004),
  (100003);


INSERT INTO votes (rest_id, user_id) VALUES
  (100003,100001);

INSERT INTO dishes (name, price, rest_id) VALUES
  ('Big Burger',5.2,100004),
  ('Kola',2.0,100004),
  ('Steak',10,100002),
  ('Milk Cocktail',1.5,100002),
  ('Pancake',2,100002);

INSERT INTO dishes (name, price,registered, rest_id) VALUES
  ('Delicios1',10,'2015-05-05',100002),
  ('Delicios2',11,'2015-05-05',100003),
  ('Delicios1',12,'2015-05-05',100004);