
import { convertHtmlToMarkdown } from './converter/htmlToMarkdown';
import axios, {AxiosInstance} from "axios";

interface ParamsByPageId {
  pageId: string;
  axiosInstance: AxiosInstance
}

interface Content {
  title: string;
  markdown: string;
  html: string;
  spaceKey: string;
  currentVersion: number;
}

export async function getContentByPageId(params: ParamsByPageId): Promise<Content> {

  const response = await params.axiosInstance.get(`/rest/api/content/${params.pageId}?expand=body.view,space,version`);
  const htmlContent = response.data.body.view.value;

  const markdownContent = convertHtmlToMarkdown(htmlContent);

  return {
    title: response.data.title,
    markdown: markdownContent,
    html: htmlContent,
    spaceKey: response.data.space.key,
    currentVersion: response.data.version.number
  };
}
