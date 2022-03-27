<template>
  <li>
    {{ toDo }}
  </li>
</template>

<script>
export default {
  name: 'ToDoList',
  props: ['toDo']
}
</script>
