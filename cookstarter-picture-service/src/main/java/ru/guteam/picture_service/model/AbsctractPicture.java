package ru.guteam.picture_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;


@MappedSuperclass
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbsctractPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "path")
    private String path;
}
