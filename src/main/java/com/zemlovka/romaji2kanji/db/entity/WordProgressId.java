package com.zemlovka.romaji2kanji.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class WordProgressId implements Serializable {

    private Integer word;
    private Integer user;
    private Integer tries;
}
