import { exec } from 'child_process';

interface ExecParams {
    command: string;
    workingDirectory?: string; // オプションの作業ディレクトリ
}

async function execCommand(command: string, workingDirectory: string): Promise<string> {
    return new Promise((resolve, reject) => {
        exec(command, { cwd: workingDirectory }, (error, stdout, stderr) => {
            if (error) {
                reject(`実行中にエラーが発生しました: ${error.message}`);
                return;
            }
            if (stderr) {
                reject(`エラー出力: ${stderr}`);
                return;
            }
            resolve(stdout);
        });
    });
}

export { execCommand };