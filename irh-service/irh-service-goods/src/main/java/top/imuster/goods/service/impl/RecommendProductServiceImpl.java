package top.imuster.goods.service.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.enums.ReleaseType;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.service.RecommendProductService;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * @ClassName: RecommendProductServiceImpl
 * @Description: RecommendProductServiceImpl
 * @author: hmr
 * @date: 2020/5/1 14:04
 */
@Service("recommendProductService")
public class RecommendProductServiceImpl implements RecommendProductService {

    @Override
    public Message<Page<ProductInfo>> getRecommendListByUserId(Integer pageSize, Integer currentPage, Long userId) {
        return null;
    }

    @Override
    public Message<List<Object>> recommendTagNames(String text) throws IOException {
        Analyzer anal = new IKAnalyzer(true);
        StringReader reader=new StringReader(text);
        //分词
        TokenStream ts = anal.tokenStream("", reader);
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
        //遍历分词数据
        ts.reset();
        Set<String> words = new HashSet<>();
        while(ts.incrementToken()){
            String s = term.toString();
            if(s.length() <= 1) continue;
            words.add(s);
        }
        ts.close();
        reader.close();
        String tempKey = UUID.randomUUID().toString();
        redisTemplate.opsForSet().add(tempKey, (String[])words.toArray(new String[words.size()]));
        Set<String> result = redisTemplate.opsForSet().intersect(RedisUtil.getRedisTagNameKey(ReleaseType.GOODS), tempKey);
        List<Object> res = Arrays.asList(result.toArray());
        redisTemplate.delete(tempKey);
        return Message.createBySuccess(res);
    }
}
