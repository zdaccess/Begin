WITH foo AS (
	SELECT distinct currency.id, currency.name AS currencyn, 
	max(currency.rate_to_usd) over (partition by currency.id, currency.name order by currency.updated desc) as currency_max
	FROM currency
)
SELECT 
	COALESCE("user".name, 'not defined') AS name, 
	COALESCE("user".lastname, 'not defined') AS lastname, 
	balance.type,
	SUM(COALESCE(balance.money, 1)) AS volume, 
	COALESCE(currencyn, 'not defined') AS currency_name,
	COALESCE(currency_max, 1)::float AS currency_max_to_usd,
	(SUM(COALESCE(balance.money, 0)) * COALESCE(currency_max, 1))::float AS total_volume_in_usd
FROM balance
LEFT JOIN foo ON balance.currency_id = foo.id
left JOIN "user" ON balance.user_id = "user".id
GROUP BY type, "user".name, lastname, currency_name, currency_max
ORDER BY name DESC, lastname ASC, type ASC;
