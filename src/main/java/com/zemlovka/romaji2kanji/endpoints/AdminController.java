package com.zemlovka.romaji2kanji.endpoints;

import com.zemlovka.romaji2kanji.db.entity.Report;
import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.entity.Word;
import com.zemlovka.romaji2kanji.db.service.UserService;
import com.zemlovka.romaji2kanji.db.service.WordService;
import com.zemlovka.romaji2kanji.endpoints.dto.*;
import com.zemlovka.romaji2kanji.endpoints.exceptions.WordIdNotPresentExceptions;
import com.zemlovka.romaji2kanji.endpoints.exceptions.WordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(value = "http://localhost:3000", exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"})
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final WordService wordService;
    private final UserService userService;

    @Autowired
    public AdminController(WordService wordService, UserService userService) {
        this.wordService = wordService;
        this.userService = userService;
    }

    @GetMapping(path="/users/all", produces = "application/json")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok().body(Mapper.mapUser(userService.getAllUsers()));
    }


    @GetMapping(path="/words/all", produces = "application/json")
    public ResponseEntity<List<WordDTO>> getWord(){
        return ResponseEntity.ok(Mapper.mapWord(wordService.getAllWords()));
    }

    @PostMapping(path = "/words/edit", produces = "application/json", consumes = "application/json")
    public ResponseEntity<WordDTO> updateWord(@RequestBody WordDTO wordDTO) {
        Word updatedWord;
        try {
            Word word = Mapper.mapWord(wordDTO);
            word.setId(wordDTO.id());
            updatedWord = wordService.updateWord(word);
        } catch (Exception e) {
            throw new WordIdNotPresentExceptions(e.getMessage());
        }
        return ResponseEntity.ok(Mapper.mapWord(updatedWord));
    }

    @GetMapping(path = "/users/{username}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserCompleteDTO> userCompleteDTOResponseEntity(@PathVariable String username) {
        return ResponseEntity.ok(Mapper.mapUserComplete(userService.loadUserByUsername(username)));
    }

    @PostMapping(path = "/words/report/edit", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReportDTOOut> updateReport(@RequestBody ReportDTOIn reportDTOIn, Authentication auth) {
        Word word = wordService.getWord(reportDTOIn.reportedWordId()).orElseThrow(() -> new WordNotFoundException("Word with this id has not been found"));
        User user = (User) auth.getPrincipal();
        Report report = wordService.saveReport(Mapper.mapReport(reportDTOIn), word, user);
        return ResponseEntity.ok().body(Mapper.mapReport(report));
    }
}
