package top.teainn.project.constant;

/**
 * Redis 通用常量
 *
 * @author teainn
 * @date 2024/01/10 22:07
 */
public class RedisConstant {
    public static final String YIBAN_TOKEN_KEY = "yiban:token:";

    /**
     * 30 天过期
     */
    public static final Long YIBAN_TOKEN_TTL = 30L;

    public static final Long CACHE_NULL_TTL = 2L;

    public static final String LOCK_SHOP_KEY = "lock:shop:";

}