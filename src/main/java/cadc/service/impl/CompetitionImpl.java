package cadc.service.impl;

import cadc.entity.Competition;
import cadc.entity.Join;
import cadc.mapper.CompetitionMapper;
import cadc.mapper.JoinMapper;
import cadc.service.CompetitionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static cadc.bean.message.STATE.*;

/**
 * @author haya
 */
@Service
@Log4j2
@Transactional(rollbackFor = Exception.class)
public class CompetitionImpl extends ServiceImpl<CompetitionMapper,Competition> implements CompetitionService {
    @Resource
    private CompetitionMapper competitionMapper;

    @Resource
    private JoinMapper joinMapper;

    @Override
    public boolean insertCompetition(Competition competition) {
        return competitionMapper.insert( competition ) > 0;
    }

    @Override
    public Integer add(Competition competition) {
        int insert = competitionMapper.insert( competition );
        return competition.getId();
    }

    @Override
    public boolean deleteById(int id) {
        //先删除join里引用外键的内容
        UpdateWrapper<Join> joinWrapper = new UpdateWrapper<>();
        joinWrapper.eq( "competition_id", id );
        joinMapper.delete( joinWrapper );
        return competitionMapper.deleteById( id ) > 0;
    }

    @Override
    public IPage<Competition> findByGroupId(Page<Competition> page, int groupId) {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.eq( "teacher_group_id", groupId );
        return competitionMapper.selectPage( page, wrapper );
    }

    @Override
    public IPage<Competition> findAll(Page<Competition> page) {
        return competitionMapper.selectPage( page, new QueryWrapper<>() );
    }

    @Override
    public IPage<Competition> find(IPage<Competition> page, String key) {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.like("id", key).or()
                .like("name", key);
        return competitionMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<Competition> findPassByKey(IPage<Competition> page, String key) {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.eq("state", STATE_AGREE.toString())
                .like("id", key).or()
                .like("name", key);
        return competitionMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<Competition> findPassAll(IPage<Competition> page) {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.eq( "state", STATE_AGREE.toString() );
        return competitionMapper.selectPage(page, wrapper);
    }


    @Override
    public boolean setState(int id, String state) {
        return competitionMapper.updateState( id, state ) > 0;
    }

    @Override
    public boolean setStartState(int id, String state) {
        return competitionMapper.updateStartState( id, state ) > 0;
    }

    @Override
    public List<Competition> getStartNoEnd() {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.eq( "start_state", STATE_NOT_START.toString() ).or()
                .eq( "start_state", STATE_HAD_START.toString() );
        return competitionMapper.selectList( wrapper );
    }

    @Override
    public List<Competition> get5ByType(int typeId) {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        // 查询已开始的
        wrapper.eq( "type_id", typeId )
                .eq( "state",STATE_AGREE.toString() )
                .eq( "enter_state", STATE_HAD_START.toString() )
                .last( "LIMIT 5" );
        List<Competition> startList = competitionMapper.selectList( wrapper );
        // 按结束时间升序排序
        startList.sort( Comparator.comparing( Competition::getEnterEndTime ) );
        //满5个返回
        if (startList.size() == 5) {
            return startList;
        }
        // 不满5个继续查询 未开始的
        List<Competition> list = new LinkedList<>( startList );
        wrapper = new QueryWrapper<>();
        wrapper.eq( "type_id", typeId )
                .eq( "state",STATE_AGREE.toString() )
                .eq( "enter_state", STATE_NOT_START.toString() )
                .last( "LIMIT 5" );
        List<Competition> noStartList = competitionMapper.selectList( wrapper );
        // 按开始报名时间升序排序
        noStartList.sort( Comparator.comparing( Competition::getEnterStartTime ) );
        list.addAll( noStartList );
        // 满5个
        if (list.size() >= 5) {
            return list.subList( 0,4 );
        }
        wrapper = new QueryWrapper<>();
        wrapper.eq( "type_id", typeId )
                .eq( "state",STATE_AGREE.toString() )
                .eq( "enter_state", STATE_END )
                .last( "LIMIT 5" );
        list.addAll( competitionMapper.selectList( wrapper ) );
        return list;
    }

    @Override
    public List<Competition> getEnterNoEnd() {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.eq( "enter_state", STATE_NOT_START.toString() ).or()
                .eq( "enter_state", STATE_HAD_START.toString() );
        return competitionMapper.selectList( wrapper );
    }

    @Override
    public boolean setEnterState(int id, String state) {
        return competitionMapper.updateEnterState( id, state ) > 0;
    }

    @Override
    public String generateWord(int id) {
        Competition competition = competitionMapper.selectById( id );
        return generateWord( competition );
    }

    @Override
    public String generateWord(Competition competition) {
        // targer路径
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        // word模版路径
        String templatePath = root + "template/";
        // word输出路径
        String outPath = root + "static/word/" + competition.getId() + ".doc";
        // 获取属性
        Field[] declaredFields = competition.getClass().getDeclaredFields();
        Map<String, Object> res = new HashMap<>( 13 );
        Object val;
        String name;
        try {
            // 遍历属性
            for (Field item : declaredFields) {
                // 获取属性名
                name = item.getName();
                item.setAccessible( true );
                // 取值
                val = item.get( competition );
                if (val != null) {
                    res.put( name, val );
                    item.setAccessible( false );
                }
            }
            log.info( res );
            Configuration configuration = new Configuration( new Version( "2.3.0" ) );
            configuration.setDefaultEncoding( "utf-8" );
            configuration.setDirectoryForTemplateLoading( new File( templatePath ) );
            Template template = configuration.getTemplate( "word.ftl" );

            File outFile = new File( outPath);
            Writer writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( outFile ), StandardCharsets.UTF_8 ), 10240 );
            template.process( res, writer );
            writer.close();
        } catch (IllegalAccessException | IOException | TemplateException e) {
            e.printStackTrace();
        }
        return outPath;
    }
}
