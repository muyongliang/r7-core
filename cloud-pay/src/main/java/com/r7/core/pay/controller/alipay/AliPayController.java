package com.r7.core.pay.controller.alipay;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayFundAuthOrderFreezeResponse;
import com.alipay.api.response.AlipayFundCouponOrderAgreementPayResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.ijpay.alipay.AliPayApi;
import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.RsaKit;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.pay.entity.AliPayBean;
import com.r7.core.pay.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author muyongliang
 * @date 2020/11/6 17:20
 * @description 支付宝支付接口
 */
@RestController
@RequestMapping("/aliPay")
@Slf4j
public class AliPayController extends AbstractAliPayApiController {
    @Resource
    private AliPayBean aliPayBean;
    /**
     * 证书模式
     */
    private final static String NOTIFY_URL = "/aliPay/cert_notify_url";
    /**
     * 证书模式
     */
    private final static String RETURN_URL = "/aliPay/cert_return_url";

    @Override
    public AliPayApiConfig getApiConfig() throws AlipayApiException {
        AliPayApiConfig aliPayApiConfig;
        try {
            aliPayApiConfig = AliPayApiConfigKit.getApiConfig(aliPayBean.getAppId());
        } catch (Exception e) {
            aliPayApiConfig = AliPayApiConfig.builder()
                    .setAppId(aliPayBean.getAppId())
                    .setAliPayPublicKey(aliPayBean.getPublicKey())
                    .setAppCertPath(aliPayBean.getAppCertPath())
                    .setAliPayCertPath(aliPayBean.getAliPayCertPath())
                    .setAliPayRootCertPath(aliPayBean.getAliPayRootCertPath())
                    .setCharset("UTF-8")
                    .setPrivateKey(aliPayBean.getPrivateKey())
                    .setServiceUrl(aliPayBean.getServerUrl())
                    .setSignType("RSA2")
                    // 证书模式
                    .buildByCert();
        }
        return aliPayApiConfig;
    }

    @ApiOperation(
            value = "app支付生成支付订单"
    )
    @GetMapping
    public ResponseEntity appPay() throws Exception {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据-By Javen");
        model.setSubject("App支付测试-By Javen");
        model.setOutTradeNo("StringUtils.getOutTradeNo()");
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setPassbackParams("callback params");
        model.setProductCode("QUICK_MSECURITY_PAY");
        String orderInfo = AliPayApi.appPayToResponse(model, aliPayBean.getDomain() + NOTIFY_URL).getBody();
        log.info("生成的订单信息为:{}", orderInfo);
        return ResponseEntity.success(orderInfo);
    }

    @PostMapping(value = "/cert_notify_url")
    @ApiOperation(
            value = "支付宝异步回调接口,不由前端操作"
    )
    public String certNotifyUrl(HttpServletRequest request) {
        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = AliPayApi.toMap(request);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                log.info(entry.getKey() + " = " + entry.getValue());
            }
            boolean verifyResult = AlipaySignature.rsaCertCheckV1(params, aliPayBean.getAliPayCertPath(), "UTF-8", "RSA2");
            if (verifyResult) {
                //  请在这里加上商户的业务逻辑程序代码 异步通知可能出现订单重复通知 需要做去重处理
                log.info("certNotifyUrl 验证成功succcess");
                return "success";
            } else {
                log.info("certNotifyUrl 验证失败");
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }

