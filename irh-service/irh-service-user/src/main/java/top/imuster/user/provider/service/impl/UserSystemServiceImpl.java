package top.imuster.user.provider.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.user.api.dto.UserTrendDto;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.UserInfoService;
import top.imuster.user.provider.service.UserSystemService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserSystemServiceImpl
 * @Description: 用户系统服务
 * @author: hmr
 * @date: 2020/2/26 10:08
 */
@Service("userSystemService")
public class UserSystemServiceImpl implements UserSystemService {

    @Resource
    UserInfoService userInfoService;

    @Override
    public Message<UserTrendDto> userTrend(Integer type) {
        UserTrendDto userTrendDto = new UserTrendDto();
        List<Long> increments = new ArrayList<>();
        List<Long> userTotals = new ArrayList<>();
        List<String> abscissaUnit = new ArrayList<>();
        Long userTotal = 0L;
        if(type == 1){
            //获得一周的开始时间和结束时间
            ArrayList<String> weekStartTimeAndEndTime = DateUtil.getWeekStartTimeAndEndTime();
            //获得一周7天的开始时间
            List<String> weekDayStartTime = DateUtil.getBetweenDates(weekStartTimeAndEndTime.get(0), weekStartTimeAndEndTime.get(1));

            if(weekDayStartTime == null || weekDayStartTime.isEmpty()) throw new UserException();

            for (int i = 0; i < weekDayStartTime.size(); i++) {
                String start = DateUtil.getTheDateOfStartTime(weekDayStartTime.get(i), "yyyy-MM-dd");
                String end = DateUtil.getTheDateOfEndTime(weekDayStartTime.get(i), "yyyy-MM-dd");
                Long increment = userInfoService.getIncrementUserByTime(start, end);
                userTotal = userInfoService.getUserTotalByCreateTime(start);
                userTotals.add(userTotal);
                increments.add(increment);
            }
            abscissaUnit = weekDayStartTime;
            userTrendDto.setUnit("天");
        }else if(type == 2){
            //一个月
            String startTime = DateUtil.getPastDate(30);
            String endTime = DateUtil.now();
            List<String> days = DateUtil.getBetweenDates(startTime, endTime);
            userTotal = userInfoService.getUserTotalByCreateTime(startTime);
            userTotals.add(userTotal);
            days.stream().forEach(time -> {
                String start = DateUtil.getTheDateOfStartTime(time, "yyyy-MM-dd");
                String end = DateUtil.getTheDateOfEndTime(time, "yyyy-MM-dd");
                long increment = userInfoService.getIncrementUserByTime(start, end);
                increments.add(increment);
                userTotals.add(userTotals.get(userTotals.size()-1) + increment);
            });
            abscissaUnit = days;
        }else if(type == 3){
            //最近半年
            ArrayList<String> days = DateUtil.getDays(180);
            int m = 7;
            for (int j = 0; j < days.size() - 1;) {
                String start = days.get(j);
                String end = days.get(m - 1);
                userTotal = userInfoService.getUserTotalByCreateTime(start);
                if(m > days.size() - 7){
                    m = days.size();
                    j += 7;
                }else{
                    j += 7;
                    m = j + 7;
                }
                abscissaUnit.add(start);
                long increment = userInfoService.getIncrementUserByTime(start, end);
                increments.add(increment);
                userTotals.add(userTotal);
            }
            userTrendDto.setUnit("周");
        }else if(type == 4){
            //最近一年
            String startTime = DateUtil.getPastDate(365);
            String endTime = DateUtil.getPreMonth(startTime);
            boolean flag = true;
            while (flag){
                //当目前的月份和搜索的时间的结束时间相同时，则再循环一次就可以退出循环
                if(DateUtil.getPreMonth(DateUtil.getMinMonthDate(DateUtil.now())).equalsIgnoreCase(endTime)){
                    startTime = endTime;
                    endTime = DateUtil.now();
                    flag = false;
                }
                userTotal = userInfoService.getUserTotalByCreateTime(startTime);
                long increment = userInfoService.getIncrementUserByTime(startTime, endTime);
                increments.add(increment);
                userTotals.add(userTotal);
                startTime = endTime;
                endTime = DateUtil.getPreMonth(endTime);
                abscissaUnit.add(new StringBuilder().append(startTime.substring(0, 7)).append("月").toString());
            }
            userTrendDto.setUnit("月");
        }
        userTrendDto.setUserTotals(userTotals);
        userTrendDto.setIncrements(increments);
        userTrendDto.setAbscissaUnit(abscissaUnit);
        userTrendDto.setMax(userTotal + 7);                 //加7就是为了前端显示好看，没有其他作用
        userTrendDto.setInterval(userTotal.intValue() / 15 + 1);
        return Message.createBySuccess(userTrendDto);
    }
}
