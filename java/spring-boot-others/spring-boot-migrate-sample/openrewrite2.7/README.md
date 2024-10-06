# OpenRewriteでの移行手順

## 1. 定義ファイルを作成

プロジェクト直下に `rewrite.yml`を作成します。  
※ファイルの内容はこのプロジェクトに配置されているものをご確認ください。

## 2. プロジェクトにOpenRewriteを導入

build.gradleに変更を加えます。

```groovy
plugins {
    ...
    // OpenRewriteプラグインを追加する
    id("org.openrewrite.rewrite") version("5.32.0")
}

...

repositories {
    // mavenCentralが必要
    mavenCentral()
}

dependencies {
    ...
    // 書換に必要な依存を追加
    rewrite("org.openrewrite.recipe:rewrite-spring:4.30.0")
    rewrite("org.openrewrite:rewrite-gradle:7.33.0")
}

// 実行するレシピを定義
rewrite {
    // OpenRewite側に用意されているSpringBoot3への移行用のレシピ
    activeRecipe("org.openrewrite.java.spring.boot3.SpringBoot2To3Migration")
    // 先ほど定義した独自のレシピ
    activeRecipe('custom.SpringBootMigration')
}
```

## 3. テスト実行

rewriteDryRunタスクを実行します。

```bash
./gradlew rewriteDryRun
```

`build/reports/rewrite/rewrite.patch`に変更内容が出力されるので、
内容に問題がないか確認します。

## 4. 実行

rewriteRunタスクを実行します

```bash
./gradlew rewriteRun
```