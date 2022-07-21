package com.springboot.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookstore.model.Admin;
import com.springboot.bookstore.model.Location;
import com.springboot.bookstore.repository.AdminRepository;
import com.springboot.bookstore.repository.LocationRepository;

@RestController
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private LocationRepository locationRepository;

	// Inserting Admin
	@PostMapping("/admin/{lId}")
	public void registerAdmin(@RequestBody Admin admin, @PathVariable("lId") Long lId) {
		
		Optional<Location> opt = locationRepository.findById(lId);
		if (!opt.isPresent()) {
			throw new RuntimeException("Location ID Invalid");
		}
		Location location =opt.get();
		
		admin.setLocation(location);

		adminRepository.save(admin);

	}

	// Showing all Admin info
	@GetMapping("/admin")
	public List<Admin> showAllAdmin() {
		List<Admin> list = adminRepository.findAll();

		return list;
	}

	// Delete admin by ID
	@DeleteMapping("/admin/{id}")
	public void deleteAdminById(@PathVariable("id") Long id) {
		Optional<Admin> opt = adminRepository.findById(id);

		if (!opt.isPresent()) {
			throw new RuntimeException("ID Invalid");
		}

		adminRepository.deleteById(id);
	}

	// Update Admin by adminID
	@PutMapping("/admin/{id}/{lId}")
	public Admin updateAdminById(@RequestBody Admin newAdmin, @PathVariable("id") Long id,@PathVariable("lId") Long lId) {

		Optional<Admin> opt = adminRepository.findById(id);
		if (!opt.isPresent()) {
			throw new RuntimeException("ID Invalid");
		}
		
		Optional<Location> opt1 = locationRepository.findById(lId);
		if (!opt1.isPresent()) {
			throw new RuntimeException("Location ID Invalid");
		}
		Location location = opt1.get();
		
		Admin admin = opt.get();

		admin.setFirst_name(newAdmin.getFirst_name());
		admin.setLast_name(newAdmin.getLast_name());
		admin.setPassword(newAdmin.getPassword());
		admin.setUsername(newAdmin.getUsername());
		admin.setPhone(newAdmin.getPhone());
		admin.setLocation(location);

		return adminRepository.save(admin);
	}

}
