import TurndownService from 'turndown';

const turndownService = new TurndownService();

// 見出しタグをMarkdownの見出し形式に変換するカスタムルールを追加
turndownService.addRule('heading', {
    filter: ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'],
    replacement: function(content: string, node: TurndownService.Node) {
        const level = node.nodeName.charAt(1);
        return `\n\n${'#'.repeat(parseInt(level))} ${content}\n\n`;
    }
});

// テーブル変換のカスタムルールを追加
turndownService.addRule('table', {
    filter: 'table',
    replacement: function(content: string, node: TurndownService.Node) {
        let table = '';
        const rows = Array.from(node.querySelectorAll('tr')); // NodeListを配列に変換
        rows.forEach((row, rowIndex) => {
            const cells = Array.from(row.querySelectorAll('th, td')); // NodeListを配列に変換
            table += '|';
            cells.forEach((cell) => {
                const cellContent = turndownService.turndown(cell.innerHTML)
                table += ` ${cellContent} |`;
            });
            table += '\n';
            if (rowIndex === 0) {
                table += '|';
                cells.forEach(() => {
                    table += ' --- |';
                });
                table += '\n';
            }
        });
        return table;
    }
});

// リンクのhref属性がパスのみの場合、フルURLに変換するカスタムルールを追加
turndownService.addRule('relativeLink', {
    filter: 'a',
    replacement: function(content: string, node: TurndownService.Node) {
        if ('getAttribute' in node) {
            const href = node.getAttribute('href') || '';
            const fullUrl = href.startsWith('/') ? `https://wiki.workers-hub.com${href}` : href;
            return `[${content}](${fullUrl})`;
        }
        return content;
    }
});

export function convertHtmlToMarkdown(html: string): string {
    return turndownService.turndown(html);
}