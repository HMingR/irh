package top.imuster.order.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;
import top.imuster.order.provider.dao.ProductDonationApplyInfoDao;

import java.util.HashMap;
import java.util.List;

/**
 * ProductDonationApplyInfoDao 实现类
 * @author 黄明人
 * @since 2020-04-14 16:45:13
 */
@Repository("productDonationApplyInfoDao")
public class ProductDonationApplyInfoDaoImpl extends BaseDaoImpl<ProductDonationApplyInfo, Long> implements ProductDonationApplyInfoDao {
	private final static String NAMESPACE = "top.imuster.order.provider.dao.ProductDonationApplyInfoDao.";
	private final static String SELECT_AVAILABLE_APPLY_BY_ID = "selectAvailableApplyById";
	private final static String SELECT_FINISH_APPLY_LIST = "selectFinishApplyList";
	private final static String SELECT_APPLY_COUNT_BY_STATE = "selectApplyCountByState";
	private final static String SELECT_UN_FINISH_APPLY_LIST = "selectUnfinishApplyList";
	private final static String SELECT_NEWEST_APPLY_INFO = "selectNewestApplyInfo";
	private final static String SELECT_APPLY_INFO_BY_ID = "selectApplyInfoById";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


	@Override
	public ProductDonationApplyInfo selectAvailableApplyById(Long id) {
		return this.select(getNameSpace(SELECT_AVAILABLE_APPLY_BY_ID), id);
	}

	@Override
	public List<ProductDonationApplyInfo> selectFinishApplyList(HashMap<String, Integer> param) {
		return this.selectList(getNameSpace(SELECT_FINISH_APPLY_LIST), param);
	}

	@Override
	public Integer selectApplyCountByState(Integer state) {
		return this.select(getNameSpace(SELECT_APPLY_COUNT_BY_STATE), state);
	}

	@Override
	public List<ProductDonationApplyInfo> selectUnfinishApplyList(HashMap<String, Integer> param) {
		return this.selectList(getNameSpace(SELECT_UN_FINISH_APPLY_LIST), param);
	}

	@Override
	public List<ProductDonationApplyInfo> selectNewestApplyInfo() {
		return this.selectList(getNameSpace(SELECT_NEWEST_APPLY_INFO), null);
	}

	@Override
	public ProductDonationApplyInfo selectApplyInfoById(Long applyId) {
		return this.select(getNameSpace(SELECT_APPLY_INFO_BY_ID), applyId);
	}
}