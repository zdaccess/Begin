SET ENABLE_SEQSCAN TO OFF;

EXPLAIN ANALYSE 
SELECT menu.pizza_name, pizzeria.name AS pizzeria_name
FROM menu
JOIN pizzeria ON menu.pizzeria_id = pizzeria.id;