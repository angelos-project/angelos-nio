package angelos.io

import kotlin.test.Test
import kotlin.test.assertEquals

class MutableByteBufferTest {
    private val short: Short = 0B1010101_10101010
    private val ushort: UShort = 0B10101010_10101010u

    private val int: Int = 0B1010101_10101010_10101010_10101010
    private val uint: UInt = 0B10101010_10101010_10101010_10101010u

    private val long: Long = 0B1010101_10101010_10101010_10101010_10101010_10101010_10101010_10101010L
    private val ulong: ULong = 0B10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010u

    private val size: Int = 128
    private var buffer = mutableByteBufferWith(size)

    fun reverseEndian() {
        buffer.endian = when(buffer.endian != Endianness.LITTLE_ENDIAN) {
            true -> Endianness.LITTLE_ENDIAN
            false -> Endianness.BIG_ENDIAN
        }
    }

    @Test
    fun writeChar() {
        val letter = 'Å'

        buffer.setChar(letter)
        assertEquals(buffer.position, Char.SIZE_BYTES)
        assertEquals(buffer.getChar(), letter)

        reverseEndian()
        buffer.rewind()

        buffer.setChar(letter)
        assertEquals(buffer.position, Char.SIZE_BYTES)
        assertEquals(buffer.getChar(), letter)
    }

    @Test
    fun writeShort() {
        buffer.setShort(short)
        assertEquals(buffer.position, Short.SIZE_BYTES)
        assertEquals(buffer.getShort(), short)

        reverseEndian()
        buffer.rewind()

        buffer.setShort(short)
        assertEquals(buffer.position, Short.SIZE_BYTES)
        assertEquals(buffer.getShort(), short)
    }

    @Test
    fun writeUShort() {
        buffer.setUShort(ushort)
        assertEquals(buffer.position, UShort.SIZE_BYTES)
        assertEquals(ushort, buffer.getUShort())

        reverseEndian()
        buffer.rewind()

        buffer.setUShort(ushort)
        assertEquals(buffer.position, UShort.SIZE_BYTES)
        assertEquals(ushort, buffer.getUShort())
    }

    @Test
    fun writeInt() {
        buffer.setInt(-int)
        assertEquals(buffer.position, Int.SIZE_BYTES)
        assertEquals(buffer.getInt(), -int)

        reverseEndian()
        buffer.rewind()

        buffer.setInt(-int)
        assertEquals(buffer.position, Int.SIZE_BYTES)
        assertEquals(buffer.getInt(), -int)
    }

    @Test
    fun writeUInt() {
        buffer.setUInt(uint)
        assertEquals(buffer.position, UInt.SIZE_BYTES)
        assertEquals(buffer.getUInt(), uint)

        reverseEndian()
        buffer.rewind()

        buffer.setUInt(uint)
        assertEquals(buffer.position, UInt.SIZE_BYTES)
        assertEquals(buffer.getUInt(), uint)
    }

    @Test
    fun writeLong() {
        buffer.setLong(long)
        assertEquals(buffer.position, Long.SIZE_BYTES)
        assertEquals(buffer.getLong(), long)

        reverseEndian()
        buffer.rewind()

        buffer.setLong(long)
        assertEquals(buffer.position, Long.SIZE_BYTES)
        assertEquals(buffer.getLong(), long)
    }

    @Test
    fun writeULong() {
        buffer.setULong(ulong)
        assertEquals(buffer.position, ULong.SIZE_BYTES)
        assertEquals(buffer.getULong(), ulong)

        reverseEndian()
        buffer.rewind()

        buffer.setULong(ulong)
        assertEquals(buffer.position, ULong.SIZE_BYTES)
        assertEquals(buffer.getULong(), ulong)
    }

    @Test
    fun writeFloat() {
        val value: Float = -123.565F
        buffer.setFloat(value)
        assertEquals(buffer.position, Float.SIZE_BYTES)
        assertEquals(buffer.getFloat(), value)

        reverseEndian()
        buffer.rewind()

        buffer.setFloat(value)
        assertEquals(buffer.position, Float.SIZE_BYTES)
        assertEquals(buffer.getFloat(), value)
    }

    @Test
    fun writeDouble() {
        val value: Double = (-234958739.324893498573495834753947535234571209347F).toDouble()
        buffer.setDouble(value)
        assertEquals(buffer.position, Double.SIZE_BYTES)
        assertEquals(buffer.getDouble(), value, 0.0)

        reverseEndian()
        buffer.rewind()

        buffer.setDouble(value)
        assertEquals(buffer.position, Double.SIZE_BYTES)
        assertEquals(buffer.getDouble(), value, 0.0)
    }
}