package com.youssfi.springboot.stateful.controllers;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youssfi.springboot.stateful.entities.AppRole;
import com.youssfi.springboot.stateful.entities.AppUser;
import com.youssfi.springboot.stateful.requests.UserRoleRequest;
import com.youssfi.springboot.stateful.services.UserRoleService;

@RestController
public class UserRoleController {
	@Autowired
	private UserRoleService userRoleService;
	
	
	@GetMapping("/user/{id}")
	public AppUser getUser(@PathVariable Long id) {
		return userRoleService.getUser(id);
	}
	
	@GetMapping("/users")
	public List<AppUser> getUsers() {
		return userRoleService.getUsers();
	}
	
	@GetMapping("/roles")
	public List<AppRole> getRoles() {
		return userRoleService.getRoles();
	}
	
	@PostMapping("/createUser")
	public AppUser createUser(@RequestBody AppUser appUser) {
		return userRoleService.createUser(appUser);
	}
	
	@PostMapping("/createRole")
	public AppRole createRole(@RequestBody AppRole appRole) {
		return userRoleService.createRole(appRole);
	}
	
	@PostMapping("/affectRoleToUser")
	public AppUser affectRoleToUser(@RequestBody UserRoleRequest userRoleRequest) {
		return userRoleService.affectRoleToUser(userRoleRequest.getRolename(), userRoleRequest.getUsername());
	}
	
	@GetMapping("/profile")
	public Collection<? extends GrantedAuthority> profile(Principal principal) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    return authentication.getAuthorities();
		}
		return null;//principal;
	}
	
	@GetMapping("/user")
	public String user() {
		return "user called";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin called";
	}
}
