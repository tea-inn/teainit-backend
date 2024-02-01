package top.teainn.project.model.enums;

import lombok.Getter;

/**
 * 业务类型枚举
 * 在操作日志注解中定义类型
 *
 * @author teainn
 * @date 2024/01/13 19:41
 */
@Getter
public enum BusinessTypeEnum {
    /**
     * 其他
     */
    OTHER("其他",0),

    /**
     * 新增
     */
    INSERT("新增",1),

    /**
     * 修改
     */
    UPDATE("修改",2),

    /**
     * 删除
     */
    DELETE("删除",3),

    /**
     * 导入
     */
    IMPORT("导入",4),

    /**
     * 导出
     */
    EXPORT("导出",5);


    private final String text;

    private final Integer value;

    BusinessTypeEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }
}
