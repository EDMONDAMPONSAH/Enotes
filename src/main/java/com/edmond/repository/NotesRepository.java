package com.edmond.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edmond.entity.Notes;
import com.edmond.entity.User;

@Repository
public interface NotesRepository extends JpaRepository<Notes,Integer>{
	
	public Page<Notes> findByUser(User user,Pageable pageable);

}
