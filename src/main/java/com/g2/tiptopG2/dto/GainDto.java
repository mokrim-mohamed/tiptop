package com.g2.tiptopG2.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainDto {
    private Integer id;
    private String code; // Le code du gain
    private Integer userId; // ID de l'utilisateur qui a gagné
    private Integer gainTypeId; // ID du type de gain
}