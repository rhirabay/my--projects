import {NextResponse} from "next/server";
import OpenAI from 'openai';

require('dotenv').config();
const openai = new OpenAI();

export async function POST(request: Request) {
    const requestBody = await request.json()
    const userMessage = requestBody.message;

    const chatCompletion = await openai.chat.completions.create({
        messages: [
            // { role: 'system', content: '回答には改行を入れて読みやすくしてください。' },
            { role: 'user', content: userMessage }
        ],
        model: 'gpt-3.5-turbo',
    });

    return NextResponse.json({
        message: chatCompletion.choices[0].message.content
    })
}