package ru.hogwarts.school.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;
    private int maxFileSizeInB = 300 * 1024;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@RequestParam Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > maxFileSizeInB) {
            return ResponseEntity
                    .badRequest()
                    .body("Изображение слишком большое!");
        }
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity
                .ok()
                .body("Изображение добавлено!");
    }

    @GetMapping("/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable long id) {
        Avatar avatar = avatarService.readFromDB(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(avatar.getData());
    }

    @GetMapping("/{id}/avatar-from-file")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) throws IOException {
        File avatar = avatarService.readFromFile(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(Files.probeContentType(avatar.toPath())));
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(Files.readAllBytes(avatar.toPath()));

    }

  /*  @GetMapping(value = "/{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }*/

    @GetMapping(value = "/all")
    public Page<Avatar> getAllAvatars(@RequestParam("page") int page, @RequestParam("size") int size) {
        return avatarService.getAllAvatars(page, size);
    }
}

