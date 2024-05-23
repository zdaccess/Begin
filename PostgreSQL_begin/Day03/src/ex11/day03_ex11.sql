UPDATE menu 
SET price = cast(price * 0.9 AS int)
WHERE pizza_name = 'greek pizza';