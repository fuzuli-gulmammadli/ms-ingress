package ingress.fuzuli.gulmamadli.microservice.repository;

import ingress.fuzuli.gulmamadli.microservice.entity.MicroServiceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MicroServiceUserRepository extends JpaRepository<MicroServiceUser, Long> {
}
