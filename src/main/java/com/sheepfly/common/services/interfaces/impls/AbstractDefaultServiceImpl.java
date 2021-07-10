package com.sheepfly.common.services.interfaces.impls;

import com.sheepfly.common.services.interfaces.Service;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Service}的默认实现。
 *
 * <p>默认实现会设置程序的命令行参数。</p>
 *
 * <p>命令行参数有两种类型，一种是必须传入的参数，比如{@code node hello.js}中，hello.js就是必须传入
 * 的参数。另一种是可选参数，即以{@code -}家字符描述的参数。在初始化时，会讲这两种参数分别保存在成员变量
 * cli和args中。</p>
 *
 * @author sheepfly
 */
public abstract class AbstractDefaultServiceImpl implements Service {
    private static final Logger log = LoggerFactory.getLogger(AbstractDefaultServiceImpl.class);

    /**
     * 命令行参数。
     */
    private CommandLine cli;
    /**
     * 必须传入的命令行参数。
     */
    private String[] args;

    @Override
    public void init(CommandLine cli) {
        this.cli = cli;
        this.args = cli.getArgs();
    }

    /**
     * 此方法请自行实现，此处实现为应对语法检查。
     */
    @Override
    public void doService() {
        log.warn("此方法请自行实现，此处实现为应对语法检查");
    }
}
