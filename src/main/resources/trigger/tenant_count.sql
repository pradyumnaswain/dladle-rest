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

