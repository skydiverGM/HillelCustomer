package imaks.hillelcustomer.dao;

import imaks.hillelcustomer.entity.Customer;
import imaks.hillelcustomer.exception.CustomerDAOException;
import imaks.hillelcustomer.mapper.CustomerRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerDAOImpl implements CustomerDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;


    @Transactional
    @Override
    public Long save(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO customer (full_name, email, social_security_number) VALUES (?, ?, ?)";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getSocialSecurityNumber());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        String sql = "select * from customer where id = ?";
        Customer customer = jdbcTemplate.queryForObject(sql, customerRowMapper, id);
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customer";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Transactional
    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customer SET full_name = ?, email = ?, social_security_number = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                customer.getFullName(),
                customer.getEmail(),
                customer.getSocialSecurityNumber(),
                customer.getId()
        );

        if (rowsAffected == 0) {
            throw new CustomerDAOException("Update failed");
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected == 0) {
            throw new CustomerDAOException("Delete failed");
        }
    }
}
