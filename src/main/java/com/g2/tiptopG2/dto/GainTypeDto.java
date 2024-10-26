package com.g2.tiptopG2.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainTypeDto {
    private Integer id; // L'identifiant du gain
    private String description; // Le code du gain
}