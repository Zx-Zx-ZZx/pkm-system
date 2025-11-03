package com.Zzx.controller;

// 恢复NoteController的原始设计
import com.Zzx.model.Note;
import com.Zzx.model.Tag;
import com.Zzx.service.NoteService;


import java.util.List;
import java.util.stream.Collectors;

public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    public void createNote(String title, String content) {
        try {
            Note newNote = noteService.createNote(title, content);
            System.out.println("✔ 笔记创建成功！");
            System.out.println(" ID: " + newNote.getId());
            System.out.println(" 标题: " + newNote.getTitle());
        } catch (IllegalArgumentException e) {
            System.err.println("✘ 错误: " + e.getMessage());
        }
    }

    public void listAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        if (notes.isEmpty()) {
            System.out.println("暂无笔记");
            return;
        }

        System.out.println("共有 " + notes.size() + " 条笔记:");
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            System.out.printf("%d. %s\n", i + 1, note);
        }
    }

    public void listNotesByTag(String tag) {
        List<Note> notes = noteService.getNotesByTag(tag);
        if (notes.isEmpty()) {
            System.out.println("没有找到标签为 '" + tag + "' 的笔记");
            return;
        }

        System.out.println("标签 '" + tag + "' 的笔记 (" + notes.size() + " 条):");
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            System.out.printf("%d. %s\n", i + 1, note);
        }
    }

    public void viewNote(String id) {
        try {
            Note note = noteService.getNoteById(id)
                    .orElseThrow(() -> new IllegalArgumentException("笔记不存在: " + id));

            System.out.println("标题: " + note.getTitle());
            System.out.println("标签: " + (note.getTags().isEmpty() ? "无" : note.getTags().stream()
                    .map(Tag::getName)
                    .collect(Collectors.joining(", "))));
            System.out.println("创建时间: " + note.getCreatedAt());
            System.out.println("更新时间: " + note.getUpdatedAt());
            System.out.println("\n内容:");
            System.out.println(note.getContent());
        } catch (IllegalArgumentException e) {
            System.err.println("✘ 错误: " + e.getMessage());
        }
    }

    public void deleteNote(String id) {
        try {
            boolean deleted = noteService.deleteNote(id);
            if (deleted) {
                System.out.println("✔ 笔记删除成功: " + id);
            } else {
                System.err.println("✘ 错误: 笔记不存在: " + id);
            }
        } catch (Exception e) {
            System.err.println("✘ 错误: " + e.getMessage());
        }
    }

    public void editNote(String id, String newContent) {
        try {
            Note updatedNote = noteService.updateNoteContent(id, newContent);
            System.out.println("✔ 笔记更新成功: " + updatedNote.getTitle());
        } catch (IllegalArgumentException e) {
            System.err.println("✘ 错误: " + e.getMessage());
        }
    }

    public void searchNotes(String keyword) {
        List<Note> notes = noteService.searchNotes(keyword);
        if (notes.isEmpty()) {
            System.out.println("没有找到包含 '" + keyword + "' 的笔记");
            return;
        }

        System.out.println("搜索 '" + keyword + "' 的结果 (" + notes.size() + " 条):");
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            System.out.printf("%d. %s\n", i + 1, note);
        }
    }

    // 删除这个有问题的deleteTag方法
    // public void deleteTag(String tagName) {
    //     TagController tagController = new TagController(new TagService(storageService));
    //     tagController.deleteTag(tagName);
    // }


    public void exportNote(String noteId, String format, String filePath) {
        try {
            noteService.exportNoteToFile(noteId, format, filePath);
            System.out.println("✔ 笔记导出成功: " + filePath);
        } catch (Exception e) {
            System.err.println("✘ 导出失败: " + e.getMessage());
        }
    }

    public void exportAllNotes(String format, String filePath) {
        try {
            noteService.exportAllNotes(format, filePath);
            System.out.println("✔ 所有笔记导出成功: " + filePath);
        } catch (Exception e) {
            System.err.println("✘ 导出失败: " + e.getMessage());
        }
    }



}