package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import java.io.File;

import java.io.IOException;

@Service
public interface AvatarService {


    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar readFromDB(long id);

    File readFromFile(long id) throws IOException;

    Avatar findAvatar(Long studentId);

    Page<Avatar> getAllAvatars(Integer pageNo, Integer pageSize);

}
