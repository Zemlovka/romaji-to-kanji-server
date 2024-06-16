package com.zemlovka.romaji2kanji.db.entitie;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@EqualsAndHashCode
public class AchievmentProgressId implements Serializable {
    private Integer user;
    private Integer achievement;
}
