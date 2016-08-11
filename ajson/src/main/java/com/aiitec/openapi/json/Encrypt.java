package com.aiitec.openapi.json;


import com.aiitec.openapi.utils.AiiUtil;

/**
 * 加密类
 * @author Anthony
 *
 */
public class Encrypt {

   
    /**
     * 加盐内容
     */
    private static String saltingStr = "81hqbcqfn5m80dreg526s8knq6";
    /**
     * 修改加盐内容
     * @param saltingStr
     */
    public static void setSaltingStr(String saltingStr) {
		Encrypt.saltingStr = saltingStr;
	}
   
    /**
     * 密码加盐
     * @param str
     * @return 加盐后的密码
     */
    public static String saltingPassword(String str) {
    	return AiiUtil.md5(str+saltingStr);
    }
}
