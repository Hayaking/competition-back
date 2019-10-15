package cadc.service.impl;

import cadc.entity.Budget;
import cadc.mapper.BudgetMapper;
import cadc.service.BudgetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author haya
 */
@Service
public class BudgetServiceImpl extends ServiceImpl<BudgetMapper, Budget> implements BudgetService {

}
