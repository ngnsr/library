package com.ridenrite.library.repository;

import com.ridenrite.library.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
    Optional<Client> findByEmailOrPhone(String email, String phone);

    Optional<Client> findById(Long id);

    Client save(Client client);

    void deleteById(Long id);

    @Query("select client from Client client " +
            "where lower(client.firstName) like :query " +
            "or lower(client.lastName) like :query " +
            "or lower(client.phone) like :query " +
            "or lower(client.email) like :query")
    Page<Client> findByQuery(String query, Pageable pageable);

    void deleteAll();
}
