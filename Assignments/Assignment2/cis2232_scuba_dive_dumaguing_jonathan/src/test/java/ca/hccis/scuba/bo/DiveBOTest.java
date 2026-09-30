package ca.hccis.scuba.bo;

import ca.hccis.scuba.entity.Dive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the DiveBO calculate method.
 *
 * These three tests were written following a test driven development (TDD)
 * approach.  Each test was written first, run to confirm it failed, and then
 * only the code needed to make it pass was added to DiveBO.calculate.
 *
 * @author Jonathan Dumaguing
 * @since 20260930
 */
public class DiveBOTest {

    private static final double DELTA = 0.01;

    /**
     * Test the calculation with values that are easy to verify by hand.
     *
     * TDD note: This test was written first, before calculate existed.  It
     * failed (red), then the Mosteller BSA, gender factor, age multiplier,
     * ata and tank size steps were coded to make it pass (green).
     *
     * Hand calculation:
     * bsa = sqrt(180 x 80 / 3600) = 2.0
     * baselineSAC = 2.0 x 9.8 x 1.00 = 19.6
     * ata = 20 / 10 + 1 = 3.0
     * totalAir = 19.6 x 1.0 x 3.0 x 30 = 1764
     * tank = 1764 / 150 = 11.76
     *
     * @author Jonathan Dumaguing
     * @since 20260930
     */
    @Test
    public void testCalculateMaleDriftDive() {
        Dive dive = new Dive(1, "Test Diver", 180.0, 80.0,
                "MALE", "AGE_20S", 20.0, 30.0, "DRIFT");

        double result = DiveBO.calculate(dive);

        assertEquals(11.76, result, DELTA);
    }

    /**
     * Test that the female gender factor, the 50s plus age multiplier and the
     * cave/wreck scenario multiplier are applied.
     *
     * TDD note: This test was written second.  It failed until the FEMALE
     * gender factor, the AGE_50S_PLUS multiplier and the CAVE_WRECK scenario
     * multiplier were added to DiveBO.
     *
     * @author Jonathan Dumaguing
     * @since 20260930
     */
    @Test
    public void testCalculateFemaleCaveWreckDive() {
        Dive dive = new Dive(2, "Test Diver", 160.0, 55.0,
                "FEMALE", "AGE_50S_PLUS", 10.0, 40.0, "CAVE_WRECK");

        double result = DiveBO.calculate(dive);

        assertEquals(10.79, result, DELTA);
        assertTrue(result > 0, "Tank size should be greater than zero");
    }

    /**
     * Test that invalid input is rejected instead of returning a bad value.
     *
     * TDD note: This test was written third.  It failed until the validation
     * for a null dive and for zero/negative height was added to calculate.
     *
     * @author Jonathan Dumaguing
     * @since 20260930
     */
    @Test
    public void testCalculateInvalidInputThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> DiveBO.calculate(null));

        Dive badDive = new Dive(3, "Test Diver", 0.0, 80.0,
                "MALE", "AGE_20S", 20.0, 30.0, "DRIFT");
        assertThrows(IllegalArgumentException.class, () -> DiveBO.calculate(badDive));
    }
}
