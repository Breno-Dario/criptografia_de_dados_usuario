package com.cryptographic.studyCryptographic.repositories;

import com.cryptographic.studyCryptographic.entities.client.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

  Optional<ClientEntity> findByLogin(String login);

  boolean existsByLogin(String login);
}
