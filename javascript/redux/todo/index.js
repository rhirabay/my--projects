import reducers from './reducers.js'
import actions from './actions.js'
import TodoList from './component/TodoList.js'

const store = Redux.createStore(reducers.todoApp)

//store.subscribe(() => console.log(store.getState()))
//
//store.dispatch(actions.addTask('A'))
//
//store.dispatch(actions.addTask('B'))
//
//store.dispatch(actions.addTask('C'))

todos = [{
    text: 'sample1'
},{
    text: 'sample2'
}]

ReactDOM.render(
    <TodoList todos={todos} />,
    document.getElementById('app')
)