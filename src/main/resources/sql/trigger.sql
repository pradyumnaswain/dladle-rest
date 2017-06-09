SELECT *
FROM pg_trigger;

/*Trigger to update contacts count when contact is inserted*/
CREATE OR REPLACE FUNCTION contact_count_update()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  UPDATE house
  SET
    contacts_count = contacts_count + 1
  WHERE id = new.house_id;
  RETURN new;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION contact_count_update()
OWNER TO sysadmin;

CREATE TRIGGER tr_contact_count_update
AFTER INSERT ON property_contact
FOR EACH ROW
EXECUTE PROCEDURE contact_count_update();