package com.zemlovka.romaji2kanji.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@EqualsAndHashCode
public class FollowId implements Serializable {
    private Integer followingUser;
    private Integer followedUser;
}
