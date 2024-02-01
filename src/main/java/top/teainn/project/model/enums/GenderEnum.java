package top.teainn.project.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 性别枚举
 *
 * @author teainn
 * @date 2024/01/13 19:42
 */
public enum GenderEnum {

    MALE("男", 0),
    FEMALE("女", 1),

    UNKNOWN("未知",2);

    private final String text;

    private final int value;

    GenderEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
