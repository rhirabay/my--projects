package rhirabay.grpc.yidongnan.grpc.service

import net.devh.boot.grpc.server.service.GrpcService
import rhirabay.grpc.sample.GreetGrpcKt
import rhirabay.grpc.sample.GreetRequest
import rhirabay.grpc.sample.greetResponse

@GrpcService
class GreetService : GreetGrpcKt.GreetCoroutineImplBase() {
    override suspend fun greeting(request: GreetRequest) = greetResponse {
        message = "Hello, ${request.name}."
    }
}