/**
 * @fileoverview gRPC-Web generated client stub for greet
 * @enhanceable
 * @public
 */

// GENERATED CODE -- DO NOT EDIT!


/* eslint-disable */
// @ts-nocheck



const grpc = {};
grpc.web = require('grpc-web');

const proto = {};
proto.greet = require('./greet_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.greet.GreetClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options.format = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname;

};


/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.greet.GreetPromiseClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options.format = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname;

};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.greet.GreetRequest,
 *   !proto.greet.GreetResponse>}
 */
const methodDescriptor_Greet_greeting = new grpc.web.MethodDescriptor(
  '/greet.Greet/greeting',
  grpc.web.MethodType.UNARY,
  proto.greet.GreetRequest,
  proto.greet.GreetResponse,
  /**
   * @param {!proto.greet.GreetRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.greet.GreetResponse.deserializeBinary
);


/**
 * @param {!proto.greet.GreetRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.RpcError, ?proto.greet.GreetResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.greet.GreetResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.greet.GreetClient.prototype.greeting =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/greet.Greet/greeting',
      request,
      metadata || {},
      methodDescriptor_Greet_greeting,
      callback);
};


/**
 * @param {!proto.greet.GreetRequest} request The
 *     request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.greet.GreetResponse>}
 *     Promise that resolves to the response
 */
proto.greet.GreetPromiseClient.prototype.greeting =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/greet.Greet/greeting',
      request,
      metadata || {},
      methodDescriptor_Greet_greeting);
};


module.exports = proto.greet;

