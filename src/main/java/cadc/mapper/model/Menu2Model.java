package cadc.mapper.model;

import java.util.List;
import java.util.Map;

/**
 * @author haya
 */
public class Menu2Model {
    public String getByIdList(Map<String, Object> map) {
        List<Integer> list = (List<Integer>) map.get( "list" );
        StringBuilder sb = new StringBuilder();
        sb.append( "SELECT * FROM menu2 WHERE id IN(" );
        for (int i = 0; i < list.size(); i++) {
            sb.append( list.get( i ) );
            if (i < list.size() - 1) {
                sb.append( "," );
            }
        }
        sb.append( ")" );
        return sb.toString();
    }
}
