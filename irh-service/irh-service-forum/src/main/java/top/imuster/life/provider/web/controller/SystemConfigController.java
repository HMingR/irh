package top.imuster.life.provider.web.controller;

import com.sun.management.OperatingSystemMXBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;

import java.lang.management.ManagementFactory;

/**
 * @ClassName: SystemConfigController
 * @Description: forum模块的系统参数配置
 * @author: hmr
 * @date: 2020/2/24 10:31
 */
@RestController
@RequestMapping("/system")
@Api
public class SystemConfigController {

    @ApiOperation("搜索一个时间段内的发布帖子数量趋势  小于15天单位为天,大于15小于3个月单位为周,大于三个月单位为月")
    @GetMapping("/articleTrend")
    public Message articleTrend(){
        return null;
    }

    @ApiOperation("当前跑腿数量")
    @GetMapping("/currentErrand")
    public Message currentErrand(){
        return null;
    }

    @GetMapping("/orderTrend/{startTime}/{endTime}")
    public Message errandOrderTrend(@PathVariable("startTime") String startTime, @PathVariable("endTime") String endTime){
        return null;
    }

    private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    public static int memoryLoad() {
        double totalvirtualMemory = osmxb.getTotalPhysicalMemorySize();
        double freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();

        double value = freePhysicalMemorySize/totalvirtualMemory;
        int percentMemoryLoad = (int) ((1-value)*100);
        return percentMemoryLoad;

    }

    public static void main(String[] args) {
        int i = memoryLoad();
        System.out.println(i);
    }

}
