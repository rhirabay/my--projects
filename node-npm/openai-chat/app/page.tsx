'use client'

import {useEffect, useState} from "react";
import axios from "axios";
import ReactMarkdown from 'react-markdown';

export default function Page() {
    const [messages, setMessage] = useState([]);
    const [ sendMessage, setSendMessage ] = useState('');

    const handleInputChange = (event) => {
        setSendMessage(event.target.value);
    };

    const handleClick = async () => {
        try {
            const response = await axios.post('http://localhost:3000/api/chat', {
                message: sendMessage
            });
            setMessage([
                ...messages,
                response.data.message
            ]);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    return (
        <div>
            <h1>サンプル</h1>

            <div className="flex border-t-2 border-gray-200 p-4">
                <textarea rows={3} value={sendMessage} onChange={handleInputChange}
                       className="flex-1 border rounded-lg p-2 mx-2" />
                <br/>
                <button onClick={handleClick}
                        className="bg-blue-500 text-white rounded-lg px-4 py-2" >Send message.</button>

            </div>

            <div className="flex-1 overflow-y-scroll p-4 space-y-4">
                {messages.map((message) => (
                    <div className='w-full mx-2 p-2 rounded-lg bg-gray-200'>
                        <ReactMarkdown>{message}</ReactMarkdown>
                    </div>
                ))}
            </div>
        </div>
    );
}