/*Trigger to update notifications to when there is a notification*/
CREATE OR REPLACE FUNCTION notification_count_to_update()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  INSERT INTO notification_count (user_id, count, house_id) VALUES (new.notification_to, 1, new.house_id)
  ON CONFLICT (user_id, house_id)
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

CREATE OR REPLACE FUNCTION notification_count_to_Negate()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  UPDATE notification_count
  SET count = notification_count.count - 1
  WHERE user_id = old.notification_to AND house_id = old.house_id;
  RETURN new;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION notification_count_to_Negate()
OWNER TO sysadmin;

CREATE TRIGGER tr_notification_count_to_Negate
AFTER UPDATE ON notification
FOR EACH ROW
EXECUTE PROCEDURE notification_count_to_Negate();