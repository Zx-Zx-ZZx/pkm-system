package com.Zzx.pkm.entity;


import java.time.LocalDateTime;

public class Note {
    // 1. 字段（属性） - 使用private封装
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 2. 构造方法
    public Note() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public Note(String title, String content) {
        this(); // 调用无参构造
        this.title = title;
        this.content = content;
    }

    // 3. Getter 和 Setter 方法 (体现封装)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updateTime = LocalDateTime.now(); // 更新修改时间
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.updateTime = LocalDateTime.now(); // 更新修改时间
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}

