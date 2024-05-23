SELECT person.name AS person_name1, foo.name AS person_name2, person.address AS common_address
FROM person
CROSS JOIN (SELECT * FROM person) AS foo
WHERE person.address = foo.address AND person.name != foo.name AND person.id > foo.id 
ORDER BY person_name1, person_name2, common_address ASC