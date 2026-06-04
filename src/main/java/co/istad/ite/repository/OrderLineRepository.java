package co.istad.ite.repository;

import co.istad.ite.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {

}
