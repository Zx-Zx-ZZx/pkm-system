package com.Zzx.service;

import com.Zzx.model.Note;
import com.Zzx.model.Tag;
import com.Zzx.storage.StorageService;

import java.util.*;
import java.util.stream.Collectors;

public class TagService {
    private final StorageService storageService;
    private final Map<String, Tag> allTags; // 全局标签管理

    public TagService(StorageService storageService) {
        this.storageService = storageService;
        this.allTags = new HashMap<>();
        loadAllTags();
    }

    private void loadAllTags() {
        // 从所有笔记中加载标签
        storageService.findAllNotes().stream()
                .flatMap(note -> note.getTags().stream())
                .forEach(tag -> allTags.put(tag.getName(), tag));
    }

    public Tag createTag(String tagName) {
        Tag tag = new Tag(tagName);
        allTags.put(tag.getName(), tag);
        return tag;
    }

    public Tag getOrCreateTag(String tagName) {
        return allTags.computeIfAbsent(tagName.toLowerCase(), this::createTag);
    }

    public boolean addTagToNote(String noteId, String tagName) {
        if (tagName == null || tagName.trim().isEmpty()) {
            throw new IllegalArgumentException("标签不能为空");
        }

        Optional<Note> optionalNote = storageService.findNoteById(noteId);
        if (optionalNote.isEmpty()) {
            throw new IllegalArgumentException("笔记不存在: " + noteId);
        }

        Note note = optionalNote.get();
        Tag tag = getOrCreateTag(tagName.trim());
        note.addTag(tag);
        storageService.saveNote(note);
        return true;
    }

    public boolean removeTagFromNote(String noteId, String tagName) {
        Optional<Note> optionalNote = storageService.findNoteById(noteId);
        if (optionalNote.isEmpty()) {
            throw new IllegalArgumentException("笔记不存在: " + noteId);
        }

        Note note = optionalNote.get();
        Tag tag = getTagByName(tagName.trim()).orElse(null);
        if (tag != null) {
            note.removeTag(tag);
            storageService.saveNote(note);
            return true;
        }
        return false;
    }

    public Set<Tag> getAllTags() {
        return new HashSet<>(allTags.values());
    }

    public List<Note> getNotesByTag(String tagName) {
        Tag tag = allTags.get(tagName.toLowerCase());
        if (tag == null) {
            return new ArrayList<>();
        }

        return storageService.findAllNotes().stream()
                .filter(note -> note.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    public Optional<Tag> getTagByName(String tagName) {
        return Optional.ofNullable(allTags.get(tagName.toLowerCase()));
    }

    public boolean deleteTag(String tagName) {
        Tag tag = allTags.get(tagName.toLowerCase());
        if (tag == null) {
            return false;
        }

        // 从所有笔记中移除该标签
        storageService.findAllNotes().forEach(note -> {
            if (note.getTags().remove(tag)) {
                storageService.saveNote(note);
            }
        });

        allTags.remove(tagName.toLowerCase());
        return true;
    }
}