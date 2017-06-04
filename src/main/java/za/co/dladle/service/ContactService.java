package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.ContactAddRequest;
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

    public List<Contact> listContacts() {
        return null;
    }
}
