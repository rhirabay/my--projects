import Todo from './Todo.js'

const TodoList = ({todos}) => {
    let item = todos.map(todo => <Todo {...todo}/>)
}

export default TodoList