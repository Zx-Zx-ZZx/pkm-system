package com.Zzx.controller;

import com.Zzx.model.Tag;
import com.Zzx.service.TagService;

public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    public void addTag(String noteId, String tagName) {
        try {
            tagService.addTagToNote(noteId, tagName);
            System.out.println("✔ 标签添加成功: " + tagName);
        } catch (IllegalArgumentException e) {
            System.err.println("✘ 错误: " + e.getMessage());
        } catch (Exception e) {
            // 添加更详细的异常处理
            System.err.println("✘ 标签添加失败: " + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()));
        }
    }

    public void removeTag(String noteId, String tagName) {
        try {
            boolean removed = tagService.removeTagFromNote(noteId, tagName);
            if (removed) {
                System.out.println("✔ 标签移除成功: " + tagName);
            } else {
                System.out.println("ℹ 笔记中未找到标签: " + tagName);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("✘ 错误: " + e.getMessage());
        }
    }

    public void deleteTag(String tagName) {
        try {
            boolean deleted = tagService.deleteTag(tagName);
            if (deleted) {
                System.out.println("✔ 标签删除成功: " + tagName);
            } else {
                System.out.println("ℹ 标签不存在: " + tagName);
            }
        } catch (Exception e) {
            System.err.println("✘ 错误: " + e.getMessage());
        }
    }

    public void listAllTags() {
        var tags = tagService.getAllTags();
        if (tags.isEmpty()) {
            System.out.println("暂无标签");
            return;
        }

        System.out.println("共有 " + tags.size() + " 个标签:");
        tags.forEach(tag -> System.out.println("- " + tag.getName()));
    }
}