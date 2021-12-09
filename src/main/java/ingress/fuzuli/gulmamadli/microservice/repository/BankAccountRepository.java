package ingress.fuzuli.gulmamadli.microservice.repository;

import ingress.fuzuli.gulmamadli.microservice.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<BankAccount> findByOwnerName(String ownerName);

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Query("select ba from BankAccount ba")
    List<BankAccount> getAllBa();
}
