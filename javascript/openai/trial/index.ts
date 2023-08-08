const fs = require("fs");
import { Configuration, OpenAIApi } from "openai";
const configuration = new Configuration({
    organization: "org-8DcbwUo1jXn15XA7NBrHiUfG",
    apiKey: process.env.OPENAI_API_KEY,
});
const openai = new OpenAIApi(configuration);

const fine_tuned_model = 'curie:ft-personal-2023-08-07-18-30-54'

async function chat() {
    const completion = await openai.createChatCompletion({
        model: "gpt-3.5-turbo",
        messages: [
            {role: "system", content: "you are great IT engineer assistant."},
            {role: "user", content: "hello."}
        ],
    });
    console.log(completion.data.choices[0].message);
}

const training_file = "api-spec.json"

async function createFile() {
    const createFileResponse = await openai.createFile(
        fs.createReadStream(training_file),
        'fine-tune'
    );
    console.log(createFileResponse.data)
}

async function listFiles() {
    const response = await openai.listFiles();
    console.log(response.data)
}

async function fineTuning() {

    const createFineTuneResponse = await openai.createFineTune({
        training_file: 'file-yN5p6yPsYcqChPKzq77m5CTA',
    });
    console.log({
        status: createFineTuneResponse.data.status,
        fine_tuned_model: createFineTuneResponse.data.fine_tuned_model
    });
}

async function listFineTunes() {
    const response = await openai.listFineTunes();
    console.log(response.data.data.map(it => {
        return {status: it.status, fine_tuned_model: it.fine_tuned_model}
    }))
}

async function createCompletion() {
    const response = await openai.createCompletion({
        model: fine_tuned_model,
        prompt: "ユーザ登録APIについて教えて",
        max_tokens: 1000,
        temperature: 0,
    });
    console.log(response.data)
}

createCompletion()