<script>
    let files = [];
    let keys = []
    let environmentMaps = []

    $: dummy = readFile(files)

    function readFile(files) {
      for (const file of files) {
        let reader = new FileReader();
        reader.readAsText(file, "UTF-8");
        reader.onload = (evt) => {
          let json = JSON.parse(evt.target.result);
          let environments = {}

          json.containerDefinitions
            .flatMap(def => def.environment)
            .forEach(environment => {
              keys = [
                ...keys,
                environment.name
              ]
              environments[environment.name] = environment.value
            })
          environmentMaps = [
            ...environmentMaps,
            {
              filename: file.name,
              environments
            }
          ]
        };
      }

      return "dummy"
    }
</script>

<main>
    <input type="file" multiple bind:files>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th class="_sticky bg-secondary text-white font-weight-bold">ファイル</th>
            {#each keys as key}
                <th>{key}</th>
            {/each}
        </tr>
        </thead>
        <tbody>
        {#each environmentMaps as environmentMap}
            <tr>
                <td class="_sticky bg-secondary text-white">{environmentMap.filename}</td>
                {#each keys as key}
                    <td>{environmentMap.environments[key]}</td>
                {/each}
            </tr>
        {/each}
        </tbody>
    </table>
</main>

<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>


<style>
    ._sticky {
        position: sticky;
        left: 0;
    }
</style>