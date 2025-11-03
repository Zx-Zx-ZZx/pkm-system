package com.Zzx.service;

import com.Zzx.model.Note;
import com.Zzx.model.Tag;
import com.Zzx.storage.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NoteService {
    private final StorageService storageService;

    public NoteService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Note createNote(String title, String content) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("标题不能为空");
        }

        Note note = new Note(title.trim(), content != null ? content.trim() : "");
        storageService.saveNote(note);
        return note;
    }

    public List<Note> getAllNotes() {
        return storageService.findAllNotes();
    }

    public List<Note> getNotesByTag(String tagName) {
        String lowerTagName = tagName.toLowerCase();
        return storageService.findAllNotes().stream()
                .filter(note -> note.getTags().stream().anyMatch(tag -> tag.getName().equals(lowerTagName)))
                .toList();
    }

    public Optional<Note> getNoteById(String id) {
        return storageService.findNoteById(id);
    }

    public boolean deleteNote(String id) {
        return storageService.deleteNoteById(id);
    }

    public Note updateNoteContent(String id, String newContent) {
        Optional<Note> optionalNote = storageService.findNoteById(id);
        if (optionalNote.isEmpty()) {
            throw new IllegalArgumentException("笔记不存在: " + id);
        }

        Note note = optionalNote.get();
        note.setContent(newContent != null ? newContent.trim() : "");
        storageService.saveNote(note);
        return note;
    }

    public List<Note> searchNotes(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return storageService.findAllNotes().stream()
                .filter(note -> note.getTitle().toLowerCase().contains(lowerKeyword) ||
                        note.getContent().toLowerCase().contains(lowerKeyword))
                .toList();
    }



    public void exportNoteToFile(String noteId, String format, String filePath) {
        Note note = getNoteById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("笔记不存在: " + noteId));

        try {
            Path outputPath = Paths.get(filePath);
            String content;

            switch (format.toLowerCase()) {
                case "txt":
                    content = formatNoteAsText(note);
                    break;
                case "md":
                    content = formatNoteAsMarkdown(note);
                    break;
                case "json":
                    content = formatNoteAsJson(note);
                    break;
                default:
                    throw new IllegalArgumentException("不支持的格式: " + format);
            }

            Files.write(outputPath, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("导出文件失败: " + e.getMessage(), e);
        }
    }

    public void exportAllNotes(String format, String filePath) {
        List<Note> notes = getAllNotes();

        try {
            Path outputPath = Paths.get(filePath);
            String content;

            switch (format.toLowerCase()) {
                case "txt":
                    content = formatAllNotesAsText(notes);
                    break;
                case "json":
                    content = formatAllNotesAsJson(notes);
                    break;
                default:
                    throw new IllegalArgumentException("不支持的格式: " + format);
            }

            Files.write(outputPath, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("导出文件失败: " + e.getMessage(), e);
        }
    }

    private String formatNoteAsText(Note note) {
        StringBuilder sb = new StringBuilder();
        sb.append("标题: ").append(note.getTitle()).append("\n");
        sb.append("ID: ").append(note.getId()).append("\n");
        sb.append("标签: ").append(note.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "))).append("\n");
        sb.append("创建时间: ").append(note.getCreatedAt()).append("\n");
        sb.append("更新时间: ").append(note.getUpdatedAt()).append("\n");
        sb.append("\n内容:\n").append(note.getContent()).append("\n");
        return sb.toString();
    }

    private String formatNoteAsMarkdown(Note note) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ").append(note.getTitle()).append("\n\n");
        sb.append("**标签**: ").append(note.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "))).append("\n\n");
        sb.append("**创建时间**: ").append(note.getCreatedAt()).append("\n\n");
        sb.append("**更新时间**: ").append(note.getUpdatedAt()).append("\n\n");
        sb.append("---\n\n");
        sb.append(note.getContent()).append("\n");
        return sb.toString();
    }

    private String formatNoteAsJson(Note note) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(note);
    }

    private String formatAllNotesAsText(List<Note> notes) {
        StringBuilder sb = new StringBuilder();
        sb.append("个人知识管理系统 - 笔记导出\n");
        sb.append("导出时间: ").append(LocalDateTime.now()).append("\n");
        sb.append("笔记总数: ").append(notes.size()).append("\n\n");

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            sb.append("=== 笔记 ").append(i + 1).append(" ===\n");
            sb.append(formatNoteAsText(note));
            sb.append("\n");
        }

        return sb.toString();
    }

    private String formatAllNotesAsJson(List<Note> notes) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(notes);
    }



}