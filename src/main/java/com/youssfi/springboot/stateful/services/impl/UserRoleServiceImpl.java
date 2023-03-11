package com.youssfi.springboot.stateful.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youssfi.springboot.stateful.entities.AppRole;
import com.youssfi.springboot.stateful.entities.AppUser;
import com.youssfi.springboot.stateful.repositories.RoleRepository;
import com.youssfi.springboot.stateful.repositories.UserRepository;
import com.youssfi.springboot.stateful.services.UserRoleService;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public AppUser getUser(Long id) {		
		return userRepository.findById(id).get();
	}
	
	@Override
	public AppRole getRole(Long id) {
		return roleRepository.findById(id).get();
	}
	
	@Override
	public AppUser createUser(AppUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@Override
	public AppRole createRole(AppRole role) {
		return roleRepository.save(role);
	}
	
	@Override
	public AppUser affectRoleToUser(String rolename, String username) {
		AppUser appUser = userRepository.findByUsername(username);
		AppRole appRole = roleRepository.findByName(rolename);
		appUser.getRoles().add(appRole);
		return appUser;
	}
	
	@Override
	public List<AppUser> getUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public List<AppRole> getRoles() {
		return roleRepository.findAll();
	}

	@Override
	public AppUser getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
