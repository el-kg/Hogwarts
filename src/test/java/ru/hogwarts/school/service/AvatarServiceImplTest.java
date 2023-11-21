package ru.hogwarts.school.service;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvatarServiceImplTest {
    StudentService studentService = mock(StudentService.class);
    AvatarRepository avatarRepository = mock(AvatarRepository.class);
    String avatarsDir = "./src/test/resources/avatar";
    AvatarServiceImpl avatarService = new AvatarServiceImpl(studentService, avatarRepository, avatarsDir);
    Student student = new Student(1L, "Potter", 13);

    @Test
    void uploadAvatar_shouldSaveAvatarToDataBase() throws IOException {
        MultipartFile file = new MockMultipartFile("13.pdf", "13.pdf", "application/pdf"
                , new byte[]{});
        when(studentService.read(student.getId())).thenReturn(student);
        when(avatarRepository.findByStudent_id(student.getId())).thenReturn(Optional.empty());

        avatarService.uploadAvatar(student.getId(), file);
        verify(avatarRepository, times(1)).save(any());
        assertTrue(FileUtils.canRead(new File(
                avatarsDir + "/" + student.getId() + "." +
                        file.getOriginalFilename().substring(
                                file.getOriginalFilename().lastIndexOf(".") + 1))));
    }

    @Test
    void readFromDB_shouldReadAvatar() {
        Avatar avatar = new Avatar();
        when(avatarRepository.findById(1L)).thenReturn(Optional.of(avatar));
        assertEquals(avatar, avatarService.readFromDB(1L));
    }

    @Test
    void readFromDB_shouldThrowAvatarNotFoundException() {
        when(avatarRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(AvatarNotFoundException.class, () -> avatarService.readFromDB(1L));
    }

    @Test
    void readFromFile_shouldReadAvatar() throws IOException {
        Avatar avatar = new Avatar();
        avatar.setData(new byte[]{});
        avatar.setFilePath("/1L.pdf");
        avatar.setFileSize(11);
        avatar.setStudent(student);
        avatar.setMediaType(".pdf");
        when(avatarRepository.findById(1L)).thenReturn(Optional.of(avatar));
        assertEquals(new File(avatar.getFilePath()), avatarService.readFromFile(1L));
    }
}