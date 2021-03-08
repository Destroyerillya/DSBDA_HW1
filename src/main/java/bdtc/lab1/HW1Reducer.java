package bdtc.lab1;

import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Редьюсер: суммирует все единицы полученные от {@link HW1Mapper}, выдаёт суммарное количество пользователей по браузерам
 */
public class HW1Reducer extends Reducer<Text, IntWritable, Text, Map<String, Integer>> {
    private static final String[] values_key = {"emerg,panic","alert", "crit", "err,error","warning,warn","notice","info","debug"};
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int i = 0;
        Map<String, Integer> dict = new HashMap();
        while (values.iterator().hasNext()) {
            i = values.iterator().next().get();
            if (dict.get(values_key[i]) != null) {
                dict.put(values_key[i], dict.get(values_key[i]) + 1);
            }
            else{
                dict.put(values_key[i], 1);
            }
        }
        context.write(key, dict);
    }
}
