package br.com.vital.controle_servico.users.repository;

import br.com.vital.controle_servico.users.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends org.springframework.data.repository.Repository<User, Long> {

    Optional<User> findByUsername(String username);

}
