SELECT address, ROUND((MAX(age) - ((MIN(age::numeric) / MAX(age::numeric)))),2)::float AS formula, ROUND((AVG(age::numeric)),2)::float AS average, 
(CASE 
	WHEN (ROUND((MAX(age) - ((MIN(age::numeric) / MAX(age::numeric)))),2)::float > ROUND((AVG(age::numeric)),2)::float ) 
	THEN true
 	ELSE false
	END) AS comparison
FROM person
GROUP BY address
ORDER BY address ASC;