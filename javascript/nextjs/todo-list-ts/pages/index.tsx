import type { NextPage, GetServerSideProps, GetServerSidePropsContext } from 'next'
import Head from 'next/head'
import { ToDoListApplicationProps } from '../types/types'
import ToDoList from '../components/ToDoList'

const Home = ({toDoList}: ToDoListApplicationProps) => {
  return (
    <div>
      <Head>
        <title>ToDoリスト</title>
      </Head>

      <h1>To Do リスト</h1>
      <ToDoList toDoList={toDoList}/>
    </div>
  )
}

export async function getServerSideProps(ctx: GetServerSidePropsContext) {
  const res: Response = await fetch('http://localhost:3000/api/toDoList')
  const json = await res.json()

  let props: ToDoListApplicationProps = {
    toDoList: json
  }

  return {
    props
  }
}

export default Home
