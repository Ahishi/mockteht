package Testing;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import main.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TilaustenKäsittelyTest {

    private TilaustenKäsittely käsittelijä;
    private IHinnoittelija hinnoittelijaMock;
    private final Asiakas asiakas;
    private final Tuote tuote;
    private final float expectedBalance;

    public TilaustenKäsittelyTest(Asiakas asiakas, Tuote tuote, float expectedBalance) {
        this.asiakas = asiakas;
        this.tuote = tuote;
        this.expectedBalance = expectedBalance;
    }

    @Before
    public void setUp() {
        hinnoittelijaMock = mock(IHinnoittelija.class);
        käsittelijä = new TilaustenKäsittely(hinnoittelijaMock);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][] {
                { new Asiakas(500), new Tuote("Testituote", 50), 450f },
                { new Asiakas(500), new Tuote("Testituote", 150), 380f }
        });
    }

    @Test
    public void testKäsittely() {
        // Arrange
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote)).thenReturn(getDiscountPercentage());

        // Act
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));

        // Assert
        verify(hinnoittelijaMock).aloita();
        verify(hinnoittelijaMock).lopeta();
        assertEquals(expectedBalance, asiakas.getSaldo(), 0.001f);
    }

    private float getDiscountPercentage() {
        return tuote.getHinta() >= 100 ? 20f : 0f;
    }
}
