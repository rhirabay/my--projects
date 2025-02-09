import { convertHtmlToMarkdown } from './htmlToMarkdown';

process.env.CONFLUENCE_BASE_URL = 'https://sample.com';

test('html to markdown', () => {
    const html = `
  <h1>Confluence Page Title</h1>
  <p>This is a <strong>sample</strong> Confluence HTML content.</p>
  <table>
    <thead>
      <tr>
        <th>Header 1</th>
        <th>Header 2</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>Data 1</td>
        <td>Data 2</td>
      </tr>
      <tr>
        <td>Data 3</td>
        <td><a href="https://google.com">Yahoo</a></td>
      </tr>
    </tbody>
  </table>
  <a href="/pages/viewpage.action?pageId=103552635">Link</a>
`;
    const expected = `# Confluence Page Title

This is a **sample** Confluence HTML content.

| Header 1 | Header 2 |
| --- | --- |
| Data 1 | Data 2 |
| Data 3 | [Yahoo](https://google.com) |
[Link](https://sample.com/pages/viewpage.action?pageId=103552635)`;
    expect(convertHtmlToMarkdown(html)).toBe(expected);
});
