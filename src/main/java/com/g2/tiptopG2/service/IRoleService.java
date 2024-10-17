package com.g2.tiptopG2.service;

import java.util.List;

import com.g2.tiptopG2.dto.RoleRequestDto;
import com.g2.tiptopG2.dto.RoleResponseDto;

public interface IRoleService {
    RoleResponseDto findById(Integer id);
    RoleResponseDto findByRole(String role);  // Si vous avez besoin de rechercher par nom de rôle
    List<RoleResponseDto> findAll();
}
