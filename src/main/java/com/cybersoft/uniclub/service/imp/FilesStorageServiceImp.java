package com.cybersoft.uniclub.service.imp;

import com.cybersoft.uniclub.exception.FileException;
import com.cybersoft.uniclub.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.stream.Stream;
@Service
public class FilesStorageServiceImp implements FilesStorageService {
    @Value("${root.path}")
    private String rootPath;
    private  Path root;
    @Override
    public void init() {
        try {
            root = Paths.get(rootPath);
            if (!Files.exists(root))
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new FileException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        root = Paths.get(rootPath);
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new FileException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        root = Paths.get(rootPath);
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new FileException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        root = Paths.get(rootPath);
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            root = Paths.get(rootPath);
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new FileException("Could not load the files!");
        }
    }
}
