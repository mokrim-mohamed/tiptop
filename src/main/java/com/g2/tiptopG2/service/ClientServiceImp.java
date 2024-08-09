package com.g2.tiptopG2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.g2.tiptopG2.dao.IClientDao;
import com.g2.tiptopG2.dto.ClientRequestDto;
import com.g2.tiptopG2.dto.ClientResponseDto;
import com.g2.tiptopG2.models.ClientEntity;
@Service()
public class ClientServiceImp implements IClientService {
	private IClientDao clientDao;
	private ModelMapper modelmapper;
	
	public ClientServiceImp(IClientDao clientDao, ModelMapper modelmapper) {
		super();
		this.clientDao = clientDao;
		this.modelmapper = modelmapper;
	}

	@Override
	public ClientResponseDto save(ClientRequestDto clientRequestDto) {
		ClientEntity clientEntity=modelmapper.map(clientRequestDto, ClientEntity.class);
		ClientEntity saved=clientDao.save(clientEntity);
		return modelmapper.map(saved, ClientResponseDto.class);
	}

	@Override
	public ClientResponseDto findById(Integer id) {
		ClientEntity clientEntity=clientDao.findById(id).orElseThrow(()-> new RuntimeException("Client not found"));
		
		return modelmapper.map(clientEntity, ClientResponseDto.class);
	}

	@Override
	public ClientResponseDto findByNom(String nom) {
		ClientEntity clientEntity=clientDao.findByNom(nom);
		return modelmapper.map(clientEntity, ClientResponseDto.class);
			}

	@Override
	public ClientResponseDto update(ClientRequestDto clientRequestDto, Integer id) {
		Optional<ClientEntity> clientEntityOptional=clientDao.findById(id);
		if(clientEntityOptional.isPresent()) {
			ClientEntity clientEntity = modelmapper.map(clientRequestDto, ClientEntity.class);
			
			ClientEntity updated=clientDao.save(clientEntity);
			return modelmapper.map(updated,ClientResponseDto.class);
		}else {
			return null;
		}
		
	}

	@Override
	public List<ClientResponseDto> findAll() {
		
		return clientDao.findAll().stream().map(el->modelmapper.map(el, ClientResponseDto.class)).collect(Collectors.toList());
	}

}
