package org.example.polezno.Repositories;

import org.example.polezno.Entities.Order;
import org.example.polezno.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(int id);
    List<Order> findByUser(User user);
}
