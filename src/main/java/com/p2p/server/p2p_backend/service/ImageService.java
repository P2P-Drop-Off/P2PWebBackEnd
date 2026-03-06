package com.p2p.server.p2p_backend.service;

import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Bucket;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    public String uploadImage(MultipartFile file) throws IOException {

        String fileName = "items/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        Bucket bucket = StorageClient.getInstance().bucket();

        var blob = bucket.create(
                fileName,
                file.getInputStream(),
                file.getContentType()
        );
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        return "https://storage.googleapis.com/" + bucket.getName() + "/" + blob.getName();
    }
}