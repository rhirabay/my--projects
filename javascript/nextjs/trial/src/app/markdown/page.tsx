"use server"

import React from "react";
import Markdown from 'react-markdown'
import remarkGfm from 'remark-gfm'

const markdown = `# H1 header

### H3 header

A paragraph with *emphasis* and **strong importance**.

> A block quote with ~strikethrough~ and a URL: https://reactjs.org.

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
`

export default async function Page() {
    return (
        <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100 w-11/12">
            <h1 className="text-3xl font-bold underline">Hello World</h1>

            <Markdown remarkPlugins={[remarkGfm]}　className="markdown">{markdown}</Markdown>
        </div>

    )
}

