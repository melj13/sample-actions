package com.codility.tasks.hibernate.solution;

import org.springframework.data.jpa.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.*;
import java.util.logging.Logger;

@Entity
@Table(name = "task")
class Task {

    @Id
    private Long id;

    @Column(nullable = false, length = 200)
    private String description;

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

record TaskPayload(String description, Long priority) {
}

record ErrorPayload(String message, int status) {
}

@RestController
class TaskController {
    private static Logger log = Logger.getLogger("Solution");

    private final TaskRepository taskRepository;

    TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PutMapping(value = "/tasks/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    ResponseEntity<Object> update(@PathVariable Long id, @RequestBody TaskPayload payload) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return error(HttpStatus.NOT_FOUND, "Cannot find task with given id");
        }
        if (payload == null || payload.description() == null || payload.description().isBlank()) {
            return error(HttpStatus.BAD_REQUEST, "Task description is required");
        }

        task.setDescription(payload.description());
        task.setPriority(payload.priority());
        taskRepository.save(task);

        return ResponseEntity.ok(new TaskPayload(task.getDescription(), task.getPriority()));
    }

    private static ResponseEntity<Object> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ErrorPayload(message, status.value()));
    }
}

interface TaskRepository extends JpaRepository<Task, Long> {

}
