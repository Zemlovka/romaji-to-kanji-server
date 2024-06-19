package com.zemlovka.romaji2kanji.endpoints;


import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.entity.Word;
import com.zemlovka.romaji2kanji.db.service.UserService;
import com.zemlovka.romaji2kanji.db.service.WordService;
import com.zemlovka.romaji2kanji.endpoints.dto.WordDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class Controller {
    private final static Integer DEFAULT_SENT_WORDS_NUMBER = 10;
    private WordService wordService;
    private UserService userService;

    public Controller(WordService wordService, UserService userService) {
        this.wordService = wordService;
        this.userService = userService;
    }

    @GetMapping(path="/words/{id}", produces = "application/json")
    public ResponseEntity<WordDTO> getWord(@PathVariable int id){
        return null;
    }

    @GetMapping(path="/words", produces = "application/json")
    public ResponseEntity<List<WordDTO>> getWords(@RequestParam(name = "n") Integer wordNumber,
                                                  @RequestParam(name = "k") Boolean isKatakana,
                                                  @RequestParam(name = "h") Boolean isHiragana) {
        if (wordNumber == null) wordNumber = DEFAULT_SENT_WORDS_NUMBER;
        if (isKatakana == null) isKatakana = false;
        if (isHiragana == null) isHiragana = false;
        return ResponseEntity.ok(Mapper.mapWord(wordService.getRandomWords(isKatakana, isHiragana, wordNumber)));
    }

    @PostMapping(path="/words", consumes = "application/json", produces = "application/json")
    public ResponseEntity<WordDTO> postWord(@RequestBody WordDTO wordDTO, Authentication auth) {
        Word word = Mapper.mapWord(wordDTO);
        word.setCreatedBy((User) auth.getPrincipal());
        return ResponseEntity.ok(Mapper.mapWord(wordService.insertWord(word)));
    }
}