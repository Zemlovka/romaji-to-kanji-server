package com.zemlovka.romaji2kanji.db.entitie;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class SentenceWordId implements Serializable {

    private Integer word;
    private Integer sentence;
    private Integer wordOrder;
}
