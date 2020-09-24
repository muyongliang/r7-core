package com.r7.core.setting.common.domain;

import cn.hutool.json.JSONUtil;
import com.r7.core.setting.common.enums.RetEnum;


/**
 * @Description: 系统 api 返回 封装
 * @author: jinghan
 * @date 2019/4/24 15:29
 */
public class ResultUtil {
    /**
	 * 数据交互成功返回
	 * @param object json返回的数据
	 * */
	public static Result success(Object object){
		if(object==null){
			object = "";
		}
		return new Result(RetEnum.RET_SUCCESS.getCode(),RetEnum.RET_SUCCESS.getMessage(),object);
	}
	/**
	 * 数据交互
	 * */
	public static Result notFound(){
			return new Result(RetEnum.RET_BIZ_DATA_NOT_EXISTS.getCode(),RetEnum.RET_BIZ_DATA_NOT_EXISTS.getMessage(),"");
	}
	/**
	 * 未登录
	 * */
	public static Result  notLoggedIn (){
		return new Result(RetEnum.RET_AUTH_NO_LOGIN.getCode(),RetEnum.RET_AUTH_NO_LOGIN.getMessage(),"");
	}

	/**
	 * 异常处理
	 * @param retEnum
	 * @return
	 */
	public static Result error(RetEnum retEnum){
		return new Result(retEnum.getCode(),retEnum.getMessage(),"");
	}
	/**
	 * 异常处理
	 * @param retEnum
	 * @return
	 */
	public static Result error(RetEnum retEnum,Object result){
		return new Result(retEnum.getCode(),retEnum.getMessage(),result);
	}

	/**
	 * 异常处理
	 * @param code
	 * @return
	 */
	public static Result error(String code,String msg){
		return new Result(code,msg,null);
	}

	/**
	 * 参数异常
	 * */
	public static Result parameterError(){
		return new Result(RetEnum.RET_PARAM_NOT_FOUND.getCode(),RetEnum.RET_PARAM_NOT_FOUND.getMessage(),"");
	}



	/**
	 * 系统异常
	 * */
	public static Result systemError(String message){
		return new Result(RetEnum.RET_UNKNOWN_ERROR.getCode(),message,"");
	}

	/**
	 * 未支付
	 * */
	public static Result noPayError(String result){
		return new Result(RetEnum.RET_BIZ_NO_PAY_FAIL.getCode(),RetEnum.RET_BIZ_NO_PAY_FAIL.getMessage(),result);
	}

	/**
	 * 系统异常
	 * */
	public static Result systemError(String message,Object result){
		return new Result(RetEnum.RET_UNKNOWN_ERROR.getCode(),message,result);
	}

	/**
	 * 系统异常
	 * */
	public static Result systemError(Object result){
		return new Result(RetEnum.RET_UNKNOWN_ERROR.getCode(),RetEnum.RET_UNKNOWN_ERROR.getMessage(),result);
	}

	/**
	 * String  字符串 转  Resulit
	 * @param str
	 * @return
	 */
	public static Result strToResult(String str){
		if( str == null ||  str.length()==0){
			return null;
		}
		return  JSONUtil.toBean(str, Result.class);
	}

	/**
	 * 判断成功
	 * @param result
	 * @return
	 */
	public static boolean isSuccess(Result result){
		if(result ==null){
			return false;
		}
		if(RetEnum.RET_SUCCESS.getCode().equals(result.getCode())){
			return true;
		}
		return false;
	}

	/**
	 * 判断未成功
	 * @param result
	 * @return
	 */
	public static boolean isNotSuccess(Result result){
		return !isSuccess(result);
	}
}
