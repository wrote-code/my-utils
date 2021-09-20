package com.sheepfly.common.services.interfaces.impls;

import com.sheepfly.common.services.interfaces.Service;
import org.junit.Test;

public class AbstractDefaultServiceImplTest {
    @Test
    public void testInit() {
        Service service = new Gradle2PomService();
        service.init(new String[4]);
    }
}