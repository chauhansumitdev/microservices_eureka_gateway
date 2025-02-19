package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id")
        private UUID id;

        @Column(name = "name")
        private String name;

        @Column(name = "email")
        private String email;

        @Column(name = "address_id")
        private UUID addressID;
}
