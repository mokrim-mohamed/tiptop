package com.g2.tiptopG2.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.g2.tiptopG2.dao.IRoleDao; // Assurez-vous que cette interface existe
import com.g2.tiptopG2.dto.RoleDto;
import com.g2.tiptopG2.models.RoleEntity;

@Service
public class RoleServiceImp implements IRoleService {
    private IRoleDao roleDao; // DAO pour les opérations sur les rôles
    private ModelMapper modelMapper; // Pour la conversion entre entités et DTOs

    public RoleServiceImp(IRoleDao roleDao, ModelMapper modelMapper) {
        this.roleDao = roleDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleDto findById(Integer id) {
        RoleEntity roleEntity = roleDao.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found"));
        return modelMapper.map(roleEntity, RoleDto.class);
    }

    @Override
    public RoleDto findByRole(String role) {
        RoleEntity roleEntity = roleDao.findByRole(role);
        if (roleEntity == null) {
            throw new RuntimeException("Role not found");
        }
        return modelMapper.map(roleEntity, RoleDto.class);
    }

    @Override
    public List<RoleDto> findAll() {
        return roleDao.findAll().stream()
            .map(roleEntity -> modelMapper.map(roleEntity, RoleDto.class))
            .collect(Collectors.toList());
    }
}
