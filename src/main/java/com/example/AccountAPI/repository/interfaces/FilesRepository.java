package com.example.AccountAPI.repository.interfaces;

import com.example.AccountAPI.model.FileModel;

import java.util.List;
import java.util.UUID;

public interface FilesRepository {
    List<FileModel> getMessageFiles(UUID messageId);
    List<UUID> saveFiles(List<FileModel> files,UUID messageId);
}
