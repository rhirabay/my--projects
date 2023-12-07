// hello worldと表示するAPI（next api）
import {NextResponse} from "next/server";

export async function POST(request: Request) {
    const requestBody = await request.json()
    console.log(requestBody)

    return NextResponse.json({
        message: requestBody.message
    })
}