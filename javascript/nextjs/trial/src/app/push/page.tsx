"use client"

import React, { useEffect } from "react";
import Push from 'push.js'

export default function Page() {
    const [status, setStatus] = React.useState<string | null>(null)

    useEffect(() => {
        setTimeout(() => {
            Push.create("This is title.", {
                body: "this is body.",
                timeout: 3_000,
                //vibrate: [100, 100, 100],
                onClick: function(event: any){
                    window.focus();
                    event.close();
                },
            })}, 3_000)
    }, []);


    return (
        <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100 w-11/12">
            <h1 className="text-3xl font-bold underline">Hello World</h1>

            <div>status: {status}</div>
        </div>

    )
}

