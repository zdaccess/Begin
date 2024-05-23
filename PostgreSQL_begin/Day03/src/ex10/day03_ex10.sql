INSERT 
INTO person_order (id, person_id, menu_id, order_date) 
VALUES ((SELECT MAX(id+1) FROM person_order), (SELECT id FROM person WHERE name = 'Denis'), (SELECT id FROM menu WHERE pizza_name = 'sicilian pizza'), '20220224');

INSERT 
INTO person_order (id, person_id, menu_id, order_date) 
VALUES ((SELECT MAX(id+1) FROM person_order), (SELECT id FROM person WHERE name = 'Irina'), (SELECT id FROM menu WHERE pizza_name = 'sicilian pizza'), '20220224');