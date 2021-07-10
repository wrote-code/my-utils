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
     *
     */
    public static Options buildOptions(List<Map<String, Object>> optionList) {
        Options options = new Options();
        optionList.forEach(ele -> {
            if (ele.containsKey(OPTION_KEY.OPT) && ele.containsKey(OPTION_KEY.DESCRIPTION)) {
                Option option = new Option(ele.get("opt").toString(), ele.get("description").toString());
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
        ARG_NAME("ARG_NAME", 1), DESCRIPTION("DESCRIPTION", 2), LONG_OPT("LONG_OPT", 3), NUMBER_OF_ARGS(
                "NUMBER_OF_ARGS", 4), OPT("OPT", 5), REQUIRED("REQUIRED", 6), TYPE("TYPE", 7);

        private String key;
        private int num;

        OPTION_KEY(String key, int num) {
            this.key = key;
            this.num = num;
        }
    }
}
