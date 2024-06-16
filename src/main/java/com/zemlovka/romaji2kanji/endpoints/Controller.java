package com.zemlovka.romaji2kanji.endpoints;


import com.zemlovka.romaji2kanji.endpoints.dto.WordDTO;
import com.zemlovka.romaji2kanji.db.repository.WordRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class Controller {
    private final static int DEFAULT_SENT_WORDS_NUMBER = 10;
    WordRepository wordRepository;

    @GetMapping(path="/words/{id}", produces = "application/json")
    public ResponseEntity<WordDTO> getWord(@PathVariable int id){

    }

    @GetMapping(path="/words/hiragana", produces = "application/json")
    public ResponseEntity<List<WordDTO>> getWords(@RequestParam(name = "n") Integer wordNumber,
                                                  @RequestParam(name = "k") Boolean isKatakana,
                                                  @RequestParam(name = "h") Boolean isHiragana) {

    }
}