package com.g2.tiptopG2.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.g2.tiptopG2.dao.IUserDao;

import com.g2.tiptopG2.dao.IGainDao;
import com.g2.tiptopG2.dto.GainDto;
import com.g2.tiptopG2.models.GainEntity;
import com.g2.tiptopG2.models.UserEntity;

@Service
public class GainServiceImp implements IGainService {
    private final IGainDao gainDao;
    private final ModelMapper modelMapper;
    private final IUserDao userDao;
    public GainServiceImp(IGainDao gainDao, ModelMapper modelMapper,IUserDao userDao) {
        this.gainDao = gainDao;
         this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

  

    @Override
    public GainDto findById(Integer id) {
        GainEntity gainEntity = gainDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Gain not found"));
        return modelMapper.map(gainEntity, GainDto.class);
    }

    @Override
    public GainDto findByCode(String code) {
        GainEntity gainEntity = gainDao.findByCode(code);
        if(gainEntity == null){
            return null;
        }
        return modelMapper.map(gainEntity, GainDto.class);
    }
    @Override
    public List<GainDto> findByUserIdIsNotNull() {
        List<GainEntity> gainEntities = gainDao.findByUserIdIsNotNull();
        return gainEntities.stream()
                .map(gain -> modelMapper.map(gain, GainDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<GainDto> findByUserId(Integer Id) {
        List<GainEntity> gainEntities = gainDao.findByUserId(Id);
        return gainEntities.stream()
                .map(gain -> modelMapper.map(gain, GainDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public GainDto updateUser(String gainCode, Integer userId) {
        GainEntity gainEntity = gainDao.findByCode(gainCode);
        if (gainEntity == null) {
        throw new RuntimeException("Gain not found");
        }
        // Récupérer l'entité UserEntity correspondant au userId
        UserEntity userEntity = userDao.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        // Mettre à jour uniquement le champ user de GainEntity
        gainEntity.setUser(userEntity);

        // Sauvegarder les modifications dans la base de données
        GainEntity updatedGain = gainDao.save(gainEntity);

        // Retourner l'objet DTO mis à jour
        return modelMapper.map(updatedGain, GainDto.class);
    }

    @Override
    public GainDto updateRemis(Integer gainId) {
        GainEntity gainEntity =gainDao.findById(gainId)
                .orElseThrow(() -> new RuntimeException("Gain not found"));
        gainEntity.setRemis(true);
        GainEntity updatedGain = gainDao.save(gainEntity);
        return modelMapper.map(updatedGain, GainDto.class);
    }
    @Override
    public GainDto findByCodeAndUserIsNull(String code){
        GainEntity gainEntity = gainDao.findByCodeAndUserIsNull(code);
        if (gainEntity == null) {
            return null; }
        return modelMapper.map(gainEntity, GainDto.class);
    }
}
