package server.database;

import commons.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("task")
public interface TaskRepository extends JpaRepository<Task, Integer> {}
