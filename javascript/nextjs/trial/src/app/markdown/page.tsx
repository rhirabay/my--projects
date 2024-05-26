"use server"

import React from "react";
import Markdown from 'react-markdown'
import remarkGfm from 'remark-gfm'

const markdown = `# H1 header

### H3 header

A paragraph with *emphasis* and **strong importance**.

> A block quote with ~strikethrough~ and a URL: https://reactjs.org.

[This is link.](https://github.com/rhirabay/my-projects)

* Lists
* [ ] todo
* [x] done

A table:

| A | B |
| - | - |
| a1 | b1 |
| a2 | b2 |
| a3 | b3 |

\`\`\`java:Sample.java
System.out.println("Hello, World!");
\`\`\`

\`\`\`mermaid
sequenceDiagram
    participant User as ユーザー
    participant System as システム

    User->>System: ログインリクエスト
    System->>User: 認証
    User->>System: ログイン成功
    System->>User: ダッシュボード表示
\`\`\`
`

const components = {
    a: ({node, ...props}) => <a {...props} target="_blank" rel="noopener noreferrer" />
}

export default async function Page() {
    return (
        <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100 w-11/12">
            <h1 className="text-3xl font-bold underline">Hello World</h1>

            <Markdown remarkPlugins={[remarkGfm]} components={components}
                      className="markdown">{markdown}</Markdown>
        </div>

    )
}

