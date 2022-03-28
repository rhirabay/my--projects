<template>
  <div>Hello, gRPC by Nuxt.js</div>
</template>

<script>
import Vue from 'vue'

import { GreetRequest } from '../protos/greet_pb.js'
import { GreetClient } from '../protos/greet_grpc_web_pb.js'

export default Vue.extend({
  name: 'IndexPage',
  fetch () {
    const client = new GreetClient('http://localhost:8080', null, null)
    const request = new GreetRequest()
    client.greeting(request, {}, (err, response) => {
      if (err) {
        console.log(err)
      } else {
        console.log(response.toObject())
      }
    })
  }
})
</script>
