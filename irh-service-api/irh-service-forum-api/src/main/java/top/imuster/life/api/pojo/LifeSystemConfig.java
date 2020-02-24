package top.imuster.life.api.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

/**
 * @ClassName: LifeSystemConfig
 * @Description: life模块的一些可配置参数
 * @author: hmr
 * @date: 2020/2/24 10:33
 */
@ApiModel("life模块的参数实体类")
public class LifeSystemConfig {

    @ApiModelProperty("热搜榜显示个数")
    @Value("${hot.topic.total}")
    private Long hotTopicTotal;

    @ApiModelProperty("热搜榜刷新时间")
    @Value("${hot.topic.refreshTime}")
    private Long hotTopicRefreshTime;

    @ApiModelProperty("点赞统计任务执行周期")
    private Long upTask;

    @ApiModelProperty("收藏统计任务执行周期")
    private Long collectTask;

    @ApiModelProperty("转发统计任务执行周期")
    private Long forwardTask;

    /**
     * @Author hmr
     * @Description 也就是当点赞数量过多时需要分批处理的大小
     * @Date: 2020/2/24 10:36
     **/
    @ApiModelProperty("单次处理点赞收藏的个数")
    @Value("${batch.size}")
    private Integer batchSize;

    public synchronized Long getHotTopicTotal() {
        return hotTopicTotal;
    }

    public synchronized void setHotTopicTotal(Long hotTopicTotal) {
        this.hotTopicTotal = hotTopicTotal;
    }

    public synchronized Long getHotTopicRefreshTime() {
        return hotTopicRefreshTime;
    }

    public synchronized void setHotTopicRefreshTime(Long hotTopicRefreshTime) {
        this.hotTopicRefreshTime = hotTopicRefreshTime;
    }

    public synchronized Integer getBatchSize() {
        return batchSize;
    }

    public synchronized void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }
}
