package me.lucaskaminski.todolist.task;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public TaskModel create(@RequestBody TaskModel task, HttpServletRequest request) {
    var idUser = request.getAttribute("idUser");
    task.setIdUser(UUID.fromString(idUser.toString()));

    var createdTask = this.taskRepository.save(task);
    return createdTask;
  }

}
