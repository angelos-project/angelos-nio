import angelos.nio.ByteBuffer
import angelos.nio.InvalidMarkException
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class ByteBufferTest {
    val short: Short = 0B1010101_10101010
    val ushort: UShort = 0B10101010_10101010u

    val int: Int = 0B1010101_10101010_10101010_10101010
    val uint: UInt = 0B10101010_10101010_10101010_10101010u

    val long: Long = 0B1010101_10101010_10101010_10101010_10101010_10101010_10101010_10101010L
    val ulong: ULong = 0B10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010u

    val size: Int = 128
    var buffer: ByteBuffer? = null

    @Before
    fun setUp() {
        buffer = ByteBuffer(size)
    }

    @After
    fun tearDown() {
        buffer = null
    }

    inline fun <reified E : kotlin.Exception> assertExceptionThrown(test: () -> Unit, message: String) {
        var happened: Boolean = false
        try {
            test()
        } catch (e: Exception) {
            if (e is E) {
                happened = true
            }
        } finally {
            assertEquals(happened, true, message)
        }
    }

    @Test
    fun getReadOnly() {
        assertEquals(buffer?.readOnly, false, "Value 'readOnly' should implicitly be 'true'.")
    }

    @Test
    fun getDirect() {
        assertEquals(buffer?.direct, true, "Value 'direct' should implicitly be 'true'.")
    }

    @Test
    fun getCapacity() {
        assertEquals(
            buffer?.capacity,
            size,
            "Value 'capacity' should implicitly be the length of the underlying array."
        )
    }

    @Test
    fun getLimit() {
        assertEquals(buffer?.limit, size, "Property 'limit' should implicitly be set to 'capacity'.")
    }

    @Test
    fun setLimit() {
        assertExceptionThrown<IllegalArgumentException>(
            { buffer?.limit = -1 }, "Property 'limit' can not accept a negative value."
        )
        assertExceptionThrown<IllegalArgumentException>(
            { buffer?.limit = 1 + buffer?.capacity!! },
            "Property 'limit' can not be higher than property 'capacity'."
        )

        val limit: Int = (size * .67).toInt()
        buffer?.position = size
        buffer?.limit = limit
        assertEquals(buffer?.limit, limit, "Property 'limit' should accept value between 0 and 'capacity'.")
        assertEquals(buffer?.position, limit, "Property 'position' should be decreased to new 'limit'.")
    }

    @Test
    fun getPosition() {
        assertEquals(buffer?.position, 0, "Property 'position' should implicitly be set to 0.")
    }

    @Test
    fun setPosition() {
        assertExceptionThrown<IllegalArgumentException>(
            { buffer?.position = -1 }, "Property 'position' can not accept a negative value."
        )
        assertExceptionThrown<IllegalArgumentException>(
            { buffer?.position = 1 + buffer?.limit!! },
            "Property 'position' can not be higher than property 'limit'."
        )

        val position: Int = (buffer?.limit!! * .67).toInt()
        buffer?.position = position
        assertEquals(buffer?.position, position, "Property 'position' should accept value between 0 and 'limit'.")
    }

    @Test
    fun clear() {
        buffer?.limit = (size * .67).toInt()
        buffer?.position = (buffer?.limit!! * .67).toInt()

        buffer?.clear()

        assertEquals(buffer?.limit, size, "Property 'limit' should be cleared to 'capacity'.")
        assertEquals(buffer?.position, 0, "Property 'position' should be cleared to 0.")
    }

    @Test
    fun flip() {
        val position: Int = (buffer?.limit!! * .67).toInt()
        buffer?.position = position

        buffer?.flip()

        assertEquals(buffer?.limit, position, "Property 'limit' should be flipped to 'position'.")
    }

    @Test
    fun hasRemaining() {
        assertEquals(buffer?.hasRemaining(), true, "Method 'hasRemaining' should return true if 'position' has not reached the value of 'limit'.")
        buffer?.position = size
        assertEquals(buffer?.hasRemaining(), false, "When 'position' has reached value 'limit' method 'hasRemaining' should return false.")
    }

    @Test
    fun mark() {
        val position: Int = (buffer?.limit!! * .33).toInt()
        buffer?.position = position

        buffer?.mark()
        buffer?.reset()
    }

    @Test
    fun remaining() {
        assertEquals(buffer?.remaining(), buffer?.limit!! - buffer?.position!!, "Method 'remaining' should return 'limit' minus 'position'.")
    }

    @Test
    fun reset() {
        assertExceptionThrown<InvalidMarkException>(
            { buffer?.reset()}, "Unmarked buffer shouldn't be resettable."
        )
    }

    @Test
    fun rewind() {
        val position: Int = (buffer?.limit!! * .67).toInt()
        buffer?.position = position

        buffer?.rewind()

        assertEquals(buffer?.position, 0, "Method 'rewind' should rewind 'position' to 0.")
    }

    @Test
    fun get() {
    }

    @Test
    fun set() {
    }

    @Test
    fun testSet() {
    }

    @Test
    fun hasArray() {
        assertEquals(buffer?.hasArray(), true, "Method 'hasArray' should return true implicitly.")
    }

    @Test
    fun array() {
        assertEquals(buffer?.array() is ByteArray, true, "Method 'array' should return a ByteArray.")
    }

    @Test
    fun arrayOffset() {

    }

    @Test
    fun testHashCode() {
    }

    @Test
    fun testEquals() {
    }

    @Test
    fun compareTo() {
    }

    @Test
    fun testGet() {
    }

    @Test
    fun testSet1() {
    }

    @Test
    fun testGet1() {
    }

    @Test
    fun testSet2() {
    }

    @Test
    fun shiftDown() {
    }

    @Test
    fun slice() { // To be implemented
    }

    @Test
    fun duplicate() { // To be implemented
    }

    @Test
    fun asReadOnly() { // To be implemented
    }

    @Test
    fun readChar() {

    }

    @Test
    fun writeChar() {
        val letter: Char = 'Å'
        buffer?.writeChar(letter)
        assertEquals(buffer?.position, Char.SIZE_BYTES, "Method 'writeChar' should advance the 'position' with 'Char.SIZE_BYTES'.")
        buffer?.position = 0
        assertEquals(buffer?.readChar(), letter, "Method 'readChar' should be able to read what was written.")
    }

    @Test
    fun readShort() {
    }

    @Test
    fun writeShort() {
        buffer?.writeShort(short)
        assertEquals(buffer?.position, Short.SIZE_BYTES, "Method 'writeShort' should advance the 'position' with 'Short.SIZE_BYTES'.")
        buffer?.position = 0
        assertEquals(buffer?.readShort(), short, "Method 'readShort' should be able to read what was written.")
    }

    @Test
    fun readUShort() {
    }

    @Test
    fun writeUShort() {
        buffer?.writeUShort(ushort)
        assertEquals(buffer?.position, UShort.SIZE_BYTES, "Method 'writeUShort' should advance the 'position' with 'UShort.SIZE_BYTES'.")
        buffer?.position = 0
        assertEquals(ushort, buffer?.readUShort(),"Method 'readUShort' should be able to read what was written.")
    }

    @Test
    fun readInt() {
    }

    @Test
    fun writeInt() {
        buffer?.writeInt(1_000_000)
        assertEquals(buffer?.position, Int.SIZE_BYTES, "Method 'writeInt' should advance the 'position' with 'Int.SIZE_BYTES'.")
        buffer?.position = 0
        assertEquals(buffer?.readInt(), 1_000_000, "Method 'ReadInt' should be able to read what was written.")
    }

    @Test
    fun readUInt() {
    }

    @Test
    fun writeUInt() {
    }

    @Test
    fun readLong() {
    }

    @Test
    fun writeLong() {
        buffer?.writeLong(1_000_000)
        assertEquals(buffer?.position, Long.SIZE_BYTES, "Method 'writeLong' should advance the 'position' with 'Long.SIZE_BYTES'.")
        buffer?.position = 0
        assertEquals(buffer?.readLong(), 1_000_000, "Method 'readLong' should be able to read what was written.")
    }

    @Test
    fun readULong() {
    }

    @Test
    fun writeULong() {
    }

    @Test
    fun readFloat() {
    }

    @Test
    fun writeFloat() {
        val value: Float = 123.565F
        buffer?.writeFloat(value)
        assertEquals(buffer?.position, Float.SIZE_BYTES, "Method 'writeFloat' should advance the 'position' with 'Float.SIZE_BYTES'.")
        buffer?.position = 0
        assertEquals(buffer?.readFloat(), value, "Method 'readFloat' should be able to read what was written.")
    }

    @Test
    fun readDouble() {
    }

    @Test
    fun writeDouble() {
    }

    @Test
    fun testToString() {
    }

    @Test
    fun allocateDirect() {
        buffer = ByteBuffer.allocateDirect(size)
        assertEquals(buffer!!.capacity, size, "Value 'capacity' should always be the same as the given size.")
        assertEquals(buffer!!.limit, buffer!!.capacity, "Property 'limit' should implicitly be set to capacity.")
        assertEquals(buffer!!.position, 0, "Property 'position' should implicitly be set to 0.")
        assertEquals(buffer!!.readOnly, false, "Value 'readOnly' should implicitly be set to 'false'.")
        assertEquals(buffer!!.direct, true, "Value 'direct' should implicitly be set to 'true'.")
    }

    @Test
    fun allocate() {
        buffer = ByteBuffer.allocate(size)
        assertEquals(buffer!!.capacity, size, "Value 'capacity' should always be the same as the given size.")
        assertEquals(buffer!!.limit, buffer!!.capacity, "Property 'limit' should implicitly be set to capacity.")
        assertEquals(buffer!!.position, 0, "Property 'position' should implicitly be set to 0.")
        assertEquals(buffer!!.readOnly, false, "Value 'readOnly' should implicitly be set to 'false'.")
        assertEquals(buffer!!.direct, false, "Value 'direct' should implicitly be set to 'false'.")
    }

    @Test
    fun wrap() {
        buffer = ByteBuffer.wrap(ByteArray(size))
        assertEquals(buffer!!.capacity, size, "Value 'capacity' should always be the same as the given size.")
        assertEquals(buffer!!.limit, buffer!!.capacity, "Property 'limit' should implicitly be set to capacity.")
        assertEquals(buffer!!.position, 0, "Property 'position' should implicitly be set to 0.")
        assertEquals(buffer!!.readOnly, false, "Value 'readOnly' should implicitly be set to 'false'.")
        assertEquals(buffer!!.direct, false, "Value 'direct' should implicitly be set to 'false'.")
    }
}