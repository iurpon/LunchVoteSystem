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

INSERT INTO dishes (name, price, menu_id) VALUES
  ('Big Burger',5.2,100006),
  ('Kola',2.0,100006),
  ('Steak',10,100005),
  ('Milk Cocktail',1.5,100005),
  ('Pancake',2,100005);