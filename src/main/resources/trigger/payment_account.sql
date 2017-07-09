/*Trigger to Check payment account set or not*/
CREATE OR REPLACE FUNCTION user_dladle_payment_account_set()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  UPDATE user_dladle
  SET payment_account_set = TRUE
  WHERE user_dladle.id = new.user_id;
  RETURN new;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION user_dladle_payment_account_set()
OWNER TO sysadmin;

CREATE TRIGGER tr_user_dladle_payment_account_set
AFTER INSERT ON payment_card
FOR EACH ROW
EXECUTE PROCEDURE user_dladle_payment_account_set();

/*Trigger to Check payment account set or not*/
CREATE OR REPLACE FUNCTION user_dladle_payment_account_unset()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  UPDATE user_dladle
  SET payment_account_set = FALSE
  WHERE user_dladle.id = old.user_id;
  RETURN new;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION user_dladle_payment_account_unset()
OWNER TO sysadmin;

CREATE TRIGGER tr_user_dladle_payment_account_unset
AFTER DELETE ON payment_card
FOR EACH ROW
EXECUTE PROCEDURE user_dladle_payment_account_unset();
