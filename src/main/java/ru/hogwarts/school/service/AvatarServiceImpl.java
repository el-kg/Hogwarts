package ru.hogwarts.school.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.model.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class AvatarServiceImpl implements AvatarService {
    private final Logger logger = (Logger) LoggerFactory.getLogger(AvatarServiceImpl.class);

    private final String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository,
                             @Value("${path.to.avatars.folder}") String avatarsDir) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
        this.avatarsDir = avatarsDir;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Отработал метод uploadAvatar");
        Student student = studentService.read(studentId);
        Path filePath = saveToFile(student, avatarFile);
        saveToDb(filePath, avatarFile, student);
    }

    private void saveToDb(Path filePath, MultipartFile avatarFile, Student student) throws IOException {
        logger.info("Отработал метод saveToDb");
        Avatar avatar = avatarRepository.findByStudent_id(student.getId()).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatar.getFileSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        avatarRepository.save(avatar);
    }

    private Path saveToFile(Student student, MultipartFile avatarFile) throws IOException {
        logger.info("Отработал метод saveToFile");
        Path filePath = Path.of(avatarsDir,
                student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return filePath;
    }

    @Override
    public Avatar readFromDB(long id) {
        logger.info("Отработал метод readFromDB");
        return avatarRepository.findById(id).orElseThrow(() -> new AvatarNotFoundException("Аватар не быд найден!"));
    }

    @Override
    public File readFromFile(long id) throws IOException {
        logger.info("Отработал метод readFromFile");
        Avatar avatar = readFromDB(id);
        return new File(avatar.getFilePath());
    }

    private String getExtensions(String fileName) {
        logger.info("Отработал метод getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    public Avatar findAvatar(Long studentId) {
        logger.info("Отработал метод findAvatar");
        return avatarRepository.findByStudent_id(studentId).orElse(new Avatar());
    }


    public Page<Avatar> getAllAvatars(Integer pageNo, Integer pageSize) {
        logger.info("Отработал метод getAllAvatars");
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return avatarRepository.findAll(paging);
    }
}
