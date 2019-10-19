package cadc.mapper.model;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author haya
 */
public class Menu2Model {
    public String getByIdList(Map<String,Object> map) {
        List<Integer> list = (List<Integer>) map.get( "list" );
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM menu2 WHERE id IN(");
//        MessageFormat mf = new MessageFormat("{0}");
        for (int i = 0; i < list.size(); i++) {
            sb.append( list.get( i ) );
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
//        list.forEach( (item,index) -> {
////            sb.append(mf.format(new Object[]{item}));
//            if (item < list.size() - 1) {
//                sb.append(",");
//            }
//        } );
        sb.append(")");
        return sb.toString();
//        new SQL(){{
//            SELECT("*");
//            FROM("menu2");
//            WHERE("id="+para.get("id"));
//        }}.toString();
    }
}
