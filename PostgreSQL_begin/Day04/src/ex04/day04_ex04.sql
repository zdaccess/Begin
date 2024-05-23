CREATE VIEW v_symmetric_union AS
WITH R AS(
	SELECT person_id
	FROM person_visits
	WHERE visit_date = '20220102'),
	S AS(
	SELECT person_id
	FROM person_visits
	WHERE visit_date = '20220106'
	)
(SELECT * FROM R
EXCEPT
SELECT * FROM S)
UNION
(SELECT * FROM S
EXCEPT
SELECT * FROM R)
ORDER BY person_id ASC;