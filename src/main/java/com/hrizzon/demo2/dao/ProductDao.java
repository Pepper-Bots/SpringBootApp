package com.hrizzon.demo2.dao;

import com.hrizzon.demo2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ou @Service > pour des classes qui vont donner des fonctionnalités
// ou @Component > pour faire marcher l'app
// pour signifier que c'est une interface = créé une dépendance
@Repository  // > fait un truc en plus
public interface ProductDao extends JpaRepository<Product, Integer> {


}
