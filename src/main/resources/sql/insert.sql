INSERT INTO user_type (id, name) VALUES (1, 'Tenant');
INSERT INTO user_type (id, name) VALUES (2, 'Landlord');
INSERT INTO user_type (id, name) VALUES (3, 'Vendor');

INSERT INTO service_type (id, name) VALUES (1, 'Plumber');
INSERT INTO service_type (id, name) VALUES (2, 'Electrician');
INSERT INTO service_type (id, name) VALUES (3, 'Painter');

INSERT INTO place_type (id, name) VALUES (1, 'Flat');
INSERT INTO place_type (id, name) VALUES (2, 'Town House');
INSERT INTO place_type (id, name) VALUES (3, 'House');

INSERT INTO home_view_type (id, name) VALUES (1, 'Home View Type 1');
INSERT INTO home_view_type (id, name) VALUES (2, 'Home View Type 2');
INSERT INTO home_view_type (id, name) VALUES (3, 'Home View Type 3');

INSERT INTO years_exp (id, name) VALUES (1, '< 1 Year');
INSERT INTO years_exp (id, name) VALUES (2, '1-3 Years');
INSERT INTO years_exp (id, name) VALUES (3, '3-5 Years');
INSERT INTO years_exp (id, name) VALUES (4, '5+ Years');

INSERT INTO contact_type (id, name) VALUES (1, 'Hospital');
INSERT INTO contact_type (id, name) VALUES (2, 'Police Station');
INSERT INTO contact_type (id, name) VALUES (3, 'Fire Station');
INSERT INTO contact_type (id, name) VALUES (4, 'Landlord');

INSERT INTO notification_type (id, name) VALUES (1, 'Landlord Request Tenant to Property');
INSERT INTO notification_type (id, name) VALUES (2, 'Tenant Accepts Property Invitation');
INSERT INTO notification_type (id, name) VALUES (3, 'Tenant Rejects Property Invitation');
INSERT INTO notification_type (id, name) VALUES (4, 'Tenant Request Landlord for Property');
INSERT INTO notification_type (id, name) VALUES (5, 'Landlord Accepts Property Request');
INSERT INTO notification_type (id, name) VALUES (6, 'Landlord Rejects Property Request');
INSERT INTO notification_type (id, name) VALUES (7, 'Lease Terminate Request from Landlord to Tenant');
INSERT INTO notification_type (id, name) VALUES (8, 'Tenant Accepts Lease Termination');
INSERT INTO notification_type (id, name) VALUES (9, 'Tenant Rejects Lease Termination');
INSERT INTO notification_type (id, name) VALUES (10, 'Lease Terminate Request from Tenant to Landlord');
INSERT INTO notification_type (id, name) VALUES (11, 'Landlord Accepts Lease Termination');
INSERT INTO notification_type (id, name) VALUES (12, 'Landlord Rejects Lease Termination');
INSERT INTO notification_type (id, name) VALUES (13, 'Landlord removes Tenat from Lease');
INSERT INTO notification_type (id, name) VALUES (14, 'Tenant Leaves Lease');

INSERT INTO service_status (id, name) VALUES (1, 'Requested');