package com.fundoo.note.service;

import com.fundoo.note.dto.NoteColorDto;
import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.dto.ResponseDto;
import com.fundoo.note.exception.AuthenticationException;
import com.fundoo.note.exception.NoteException;

public interface INoteService {
    ResponseDto createNote(NoteDto noteDto, String email) throws NoteException, AuthenticationException;

    Boolean editNote(NoteDto noteDto, String authorizeToken) throws AuthenticationException, NoteException;

    ResponseDto deleteNote(int note_id, String authorizeToken) throws AuthenticationException, NoteException;

    boolean archive(int note_id, String authorizeToken) throws AuthenticationException, NoteException;

    boolean setNoteColor(NoteColorDto note_id, String authorizeToken) throws AuthenticationException, NoteException;
}
