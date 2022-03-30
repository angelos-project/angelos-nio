/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package angelos.io

import angelos.interop.NativeBuffer

expect class NativeByteBufferImpl internal constructor(
    capacity: Int,
    limit: Int,
    mark: Int,
    endianness: Endianness
): ByteBuffer, NativeBuffer {
    override fun getArray(): ByteArray
    override fun load(offset: Int): UByte

    fun toByteBuffer(): ByteBufferImpl
    fun toMutableNativeByteBuffer(): MutableNativeByteBufferImpl
}

fun nativeByteBufferWith(capacity: Int, endianness: Endianness = ByteBuffer.nativeEndianness): NativeByteBufferImpl = NativeByteBufferImpl(capacity, capacity, 0, endianness)