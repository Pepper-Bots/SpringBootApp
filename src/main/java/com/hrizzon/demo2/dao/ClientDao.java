package com.hrizzon.demo2.dao;

import com.hrizzon.demo2.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// ou @Service > pour des classes qui vont donner des fonctionnalités
// ou @Component > pour faire marcher l'app
// pour signifier que c'est une interface = créé une dépendance
@Repository  // > fait un truc en plus
public interface ClientDao extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);
}
