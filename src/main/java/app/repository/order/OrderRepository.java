package app.repository.order;

import app.model.entity.order.Order;
import app.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByUserOrderByCreatedOnDesc(User user);

    List<Order> findAllByOrderByCreatedOnDesc();

}
