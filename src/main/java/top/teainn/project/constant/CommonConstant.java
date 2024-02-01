package top.teainn.project.constant;

/**
 * 通用常量
 *
 * @author teainn
 */
public interface CommonConstant {

    /**
     * session 为 key
     * 易班 token 前缀（例yiban:token:34343434-3434343-3434343-3434344   :   {userId: xx}）
     */
    // String YIBAN_TOKEN = "yiban:token:";

    /**
     * session 中为 value
     * 易班的 userid
     */
    String YIBAN_USER_ID = "yibanUserId";

    /**
     * session 中为 value
     * 是否为教师标识
     */
    String IS_TEACHER = "isTeacher";

    /**
     * 学生状态 - 在读
     */
    Integer STU_STATUS_STUDYING = 0;

    /**
     * 学生状态 - 毕业
     */
    Integer STU_STATUS_GRADUATING  = 1;

    /**
     * 五邑大学 中文名称
     */
    String WYU_UNIVERSITY_CN = "五邑大学";

    /**
     * 宿舍核对状态 - 未核对
     */
    Integer DOR_VERIFY_DEFAULT = 0;

    /**
     * 宿舍核对状态 - 核对正确
     */
    Integer DOR_VERIFY_RIGHT = 1;

    /**
     * 宿舍核对状态 - 核对有误
     */
    Integer DOR_VERIFY_ERROR = -1;

    /**
     * 升序
     */
    String SORT_ORDER_ASC = "ascend";

    /**
     * 降序
     */
    String SORT_ORDER_DESC = " descend";
}
