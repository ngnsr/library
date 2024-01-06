package com.ridenrite.library.controller;

import com.ridenrite.library.service.ClientService;
import com.ridenrite.library.dto.ClientDto;
import com.ridenrite.library.entity.Client;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/client/{id}")
    public Client getClient(@PathVariable Long id){
        return clientService.get(id);
    }

    @GetMapping("/client")
    public Page<Client> getAllClients(@RequestParam Integer page,
                                      @RequestParam Integer size,
                                      @RequestParam(required = false) String query){
        Pageable pageable = PageRequest.of(page, size);
        return clientService.getAll(query, pageable);
    }

    @PostMapping("/client")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Client createClient(@Valid @RequestBody ClientDto dto){
        return clientService.create(dto);
    }

    @PutMapping("/client/{id}")
    public Client editClient(@PathVariable Long id,
                             @Valid @RequestBody ClientDto dto){
        return clientService.update(id, dto);
    }

    @DeleteMapping("/client/{id}")
    public void deleteClient(@PathVariable Long id){
        clientService.delete(id);
    }
}
