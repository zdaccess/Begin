WITH foo AS (
      SELECT 
      balance.user_id,
      currency.id,
      currency.name,
      balance.money,
      (SELECT currency.rate_to_usd FROM currency
       WHERE currency.id = balance.currency_id AND currency.updated < balance.updated
       ORDER BY currency.updated desc LIMIT 1) AS min_rate_to_usd,
      (SELECT currency.rate_to_usd FROM currency
       WHERE currency.id = balance.currency_id AND currency.updated > balance.updated
       ORDER BY currency.updated ASC LIMIT 1) AS max_rate_to_usd
	FROM currency
	JOIN balance ON balance.currency_id = currency.id 
	GROUP BY balance.money, currency.name, currency.id, balance.updated, balance.currency_id, balance.user_id
	ORDER BY min_rate_to_usd DESC, max_rate_to_usd ASC
)
SELECT 
	COALESCE("user".name, 'not defined') AS name, 
	COALESCE("user".lastname, 'not defined') AS lastname,
	foo.name AS currency_name, 
	(foo.money * COALESCE(foo.min_rate_to_usd, foo.max_rate_to_usd))::float AS currency_in_usd
FROM foo
LEFT JOIN "user" ON "user".id = foo.user_id
ORDER BY name DESC, lastname ASC, currency_name ASC
;
