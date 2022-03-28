package rhirabay.grpc.yidongnan.grpc.client

import rhirabay.grpc.sample.GreetGrpc
import rhirabay.grpc.sample.GreetRequest

class GreetClient (private val stub: GreetGrpc.GreetBlockingStub ) {
    fun greeting(name: String): String {
        val request = GreetRequest.newBuilder()
                .setName(name)
                .build()
        val response = stub.greeting(request)
        return response.message
    }
}