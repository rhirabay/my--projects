import { z } from "zod";

interface Params {
    filePath: string;
    content: string;
}

async function writeFileContent(filePath: string, content: string): Promise<string> {
    // /Users/rhirabay/dev配下のみ書き込みを許可する
    if (!filePath.startsWith('/Users/rhirabay/dev')) {
        return '指定されたファイルへの書き込みは許可されていません。';
    }

    const fs = require('fs');
    fs.writeFileSync(filePath, content, 'utf-8');

    return 'ファイルへの書き込みに成功しました';
}

export { writeFileContent };