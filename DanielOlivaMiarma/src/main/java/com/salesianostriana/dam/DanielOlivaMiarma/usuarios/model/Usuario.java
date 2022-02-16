package com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model;

import com.salesianostriana.dam.DanielOlivaMiarma.model.Publicacion;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="usuarios")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private String apellidos;

    private String username;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String email;

    private String telefono;

    private LocalDate fechaNacimiento;

    private TipoVisualizacion tipoVisualizacion;

    private String avatar;

    private String password;

    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    @Builder.Default
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Publicacion> publicacionList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    List<Usuario> followings = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    List<Usuario> followers = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Usuario(String nombre, String apellidos, String username, String email, String telefono, LocalDate fechaNacimiento, TipoVisualizacion tipoVisualizacion, String avatar) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoVisualizacion = tipoVisualizacion;
        this.avatar = avatar;
    }
}