    @RequestMapping(value = "/wapPayNoSdk")
    public void wapPayNoSdk(HttpServletResponse response) {
        try {
            AliPayApiConfig aliPayApiConfig = AliPayApiConfigKit.getAliPayApiConfig();
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("app_id", aliPayApiConfig.getAppId());
            paramsMap.put("method", "alipay.trade.wap.pay");
            paramsMap.put("return_url", aliPayBean.getDomain() + RETURN_URL);
            paramsMap.put("charset", aliPayApiConfig.getCharset());
            paramsMap.put("sign_type", aliPayApiConfig.getSignType());
            paramsMap.put("timestamp", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            paramsMap.put("version", "1.0");
            paramsMap.put("notify_url", aliPayBean.getDomain() + NOTIFY_URL);
            Map<String, String> bizMap = new HashMap<>();
            bizMap.put("body", "IJPay 聚合支付-H5");
            bizMap.put("subject", "IJPay 让支付触手可及");
            bizMap.put("out_trade_no", "");
            bizMap.put("total_amount", "6.66");
            bizMap.put("product_code", "QUICK_WAP_WAY");
            paramsMap.put("biz_content", JSON.toJSONString(bizMap));
            String content = PayKit.createLinkString(paramsMap);
            log.info(content);
            String encrypt = RsaKit.encryptByPrivateKey(content, aliPayApiConfig.getPrivateKey());
            log.info(encrypt);
//            encrypt = AlipaySignature.rsaSign(content,aliPayApiConfig.getPrivateKey(), "UTF-8","RSA2");
//            log.info(encrypt);
            paramsMap.put("sign", encrypt);
            String url = aliPayApiConfig.getServiceUrl() + "?" + PayKit.createLinkString(paramsMap, true);
            log.info(url);
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/wapPay")
    public void wapPay(HttpServletResponse response) {
        String body = "我是测试数据-By Javen";
        String subject = "Javen Wap支付测试";
        String totalAmount = "1";
        String passBackParams = "1";
        String returnUrl = aliPayBean.getDomain() + RETURN_URL;
        String notifyUrl = aliPayBean.getDomain() + NOTIFY_URL;
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setPassbackParams(passBackParams);
        String outTradeNo = StringUtils.getOutTradeNo();
        log.info("wap outTradeNo>" + outTradeNo);
        model.setOutTradeNo(outTradeNo);
        model.setProductCode("QUICK_WAP_PAY");
        try {
            AliPayApi.wapPay(response, model, returnUrl, notifyUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * PC支付
     */
    @RequestMapping(value = "/pcPay")
    public void pcPay(HttpServletResponse response) {
        try {
            String totalAmount = "88.88";
            String outTradeNo = StringUtils.getOutTradeNo();
            log.info("pc outTradeNo>" + outTradeNo);
            String returnUrl = aliPayBean.getDomain() + RETURN_URL;
            String notifyUrl = aliPayBean.getDomain() + NOTIFY_URL;
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setOutTradeNo(outTradeNo);
            model.setProductCode("FAST_INSTANT_TRADE_PAY");
            model.setTotalAmount(totalAmount);
            model.setSubject("Javen PC支付测试");
            model.setBody("Javen IJPay PC支付测试");
            model.setPassbackParams("passback_params");
            /**
             * 花呗分期相关的设置,测试环境不支持花呗分期的测试
             * hb_fq_num代表花呗分期数，仅支持传入3、6、12，其他期数暂不支持，传入会报错；
             * hb_fq_seller_percent代表卖家承担收费比例，商家承担手续费传入100，用户承担手续费传入0，仅支持传入100、0两种，其他比例暂不支持，传入会报错。
             */
//            ExtendParams extendParams = new ExtendParams();
//            extendParams.setHbFqNum("3");
//            extendParams.setHbFqSellerPercent("0");
//            model.setExtendParams(extendParams);
            AliPayApi.tradePage(response, model, notifyUrl, returnUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/tradePay")
    public String tradePay(@RequestParam("authCode") String authCode, @RequestParam("scene") String scene) {
        String subject = null;
        String waveCode = "wave_code";
        String barCode = "bar_code";
        if (scene.equals(waveCode)) {
            subject = "Javen 支付宝声波支付测试";
        } else if (scene.equals(barCode)) {
            subject = "Javen 支付宝条形码支付测试";
        }
        String totalAmount = "100";
        String notifyUrl = aliPayBean.getDomain() + NOTIFY_URL;
        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setAuthCode(authCode);
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setOutTradeNo(StringUtils.getOutTradeNo());
        model.setScene(scene);
        try {
            return AliPayApi.tradePayToResponse(model, notifyUrl).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 扫码支付
     */
    @RequestMapping(value = "/tradePreCreatePay")
    public String tradePreCreatePay() {
        String subject = "Javen 支付宝扫码支付测试";
        String totalAmount = "86";
        String storeId = "123";
//        String notifyUrl = aliPayBean.getDomain() + NOTIFY_URL;
        String notifyUrl = aliPayBean.getDomain() + "/aliPay/cert_notify_url";
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setStoreId(storeId);
        model.setTimeoutExpress("5m");
        model.setOutTradeNo(StringUtils.getOutTradeNo());
        try {
            String resultStr = AliPayApi.tradePrecreatePayToResponse(model, notifyUrl).getBody();
            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            return jsonObject.getJSONObject("alipay_trade_precreate_response").getString("qr_code");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 单笔转账到支付宝账户
     * https://docs.open.alipay.com/309/106235/
     */
    @RequestMapping(value = "/transfer")
    public String transfer() {
        String totalAmount = "66";
        AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
        model.setOutBizNo(StringUtils.getOutTradeNo());
        model.setPayeeType("ALIPAY_LOGONID");
        model.setPayeeAccount("gxthqd7606@sandbox.com");
        model.setAmount(totalAmount);
        model.setPayerShowName("测试退款");
        model.setPayerRealName("沙箱环境");
        model.setRemark("javen测试单笔转账到支付宝");
        try {
            return AliPayApi.transferToResponse(model).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/transferQuery")
    public String transferQuery(@RequestParam(required = false, name = "outBizNo") String outBizNo,
                                @RequestParam(required = false, name = "orderId") String orderId) {
        AlipayFundTransOrderQueryModel model = new AlipayFundTransOrderQueryModel();
        if (StringUtils.isNotEmpty(outBizNo)) {
            model.setOutBizNo(outBizNo);
        }
        if (StringUtils.isNotEmpty(orderId)) {
            model.setOrderId(orderId);
        }
        try {
            return AliPayApi.transferQueryToResponse(model).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/uniTransfer")
    public String uniTransfer() {
        String totalAmount = "1";
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        model.setOutBizNo(StringUtils.getOutTradeNo());
        model.setTransAmount(totalAmount);
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");
        model.setBizScene("DIRECT_TRANSFER");
        model.setOrderTitle("统一转账-转账至支付宝账户");
        model.setRemark("IJPay 测试统一转账");
        Participant payeeInfo = new Participant();
        payeeInfo.setIdentity("gxthqd7606@sandbox.com");
        payeeInfo.setIdentityType("ALIPAY_LOGON_ID");
        payeeInfo.setName("沙箱环境");
        model.setPayeeInfo(payeeInfo);
        try {
            return AliPayApi.uniTransferToResponse(model, null).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/uniTransferQuery")
    public String uniTransferQuery(@RequestParam(required = false, name = "outBizNo") String outBizNo,
                                   @RequestParam(required = false, name = "orderId") String orderId) {
        AlipayFundTransCommonQueryModel model = new AlipayFundTransCommonQueryModel();
        if (StringUtils.isNotEmpty(outBizNo)) {
            model.setOutBizNo(outBizNo);
        }
        if (StringUtils.isNotEmpty(orderId)) {
            model.setOrderId(orderId);
        }
        try {
            return AliPayApi.transCommonQueryToResponse(model, null).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/accountQuery")
    public String accountQuery(@RequestParam(required = true, name = "aliPayUserId") String aliPayUserId) {
        AlipayFundAccountQueryModel model = new AlipayFundAccountQueryModel();
        model.setAlipayUserId(aliPayUserId);
        model.setAccountType("ACCTRANS_ACCOUNT");
        try {
            return AliPayApi.accountQueryToResponse(model, null).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 资金授权冻结接口
     */
    @RequestMapping(value = "/authOrderFreeze")
    public AlipayFundAuthOrderFreezeResponse authOrderFreeze(@RequestParam("auth_code") String authCode) {
        try {
            AlipayFundAuthOrderFreezeModel model = new AlipayFundAuthOrderFreezeModel();
            model.setOutOrderNo(StringUtils.getOutTradeNo());
            model.setOutRequestNo(StringUtils.getOutTradeNo());
            model.setAuthCode(authCode);
            model.setAuthCodeType("bar_code");
            model.setOrderTitle("资金授权冻结-By IJPay");
            model.setAmount("36");
            model.setProductCode("PRE_AUTH");
            return AliPayApi.authOrderFreezeToResponse(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 红包协议支付接口
     * https://docs.open.alipay.com/301/106168/
     */
    @RequestMapping(value = "/agreementPay")
    public AlipayFundCouponOrderAgreementPayResponse agreementPay() {
        try {
            AlipayFundCouponOrderAgreementPayModel model = new AlipayFundCouponOrderAgreementPayModel();
            model.setOutOrderNo(StringUtils.getOutTradeNo());
            model.setOutRequestNo(StringUtils.getOutTradeNo());
            model.setOrderTitle("红包协议支付接口-By IJPay");
            model.setAmount("36");
            model.setPayerUserId("2088102180432465");
            return AliPayApi.fundCouponOrderAgreementPayToResponse(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载对账单
     */
    @RequestMapping(value = "/dataDataServiceBill")
    public String dataDataServiceBill(@RequestParam("billDate") String billDate) {
        try {
            AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
            model.setBillType("trade");
            model.setBillDate(billDate);
            return AliPayApi.billDownloadUrlQuery(model);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退款
     */
    @RequestMapping(value = "/tradeRefund")
    public String tradeRefund(@RequestParam(required = false, name = "outTradeNo") String outTradeNo, @RequestParam(required = false, name = "tradeNo") String tradeNo) {
        try {
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            if (StringUtils.isNotEmpty(outTradeNo)) {
                model.setOutTradeNo(outTradeNo);
            }
            if (StringUtils.isNotEmpty(tradeNo)) {
                model.setTradeNo(tradeNo);
            }
            model.setRefundAmount("86.00");
            model.setRefundReason("正常退款");
            return AliPayApi.tradeRefundToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 交易查询
     */
    @RequestMapping(value = "/tradeQuery")
    public String tradeQuery(@RequestParam(required = false, name = "outTradeNo") String outTradeNo, @RequestParam(required = false, name = "tradeNo") String tradeNo) {
        try {
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            if (StringUtils.isNotEmpty(outTradeNo)) {
                model.setOutTradeNo(outTradeNo);
            }
            if (StringUtils.isNotEmpty(tradeNo)) {
                model.setTradeNo(tradeNo);
            }
            return AliPayApi.tradeQueryToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;


    }

    @RequestMapping(value = "/tradeQueryByStr")
    public String tradeQueryByStr(@RequestParam(required = false, name = "outTradeNo") String outTradeNo, @RequestParam(required = false, name = "tradeNo") String tradeNo) {
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        if (StringUtils.isNotEmpty(outTradeNo)) {
            model.setOutTradeNo(outTradeNo);
        }
        if (StringUtils.isNotEmpty(tradeNo)) {
            model.setTradeNo(tradeNo);
        }
        try {
            return AliPayApi.tradeQueryToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建订单
     * {"alipay_trade_create_response":{"code":"10000","msg":"Success","out_trade_no":"081014283315033","trade_no":"2017081021001004200200274066"},"sign":"ZagfFZntf0loojZzdrBNnHhenhyRrsXwHLBNt1Z/dBbx7cF1o7SZQrzNjRHHmVypHKuCmYifikZIqbNNrFJauSuhT4MQkBJE+YGPDtHqDf4Ajdsv3JEyAM3TR/Xm5gUOpzCY7w+RZzkHevsTd4cjKeGM54GBh0hQH/gSyhs4pEN3lRWopqcKkrkOGZPcmunkbrUAF7+AhKGUpK+AqDw4xmKFuVChDKaRdnhM6/yVsezJFXzlQeVgFjbfiWqULxBXq1gqicntyUxvRygKA+5zDTqE5Jj3XRDjVFIDBeOBAnM+u03fUP489wV5V5apyI449RWeybLg08Wo+jUmeOuXOA=="}
     */
    @RequestMapping(value = "/tradeCreate")
    public String tradeCreate(@RequestParam("outTradeNo") String outTradeNo) {
        String notifyUrl = aliPayBean.getDomain() + NOTIFY_URL;
        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount("88.88");
        model.setBody("Body");
        model.setSubject("Javen 测试统一收单交易创建接口");
        //买家支付宝账号，和buyer_id不能同时为空
        model.setBuyerLogonId("abpkvd0206@sandbox.com");
        try {
            AlipayTradeCreateResponse response = AliPayApi.tradeCreateToResponse(model, notifyUrl);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 撤销订单
     */
    @RequestMapping(value = "/tradeCancel")
    public String tradeCancel(@RequestParam(required = false, name = "outTradeNo") String outTradeNo, @RequestParam(required = false, name = "tradeNo") String tradeNo) {
        try {
            AlipayTradeCancelModel model = new AlipayTradeCancelModel();
            if (StringUtils.isNotEmpty(outTradeNo)) {
                model.setOutTradeNo(outTradeNo);
            }
            if (StringUtils.isNotEmpty(tradeNo)) {
                model.setTradeNo(tradeNo);
            }
            return AliPayApi.tradeCancelToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭订单
     */
    @RequestMapping(value = "/tradeClose")
    public String tradeClose(@RequestParam("outTradeNo") String outTradeNo, @RequestParam("tradeNo") String tradeNo) {
        try {
            AlipayTradeCloseModel model = new AlipayTradeCloseModel();
            if (StringUtils.isNotEmpty(outTradeNo)) {
                model.setOutTradeNo(outTradeNo);
            }
            if (StringUtils.isNotEmpty(tradeNo)) {
                model.setTradeNo(tradeNo);
            }
            return AliPayApi.tradeCloseToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 结算
     */
    @RequestMapping(value = "/tradeOrderSettle")
    public String tradeOrderSettle(@RequestParam("tradeNo") String tradeNo) {
        try {
            AlipayTradeOrderSettleModel model = new AlipayTradeOrderSettleModel();
            model.setOutRequestNo(StringUtils.getOutTradeNo());
            model.setTradeNo(tradeNo);
            return AliPayApi.tradeOrderSettleToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用授权URL并授权
     */
    @RequestMapping(value = "/toOauth")
    public void toOauth(HttpServletResponse response) {
        try {
            String redirectUri = aliPayBean.getDomain() + "/aliPay/redirect_uri";
            String oauth2Url = AliPayApi.getOauth2Url(aliPayBean.getAppId(), redirectUri);
            response.sendRedirect(oauth2Url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 应用授权回调
     */
    @RequestMapping(value = "/redirect_uri")
    public String redirectUri(@RequestParam("app_id") String appId, @RequestParam("app_auth_code") String appAuthCode) {
        try {
            log.info("app_id:" + appId);
            log.info("app_auth_code:" + appAuthCode);
            //使用app_auth_code换取app_auth_token
            AlipayOpenAuthTokenAppModel model = new AlipayOpenAuthTokenAppModel();
            model.setGrantType("authorization_code");
            model.setCode(appAuthCode);
            return AliPayApi.openAuthTokenAppToResponse(model).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询授权信息
     */
    @RequestMapping(value = "/openAuthTokenAppQuery")
    public String openAuthTokenAppQuery(@RequestParam("appAuthToken") String appAuthToken) {
        try {
            AlipayOpenAuthTokenAppQueryModel model = new AlipayOpenAuthTokenAppQueryModel();
            model.setAppAuthToken(appAuthToken);
            return AliPayApi.openAuthTokenAppQueryToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量付款到支付宝账户有密接口
     */
    @RequestMapping(value = "/batchTrans")
    public void batchTrans(HttpServletResponse response) {
        try {
            String signType = "MD5";
            String notifyUrl = aliPayBean.getDomain() + NOTIFY_URL;
            Map<String, String> params = new HashMap<>(15);
            params.put("partner", "PID");
            params.put("sign_type", signType);
            params.put("notify_url", notifyUrl);
            params.put("account_name", "xxx");
            params.put("detail_data", "流水号1^收款方账号1^收款账号姓名1^付款金额1^备注说明1|流水号2^收款方账号2^收款账号姓名2^付款金额2^备注说明2");
            params.put("batch_no", String.valueOf(System.currentTimeMillis()));
            params.put("batch_num", 1 + "");
            params.put("batch_fee", 10.00 + "");
            params.put("email", "xx@xxx.com");
            AliPayApi.batchTrans(params, aliPayBean.getPrivateKey(), signType, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 地铁购票核销码发码
     */
    @RequestMapping(value = "/voucherGenerate")
    public String voucherGenerate(@RequestParam("tradeNo") String tradeNo) {
        try {
            //需要支付成功的订单号
//			String tradeNo = getPara("tradeNo");
            AlipayCommerceCityfacilitatorVoucherGenerateModel model = new AlipayCommerceCityfacilitatorVoucherGenerateModel();
            model.setCityCode("440300");
            model.setTradeNo(tradeNo);
            model.setTotalFee("8");
            model.setTicketNum("2");
            model.setTicketType("oneway");
            model.setSiteBegin("001");
            model.setSiteEnd("002");
            model.setTicketPrice("4");
            return AliPayApi.voucherGenerateToResponse(model).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/return_url")
    public String returnUrl(HttpServletRequest request) {
        try {
            // 获取支付宝GET过来反馈信息
            Map<String, String> map = AliPayApi.toMap(request);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                log.info(entry.getKey() + " = " + entry.getValue());
            }
            boolean verifyResult = AlipaySignature.rsaCheckV1(map, aliPayBean.getPublicKey(), "UTF-8",
                    "RSA2");
            if (verifyResult) {
                // TODO 请在这里加上商户的业务逻辑程序代码
                log.info("return_url 验证成功");
                return "success";
            } else {
                log.info("return_url 验证失败");
                // TODO
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }

    @RequestMapping(value = "/cert_return_url")
    public String certReturnUrl(HttpServletRequest request) {
        try {
            // 获取支付宝GET过来反馈信息
            Map<String, String> map = AliPayApi.toMap(request);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                log.info(entry.getKey() + " = " + entry.getValue());
            }
            boolean verifyResult = AlipaySignature.rsaCertCheckV1(map, aliPayBean.getAliPayCertPath(), "UTF-8",
                    "RSA2");
            if (verifyResult) {
                // TODO 请在这里加上商户的业务逻辑程序代码
                log.info("certReturnUrl 验证成功");
                return "success";
            } else {
                log.info("certReturnUrl 验证失败");
                // TODO
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }

    @RequestMapping(value = "/notify_url")
    public String notifyUrl(HttpServletRequest request) {
        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = AliPayApi.toMap(request);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                log.info(entry.getKey() + " = " + entry.getValue());
            }
            boolean verifyResult = AlipaySignature.rsaCheckV1(params, aliPayBean.getPublicKey(), "UTF-8", "RSA2");
            if (verifyResult) {
                // TODO 请在这里加上商户的业务逻辑程序代码 异步通知可能出现订单重复通知 需要做去重处理
                log.info("notify_url 验证成功succcess");
                return "success";
            } else {
                log.info("notify_url 验证失败");
                // TODO
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }
}