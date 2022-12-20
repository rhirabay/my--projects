package rhirabay.gcp.linebot.service;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rhirabay.gcp.linebot.domain.model.tdl.TdlResource;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TdlService {
    private final BigQuery bigQuery;

    public List<TdlResource> findAllItemsByChannel(String channelId) {
        var query = """
            select
                channel_id,
                item_name
            from `helpful-range-323616.linebot_tdl.tdl_items`
            where channel_id = '%s'
            order by create_at asc
            LIMIT 1000
            """.formatted(channelId);
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
        try {
            TableResult results = bigQuery.query(queryConfig);
            return TdlResource.of(results);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    public void save(TdlResource tdlResource) {
        var query = """
            insert `helpful-range-323616.linebot_tdl.tdl_items`
            ( channel_id, item_name, create_at )
            values
            ( '%s', '%s', CURRENT_TIMESTAMP() )
        """.formatted(tdlResource.id(), tdlResource.item());
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
        try {
           bigQuery.query(queryConfig);
        } catch (Exception ex) {
            // nothing to do
        }
    }

    public void delete(TdlResource resource) {
        var query = """
            delete from `helpful-range-323616.linebot_tdl.tdl_items`
            where channel_id = '%s'
            and item_name = '%s'
        """.formatted(resource.id(), resource.item().substring(1));

        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
        try {
            bigQuery.query(queryConfig);
        } catch (Exception ex) {
            // nothing to do
        }
    }
}
