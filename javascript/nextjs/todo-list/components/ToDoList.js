import ToDo from "./ToDo";
import React from "react";

export default class ToDoList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      toDoList: props.toDoList,
      newTask: ""
    }

    this.handleChange = this.handleChange.bind(this)
    this.addTask = this.addTask.bind(this)
  }

  handleChange = (e) => {
    this.setState(state => {
      return {
        ...state,
        newTask: e.target.value
      }
    });
  };

  addTask() {
    if (!this.state.newTask) {
      return;
    }

    this.setState(state => {
      return {
        newTask: '',
        toDoList: [
          ...state.toDoList,
          state.newTask
        ]
      }
    })
  }

  render() {
    return (
      <>
        <input type="text" value={this.state.newTask} onChange={this.handleChange} />
        <pre>{this.state.newTask}</pre>
        <button onClick={this.addTask}>追加</button>

        <ul>
          {this.state.toDoList.map(todo => <ToDo toDo={todo} key={todo} />)}
        </ul>
      </>
    )
  }
}