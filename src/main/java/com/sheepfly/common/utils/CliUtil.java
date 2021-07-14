package com.sheepfly.common.utils;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * CliUtil
 *
 * @author sheepfly
 */
public class CliUtil {
    private static Logger log = LoggerFactory.getLogger(CliUtil.class);

    /**
     * 生成参数对应的{@link Options}
     *
     * <p>生成{@link Options}需要传入参数列表。每一个参数列表需要定义以下key:</p>
     * <ul>
     *     <li>OPT:参数名</li>
     *     <li>LONG_OPT:长参数名</li>
     *     <li>DESCRIPTION:参数说明</li>
     *     <li>REQUIRED:参数是否必须传入</li>
     *     <li>NUMBER_OF_ARGS:参数个数</li>
     * </ul>
     * <p>以上key可以手工输入，可以通过枚举类{@link OPTION_KEY}输入。讲参数列表传入后，方法会自动
     * 根据参数列表自动组装{@link Options}并返回。</p>
     *
     * @param optionList 参数列表
     */
    public static Options buildOptions(List<Map<CliUtil.OPTION_KEY, Object>> optionList) {
        Options options = new Options();
        optionList.forEach(ele -> {
            if (ele.containsKey(OPTION_KEY.OPT) && ele.containsKey(OPTION_KEY.DESCRIPTION)) {
                Option option = new Option(ele.get(OPTION_KEY.OPT).toString(),
                        (Boolean) ele.get(OPTION_KEY.HAS_ARGS),
                        ele.get(OPTION_KEY.LONG_OPT).toString());
                if (ele.containsKey(OPTION_KEY.LONG_OPT)) {
                    option.setLongOpt(ele.get(OPTION_KEY.LONG_OPT).toString());
                }
                if (ele.containsKey(OPTION_KEY.REQUIRED)) {
                    option.setRequired((Boolean) ele.get(OPTION_KEY.REQUIRED));
                }
                if (ele.containsKey(OPTION_KEY.NUMBER_OF_ARGS)) {
                    option.setArgs((Integer) ele.get(OPTION_KEY.NUMBER_OF_ARGS));
                }
                options.addOption(option);
            } else {
                log.warn("缺少参数opt和description");
            }
        });
        return options;
    }

    /**
     * 参数选项
     *
     * @author sheepfly
     */
    public enum OPTION_KEY {
        ARG_NAME("ARG_NAME", 1),
        DESCRIPTION("DESCRIPTION", 2),
        LONG_OPT("LONG_OPT", 3),
        NUMBER_OF_ARGS("NUMBER_OF_ARGS", 4),
        OPT("OPT", 5),
        REQUIRED("REQUIRED", 6),
        TYPE("TYPE", 7),
        HAS_ARGS("HAS_ARGS", 8);

        private String key;
        private int num;

        OPTION_KEY(String key, int num) {
            this.key = key;
            this.num = num;
        }
    }
}
