package com.Zzx.storage;


import com.Zzx.model.Note;
import java.util.List;
import java.util.Optional;

public interface StorageService {
    void saveNote(Note note);
    List<Note> findAllNotes();
    Optional<Note> findNoteById(String id);
    boolean deleteNoteById(String id);
    void saveAllNotes(List<Note> notes);
}

