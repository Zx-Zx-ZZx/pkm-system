package com.Zzx.storage;

import com.Zzx.storage.StorageService;
import com.Zzx.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonStorageService implements StorageService {
    private static final String DATA_FILE = "notes.json";
    private final ObjectMapper objectMapper;
    private final File dataFile;

    public JsonStorageService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.dataFile = new File(DATA_FILE);
    }

    @Override
    public void saveNote(Note note) {
        List<Note> notes = findAllNotes();

        // Remove existing note with same ID if present
        notes.removeIf(n -> n.getId().equals(note.getId()));
        notes.add(note);

        saveAllNotes(notes);
    }

    @Override
    public List<Note> findAllNotes() {
        if (!dataFile.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(dataFile,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Note.class));
        } catch (IOException e) {
            System.err.println("读取数据文件失败: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Note> findNoteById(String id) {
        return findAllNotes().stream()
                .filter(note -> note.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean deleteNoteById(String id) {
        List<Note> notes = findAllNotes();
        boolean removed = notes.removeIf(note -> note.getId().equals(id));

        if (removed) {
            saveAllNotes(notes);
        }

        return removed;
    }

    @Override
    public void saveAllNotes(List<Note> notes) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(dataFile, notes);
        } catch (IOException e) {
            System.err.println("保存数据文件失败: " + e.getMessage());
        }
    }
}
