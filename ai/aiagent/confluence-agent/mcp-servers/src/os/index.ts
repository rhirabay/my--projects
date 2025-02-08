import { McpServer } from "@modelcontextprotocol/sdk/server/mcp.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import { z } from "zod";
import { execCommand } from "./tools/exec-command";

// Create server instance
const server = new McpServer({
    name: "os",
    version: "1.0.0",
});

// Register tools
server.tool(
    "exec-command",
    "指定されたコマンドを実行します。",
    {
        command: z.string().describe("実行するコマンド"),
        workingDirectory: z.string().describe("コマンド実行ディレクトリ"),
    },
    async ({ command }) => {
        return {
            content: [
                {
                    type: "text",
                    text: await execCommand(command)
                }
            ],
        };
    }
);

async function main() {
    const transport = new StdioServerTransport();
    await server.connect(transport);
    console.error("OS MCP Server running on stdio");
}

main().catch((error) => {
    console.error("Fatal error in main():", error);
    process.exit(1);
});
