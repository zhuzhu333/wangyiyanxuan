package com.kgc.consumer.utils.result;

/***
 * created by 北大课工场
 *
 * 统一返回工具类
 */
public class ReturnResultUtils{

    /***
     * 成功 不带数据
     * @return
     */
    public static ReturnResult returnSuccess(){
        ReturnResult returnResult=new ReturnResult();
        returnResult.setCode(0);
        returnResult.setMessage("success");
        return returnResult;
    }
    /***
     * 成功 带数据
     * @return
     */
    public static ReturnResult returnSuccess(Object data){
        ReturnResult returnResult=new ReturnResult();
        returnResult.setMessage("success");
        returnResult.setCode(0);
        returnResult.setData(data);
        return returnResult;
    }
    /***
     * 失败
     * @return
     */
    public static ReturnResult returnFail(Integer code, String message){
        ReturnResult returnResult=new ReturnResult();
        returnResult.setCode(code);
        returnResult.setMessage(message);
        return returnResult;
    }
}
