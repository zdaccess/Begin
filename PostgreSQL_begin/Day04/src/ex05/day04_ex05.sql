CREATE VIEW v_price_with_discount AS
SELECT person.name, menu.pizza_name, menu.price, cast(menu.price - (menu.price * 0.1) AS int) AS discount_price
FROM person_order
JOIN menu ON menu.id = person_order.menu_id
JOIN person ON person_order.person_id = person.id
ORDER BY person.name ASC, menu.pizza_name ASC;