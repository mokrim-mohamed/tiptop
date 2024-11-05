package com.g2.tiptopG2.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;   // Import de la classe List
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String nom;
    private String prenom;
    private String sexe;
    private String email;
    private String telephone;
    private Integer roleId; 
    private String motDePasse;
    private int age;


}
