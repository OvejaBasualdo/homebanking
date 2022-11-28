package com.minduhub.homebanking.repositories;

import com.minduhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client,Long> {

    List<Client> findByLastNameIgnoreCase(String lastName);//-Buscar una lista de clientes por apellido
    //IgnoreCase ignora las mayúsculas
    List<Client> findByFirstNameIgnoreCaseOrderByLastNameAsc(String firstName);//-Buscar una lista de clientes por nombre
    Optional<Client> findByEmail(String email);

    Optional<Client> findByFirstNameIgnoreCaseAndEmailIgnoreCase(String firstName, String email);//-Buscar un cliente por Nombre y Email

}
