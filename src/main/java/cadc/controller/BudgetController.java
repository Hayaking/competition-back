package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Budget;
import cadc.service.BudgetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author haya
 */
@Log4j2
@RestController
public class BudgetController {
    @Autowired
    private BudgetService budgetService;
    
    @RequestMapping(value = "budget/list", method = RequestMethod.POST)
    public Object create(@RequestBody List<Budget> budgets) {
        for (Budget item: budgets) {
            item.insert();
        }
        return MessageFactory.message( true );
    }
}
