package com.loodeer.controller;

import com.loodeer.controller.viewObject.OrderVO;
import com.loodeer.error.BussinessException;
import com.loodeer.error.EmBussinessError;
import com.loodeer.response.CommonResult;
import com.loodeer.service.OrderService;
import com.loodeer.service.impl.OrderServiceImpl;
import com.loodeer.service.model.OrderModel;
import com.loodeer.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author loodeer
 * @date 2018-12-19 00:01
 */
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(origins = { "*" }, allowCredentials = "true")
public class OrderController extends BaseController {

    @Resource
    private OrderServiceImpl orderService;

    @Resource
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "create", method = { RequestMethod.POST }, consumes = { CONTENT_TYPE_FORMED })
    @ResponseBody
    public CommonResult create(@RequestParam("itemId") Integer itemId,
            @RequestParam("amount") Integer amount, @RequestParam("promoId") Integer promoId)
            throws BussinessException {

        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin) {
            throw new BussinessException(EmBussinessError.USER_NOT_LOGIN);
        }

        // 获取登陆信息
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, amount, promoId);
        OrderVO orderVO = orderService.convertOrderVOFromPromoModel(orderModel);
        return CommonResult.create(orderVO);
    }

}
