package com.sheepfly.common.services.interfaces;

import org.apache.commons.cli.CommandLine;

/**
 * 服务接口。
 *
 * <p>所有工具类都应该实现此接口，接口实现类应该自动解析程序传入的参数。若从独立应用程序中使用工具，
 * 则参数指的是程序参数，即main方法中的参数。若从其他方法中调用程序，则需要手动组装程序参数对应的数
 * 组来传入初始化方法。</p>
 *
 * <p>每个实现类都应该在{@link Service#init(CommandLine)}或{@link Service#init(String[])}
 * 中初始化程序参数，之后只需要调用{@link Service#doService()}来开始执行程序。</p>
 *
 * @author sheepfly
 */
public interface Service {
    /**
     * 使用命令行参数初始化。
     *
     * <p>若使用此方法进行初始化，需要传入解析后的命令行参数{@link CommandLine}。</p>
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

    /**
     * 使用原始参数初始化程序。
     *
     * @param args main方法中参数
     */
    void init(String[] args);

}
