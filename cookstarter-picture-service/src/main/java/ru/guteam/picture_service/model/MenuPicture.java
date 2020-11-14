package ru.guteam.picture_service.model;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "MenuPicture")
public class MenuPicture extends AbsctractPicture {
}
