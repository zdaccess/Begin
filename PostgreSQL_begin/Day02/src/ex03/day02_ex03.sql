WITH CTE AS(
SELECT missing_date::date
FROM generate_series('20220101', '20220110', interval '1 day') AS missing_date)
SELECT missing_date
FROM CTE
FULL JOIN (SELECT * FROM person_visits WHERE person_visits.person_id = 1 OR person_visits.person_id = 2) AS day
ON day.visit_date = missing_date::date
WHERE day.person_id IS NULL
ORDER BY missing_date::date ASC