package top.teainn.project.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * 生成 token 和解析 token 的工具类
 *
 * @author teainn
 * @date 2023/11/03 14:21
 */
public class TokenUtil {
    //密钥明文
    private static final String SECRET_CLEARTEXT = "EveryOne";
    //过期时间默认为半小时后过期
    private static final long EXPIRATION_TIME = 1000 * 60 * 30L;

    /**
     * 以 Base64 编码获取一个 AES 算法密钥
     */
    public static SecretKey getSecretKey() {
        //以 Base64 编码获取到明文密钥的字节,以该数组生成一个AES算法的的密钥
        return new SecretKeySpec(Base64.getDecoder().decode(SECRET_CLEARTEXT), "AES");
    }

    /**
     * 获取一个无 - 作为 token 的唯一ID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 以默认的过期时间构造 token
     *
     * @param str 存放在token里的信息
     * @return java.lang.String
     */
    public static String getToken(String str) {
        return getJwtBuilder(str).compact();
    }

    /**
     * 以指定的过期时间构造token
     *
     * @param str   存放在 token 里的信息
     * @param mills 指定多少毫秒后过期
     * @return java.lang.String
     */
    public static String getToken(String str, long mills) {
        return getJwtBuilder(str, mills).compact();
    }


    /**
     * 获取一个 JWT 的构造器
     *
     * @param str 存放在 token 里的信息
     * @return io.jsonwebtoken.JwtBuilder
     */
    public static JwtBuilder getJwtBuilder(String str) {
        if (StringUtils.isBlank(str)) {
            throw new RuntimeException("实体数据为空");
        }
        //获取到 AES 算法的密钥
        long nowMills = System.currentTimeMillis();
        DefaultClaims defaultClaims = new DefaultClaims();
        defaultClaims.put("msg", str);
        return Jwts.builder()
                .setId(getUUID())          //唯一的ID
                .setSubject("token")       // 数据
                .setIssuer("HerbalTea")     // 签发者
                .setClaims(defaultClaims)  //数据
                .setIssuedAt(new Date(nowMills))      // 签发时间设置为当前
                .signWith(SignatureAlgorithm.HS256, getSecretKey()) //使用 HS256 对称加密算法签名, 第二个参数为秘钥
                .setExpiration(new Date(nowMills + EXPIRATION_TIME));//设置为超时时间
    }

    /***
     * 获取一个 token 的构造器
     * @param str 数据
     * @param mills 过期时间
     * @return io.jsonwebtoken.JwtBuilder
     */
    public static JwtBuilder getJwtBuilder(String str, long mills) {
        if (StringUtils.isBlank(str)) {
            throw new RuntimeException("实体数据为空");
        }
        //获取到 AES 算法的密钥
        long nowMills = System.currentTimeMillis();
        DefaultClaims defaultClaims = new DefaultClaims();
        defaultClaims.put("msg", str);
        return Jwts.builder()//一个构造器 下面为必要属性的设置
                .setId(getUUID())           //唯一的ID
                .setSubject("token")        //主题为token
                .setIssuer("HerbalTea")      // 签发者
                .setClaims(defaultClaims)   //数据存放
                .setIssuedAt(new Date(nowMills))      // 签发时间设置为当前
                .signWith(SignatureAlgorithm.HS256, getSecretKey()) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(new Date(nowMills + mills));//设置过期时间
    }

    /**
     * 从给定的token中获取msg信息
     *
     * @param token token
     * @return java.lang.String
     */
    public static String getMsgFromToken(String token)
            throws UnsupportedJwtException,//不支持的格式异常
            MalformedJwtException,//平台jwt异常
            SignatureException,//签名异常
            ExpiredJwtException,//超时异常
            IllegalArgumentException { //非法参数异常
        return Jwts.parser()//token的语法分析器
                .setSigningKey(getSecretKey())//设置签名验证所用的密钥
                .parseClaimsJws(token)//处理token
                .getBody()//获取存入的token里的所有信息
                .get("msg").toString();//获取claims里面存放的msg数据
    }

}

