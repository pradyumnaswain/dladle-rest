package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.DeleteContactRequest;
import za.co.dladle.entity.PropertyContactView;
import za.co.dladle.exception.PropertyAddException;
import za.co.dladle.mapper.ContactTypeMapper;
import za.co.dladle.model.PropertyContact;
import za.co.dladle.session.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 6/18/2017.
 */
@Service
public class PropertyContactService {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    public Boolean addContact(List<PropertyContact> propertyContactList, Long houseId) throws PropertyAddException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqLANDLORD()) {
            List<Map<String, Object>> list = new ArrayList<>();

            for (PropertyContact propertyContact : propertyContactList) {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("houseId", houseId);
                map1.put("contactType", ContactTypeMapper.getPropertyContactType(propertyContact.getContactType()));
                map1.put("name", propertyContact.getName());
                map1.put("address", propertyContact.getAddress());
                map1.put("contactNumber", propertyContact.getContactNumber());
                list.add(map1);
            }
            String sql = "INSERT INTO property_contact(house_id, contact_type_id, name, address, contact_number) VALUES (:houseId,:contactType,:name,:address,:contactNumber)";
            this.parameterJdbcTemplate.batchUpdate(sql, list.toArray(new Map[propertyContactList.size()]));

            return true;
        } else {
            throw new PropertyAddException("Property contact can only be added by Landlord");
        }
    }

    public Boolean deleteContact(DeleteContactRequest deleteContactRequest) throws PropertyAddException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqLANDLORD()) {

            Map<String, Object> map1 = new HashMap<>();
            map1.put("contactId", deleteContactRequest.getContactId());
            String sql = "DELETE  FROM property_contact WHERE id=:contactId";
            this.parameterJdbcTemplate.update(sql, map1);
            return true;
        } else {
            throw new PropertyAddException("Property Contact can ony be deleted by Landlord");
        }
    }

    public List<PropertyContactView> listContactsOfProperty(long houseId) {
        Map<String, Object> map = new HashMap<>();
        map.put("houseId", houseId);

        List<PropertyContactView> propertyContactViews = new ArrayList<>();
        String sql = "SELECT property_contact.*,property_contact.name property_conact_name, contact_type.name contact_type_name FROM property_contact INNER JOIN contact_type ON property_contact.contact_type_id = contact_type.id WHERE house_id=:houseId";
        this.parameterJdbcTemplate.query(sql, map, (rs, rowNum) -> {
            PropertyContactView propertyContact = new PropertyContactView();
            propertyContact.setPropertyContactId(rs.getLong("id"));
            propertyContact.setAddress(rs.getString("address"));
            propertyContact.setName(rs.getString("property_conact_name"));
            propertyContact.setContactType(rs.getString("contact_type_name"));
            propertyContact.setContactNumber(rs.getString("contact_number"));
            propertyContactViews.add(propertyContact);
            return propertyContact;
        });
        return propertyContactViews;
    }


}
