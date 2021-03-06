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

package com.dinstone.rpc.protocol;

/**
 * RPC protocol version.
 * 
 * @author guojinfei
 * @version 1.0.0.2014-6-23
 */
public enum RpcVersion {
    VERSION1((byte) 1);

    private byte value;

    private RpcVersion(byte value) {
        this.value = value;
    }

    /**
     * the value to get
     * 
     * @return the value
     * @see RpcVersion#value
     */
    public byte getValue() {
        return value;
    }

    public static RpcVersion valueOf(int value) {
        switch (value) {
        case 1:
            return VERSION1;

        default:
            break;
        }
        throw new IllegalArgumentException("unsupported RPC version [" + value + "]");
    }

}
