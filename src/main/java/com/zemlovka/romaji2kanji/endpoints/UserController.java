package com.zemlovka.romaji2kanji.endpoints;

import com.zemlovka.romaji2kanji.db.entity.Report;
import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.service.UserService;
import com.zemlovka.romaji2kanji.endpoints.dto.*;
import com.zemlovka.romaji2kanji.endpoints.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> login(Authentication auth) {
        return ResponseEntity.ok().body(Mapper.mapUser((User) auth.getPrincipal()));
    }

    @PostMapping(path="/users/register", produces = "application/json")
    public ResponseEntity<UserDTO> register(@RequestBody NewUserDTO newUserDTO) {
        User user = Mapper.mapUser(newUserDTO);
        if (userService.userExists(user.getUsername()))
            throw new UserAlreadyExistsException("User with this username already exists.");
        return ResponseEntity.ok().body(Mapper.mapUser(userService.createUser(user)));
    }

    @PostMapping(path = "/change-password", consumes = "application/json")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        if (changePasswordDTO.oldPassword().equals(changePasswordDTO.newPassword()))
            return ResponseEntity.badRequest().build();
        userService.changePassword(changePasswordDTO.oldPassword(), changePasswordDTO.newPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/users/{username}", produces = "application/json")
    public ResponseEntity<UserCompleteDTO> getUser(@PathVariable String username) {
        return ResponseEntity.ok(Mapper.mapUserComplete(userService.loadUserByUsername(username)));
    }

    @GetMapping(path="/users/{username}/words", produces = "application/json")
    public ResponseEntity<List<WordDTO>> getUserWords(@PathVariable String username) {
        return ResponseEntity.ok().body(Mapper.mapWord(userService.loadUserByUsername(username).getCreatedWords()));
    }

    @GetMapping(path="/users/words", produces = "application/json")
    public ModelAndView getUserWords(Authentication auth, ModelMap model) {
        return new ModelAndView("forward:/users/" + ((User)auth.getPrincipal()).getUsername()+"/words", model);
    }

    @GetMapping(path="/users/{username}/solved", produces = "application/json")
    public ResponseEntity<List<WordProgressDTO>> getSolvedWords(@PathVariable String username) {
        return ResponseEntity.ok().body(Mapper.mapWordProgress(userService.loadUserByUsername(username).getWordProgresses()));
    }

    @GetMapping(path="/users/solved", produces = "application/json")
    public ModelAndView getSolvedWords(Authentication auth, ModelMap model) {
        return new ModelAndView("forward:/users/" + ((User)auth.getPrincipal()).getUsername()+"/solved", model);
    }

    @GetMapping(path="/users/{username}/reports", produces = "application/json")
    public ResponseEntity<List<ReportDTOOut>> getReports(@PathVariable String username) {
        return ResponseEntity.ok().body(Mapper.mapReport(userService.loadUserByUsername(username).getReports()));
    }

    @GetMapping(path="/users/reports", produces = "application/json")
    public ModelAndView getReports(Authentication auth, ModelMap model) {
        return new ModelAndView("forward:/users/" + ((User)auth.getPrincipal()).getUsername()+"/reports", model);
    }
}
