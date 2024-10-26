package com.g2.tiptopG2.service;

import java.util.List;

import com.g2.tiptopG2.dto.GainDto;
import com.g2.tiptopG2.dto.GainDto;

public interface IGainService {
    GainDto findById(Integer id);
    GainDto findByCode(String code);
    List<GainDto> findByUserIdIsNotNull();
    GainDto updateUser(Integer gainId, Integer userId);
    List<GainDto> findByUserId(Integer Id);

}
