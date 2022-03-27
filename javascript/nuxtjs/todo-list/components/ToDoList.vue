<template>
  <div>
    <input v-model="newTask" type="text">
    <pre>{{ newTask }}</pre>
    <button @click="addTask">
      追加
    </button>
    <ul>
      <ToDo v-for="toDo in toDoList" :key="toDo" :toDo="toDo" />
    </ul>
  </div>
</template>

<script>
export default {
  name: 'ToDoList',
  data: () => {
    return {
      toDoList: [],
      newTask: ''
    }
  },
  async fetch () {
    this.toDoList = await this.$http.$get('http://localhost:3000/api/toDoList')
  },
  methods: {
    addTask () {
      if (!this.newTask) {
        return
      }

      this.toDoList = [
        ...this.toDoList,
        this.newTask
      ]

      this.newTask = ''
    }
  }
}
</script>
