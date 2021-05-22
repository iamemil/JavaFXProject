package Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void of() {
        assertEquals(Direction.UP, Direction.of(0, -1));
        assertEquals(Direction.RIGHT, Direction.of(1, 0));
        assertEquals(Direction.DOWN, Direction.of(0, 1));
        assertEquals(Direction.LEFT, Direction.of(-1, 0));
    }

    @Test
    void testOf_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Direction.of(1, 1));
    }

}