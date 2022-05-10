package com.balita.edi.clients;

import com.balita.edi.utils.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/clients")
public class ClientApi {
    private ClientRepository clientRepository;

    public ClientApi(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public ResponseEntity clientList() {
        return ResponseEntity.ok(clientRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity clientGet(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity clientCreate(@RequestBody @Valid ClientRequest clientRequest, HttpServletRequest request) {
        Client saved = clientRepository.save(
                Client.builder()
                        .name(clientRequest.getName())
                        .domaine(clientRequest.getDomaine())
                        .build()
        );
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/clients/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri()
        ).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity clientUpdate(@PathVariable("id") Long id, @RequestBody @Valid ClientRequest clientRequest) {
        Client existed = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        existed.setName(clientRequest.getName());
        existed.setDomaine(clientRequest.getDomaine());
        clientRepository.save(existed);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity clientDelete(@PathVariable("id") Long id) {
        Client existed = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        clientRepository.delete(existed);
        return ResponseEntity.noContent().build();
    }
}
