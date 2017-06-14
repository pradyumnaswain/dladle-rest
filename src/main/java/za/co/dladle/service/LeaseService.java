package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.PropertyView;
import za.co.dladle.entity.TenantLeaseView;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.model.LeaseLandlord;
import za.co.dladle.model.LeaseTenant;
import za.co.dladle.model.User;
import za.co.dladle.session.UserSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 6/14/2017.
 */
@Service
public class LeaseService {

    @Autowired
    private UserServiceUtility userServiceUtility;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    public LeaseLandlord viewLease(long houseId) throws UserNotFoundException {
        LeaseLandlord leaseLandlord = new LeaseLandlord();
        Map<String, Object> map = new HashMap<>();

        map.put("houseId", houseId);

        String sql = "SELECT * FROM lease  WHERE lease.house_id=:houseId AND lease_status=TRUE ";
        leaseLandlord = this.jdbcTemplate.query(sql, map, (rs, rowNum) -> {
            LeaseLandlord leaseTenant1 = new LeaseLandlord();
            leaseTenant1.setLeaseStartDate(rs.getDate("lease_start_date"));
            leaseTenant1.setLeaseEndDate(rs.getDate("lease_end_date"));
            leaseTenant1.setLeaseRenewalDate(rs.getDate("lease_renewal_date"));
            leaseTenant1.setLeaseTerminateDate(rs.getDate("lease_terminate_date"));

            map.put("leaseId", rs.getLong("id"));

            List<TenantLeaseView> tenantLeaseViews = new ArrayList<>();
            String sql1 = "SELECT * FROM lease_tenant " +
                    " INNER JOIN tenant ON tenant.id=lease_tenant.tenant_id " +
                    " INNER JOIN user_dladle ON user_dladle.id=tenant.user_id " +
                    "WHERE lease_id=:leaseId ";
            this.jdbcTemplate.query(sql1, map, (rs1, rowNum1) -> {
                TenantLeaseView user = new TenantLeaseView(rs1.getString("emailId"),
                        rs1.getString("first_name"),
                        rs1.getString("last_name"),
                        rs1.getString("cell_number"),
                        rs1.getString("profile_picture"),
                        rs1.getString("id_number"),
                        rs1.getDate("joined_date"));
                tenantLeaseViews.add(user);
                return user;
            });
            leaseTenant1.setTenantList(tenantLeaseViews);

            return leaseTenant1;
        }).get(0);

        return leaseLandlord;
    }

    public LeaseTenant viewLease() throws Exception {
        LeaseTenant leaseTenant = new LeaseTenant();
        Map<String, Object> map = new HashMap<>();
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqTENANT()) {
            Long tenantId = userServiceUtility.findTenantIdByEmail(userSession.getUser().getEmailId());
            map.put("tenantId", tenantId);

            String sql = "SELECT * FROM lease INNER JOIN lease_tenant ON lease.id = lease_tenant.lease_id " +
                    "INNER JOIN house ON lease.house_id = house.id " +
                    "INNER JOIN property ON house.property_id = property.id " +
                    "INNER JOIN landlord ON property.landlord_id = landlord.id " +
                    "INNER JOIN user_dladle ON landlord.user_id = user_dladle.id " +
                    "WHERE tenant_id=:tenantId AND lease_status=TRUE ";
            leaseTenant = this.jdbcTemplate.query(sql, map, (rs, rowNum) -> {
                LeaseTenant leaseTenant1 = new LeaseTenant();
                User user = new User(rs.getString("emailId"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("cell_number"),
                        rs.getString("profile_picture"),
                        rs.getString("id_number"));
                leaseTenant1.setLandlord(user);
                PropertyView property = new PropertyView();
                property.setAddress(rs.getString("address"));
                property.setComplexName(rs.getString("complex_name"));
                property.setEstate(rs.getBoolean("isestate"));
                property.setEstateName(rs.getString("estate_name"));
                property.setPlaceImage(rs.getString("image_url"));
                property.setUnitNumber(rs.getString("unit_number"));

                leaseTenant1.setProperty(property);

                leaseTenant1.setLeaseStartDate(rs.getDate("lease_start_date"));
                leaseTenant1.setLeaseEndDate(rs.getDate("lease_end_date"));
                leaseTenant1.setLeaseRenewalDate(rs.getDate("lease_renewal_date"));
                leaseTenant1.setLeaseTerminateDate(rs.getDate("lease_terminate_date"));
                return leaseTenant1;
            }).get(0);

            return leaseTenant;
        } else {
            throw new Exception("This API can only accessed via Tenant");
        }

    }

    void createOrUpdateLease(Long houseId, Long tenantId) {
        Map<String, Object> map = new HashMap<>();
        map.put("houseId", houseId);
        map.put("tenantId", tenantId);
        try {
            String sql = "SELECT id FROM lease WHERE house_id=:houseId AND lease_status=TRUE ";
            Long leaseId = this.jdbcTemplate.queryForObject(sql, map, Long.class);
            map.put("leaseId", leaseId);
            map.put("joinedDate", LocalDate.now());
            String sql1 = "INSERT INTO lease_tenant(lease_id, tenant_id, joined_date) VALUES (:leaseId,:tenantId,:joinedDate) ON CONFLICT DO NOTHING ";
            this.jdbcTemplate.update(sql1, map);

        } catch (EmptyResultDataAccessException e) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("houseId", houseId)
                    .addValue("leaseStartDate", LocalDate.now())
                    .addValue("leaseEndDate", LocalDate.now().plusYears(1))
                    .addValue("leaseRenewalDate", LocalDate.now().plusYears(1));

            String sql2 = "INSERT INTO lease(lease_start_date, lease_end_date, lease_renewal_date, house_id, lease_status) VALUES (:leaseStartDate,:leaseEndDate,:leaseRenewalDate,:houseId,TRUE ) ON CONFLICT DO NOTHING ";
            this.jdbcTemplate.update(sql2, mapSqlParameterSource, keyHolder, new String[]{"id"});
            map.put("leaseId", keyHolder.getKey().longValue());
            map.put("joinedDate", LocalDate.now());

            String sql1 = "INSERT INTO lease_tenant(lease_id, tenant_id, joined_date) VALUES (:leaseId,:tenantId,:joinedDate)";
            this.jdbcTemplate.update(sql1, map);
        }
    }
}
