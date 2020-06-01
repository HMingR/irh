package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.goods.api.pojo.ProductRotationImgInfo;
import top.imuster.goods.dao.ProductRotationImgInfoDao;

import java.util.List;

/**
 * ProductRotationImgInfoDao 实现类
 * @author 黄明人
 * @since 2020-06-01 15:45:21
 */
@Repository("productRotationImgInfoDao")
public class ProductRotationImgInfoDaoImpl extends BaseDaoImpl<ProductRotationImgInfo, Long> implements ProductRotationImgInfoDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ProductRotationImgInfoDao.";
	private final static String UPDATE_CLICK_TOTAL = "updateClickTotal";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Integer updateClickTotal(List<BrowserTimesDto> res) {
		return this.update(getNameSpace(UPDATE_CLICK_TOTAL), res);
	}
}