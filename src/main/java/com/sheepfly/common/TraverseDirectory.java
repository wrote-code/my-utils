package com.sheepfly.common;

import com.sheepfly.common.services.interfaces.Service;
import com.sheepfly.common.services.interfaces.impls.TraverseDirectoryServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 遍历目录。
 *
 * @author sheepfly
 */
@Slf4j
public class TraverseDirectory {
    public static void main(String[] args) {
        Service service = new TraverseDirectoryServiceImpl();
        service.init(args);
        service.doService();
    }
}
