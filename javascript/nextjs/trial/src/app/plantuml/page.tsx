"use server"

import React from "react";
import Push from 'push.js'
import fs from 'fs'
import path from 'path'
const { execSync } = require('child_process');

var plantuml = require('node-plantuml');

const uml = `
@startuml

' ネットワーク領域を定義
frame "一般居室" {
  actor "ツール利用者" as User
}

frame "Tool" {
  node "Webアプリケーション" as Web {
    component "oauth2-proxy" as corp
    component "アプリケーション" as webapp
  }
}

frame "セキュア" {
    node "APIアプリケーション" as Api {
      component "Athenz Provider Sidecar" as athenz
      component "アプリケーション" as apiapp
    }
    database DB
}

node "API Gateway" as apigw

' コンポーネント間の関係を定義
User -right-> corp : https:443\\nCorporateIdp認証
corp -down-> webapp : 8080
webapp -right-> apigw : https:443
apigw -right-> athenz : https:443
athenz -down-> apiapp : 8080
apiapp -right-> DB : 6501?

@enduml
`

export default async function Page() {
    // uuidを生成
    const uuid = Math.random().toString(36).slice(-8);
    // umlをファイルに保存
    fs.writeFileSync(`${uuid}.puml`, uml);
    // コマンドを実行（puml generate file.puml -o file.png）
    execSync(`puml generate ${uuid}.puml -o ${uuid}.png`, (error: any, stdout: any, stderr: any) => {
        if (error) {
            console.error(`exec error: ${error}`);
            return;
        }
    });

    const imagePath = path.resolve(`./${uuid}.png`);
    const imageBuffer = fs.readFileSync(imagePath);
    const imageBase64 = imageBuffer.toString('base64');

    // ファイルを削除
    fs.unlinkSync(`${uuid}.puml`);
    fs.unlinkSync(`${uuid}.png`);

    return (
        <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100 w-11/12">
            <h1 className="text-3xl font-bold underline">Plant UML</h1>
            {imageBase64 && <img src={`data:image/png;base64,${imageBase64}`} alt="Sample" />}
        </div>

    )
}

