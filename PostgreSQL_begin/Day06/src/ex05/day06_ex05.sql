COMMENT ON TABLE person_discounts IS 'Таблица person_discounts необходима для показа персональных скидок клиентам и персоналу пиццерии';
COMMENT ON COLUMN person_discounts.id IS 'Первичный ключ';
COMMENT ON COLUMN person_discounts.person_id IS 'Индетификационный номер клиента пиццерии';
COMMENT ON COLUMN person_discounts.pizzeria_id IS 'Индетификационный номер пиццерии';
COMMENT ON COLUMN person_discounts.discount IS 'Персональная скидка клиента пиццерии';