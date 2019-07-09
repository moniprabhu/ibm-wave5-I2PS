package com.stackroute.recommendationservice.controller;

import com.stackroute.recommendationservice.model.Role;
import com.stackroute.recommendationservice.model.User;
import com.stackroute.recommendationservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/rest/neo4j/role")
public class RoleController {
    @Autowired
    public RoleService roleService;

    @GetMapping("/getAll")
    public Collection<Role> getAll() {

        return roleService.getAll();
    }

    @GetMapping("/{roleName}")
    public Role findByName(@PathVariable String roleName) {
        return roleService.findByName(roleName);
    }

    @PostMapping("/save")
    public Role saveRole(@RequestBody Role role) {

        return roleService.saveRole(role.getRoleId(),role.getRoleName());
    }

    @PostMapping("/map")
    public User createRelation(){
        User idea1=roleService.createRelations();
        return idea1;
    }

    @DeleteMapping("{roleName}")
    public String deleteRole(@PathVariable String roleName) {
        roleService.deleteRole(roleName);
        return "Deleted Role";
    }

    @PostMapping("/saved")
    public ResponseEntity<?> savedUser(@RequestBody Role role)
    {
        roleService.saved(role);
        return new ResponseEntity<Role>(roleService.saved(role), HttpStatus.CREATED);
    }



}