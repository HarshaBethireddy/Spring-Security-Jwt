package com.harsha.userlogin.web.controller;

import com.harsha.userlogin.domain.Project;
import com.harsha.userlogin.service.project.ProjectService;
import com.harsha.userlogin.web.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PROJECT')")
    @Operation(summary = "Create a new project")
    public ResponseEntity<MessageResponse> createProject(@RequestBody Project project) {
        projectService.createProject(project);
        return ResponseEntity.ok(MessageResponse.success("Project created successfully"));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_PROJECT')")
    @Operation(summary = "View all projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_PROJECT')")
    @Operation(summary = "Delete a project by ID")
    public ResponseEntity<MessageResponse> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(MessageResponse.success("Project deleted successfully"));
    }
}
