import Head from 'next/head'
import ToDoList from "../components/ToDoList";

function Home({toDoList}) {
  return (
    <div>
      <Head>
        <title>ToDoリスト</title>
      </Head>

      <main>
        <h1>ToDoリスト</h1>
        <ToDoList toDoList={toDoList} />
      </main>
    </div>
  )
}

export async function getServerSideProps(ctx) {
  const res = await fetch('http://localhost:3000/api/toDoList')
  const json = await res.json()

  return {
    props: {
      toDoList: json
    }
  }
}

export default Home