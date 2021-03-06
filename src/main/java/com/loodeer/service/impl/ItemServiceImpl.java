package com.loodeer.service.impl;

import com.loodeer.controller.viewObject.ItemVO;
import com.loodeer.dao.ItemDOMapper;
import com.loodeer.dao.ItemStockDOMapper;
import com.loodeer.dataobject.ItemDO;
import com.loodeer.dataobject.ItemStockDO;
import com.loodeer.error.BussinessException;
import com.loodeer.error.EmBussinessError;
import com.loodeer.service.ItemService;
import com.loodeer.service.PromoService;
import com.loodeer.service.model.ItemModel;
import com.loodeer.service.model.PromoModel;
import com.loodeer.validator.ValidationResult;
import com.loodeer.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ValidatorImpl validator;

    @Resource
    private ItemDOMapper itemDOMapper;

    @Resource
    private ItemStockDOMapper itemStockDOMapper;

    @Resource
    private PromoServiceImpl promoService;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BussinessException {
        // 1. 入参校验
        ValidationResult validationResult = validator.validate(itemModel);
        if (validationResult.isHasErrors()) {
            throw new BussinessException(EmBussinessError.PARAM_INVALID, validationResult.getErrMsg());
        }
        // 2. 转化 itemModel -> dataObject
        ItemDO itemDO = convertItemDOFromItemModel(itemModel);
        // 3. 写入数据库
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        ItemStockDO itemStockDO = convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        // 4. 返回创建完的对象
        return getItemById(itemModel.getId());
    }

    @Override public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.itemList();
        List<ItemModel> itemModelList = new ArrayList<>();
        for (int i = 0; i < itemDOList.size(); i++) {
            ItemDO itemDO = itemDOList.get(i);
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = convertFromDataObject(itemDO, itemStockDO);
            itemModelList.add(itemModel);
        }
        return itemModelList;
    }

    @Override public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if (itemDO == null) {
            return null;
        }
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(id);

        // 将 dataObject 转为 model
        ItemModel itemModel = convertFromDataObject(itemDO, itemStockDO);

        // 查询秒杀信息
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if (promoModel != null && promoModel.getStatus() != 3) {
            itemModel.setPromoModel(promoModel);
        }

        return itemModel;
    }

    @Override public Boolean decreaseStock(Integer itemId, Integer amount) {

        int affectRow = itemStockDOMapper.decreaseStock(itemId, amount);
        if (affectRow > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override public Boolean increaseSales(Integer itemId, Integer amount) {
        int affectedRow = itemDOMapper.increaseSales(itemId, amount);
        if (affectedRow > 0) {
            return true;
        } else {
            return false;
        }
    }

    private ItemModel convertFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }

    private ItemDO convertItemDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);

        return itemDO;
    }

    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    public ItemVO convertVOFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        itemVO.setPrice(BigDecimal.valueOf(itemModel.getPrice()).divide(new BigDecimal(100)));
        if (itemModel.getPromoModel() != null) {
            itemVO.setPromoVO(promoService.convertPromoVOFromPromoModel(itemModel.getPromoModel()));
        }
        return itemVO;
    }
}
