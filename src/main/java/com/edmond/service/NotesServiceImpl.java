package com.edmond.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.edmond.entity.Notes;
import com.edmond.entity.User;
import com.edmond.repository.NotesRepository;

@Service
public class NotesServiceImpl implements NotesService{
	
	@Autowired
	private NotesRepository nRepo;

	@Override
	public Notes saveNotes(Notes notes) {
		// TODO Auto-generated method stub
		return nRepo.save(notes);
	}

	@Override
	public Notes getNotesById(int id) {
		// TODO Auto-generated method stub
		return nRepo.findById(id).get();
	}

	@Override
	public Page<Notes> getNotesByUser(User user,int pageNo) {
		Pageable pageable =PageRequest.of(pageNo, 5);
		return nRepo.findByUser(user,pageable);
	}

	@Override
	public Notes updateNotes(Notes notes) {
		// TODO Auto-generated method stub
		return nRepo.save(notes);
	}

	@Override
	public boolean deleteNotes(int id) {
		Notes notes = nRepo.findById(id).get();
		if(notes!=null) {
			nRepo.delete(notes);
			return true;
		}
		return false;
	}

}
