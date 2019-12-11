package cadc.mapper.model;

import cadc.entity.Join;

import java.util.List;
import java.util.Map;

/**
 * @author haya
 */
public class PriceModel {
    public String getPriceByStudent(Map<String, Object> map) {
        List<Join> list = (List<Join>) map.get( "joinList" );
        StringBuilder sb = new StringBuilder();
        sb.append( "SELECT * FROM price WHERE join_id IN(" );
        for (int i = 0; i < list.size(); i++) {
            sb.append( list.get( i ).getId() );
            if (i < list.size() - 1) {
                sb.append( "," );
            }
        }
        sb.append( ")" );
        return sb.toString();
    }
}
