CREATE OR REPLACE FUNCTION fnc_persons_male() RETURNS SETOF person AS $$
    (SELECT * FROM person WHERE person.gender = 'female');
$$ LANGUAGE sql;


CREATE OR REPLACE FUNCTION fnc_persons_female() RETURNS SETOF person AS $$
    (SELECT * FROM person WHERE person.gender = 'male');
$$ LANGUAGE sql;

SELECT *
FROM fnc_persons_female();

SELECT *
FROM fnc_persons_male();