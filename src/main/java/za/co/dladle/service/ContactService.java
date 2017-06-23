package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.ContactAddRequest;
import za.co.dladle.entity.DeleteContactRequest;
import za.co.dladle.entity.TenantContactView;
import za.co.dladle.exception.PropertyAddException;
import za.co.dladle.mapper.ContactTypeMapper;
import za.co.dladle.model.Contact;
import za.co.dladle.session.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 6/4/2017.
 */
@Service
public class ContactService {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    public Boolean addContact(ContactAddRequest contactAddRequest) throws Exception {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqTENANT()) {
            String email = userSession.getUser().getEmailId();

            Map<String, Object> map = new HashMap<>();
            map.put("emailId", email);
            String tenantSql = "SELECT tenant.id tenant_id FROM user_dladle INNER JOIN tenant ON user_dladle.id = tenant.user_id WHERE emailid=:emailId";
            Integer tenantId = this.parameterJdbcTemplate.queryForObject(tenantSql, map, Integer.class);

            List<Map<String, Object>> list = new ArrayList<>();

            for (Contact contact : contactAddRequest.getContactList()) {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("tenantId", tenantId);
                map1.put("houseId", contactAddRequest.getHouseId());
                map1.put("contactType", ContactTypeMapper.getPropertyContactType(contact.getContactType()));
                map1.put("name", contact.getName());
                map1.put("address", contact.getAddress());
                map1.put("contactNumber", contact.getContactNumber());
                list.add(map1);
            }
            String sql = "INSERT INTO tenant_contact(tenant_id, house_id,contact_type_id, name, address, contact_number) VALUES (:tenantId,:houseId,:contactType,:name,:address,:contactNumber)";
            this.parameterJdbcTemplate.batchUpdate(sql, list.toArray(new Map[contactAddRequest.getContactList().size()]));

            return true;
        } else {
            throw new Exception("Contacts can only be added by Tenant");
        }
    }

    public List<TenantContactView> listContacts() throws Exception {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqTENANT()) {
            String email = userSession.getUser().getEmailId();

            Map<String, Object> map = new HashMap<>();
            List<TenantContactView> contacts = new ArrayList<>();
            map.put("emailId", email);
            String sql = "SELECT tenant_contact.*,tenant_contact.id tenant_contact_id, tenant_contact.name tenant_conact_name, contact_type.name contact_type_name FROM tenant_contact " +
                    " INNER JOIN contact_type ON tenant_contact.contact_type_id = contact_type.id " +
                    " INNER JOIN tenant ON tenant_contact.tenant_id= tenant.id " +
                    " INNER JOIN user_dladle ON user_dladle.id= tenant.user_id " +
                    " WHERE emailid=:emailId";
            this.parameterJdbcTemplate.query(sql, map, (rs1, rowNum1) -> {
                TenantContactView propertyContact = new TenantContactView();
                propertyContact.setContactId(rs1.getLong("tenant_contact_id"));
                propertyContact.setAddress(rs1.getString("address"));
                propertyContact.setName(rs1.getString("tenant_conact_name"));
                propertyContact.setContactType(rs1.getString("contact_type_name"));
                propertyContact.setContactNumber(rs1.getString("contact_number"));
                contacts.add(propertyContact);
                return propertyContact;
            });

            return contacts;
        } else {
            throw new Exception("Contacts Unavailable");
        }
    }

    public Boolean deleteContact(DeleteContactRequest deleteContactRequest) throws PropertyAddException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqTENANT()) {

            Map<String, Object> map1 = new HashMap<>();
            map1.put("contactId", deleteContactRequest.getContactId());
            String sql = "DELETE  FROM tenant_contact WHERE id=:contactId";
            this.parameterJdbcTemplate.update(sql, map1);
            return true;
        } else {
            throw new PropertyAddException("Contacts can only be deleted by Tenant");
        }

    }
}
