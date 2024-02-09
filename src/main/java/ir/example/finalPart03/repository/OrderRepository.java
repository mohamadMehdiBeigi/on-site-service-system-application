package ir.example.finalPart03.repository;

import ir.example.finalPart03.model.Order;
import ir.example.finalPart03.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.subServices in (select ss from SubServices ss join ss.specialists s where s.id = :specialistId)")
    List<Order> findOrdersBySpecialistSubServicesAndStatus(@Param("specialistId") Long specialistId);

    @Query(nativeQuery = true, value =
            "select \"order\".* " +
                    "from final_part3.\"order\" " +
                    "join final_part3.sub_services ss on ss.id = \"order\".sub_services_id " +
                    "join final_part3.users_sub_services uss on ss.id = uss.sub_service_id " +
                    "join final_part3.users u on u.id = uss.user_id " +
                    "where u.id =:specialistId " +
                    "and u.dtype ='Specialist' " +
                    "and (\"order\".order_status = 'WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST' " +
                    "or \"order\".order_status = 'WAITING_FOR_SPECIALIST_SELECTION')"
    )
    List<Order> findAllByOrderStatusAndSpecialistId(Long specialistId);

    @Query(
            "from Order o " +
                    "where o.id =:id " +
                    "and o.customer.id =:customerId"
    )
    Optional<Order> findByIdAndCustomerId(Long id, Long customerId);


    List<Order> findAllByOrderStatus(OrderStatus orderStatus);

}
