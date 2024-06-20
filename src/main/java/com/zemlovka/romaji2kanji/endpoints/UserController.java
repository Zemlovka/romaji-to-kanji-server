package com.zemlovka.romaji2kanji.endpoints;

import com.zemlovka.romaji2kanji.db.entity.User;
import com.zemlovka.romaji2kanji.db.service.UserService;
import com.zemlovka.romaji2kanji.endpoints.dto.NewUserDTO;
import com.zemlovka.romaji2kanji.endpoints.dto.UserDTO;
import com.zemlovka.romaji2kanji.endpoints.dto.WordDTO;
import com.zemlovka.romaji2kanji.endpoints.exceptions.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@CrossOrigin
@RestController
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path="/users/register", produces = "application/json")
    public ResponseEntity<UserDTO> register(@RequestBody NewUserDTO newUserDTO) {
        User user = Mapper.mapUser(newUserDTO);
        if (userService.userExists(user.getUsername()))
            throw new UserAlreadyExistsException("User with this username already exists.");
        return ResponseEntity.ok().body(Mapper.mapUser(userService.createUser(user)));
    }

    @GetMapping(path="/users/{username}", produces = "application/json")
    public ResponseEntity<UserDTO> register(@PathVariable String username) {
        return ResponseEntity.ok().body(Mapper.mapUser(userService.loadUserByUsername(username)));
    }

    @GetMapping(path="/users/{username}/words", produces = "application/json")
    public ResponseEntity<List<WordDTO>> getUserWords(@PathVariable String username) {
        return ResponseEntity.ok().body(Mapper.mapWord(userService.loadUserByUsername(username).getCreatedWords()));
    }

    @GetMapping(path="/users/words", produces = "application/json")
    public ModelAndView getUserWords(Authentication auth, ModelMap model) {
        return new ModelAndView("forward:/users/" + ((User)auth.getPrincipal()).getUsername()+"/words", model);
    }
}
