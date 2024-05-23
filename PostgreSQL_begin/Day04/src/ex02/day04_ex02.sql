CREATE VIEW v_generated_dates AS
SELECT generated_date::DATE
FROM generate_series('20220101', '20220131', interval '1 day') AS generated_date
ORDER BY generated_date ASC;