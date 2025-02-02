import { McpServer } from "@modelcontextprotocol/sdk/server/mcp.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import { z } from "zod";

// Create server instance
const server = new McpServer({
    name: "weather",
    version: "1.0.0",
});

// Register weather tools
server.tool(
    "get-weather",
    "特定地域の天気予報を取得する",
    {
        location: z.string().describe("[必須]地域 (例：東京都)"),
    },
    async ({ location }) => {
        // 仮の実装
        return {
            content: [
                {
                    type: "text",
                    text: `${location}の天気は「晴れ」です。`,
                },
            ],
        }
    },
);

async function main() {
    const transport = new StdioServerTransport();
    await server.connect(transport);
    console.error("Weather MCP Server running on stdio");
}

main().catch((error) => {
    console.error("Fatal error in main():", error);
    process.exit(1);
});
