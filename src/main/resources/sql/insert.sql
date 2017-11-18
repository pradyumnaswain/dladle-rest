INSERT INTO user_type (id, name) VALUES (1, 'Tenant');
INSERT INTO user_type (id, name) VALUES (2, 'Landlord');
INSERT INTO user_type (id, name) VALUES (3, 'Vendor');
INSERT INTO user_type (id, name) VALUES (4, 'Administrator');

INSERT INTO service_type (id, name) VALUES (1, 'Plumber');
INSERT INTO service_type (id, name) VALUES (2, 'Electrician');
INSERT INTO service_type (id, name) VALUES (3, 'Painter');

INSERT INTO place_type (id, name) VALUES (1, 'Flat');
INSERT INTO place_type (id, name) VALUES (2, 'Town House');
INSERT INTO place_type (id, name) VALUES (3, 'House');

INSERT INTO home_view_type (id, name) VALUES (1, 'Home View Type 1');
INSERT INTO home_view_type (id, name) VALUES (2, 'Home View Type 2');
INSERT INTO home_view_type (id, name) VALUES (3, 'Home View Type 3');

INSERT INTO years_exp (id, name) VALUES (1, '0.5');
INSERT INTO years_exp (id, name) VALUES (2, '1');
INSERT INTO years_exp (id, name) VALUES (3, '1.5');
INSERT INTO years_exp (id, name) VALUES (4, '2');
INSERT INTO years_exp (id, name) VALUES (5, '2.5');
INSERT INTO years_exp (id, name) VALUES (6, '3');
INSERT INTO years_exp (id, name) VALUES (7, '3.5');
INSERT INTO years_exp (id, name) VALUES (8, '4');
INSERT INTO years_exp (id, name) VALUES (9, '4.5');
INSERT INTO years_exp (id, name) VALUES (10, '5');
INSERT INTO years_exp (id, name) VALUES (11, '5.5');
INSERT INTO years_exp (id, name) VALUES (12, '6');
INSERT INTO years_exp (id, name) VALUES (13, '6.5');
INSERT INTO years_exp (id, name) VALUES (14, '7');
INSERT INTO years_exp (id, name) VALUES (15, '7.5');
INSERT INTO years_exp (id, name) VALUES (16, '8');
INSERT INTO years_exp (id, name) VALUES (17, '8.5');
INSERT INTO years_exp (id, name) VALUES (18, '9');
INSERT INTO years_exp (id, name) VALUES (19, '9.5');
INSERT INTO years_exp (id, name) VALUES (20, '10');
INSERT INTO years_exp (id, name) VALUES (21, '11');

INSERT INTO contact_type (id, name) VALUES (1, 'Hospital');
INSERT INTO contact_type (id, name) VALUES (2, 'Police Station');
INSERT INTO contact_type (id, name) VALUES (3, 'Fire Station');

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
INSERT INTO notification_type (id, name) VALUES (15, 'Rate your Tenant');
INSERT INTO notification_type (id, name) VALUES (16, 'Rate your Landlord');
INSERT INTO notification_type (id, name) VALUES (17, 'Rate Vendor');
INSERT INTO notification_type (id, name) VALUES (18, 'Service Request');
INSERT INTO notification_type (id, name) VALUES (19, 'Service Request Accepted');
INSERT INTO notification_type (id, name) VALUES (20, 'Service Request Rejected');
INSERT INTO notification_type (id, name) VALUES (21, 'Service Estimate Accepted');
INSERT INTO notification_type (id, name) VALUES (22, 'Service Estimate Rejected');

INSERT INTO service_status (id, name) VALUES (1, 'Requested');

INSERT INTO document_type (id, name) VALUES (1, 'Audio');
INSERT INTO document_type (id, name) VALUES (2, 'Image');

INSERT INTO transaction_status (id, name) VALUES (1, 'Success');
INSERT INTO transaction_status (id, name) VALUES (2, 'Fail');
INSERT INTO transaction_status (id, name) VALUES (3, 'Pending');
INSERT INTO transaction_status (id, name) VALUES (4, 'Unknown');

INSERT INTO transaction_type (id, name) VALUES (1, 'Credit');
INSERT INTO transaction_type (id, name) VALUES (2, 'Debit');

INSERT INTO operation_type (id, name) VALUES (1, 'Key Generation');
INSERT INTO operation_type (id, name) VALUES (2, 'Service Fee');

INSERT INTO vendor_rejection_reason (id, rejection_reason) VALUES (1, 'Price is very High');
INSERT INTO vendor_rejection_reason (id, rejection_reason) VALUES (2, 'Vendor does not look Appropriate ');
INSERT INTO vendor_rejection_reason (id, rejection_reason) VALUES (3, 'Vendor seems like inexperienced');
INSERT INTO vendor_rejection_reason (id, rejection_reason) VALUES (4, 'Vendor rating looks very bad');
INSERT INTO vendor_rejection_reason (id, rejection_reason) VALUES (5, 'Other');

INSERT INTO service_over_price_reason (id, over_price_reason) VALUES (1,'')

