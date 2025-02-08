import { exec } from 'child_process';

interface ExecParams {
    command: string;
    workingDirectory: string; // オプションの作業ディレクトリ
}

async function execCommand(command: string, workingDirectory: string): Promise<string> {
    return new Promise((resolve, reject) => {
        exec(command, { cwd: workingDirectory }, (error, stdout, stderr) => {
            let result = ''

            if (error) {
                result += `・実行中にエラーが発生しました\n${error.message}`;
            }
            if (stderr) {
                result += `・標準エラー出力\n${stderr}`;
            }
            if (stdout) {
                result += `・標準出力\n${stdout}`;
            }
            resolve(result);
        });
    });
}

export { execCommand };