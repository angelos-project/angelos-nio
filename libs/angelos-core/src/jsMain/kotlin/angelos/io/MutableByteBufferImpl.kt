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

@OptIn(ExperimentalUnsignedTypes::class)
actual class MutableByteBufferImpl internal actual constructor(
    array: ByteArray,
    capacity: Int,
    limit: Int,
    position: Int,
    mark: Int,
    endianness: Endianness
) : MutableByteBuffer(capacity, limit, position, mark, endianness) {
    private val _array = array
    private val _view = _array.asUByteArray()

    actual override fun getArray(): ByteArray = _array
    actual override fun save(value: UByte, offset: Int) { _view[_position + offset] = value }
    actual override fun load(offset: Int): UByte = _view[_mark + offset]

    actual fun toMutableNativeByteBuffer(): MutableNativeByteBufferImpl {
        val mnbb = MutableNativeByteBufferImpl(capacity, limit, position, mark, endian)
        copyInto(mnbb, 0..capacity)
        return mnbb
    }
}