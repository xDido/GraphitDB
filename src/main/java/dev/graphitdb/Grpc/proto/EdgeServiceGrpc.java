package dev.graphitdb.Grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Define the EdgeService gRPC service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.66.0)",
    comments = "Source: Edge.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class EdgeServiceGrpc {

  private EdgeServiceGrpc() {}

  public static final String SERVICE_NAME = "dev.graphitdb.grpc.EdgeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<EdgeServiceProto.CreateEdgeRequest,
      EdgeServiceProto.CreateEdgeResponse> getCreateEdgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateEdge",
      requestType = EdgeServiceProto.CreateEdgeRequest.class,
      responseType = EdgeServiceProto.CreateEdgeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<EdgeServiceProto.CreateEdgeRequest,
      EdgeServiceProto.CreateEdgeResponse> getCreateEdgeMethod() {
    io.grpc.MethodDescriptor<EdgeServiceProto.CreateEdgeRequest, EdgeServiceProto.CreateEdgeResponse> getCreateEdgeMethod;
    if ((getCreateEdgeMethod = EdgeServiceGrpc.getCreateEdgeMethod) == null) {
      synchronized (EdgeServiceGrpc.class) {
        if ((getCreateEdgeMethod = EdgeServiceGrpc.getCreateEdgeMethod) == null) {
          EdgeServiceGrpc.getCreateEdgeMethod = getCreateEdgeMethod =
              io.grpc.MethodDescriptor.<EdgeServiceProto.CreateEdgeRequest, EdgeServiceProto.CreateEdgeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateEdge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.CreateEdgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.CreateEdgeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EdgeServiceMethodDescriptorSupplier("CreateEdge"))
              .build();
        }
      }
    }
    return getCreateEdgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgeRequest,
      EdgeServiceProto.GetEdgeResponse> getGetEdgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetEdge",
      requestType = EdgeServiceProto.GetEdgeRequest.class,
      responseType = EdgeServiceProto.GetEdgeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgeRequest,
      EdgeServiceProto.GetEdgeResponse> getGetEdgeMethod() {
    io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgeRequest, EdgeServiceProto.GetEdgeResponse> getGetEdgeMethod;
    if ((getGetEdgeMethod = EdgeServiceGrpc.getGetEdgeMethod) == null) {
      synchronized (EdgeServiceGrpc.class) {
        if ((getGetEdgeMethod = EdgeServiceGrpc.getGetEdgeMethod) == null) {
          EdgeServiceGrpc.getGetEdgeMethod = getGetEdgeMethod =
              io.grpc.MethodDescriptor.<EdgeServiceProto.GetEdgeRequest, EdgeServiceProto.GetEdgeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetEdge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.GetEdgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.GetEdgeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EdgeServiceMethodDescriptorSupplier("GetEdge"))
              .build();
        }
      }
    }
    return getGetEdgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<EdgeServiceProto.DeleteEdgeRequest,
      EdgeServiceProto.DeleteEdgeResponse> getDeleteEdgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteEdge",
      requestType = EdgeServiceProto.DeleteEdgeRequest.class,
      responseType = EdgeServiceProto.DeleteEdgeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<EdgeServiceProto.DeleteEdgeRequest,
      EdgeServiceProto.DeleteEdgeResponse> getDeleteEdgeMethod() {
    io.grpc.MethodDescriptor<EdgeServiceProto.DeleteEdgeRequest, EdgeServiceProto.DeleteEdgeResponse> getDeleteEdgeMethod;
    if ((getDeleteEdgeMethod = EdgeServiceGrpc.getDeleteEdgeMethod) == null) {
      synchronized (EdgeServiceGrpc.class) {
        if ((getDeleteEdgeMethod = EdgeServiceGrpc.getDeleteEdgeMethod) == null) {
          EdgeServiceGrpc.getDeleteEdgeMethod = getDeleteEdgeMethod =
              io.grpc.MethodDescriptor.<EdgeServiceProto.DeleteEdgeRequest, EdgeServiceProto.DeleteEdgeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteEdge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.DeleteEdgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.DeleteEdgeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EdgeServiceMethodDescriptorSupplier("DeleteEdge"))
              .build();
        }
      }
    }
    return getDeleteEdgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<EdgeServiceProto.UpdateEdgeRequest,
      EdgeServiceProto.CreateEdgeResponse> getUpdateEdgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateEdge",
      requestType = EdgeServiceProto.UpdateEdgeRequest.class,
      responseType = EdgeServiceProto.CreateEdgeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<EdgeServiceProto.UpdateEdgeRequest,
      EdgeServiceProto.CreateEdgeResponse> getUpdateEdgeMethod() {
    io.grpc.MethodDescriptor<EdgeServiceProto.UpdateEdgeRequest, EdgeServiceProto.CreateEdgeResponse> getUpdateEdgeMethod;
    if ((getUpdateEdgeMethod = EdgeServiceGrpc.getUpdateEdgeMethod) == null) {
      synchronized (EdgeServiceGrpc.class) {
        if ((getUpdateEdgeMethod = EdgeServiceGrpc.getUpdateEdgeMethod) == null) {
          EdgeServiceGrpc.getUpdateEdgeMethod = getUpdateEdgeMethod =
              io.grpc.MethodDescriptor.<EdgeServiceProto.UpdateEdgeRequest, EdgeServiceProto.CreateEdgeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateEdge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.UpdateEdgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.CreateEdgeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EdgeServiceMethodDescriptorSupplier("UpdateEdge"))
              .build();
        }
      }
    }
    return getUpdateEdgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgesRequest,
      EdgeServiceProto.GetEdgesResponse> getGetEdgesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetEdges",
      requestType = EdgeServiceProto.GetEdgesRequest.class,
      responseType = EdgeServiceProto.GetEdgesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgesRequest,
      EdgeServiceProto.GetEdgesResponse> getGetEdgesMethod() {
    io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgesRequest, EdgeServiceProto.GetEdgesResponse> getGetEdgesMethod;
    if ((getGetEdgesMethod = EdgeServiceGrpc.getGetEdgesMethod) == null) {
      synchronized (EdgeServiceGrpc.class) {
        if ((getGetEdgesMethod = EdgeServiceGrpc.getGetEdgesMethod) == null) {
          EdgeServiceGrpc.getGetEdgesMethod = getGetEdgesMethod =
              io.grpc.MethodDescriptor.<EdgeServiceProto.GetEdgesRequest, EdgeServiceProto.GetEdgesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetEdges"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.GetEdgesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.GetEdgesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EdgeServiceMethodDescriptorSupplier("GetEdges"))
              .build();
        }
      }
    }
    return getGetEdgesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgesRequest,
      EdgeServiceProto.GetEdgesResponse> getGetOutgoingEdgesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOutgoingEdges",
      requestType = EdgeServiceProto.GetEdgesRequest.class,
      responseType = EdgeServiceProto.GetEdgesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgesRequest,
      EdgeServiceProto.GetEdgesResponse> getGetOutgoingEdgesMethod() {
    io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgesRequest, EdgeServiceProto.GetEdgesResponse> getGetOutgoingEdgesMethod;
    if ((getGetOutgoingEdgesMethod = EdgeServiceGrpc.getGetOutgoingEdgesMethod) == null) {
      synchronized (EdgeServiceGrpc.class) {
        if ((getGetOutgoingEdgesMethod = EdgeServiceGrpc.getGetOutgoingEdgesMethod) == null) {
          EdgeServiceGrpc.getGetOutgoingEdgesMethod = getGetOutgoingEdgesMethod =
              io.grpc.MethodDescriptor.<EdgeServiceProto.GetEdgesRequest, EdgeServiceProto.GetEdgesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOutgoingEdges"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.GetEdgesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.GetEdgesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EdgeServiceMethodDescriptorSupplier("GetOutgoingEdges"))
              .build();
        }
      }
    }
    return getGetOutgoingEdgesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgesRequest,
      EdgeServiceProto.GetEdgesResponse> getGetIncomingEdgesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetIncomingEdges",
      requestType = EdgeServiceProto.GetEdgesRequest.class,
      responseType = EdgeServiceProto.GetEdgesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgesRequest,
      EdgeServiceProto.GetEdgesResponse> getGetIncomingEdgesMethod() {
    io.grpc.MethodDescriptor<EdgeServiceProto.GetEdgesRequest, EdgeServiceProto.GetEdgesResponse> getGetIncomingEdgesMethod;
    if ((getGetIncomingEdgesMethod = EdgeServiceGrpc.getGetIncomingEdgesMethod) == null) {
      synchronized (EdgeServiceGrpc.class) {
        if ((getGetIncomingEdgesMethod = EdgeServiceGrpc.getGetIncomingEdgesMethod) == null) {
          EdgeServiceGrpc.getGetIncomingEdgesMethod = getGetIncomingEdgesMethod =
              io.grpc.MethodDescriptor.<EdgeServiceProto.GetEdgesRequest, EdgeServiceProto.GetEdgesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetIncomingEdges"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.GetEdgesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EdgeServiceProto.GetEdgesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EdgeServiceMethodDescriptorSupplier("GetIncomingEdges"))
              .build();
        }
      }
    }
    return getGetIncomingEdgesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EdgeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EdgeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EdgeServiceStub>() {
        @Override
        public EdgeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EdgeServiceStub(channel, callOptions);
        }
      };
    return EdgeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EdgeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EdgeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EdgeServiceBlockingStub>() {
        @Override
        public EdgeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EdgeServiceBlockingStub(channel, callOptions);
        }
      };
    return EdgeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EdgeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EdgeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EdgeServiceFutureStub>() {
        @Override
        public EdgeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EdgeServiceFutureStub(channel, callOptions);
        }
      };
    return EdgeServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Define the EdgeService gRPC service
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void createEdge(EdgeServiceProto.CreateEdgeRequest request,
                            io.grpc.stub.StreamObserver<EdgeServiceProto.CreateEdgeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateEdgeMethod(), responseObserver);
    }

    /**
     */
    default void getEdge(EdgeServiceProto.GetEdgeRequest request,
                         io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEdgeMethod(), responseObserver);
    }

    /**
     */
    default void deleteEdge(EdgeServiceProto.DeleteEdgeRequest request,
                            io.grpc.stub.StreamObserver<EdgeServiceProto.DeleteEdgeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteEdgeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Ensure this is defined
     * </pre>
     */
    default void updateEdge(EdgeServiceProto.UpdateEdgeRequest request,
                            io.grpc.stub.StreamObserver<EdgeServiceProto.CreateEdgeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateEdgeMethod(), responseObserver);
    }

    /**
     * <pre>
     * For getting edges
     * </pre>
     */
    default void getEdges(EdgeServiceProto.GetEdgesRequest request,
                          io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEdgesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Add these two RPC methods
     * </pre>
     */
    default void getOutgoingEdges(EdgeServiceProto.GetEdgesRequest request,
                                  io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOutgoingEdgesMethod(), responseObserver);
    }

    /**
     */
    default void getIncomingEdges(EdgeServiceProto.GetEdgesRequest request,
                                  io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetIncomingEdgesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service EdgeService.
   * <pre>
   * Define the EdgeService gRPC service
   * </pre>
   */
  public static abstract class EdgeServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return EdgeServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service EdgeService.
   * <pre>
   * Define the EdgeService gRPC service
   * </pre>
   */
  public static final class EdgeServiceStub
      extends io.grpc.stub.AbstractAsyncStub<EdgeServiceStub> {
    private EdgeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected EdgeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EdgeServiceStub(channel, callOptions);
    }

    /**
     */
    public void createEdge(EdgeServiceProto.CreateEdgeRequest request,
                           io.grpc.stub.StreamObserver<EdgeServiceProto.CreateEdgeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateEdgeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getEdge(EdgeServiceProto.GetEdgeRequest request,
                        io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetEdgeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteEdge(EdgeServiceProto.DeleteEdgeRequest request,
                           io.grpc.stub.StreamObserver<EdgeServiceProto.DeleteEdgeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteEdgeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Ensure this is defined
     * </pre>
     */
    public void updateEdge(EdgeServiceProto.UpdateEdgeRequest request,
                           io.grpc.stub.StreamObserver<EdgeServiceProto.CreateEdgeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateEdgeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * For getting edges
     * </pre>
     */
    public void getEdges(EdgeServiceProto.GetEdgesRequest request,
                         io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetEdgesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Add these two RPC methods
     * </pre>
     */
    public void getOutgoingEdges(EdgeServiceProto.GetEdgesRequest request,
                                 io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOutgoingEdgesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getIncomingEdges(EdgeServiceProto.GetEdgesRequest request,
                                 io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetIncomingEdgesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service EdgeService.
   * <pre>
   * Define the EdgeService gRPC service
   * </pre>
   */
  public static final class EdgeServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EdgeServiceBlockingStub> {
    private EdgeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected EdgeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EdgeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public EdgeServiceProto.CreateEdgeResponse createEdge(EdgeServiceProto.CreateEdgeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateEdgeMethod(), getCallOptions(), request);
    }

    /**
     */
    public EdgeServiceProto.GetEdgeResponse getEdge(EdgeServiceProto.GetEdgeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetEdgeMethod(), getCallOptions(), request);
    }

    /**
     */
    public EdgeServiceProto.DeleteEdgeResponse deleteEdge(EdgeServiceProto.DeleteEdgeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteEdgeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Ensure this is defined
     * </pre>
     */
    public EdgeServiceProto.CreateEdgeResponse updateEdge(EdgeServiceProto.UpdateEdgeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateEdgeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * For getting edges
     * </pre>
     */
    public EdgeServiceProto.GetEdgesResponse getEdges(EdgeServiceProto.GetEdgesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetEdgesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Add these two RPC methods
     * </pre>
     */
    public EdgeServiceProto.GetEdgesResponse getOutgoingEdges(EdgeServiceProto.GetEdgesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOutgoingEdgesMethod(), getCallOptions(), request);
    }

    /**
     */
    public EdgeServiceProto.GetEdgesResponse getIncomingEdges(EdgeServiceProto.GetEdgesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetIncomingEdgesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service EdgeService.
   * <pre>
   * Define the EdgeService gRPC service
   * </pre>
   */
  public static final class EdgeServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<EdgeServiceFutureStub> {
    private EdgeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected EdgeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EdgeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<EdgeServiceProto.CreateEdgeResponse> createEdge(
        EdgeServiceProto.CreateEdgeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateEdgeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<EdgeServiceProto.GetEdgeResponse> getEdge(
        EdgeServiceProto.GetEdgeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetEdgeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<EdgeServiceProto.DeleteEdgeResponse> deleteEdge(
        EdgeServiceProto.DeleteEdgeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteEdgeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Ensure this is defined
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<EdgeServiceProto.CreateEdgeResponse> updateEdge(
        EdgeServiceProto.UpdateEdgeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateEdgeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * For getting edges
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<EdgeServiceProto.GetEdgesResponse> getEdges(
        EdgeServiceProto.GetEdgesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetEdgesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Add these two RPC methods
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<EdgeServiceProto.GetEdgesResponse> getOutgoingEdges(
        EdgeServiceProto.GetEdgesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOutgoingEdgesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<EdgeServiceProto.GetEdgesResponse> getIncomingEdges(
        EdgeServiceProto.GetEdgesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetIncomingEdgesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_EDGE = 0;
  private static final int METHODID_GET_EDGE = 1;
  private static final int METHODID_DELETE_EDGE = 2;
  private static final int METHODID_UPDATE_EDGE = 3;
  private static final int METHODID_GET_EDGES = 4;
  private static final int METHODID_GET_OUTGOING_EDGES = 5;
  private static final int METHODID_GET_INCOMING_EDGES = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_EDGE:
          serviceImpl.createEdge((EdgeServiceProto.CreateEdgeRequest) request,
              (io.grpc.stub.StreamObserver<EdgeServiceProto.CreateEdgeResponse>) responseObserver);
          break;
        case METHODID_GET_EDGE:
          serviceImpl.getEdge((EdgeServiceProto.GetEdgeRequest) request,
              (io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgeResponse>) responseObserver);
          break;
        case METHODID_DELETE_EDGE:
          serviceImpl.deleteEdge((EdgeServiceProto.DeleteEdgeRequest) request,
              (io.grpc.stub.StreamObserver<EdgeServiceProto.DeleteEdgeResponse>) responseObserver);
          break;
        case METHODID_UPDATE_EDGE:
          serviceImpl.updateEdge((EdgeServiceProto.UpdateEdgeRequest) request,
              (io.grpc.stub.StreamObserver<EdgeServiceProto.CreateEdgeResponse>) responseObserver);
          break;
        case METHODID_GET_EDGES:
          serviceImpl.getEdges((EdgeServiceProto.GetEdgesRequest) request,
              (io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgesResponse>) responseObserver);
          break;
        case METHODID_GET_OUTGOING_EDGES:
          serviceImpl.getOutgoingEdges((EdgeServiceProto.GetEdgesRequest) request,
              (io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgesResponse>) responseObserver);
          break;
        case METHODID_GET_INCOMING_EDGES:
          serviceImpl.getIncomingEdges((EdgeServiceProto.GetEdgesRequest) request,
              (io.grpc.stub.StreamObserver<EdgeServiceProto.GetEdgesResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateEdgeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              EdgeServiceProto.CreateEdgeRequest,
              EdgeServiceProto.CreateEdgeResponse>(
                service, METHODID_CREATE_EDGE)))
        .addMethod(
          getGetEdgeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              EdgeServiceProto.GetEdgeRequest,
              EdgeServiceProto.GetEdgeResponse>(
                service, METHODID_GET_EDGE)))
        .addMethod(
          getDeleteEdgeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              EdgeServiceProto.DeleteEdgeRequest,
              EdgeServiceProto.DeleteEdgeResponse>(
                service, METHODID_DELETE_EDGE)))
        .addMethod(
          getUpdateEdgeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              EdgeServiceProto.UpdateEdgeRequest,
              EdgeServiceProto.CreateEdgeResponse>(
                service, METHODID_UPDATE_EDGE)))
        .addMethod(
          getGetEdgesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              EdgeServiceProto.GetEdgesRequest,
              EdgeServiceProto.GetEdgesResponse>(
                service, METHODID_GET_EDGES)))
        .addMethod(
          getGetOutgoingEdgesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              EdgeServiceProto.GetEdgesRequest,
              EdgeServiceProto.GetEdgesResponse>(
                service, METHODID_GET_OUTGOING_EDGES)))
        .addMethod(
          getGetIncomingEdgesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              EdgeServiceProto.GetEdgesRequest,
              EdgeServiceProto.GetEdgesResponse>(
                service, METHODID_GET_INCOMING_EDGES)))
        .build();
  }

  private static abstract class EdgeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EdgeServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return EdgeServiceProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EdgeService");
    }
  }

  private static final class EdgeServiceFileDescriptorSupplier
      extends EdgeServiceBaseDescriptorSupplier {
    EdgeServiceFileDescriptorSupplier() {}
  }

  private static final class EdgeServiceMethodDescriptorSupplier
      extends EdgeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EdgeServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EdgeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EdgeServiceFileDescriptorSupplier())
              .addMethod(getCreateEdgeMethod())
              .addMethod(getGetEdgeMethod())
              .addMethod(getDeleteEdgeMethod())
              .addMethod(getUpdateEdgeMethod())
              .addMethod(getGetEdgesMethod())
              .addMethod(getGetOutgoingEdgesMethod())
              .addMethod(getGetIncomingEdgesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
