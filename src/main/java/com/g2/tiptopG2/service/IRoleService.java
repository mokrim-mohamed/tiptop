package com.g2.tiptopG2.service;

import java.util.List;

import com.g2.tiptopG2.dto.RoleDto;
import com.g2.tiptopG2.dto.RoleDto;

public interface IRoleService {
    RoleDto findById(Integer id);
    RoleDto findByRole(String role);  // Si vous avez besoin de rechercher par nom de rôle
    List<RoleDto> findAll();
}
