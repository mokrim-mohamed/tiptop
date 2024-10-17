package com.g2.tiptopG2.service;

import java.util.List;

import com.g2.tiptopG2.dto.GainRequestDto;
import com.g2.tiptopG2.dto.GainResponseDto;

public interface IGainService {
    GainResponseDto findById(Integer id);
    GainResponseDto findByCode(String code);
    //List<GainResponseDto> findAll();
}
