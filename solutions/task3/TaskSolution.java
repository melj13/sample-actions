package com.codility.tasks.hibernate.solution;

import org.springframework.data.jpa.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.*;
import java.util.*;
import java.util.logging.Logger;

@Entity
@Table(name = "task")
class Task {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "priority")
    private Long priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }
}

@RestController
class TaskController {
    private static Logger log = Logger.getLogger("Solution");
    // log.info("You can use 'log' for debug messages");

    private final TaskRepository taskRepository;

    TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PutMapping(value = "/tasks/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    ResponseEntity<Object> update(@PathVariable("id") Long id,
                                  @RequestBody Map<String, Object> body) {

        // Error precedence: the task must exist before the body is validated.
        Optional<Task> found = taskRepository.findById(id);
        if (!found.isPresent()) {
            return error(HttpStatus.NOT_FOUND, "Cannot find task with given id");
        }

        Object description = body == null ? null : body.get("description");
        if (description == null || description.toString().trim().isEmpty()) {
            return error(HttpStatus.BAD_REQUEST, "Task description is required");
        }

        Object priority = body.get("priority");

        Task task = found.get();
        task.setDescription(description.toString());
        task.setPriority(priority instanceof Number ? ((Number) priority).longValue() : null);
        taskRepository.save(task);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("description", task.getDescription());
        response.put("priority", task.getPriority());
        return ResponseEntity.ok(response);
    }

    private static ResponseEntity<Object> error(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("status", status.value());
        return ResponseEntity.status(status).body(body);
    }
}

interface TaskRepository extends JpaRepository<Task, Long> {

}
