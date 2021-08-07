package com.sheepfly.common.services.interfaces.impls;

import com.sheepfly.common.services.interfaces.Service;
import com.sheepfly.common.utils.OptionUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    /**
     * 原市参数，即main方法的参数。
     */
    private String[] rawArgs;

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
        this.rawArgs = cli.getArgs();
        String serviceName = this.getClass().getName();
        serviceName = serviceName.substring(serviceName.lastIndexOf(".") + 1, serviceName.length());
        String methodName = "build" + serviceName.replace("Service", "") + "Option";
        try {
            Method method = OptionUtil.class.getMethod(methodName);
            Object result = method.invoke(OptionUtil.class);
            CommandLineParser parser = new DefaultParser();
            CommandLine cli = parser.parse((Options) result, args);
            this.cli = cli;
            this.args = cli.getArgs();
            log.info("参数解析完成");
        } catch (NoSuchMethodException e) {
            log.error("没有对应的方法", e);
        } catch (IllegalAccessException e) {
            log.error("非法访问", e);
        } catch (InvocationTargetException e) {
            log.error("调用异常", e);
        } catch (ParseException e) {
            log.error("参数解析失败", e);
        }
    }
}
