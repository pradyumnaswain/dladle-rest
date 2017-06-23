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

/*Trigger to update contacts count when contact is deleted*/
CREATE OR REPLACE FUNCTION contact_count_update_on_delete()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  UPDATE house
  SET
    contacts_count = contacts_count - 1
  WHERE id = old.house_id;
  RETURN new;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION contact_count_update_on_delete()
OWNER TO sysadmin;

CREATE TRIGGER tr_contact_count_update_on_delete
AFTER DELETE ON property_contact
FOR EACH ROW
EXECUTE PROCEDURE contact_count_update_on_delete();

/*Trigger to update tenants count when tenant is assigned with property*/
CREATE OR REPLACE FUNCTION tenant_count_update()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  IF new.house_id IS NOT NULL AND old.house_id IS NULL
  THEN
    UPDATE house
    SET
      tenants_count = house.tenants_count + 1
    WHERE id = new.house_id;
    RETURN new;
  ELSE
    UPDATE house
    SET
      tenants_count = house.tenants_count - 1
    WHERE id = old.house_id;
    RETURN new;
  END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION tenant_count_update()
OWNER TO sysadmin;

CREATE TRIGGER tr_tenant_count_update
AFTER UPDATE ON tenant
FOR EACH ROW
EXECUTE PROCEDURE tenant_count_update();

/*Trigger to update notifications to when there is a notification*/
CREATE OR REPLACE FUNCTION notification_count_to_update()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  INSERT INTO notification_count (user_id, count) VALUES (new.notification_to, 1)
  ON CONFLICT (user_id)
    DO UPDATE
      SET
        count = notification_count.count + 1
      WHERE notification_count.user_id = new.notification_to;
  RETURN new;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION notification_count_to_update()
OWNER TO sysadmin;

CREATE TRIGGER tr_notification_count_to_update
AFTER INSERT ON notification
FOR EACH ROW
EXECUTE PROCEDURE notification_count_to_update();
