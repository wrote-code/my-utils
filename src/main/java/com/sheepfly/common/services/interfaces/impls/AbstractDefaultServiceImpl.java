package com.sheepfly.common.services.interfaces.impls;

import com.sheepfly.common.services.interfaces.Service;
import com.sheepfly.common.utils.OptionUtil;
import lombok.Data;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Service}的默认实现。
 *
 * <p>默认实现会设置程序的命令行参数。</p>
 *
 * <p>在默认情况下，会将解析后的参数放入{@link AbstractDefaultServiceImpl#cli}中，同时将
 * 没有解析的参数放入{@link AbstractDefaultServiceImpl#args}中。另外，程序的原始参数，即从
 * 主方法中传入的参数，会保存在{@link AbstractDefaultServiceImpl#rawArgs}中。
 * </p>
 *
 * <p>通常，{@link Service}的实现中会有自己的参数，这些参数会保存在成员变量中。可以在具体实现中
 * 初始化这些成员变量，建议在初始化前调用{@link AbstractDefaultServiceImpl#init(String[])}
 * 或者{@link AbstractDefaultServiceImpl#init(CommandLine)}。
 * </p>
 *
 * @author sheepfly
 */
@Data
public abstract class AbstractDefaultServiceImpl implements Service {
    private static final Logger log = LoggerFactory.getLogger(AbstractDefaultServiceImpl.class);

    /**
     * 命令行参数。
     */
    private Options options;
    /**
     * 命令行参数。
     */
    private CommandLine cli;
    /**
     * 没有解析的命令行参数。
     */
    private String[] args;

    /**
     * 原始参数，即main方法的参数。
     */
    private String[] rawArgs;

    @Override
    public void init(CommandLine cli) {
        this.cli = cli;
        this.args = cli.getArgs();
    }

    /**
     * 初始化方法。
     *
     * <p>传入参数后，将参数分别解析为由横线定义的参数和剩余参数。例如，对于命令{@code java -cp
     * lib.jar Hello}来说，横线定义的参数是 cp，剩余参数是 Hello。
     * </p>
     *
     * @param args main方法中参数
     */
    @Override
    public void init(String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine parsedCli = parser.parse(OptionUtil.buildOptions(this.getClass()), args);
            this.cli = parsedCli;
            this.args = parsedCli.getArgs();
            this.rawArgs = parsedCli.getArgs();
            log.info("参数解析完成");
        } catch (ParseException e) {
            log.error("参数解析失败", e);
        }
    }
}
