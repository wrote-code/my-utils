package com.sheepfly.common.services.interfaces;

import org.apache.commons.cli.CommandLine;

/**
 * 服务接口。
 *
 * <p>所有工具入口(main方法)只提供参数解析、调用初始化方法以及调用服务实例方法，
 * 工具的操作需要实现此接口。</p>
 *
 * @author sheepfly
 */
public interface Service {
    /**
     * 使用命令行参数初始化。
     *
     * @param cli 解析后的命令行参数
     */
    void init(CommandLine cli);

    /**
     * 工具类的实际操作。
     *
     * <p>工具类的实际操作。在初始化完成后调用此方法即可开始使用工具。</p>
     */
    void doService();

}
