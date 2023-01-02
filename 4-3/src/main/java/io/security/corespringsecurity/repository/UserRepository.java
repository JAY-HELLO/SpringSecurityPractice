package io.security.corespringsecurity.repository;

import io.security.corespringsecurity.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface UserRepository extends JpaRepository<Account, Long> {
}
