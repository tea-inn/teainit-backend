package top.teainn.project.common;

import top.teainn.project.constant.CommonConstant;
import lombok.Data;

/**
 * 分页请求
 *
 * @author teainn
 * @date 2024/01/10 21:31
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private Long current = 1L;

    /**
     * 页面大小
     */
    private Long pageSize = 10L;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
