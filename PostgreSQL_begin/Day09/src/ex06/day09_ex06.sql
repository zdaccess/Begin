CREATE OR REPLACE FUNCTION fnc_person_visits_and_eats_on_date(IN pperson varchar DEFAULT 'Dmitriy', IN pprice numeric DEFAULT 500, IN pdate date DEFAULT '20220108') RETURNS TABLE(name varchar ) AS $$
BEGIN
RETURN QUERY 
    SELECT pizzeria.name
    FROM person_visits
    JOIN person ON person.id = person_visits.person_id
    JOIN pizzeria ON person_visits.pizzeria_id = pizzeria.id
    JOIN menu ON  pizzeria.id = menu.pizzeria_id
    WHERE person.name = pperson AND menu.price < pprice AND person_visits.visit_date = pdate;
END; 
$$ LANGUAGE plpgsql;

SELECT *
FROM fnc_person_visits_and_eats_on_date(pprice := 800);

SELECT *
FROM fnc_person_visits_and_eats_on_date(pperson := 'Anna', pprice := 1300, pdate := '2022-01-01');