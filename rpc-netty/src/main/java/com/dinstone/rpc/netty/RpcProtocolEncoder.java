/*
 * Copyright (C) 2012~2014 dinstone<dinstone@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dinstone.rpc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.dinstone.rpc.protocol.RpcObject;
import com.dinstone.rpc.protocol.RpcProtocolCodec;
import com.dinstone.rpc.protocol.RpcRequest;
import com.dinstone.rpc.protocol.RpcResponse;

public class RpcProtocolEncoder extends MessageToByteEncoder<RpcObject> {

    private int maxObjectSize = Integer.MAX_VALUE;

    private boolean server;

    public RpcProtocolEncoder(boolean server) {
        this.server = server;
    }

    /**
     * the maxObjectSize to get
     * 
     * @return the maxObjectSize
     * @see RpcProtocolEncoder#maxObjectSize
     */
    public int getMaxObjectSize() {
        return maxObjectSize;
    }

    /**
     * the maxObjectSize to set
     * 
     * @param maxObjectSize
     * @see RpcProtocolEncoder#maxObjectSize
     */
    public void setMaxObjectSize(int maxObjectSize) {
        if (maxObjectSize <= 0) {
            throw new IllegalArgumentException("maxObjectSize: " + maxObjectSize);
        }

        this.maxObjectSize = maxObjectSize;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcObject message, ByteBuf out) throws Exception {
        if (server) {
            RpcResponse response = (RpcResponse) message;
            byte[] rpcBytes = RpcProtocolCodec.encodeResponse(response);
            writeFrame(out, rpcBytes);
        } else {
            RpcRequest request = (RpcRequest) message;
            byte[] rpcBytes = RpcProtocolCodec.encodeRequest(request);
            writeFrame(out, rpcBytes);
        }
    }

    private void writeFrame(ByteBuf out, byte[] rpcBytes) {
        int objectSize = rpcBytes.length;
        if (objectSize > maxObjectSize) {
            throw new IllegalArgumentException("The encoded object is too big: " + objectSize + " (> " + maxObjectSize
                    + ')');
        }

        // FrameLen = PrefixLen + RpcObjectSize
        out.writeInt(objectSize);
        out.writeBytes(rpcBytes);
    }

}
