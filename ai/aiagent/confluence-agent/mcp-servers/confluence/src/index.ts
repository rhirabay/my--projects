import { McpServer } from "@modelcontextprotocol/sdk/server/mcp.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import { z } from "zod";
import {getContentByPageId} from "./getContent";
import {createAxiosInstance} from "./createAxiosInstance";
import dotenv from "dotenv";

console.log(__dirname);
dotenv.config({path: __dirname + '/../../../.env'});

const confluenceBaseUrl = process.env.CONFLUENCE_BASE_URL;
if (!confluenceBaseUrl) {
    throw new Error("CONFLUENCE_BASE_URL is required");
}
const confluenceAccessToken = process.env.CONFLUENCE_TOKEN;
if (!confluenceAccessToken) {
    throw new Error("CONFLUENCE_TOKEN is required");
}
const axiosInstance = createAxiosInstance({
    baseUrl: confluenceBaseUrl,
    accessToken: confluenceAccessToken,
});

// Create server instance
const server = new McpServer({
    name: "confluence",
    version: "1.0.0",
});

server.tool(
    "get-confluence-content",
    `・コンフルエンス（Wiki）の内容をマークダウン形式で取得する\n・利用可能なURLの形式：${confluenceBaseUrl}/pages/viewpage.action?pageId=<pageId>`,
    {
        pageId: z.string().describe(`・コンフルエンス（wiki）のpageId
・https://wiki.workers-hub.com/pages/viewpage.action?pageId=<pageId>のpageIdパラメータの値`),
    },
    async ({ pageId }) => {
        const content = await getContentByPageId({ axiosInstance, pageId });

        return {
            content: [
                {
                    type: "text",
                    text: `タイトル:
${content.title}

コンテンツ（マークダウン形式）:
${content.markdown}`
                }
            ],
        }
    },
);

async function main() {
    const transport = new StdioServerTransport();
    await server.connect(transport);
    console.error("Confluence MCP Server running on stdio");
}

main().catch((error) => {
    console.error("Fatal error in main():", error);
    process.exit(1);
});