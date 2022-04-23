import {LitElement, html} from 'lit';
import {customElement, property, query} from 'lit/decorators.js';

@customElement('todo-list')
export class TodoList extends LitElement {
    @property()
    todoList: Array<string> = [
        'task1',
        'task2',
        'task3',
    ]

    @property()
    newTask = ''

    @query('#newtask')
    input!: HTMLInputElement;

    render() {
        return html`
          <input type="text" @input="${this.handleChange}" id="newtask">
          <button @click="${this.addTask}">追加</button>
          <ul>
            ${this.todoList.map(it => html`<li>${it}</li>`)}
          </ul>
        `;
    }

    handleChange(event: Event) {
        const input = event.target as HTMLInputElement
        this.newTask = input.value
    }

    addTask() {
        this.todoList = [
            ...this.todoList,
            this.newTask
        ]
        this.newTask = ''
        this.input.value = ''
    }
}
