package com.fundoo.note.service;

import com.fundoo.note.dto.NoteColorDto;
import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.dto.ResponseDto;
import com.fundoo.note.exception.AuthenticationException;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.note.repository.INoteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NoteService implements INoteService {

    @Autowired
    INoteRepository noteRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseDto createNote(NoteDto noteDto, String token) throws AuthenticationException {
        Note note = new Note();
        BeanUtils.copyProperties(noteDto, note);
        try {
            Integer userId = restTemplate.getForObject("http://user-service/fundoo/user/id/" + token, Integer.class);
            note.setUserId(userId);
        } catch (Exception exception) {
            throw new AuthenticationException("User not authenticate");
        }
        noteRepository.save(note);
        return new ResponseDto("Note created successfully", 200);
    }


    @Override
    public Boolean editNote(NoteDto noteDto, String token) throws AuthenticationException, NoteException {
        try {
            restTemplate.getForObject("http://user-service/fundoo/user/id/" + token, Integer.class);
        } catch (Exception exception) {
            throw new AuthenticationException("User not authenticate");
        }
        Optional<Note> note = noteRepository.findById(noteDto.note_id);
        return note.map((value) -> {
            value.setTitle(noteDto.title);
            value.setDescription(noteDto.description);
            value.setEditDate(LocalDateTime.now());
            noteRepository.save(value);
            return true;
        }).orElseThrow(() -> new NoteException("Note Is Not Present"));
    }

    @Override
    public ResponseDto deleteNote(int note_id, String token) throws AuthenticationException, NoteException {
        try {
            restTemplate.getForObject("http://user-service/fundoo/user/id/" + token, Integer.class);
        } catch (Exception exception) {
            throw new AuthenticationException("User not authenticate");
        }
        Optional<Note> note = noteRepository.findById(note_id);
        if (note.isPresent()) {
            note.get().setTrash(true);
            noteRepository.save(note.get());
            return new ResponseDto("Note trashed", 200);
        }
        throw new NoteException("Note is not present");

    }

    @Override
    public boolean archive(int note_id, String token) throws AuthenticationException, NoteException {
        try {
            restTemplate.getForObject("http://user-service/fundoo/user/id/" + token, Integer.class);
        } catch (Exception exception) {
            throw new AuthenticationException("User not authenticate");
        }

        Optional<Note> note = noteRepository.findById(note_id);
        if (note.isPresent()) {
            if (note.get().isArchive()) {
                note.get().setArchive(false);
                noteRepository.save(note.get());
                return false;
            } else {
                note.get().setArchive(true);
                noteRepository.save(note.get());
                return true;
            }
        }
        throw new NoteException("Note Is Not Present");
    }

    @Override
    public boolean setNoteColor(NoteColorDto noteColorDto, String token) throws AuthenticationException, NoteException {
        try {
            restTemplate.getForObject("http://user-service/fundoo/user/id/" + token, Integer.class);
        } catch (Exception exception) {
            throw new AuthenticationException("User not authenticate");
        }
        Optional<Note> note = noteRepository.findById(noteColorDto.note_id);
        if (note.isPresent()) {
            note.get().setColor(noteColorDto.color);
            noteRepository.save(note.get());
            return true;
        }
        throw new NoteException("Note Is Not Present");
    }

}
