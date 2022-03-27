import {ADD_TASK, REMOVE_TASK, addTask} from './actions.js'

const initialState = {
    todos: {}
}

export function generateId(idLength) {
    let org = "0123456789abcdef"
    let id = ''
    for (let i = 0; i < idLength; i++) {
        let index = Math.floor(Math.random() * org.length)
        id += org[index]
    }

    return id
}

export function todoApp(state = initialState, action) {
    switch (action.type) {
        case ADD_TASK:
            let newState = Object.assign({}, state)
            newState.todos[generateId(16)] = action.text
            return newState
         default:
            return state;
    }
}

export default {todoApp}