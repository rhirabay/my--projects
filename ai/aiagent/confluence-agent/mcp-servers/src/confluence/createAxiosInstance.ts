import axios, { AxiosInstance } from 'axios';

interface Params {
    baseUrl: string
    accessToken: string
}

export function createAxiosInstance(params: Params): AxiosInstance {
    return axios.create({
        baseURL: params.baseUrl,
        headers: {
            Authorization: `Bearer ${params.accessToken}`
        }
    });
}