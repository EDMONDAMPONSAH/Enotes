package com.edmond.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edmond.entity.Notes;
import com.edmond.entity.User;
import com.edmond.repository.UserRepository;
import com.edmond.service.NotesService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository uRepo;
	@Autowired
	private NotesService nService;
	
	
	@ModelAttribute
	public User getUser(Principal p, Model m) {
		String email = p.getName();
		User user = uRepo.findByEmail(email);
		m.addAttribute("user",user);
		return user;
	}

	@GetMapping("/addNotes")
	public String addNotes() {
		return "add_notes";
	}

	@GetMapping("/viewNotes")
	public String viewNotes(Principal p,Model m, @RequestParam(defaultValue="0") int pageNo) {
		User user=getUser(p,m);
		Page<Notes> allNotes= nService.getNotesByUser(user,pageNo);
		m.addAttribute("currentpage",pageNo);
		m.addAttribute("totalElements",allNotes.getTotalElements());
		m.addAttribute("totalPages",allNotes.getTotalPages());
		m.addAttribute("notesList",allNotes.getContent());
		return "view_notes";
	}

	@GetMapping("/editNotes/{id}")
	public String editNotes(@PathVariable int id,Model m) {
		m.addAttribute("n",nService.getNotesById(id));
		return "edit_notes";
	}
	
	@PostMapping("/saveNotes")
	public String saveNotes(@ModelAttribute Notes notes, HttpSession session, Principal p, Model m) {
		notes.setDate(LocalDate.now());
		notes.setUser(getUser(p,m));
		Notes n = nService.saveNotes(notes);
		if(n!=null) {
			session.setAttribute("msg", "Notes saved successfully");
		}else {
			session.setAttribute("msg", "something wrong");
		}
		return "redirect:/user/addNotes";
		
	}
	@PostMapping("/updateNotes")
	public String updateNotes(@ModelAttribute Notes notes, HttpSession session, Principal p, Model m) {
		notes.setDate(LocalDate.now());
		notes.setUser(getUser(p,m));
		Notes n = nService.saveNotes(notes);
		if(n!=null) {
			session.setAttribute("msg", "Notes updated successfully");
		}else {
			session.setAttribute("msg", "something wrong");
		}
		return "redirect:/user/viewNotes";
		
	}
	
	@GetMapping("/deleteNotes/{id}")
	public String deleteNotes(@PathVariable int id,HttpSession session) {
		boolean b=nService.deleteNotes(id);
		if(b) {
			session.setAttribute("msg", "Notes deleted successfully");
		}else {
			session.setAttribute("msg","something wrong");
		}
		return "redirect:/user/viewNotes";
	}

}
