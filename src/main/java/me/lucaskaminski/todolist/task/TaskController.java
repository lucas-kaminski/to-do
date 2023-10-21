package me.lucaskaminski.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import me.lucaskaminski.todolist.utils.Utils;

@RestController
@RequestMapping("/task")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity<?> create(@RequestBody TaskModel task, HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");
    task.setIdUser(UUID.fromString(idUser.toString()));

    var currentDateTime = LocalDateTime.now();
    if (currentDateTime.isAfter(task.getStartAt())) {
      return ResponseEntity.badRequest().body("Start date must be after current date");
    }

    if (task.getEndAt().isBefore(task.getStartAt())) {
      return ResponseEntity.badRequest().body("End date must be after start date");
    }

    var createdTask = this.taskRepository.save(task);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
  }

  @GetMapping("/")
  public ResponseEntity<?> list(HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");
    var tasks = this.taskRepository.findByIdUser(UUID.fromString(idUser.toString()));
    return ResponseEntity.status(HttpStatus.OK).body(tasks);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@RequestBody TaskModel task, HttpServletRequest request, @PathVariable UUID id) {
    task.setId(id);
    task.setIdUser(UUID.fromString(request.getAttribute("idUser").toString()));

    var dbTask = this.taskRepository.findById(id).orElse(null);

    if (dbTask == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }

    if (!dbTask.getIdUser().equals(task.getIdUser())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don't have permission to update this task");
    }

    Utils.copyNonNullProperties(task, dbTask);

    var taskUpdated = this.taskRepository.save(dbTask);

    return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable UUID id) {
    var idUser = request.getAttribute("idUser");
    var task = this.taskRepository.findById(id).orElse(null);

    if (task == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }

    if (!task.getIdUser().equals(UUID.fromString(idUser.toString()))) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don't have permission to delete this task");
    }

    this.taskRepository.deleteById(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }
}
