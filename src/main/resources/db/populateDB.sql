DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
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
