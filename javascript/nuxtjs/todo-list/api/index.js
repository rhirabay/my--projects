const express = require('express')
const app = express()
app.get('/', (req, res) => {
  res.json({ message: 'hello, api' })
})

app.get('/toDoList', (req, res) => {
  res.json(['task1', 'task2', 'task3'])
})

module.exports = {
  path: '/api',
  handler: app
}
