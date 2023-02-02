const fs = require('fs')

// 「./reports」配下のjsonファイル名を取得
const filenames = fs.readdirSync('reports').filter(it => it.endsWith(".json"))

const reports = {}

filenames.forEach(filename => {
    // ファイルの内容を取得
    const content = fs.readFileSync(`reports/${filename}`).toString()
    // jsonで読込
    const json = JSON.parse(content)
    // 各種計測結果を取得（サンプルとしてperformanceのスコアを取得）
    const performance = json.categories.performance.score * 100

    // ファイル名からnpmスクリプトで指定した「name」部分を抽出
    const name = filename.match(/(.+).report.json$/)[1]

    // レポートに追加
    reports[name] = {
        performance
    }
})

// テーブル形式で出力
console.table(reports)

