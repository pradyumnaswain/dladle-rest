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
  UPDATE house
  SET
    tenants_count = house.tenants_count + 1
  WHERE id = new.house_id;
  RETURN new;
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

/*Trigger to update notifications count tenants  when there is a notification*/
CREATE OR REPLACE FUNCTION notification_count_tenant_update()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  UPDATE house
  SET
    notifications_count_tenant = house.notifications_count_tenant + 1
  FROM tenant
  WHERE house_id = house.id
        AND house.id = new.house_id AND (user_id = new.notification_from OR user_id = new.notification_to);
  RETURN new;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION notification_count_tenant_update()
OWNER TO sysadmin;

CREATE TRIGGER tr_notification_count_tenant_update
AFTER INSERT ON notification
FOR EACH ROW
EXECUTE PROCEDURE notification_count_tenant_update();

/*Trigger to update notifications count landlord when there is a notification*/
CREATE OR REPLACE FUNCTION notification_count_landlord_update()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  UPDATE house
  SET
    notifications_count_tenant = house.notifications_count_tenant + 1
  WHERE house.id = new.house_id;
  RETURN new;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION notification_count_landlord_update()
OWNER TO sysadmin;

CREATE TRIGGER tr_notification_count_landlord_update
AFTER INSERT ON notification
FOR EACH ROW
EXECUTE PROCEDURE notification_count_landlord_update();