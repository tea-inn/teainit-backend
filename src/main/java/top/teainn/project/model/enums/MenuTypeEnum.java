package top.teainn.project.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单类别枚举
 *
 * @author teainn
 * @date 2024/01/28 10:19
 */
public enum MenuTypeEnum {

    DIRECTORY("目录", 0),

    MENU("菜单", 1),

    BUTTON("按钮",2);

    private final String text;

    private final int value;

    MenuTypeEnum(String text, int value) {
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
