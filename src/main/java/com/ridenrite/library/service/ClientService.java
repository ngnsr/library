package com.ridenrite.library.service;

import com.ridenrite.library.dto.ClientDto;
import com.ridenrite.library.entity.Client;
import com.ridenrite.library.exception.ConflictException;
import com.ridenrite.library.exception.NotFoundException;
import com.ridenrite.library.repository.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client create(ClientDto dto){
        Optional<Client> optional = clientRepository.findByEmailOrPhone(dto.getEmail(), dto.getPhone());
        if(optional.isPresent()) {
            throw new ConflictException("Client already exist");
        }

        Client client = new Client(dto);
        return clientRepository.save(client);
    }

    public Client get(Long id){
       return clientRepository.findById(id)
               .orElseThrow(() -> new NotFoundException("Client not found"));
    }

    public Page<Client> getAll(String query, Pageable pageable){
        if(query != null){
            return clientRepository.findByQuery("%" + query + "%", pageable);
        }
        return clientRepository.findAll(pageable);
    }

    public Client update(Long id, ClientDto dto){
        Client original = get(id);
        original.setEmail(dto.getEmail());
        original.setPhone(dto.getPhone());
        original.setFirstName(dto.getFirstName());
        original.setLastName(dto.getLastName());
        return clientRepository.save(original);
    }

    public void delete(Long id){
        clientRepository.deleteById(id);
    }
}
