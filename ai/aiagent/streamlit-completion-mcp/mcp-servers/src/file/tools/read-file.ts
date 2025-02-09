import { z } from "zod";

interface Params {
    filePath: string;
}

async function readFileContent(filePath: string): Promise<string> {
    const fs = require('fs');
    const content = fs.readFileSync(filePath, 'utf-8');

    return content;
}
export { readFileContent };