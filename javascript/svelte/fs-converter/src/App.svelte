<script>
	let path = '\\S0032f\\BtoBサイト統合プロジェクト\\020_設計_秘密\\01_集客・検索\\90_WORK\\90_個人\\小白井T\\08.R2テスト支援\\チケット13563のエビデンス';
	$: pathParts = getParts(path)
	$: windowsPath = createWindowsPath(pathParts)
	$: macPath = createMacPath(pathParts)

	// パスを分解して要素の配列を返却
	function getParts(path) {
		let parts = path.split(/[\/\\]/)
		if (parts.length > 1 && parts[0] === "") {
			parts = parts.slice(1)
		}
		if (parts.length > 1 && parts[0] === "smb:") {
			parts = parts.slice(2)
		}

		return parts
	}

	function createWindowsPath(pathParts) {
		return `\\${pathParts.join('\\')}`
	}

	function createMacPath(pathParts) {
		return `smb://${pathParts.join('/')}`
	}
</script>

<main>
	<div class="container-fluid">
		<div>
			<div>ファイルパス</div>
			<input id="path" class="form-control" type="text" bind:value={path} placeholder="ファイルサーバのパスを入力してください。" />
		</div>
		<div>
			<div>For Windows</div>
			<pre class="form-control">{windowsPath}</pre>
		</div>
		<div>
			<div>For Mac</div>
			<pre class="form-control">{macPath}</pre>
		</div>
		<div>
			<div>For All</div>
			<pre class="form-control">・Windows：{windowsPath}
・Mac：{macPath}</pre>
		</div>
	</div>
</main>

<svelte:head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css">
</svelte:head>
