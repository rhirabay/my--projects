<script>
	let todoList = [];
	chrome.storage.local.get('sTodoList', ({ sTodoList }) => {
		if (sTodoList) {
			todoList = sTodoList
		}
	});
	let newTodo = ""

	function addTodo() {
		todoList = [
				...todoList,
				newTodo
		]
		newTodo = ""
		syncStorage()
	}

	function deleteTodo(todo) {
		todoList = todoList.filter(it => it != todo)
		syncStorage()
	}

	function syncStorage() {
		chrome.storage.local.set({ sTodoList: todoList });
	}
</script>

<main>
	<h1>TODOリスト</h1>

	<table class="table table-sm">
		<tbody>
		<tr>
			<td>
				<button class="btn btn-sm btn-success" on:click={addTodo}>追加</button>
			</td>
			<td>
				<input type="text" class="form-control" bind:value={newTodo}>
			</td>
		</tr>
		{#each todoList as todo}
			<tr>
				<td><button class="btn btn-sm btn-danger" on:click={() => deleteTodo(todo)}>削除</button></td>
				<td>{todo}</td>
			</tr>
		{/each}
		</tbody>
	</table>
</main>

<head>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
</head>

<style>
	main {
		height: 1000px;
		width: 1000px;
	}
</style>
