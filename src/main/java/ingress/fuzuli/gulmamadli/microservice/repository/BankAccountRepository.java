package ingress.fuzuli.gulmamadli.microservice.repository;

import ingress.fuzuli.gulmamadli.microservice.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
