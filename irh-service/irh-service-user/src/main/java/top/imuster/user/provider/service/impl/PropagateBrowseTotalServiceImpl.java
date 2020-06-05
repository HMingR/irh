package top.imuster.user.provider.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.service.BrowserTimesService;
import top.imuster.user.provider.service.PropagateInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: PropagateBrowseTotalServiceImpl
 * @Description: PropagateBrowseTotalServiceImpl
 * @author: hmr
 * @date: 2020/6/1 20:35
 */
@Service("propagateBrowseTotalService")
public class PropagateBrowseTotalServiceImpl extends BrowserTimesService {

    @Resource
    PropagateInfoService propagateInfoService;

    @Override
    public void generate() {
        List<BrowserTimesDto> res = getBrowserTimesFromRedis(BrowserType.PROPAGATE);
        if(CollectionUtils.isEmpty(res)) return;
        Integer total = propagateInfoService.saveBrowseTimes2DB(res);
        log.info("------->一共更新了{}条数据", total);
    }
}
