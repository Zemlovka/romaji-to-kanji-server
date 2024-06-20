package com.zemlovka.romaji2kanji.endpoints;


import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.entity.Word;
import com.zemlovka.romaji2kanji.db.entity.WordProgress;
import com.zemlovka.romaji2kanji.db.service.UserService;
import com.zemlovka.romaji2kanji.db.service.WordService;
import com.zemlovka.romaji2kanji.endpoints.dto.GuessDTO;
import com.zemlovka.romaji2kanji.endpoints.dto.WordDTO;
import com.zemlovka.romaji2kanji.endpoints.dto.WordProgressDTO;
import com.zemlovka.romaji2kanji.endpoints.exceptions.WordIdNotPresentExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
public class WordsController {
    private final static Integer DEFAULT_SENT_WORDS_NUMBER = 10;
    private WordService wordService;
    private UserService userService;

    public WordsController(WordService wordService, UserService userService) {
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
        List<WordDTO> words = Mapper.mapWord(wordService.getRandomWords(isKatakana, isHiragana, wordNumber));
        return ResponseEntity.ok(words);
    }

    @PostMapping(path="/words", consumes = "application/json", produces = "application/json")
    public ResponseEntity<WordDTO> postWord(@RequestBody WordDTO wordDTO, Authentication auth) {
        Word word = Mapper.mapWord(wordDTO);
        word.setCreatedBy((User) auth.getPrincipal());
        return ResponseEntity.ok(Mapper.mapWord(wordService.insertWord(word)));
    }

    @PostMapping(path="/guess", consumes = "application/json", produces = "application/json")
    public ResponseEntity<WordProgressDTO> guess(@RequestBody GuessDTO guessDTO, Authentication auth) {
        Word word = wordService.getWord(guessDTO.id()).orElseThrow(() -> new WordIdNotPresentExceptions(""));
        WordProgress wordProgress = new WordProgress();
        wordProgress.setWord(word);
        wordProgress.setUser((User) auth.getPrincipal());
        wordProgress.setSuccessful(guessDTO.successful());
        wordService.insertWordProgress(wordProgress);
        return ResponseEntity.ok(Mapper.mapWordProgress(wordProgress));
    }
}