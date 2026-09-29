import { useState, ChangeEvent } from "react";
import "./index.css";

interface Task {
  id: number;
  name: string;
  completed: boolean;
}

const ToDoListSol: React.FC = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [taskName, setTaskName] = useState<string>("");
  const [showCompleted, setShowCompleted] = useState<boolean>(false);

  const handleTaskNameChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTaskName(event.target.value);
  };

  const handleAddTask = () => {
    if (taskName.trim() === "") {
      alert("Please enter the task name");
      return;
    }

    setTasks((previousTasks) => [
      ...previousTasks,
      { id: Date.now() + previousTasks.length, name: taskName, completed: false },
    ]);
    setTaskName("");
  };

  const handleMarkAsComplete = (id: number) => {
    setTasks((previousTasks) =>
      previousTasks.map((task) =>
        task.id === id ? { ...task, completed: true } : task
      )
    );
  };

  const handleDeleteTask = (id: number) => {
    setTasks((previousTasks) => previousTasks.filter((task) => task.id !== id));
  };

  const handleShowCompletedChange = (event: ChangeEvent<HTMLInputElement>) => {
    setShowCompleted(event.target.checked);
  };

  const visibleTasks = showCompleted
    ? tasks
    : tasks.filter((task) => !task.completed);

  return (
    <div className="layout-column align-items-center justify-content-start">
      <section className="layout-row align-items-center justify-content-center mt-30">
        <input
          type="text"
          placeholder="Task Name"
          data-testid="input-task-name"
          value={taskName}
          onChange={handleTaskNameChange}
        />
        <button
          className="outlined"
          data-testid="add-task-button"
          onClick={handleAddTask}
        >
          Add Task
        </button>
      </section>
      <div className="card w-40 pt-30 pb-8 mt-2">
        <table>
          <thead>
            <tr>
              <th colSpan={3}>Name</th>
            </tr>
          </thead>
          <tbody data-testid="tasks-list">
            {visibleTasks.map((task) => (
              <tr key={task.id}>
                <td>{task.name}</td>
                <td>
                  <button
                    disabled={task.completed}
                    onClick={() => handleMarkAsComplete(task.id)}
                  >
                    {task.completed ? "Completed" : "Mark as Complete"}
                  </button>
                </td>
                <td>
                  <button
                    className="danger"
                    onClick={() => handleDeleteTask(task.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <section className="layout-row align-items-center justify-content-center mt-30">
        <input
          className="show-completed-tasks-checkbox"
          data-testid="show-completed-tasks-checkbox"
          type="checkbox"
          checked={showCompleted}
          onChange={handleShowCompletedChange}
        />
        <label className="show-completed-tasks-label">Show Completed Tasks</label>
      </section>
    </div>
  );
};

export default ToDoListSol;
