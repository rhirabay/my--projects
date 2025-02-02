"use client"

import React, {useState} from "react";
import Markdown from 'react-markdown'
import remarkGfm from 'remark-gfm'
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter'
import { a11yDark } from 'react-syntax-highlighter/dist/cjs/styles/prism'

const markdown = `### H3 header

\`\`\`java:Sample.java
public static void main(String[] args) {
    System.out.println("Hello, World!");
}
\`\`\`

\`\`\`java
public static void main(String[] args) {
    System.out.println("java");
}
\`\`\`

`

const CodeCopy = ({children}: any) => {
    const [buttonText, setButtonText] = useState("Copy");

    const handleClick = () => {
        console.log(children);
        navigator.clipboard.writeText(children);
        setButtonText('Copied!');
        setTimeout(() => {
            setButtonText("Copy");
        }, 500);
    }

    return (
        <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 text-xs rounded"
                onClick={handleClick}>{buttonText}</button>
    )
}

export default async function Page() {
    const Code = ({ node, inline, className = "blog-code", children, ...props } :any) => {
        if (!inline && className.startsWith("language-")) {
            let match = className.match(/language-(\w+)/);
            let filename = className.split(":")[1];
            return <>
                <div className="flex justify-between">
                    <div>{filename}</div>
                    <CodeCopy children={children}/>
                </div>
                <SyntaxHighlighter style={a11yDark} language={match[1]} PreTag="div" {...props}>
                    {children}
                </SyntaxHighlighter>
            </>
        }

        return <code className={className} {...props}>
            {children}
        </code>
    }

    return (
        <div className="w-10/12">
            <Markdown className="markdown"
                      remarkPlugins={[remarkGfm]}
                      components={{
                          code: Code,
                      }}
            >{markdown}</Markdown>
        </div>

    )
}

