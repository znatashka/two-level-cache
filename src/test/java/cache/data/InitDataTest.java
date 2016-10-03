package cache.data;

import cache.utils.InitDataUtils;
import cache.utils.Tools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest({InitDataUtils.class, Tools.class})
public class InitDataTest {

    @Test
    public void initialize() throws Exception {
        // PREPARE
        PowerMockito.mockStatic(InitDataUtils.class);
        PowerMockito.when(InitDataUtils.checkBooleanAnswer(anyString())).thenReturn(true);
        PowerMockito.when(InitDataUtils.checkIntegerAnswer(anyString())).thenReturn(5);

        PowerMockito.mockStatic(Tools.class);
        PowerMockito.when(Tools.readAnswerFromConsole(anyString())).thenReturn("fake_path");

        // ACT
        InitData initData = InitData.initialize();

        // ASSERT
        assertEquals(5, initData.getMaxLengthInMemoryCache());
        assertEquals(5, initData.getMaxLengthFileSystemCache());
        assertEquals("fake_path", initData.getFileSystemCachePath());
    }
}