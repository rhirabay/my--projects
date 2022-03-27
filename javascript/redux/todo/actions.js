export const ADD_TASK    = "add task";
export const REMOVE_TASK = "remove task";

export function addTask(text) {
    return {
        type: ADD_TASK,
        text
    }
}

export function removeTask(id) {
    return {
        type: REMOVE_TASK,
        id
    }
}

export default {addTask, removeTask}