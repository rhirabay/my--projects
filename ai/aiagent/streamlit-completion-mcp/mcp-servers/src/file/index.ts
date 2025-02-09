import { McpServer } from "@modelcontextprotocol/sdk/server/mcp.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import dotenv from "dotenv";
import { z } from "zod";
import { readFileContent } from "./tools/read-file";
import { writeFileContent } from "./tools/write-file";

// Create server instance
const server = new McpServer({
    name: "file",
    version: "1.0.0",
});

// Register tools
server.tool(
    "read-file-content",
    "指定されたローカルファイルの内容を取得します。",
    {
        filePath: z.string().describe("ローカルファイルの絶対パス"),
    },
    async ({ filePath }) => {
        return {
            content: [
                {
                    type: "text",
                    text: await readFileContent(filePath)
                }
            ],
        }
    }
);
server.tool(
    "write-file-content",
    "指定されたローカルファイルにコンテンツを書き込みます。（完全上書き）",
    {
        filePath: z.string().describe("ローカルファイルの絶対パス"),
        content: z.string().describe("ファイルに書き込むコンテンツ"),
    },
    async ({ filePath, content }) => {
        return {
            content: [
                {
                    type: "text",
                    text: await writeFileContent(filePath, content)
                }
            ],
        }
    }
);

async function main() {
    const transport = new StdioServerTransport();
    await server.connect(transport);
    console.error("File MCP Server running on stdio");
}

main().catch((error) => {
    console.error("Fatal error in main():", error);
    process.exit(1);
});