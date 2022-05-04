package com.example.AccountAPI.repository.Mappers;

import com.example.AccountAPI.model.DMChatsModel;
import com.example.AccountAPI.model.FileModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FileMapper implements RowMapper<FileModel> {

    @Override
    public FileModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        FileModel fileModel= FileModel.build()
                .setId(rs.getObject("id", UUID.class))
                .setMessageId(rs.getObject("message_id",UUID.class))
                .setFileSize(rs.getInt("file_size"))
                .setFileName(rs.getString("file_name"));
        return fileModel;
    }
}
