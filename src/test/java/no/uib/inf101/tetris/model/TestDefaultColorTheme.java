package no.uib.inf101.tetris.model;

import no.uib.inf101.tetris.view.ColorTheme;
import no.uib.inf101.tetris.view.DefaultColorTheme;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDefaultColorTheme {
    @Test
    public void sanityTestDefaultColorTheme() {
        ColorTheme colors = new DefaultColorTheme();
        assertEquals(new Color(242,233,234, 200), colors.getBackgroundColor());
        assertEquals(new Color(0,47,108, 200), colors.getFrameColor());
        assertEquals(Color.BLACK, colors.getCellColor('-'));
        assertEquals(new Color(199,36,177, 255), colors.getCellColor('T'));
        assertThrows(IllegalArgumentException.class, () -> colors.getCellColor('\n'));
    }
}
