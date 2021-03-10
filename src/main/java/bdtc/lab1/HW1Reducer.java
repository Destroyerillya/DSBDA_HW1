package bdtc.lab1;

import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
/**
 * Редьюсер: считает количество ошибок определённого типа за час.
 */
public class HW1Reducer extends Reducer<Text, IntWritable, Text, MapWritable> {
    private static Text keyWritable = new Text();
    private static MapWritable dictWritable = new MapWritable();
    private static final String[] values_key =
            {"emerg, panic", "alert", "crit", "err, error", "warning, warn", "notice", "info", "debug"};

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        keyWritable = new Text();
        dictWritable = new MapWritable();
        while (values.iterator().hasNext()) {
            int element = values.iterator().next().get();
            IntWritable valueWritable = new IntWritable();
            keyWritable.set(new Text(values_key[element]));
            System.out.println("key " + keyWritable );
            if (dictWritable.get(keyWritable) != null) {
                valueWritable = (IntWritable) dictWritable.get(keyWritable);
                valueWritable.set(valueWritable.get() + 1);

                dictWritable.put(new Text(keyWritable), valueWritable);
                System.out.println("dict exist " + dictWritable);
            }
            else {
                valueWritable.set(1);
                dictWritable.put(new Text(keyWritable), valueWritable);
                System.out.println("dict first " + dictWritable);
            }
        }
        context.write(key, dictWritable);
        System.out.println("dict " + key + " " + dictWritable);
    }
}
