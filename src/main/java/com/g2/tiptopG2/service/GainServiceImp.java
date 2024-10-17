package com.g2.tiptopG2.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.g2.tiptopG2.dao.IGainDao;
import com.g2.tiptopG2.dto.GainRequestDto;
import com.g2.tiptopG2.dto.GainResponseDto;
import com.g2.tiptopG2.models.GainEntity;

@Service
public class GainServiceImp implements IGainService {
    private final IGainDao gainDao;
    private final ModelMapper modelMapper;

    public GainServiceImp(IGainDao gainDao, ModelMapper modelMapper) {
        this.gainDao = gainDao;
        this.modelMapper = modelMapper;
    }

  

    @Override
    public GainResponseDto findById(Integer id) {
        GainEntity gainEntity = gainDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Gain not found"));
        return modelMapper.map(gainEntity, GainResponseDto.class);
    }

    @Override
    public GainResponseDto findByCode(String code) {
        GainEntity gainEntity = gainDao.findByCode(code);
        return modelMapper.map(gainEntity, GainResponseDto.class);
    }

    
}
