package com.g2.tiptopG2.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.g2.tiptopG2.dao.IGainDao;
import com.g2.tiptopG2.dto.GainDto;
import com.g2.tiptopG2.models.GainEntity;

/**
 * Implémentation du service pour gérer les gains.
 * Utilise un DAO pour accéder aux données et un ModelMapper pour convertir les entités en DTO.
 */
@Service
public class GainServiceImp implements IGainService {
    private final IGainDao gainDao;
    private final ModelMapper modelMapper;

    /**
     * Constructeur pour injecter les dépendances.
     * @param gainDao DAO pour accéder aux données des gains.
     * @param modelMapper Mapper pour convertir les entités en DTO.
     */
    public GainServiceImp(IGainDao gainDao, ModelMapper modelMapper) {
        this.gainDao = gainDao;
        this.modelMapper = modelMapper;
    }

    /**
     * Méthode pour trouver un gain par son identifiant.
     * @param id L'identifiant du gain.
     * @return Le DTO du gain correspondant.
     * @throws RuntimeException si le gain n'est pas trouvé.
     */
    @Override
    public GainDto findById(Integer id) {
        GainEntity gainEntity = gainDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Gain not found"));
        return modelMapper.map(gainEntity, GainDto.class);
    }

    /**
     * Méthode pour trouver un gain par son code.
     * @param code Le code du gain.
     * @return Le DTO du gain correspondant.
     */
    @Override
    public GainDto findByCode(String code) {
        GainEntity gainEntity = gainDao.findByCode(code);
        return modelMapper.map(gainEntity, GainDto.class);
    }
}