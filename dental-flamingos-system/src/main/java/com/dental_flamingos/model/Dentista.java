package com.dental_flamingos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "dentistas")
public class Dentista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDentista;
    private String nombre;
    @Column(name = "numero_celular")
    private String numeroCelular;
    private String usuario;
    @Column(name = "dent_password")
    private String password;

}
