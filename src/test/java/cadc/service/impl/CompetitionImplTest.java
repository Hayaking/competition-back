package cadc.service.impl;

import cadc.entity.Competition;
import cadc.mapper.CompetitionMapper;
import cadc.service.CompetitionService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.junit.Assert.*;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompetitionImplTest {
    @Resource
    private CompetitionMapper competitionMapper;
    @Autowired
    private CompetitionService competitionService;
    @Test
    public void get() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Competition c = competitionMapper.getWithProgressListById( 84 );
        Map<String, String> map = BeanUtils.describe( c );
        log.info( map );
    }

    @Test
    public void createCompetition() {
    }

    @Test
    public void add() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void findByGroupId() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void find() {
    }

    @Test
    public void findPassByKey() {
    }

    @Test
    public void findPassAll() {
    }

    @Test
    public void setState() {
    }

    @Test
    public void setStartState() {
    }

    @Test
    public void getStartNoEnd() {
    }

    @Test
    public void get5ByType() {
    }

    @Test
    public void getEnterNoEnd() {
    }

    @Test
    public void setEnterState() {
    }

    @Test
    public void generateWord() {
//        String path = competitionService.generateWord( 84 );
//        log.info( path );
    }

    @Test
    public void testGenerateWord() {
    }

    @Test
    public void getWord() {
    }

    @Test
    public void testFindByGroupId() {
    }
}
