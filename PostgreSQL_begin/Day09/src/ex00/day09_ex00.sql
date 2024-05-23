CREATE TABLE person_audit(
	created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
	type_event char(1) DEFAULT 'I' NOT NULL,
	row_id bigint NOT NULL,
	name varchar,
	age  integer,
	gender varchar,
	address varchar,
	constraint ch_type_event CHECK (type_event IN ('I', 'U', 'D'))
	);
	
CREATE OR REPLACE FUNCTION fnc_trg_person_insert_audit() RETURNS trigger AS $person_audit$
BEGIN
IF (TG_OP = 'INSERT') THEN
	INSERT INTO person_audit VALUES (NOW(), 'I', NEW.*);
END IF;
RETURN NEW;
END;
$person_audit$ LANGUAGE plpgsql;

CREATE TRIGGER trg_person_insert_audit
AFTER INSERT ON person
FOR EACH ROW
EXECUTE FUNCTION fnc_trg_person_insert_audit();

INSERT INTO person(id, name, age, gender, address) VALUES (10,'Damir', 22, 'male', 'Irkutsk');