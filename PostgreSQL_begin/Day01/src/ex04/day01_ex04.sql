SELECT person_id
FROM person_order
WHERE order_date = '20220107'
EXCEPT ALL
SELECT person_id
FROM person_visits
WHERE visit_date = '20220107'