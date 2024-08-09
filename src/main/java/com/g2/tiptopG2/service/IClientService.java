package com.g2.tiptopG2.service;

import java.util.List;

import com.g2.tiptopG2.dto.ClientRequestDto;
import com.g2.tiptopG2.dto.ClientResponseDto;

public interface IClientService {
	ClientResponseDto save(ClientRequestDto clientRequestDto);
	ClientResponseDto findById(Integer id);
	ClientResponseDto findByNom(String nom);
	ClientResponseDto update(ClientRequestDto clientRequestDto, Integer id);
	List<ClientResponseDto> findAll();
}
