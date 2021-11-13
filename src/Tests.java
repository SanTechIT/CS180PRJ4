/*
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
*/

import java.util.Scanner;

public class Tests {

    public static void main(String[] args) {
        Teacher test = new Teacher("Bob", "123", "Bobby");

        Scanner reader = new Scanner(System.in);
        test.loop(reader);
    }

    @Test
    public void testOne() {

    }
}
