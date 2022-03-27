interface ToDoProps {
  toDo: string
}

export default function ToDo({toDo}: ToDoProps) {
  return (
    <li>{toDo}</li>
  )
}

