package pl.nogacz.rsq.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "patients")
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Column(name = "address")
    private String address;

    @OneToMany(
            targetEntity = Visit.class,
            cascade = CascadeType.ALL,
            mappedBy = "patient",
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Visit> visits = new ArrayList<>();
}
