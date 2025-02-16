package imaks.hillelcustomer.dao;

import imaks.hillelcustomer.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {

    Long save(Customer customer);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    void update(Customer customer);
    void delete(Long id);
}
