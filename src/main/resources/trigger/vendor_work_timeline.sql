/*Trigger to Insert Vendor Work Timeline */
CREATE OR REPLACE FUNCTION vendor_work_timeline_insert()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  INSERT INTO vendor_work_timeline (vendor_id) VALUES (new.id);
  RETURN new;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION vendor_work_timeline_insert()
OWNER TO sysadmin;

CREATE TRIGGER tr_vendor_work_timeline_insert
AFTER INSERT ON vendor
FOR EACH ROW
EXECUTE PROCEDURE vendor_work_timeline_insert();
