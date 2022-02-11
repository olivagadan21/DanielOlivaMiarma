package com.salesianostriana.dam.DanielOlivaMiarma.model;

import com.salesianostriana.dam.DanielOlivaMiarma.usuarios.model.Usuario;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publicacion implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String titulo;

    @Lob
    private String texto;

    private String fichero;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    private TipoPublicacion tipoPublicacion;

    public Publicacion(String titulo, String texto, String fichero, TipoPublicacion tipoPublicacion) {
        this.titulo = titulo;
        this.texto = texto;
        this.fichero = fichero;
        this.tipoPublicacion = tipoPublicacion;
    }

}
