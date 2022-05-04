package com.example.AccountAPI.model;

import java.nio.ByteBuffer;
import java.util.UUID;

public class FileModel {
    private UUID id;
    private UUID messageId;
    private String fileName;
    private int fileSize;
    private ByteBuffer buffer;
    private int lastIndex=0;


    public static FileModel build(){
        return new FileModel();
    }

    public FileModel setFileSize(int fileSize) {
        this.fileSize = fileSize;
        return this.setBuffer(this.fileSize);
    }

    public int getFileSize(){
        return this.fileSize;
    }

    public UUID getId() {
        return id;
    }

    public FileModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public FileModel setMessageId(UUID messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public FileModel setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public FileModel setBuffer(int initSize) {
        this.buffer = ByteBuffer.allocate(initSize);
        return this;
    }

    public void appendDataToBuffer(byte[] slice){
        if(this.buffer!=null){
            this.buffer.put(slice,lastIndex,slice.length);
            lastIndex+=slice.length+1;
        }
    }
}
