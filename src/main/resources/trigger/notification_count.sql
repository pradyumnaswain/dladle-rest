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