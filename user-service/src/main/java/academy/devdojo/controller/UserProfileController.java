package academy.devdojo.controller;


import academy.devdojo.domain.UserProfile;
import academy.devdojo.mapper.ProfileMapper;
import academy.devdojo.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/user-profiles", "v1/user-profiles/"})
@Log4j2
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;
    private final ProfileMapper profileMapper;

    @GetMapping
    public ResponseEntity<List<UserProfile>> list() {
        log.debug("Request received to list all user profiles");

        var userProfiles = service.findAll();


        return ResponseEntity.ok(userProfiles);
    }
}
