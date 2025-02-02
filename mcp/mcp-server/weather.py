from typing import Any
import httpx
from mcp.server.fastmcp import FastMCP

# Initialize FastMCP server
mcp = FastMCP("weather")


@mcp.tool()
async def get_weather(location: str) -> str:
    """
    Get weather information for a given latitude and longitude

    Args:
      location: description: The location to get weather information for
    """
    return f"{location}の天気は「晴れ」です。"


if __name__ == "__main__":
    # Initialize and run the server
    mcp.run(transport='stdio')
