package com.devmountain.noteApp.services;


import com.devmountain.noteApp.dtos.NoteDto;
import com.devmountain.noteApp.entities.Note;
import com.devmountain.noteApp.entities.User;
import com.devmountain.noteApp.repositories.NoteRepository;
import com.devmountain.noteApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//This is where actual Bussiness logic is for Note scope, the fetch and push to DB happens

// These services are used to execute CRUD SQL call using transactional and methods have been Implemented here
@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Override
    // This is where note for user by user id will added to DB
    public void addNote(NoteDto noteDto,Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        Note note = new Note(noteDto);
        userOptional.ifPresent(note::setUser);
        noteRepository.saveAndFlush(note);
    }

    @Override
    // This is where note will be deleted by note id from DB
    public void deleteNoteById(Long noteId) {
      Optional<Note> noteOptional = noteRepository.findById(noteId);
      noteOptional.ifPresent(note -> noteRepository.delete(note));
    }

    @Override
    // This is where note will be updated by note id to DB
    public void updateNoteById(NoteDto noteDto) {
     Optional<Note> noteOptional = noteRepository.findById(noteDto.getId() );
     noteOptional.ifPresent(note -> {
         note.setBody(noteDto.getBody());
         noteRepository.saveAndFlush(note);
     });
    }

    @Override
    // This is where all note fetched from DB by user id
    public List<NoteDto> getAllNotesByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            List<Note> noteList = noteRepository.findAllByUserEquals(userOptional.get());
            return noteList.stream().map(note -> new NoteDto(note)).collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

    @Override
    // This is where note details is fetched from DB by note id
    public Optional<NoteDto> getNoteById(Long noteId) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);
        if(noteOptional.isPresent()){
            return Optional.of(new NoteDto(noteOptional.get()));
        }
        return Optional.empty();
    }

}


