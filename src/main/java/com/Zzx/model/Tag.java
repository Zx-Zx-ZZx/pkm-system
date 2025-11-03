package com.Zzx.model;

import java.util.UUID;

public class Tag {
    private String id;
    private String name;
    private String color; // 可选：标签颜色
    private String description; // 可选：标签描述

    public Tag(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name.toLowerCase(); // 标签名统一小写
        this.color = "#3498db"; // 默认颜色
    }

    public Tag(String name, String color) {
        this(name);
        this.color = color;
    }

    public Tag(String name, String color, String description) {
        this(name, color);
        this.description = description;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name.toLowerCase(); }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tag tag = (Tag) obj;
        return name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}