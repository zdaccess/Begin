CREATE UNIQUE INDEX IF NOT EXISTS idx_person_discounts_unique ON person_discounts(person_id, pizzeria_id);

SET ENABLE_SEQSCAN TO OFF;

EXPLAIN ANALYSE 
SELECT *
FROM person_discounts
WHERE person_id = 1 AND pizzeria_id = 4;