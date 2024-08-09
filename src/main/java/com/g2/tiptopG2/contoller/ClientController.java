package com.g2.tiptopG2.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g2.tiptopG2.dto.ClientRequestDto;
import com.g2.tiptopG2.dto.ClientResponseDto;

import com.g2.tiptopG2.service.IClientService;

@RestController
@RequestMapping("api/clients")
public class ClientController {
	@Autowired
	private IClientService clientService;
	
	@GetMapping("")
	public List<ClientResponseDto> getClients(){
		return clientService.findAll();
	}
	 @GetMapping("/{id}") // URL pour obtenir un client par son ID
	    public ClientResponseDto getClientById(@PathVariable Integer id) {
	        return clientService.findById(id);
	    }
	@PostMapping("")
	public ClientResponseDto save(ClientRequestDto clientRequestDto) {
		return clientService.save(clientRequestDto);
	}
	@PostMapping("/{id}")
    public ClientResponseDto updateClient(@PathVariable Integer id, @RequestBody ClientRequestDto clientRequestDto) {
        return clientService.update(clientRequestDto, id);
        
     
    }
	
}
