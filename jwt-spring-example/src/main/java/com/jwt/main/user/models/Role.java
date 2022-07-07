package com.jwt.main.user.models;

import com.jwt.main.user.enums.ERole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @Column(name = "name")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ERole name;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(final ERole name) {
        this.name = name;
    }
}
