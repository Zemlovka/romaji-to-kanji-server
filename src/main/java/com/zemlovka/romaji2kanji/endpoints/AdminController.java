package com.zemlovka.romaji2kanji.endpoints;

import com.zemlovka.romaji2kanji.db.entity.Report;
import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.entity.Word;
import com.zemlovka.romaji2kanji.db.service.UserService;
import com.zemlovka.romaji2kanji.db.service.WordService;
import com.zemlovka.romaji2kanji.endpoints.dto.*;
import com.zemlovka.romaji2kanji.endpoints.exceptions.WordIdNotPresentExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path="/reports/all", produces = "application/json")
    public ResponseEntity<List<ReportDTOOut>> getReports(){
        return ResponseEntity.ok(Mapper.mapReport(wordService.getAllReports()));
    }


    @GetMapping(path = "/users/{username}", produces = "application/json")
    public ResponseEntity<UserCompleteDTO> userCompleteDTOResponseEntity(@PathVariable String username) {
        return ResponseEntity.ok(Mapper.mapUserComplete(userService.loadUserByUsername(username)));
    }

    @PostMapping(path = "/reports/edit", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReportDTOOut> updateReport(@RequestBody ReportEditDTOIn reportDTOIn) {
        Report report = wordService.updateReport(reportDTOIn.id(), reportDTOIn.state(), reportDTOIn.notes());
        return ResponseEntity.ok().body(Mapper.mapReport(report));
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

    @PostMapping(path = "/users/edit", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        User user = userService.updateUserRole(updateUserDTO.username(), updateUserDTO.role());
        return ResponseEntity.ok().body(Mapper.mapUser(user));
    }

    @DeleteMapping(path = "/reports/delete/{id}", produces = "application/json")
    public ResponseEntity deleteReport(@PathVariable int id) {
        wordService.deleteReport(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/words/delete/{id}", consumes = "application/json")
    public ResponseEntity deleteWord(@PathVariable int id) {
        wordService.deleteWord(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/users/delete/{username}", produces = "application/json")
    public ResponseEntity deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }
}
