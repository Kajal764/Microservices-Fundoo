package com.fundoo.note.controller;

import com.fundoo.note.dto.NoteColorDto;
import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.dto.ResponseDto;
import com.fundoo.note.exception.AuthenticationException;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/fundoo/note")
public class NoteController {

    @Autowired
    INoteService noteService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto createNote(@Valid @RequestBody NoteDto noteDto, @RequestHeader(value = "Authorization", required = false) String authorizeToken) throws NoteException, AuthenticationException {
        return noteService.createNote(noteDto, authorizeToken);
    }

    @PutMapping(value = "/update")
    public Object editNote(@RequestBody NoteDto noteDto, @RequestHeader(value = "Authorization", required = false) String authorizeToken) throws NoteException, AuthenticationException {
        if (noteService.editNote(noteDto, authorizeToken))
            return new ResponseDto("Note Updated", 200);
        return new ResponseDto("Error Updating Note", 400);
    }

    @PutMapping(value = "/delete/{note_Id}")
    public ResponseDto trashNote(@PathVariable("note_Id") int note_id,@RequestHeader(value = "Authorization", required = false) String authorizeToken) throws NoteException, AuthenticationException {
        ResponseDto trash = noteService.deleteNote(note_id, authorizeToken);
        return trash;
    }

    @PutMapping(value = "/archive/{note_Id}")
    public ResponseDto pinArchiveNote(@PathVariable("note_Id") int note_id, @RequestHeader(value = "Authorization", required = false) String authorizeToken) throws NoteException, AuthenticationException {
        if (noteService.archive(note_id, authorizeToken)) {
            return new ResponseDto("Note Archived", 200);
        }
        return new ResponseDto("Note Unarchived", 200);
    }

    @PutMapping(value = "/color")
    public ResponseDto setNoteColor(@Valid @RequestBody NoteColorDto noteColorDto, @RequestHeader(value = "Authorization", required = false) String authorizeToken) throws NoteException, AuthenticationException {
        if (noteService.setNoteColor(noteColorDto, authorizeToken)) {
            return new ResponseDto("Note color set", 200);
        }
        return new ResponseDto("Error, for setting note color", 200);
    }

}
